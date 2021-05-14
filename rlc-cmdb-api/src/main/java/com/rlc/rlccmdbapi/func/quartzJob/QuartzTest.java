package com.rlc.rlccmdbapi.func.quartzJob;

import com.rlc.rlcbase.quartz.QuartzManager;
import com.rlc.rlcbase.result.ResultData;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("addJob")
public class QuartzTest {
    @Autowired
    QuartzManager quartzManager;
    @RequestMapping("aa")
    public ResultData addJob(){
        try {
            JobDataMap paramsMap = new JobDataMap();
            paramsMap.put("jobName","job2");
            quartzManager.addJob(QuartzJobTestClaster.class,"jjName","jjGname","0/10 * * * * ?");
            return ResultData.OK().message("addJob success!");
        } catch (Exception e) {
            return ResultData.ERROR().message("addJob failed!");
        }
    }
    @RequestMapping("delJob")
    public ResultData delJob(){
        try {
            JobDataMap paramsMap = new JobDataMap();
            paramsMap.put("jobName","job2");
            quartzManager.deleteJob("jjName","jjGname");
            return ResultData.OK().message("addJob success!");
        } catch (Exception e) {
            return ResultData.ERROR().message("addJob failed!");
        }
    }
}
