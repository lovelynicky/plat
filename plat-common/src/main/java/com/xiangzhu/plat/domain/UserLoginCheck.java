package com.xiangzhu.plat.domain;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Created by liluoqi on 2017/7/11.
 * 用户登录校验
 */
public class UserLoginCheck {
    private boolean valid;
    private String message;

    private UserLoginCheck() {
    }

    private UserLoginCheck(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static UserLoginCheck valid() {
        return new UserLoginCheck(true, EMPTY);
    }

    public static UserLoginCheck notValid(String message) {
        return new UserLoginCheck(false, message);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
