package com.xiangzhu.plat.domain;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * Created by liluoqi on 2017/7/16.
 */
public class JobListener implements org.quartz.JobListener {
    private static Logger logger = LoggerFactory.getLogger(JobListener.class);
    @Override
    public String getName() {
        return "taskListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        final JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        if (logger.isInfoEnabled()) {
            logger.info("定时任务开始执行：{}-{}", jobExecutionContext.getJobDetail().getJobClass(),
                    jobExecutionContext.getJobDetail().getDescription());
        }
        // 保存日志
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        Timestamp end = new Timestamp(System.currentTimeMillis());
        final JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        if (logger.isInfoEnabled()) {
            logger.info("定时任务执行结束：{}-{}", jobExecutionContext.getJobDetail().getJobClass(),
                    jobExecutionContext.getJobDetail().getDescription());
        }
        // 更新任务执行状态
    }
}
