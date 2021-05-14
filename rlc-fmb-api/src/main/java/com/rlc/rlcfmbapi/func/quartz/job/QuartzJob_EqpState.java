package com.rlc.rlcfmbapi.func.quartz.job;

import com.rlc.rlcbase.listener.SpringInit;
import com.rlc.rlcfmbapi.modules.interface_utils.EqpCurrentDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * TODO
 * ClassName:QuartzJob_EqpState <br/>
 * Function: 机台状态更新 ADD FUNCTION. <br/>
 * Reason:	 机台状态更新 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/29 13:25
 * @since JDK 1.8
 */
@Component
public class QuartzJob_EqpState extends QuartzJobBean {
    Logger logger =  LogManager.getLogger(QuartzJob_Eqp.class);
    @Autowired
    @Lazy
    private EqpCurrentDetailService eqpCurrentDetailService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(null==eqpCurrentDetailService){
            eqpCurrentDetailService = SpringInit.getBean(EqpCurrentDetailService.class);
        }
        eqpCurrentDetailService.SyncEqpState(null);
    }
}