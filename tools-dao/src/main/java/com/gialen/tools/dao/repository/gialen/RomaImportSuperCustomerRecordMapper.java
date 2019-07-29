package com.gialen.tools.dao.repository.gialen;

import com.gialen.tools.dao.entity.gialen.RomaImportSuperCustomerRecord;
import com.gialen.tools.dao.entity.gialen.RomaImportSuperCustomerRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@org.springframework.stereotype.Repository
public interface RomaImportSuperCustomerRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    long countByExample(RomaImportSuperCustomerRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int deleteByExample(RomaImportSuperCustomerRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long importCustomerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int insert(RomaImportSuperCustomerRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int insertSelective(RomaImportSuperCustomerRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    List<RomaImportSuperCustomerRecord> selectByExample(RomaImportSuperCustomerRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    RomaImportSuperCustomerRecord selectByPrimaryKey(Long importCustomerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") RomaImportSuperCustomerRecord record, @Param("example") RomaImportSuperCustomerRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") RomaImportSuperCustomerRecord record, @Param("example") RomaImportSuperCustomerRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RomaImportSuperCustomerRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RomaImportSuperCustomerRecord record);
}