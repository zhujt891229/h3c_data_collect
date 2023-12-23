package com.shinwa.datacollect.entity;

public class UserInfo {
    private String name;//展示的姓名
    private String username;//登录的名称
    private String dept;//所属部门

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
