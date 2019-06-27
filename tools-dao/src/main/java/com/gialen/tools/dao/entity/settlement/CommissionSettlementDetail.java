package com.gialen.tools.dao.entity.settlement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CommissionSettlementDetail implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.order_sn
     *
     * @mbg.generated
     */
    private String orderSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.order_detail_sn
     *
     * @mbg.generated
     */
    private String orderDetailSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.status
     *
     * @mbg.generated
     */
    private Short status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.sku_id
     *
     * @mbg.generated
     */
    private String skuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.item_id
     *
     * @mbg.generated
     */
    private Long itemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.brand_id
     *
     * @mbg.generated
     */
    private Long brandId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.item_name
     *
     * @mbg.generated
     */
    private String itemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.buy_num
     *
     * @mbg.generated
     */
    private Integer buyNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.item_price
     *
     * @mbg.generated
     */
    private BigDecimal itemPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.settlement_rule
     *
     * @mbg.generated
     */
    private String settlementRule;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.store_master_commission
     *
     * @mbg.generated
     */
    private BigDecimal storeMasterCommission;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.store_master_experience
     *
     * @mbg.generated
     */
    private Integer storeMasterExperience;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.store_manage_commission
     *
     * @mbg.generated
     */
    private BigDecimal storeManageCommission;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.store_manage_experience
     *
     * @mbg.generated
     */
    private Integer storeManageExperience;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.store_commission
     *
     * @mbg.generated
     */
    private BigDecimal storeCommission;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.company_commission
     *
     * @mbg.generated
     */
    private BigDecimal companyCommission;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column commission_settlement_detail.data_version
     *
     * @mbg.generated
     */
    private Integer dataVersion;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table commission_settlement_detail
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.id
     *
     * @return the value of commission_settlement_detail.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.id
     *
     * @param id the value for commission_settlement_detail.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.create_time
     *
     * @return the value of commission_settlement_detail.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.create_time
     *
     * @param createTime the value for commission_settlement_detail.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.update_time
     *
     * @return the value of commission_settlement_detail.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.update_time
     *
     * @param updateTime the value for commission_settlement_detail.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.order_sn
     *
     * @return the value of commission_settlement_detail.order_sn
     *
     * @mbg.generated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.order_sn
     *
     * @param orderSn the value for commission_settlement_detail.order_sn
     *
     * @mbg.generated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.order_detail_sn
     *
     * @return the value of commission_settlement_detail.order_detail_sn
     *
     * @mbg.generated
     */
    public String getOrderDetailSn() {
        return orderDetailSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.order_detail_sn
     *
     * @param orderDetailSn the value for commission_settlement_detail.order_detail_sn
     *
     * @mbg.generated
     */
    public void setOrderDetailSn(String orderDetailSn) {
        this.orderDetailSn = orderDetailSn == null ? null : orderDetailSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.status
     *
     * @return the value of commission_settlement_detail.status
     *
     * @mbg.generated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.status
     *
     * @param status the value for commission_settlement_detail.status
     *
     * @mbg.generated
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.sku_id
     *
     * @return the value of commission_settlement_detail.sku_id
     *
     * @mbg.generated
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.sku_id
     *
     * @param skuId the value for commission_settlement_detail.sku_id
     *
     * @mbg.generated
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.item_id
     *
     * @return the value of commission_settlement_detail.item_id
     *
     * @mbg.generated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.item_id
     *
     * @param itemId the value for commission_settlement_detail.item_id
     *
     * @mbg.generated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.brand_id
     *
     * @return the value of commission_settlement_detail.brand_id
     *
     * @mbg.generated
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.brand_id
     *
     * @param brandId the value for commission_settlement_detail.brand_id
     *
     * @mbg.generated
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.item_name
     *
     * @return the value of commission_settlement_detail.item_name
     *
     * @mbg.generated
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.item_name
     *
     * @param itemName the value for commission_settlement_detail.item_name
     *
     * @mbg.generated
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.buy_num
     *
     * @return the value of commission_settlement_detail.buy_num
     *
     * @mbg.generated
     */
    public Integer getBuyNum() {
        return buyNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.buy_num
     *
     * @param buyNum the value for commission_settlement_detail.buy_num
     *
     * @mbg.generated
     */
    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.item_price
     *
     * @return the value of commission_settlement_detail.item_price
     *
     * @mbg.generated
     */
    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.item_price
     *
     * @param itemPrice the value for commission_settlement_detail.item_price
     *
     * @mbg.generated
     */
    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.settlement_rule
     *
     * @return the value of commission_settlement_detail.settlement_rule
     *
     * @mbg.generated
     */
    public String getSettlementRule() {
        return settlementRule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.settlement_rule
     *
     * @param settlementRule the value for commission_settlement_detail.settlement_rule
     *
     * @mbg.generated
     */
    public void setSettlementRule(String settlementRule) {
        this.settlementRule = settlementRule == null ? null : settlementRule.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.store_master_commission
     *
     * @return the value of commission_settlement_detail.store_master_commission
     *
     * @mbg.generated
     */
    public BigDecimal getStoreMasterCommission() {
        return storeMasterCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.store_master_commission
     *
     * @param storeMasterCommission the value for commission_settlement_detail.store_master_commission
     *
     * @mbg.generated
     */
    public void setStoreMasterCommission(BigDecimal storeMasterCommission) {
        this.storeMasterCommission = storeMasterCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.store_master_experience
     *
     * @return the value of commission_settlement_detail.store_master_experience
     *
     * @mbg.generated
     */
    public Integer getStoreMasterExperience() {
        return storeMasterExperience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.store_master_experience
     *
     * @param storeMasterExperience the value for commission_settlement_detail.store_master_experience
     *
     * @mbg.generated
     */
    public void setStoreMasterExperience(Integer storeMasterExperience) {
        this.storeMasterExperience = storeMasterExperience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.store_manage_commission
     *
     * @return the value of commission_settlement_detail.store_manage_commission
     *
     * @mbg.generated
     */
    public BigDecimal getStoreManageCommission() {
        return storeManageCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.store_manage_commission
     *
     * @param storeManageCommission the value for commission_settlement_detail.store_manage_commission
     *
     * @mbg.generated
     */
    public void setStoreManageCommission(BigDecimal storeManageCommission) {
        this.storeManageCommission = storeManageCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.store_manage_experience
     *
     * @return the value of commission_settlement_detail.store_manage_experience
     *
     * @mbg.generated
     */
    public Integer getStoreManageExperience() {
        return storeManageExperience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.store_manage_experience
     *
     * @param storeManageExperience the value for commission_settlement_detail.store_manage_experience
     *
     * @mbg.generated
     */
    public void setStoreManageExperience(Integer storeManageExperience) {
        this.storeManageExperience = storeManageExperience;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.store_commission
     *
     * @return the value of commission_settlement_detail.store_commission
     *
     * @mbg.generated
     */
    public BigDecimal getStoreCommission() {
        return storeCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.store_commission
     *
     * @param storeCommission the value for commission_settlement_detail.store_commission
     *
     * @mbg.generated
     */
    public void setStoreCommission(BigDecimal storeCommission) {
        this.storeCommission = storeCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.company_commission
     *
     * @return the value of commission_settlement_detail.company_commission
     *
     * @mbg.generated
     */
    public BigDecimal getCompanyCommission() {
        return companyCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.company_commission
     *
     * @param companyCommission the value for commission_settlement_detail.company_commission
     *
     * @mbg.generated
     */
    public void setCompanyCommission(BigDecimal companyCommission) {
        this.companyCommission = companyCommission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column commission_settlement_detail.data_version
     *
     * @return the value of commission_settlement_detail.data_version
     *
     * @mbg.generated
     */
    public Integer getDataVersion() {
        return dataVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column commission_settlement_detail.data_version
     *
     * @param dataVersion the value for commission_settlement_detail.data_version
     *
     * @mbg.generated
     */
    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement_detail
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
        CommissionSettlementDetail other = (CommissionSettlementDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
            && (this.getOrderDetailSn() == null ? other.getOrderDetailSn() == null : this.getOrderDetailSn().equals(other.getOrderDetailSn()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getSkuId() == null ? other.getSkuId() == null : this.getSkuId().equals(other.getSkuId()))
            && (this.getItemId() == null ? other.getItemId() == null : this.getItemId().equals(other.getItemId()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getItemName() == null ? other.getItemName() == null : this.getItemName().equals(other.getItemName()))
            && (this.getBuyNum() == null ? other.getBuyNum() == null : this.getBuyNum().equals(other.getBuyNum()))
            && (this.getItemPrice() == null ? other.getItemPrice() == null : this.getItemPrice().equals(other.getItemPrice()))
            && (this.getSettlementRule() == null ? other.getSettlementRule() == null : this.getSettlementRule().equals(other.getSettlementRule()))
            && (this.getStoreMasterCommission() == null ? other.getStoreMasterCommission() == null : this.getStoreMasterCommission().equals(other.getStoreMasterCommission()))
            && (this.getStoreMasterExperience() == null ? other.getStoreMasterExperience() == null : this.getStoreMasterExperience().equals(other.getStoreMasterExperience()))
            && (this.getStoreManageCommission() == null ? other.getStoreManageCommission() == null : this.getStoreManageCommission().equals(other.getStoreManageCommission()))
            && (this.getStoreManageExperience() == null ? other.getStoreManageExperience() == null : this.getStoreManageExperience().equals(other.getStoreManageExperience()))
            && (this.getStoreCommission() == null ? other.getStoreCommission() == null : this.getStoreCommission().equals(other.getStoreCommission()))
            && (this.getCompanyCommission() == null ? other.getCompanyCommission() == null : this.getCompanyCommission().equals(other.getCompanyCommission()))
            && (this.getDataVersion() == null ? other.getDataVersion() == null : this.getDataVersion().equals(other.getDataVersion()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement_detail
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getOrderDetailSn() == null) ? 0 : getOrderDetailSn().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getSkuId() == null) ? 0 : getSkuId().hashCode());
        result = prime * result + ((getItemId() == null) ? 0 : getItemId().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getItemName() == null) ? 0 : getItemName().hashCode());
        result = prime * result + ((getBuyNum() == null) ? 0 : getBuyNum().hashCode());
        result = prime * result + ((getItemPrice() == null) ? 0 : getItemPrice().hashCode());
        result = prime * result + ((getSettlementRule() == null) ? 0 : getSettlementRule().hashCode());
        result = prime * result + ((getStoreMasterCommission() == null) ? 0 : getStoreMasterCommission().hashCode());
        result = prime * result + ((getStoreMasterExperience() == null) ? 0 : getStoreMasterExperience().hashCode());
        result = prime * result + ((getStoreManageCommission() == null) ? 0 : getStoreManageCommission().hashCode());
        result = prime * result + ((getStoreManageExperience() == null) ? 0 : getStoreManageExperience().hashCode());
        result = prime * result + ((getStoreCommission() == null) ? 0 : getStoreCommission().hashCode());
        result = prime * result + ((getCompanyCommission() == null) ? 0 : getCompanyCommission().hashCode());
        result = prime * result + ((getDataVersion() == null) ? 0 : getDataVersion().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table commission_settlement_detail
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", orderDetailSn=").append(orderDetailSn);
        sb.append(", status=").append(status);
        sb.append(", skuId=").append(skuId);
        sb.append(", itemId=").append(itemId);
        sb.append(", brandId=").append(brandId);
        sb.append(", itemName=").append(itemName);
        sb.append(", buyNum=").append(buyNum);
        sb.append(", itemPrice=").append(itemPrice);
        sb.append(", settlementRule=").append(settlementRule);
        sb.append(", storeMasterCommission=").append(storeMasterCommission);
        sb.append(", storeMasterExperience=").append(storeMasterExperience);
        sb.append(", storeManageCommission=").append(storeManageCommission);
        sb.append(", storeManageExperience=").append(storeManageExperience);
        sb.append(", storeCommission=").append(storeCommission);
        sb.append(", companyCommission=").append(companyCommission);
        sb.append(", dataVersion=").append(dataVersion);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}