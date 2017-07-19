package com.xiangzhu.plat.domain.schedule;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liluoqi on 2017/7/16.
 * 测试job
 */
public class TestJob extends BaseJob {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        logger.info("test job executing!");
    }
}
