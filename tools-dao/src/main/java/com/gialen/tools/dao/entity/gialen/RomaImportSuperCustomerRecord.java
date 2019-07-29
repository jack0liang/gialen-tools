package com.gialen.tools.dao.entity.gialen;

import java.io.Serializable;
import java.util.Date;

public class RomaImportSuperCustomerRecord implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.import_customer_id
     *
     * @mbg.generated
     */
    private Long importCustomerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.customer_id
     *
     * @mbg.generated
     */
    private Long customerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.import_time
     *
     * @mbg.generated
     */
    private Date importTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.is_exist
     *
     * @mbg.generated
     */
    private Boolean isExist;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.original_store_super_mgr_id
     *
     * @mbg.generated
     */
    private Long originalStoreSuperMgrId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.original_store_id
     *
     * @mbg.generated
     */
    private Long originalStoreId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.activated_time
     *
     * @mbg.generated
     */
    private Date activatedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.expire_date
     *
     * @mbg.generated
     */
    private Date expireDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.handled_time
     *
     * @mbg.generated
     */
    private Date handledTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.is_delete
     *
     * @mbg.generated
     */
    private Boolean isDelete;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.actually_activated_time
     *
     * @mbg.generated
     */
    private Date actuallyActivatedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column roma_import_super_customer_record.expire_remain_time
     *
     * @mbg.generated
     */
    private Integer expireRemainTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.import_customer_id
     *
     * @return the value of roma_import_super_customer_record.import_customer_id
     *
     * @mbg.generated
     */
    public Long getImportCustomerId() {
        return importCustomerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.import_customer_id
     *
     * @param importCustomerId the value for roma_import_super_customer_record.import_customer_id
     *
     * @mbg.generated
     */
    public void setImportCustomerId(Long importCustomerId) {
        this.importCustomerId = importCustomerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.customer_id
     *
     * @return the value of roma_import_super_customer_record.customer_id
     *
     * @mbg.generated
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.customer_id
     *
     * @param customerId the value for roma_import_super_customer_record.customer_id
     *
     * @mbg.generated
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.import_time
     *
     * @return the value of roma_import_super_customer_record.import_time
     *
     * @mbg.generated
     */
    public Date getImportTime() {
        return importTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.import_time
     *
     * @param importTime the value for roma_import_super_customer_record.import_time
     *
     * @mbg.generated
     */
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.is_exist
     *
     * @return the value of roma_import_super_customer_record.is_exist
     *
     * @mbg.generated
     */
    public Boolean getIsExist() {
        return isExist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.is_exist
     *
     * @param isExist the value for roma_import_super_customer_record.is_exist
     *
     * @mbg.generated
     */
    public void setIsExist(Boolean isExist) {
        this.isExist = isExist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.status
     *
     * @return the value of roma_import_super_customer_record.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.status
     *
     * @param status the value for roma_import_super_customer_record.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.original_store_super_mgr_id
     *
     * @return the value of roma_import_super_customer_record.original_store_super_mgr_id
     *
     * @mbg.generated
     */
    public Long getOriginalStoreSuperMgrId() {
        return originalStoreSuperMgrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.original_store_super_mgr_id
     *
     * @param originalStoreSuperMgrId the value for roma_import_super_customer_record.original_store_super_mgr_id
     *
     * @mbg.generated
     */
    public void setOriginalStoreSuperMgrId(Long originalStoreSuperMgrId) {
        this.originalStoreSuperMgrId = originalStoreSuperMgrId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.original_store_id
     *
     * @return the value of roma_import_super_customer_record.original_store_id
     *
     * @mbg.generated
     */
    public Long getOriginalStoreId() {
        return originalStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.original_store_id
     *
     * @param originalStoreId the value for roma_import_super_customer_record.original_store_id
     *
     * @mbg.generated
     */
    public void setOriginalStoreId(Long originalStoreId) {
        this.originalStoreId = originalStoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.create_time
     *
     * @return the value of roma_import_super_customer_record.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.create_time
     *
     * @param createTime the value for roma_import_super_customer_record.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.activated_time
     *
     * @return the value of roma_import_super_customer_record.activated_time
     *
     * @mbg.generated
     */
    public Date getActivatedTime() {
        return activatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.activated_time
     *
     * @param activatedTime the value for roma_import_super_customer_record.activated_time
     *
     * @mbg.generated
     */
    public void setActivatedTime(Date activatedTime) {
        this.activatedTime = activatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.expire_date
     *
     * @return the value of roma_import_super_customer_record.expire_date
     *
     * @mbg.generated
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.expire_date
     *
     * @param expireDate the value for roma_import_super_customer_record.expire_date
     *
     * @mbg.generated
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.handled_time
     *
     * @return the value of roma_import_super_customer_record.handled_time
     *
     * @mbg.generated
     */
    public Date getHandledTime() {
        return handledTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.handled_time
     *
     * @param handledTime the value for roma_import_super_customer_record.handled_time
     *
     * @mbg.generated
     */
    public void setHandledTime(Date handledTime) {
        this.handledTime = handledTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.is_delete
     *
     * @return the value of roma_import_super_customer_record.is_delete
     *
     * @mbg.generated
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.is_delete
     *
     * @param isDelete the value for roma_import_super_customer_record.is_delete
     *
     * @mbg.generated
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.actually_activated_time
     *
     * @return the value of roma_import_super_customer_record.actually_activated_time
     *
     * @mbg.generated
     */
    public Date getActuallyActivatedTime() {
        return actuallyActivatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.actually_activated_time
     *
     * @param actuallyActivatedTime the value for roma_import_super_customer_record.actually_activated_time
     *
     * @mbg.generated
     */
    public void setActuallyActivatedTime(Date actuallyActivatedTime) {
        this.actuallyActivatedTime = actuallyActivatedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column roma_import_super_customer_record.expire_remain_time
     *
     * @return the value of roma_import_super_customer_record.expire_remain_time
     *
     * @mbg.generated
     */
    public Integer getExpireRemainTime() {
        return expireRemainTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column roma_import_super_customer_record.expire_remain_time
     *
     * @param expireRemainTime the value for roma_import_super_customer_record.expire_remain_time
     *
     * @mbg.generated
     */
    public void setExpireRemainTime(Integer expireRemainTime) {
        this.expireRemainTime = expireRemainTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RomaImportSuperCustomerRecord other = (RomaImportSuperCustomerRecord) that;
        return (this.getImportCustomerId() == null ? other.getImportCustomerId() == null : this.getImportCustomerId().equals(other.getImportCustomerId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getImportTime() == null ? other.getImportTime() == null : this.getImportTime().equals(other.getImportTime()))
            && (this.getIsExist() == null ? other.getIsExist() == null : this.getIsExist().equals(other.getIsExist()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOriginalStoreSuperMgrId() == null ? other.getOriginalStoreSuperMgrId() == null : this.getOriginalStoreSuperMgrId().equals(other.getOriginalStoreSuperMgrId()))
            && (this.getOriginalStoreId() == null ? other.getOriginalStoreId() == null : this.getOriginalStoreId().equals(other.getOriginalStoreId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getActivatedTime() == null ? other.getActivatedTime() == null : this.getActivatedTime().equals(other.getActivatedTime()))
            && (this.getExpireDate() == null ? other.getExpireDate() == null : this.getExpireDate().equals(other.getExpireDate()))
            && (this.getHandledTime() == null ? other.getHandledTime() == null : this.getHandledTime().equals(other.getHandledTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getActuallyActivatedTime() == null ? other.getActuallyActivatedTime() == null : this.getActuallyActivatedTime().equals(other.getActuallyActivatedTime()))
            && (this.getExpireRemainTime() == null ? other.getExpireRemainTime() == null : this.getExpireRemainTime().equals(other.getExpireRemainTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getImportCustomerId() == null) ? 0 : getImportCustomerId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getImportTime() == null) ? 0 : getImportTime().hashCode());
        result = prime * result + ((getIsExist() == null) ? 0 : getIsExist().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOriginalStoreSuperMgrId() == null) ? 0 : getOriginalStoreSuperMgrId().hashCode());
        result = prime * result + ((getOriginalStoreId() == null) ? 0 : getOriginalStoreId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getActivatedTime() == null) ? 0 : getActivatedTime().hashCode());
        result = prime * result + ((getExpireDate() == null) ? 0 : getExpireDate().hashCode());
        result = prime * result + ((getHandledTime() == null) ? 0 : getHandledTime().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getActuallyActivatedTime() == null) ? 0 : getActuallyActivatedTime().hashCode());
        result = prime * result + ((getExpireRemainTime() == null) ? 0 : getExpireRemainTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table roma_import_super_customer_record
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", importCustomerId=").append(importCustomerId);
        sb.append(", customerId=").append(customerId);
        sb.append(", importTime=").append(importTime);
        sb.append(", isExist=").append(isExist);
        sb.append(", status=").append(status);
        sb.append(", originalStoreSuperMgrId=").append(originalStoreSuperMgrId);
        sb.append(", originalStoreId=").append(originalStoreId);
        sb.append(", createTime=").append(createTime);
        sb.append(", activatedTime=").append(activatedTime);
        sb.append(", expireDate=").append(expireDate);
        sb.append(", handledTime=").append(handledTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", actuallyActivatedTime=").append(actuallyActivatedTime);
        sb.append(", expireRemainTime=").append(expireRemainTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}