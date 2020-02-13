package com.gialen.tools.dao.entity.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BigSuperMgrSalesExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    protected int offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    protected int limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public BigSuperMgrSalesExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
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
     * This method corresponds to the database table big_super_mgr_sales
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
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
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
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public int getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table big_super_mgr_sales
     *
     * @mbg.generated
     */
    public int getLimit() {
        return limit;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table big_super_mgr_sales
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

        public Criteria andSuperMgrIdIsNull() {
            addCriterion("super_mgr_id is null");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdIsNotNull() {
            addCriterion("super_mgr_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdEqualTo(Long value) {
            addCriterion("super_mgr_id =", value, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdNotEqualTo(Long value) {
            addCriterion("super_mgr_id <>", value, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdGreaterThan(Long value) {
            addCriterion("super_mgr_id >", value, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("super_mgr_id >=", value, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdLessThan(Long value) {
            addCriterion("super_mgr_id <", value, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdLessThanOrEqualTo(Long value) {
            addCriterion("super_mgr_id <=", value, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdIn(List<Long> values) {
            addCriterion("super_mgr_id in", values, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdNotIn(List<Long> values) {
            addCriterion("super_mgr_id not in", values, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdBetween(Long value1, Long value2) {
            addCriterion("super_mgr_id between", value1, value2, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andSuperMgrIdNotBetween(Long value1, Long value2) {
            addCriterion("super_mgr_id not between", value1, value2, "superMgrId");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumIsNull() {
            addCriterion("store_manger_num is null");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumIsNotNull() {
            addCriterion("store_manger_num is not null");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumEqualTo(Integer value) {
            addCriterion("store_manger_num =", value, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumNotEqualTo(Integer value) {
            addCriterion("store_manger_num <>", value, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumGreaterThan(Integer value) {
            addCriterion("store_manger_num >", value, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("store_manger_num >=", value, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumLessThan(Integer value) {
            addCriterion("store_manger_num <", value, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumLessThanOrEqualTo(Integer value) {
            addCriterion("store_manger_num <=", value, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumIn(List<Integer> values) {
            addCriterion("store_manger_num in", values, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumNotIn(List<Integer> values) {
            addCriterion("store_manger_num not in", values, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumBetween(Integer value1, Integer value2) {
            addCriterion("store_manger_num between", value1, value2, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andStoreMangerNumNotBetween(Integer value1, Integer value2) {
            addCriterion("store_manger_num not between", value1, value2, "storeMangerNum");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesIsNull() {
            addCriterion("big_gift_bag_sales is null");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesIsNotNull() {
            addCriterion("big_gift_bag_sales is not null");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesEqualTo(BigDecimal value) {
            addCriterion("big_gift_bag_sales =", value, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesNotEqualTo(BigDecimal value) {
            addCriterion("big_gift_bag_sales <>", value, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesGreaterThan(BigDecimal value) {
            addCriterion("big_gift_bag_sales >", value, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("big_gift_bag_sales >=", value, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesLessThan(BigDecimal value) {
            addCriterion("big_gift_bag_sales <", value, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("big_gift_bag_sales <=", value, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesIn(List<BigDecimal> values) {
            addCriterion("big_gift_bag_sales in", values, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesNotIn(List<BigDecimal> values) {
            addCriterion("big_gift_bag_sales not in", values, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("big_gift_bag_sales between", value1, value2, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andBigGiftBagSalesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("big_gift_bag_sales not between", value1, value2, "bigGiftBagSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesIsNull() {
            addCriterion("total_sales is null");
            return (Criteria) this;
        }

        public Criteria andTotalSalesIsNotNull() {
            addCriterion("total_sales is not null");
            return (Criteria) this;
        }

        public Criteria andTotalSalesEqualTo(BigDecimal value) {
            addCriterion("total_sales =", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesNotEqualTo(BigDecimal value) {
            addCriterion("total_sales <>", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesGreaterThan(BigDecimal value) {
            addCriterion("total_sales >", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_sales >=", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesLessThan(BigDecimal value) {
            addCriterion("total_sales <", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_sales <=", value, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesIn(List<BigDecimal> values) {
            addCriterion("total_sales in", values, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesNotIn(List<BigDecimal> values) {
            addCriterion("total_sales not in", values, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_sales between", value1, value2, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalSalesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_sales not between", value1, value2, "totalSales");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIsNull() {
            addCriterion("total_commission is null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIsNotNull() {
            addCriterion("total_commission is not null");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionEqualTo(BigDecimal value) {
            addCriterion("total_commission =", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotEqualTo(BigDecimal value) {
            addCriterion("total_commission <>", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionGreaterThan(BigDecimal value) {
            addCriterion("total_commission >", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_commission >=", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLessThan(BigDecimal value) {
            addCriterion("total_commission <", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_commission <=", value, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionIn(List<BigDecimal> values) {
            addCriterion("total_commission in", values, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotIn(List<BigDecimal> values) {
            addCriterion("total_commission not in", values, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_commission between", value1, value2, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andTotalCommissionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_commission not between", value1, value2, "totalCommission");
            return (Criteria) this;
        }

        public Criteria andMonthIsNull() {
            addCriterion("month is null");
            return (Criteria) this;
        }

        public Criteria andMonthIsNotNull() {
            addCriterion("month is not null");
            return (Criteria) this;
        }

        public Criteria andMonthEqualTo(Integer value) {
            addCriterion("month =", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotEqualTo(Integer value) {
            addCriterion("month <>", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThan(Integer value) {
            addCriterion("month >", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("month >=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThan(Integer value) {
            addCriterion("month <", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthLessThanOrEqualTo(Integer value) {
            addCriterion("month <=", value, "month");
            return (Criteria) this;
        }

        public Criteria andMonthIn(List<Integer> values) {
            addCriterion("month in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotIn(List<Integer> values) {
            addCriterion("month not in", values, "month");
            return (Criteria) this;
        }

        public Criteria andMonthBetween(Integer value1, Integer value2) {
            addCriterion("month between", value1, value2, "month");
            return (Criteria) this;
        }

        public Criteria andMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("month not between", value1, value2, "month");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table big_super_mgr_sales
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
     * This class corresponds to the database table big_super_mgr_sales
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