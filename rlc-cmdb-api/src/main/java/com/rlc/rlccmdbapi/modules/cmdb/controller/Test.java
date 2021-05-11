package com.rlc.rlccmdbapi.modules.cmdb.controller;

import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlccmdbapi.modules.cmdb.entity.CmdbModel;
import com.rlc.rlccmdbapi.modules.cmdb.entity.Log;
import com.rlc.rlccmdbapi.modules.cmdb.service.CmdbModelService;
import com.rlc.rlccmdbapi.modules.cmdb.service.LogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("testC")
public class Test {
    protected static Logger logger =  LogManager.getLogger(Test.class);
    @Autowired
    private LogService logService;
    @Autowired
    private CmdbModelService modelService;
    @RequestMapping("test")
    public Map test(){
        Map map = new HashMap();
        map.put("test","test");
        logger.info("进入test方法");
        return map;
    }
    @RequestMapping("logList")
    public ResultData getLogList(@RequestParam("cp")Integer cp, @RequestParam("pz")Integer pz){
        Page<Log> logPage = new Page<>();
        logPage.setCurrentPage(cp);
        logPage.setPageSize(pz);
        logPage = logService.findPage(logPage,new Log());

//        PageInfo<Log> pageInfo = logService.getLogInfoPage(1,10);
        return ResultData.OK().data("page",logPage).message("成功");
    }
    @RequestMapping("modelList")
    public ResultData getModelList(@RequestParam("cp")Integer cp, @RequestParam("pz")Integer pz){
        Page<CmdbModel> logPage = new Page<>();
        logPage.setCurrentPage(cp);
        logPage.setPageSize(pz);
        logPage = modelService.findPage(logPage,new CmdbModel());

//        PageInfo<Log> pageInfo = logService.getLogInfoPage(1,10);
        return ResultData.OK().data("page",logPage).message("成功");
    }
}
