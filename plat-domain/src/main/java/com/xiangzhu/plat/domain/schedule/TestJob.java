package com.xiangzhu.plat.domain.schedule;

import com.xiangzhu.plat.utils.DateUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

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
        logger.info(String.format("test job execute at :%s",DateUtils.formatDateToSeconds(new Date())));
    }
}
