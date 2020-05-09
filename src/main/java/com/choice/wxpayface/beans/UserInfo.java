package com.choice.wxpayface.beans;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String name;
    private String idcard;
    private String mobile;
    private String openId;

    public UserInfo() {
    }

    public UserInfo(String name, String idcard, String mobile, String ehrcCard, String openId) {
        this.name = name;
        this.idcard = idcard;
        this.mobile = mobile;
        this.openId = openId;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", mobile='" + mobile + '\'' +
                ", openId='" + openId + '\'' +
                '}';
    }
}
