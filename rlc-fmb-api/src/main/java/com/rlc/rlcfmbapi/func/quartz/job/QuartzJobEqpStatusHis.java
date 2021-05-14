package com.rlc.rlcfmbapi.func.quartz.job;

import com.rlc.rlcbase.listener.SpringInit;
import com.rlc.rlcfmbapi.modules.interface_utils.RecordEqpStatusHisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 线切机台数据同步定时任务
 */
@Component
public class QuartzJobEqpStatusHis extends QuartzJobBean {
    Logger logger =  LogManager.getLogger(QuartzJobEqpStatusHis.class);
    @Autowired
    @Lazy
    private RecordEqpStatusHisService recordEqpStatusHisService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(null==recordEqpStatusHisService){
            recordEqpStatusHisService = SpringInit.getBean(RecordEqpStatusHisService.class);
        }
        recordEqpStatusHisService.Sync_EqpStatusData("");
    }

}