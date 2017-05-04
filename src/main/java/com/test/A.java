package com.test;

import java.util.Date;

public class A {
    private Integer id;
    private String name;
    private String address;
    private String school;
    private Date time;
    private String type;
    private String typeCode;
    private String type2;
    private String type2Code;

    public A() {
    }

    public A(Integer id, String name, String address, String school, Date time) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.school = school;
        this.time = time;
    }

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
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getType2Code() {
        return type2Code;
    }

    public void setType2Code(String type2Code) {
        this.type2Code = type2Code;
    }
}
