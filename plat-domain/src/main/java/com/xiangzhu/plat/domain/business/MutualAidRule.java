package com.xiangzhu.plat.domain.business;

import com.xiangzhu.plat.domain.BaseModel;

/**
 * Created by lqli on 2017/7/19 9:00.
 * 互助规则
 *
 * @author lqli
 */
public class MutualAidRule extends BaseModel {
    private static final long serialVersionUID = 7347714666080613254L;

    /**
     * 互助规则名称
     */
    private String name;
    /**
     * 模板路径 静态服务器资源路径
     */
    private String templatePath;

    /**
     * 默认构造方法
     */
    public MutualAidRule() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
