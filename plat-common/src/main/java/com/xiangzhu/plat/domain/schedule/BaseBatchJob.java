package com.xiangzhu.plat.domain.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by liluoqi on 15/6/27.
 * 批量job
 */
public abstract class BaseBatchJob<T> implements Job {

    protected ApplicationContext applicationContext;
    protected static final int BATCH_SIZE = 100;
    private Logger logger = LoggerFactory.getLogger(BaseBatchJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getContext(jobExecutionContext);
        injectDependency();
        long totalCount = count();
        logger.info(String.format("数据总量是:%s", totalCount));
        long loopCount = totalCount % BATCH_SIZE == 0 ? totalCount / BATCH_SIZE : totalCount / BATCH_SIZE + 1;
        for (long index = 0; index < loopCount; index++) {
            logger.info(String.format("执行第:%s圈,总共:%s圈", index + 1, loopCount));
            executeInternal(loadData(index * BATCH_SIZE, BATCH_SIZE));
        }
    }

    protected void getContext(JobExecutionContext jobExecutionContext) {
        try {
            applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
        } catch (SchedulerException e) {
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
    }

    protected abstract List<T> loadData(long startIndex, int batchSize);

    protected abstract long count();

    protected abstract void executeInternal(List<T> data);

    protected void injectDependency() {

    }
}
