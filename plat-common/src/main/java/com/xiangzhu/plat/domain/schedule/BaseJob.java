package com.xiangzhu.plat.domain.schedule;

import org.quartz.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author liluoqi
 * @date 2017/7/16
 * 定时job
 */
public abstract class BaseJob implements Job{
    protected ApplicationContext applicationContext;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        getContext(jobExecutionContext);
        injectDependency();
        executeInternal(jobExecutionContext);
    }

    protected void getContext(JobExecutionContext jobExecutionContext) {
        try {
            applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContext");
        } catch (SchedulerException e) {
            applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
    }

    protected abstract void executeInternal(JobExecutionContext jobExecutionContext);

    protected void injectDependency() {

    }
}
