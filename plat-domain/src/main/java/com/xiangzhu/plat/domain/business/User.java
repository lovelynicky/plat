package com.xiangzhu.plat.domain.business;

import com.xiangzhu.plat.domain.BaseModel;

/**
 * Created by liluoqi on 2017/7/9.
 * 用户信息表
 */
public class User extends BaseModel{
    private static final long serialVersionUID = 7247714666080613254L;
    private String username;
    private String password;
    private String wechatName;
    private String openId;
    private String name;
    private String identityNo;
    private String mobileNo;

    public User() {
        super();
    }

    public User(String username,String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
