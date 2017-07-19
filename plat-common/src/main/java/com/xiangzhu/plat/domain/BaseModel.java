package com.xiangzhu.plat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liluoqi on 2017/7/9.
 */
public class BaseModel implements Serializable{
    private long id;
    private Date createTime;
    private Date updateTime;

    public BaseModel() {
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
