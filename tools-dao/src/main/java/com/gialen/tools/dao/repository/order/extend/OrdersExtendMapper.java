package com.gialen.tools.dao.repository.order.extend;

import com.gialen.tools.dao.dto.BigSuperMgrSalesDto;
import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.dto.OrderDto;
import com.gialen.tools.dao.dto.SalesDto;
import com.gialen.tools.dao.repository.order.OrdersMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wong
 * @Date: 2019-09-26
 * @Version: 1.0
 */
@org.springframework.stereotype.Repository
public interface OrdersExtendMapper extends OrdersMapper {
    List<OrderDto> countOrderPayNum(String startTime, String endTime);

    List<OrderDto> countOrderPayNumV1(String startTime, String endTime);

    List<SalesDto> countSales(DateTimeDto dateTimeDto, Integer orderType);

    List<SalesDto> countSalesV1(DateTimeDto dateTimeDto, Integer orderType);

    List<OrderDto> countOrderNum(DateTimeDto t);

    List<OrderDto> countUnPaiedOrderNum(DateTimeDto dateTimeDto);

    List<OrderDto> countOrderPayStatusNum(DateTimeDto dateTimeDto);

    BigDecimal calBigSuperMgrSales(@Param("month") Integer month, @Param("userIds") List<Long> userIds, @Param("orderType") Byte orderType);
}
