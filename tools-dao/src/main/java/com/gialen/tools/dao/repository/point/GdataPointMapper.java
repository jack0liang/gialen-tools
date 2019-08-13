package com.gialen.tools.dao.repository.point;

import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.dto.UvDto;
import com.gialen.tools.dao.entity.point.GdataPoint;
import com.gialen.tools.dao.entity.point.GdataPointExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

@org.springframework.stereotype.Repository
public interface GdataPointMapper {


    Integer countMiniappUv(String startTime, String endTime);

    Integer countAppUv(String startTime, String endTime);

    Integer countH5Uv(String startTime, String endTime);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    long countByExample(GdataPointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int deleteByExample(GdataPointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int insert(GdataPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int insertSelective(GdataPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    List<GdataPoint> selectByExample(GdataPointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    GdataPoint selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GdataPoint record, @Param("example") GdataPointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") GdataPoint record, @Param("example") GdataPointExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GdataPoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gdata_point
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GdataPoint record);
}