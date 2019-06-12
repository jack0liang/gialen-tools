package com.gialen.tools.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gialen.tools.common.enums.ExchangeTypeEnum;
import com.gialen.tools.common.enums.UserTypeEnum;
import com.gialen.tools.common.util.CsvUtil;
import com.gialen.tools.common.util.HttpUtil;
import com.gialen.tools.dao.dto.UserScoreDto;
import com.gialen.tools.dao.dto.UserScoreResponseDto;
import com.gialen.tools.service.CoinService;
import com.gialen.common.beantools.Copier;
import com.gialen.common.model.GLResponse;
import com.gialen.common.model.ResponseStatus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lupeibo
 * @date 2019-05-21
 */
@Slf4j
@Service
public class CoinServiceImpl implements CoinService {

    public static final int TEN_COIN_SCORE = 1000;

    public static final int FIVE_COIN_SCORE = 500;

//    private static String sendCoinUrl = "http://cs-jiaomigo.gialen.com/admin/virtualctr/putCoin";

    private static String sendCoinUrl = "http://shop.gialen.com/admin/virtualctr/putCoin";

//    private static String reduceScoreUrl = "http://dev1.pinquest.cn/gialen/MinusLove";


    private static String reduceScoreUrl =  "https://app.pinquest.cn/gateway/gialen/MinusLove";

    private List<UserScoreDto> storeUserScoreDtoList = Lists.newArrayList();

    private List<UserScoreDto> vipUserScoreDtoList = Lists.newArrayList();

    private String coinIdForTen;

    private String coinIdForFive;

    private String sessionId;

    private ExchangeTypeEnum exchangeType = ExchangeTypeEnum.EXCHANGE_All;

    @Override
    public GLResponse processUserScoreToCoin(String filePath, String sessionId,
                                             String coinIdForTen, String coinIdForFive) {
        initSendCoinParams(sessionId, coinIdForTen, coinIdForFive);
        List<List<String>> datas = CsvUtil.readCsv(filePath);
        if(CollectionUtils.isEmpty(datas)) {
            return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(), "读取的数据为空");
        }
        for (List<String> row : datas) {
            if(StringUtils.isBlank(row.get(0)) || StringUtils.isBlank(row.get(1))) {
                continue;
            }

            String userType = row.get(2);
            if(UserTypeEnum.STORE.getName().equalsIgnoreCase(userType)) {
                storeUserScoreDtoList.add(convertToUserScoreDto(row));
            } else if(UserTypeEnum.VIP.getName().equalsIgnoreCase(userType)) {
                vipUserScoreDtoList.add(convertToUserScoreDto(row));
            }
        }
        try {
            //店主分页，每次处理200条
            List<List<UserScoreDto>> storePartitons = Lists.partition(storeUserScoreDtoList, 200);
            for(List<UserScoreDto> storeList : storePartitons) {
                Boolean result = exchange(storeList, UserTypeEnum.STORE);
                if(!result) {
                    return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(), "店主转娇币失败");
                }
            }
            //vip分页，每次处理200条
            List<List<UserScoreDto>> vipPartitons = Lists.partition(vipUserScoreDtoList, 200);
            for(List<UserScoreDto> storeList : vipPartitons) {
                Boolean result = exchange(storeList, UserTypeEnum.VIP);
                if(!result) {
                    return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(), "vip转娇币失败");
                }
            }
        } catch (Exception e) {
            log.error("爱心转娇币业务过程异常：" + e.getMessage());
            return GLResponse.fail(ResponseStatus.GATEWAY_ERROR.getCode(), "爱心转娇币业务过程异常");
        } finally {
            releaseResource();
        }

        return GLResponse.succ(null);
    }

    @Override
    public GLResponse checkDatas(String filePath) {
        List<List<String>> datas = CsvUtil.readCsv(filePath);
        System.out.println("数据行数：" + datas.size());
        int rowNumber = 0;
        int errorCounter = 0;
        for (List<String> row : datas) {
            rowNumber++;
            if(CollectionUtils.isEmpty(row)) {
                log.error("第" + rowNumber + "行数据为空");
                errorCounter++;
                continue;
            }
            String outUserIdStr = row.get(0);
            if(StringUtils.isBlank(outUserIdStr)) {
                log.error("第" + rowNumber + "行外部用户id为空");
                errorCounter++;
                continue;
            }
//            String phone = row.get(1);
//            if(StringUtils.isBlank(phone)) {
//                log.error("第" + rowNumber + "行手机号为空");
//                errorCounter++;
//                continue;
//            }
            String userType = row.get(2);
            if(StringUtils.isBlank(userType)) {
                log.error("第" + rowNumber + "行用户类型为空");
                errorCounter++;
                continue;
            }
            if(!UserTypeEnum.STORE.getName().equalsIgnoreCase(userType) &&
                    !UserTypeEnum.VIP.getName().equalsIgnoreCase(userType)) {
                log.error("第" + rowNumber + "行用户类型不存在");
                errorCounter++;
                continue;
            }
            String scoreStr = row.get(3);
            if(StringUtils.isBlank(scoreStr)) {
                log.error("第" + rowNumber + "行用户爱心值为空");
                errorCounter++;
                continue;
            }
            if(!NumberUtils.isDigits(outUserIdStr)) {
                log.error("第" + rowNumber + "行用户爱心值格式错误");
                errorCounter++;
                continue;
            }
        }
        if(errorCounter > 0) {
            return GLResponse.fail(ResponseStatus.PARAM_ERROR.getCode(), "参数错误");
        }
        return GLResponse.succ("成功");
    }

    private UserScoreDto convertToUserScoreDto(List<String> rowData) {
        UserScoreDto dto = new UserScoreDto();
        String outerUserId = rowData.get(0);
        String phone = rowData.get(1);
        Integer score = Integer.parseInt(rowData.get(3));
        dto.setOuterUserId(outerUserId);
        dto.setPhone(phone);
        dto.setScore(score);
        dto.setUserType(rowData.get(2));
        return dto;
    }

    private Boolean exchange(List<UserScoreDto> userScoreDtoList, UserTypeEnum userTypeEnum) {
        if(CollectionUtils.isEmpty(userScoreDtoList)) {
            log.error("userScoreDtoList is null");
            return Boolean.FALSE;
        }

        List<String> sendTenCoinMobiles = Lists.newArrayList();
        List<String> sendFiveCoinMobiles = Lists.newArrayList();

        List<UserScoreResponseDto> userScoreResponseDtoList = Lists.newArrayList();

        for(UserScoreDto userScoreDto : userScoreDtoList) {
            Integer score = userScoreDto.getScore();
            String phone = userScoreDto.getPhone();
            int reduceScore;
            if(ExchangeTypeEnum.EXCHANGE_All.equals(exchangeType)) {
                reduceScore = setSendCoinMobilesForExchangeAll(sendTenCoinMobiles, sendFiveCoinMobiles, score, phone);
            } else if(ExchangeTypeEnum.EXCHANGE_TEN.equals(exchangeType)){
                reduceScore = setSendCoinMobilesForExchangeTen(sendTenCoinMobiles, score, phone);
            } else if(ExchangeTypeEnum.EXCHANGE_GREATER_AND_EQUALS_FIVE.equals(exchangeType)){
                reduceScore = setSendCoinMobilesForGreaterAndEqualsFive(sendTenCoinMobiles, score, phone);
            } else {
                reduceScore = setSendCoinMobilesForFiveToTen(sendTenCoinMobiles, score, phone);
            }
            if(reduceScore <= 0) {
                continue;
            }
            UserScoreResponseDto userScoreResponseDto = Copier.copy(userScoreDto, new UserScoreResponseDto());
            userScoreResponseDto.setReduceScore(reduceScore);
            userScoreResponseDtoList.add(userScoreResponseDto);
        }
        //调用普微接口发娇币
        Boolean isSuccess;
        if(CollectionUtils.isNotEmpty(sendTenCoinMobiles)) {
            isSuccess = callSendCoin(coinIdForTen, convertMobiles(sendTenCoinMobiles), userTypeEnum.getCode());
            if(! isSuccess) {
                log.error(userTypeEnum.getName() + "发放10元娇币失败");
                return Boolean.FALSE;
            }
        }
        if(CollectionUtils.isNotEmpty(sendFiveCoinMobiles)) {
            isSuccess = callSendCoin(coinIdForFive, convertMobiles(sendFiveCoinMobiles), userTypeEnum.getCode());
            if(! isSuccess) {
                log.error(userTypeEnum.getName() + "发放5元娇币失败");
                return Boolean.FALSE;
            }
        }
        //调用品快接口扣减积分
        isSuccess = reduceScore(userScoreResponseDtoList);
        if(! isSuccess) {
            log.error(userTypeEnum.getName() + "扣减用户爱心值失败");
            return Boolean.FALSE;
        }
        //入库记录处理的数据

        return Boolean.TRUE;
    }

    private Boolean addScore(List<UserScoreResponseDto> userScoreResponseDtoList) {
        if(CollectionUtils.isEmpty(userScoreResponseDtoList)) {
            return Boolean.TRUE;
        }
        for(UserScoreResponseDto userScoreResponseDto : userScoreResponseDtoList) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("account_id", userScoreResponseDto.getOuterUserId());
            params.put("val", userScoreResponseDto.getReduceScore());
            params.put("is_add", true);
            try {
                String result = HttpUtil.sendPost(reduceScoreUrl, params);
                if(StringUtils.isNotBlank(result)) {
                    JSONObject json = JSON.parseObject(result);
                    int code = json.getIntValue("errcode");
                    String msg = json.getString("errmsg");
                    if(code != 0) {
                        log.error(String.format("account_id=%s, errmsg=%s",userScoreResponseDto.getOuterUserId(), msg));
                    } else {
                        log.info(String.format("添加爱心值成功！account_id=%s, addScore=%s",
                                userScoreResponseDto.getOuterUserId(),
                                userScoreResponseDto.getReduceScore()));
                    }
                }
            } catch (Exception e) {
                log.error(String.format("account_id=%s, 调用添加用户爱心值接口异常：%s",
                        userScoreResponseDto.getOuterUserId(), e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return Boolean.TRUE;
    }

    private Boolean reduceScore(List<UserScoreResponseDto> userScoreResponseDtoList) {
        if(CollectionUtils.isEmpty(userScoreResponseDtoList)) {
            return Boolean.TRUE;
        }
        for(UserScoreResponseDto userScoreResponseDto : userScoreResponseDtoList) {
            Map<String, Object> params = Maps.newHashMap();
            params.put("account_id", userScoreResponseDto.getOuterUserId());
            params.put("val", userScoreResponseDto.getReduceScore());
            try {
                String result = HttpUtil.sendPost(reduceScoreUrl, params);
                if(StringUtils.isNotBlank(result)) {
                    JSONObject json = JSON.parseObject(result);
                    int code = json.getIntValue("errcode");
                    String msg = json.getString("errmsg");
                    if(code != 0) {
                        log.error(String.format("account_id=%s, errmsg=%s",userScoreResponseDto.getOuterUserId(), msg));
                    } else {
                        log.info(String.format("扣减爱心值成功！account_id=%s, phone=%s, reduceScore=%s, userType=%s",
                                userScoreResponseDto.getOuterUserId(), userScoreResponseDto.getPhone(),
                                userScoreResponseDto.getReduceScore(), userScoreResponseDto.getUserType()));
                    }
                }
            } catch (Exception e) {
                log.error(String.format("account_id=%s, 调用扣减用户爱心值接口异常：%s",
                        userScoreResponseDto.getOuterUserId(), e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return Boolean.TRUE;
    }

    /**
     *
     * @param sendTenCoinMobiles
     * @param sendFiveCoinMobiles
     * @param score
     * @param phone
     * @return 返回扣减的积分
     */
    private Integer setSendCoinMobilesForExchangeAll(List<String> sendTenCoinMobiles, List<String> sendFiveCoinMobiles,
                                    Integer score, String phone) {
        if(score > 0 && score < FIVE_COIN_SCORE ) {
            sendFiveCoinMobiles.add(phone);
        } else if(score >= FIVE_COIN_SCORE && score <= TEN_COIN_SCORE) {
            sendTenCoinMobiles.add(phone);
        } else if(score > TEN_COIN_SCORE) {
            int tenCoinAmount = score/TEN_COIN_SCORE;
            int remScore = score%TEN_COIN_SCORE;
            if(remScore > 0 && remScore < FIVE_COIN_SCORE ) {
                sendFiveCoinMobiles.add(phone);
            } else if(remScore >= FIVE_COIN_SCORE && remScore <= TEN_COIN_SCORE) {
                sendTenCoinMobiles.add(phone);
            }
            for(int i = 0; i < tenCoinAmount; i++) {
                sendTenCoinMobiles.add(phone);
            }
        }
        return score;
    }

    private Integer setSendCoinMobilesForExchangeTen(List<String> sendTenCoinMobiles, Integer score, String phone) {
        if(score >= TEN_COIN_SCORE ) {
            int tenCoinAmount = score/TEN_COIN_SCORE;
            int remScore = score%TEN_COIN_SCORE;
            for(int i = 0; i < tenCoinAmount; i++) {
                sendTenCoinMobiles.add(phone);
            }
            return (score - remScore);
        }
        return 0;
    }

    private Integer setSendCoinMobilesForGreaterAndEqualsFive(List<String> sendTenCoinMobiles, Integer score, String phone) {
        Integer reduceScore = 0;
        if(score >= FIVE_COIN_SCORE && score <= TEN_COIN_SCORE) {
            sendTenCoinMobiles.add(phone);
            reduceScore = score;
        } else if (score > TEN_COIN_SCORE){
            int tenCoinAmount = score/TEN_COIN_SCORE;
            int remScore = score%TEN_COIN_SCORE;
            if(remScore >= FIVE_COIN_SCORE && remScore <= TEN_COIN_SCORE) {
                sendTenCoinMobiles.add(phone);
                reduceScore += remScore;
            }
            for(int i = 0; i < tenCoinAmount; i++) {
                sendTenCoinMobiles.add(phone);
                reduceScore += TEN_COIN_SCORE;
            }
        }
        return reduceScore;
    }

    private Integer setSendCoinMobilesForFiveToTen(List<String> sendTenCoinMobiles, Integer score, String phone) {
        if(score >= FIVE_COIN_SCORE && score < TEN_COIN_SCORE ) {
            sendTenCoinMobiles.add(phone);
            return score;
        } else if(score > TEN_COIN_SCORE) {
            int remScore = score%TEN_COIN_SCORE;
            if(remScore >= FIVE_COIN_SCORE && remScore <= TEN_COIN_SCORE) {
                sendTenCoinMobiles.add(phone);
                return remScore;
            }
        }
        return 0;
    }

    /**
     * 手机号列表转字符串
     * @param mobileList
     * @return
     */
    private String convertMobiles(List<String> mobileList) {
        int counter = 0;
        StringBuilder mobilesSb = new StringBuilder();
        for(String mobile : mobileList) {
            if(counter == 0) {
                mobilesSb.append(mobile);
            } else {
                mobilesSb.append(",").append(mobile);
            }
            counter++;
        }
        return mobilesSb.toString();
    }

    private Boolean callSendCoin(String coinId, String mobiles, String userType) {
        Map<String, String> params = Maps.newHashMap();
        params.put("coinId", coinId);
        params.put("mobiles", mobiles);
        params.put("userType", userType);
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Cookie", sessionId);
        try {
            String result = HttpUtil.sendGet(sendCoinUrl, params,headers);
            if(result.startsWith("<!DOCTYPE html>")) {
                throw new RuntimeException("JSESSIONID失效");
            }
            if(StringUtils.isNotBlank(result)) {
                JSONObject json = JSON.parseObject(result);
                int status = json.getIntValue("status");
                String msg = json.getString("message");
                if(status != 0) {
                    log.error(String.format("msg=%s, coinId=%s, userType=%s", msg, coinId, userType));
                    return Boolean.FALSE;
                }
            }
        } catch (Exception e) {
            log.error("调用发送娇币接口异常：" + e.getMessage());
            throw new RuntimeException(e);
        }
        return Boolean.TRUE;
    }

    private void initSendCoinParams(String sessionId, String coinIdForTen, String coinIdForFive) {
        this.sessionId = sessionId;
        this.coinIdForTen = coinIdForTen;
        this.coinIdForFive = coinIdForFive;
        storeUserScoreDtoList.clear();
        vipUserScoreDtoList.clear();
    }

    private void releaseResource() {
        storeUserScoreDtoList.clear();
        vipUserScoreDtoList.clear();
    }

}
