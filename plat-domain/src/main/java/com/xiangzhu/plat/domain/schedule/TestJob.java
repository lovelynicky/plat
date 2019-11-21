package com.xiangzhu.plat.domain.schedule;

import com.xiangzhu.plat.utils.DateUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author liluoqi
 * @date 2017/7/16
 * 测试job
 */
public class TestJob extends BaseJob {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        long amount = jobDataMap.getLong("amount");
        logger.info(String.format("test job execute at :%s,amount param is :%s", DateUtils.formatDateToSeconds(new Date()), amount));
    }
}
