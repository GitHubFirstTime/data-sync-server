package com.rlc.rlccmdbapi.modules.func.quartzJob;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

@Slf4j
public class QuartzJobTestClaster extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String taskName = jobExecutionContext.getJobDetail().getJobDataMap().getString("name");

        log.info("---> Quartz job {}, {} <----", new Date(), taskName+"2");
    }
}
