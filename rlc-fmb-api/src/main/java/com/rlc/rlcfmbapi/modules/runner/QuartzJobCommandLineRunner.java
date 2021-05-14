package com.rlc.rlcfmbapi.modules.runner;

import com.rlc.rlcbase.quartz.QuartzManager;
import com.rlc.rlcfmbapi.func.quartz.job.*;
import org.quartz.JobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/8 14:36
 */
@Component
public class QuartzJobCommandLineRunner implements CommandLineRunner {
    @Autowired
    @Lazy
    QuartzManager QuartzManager;
    @Override
    public void run(String... args) throws Exception {
        try {

            if(!QuartzManager.checkJob(Quartz_EqpCpacity.class)){
                //获取机台容量信息 每30秒执行一次
                QuartzManager.addJob(Quartz_EqpCpacity.class, "Quartz_EqpCpacity", "Quartz_EqpCpacity", "0/30 * * * * ?");
            }

            if(!QuartzManager.checkJob(QuartzJob_Eqp.class)){
                //机台数据更新--只更新变化数据 每10秒执行一次
                QuartzManager.addJob(QuartzJob_Eqp.class, "QuartzJob_Eqp", "QuartzJob_Eqp", "0/10 * * * * ?");
            }

            if(!QuartzManager.checkJob(QuartzJob_EqpAlarm.class)){
                //机台告警信息 每30秒执行一次
                QuartzManager.addJob(QuartzJob_EqpAlarm.class, "QuartzJob_EqpAlarm", "QuartzJob_EqpAlarm", "0/30 * * * * ?");
            }

            if(!QuartzManager.checkJob(QuartzJob_EqpPms.class)){
                //机台维保信息 每30秒执行一次
                QuartzManager.addJob(QuartzJob_EqpPms.class, "QuartzJob_EqpPms", "QuartzJob_EqpPms", "0/30 * * * * ?");
            }

            if(!QuartzManager.checkJob(QuartzJob_EqpState.class)){
                //机台状态更新 每50秒执行一次
                QuartzManager.addJob(QuartzJob_EqpState.class, "QuartzJob_EqpState", "QuartzJob_EqpState", "0/50 * * * * ?");
            }

            if(!QuartzManager.checkJob(QuartzJobEqpStatusHis.class)){
                //记录机台状态历史 每30秒执行一次
                QuartzManager.addJob(QuartzJobEqpStatusHis.class, "QuartzJobEqpStatusHis", "QuartzJobEqpStatusHis", "0/30 * * * * ?");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
