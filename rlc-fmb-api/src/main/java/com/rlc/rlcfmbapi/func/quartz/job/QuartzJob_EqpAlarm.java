package com.rlc.rlcfmbapi.func.quartz.job;

import com.rlc.rlcbase.listener.SpringInit;
import com.rlc.rlcfmbapi.modules.interface_utils.EqpAlarmService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzJob_EqpAlarm extends QuartzJobBean {
    Logger logger =  LogManager.getLogger(QuartzJob_EqpAlarm.class);

    @Autowired
    @Lazy
    EqpAlarmService eqpAlarmService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(null==eqpAlarmService){
            eqpAlarmService = SpringInit.getBean(EqpAlarmService.class);
        }
        eqpAlarmService.SyncEqpAlarm();
    }
}
