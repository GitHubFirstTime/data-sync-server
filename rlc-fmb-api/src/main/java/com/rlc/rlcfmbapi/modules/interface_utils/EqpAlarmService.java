package com.rlc.rlcfmbapi.modules.interface_utils;

import com.rlc.rlcbase.kafka.ProducerService;
import com.rlc.rlcfmbapi.modules.mesUat.entity.AlarmDTO;
import com.rlc.rlcfmbapi.modules.mesUat.service.AlarmService;
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
public class EqpAlarmService {
    Logger logger =  LogManager.getLogger(EqpAlarmService.class);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private ProducerService producerService;
    public void SyncEqpAlarm(){
        try{
            List<AlarmDTO> alarmOpenList = alarmService.getAlarmOpenList();
            if(CollectionUtils.isNotEmpty(alarmOpenList)){
                logger.info("获取机台告警信息成功，检测时间:{}",sdf.format(new Date()));
                JSONArray jsonArray = new JSONArray();
                for (AlarmDTO alarm:alarmOpenList) {
                    JSONObject job = new JSONObject();
                    job.put("device_type",alarm.getDeviceType());
                    job.put("device_type_name",alarm.getDeviceTypeName());
                    job.put("device_name",alarm.getDeviceName());
                    job.put("alarm_type",alarm.getAlarmType());
                    job.put("alarm_status",alarm.getAlarmStatus());
                    job.put("alarm_level",alarm.getAlarmLevel());
                    job.put("alarm_reason_code",alarm.getAlarmReasonCode());
                    job.put("alarm_time",String.valueOf(Timestamp.valueOf(alarm.getAlarmTime()).getTime()));
                    job.put("alarm_description",alarm.getAlarmDescription());
                    job.put("alarm_id",alarm.getAlarmId());
                    jsonArray.add(job);
                }
                producerService.sendMsg("alarm","alarm",jsonArray.toString());
            }
        }catch (Exception e){
            logger.error("获取机台告警信息失败"+e.getMessage());
        }

    }
}
