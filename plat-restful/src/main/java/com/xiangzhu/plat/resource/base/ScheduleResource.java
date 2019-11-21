package com.xiangzhu.plat.resource.base;

import com.xiangzhu.plat.service.base.ScheduleService;
import com.xiangzhu.plat.utils.JsonUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by liluoqi on 2017/7/16.
 * 定时任务rest接口
 * @author lqli
 */
@Component
@Path("schedule")
public class ScheduleResource {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ScheduleResource.class);

    @Autowired
    private ScheduleService scheduleService;

    @Path("start")
    @POST
    public void start() {
        try {
            scheduleService.scheduleStart();
        } catch (SchedulerException e) {
            logger.error("执行job异常", e);
        }
    }

    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String addScheduleJob(@FormParam("jobClass") String jobClass, @FormParam("cronExpression") String cronExpression
            , @FormParam("description") String description, @FormParam("jobDataMapString") String jobDataMapString) {
        logger.info(String.format("新增job class:%s,cron express:%s,描述:%s的job", jobClass, cronExpression, description));
        try {
            return JsonUtils.toJson(scheduleService.addCronJob(jobClass, cronExpression, jobDataMapString, description));
        } catch (Exception e) {
            return JsonUtils.toJson(false);
        }
    }

    @Path("adjustJobCron")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String adjustJobCron(@FormParam("jobClass") String jobClass, @FormParam("newCronExpr") String newCronExpr){
        logger.info(String.format("调整job class:%s的定时任务时间为:%s",jobClass,newCronExpr));
        try {
            return JsonUtils.toJson(scheduleService.adjustCronExpr(jobClass, newCronExpr));
        }catch (Exception e){
            return JsonUtils.toJson(false);
        }
    }
}
