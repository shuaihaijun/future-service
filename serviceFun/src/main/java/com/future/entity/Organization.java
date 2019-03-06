package com.future.entity;

public class Organization {
    private Integer id;

    private String orgName;

    private Byte orgType;

    private String orgPhone;

    private String orgDesc;

    private String address;

    private String orgWeb;

    private Integer orgCity;

    private String orgImage;

    private Byte orgGrade;

    private Integer orgSuperior;

    private Integer orgCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Byte getOrgType() {
        return orgType;
    }

    public void setOrgType(Byte orgType) {
        this.orgType = orgType;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone == null ? null : orgPhone.trim();
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc == null ? null : orgDesc.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOrgWeb() {
        return orgWeb;
    }

    public void setOrgWeb(String orgWeb) {
        this.orgWeb = orgWeb == null ? null : orgWeb.trim();
    }

    public Integer getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(Integer orgCity) {
        this.orgCity = orgCity;
    }

    public String getOrgImage() {
        return orgImage;
    }

    public void setOrgImage(String orgImage) {
        this.orgImage = orgImage == null ? null : orgImage.trim();
    }

    public Byte getOrgGrade() {
        return orgGrade;
    }

    public void setOrgGrade(Byte orgGrade) {
        this.orgGrade = orgGrade;
    }

    public Integer getOrgSuperior() {
        return orgSuperior;
    }

    public void setOrgSuperior(Integer orgSuperior) {
        this.orgSuperior = orgSuperior;
    }

    public Integer getOrgCount() {
        return orgCount;
    }

    public void setOrgCount(Integer orgCount) {
        this.orgCount = orgCount;
    }
}