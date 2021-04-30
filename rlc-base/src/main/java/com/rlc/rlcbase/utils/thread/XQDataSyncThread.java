package com.rlc.rlcbase.utils.thread;

import com.google.common.collect.Maps;
import com.rlc.rlcbase.kafka.ProducerService;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlcbase.utils.Base64Util;
import com.rlc.rlcbase.utils.SpringBeanUtil;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * TODO
 * ClassName:XQDataSync <br/>
 * Function: 线切机数据同步 ADD FUNCTION. <br/>
 * Reason:	 线切机数据同步 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/14 15:10
 * @since JDK 1.8
 */
public class XQDataSyncThread extends Thread {
//    private SyncEqpDataService syncEqpDataService;
    private ProducerService producerService;
    private String sn;
    private String cmd;
    public XQDataSyncThread(String sn,String cmd){
        this.sn = sn;
        this.cmd = cmd;
    }
    @Override
    public void run() {
//        this.syncEqpDataService =  SpringBeanUtil.getBean(SyncEqpDataService.class);
//        this.producerService =  SpringBeanUtil.getBean(ProducerService.class);
//        ResultData resultData = syncEqpDataService.Sync_EqpData("");
        ResultData resultData = ResultData.OK();
        Map<String,Object> returnMap = Maps.newHashMap();
        returnMap.put("sn",sn);
        returnMap.put("cmd",cmd);
        resultData.data("resultJSON",JSONObject.fromObject(returnMap).toString());
        producerService.sendMsg("loadEqpData",sn, Base64Util.ENCODE((JSONObject.fromObject(resultData).toString())));
    }
}