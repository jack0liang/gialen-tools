package com.gialen.tools.dao.repository.gialenMain;

import com.gialen.tools.dao.entity.gialen.BlcOrder;
import com.gialen.tools.dao.entity.gialen.BlcOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.springframework.stereotype.Repository("mainBlcOrderMapper")
public interface BlcOrderMapper {

    int batchUpdateOrderDelivery(List<BlcOrder> deliveryList);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    long countByExample(BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int deleteByExample(BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int insert(BlcOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int insertSelective(BlcOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    List<BlcOrder> selectByExampleWithBLOBs(BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    List<BlcOrder> selectByExample(BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    BlcOrder selectByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BlcOrder record, @Param("example") BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int updateByExampleWithBLOBs(@Param("record") BlcOrder record, @Param("example") BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BlcOrder record, @Param("example") BlcOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BlcOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(BlcOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table blc_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BlcOrder record);
}