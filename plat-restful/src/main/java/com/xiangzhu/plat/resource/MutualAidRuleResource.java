package com.xiangzhu.plat.resource;

import com.xiangzhu.plat.service.business.MutualAidRuleService;
import com.xiangzhu.plat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lqli on 2017/7/19 9:51.
 * 互助规则rest服务接口
 *
 * @author lqli
 */
@Component
@Path("rule")
public class MutualAidRuleResource {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(MutualAidPlanResource.class);
    /**
     * mutualAidRuleService
     */
    @Autowired
    private MutualAidRuleService mutualAidRuleService;

    /**
     * id查询
     *
     * @param id id
     * @return json string
     */
    @Path("queryById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryById(@PathParam("id") long id) {
        logger.info("查询id:{}的互助规则", id);
        return JsonUtils.toJson(mutualAidRuleService.queryById(id));
    }
}
