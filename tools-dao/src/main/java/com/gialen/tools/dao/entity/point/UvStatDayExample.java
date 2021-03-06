package com.gialen.tools.dao.entity.point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UvStatDayExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    protected int offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    protected int limit;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public UvStatDayExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
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
     * This method corresponds to the database table uv_stat_day
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
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
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
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public int getOffset() {
        return offset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table uv_stat_day
     *
     * @mbg.generated
     */
    public int getLimit() {
        return limit;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table uv_stat_day
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

        public Criteria andStatDateIsNull() {
            addCriterion("stat_date is null");
            return (Criteria) this;
        }

        public Criteria andStatDateIsNotNull() {
            addCriterion("stat_date is not null");
            return (Criteria) this;
        }

        public Criteria andStatDateEqualTo(String value) {
            addCriterion("stat_date =", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotEqualTo(String value) {
            addCriterion("stat_date <>", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThan(String value) {
            addCriterion("stat_date >", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateGreaterThanOrEqualTo(String value) {
            addCriterion("stat_date >=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThan(String value) {
            addCriterion("stat_date <", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLessThanOrEqualTo(String value) {
            addCriterion("stat_date <=", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateLike(String value) {
            addCriterion("stat_date like", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotLike(String value) {
            addCriterion("stat_date not like", value, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateIn(List<String> values) {
            addCriterion("stat_date in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotIn(List<String> values) {
            addCriterion("stat_date not in", values, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateBetween(String value1, String value2) {
            addCriterion("stat_date between", value1, value2, "statDate");
            return (Criteria) this;
        }

        public Criteria andStatDateNotBetween(String value1, String value2) {
            addCriterion("stat_date not between", value1, value2, "statDate");
            return (Criteria) this;
        }

        public Criteria andUvTotalIsNull() {
            addCriterion("uv_total is null");
            return (Criteria) this;
        }

        public Criteria andUvTotalIsNotNull() {
            addCriterion("uv_total is not null");
            return (Criteria) this;
        }

        public Criteria andUvTotalEqualTo(Integer value) {
            addCriterion("uv_total =", value, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalNotEqualTo(Integer value) {
            addCriterion("uv_total <>", value, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalGreaterThan(Integer value) {
            addCriterion("uv_total >", value, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_total >=", value, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalLessThan(Integer value) {
            addCriterion("uv_total <", value, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalLessThanOrEqualTo(Integer value) {
            addCriterion("uv_total <=", value, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalIn(List<Integer> values) {
            addCriterion("uv_total in", values, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalNotIn(List<Integer> values) {
            addCriterion("uv_total not in", values, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalBetween(Integer value1, Integer value2) {
            addCriterion("uv_total between", value1, value2, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("uv_total not between", value1, value2, "uvTotal");
            return (Criteria) this;
        }

        public Criteria andUvAppIsNull() {
            addCriterion("uv_app is null");
            return (Criteria) this;
        }

        public Criteria andUvAppIsNotNull() {
            addCriterion("uv_app is not null");
            return (Criteria) this;
        }

        public Criteria andUvAppEqualTo(Integer value) {
            addCriterion("uv_app =", value, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppNotEqualTo(Integer value) {
            addCriterion("uv_app <>", value, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppGreaterThan(Integer value) {
            addCriterion("uv_app >", value, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_app >=", value, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppLessThan(Integer value) {
            addCriterion("uv_app <", value, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppLessThanOrEqualTo(Integer value) {
            addCriterion("uv_app <=", value, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppIn(List<Integer> values) {
            addCriterion("uv_app in", values, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppNotIn(List<Integer> values) {
            addCriterion("uv_app not in", values, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppBetween(Integer value1, Integer value2) {
            addCriterion("uv_app between", value1, value2, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvAppNotBetween(Integer value1, Integer value2) {
            addCriterion("uv_app not between", value1, value2, "uvApp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappIsNull() {
            addCriterion("uv_miniapp is null");
            return (Criteria) this;
        }

        public Criteria andUvMiniappIsNotNull() {
            addCriterion("uv_miniapp is not null");
            return (Criteria) this;
        }

        public Criteria andUvMiniappEqualTo(Integer value) {
            addCriterion("uv_miniapp =", value, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappNotEqualTo(Integer value) {
            addCriterion("uv_miniapp <>", value, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappGreaterThan(Integer value) {
            addCriterion("uv_miniapp >", value, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_miniapp >=", value, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappLessThan(Integer value) {
            addCriterion("uv_miniapp <", value, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappLessThanOrEqualTo(Integer value) {
            addCriterion("uv_miniapp <=", value, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappIn(List<Integer> values) {
            addCriterion("uv_miniapp in", values, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappNotIn(List<Integer> values) {
            addCriterion("uv_miniapp not in", values, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappBetween(Integer value1, Integer value2) {
            addCriterion("uv_miniapp between", value1, value2, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvMiniappNotBetween(Integer value1, Integer value2) {
            addCriterion("uv_miniapp not between", value1, value2, "uvMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvH5IsNull() {
            addCriterion("uv_h5 is null");
            return (Criteria) this;
        }

        public Criteria andUvH5IsNotNull() {
            addCriterion("uv_h5 is not null");
            return (Criteria) this;
        }

        public Criteria andUvH5EqualTo(Integer value) {
            addCriterion("uv_h5 =", value, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5NotEqualTo(Integer value) {
            addCriterion("uv_h5 <>", value, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5GreaterThan(Integer value) {
            addCriterion("uv_h5 >", value, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5GreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_h5 >=", value, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5LessThan(Integer value) {
            addCriterion("uv_h5 <", value, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5LessThanOrEqualTo(Integer value) {
            addCriterion("uv_h5 <=", value, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5In(List<Integer> values) {
            addCriterion("uv_h5 in", values, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5NotIn(List<Integer> values) {
            addCriterion("uv_h5 not in", values, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5Between(Integer value1, Integer value2) {
            addCriterion("uv_h5 between", value1, value2, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvH5NotBetween(Integer value1, Integer value2) {
            addCriterion("uv_h5 not between", value1, value2, "uvH5");
            return (Criteria) this;
        }

        public Criteria andUvItemIsNull() {
            addCriterion("uv_item is null");
            return (Criteria) this;
        }

        public Criteria andUvItemIsNotNull() {
            addCriterion("uv_item is not null");
            return (Criteria) this;
        }

        public Criteria andUvItemEqualTo(Integer value) {
            addCriterion("uv_item =", value, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemNotEqualTo(Integer value) {
            addCriterion("uv_item <>", value, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemGreaterThan(Integer value) {
            addCriterion("uv_item >", value, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_item >=", value, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemLessThan(Integer value) {
            addCriterion("uv_item <", value, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemLessThanOrEqualTo(Integer value) {
            addCriterion("uv_item <=", value, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemIn(List<Integer> values) {
            addCriterion("uv_item in", values, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemNotIn(List<Integer> values) {
            addCriterion("uv_item not in", values, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemBetween(Integer value1, Integer value2) {
            addCriterion("uv_item between", value1, value2, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemNotBetween(Integer value1, Integer value2) {
            addCriterion("uv_item not between", value1, value2, "uvItem");
            return (Criteria) this;
        }

        public Criteria andUvItemAppIsNull() {
            addCriterion("uv_item_app is null");
            return (Criteria) this;
        }

        public Criteria andUvItemAppIsNotNull() {
            addCriterion("uv_item_app is not null");
            return (Criteria) this;
        }

        public Criteria andUvItemAppEqualTo(Integer value) {
            addCriterion("uv_item_app =", value, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppNotEqualTo(Integer value) {
            addCriterion("uv_item_app <>", value, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppGreaterThan(Integer value) {
            addCriterion("uv_item_app >", value, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_item_app >=", value, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppLessThan(Integer value) {
            addCriterion("uv_item_app <", value, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppLessThanOrEqualTo(Integer value) {
            addCriterion("uv_item_app <=", value, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppIn(List<Integer> values) {
            addCriterion("uv_item_app in", values, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppNotIn(List<Integer> values) {
            addCriterion("uv_item_app not in", values, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppBetween(Integer value1, Integer value2) {
            addCriterion("uv_item_app between", value1, value2, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemAppNotBetween(Integer value1, Integer value2) {
            addCriterion("uv_item_app not between", value1, value2, "uvItemApp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappIsNull() {
            addCriterion("uv_item_miniapp is null");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappIsNotNull() {
            addCriterion("uv_item_miniapp is not null");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappEqualTo(Integer value) {
            addCriterion("uv_item_miniapp =", value, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappNotEqualTo(Integer value) {
            addCriterion("uv_item_miniapp <>", value, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappGreaterThan(Integer value) {
            addCriterion("uv_item_miniapp >", value, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappGreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_item_miniapp >=", value, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappLessThan(Integer value) {
            addCriterion("uv_item_miniapp <", value, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappLessThanOrEqualTo(Integer value) {
            addCriterion("uv_item_miniapp <=", value, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappIn(List<Integer> values) {
            addCriterion("uv_item_miniapp in", values, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappNotIn(List<Integer> values) {
            addCriterion("uv_item_miniapp not in", values, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappBetween(Integer value1, Integer value2) {
            addCriterion("uv_item_miniapp between", value1, value2, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemMiniappNotBetween(Integer value1, Integer value2) {
            addCriterion("uv_item_miniapp not between", value1, value2, "uvItemMiniapp");
            return (Criteria) this;
        }

        public Criteria andUvItemH5IsNull() {
            addCriterion("uv_item_h5 is null");
            return (Criteria) this;
        }

        public Criteria andUvItemH5IsNotNull() {
            addCriterion("uv_item_h5 is not null");
            return (Criteria) this;
        }

        public Criteria andUvItemH5EqualTo(Integer value) {
            addCriterion("uv_item_h5 =", value, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5NotEqualTo(Integer value) {
            addCriterion("uv_item_h5 <>", value, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5GreaterThan(Integer value) {
            addCriterion("uv_item_h5 >", value, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5GreaterThanOrEqualTo(Integer value) {
            addCriterion("uv_item_h5 >=", value, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5LessThan(Integer value) {
            addCriterion("uv_item_h5 <", value, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5LessThanOrEqualTo(Integer value) {
            addCriterion("uv_item_h5 <=", value, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5In(List<Integer> values) {
            addCriterion("uv_item_h5 in", values, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5NotIn(List<Integer> values) {
            addCriterion("uv_item_h5 not in", values, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5Between(Integer value1, Integer value2) {
            addCriterion("uv_item_h5 between", value1, value2, "uvItemH5");
            return (Criteria) this;
        }

        public Criteria andUvItemH5NotBetween(Integer value1, Integer value2) {
            addCriterion("uv_item_h5 not between", value1, value2, "uvItemH5");
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
     * This class corresponds to the database table uv_stat_day
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
     * This class corresponds to the database table uv_stat_day
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