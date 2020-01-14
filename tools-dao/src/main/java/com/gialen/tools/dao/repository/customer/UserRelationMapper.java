package com.gialen.tools.dao.repository.customer;

import com.gialen.common.model.PageRequest;
import com.gialen.tools.dao.dto.ActivityUserDetailDto;
import com.gialen.tools.dao.dto.CommunityDto;
import com.gialen.tools.dao.dto.BigSuperMgrDto;
import com.gialen.tools.dao.entity.customer.UserRelation;
import com.gialen.tools.dao.entity.customer.UserRelationExample;
import java.util.List;

import com.gialen.tools.dao.entity.gialen.BlcCustomer;
import org.apache.ibatis.annotations.Param;

@org.springframework.stereotype.Repository
public interface UserRelationMapper {

    List<Long> getChildUserListByIds(@Param("invitorIds") List<Long> invitorIds, @Param("month") Integer month);

    List<Long> getChildUserList(@Param("invitorId") Long invitorId, @Param("month") Integer month);

    /**
     * 查询大店经列表
     * @return
     */
    List<BigSuperMgrDto> getBigSuperMgrList();

    /**
     * 统计活跃或静默店主数量
     */
    Long countActivityOrSilenceStoreTotal(@Param("userId") Long userId, @Param("month") Integer month,
                                          @Param("userType") Byte userType, @Param("dataType") Byte dataType, @Param("storeName") String storeName);

    /**
     * 查询活跃或静默店主列表
     */
    List<ActivityUserDetailDto> getActivityOrSilenceStoreList(@Param("userId") Long userId, @Param("month") Integer month,
                                                              @Param("userType") Byte userType, @Param("dataType") Byte dataType, @Param("page") PageRequest pageRequest,
                                                              @Param("storeName") String storeName);

    /**
     * 统计店董的月新增店经数量
     */
    Integer countMonthManagerNumForDirector(@Param("directorId") Long directorId, @Param("month") Integer month);

    /**
     * 统计店董的月(日)新增店主数量
     */
    Integer countStoreNumForDirector(@Param("directorId") Long directorId, @Param("month") Integer month, @Param("day") Integer day);

    /**
     * 统计店董的月(日)新增vip数量
     */
    Integer countVipNumForDirector(@Param("directorId") Long directorId, @Param("month") Integer month, @Param("day") Integer day);

    /**
     * 统计店董的总社群数据
     */
    CommunityDto countTotalNumForDirector(@Param("directorId") Long directorId, @Param("userName") String userName);

    /**
     * 统计店经的月新增店经数量
     */
    Integer countMonthManagerNumForManager(@Param("managerId") Long managerId, @Param("month") Integer month);

    /**
     * 统计店经的月(日)新增店主数量
     */
    CommunityDto countStoreNumForManager(@Param("managerId") Long managerId, @Param("month") Integer month, @Param("day") Integer day);

    /**
     * 统计店经的月(日)新增vip数量
     */
    Integer countVipNumForManager(@Param("managerId") Long managerId, @Param("month") Integer month, @Param("day") Integer day);

    /**
     * 统计店经的总社群数据
     */
    CommunityDto countTotalNumForManager(@Param("managerId") Long managerId, @Param("userName") String userName);


    /**
     * 查询店董的月新增下级用户列表
     */
    List<BlcCustomer> getMonthUserChildListForDirector(@Param("directorId") Long directorId, @Param("childType") Byte childType,
                                                       @Param("month") Integer month, @Param("page") PageRequest page);

    /**
     * 查询店董的全部下级用户列表
     */
    List<BlcCustomer> getUserChildListForDirector(@Param("directorId") Long directorId, @Param("childType") Byte childType,
                                                  @Param("page") PageRequest page, @Param("userName") String userName);

    /**
     * 查询店经的月新增下级用户列表
     */
    List<BlcCustomer> getMonthUserChildListForManager(@Param("managerId") Long managerId, @Param("childType") Byte childType,
                                                       @Param("month") Integer month, @Param("page") PageRequest page);

    /**
     * 查询店经的全部下级用户列表
     */
    List<BlcCustomer> getUserChildListForManager(@Param("managerId") Long managerId, @Param("childType") Byte childType,
                                                  @Param("page") PageRequest page, @Param("userName") String userName);


    /**
     * 统计店董的月度 有新增vip的店主数量
     */
    Long countMonthHasNewVipStoreNumForDirector(@Param("directorId") Long directorId, @Param("month") Integer month, @Param("storeName") String storeName);

    /**
     * 查询店董的月新增vip列表
     */
    List<CommunityDto> getMonthNewVipListForDirector(@Param("directorId") Long directorId, @Param("day") Integer day,
                                                     @Param("month") Integer month, @Param("page") PageRequest page,
                                                     @Param("storeName") String storeName);


    /**
     * 统计店经的月度 有新增vip的店主数量
     */
    Long countMonthHasNewVipStoreNumForManager(@Param("managerId") Long managerId, @Param("month") Integer month, @Param("storeName") String storeName);

    /**
     * 查询店经的月新增vip列表
     */
    List<CommunityDto> getMonthNewVipListForManager(@Param("managerId") Long managerId, @Param("day") Integer day,
                                                     @Param("month") Integer month, @Param("page") PageRequest page,
                                                     @Param("storeName") String storeName);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    long countByExample(UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int deleteByExample(UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int insert(UserRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int insertSelective(UserRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    List<UserRelation> selectByExample(UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    UserRelation selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserRelation record, @Param("example") UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserRelation record, @Param("example") UserRelationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserRelation record);
}