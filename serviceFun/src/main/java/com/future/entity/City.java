package com.future.entity;

public class City {
    private Integer id;

    private String name;

    private Byte province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getProvince() {
        return province;
    }

    public void setProvince(Byte province) {
        this.province = province;
    }
}