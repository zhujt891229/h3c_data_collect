package com.shinwa.datacollect.entity;

public class CollectData {
    private String taskOrder;
    private String originalCountry;
    private String manufacturer;
    private String h3cSn;
    private String originalFactorySn;
    private String customerSn;
    private String creator;
    private String createTime;

    public String getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(String taskOrder) {
        this.taskOrder = taskOrder;
    }

    public String getOriginalCountry() {
        return originalCountry;
    }

    public void setOriginalCountry(String originalCountry) {
        this.originalCountry = originalCountry;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getH3cSn() {
        return h3cSn;
    }

    public void setH3cSn(String h3cSn) {
        this.h3cSn = h3cSn;
    }

    public String getOriginalFactorySn() {
        return originalFactorySn;
    }

    public void setOriginalFactorySn(String originalFactorySn) {
        this.originalFactorySn = originalFactorySn;
    }

    public String getCustomerSn() {
        return customerSn;
    }

    public void setCustomerSn(String customerSn) {
        this.customerSn = customerSn;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
