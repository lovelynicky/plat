package com.xiangzhu.plat.domain.enums;

/**
 * Created by liluoqi on 2017/7/16.
 * 互助计划状态
 */
public enum MutualAidPlanStatus {

    VALID("有效的"),
    INVALID("无效的");

    private String text;

    MutualAidPlanStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
