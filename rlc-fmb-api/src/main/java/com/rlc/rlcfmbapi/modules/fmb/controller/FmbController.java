package com.rlc.rlcfmbapi.modules.fmb.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rlc.rlcbase.persistence.web.BaseController;
import com.rlc.rlcbase.result.ResultCodeEnum;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlcbase.utils.Base64Util;
import com.rlc.rlcbase.utils.thread.XQDataSyncThread;
import com.rlc.rlcfmbapi.modules.interface_utils.EqpTypeTranslateUtils;
import com.rlc.rlcfmbapi.modules.interface_utils.SyncEqpDataService;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDTO;
import com.rlc.rlcfmbapi.modules.mes.service.EqpService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 * ClassName:FmbController <br/>
 * Function: fmb平台接口 ADD FUNCTION. <br/>
 * Reason:	 fmb平台接口 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/14 9:32
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/")
public class FmbController extends BaseController {
    private SyncEqpDataService syncEqpDataService;
    @Autowired
    private EqpService xqEqpService;
    public static String replaceEnter(String str){
        String reg ="[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
    ExecutorService executorService = new ThreadPoolExecutor(2,4,0, TimeUnit.SECONDS,
            new ArrayBlockingQueue(512), // 使用有界队列，避免OOM
            new ThreadPoolExecutor.DiscardPolicy());
    @RequestMapping(value = "loadEqpData", method = RequestMethod.POST)
    @ResponseBody
    public ResultData loadEqpData(@RequestBody(required = true) String loadEqpDataParamsStr) {
        if (StringUtils.isBlank(loadEqpDataParamsStr)){
            return ResultData.OK().data("items","").code(ResultCodeEnum.MISSING_PARAM_ERROR.getCode()).message("缺少参数，命令下达失败");
        }
        JSONObject loadEqpDataParams = JSONObject.fromObject(Base64Util.DECODE(replaceEnter(loadEqpDataParamsStr.trim())));
        String sn = loadEqpDataParams.getString("sn");
        String cmd = loadEqpDataParams.getString("cmd");
        if (StringUtils.isBlank(sn)){
            return ResultData.OK().data("items","").code(ResultCodeEnum.MISSING_PARAM_ERROR.getCode()).message("缺少参数，命令下达失败");
        }
        executorService.submit(new XQDataSyncThread(sn,cmd));
        return ResultData.OK().data("items","").message("命令下达成功");
    }

    /**
     * 加载机台当前信息
     * @param loadEqpCurrentDetailParamsStr
     * @return
     */
    @RequestMapping(value = "loadEqpCurrentDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultData loadEqpCurrentDetail(@RequestBody(required = true) String loadEqpCurrentDetailParamsStr) throws Exception {
        if (StringUtils.isBlank(loadEqpCurrentDetailParamsStr)){
            return ResultData.OK().data("items","").code(ResultCodeEnum.MISSING_PARAM_ERROR.getCode()).message("缺少参数，命令下达失败");
        }
        JSONObject loadEqpDataParams = JSONObject.fromObject(Base64Util.DECODE(replaceEnter(loadEqpCurrentDetailParamsStr.trim())));
//        String sn = loadEqpDataParams.getString("sn");
//        String cmd = loadEqpDataParams.getString("cmd");
        String eqpName = loadEqpDataParams.getString("eqpName");
        EqpDTO xqEqpDTO = new EqpDTO();
        xqEqpDTO.setEqpName(eqpName);
        xqEqpDTO = xqEqpService.getEqpCurrentInfo(xqEqpDTO);
        if (null!=xqEqpDTO){
            xqEqpDTO.setEqpTypeName(EqpTypeTranslateUtils.englishToChinese(xqEqpDTO.getEqpTypeName()));
            List<Map<String,Object>> eqpCurrentLotList = xqEqpService.findEqpLotList(xqEqpDTO);
            xqEqpDTO.setCurrentLotList(eqpCurrentLotList);
            Gson gson = new GsonBuilder().create();
//            return ResultData.OK().data("data",JSONObject.fromObject(xqEqpDTO)).message("成功");
            return ResultData.OK().data("data",gson.toJson(xqEqpDTO)).message("成功");
        }else{
            return ResultData.OK().data("data",null).message("成功");
        }
    }
}