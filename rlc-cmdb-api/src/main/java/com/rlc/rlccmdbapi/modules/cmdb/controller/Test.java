package com.rlc.rlccmdbapi.modules.cmdb.controller;

import com.rlc.rlcbase.common.IdGen;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlccmdbapi.modules.cmdb.entity.CmdbModel;
import com.rlc.rlccmdbapi.modules.cmdb.entity.Log;
import com.rlc.rlccmdbapi.modules.cmdb.service.CmdbModelService;
import com.rlc.rlccmdbapi.modules.cmdb.service.LogService;
import com.rlc.rlccmdbapi.modules.fmb.service.FmbLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("testC")
@Api(value = "cmdb测试类" ,tags = "测试接口")
public class Test {
    protected static Logger logger =  LogManager.getLogger(Test.class);
    @Autowired
    private LogService logService;
    @Autowired
    private FmbLogService fmbLogService;
    @Autowired
    private CmdbModelService modelService;
    @RequestMapping("test")
    public Map test(){
        Map map = new HashMap();
        map.put("test","test");
        logger.info("进入test方法");
        return map;
    }
    @ApiOperation("获取日志列表")
    @GetMapping("logList")
    public ResultData getLogList(@RequestParam("cp")Integer cp, @RequestParam("pz")Integer pz){
        Page<Log> logPage = new Page<>();
        logPage.setCurrentPage(cp);
        logPage.setPageSize(pz);
        logPage = logService.findPage(logPage,new Log());

//        PageInfo<Log> pageInfo = logService.getLogInfoPage(1,10);
        return ResultData.OK().data("page",logPage).message("成功");
    }
    @ApiOperation("获取模型列表")
    @GetMapping("modelList")
    public ResultData getModelList(@RequestParam("cp")Integer cp, @RequestParam("pz")Integer pz){
        Page<CmdbModel> logPage = new Page<>();
        logPage.setCurrentPage(cp);
        logPage.setPageSize(pz);
        logPage = modelService.findPage(logPage,new CmdbModel());

//        PageInfo<Log> pageInfo = logService.getLogInfoPage(1,10);
        return ResultData.OK().data("page",logPage).message("成功");
    }
    @RequestMapping("saveTest")
    public ResultData saveTest(){


        try {
           fmbLogService.saveAll();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.ERROR().message("ffff");
        }
        return ResultData.OK().message("sss");
    }
}
