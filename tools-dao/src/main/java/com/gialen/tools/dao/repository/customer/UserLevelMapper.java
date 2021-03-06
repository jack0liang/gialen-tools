package com.gialen.tools.dao.repository.customer;

import com.gialen.tools.dao.entity.customer.UserLevel;
import com.gialen.tools.dao.entity.customer.UserLevelExample;
import java.util.List;

@org.springframework.stereotype.Repository
public interface UserLevelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    long countByExample(UserLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    int insert(UserLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    int insertSelective(UserLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    List<UserLevel> selectByExample(UserLevelExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    UserLevel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserLevel record);
}