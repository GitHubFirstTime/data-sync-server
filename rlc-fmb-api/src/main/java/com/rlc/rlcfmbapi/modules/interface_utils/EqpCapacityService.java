package com.rlc.rlcfmbapi.modules.interface_utils;

import com.rlc.rlcbase.kafka.ProducerService;
import com.rlc.rlcfmbapi.modules.report.service.EqpRodNumberService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class EqpCapacityService {
    Logger logger =  LogManager.getLogger(EqpCapacityService.class);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ProducerService producerService;
    @Autowired
    private EqpRodNumberService eqpRodNumberService;

    public void SyncEqpCapacity(){
        try{
            logger.info("获取容量信息成功，检测时间:{}",sdf.format(new Date()));
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < 6; i++) {
                JSONObject obj = new JSONObject();

                if(i==0){
                    obj.put("device_type","CPK");
                    obj.put("device_type_name","成品库");
                    obj.put("rod_number",1400);
                    jsonArray.add(obj);
                }else if(i==1){
                    obj.put("device_type","YLK");
                    obj.put("device_type_name","原料库");
                    obj.put("rod_number",1300);
                    jsonArray.add(obj);
                }else if(i==2){
                    obj.put("device_type","DJK");
                    obj.put("device_type_name","单晶库");
                    obj.put("rod_number",805);
                    jsonArray.add(obj);
                }else if(i==3){
                    obj.put("device_type","GHK");
                    obj.put("device_type_name","固化库");
                    obj.put("rod_number",eqpRodNumberService.getEqpRodNumber("GHK").getRodNumber());
                    jsonArray.add(obj);
                }else if(i==4){
                    obj.put("device_type","XQ");
                    obj.put("device_type_name","线切车间");
                    obj.put("rod_number",eqpRodNumberService.getEqpRodNumber("XQ").getRodNumber());
                    jsonArray.add(obj);
                }else if(i==5){
                    obj.put("device_type","TJ");
                    obj.put("device_type_name","原料库");
                    obj.put("rod_number",eqpRodNumberService.getEqpRodNumber("TJ").getRodNumber());
                    jsonArray.add(obj);
                    return;
                }
            }
            producerService.sendMsg("capacity_stat","capacity_stat",jsonArray.toString());
        }catch (Exception e){
            logger.error("获取容量信息失败"+e.getMessage());
        }
    }

}
