package com.rlc.rlcbase.quartz;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/4/30 18:24
 */
@Component
public class QuartzManager {
    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;
    /**
     * 默认时区
     */
    private static final TimeZone defaultTimeZone = TimeZone.getTimeZone("Asia/Shanghai");

    /**
     * 增加一个job
     * @param jobClass 任务实现类
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobTime 时间表达式 （如：0/5 * * * * ? ）
     */
    public void addJob(Class<? extends Job> jobClass, String jobName,
                       String jobGroupName, String jobTime) {
        try {
          
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)// 任务名称和组构成任务key
                    .build();
            // 定义调度触发规则
            // 使用cornTrigger规则
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobName, jobGroupName)
                    // 触发器key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                    .startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个job
     *
     * @param jobClass 任务实现类
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobTime 时间表达式 (这是每隔多少秒为一次任务)
     */
    public void addJob(Class<? extends Job> jobClass, String jobName,
                       String jobGroupName, int jobTime) {
        addJob(jobClass, jobName, jobGroupName, jobTime, -1);
    }

    /**
     * 增加一个job
     *
     * @param jobClass 任务实现类
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobTime 时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes 运行的次数 （<0:表示不限次数）
     */
    public void addJob(Class<? extends Job> jobClass, String jobName,
                       String jobGroupName, int jobTime, int jobTimes) {
        try {
          
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)// 任务名称和组构成任务key
                    .build();
            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(jobName, jobGroupName)
                        .withSchedule(
                                SimpleScheduleBuilder.repeatSecondlyForever(1)
                                        .withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity(jobName, jobGroupName)
                        .withSchedule(
                                SimpleScheduleBuilder.repeatSecondlyForever(1)
                                        .withIntervalInSeconds(jobTime)
                                        .withRepeatCount(jobTimes)).startNow()
                        .build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName
     * @param jobGroupName
     * @param jobTime
     */
    public void updateJob(String jobName, String jobGroupName, String jobTime) {
        try {
          
            TriggerKey triggerKey = TriggerKey
                    .triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                    .build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务一个job
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) {
        try {
          
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
          
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
          
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即执行一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void runAJobNow(String jobName, String jobGroupName) {
        try {
          
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = null;
        try {
          
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler
                        .getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler
                            .getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public List<Map<String, Object>> queryRunJon() {
        List<Map<String, Object>> jobList = null;
        try {
          
            List<JobExecutionContext> executingJobs = scheduler
                    .getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler
                        .getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }
    /**
     * 获取任务状态
     * @param jobName
     * @param jobGroup 任务组（没有分组传值null）
     * @return
     * (" BLOCKED ", " 阻塞 ");
     * ("COMPLETE", "完成");
     * ("ERROR", "出错");
     * ("NONE", "不存在");
     * ("NORMAL", "正常");
     * ("PAUSED", "暂停");
     */
    public String getScheduleJobStatus(String jobName,String jobGroup) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,StringUtils.isNotBlank(jobGroup) ?jobGroup:null);
        Trigger.TriggerState state = scheduler.getTriggerState(triggerKey);
        return state.name();
    }

    /**
     * 根据定时任务名称来判断任务是否存在
     * @param jobName   定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @throws SchedulerException
     */
    public Boolean checkExistsScheduleJob(String jobName,String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, StringUtils.isNotBlank(jobGroup) ?jobGroup:null);
        return scheduler.checkExists(jobKey);
    }

    /**
     * 根据任务組刪除定時任务
     * @param jobGroup 任务组
     * @throws SchedulerException
     */
    public Boolean deleteGroupJob(String jobGroup) throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        Set<JobKey> jobkeySet = scheduler.getJobKeys(matcher);
        List<JobKey> jobkeyList = new ArrayList<JobKey>();
        jobkeyList.addAll(jobkeySet);
        return scheduler.deleteJobs(jobkeyList);
    }

    /**
     * 根据任务組批量刪除定時任务
     * @param jobkeyList
     * @throws SchedulerException
     */
    public Boolean batchDeleteGroupJob(List<JobKey> jobkeyList) throws Exception {
        return scheduler.deleteJobs(jobkeyList);
    }

    /**
     * 根据任务組批量查询出jobkey
     * @param jobGroup 任务组
     * @throws SchedulerException
     */
    public void batchQueryGroupJob(List<JobKey> jobkeyList,String jobGroup) throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(jobGroup);
        Set<JobKey> jobkeySet = scheduler.getJobKeys(matcher);
        jobkeyList.addAll(jobkeySet);
    }

}
