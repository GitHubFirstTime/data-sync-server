package com.rlc.rlcfmbapi.modules.interface_utils;

import com.rlc.rlcbase.kafka.ProducerService;
import com.rlc.rlcfmbapi.modules.fmb.service.FmbEqpService;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDetailDTO;
import com.rlc.rlcfmbapi.modules.mes.service.EqpDetailService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * TODO
 * ClassName:EqpCurrentDetailService <br/>
 * Function: 机台当前详情查询service ADD FUNCTION. <br/>
 * Reason:	 机台当前详情查询service ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/27 17:44
 * @since JDK 1.8
 */
@Component
public class EqpCurrentDetailService {
    Logger logger =  LogManager.getLogger(EqpCurrentDetailService.class);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private EqpDetailService eqpDetailService;
    @Autowired
    private ProducerService producerService;
//    @Autowired
//    private FmbEqpService fmbEqpService;
    public void SyncEqpState(String eqpType){
        try {
            List<EqpDetailDTO> allEqpRunInfoList = eqpDetailService.getAllEqpRunInfoList();
            if(CollectionUtils.isNotEmpty(allEqpRunInfoList)){
                logger.info("获取机台状态信息成功，检测时间:{}",sdf.format(new Date()));
                JSONArray jsonArray = new JSONArray();
                for (EqpDetailDTO eqp:allEqpRunInfoList){
                    JSONObject jsonObject = new JSONObject();
                    String str;
                    if(null==eqp.getEqpRunState()||eqp.getEqpRunState().equals("Others")||eqp.getEqpRunState().equals("Other")){
                        str = "STOP";
                    }else {
                        str = eqp.getEqpRunState().toUpperCase();
                    }
                    jsonObject.put("running_status",str);
                    jsonObject.put("device_id",eqp.getId());
                    jsonObject.put("device_name",eqp.getEqpName());
                    jsonObject.put("device_type",eqp.getEqpType());
                    jsonObject.put("device_type_name",null==eqp.getEqpType()?"":EqpTypeTranslateUtils.englishToChinese(eqp.getEqpType()));
                    jsonObject.put("control_mode",eqp.getEqpControlMode());
                    jsonObject.put("dispatch_mode",eqp.getEqpTransportMode());
                    jsonArray.add(jsonObject);
                }
                producerService.sendMsg("run_status","run_status",jsonArray.toString());
            }
        } catch (Exception e) {
            logger.error("获取机台状态信息失败",e);
        }
    }

}