package com.gialen.tools.service;

import com.gialen.common.model.GLResponse;

public interface CoinService {

    GLResponse processUserScoreToCoin(String filePath, String sessionId,
                                      String coinIdForTen, String coinIdForFive);

    GLResponse checkDatas(String filePath);
}
