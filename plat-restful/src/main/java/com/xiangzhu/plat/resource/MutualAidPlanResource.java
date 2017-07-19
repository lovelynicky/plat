package com.xiangzhu.plat.resource;

import com.xiangzhu.plat.service.business.MutualAidPlanService;
import com.xiangzhu.plat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by lqli on 2017/7/19 9:30.
 * 互助计划rest服务接口
 *
 * @author lqli
 */
@Component
@Path("plan")
public class MutualAidPlanResource {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(MutualAidPlanResource.class);

    @Autowired
    private MutualAidPlanService mutualAidPlanService;

    @Path("queryAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryAll() {
        logger.info("查询所有互助计划");
        return JsonUtils.toJson(mutualAidPlanService.queryAll());
    }
}
