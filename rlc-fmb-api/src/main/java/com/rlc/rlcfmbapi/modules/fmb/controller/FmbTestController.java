package com.rlc.rlcfmbapi.modules.fmb.controller;

import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlcfmbapi.modules.fmb.entity.Log;
import com.rlc.rlcfmbapi.modules.fmb.service.FmbLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/10 17:23
 */
@RestController
@RequestMapping("fmb")
public class FmbTestController {
    protected static Logger logger =  LogManager.getLogger(FmbTestController.class);
    @Autowired
    private FmbLogService fmbLogService;
    @RequestMapping("logList")
    public ResultData getLogList(@RequestParam("cp")Integer cp, @RequestParam("pz")Integer pz){
        Page<Log> logPage = new Page<>();
        logPage.setCurrentPage(cp);
        logPage.setPageSize(pz);
        logPage = fmbLogService.findPage(logPage,new Log());

//        PageInfo<Log> pageInfo = logService.getLogInfoPage(1,10);
        return ResultData.OK().data("page",logPage).message("成功");
    }
}
