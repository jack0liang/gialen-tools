package com.gialen.tools.dao.entity.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRelationExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected int offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected int limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public UserRelationExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public int getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public int getLimit() {
        return limit;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andStateDataIsNull() {
            addCriterion("state_data is null");
            return (Criteria) this;
        }

        public Criteria andStateDataIsNotNull() {
            addCriterion("state_data is not null");
            return (Criteria) this;
        }

        public Criteria andStateDataEqualTo(Byte value) {
            addCriterion("state_data =", value, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataNotEqualTo(Byte value) {
            addCriterion("state_data <>", value, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataGreaterThan(Byte value) {
            addCriterion("state_data >", value, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataGreaterThanOrEqualTo(Byte value) {
            addCriterion("state_data >=", value, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataLessThan(Byte value) {
            addCriterion("state_data <", value, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataLessThanOrEqualTo(Byte value) {
            addCriterion("state_data <=", value, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataIn(List<Byte> values) {
            addCriterion("state_data in", values, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataNotIn(List<Byte> values) {
            addCriterion("state_data not in", values, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataBetween(Byte value1, Byte value2) {
            addCriterion("state_data between", value1, value2, "stateData");
            return (Criteria) this;
        }

        public Criteria andStateDataNotBetween(Byte value1, Byte value2) {
            addCriterion("state_data not between", value1, value2, "stateData");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNull() {
            addCriterion("store_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNotNull() {
            addCriterion("store_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreIdEqualTo(Long value) {
            addCriterion("store_id =", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotEqualTo(Long value) {
            addCriterion("store_id <>", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThan(Long value) {
            addCriterion("store_id >", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThanOrEqualTo(Long value) {
            addCriterion("store_id >=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThan(Long value) {
            addCriterion("store_id <", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThanOrEqualTo(Long value) {
            addCriterion("store_id <=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdIn(List<Long> values) {
            addCriterion("store_id in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotIn(List<Long> values) {
            addCriterion("store_id not in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdBetween(Long value1, Long value2) {
            addCriterion("store_id between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotBetween(Long value1, Long value2) {
            addCriterion("store_id not between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdIsNull() {
            addCriterion("store_mgr_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdIsNotNull() {
            addCriterion("store_mgr_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdEqualTo(Long value) {
            addCriterion("store_mgr_id =", value, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdNotEqualTo(Long value) {
            addCriterion("store_mgr_id <>", value, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdGreaterThan(Long value) {
            addCriterion("store_mgr_id >", value, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("store_mgr_id >=", value, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdLessThan(Long value) {
            addCriterion("store_mgr_id <", value, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdLessThanOrEqualTo(Long value) {
            addCriterion("store_mgr_id <=", value, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdIn(List<Long> values) {
            addCriterion("store_mgr_id in", values, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdNotIn(List<Long> values) {
            addCriterion("store_mgr_id not in", values, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdBetween(Long value1, Long value2) {
            addCriterion("store_mgr_id between", value1, value2, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreMgrIdNotBetween(Long value1, Long value2) {
            addCriterion("store_mgr_id not between", value1, value2, "storeMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdIsNull() {
            addCriterion("store_super_mgr_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdIsNotNull() {
            addCriterion("store_super_mgr_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdEqualTo(Long value) {
            addCriterion("store_super_mgr_id =", value, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdNotEqualTo(Long value) {
            addCriterion("store_super_mgr_id <>", value, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdGreaterThan(Long value) {
            addCriterion("store_super_mgr_id >", value, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("store_super_mgr_id >=", value, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdLessThan(Long value) {
            addCriterion("store_super_mgr_id <", value, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdLessThanOrEqualTo(Long value) {
            addCriterion("store_super_mgr_id <=", value, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdIn(List<Long> values) {
            addCriterion("store_super_mgr_id in", values, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdNotIn(List<Long> values) {
            addCriterion("store_super_mgr_id not in", values, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdBetween(Long value1, Long value2) {
            addCriterion("store_super_mgr_id between", value1, value2, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andStoreSuperMgrIdNotBetween(Long value1, Long value2) {
            addCriterion("store_super_mgr_id not between", value1, value2, "storeSuperMgrId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("company_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("company_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(Long value) {
            addCriterion("company_id =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(Long value) {
            addCriterion("company_id <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(Long value) {
            addCriterion("company_id >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("company_id >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(Long value) {
            addCriterion("company_id <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(Long value) {
            addCriterion("company_id <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<Long> values) {
            addCriterion("company_id in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<Long> values) {
            addCriterion("company_id not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(Long value1, Long value2) {
            addCriterion("company_id between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(Long value1, Long value2) {
            addCriterion("company_id not between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdIsNull() {
            addCriterion("invitor_id is null");
            return (Criteria) this;
        }

        public Criteria andInvitorIdIsNotNull() {
            addCriterion("invitor_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvitorIdEqualTo(Long value) {
            addCriterion("invitor_id =", value, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdNotEqualTo(Long value) {
            addCriterion("invitor_id <>", value, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdGreaterThan(Long value) {
            addCriterion("invitor_id >", value, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("invitor_id >=", value, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdLessThan(Long value) {
            addCriterion("invitor_id <", value, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdLessThanOrEqualTo(Long value) {
            addCriterion("invitor_id <=", value, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdIn(List<Long> values) {
            addCriterion("invitor_id in", values, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdNotIn(List<Long> values) {
            addCriterion("invitor_id not in", values, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdBetween(Long value1, Long value2) {
            addCriterion("invitor_id between", value1, value2, "invitorId");
            return (Criteria) this;
        }

        public Criteria andInvitorIdNotBetween(Long value1, Long value2) {
            addCriterion("invitor_id not between", value1, value2, "invitorId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_relation
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_relation
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}