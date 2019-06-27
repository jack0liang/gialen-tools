package com.gialen.tools.dao.repository.settlement;

import com.gialen.tools.dao.dto.UserIncomeDto;
import com.gialen.tools.dao.entity.settlement.CommissionSettlement;
import com.gialen.tools.dao.entity.settlement.CommissionSettlementExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@org.springframework.stereotype.Repository
public interface CommissionSettlementMapper {

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
    BigDecimal getUserTodaySales(long userId, byte userType, int day);

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