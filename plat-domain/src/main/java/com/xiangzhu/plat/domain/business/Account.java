package com.xiangzhu.plat.domain.business;

import com.xiangzhu.plat.domain.BaseModel;

/**
 * Created by liluoqi on 2017/7/15.
 * 账户信息
 */
public class Account extends BaseModel {
    private static final long serialVersionUID = 8247714666080613254L;

    /**
     * 用户id
     */
    private long userId;
    /**
     * 优惠券
     */
    private double coupon;
    /**
     * 奖励金
     */
    private double bonus;
    /**
     * 余额
     */
    private double balance;

    public Account(long userId, double coupon, double bonus, double balance) {
        super();
        this.userId = userId;
        this.coupon = coupon;
        this.bonus = bonus;
        this.balance = balance;
    }

    public Account() {
        super();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getCoupon() {
        return coupon;
    }

    public void setCoupon(double coupon) {
        this.coupon = coupon;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
