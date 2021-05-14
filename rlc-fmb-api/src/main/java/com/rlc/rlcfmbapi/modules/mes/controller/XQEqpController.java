package com.rlc.rlcfmbapi.modules.mes.controller;

import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.web.BaseController;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDTO;
import com.rlc.rlcfmbapi.modules.mes.service.EqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 线切controller
 * @author zyc
 */
@RestController

public class XQEqpController extends BaseController {
    @Autowired
    private EqpService xqEqpService;
    @RequestMapping("getXQEQPInfoPageList")
    public ResultData getXQEQPInfoPageList(Integer currentPage, Integer pageSzie){
        info("getXQEQPInfoPageList");
        Page<EqpDTO> page= new Page<EqpDTO>(currentPage,pageSzie);
        if (null!=xqEqpService&&null!=pageSzie){
            page.setCurrentPage(currentPage);
            page.setPageSize(pageSzie);
            page = xqEqpService.findPage(page,new EqpDTO());
        }
        return ResultData.OK().data("items",page.getList()).message("线切分页");
    }
    @RequestMapping("getXQEQPInfoList")
    public ResultData getXQEQPInfoList(){
        info("getXQEQPInfoList");
        EqpDTO eqpDTO = new EqpDTO();
        List<EqpDTO> xqEqpDTOList = xqEqpService.findList(eqpDTO);
        return ResultData.OK().data("items",xqEqpDTOList).message("线切全量");
    }
}