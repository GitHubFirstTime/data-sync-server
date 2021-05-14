package com.rlc.rlcfmbapi.modules.interface_utils;

import com.rlc.rlcbase.kafka.ProducerService;
import com.rlc.rlcfmbapi.modules.mes.entity.PmsInfoDTO;
import com.rlc.rlcfmbapi.modules.mes.service.PmsInfoService;
import com.rlc.rlcfmbapi.modules.stereoLayer.response.PmsInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class EqpPmsService {
    Logger logger =  LogManager.getLogger(EqpPmsService.class);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ProducerService producerService;
    @Autowired
    private PmsInfoService pmsInfoService;

    public void SyncEqpPms(){
        try{
            List<PmsInfoDTO> pmsOpenInfo = pmsInfoService.getPmsOpenInfo();
            if(CollectionUtils.isNotEmpty(pmsOpenInfo)){
                logger.info("获取机台维保信息成功，检测时间:{}",sdf.format(new Date()));
                JSONArray jsonArray = new JSONArray();
                for (PmsInfoDTO pms:pmsOpenInfo) {
                    JSONObject job = new JSONObject();
                    PmsInfo response = new PmsInfo();
                    response.setMaintenanceTime(String.valueOf(Timestamp.valueOf(pms.getMaintenceTime()).getTime()));
                    response.setNextMaintenanceTime(String.valueOf(Timestamp.valueOf(pms.getNextMaintenceTime()).getTime()));
                    response.setMaintenanceMaterial(pms.getMaintenceMaterial());
                    response.setDeviceType(pms.getDeviceType());
                    response.setDeviceTypeName(pms.getDeviceTypeName());
                    response.setDeviceName(pms.getDeviceName());
                    jsonArray.add(response);
                }
                producerService.sendMsg("pms_info","pms_info",jsonArray.toString());
            }
        }catch (Exception e){
            logger.error("获取机台维保信息失败"+e.getMessage());
        }
    }
}
