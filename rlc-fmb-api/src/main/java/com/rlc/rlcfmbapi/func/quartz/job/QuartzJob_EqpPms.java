package com.rlc.rlcfmbapi.func.quartz.job;

import com.rlc.rlcbase.listener.SpringInit;
import com.rlc.rlcfmbapi.modules.interface_utils.EqpPmsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzJob_EqpPms extends QuartzJobBean {
    Logger logger =  LogManager.getLogger(QuartzJob_EqpPms.class);
    @Autowired
    @Lazy
    EqpPmsService eqpPmsService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(null==eqpPmsService){
            eqpPmsService = SpringInit.getBean(EqpPmsService.class);
        }
        eqpPmsService.SyncEqpPms();
    }
}
