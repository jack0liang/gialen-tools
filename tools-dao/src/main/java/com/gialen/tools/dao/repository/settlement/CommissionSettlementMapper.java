package com.gialen.tools.dao.repository.settlement;

import com.gialen.tools.dao.dto.StoreIncomeDto;
import com.gialen.tools.dao.dto.UserIncomeDto;
import com.gialen.tools.dao.entity.settlement.CommissionSettlement;
import com.gialen.tools.dao.entity.settlement.CommissionSettlementExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@org.springframework.stereotype.Repository
public interface CommissionSettlementMapper {

    /**
     * 查询门店日总收益
     * @param userId
     * @param day
     * @param brandType
     * @return
     */
    StoreIncomeDto getStoreTotalIncomeByDay(@Param("userId") Long userId, @Param("day") Integer day, @Param("brandType") byte brandType);

    /**
     * 查询门店月总收益
     * @param userId
     * @param month
     * @return
     */
    StoreIncomeDto getStoreTotalIncomeByMonth(@Param("userId") Long userId, @Param("month") Integer month, @Param("brandType") byte brandType);

    /**
     * 查询门店月可用收益
     * @param userId
     * @return
     */
    StoreIncomeDto getStoreAvailableIncomeByMonth(@Param("userId") Long userId, @Param("month") Integer month,  @Param("brandType") byte brandType);

    /**
     * 查询门店待收益
     * @param userId
     * @return
     */
    StoreIncomeDto getStoreToBeIncome(@Param("userId") Long userId, @Param("brandType") byte brandType);

    /**
     * 统计店主佣金
     * @param masterIds
     * @param month
     * @return
     */
    BigDecimal calStoreMasterCommission(@Param("masterIds") List<Long> masterIds, @Param("month") Integer month);

    /**
     * 统计店经佣金
     * @param managerIds
     * @param month
     * @return
     */
    BigDecimal calStoreManagerCommission(@Param("managerIds") List<Long> managerIds, @Param("month") Integer month);

    /**
     * 获取用户的月度销售数据（包含退款数据）
     * @param userId
     * @param userType
     * @param month
     * @return
     */
    UserIncomeDto getUserSalesByMonth(long userId, byte userType, int month, byte brandType);

    /**
     * 获取用户待收益
     * @param userId
     * @param userType
     * @return
     */
    BigDecimal getUserToBeIncome(long userId, byte userType);

    /**
     * 获取用户月可用收益
     * @param userId
     * @param userType
     * @param month
     * @return
     */
    BigDecimal getUserAvailableIncomeByMonth(long userId, byte userType, int month);

    /**
     * 获取用户月总收益
     * @param userId
     * @param userType
     * @param month
     * @return
     */
    UserIncomeDto getUserTotalIncomeByMonth(long userId, byte userType, int month);

    /**
     * 获取用户今日销售额
     * @param userId
     * @param userType
     * @param day
     * @return
     */
    BigDecimal getUserTodaySales(long userId, byte userType, int day, byte brandType);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    long countByExample(CommissionSettlementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int deleteByExample(CommissionSettlementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int insert(CommissionSettlement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int insertSelective(CommissionSettlement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    List<CommissionSettlement> selectByExample(CommissionSettlementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    CommissionSettlement selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CommissionSettlement record, @Param("example") CommissionSettlementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CommissionSettlement record, @Param("example") CommissionSettlementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CommissionSettlement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CommissionSettlement record);
}