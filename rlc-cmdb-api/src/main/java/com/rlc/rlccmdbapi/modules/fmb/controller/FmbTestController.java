package com.rlc.rlccmdbapi.modules.fmb.controller;

import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlccmdbapi.modules.cmdb.service.LogService;
import com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog;
import com.rlc.rlccmdbapi.modules.fmb.service.FmbLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: 测试用
 * @date 2021/5/10 17:23
 */
@RestController
@RequestMapping("fmb")
@Api(value = "fmb测试类" ,tags = "fmb测试接口")
public class FmbTestController {
    protected static Logger logger =  LogManager.getLogger(FmbTestController.class);
    @Autowired
    private FmbLogService fmbLogService;
    @ApiImplicitParams({
        @ApiImplicitParam(name="cp",value="当前页码",required=true,paramType="form",dataType="Integer"),
        @ApiImplicitParam(name="pz",value="每页记录数",required=true,paramType="form",dataType="Integer")
    })
    @ApiOperation(value = "fmb获取list",notes = "默认查询当天")
    @GetMapping(value = "logList")
    public ResultData getLogList(@RequestParam("cp")Integer cp, @RequestParam("pz")Integer pz){
        Page<Fmblog> logPage = new Page<>();
        logPage.setCurrentPage(cp);
        logPage.setPageSize(pz);
        logPage = fmbLogService.findPage(logPage,new Fmblog());

//        PageInfo<Log> pageInfo = logService.getLogInfoPage(1,10);
        return ResultData.OK().data("page",logPage).message("成功");
    }
}
