package com.xiangzhu.plat.domain.business;

import com.xiangzhu.plat.domain.BaseModel;
import com.xiangzhu.plat.domain.enums.MutualAidPlanStatus;

/**
 * Created by liluoqi on 2017/7/16.
 * 互助计划
 */
public class MutualAidPlan extends BaseModel {
    private static final long serialVersionUID = 7247714666080613256L;

    /**
     * 互助计划编号
     */
    private String mutualAidPlanNo;
    /**
     * 副标题
     */
    private String viceTitle;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 适用年龄
     */
    private String suitableAge;
    /**
     * 互助计划状态
     */
    private MutualAidPlanStatus status;
    /**
     * 互助规则id
     */
    private long mutualAidRuleId;
    /**
     * 互助条件id
     */
    private long mutualAidConditionId;
    /**
     * 备注
     */
    private String remark;

    /**
     * 默认构造方法
     */
    public MutualAidPlan(){
        super();
    }

    public String getMutualAidPlanNo() {
        return mutualAidPlanNo;
    }

    public void setMutualAidPlanNo(String mutualAidPlanNo) {
        this.mutualAidPlanNo = mutualAidPlanNo;
    }

    public String getViceTitle() {
        return viceTitle;
    }

    public void setViceTitle(String viceTitle) {
        this.viceTitle = viceTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuitableAge() {
        return suitableAge;
    }

    public void setSuitableAge(String suitableAge) {
        this.suitableAge = suitableAge;
    }

    public MutualAidPlanStatus getStatus() {
        return status;
    }

    public void setStatus(MutualAidPlanStatus status) {
        this.status = status;
    }

    public long getMutualAidRuleId() {
        return mutualAidRuleId;
    }

    public void setMutualAidRuleId(long mutualAidRuleId) {
        this.mutualAidRuleId = mutualAidRuleId;
    }

    public long getMutualAidConditionId() {
        return mutualAidConditionId;
    }

    public void setMutualAidConditionId(long mutualAidConditionId) {
        this.mutualAidConditionId = mutualAidConditionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
