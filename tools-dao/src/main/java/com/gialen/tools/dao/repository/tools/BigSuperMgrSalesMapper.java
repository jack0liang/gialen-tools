package com.gialen.tools.dao.repository.tools;

import com.gialen.tools.dao.entity.tools.BigSuperMgrSales;
import com.gialen.tools.dao.entity.tools.BigSuperMgrSalesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@org.springframework.stereotype.Repository
public interface BigSuperMgrSalesMapper {

    /**
     * 批量写入
     * @param bigSuperMgrSalesList
     * @return
     */
    int batchInsertBigSuperMgrSales(List<BigSuperMgrSales> bigSuperMgrSalesList);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    long countByExample(BigSuperMgrSalesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int deleteByExample(BigSuperMgrSalesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int insert(BigSuperMgrSales record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int insertSelective(BigSuperMgrSales record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    List<BigSuperMgrSales> selectByExample(BigSuperMgrSalesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    BigSuperMgrSales selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BigSuperMgrSales record, @Param("example") BigSuperMgrSalesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BigSuperMgrSales record, @Param("example") BigSuperMgrSalesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BigSuperMgrSales record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BigSuperMgrSales record);
}