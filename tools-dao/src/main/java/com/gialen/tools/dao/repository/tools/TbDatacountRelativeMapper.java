package com.gialen.tools.dao.repository.tools;

import com.gialen.tools.dao.entity.tools.TbDatacountRelative;
import com.gialen.tools.dao.entity.tools.TbDatacountRelativeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@org.springframework.stereotype.Repository
public interface TbDatacountRelativeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    long countByExample(TbDatacountRelativeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int insert(TbDatacountRelative record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int insertSelective(TbDatacountRelative record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    List<TbDatacountRelative> selectByExample(TbDatacountRelativeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    TbDatacountRelative selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbDatacountRelative record, @Param("example") TbDatacountRelativeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbDatacountRelative record, @Param("example") TbDatacountRelativeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbDatacountRelative record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_datacount_relative
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbDatacountRelative record);
}