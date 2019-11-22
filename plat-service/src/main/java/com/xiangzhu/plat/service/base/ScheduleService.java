package com.xiangzhu.plat.service.base;

import com.xiangzhu.plat.utils.ClassUtils;
import com.xiangzhu.plat.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liluoqi on 2017/7/16.
 * 任务调度操作类
 *
 * @author lqli
 */
@Service
public class ScheduleService {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    /**
     * quartz调度类，工厂方法ScheduleFactoryBean生成
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 默认组
     */
    private static final String DEFAULT_GROUP = "plat";
    /**
     * trigger前缀
     */
    private static final String TRIGGER_PREFIX = "trigger-";

    /**
     * 开始job
     *
     * @throws SchedulerException SchedulerException
     */
    public void scheduleStart() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 新增定时任务
     *
     * @param jobClass       job类必须继承Job接口
     * @param cronExpression 定时cron表达式
     * @param jobDataMap     job参数
     * @param description    描述
     * @return boolean
     */
    public boolean addCronJob(String jobClass, String cronExpression, Map<String, Object> jobDataMap, String description) {
        try {
            Class clazz = ClassUtils.getClassByName(jobClass);
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(parseJobName(jobClass), DEFAULT_GROUP)
                    .withDescription(description).storeDurably().build();
            if (jobDataMap != null && jobDataMap.size() > 0) {
                for (String key : jobDataMap.keySet()) {
                    jobDetail.getJobDataMap().put(key, jobDataMap.get(key));
                }
            }
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(String.format("%s%s", TRIGGER_PREFIX, jobDetail.getKey().getName()), jobDetail.getKey().getGroup())
                    .withSchedule(cronScheduleBuilder).startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            logger.error(String.format("新增jobClass:%s,表达式:%s,描述:%s失败", jobClass, cronExpression, description), e);
            return false;
        } catch (ClassNotFoundException e) {
            logger.error(String.format("类:%s不存在", jobClass), e);
            return false;
        }
    }

    /**
     * 新增定时任务
     *
     * @param jobClass         job类
     * @param cronExpression   定时cron表达式
     * @param jobDataMapString job参数
     * @param description      描述
     * @return boolean
     */
    public boolean addCronJob(String jobClass, String cronExpression, String jobDataMapString, String description) {
        Map<String, Object> jobDataMap = JsonUtils.fromJsonMap(jobDataMapString);
        return addCronJob(jobClass, cronExpression, jobDataMap, description);
    }

    /**
     * 调整任务执行时间
     *
     * @param jobClass    jobClass
     * @param newCronExpr 新的时间表达式
     * @return 结果
     */
    public boolean adjustCronExpr(String jobClass, String newCronExpr) {
        if (StringUtils.isBlank(jobClass)) {
            logger.error("需要调整的任务类不能为空");
            return false;
        }
        if (StringUtils.isBlank(newCronExpr)) {
            logger.error("新的定时表达式不能为空");
            return false;
        }
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(parseJobName(jobClass), DEFAULT_GROUP));
            if (jobDetail != null) {
                TriggerKey triggerKey = new TriggerKey(String.format("%s%s", TRIGGER_PREFIX, jobDetail.getKey().getName()), DEFAULT_GROUP);
                Trigger trigger = scheduler.getTrigger(triggerKey);
                if (trigger != null) {
                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(newCronExpr);
                    Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(String.format("%s%s", TRIGGER_PREFIX, jobDetail.getKey().getName()), jobDetail.getKey().getGroup())
                            .withSchedule(cronScheduleBuilder).startNow().build();
                    scheduler.rescheduleJob(triggerKey, newTrigger);
                    return true;
                } else {
                    logger.error(String.format("获取不到jobClass:%s对应job trigger", jobClass));
                }
            } else {
                logger.error(String.format("获取不到jobClass:%s对应jobDetail", jobClass));
            }
        } catch (SchedulerException e) {
            logger.error(String.format("获取jobClass:%s对应jobDetail异常", jobClass), e);
        }
        return false;
    }

    /**
     * 添加任务数据
     *
     * @param jobClass 任务完整类名
     * @param key      key
     * @param value    value
     * @return boolean
     * // TODO: 2019/11/21  还未生效
     */
    public boolean addJobData(String jobClass, String key, String value) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(parseJobName(jobClass), DEFAULT_GROUP));
            if (jobDetail != null) {
                jobDetail.getJobDataMap().put(key,value);
                TriggerKey triggerKey = new TriggerKey(String.format("%s%s", TRIGGER_PREFIX, jobDetail.getKey().getName()), DEFAULT_GROUP);
                Trigger trigger = scheduler.getTrigger(triggerKey);
                CronScheduleBuilder cronScheduleBuilder = (CronScheduleBuilder) trigger.getScheduleBuilder();
                Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(String.format("%s%s", TRIGGER_PREFIX, jobDetail.getKey().getName()), jobDetail.getKey().getGroup())
                        .withSchedule(cronScheduleBuilder).usingJobData(key,value).startNow().build();
                scheduler.rescheduleJob(triggerKey,newTrigger);
                return true;
            } else {
                logger.error(String.format("找不到jobClass:%s对应jobDetail", jobClass));
            }
        } catch (SchedulerException e) {
            logger.error(String.format("添加参数键值对key:%s,value:%s到jobClass:%s对应jobDetail失败", key,value,jobClass),e);
        }
        return false;
    }

    private String parseJobName(String jobClass) {
        return jobClass.substring(jobClass.lastIndexOf(".") + 1);
    }
}
