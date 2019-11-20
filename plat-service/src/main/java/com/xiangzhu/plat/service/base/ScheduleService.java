package com.xiangzhu.plat.service.base;

import com.xiangzhu.plat.utils.ClassUtils;
import com.xiangzhu.plat.utils.JsonUtils;
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

    private String parseJobName(String jobClass) {
        return jobClass.substring(jobClass.lastIndexOf(".") + 1);
    }
}
