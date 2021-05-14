package com.rlc.rlcfmbapi.modules.mesUat.service;

import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.mesUat.dao.AlarmDao;
import com.rlc.rlcfmbapi.modules.mesUat.entity.AlarmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AlarmService extends CrudService<AlarmDao,AlarmDTO> {
    @Autowired
    private AlarmDao alarmDao;
    //分页
    public Page<AlarmDTO> findPage(Page<AlarmDTO> page, AlarmDTO log) {
        return super.findPage(page, log);
    }
    //单台设备告警信息
    public AlarmDTO getAlarmDetail(String toolId,String alarmId){
        AlarmDTO alarmDetail = alarmDao.getAlarmDetail(toolId, alarmId);
        return alarmDetail;
    }
    //设备告警信息
    public List<AlarmDTO> getAlarmInfo(int page_num,int page_size,String toolId){
        List<AlarmDTO> alarmInfo = alarmDao.getAlarmInfo(page_num,page_size,toolId);
        return alarmInfo;
    }
    //单台设备告警信息统计
    public List<AlarmDTO> getAlarmInfoList (int page_num,int page_size,String device_type){
        List<AlarmDTO> alarmInfoList = alarmDao.getAlarmInfoList(page_num,page_size,device_type);
        return alarmInfoList;
    }
    //已处理一楼
    public List<AlarmDTO> getAlarmInfoListOneFloor (int page_num,int page_size){
        List<AlarmDTO> alarmInfoListOneFloor = alarmDao.getAlarmInfoListOneFloor(page_num,page_size);
        return alarmInfoListOneFloor;
    }

    //已处理二楼
    public List<AlarmDTO> getAlarmInfoListTwoFloor (int page_num,int page_size){
        List<AlarmDTO> alarmInfoListTwoFloor = alarmDao.getAlarmInfoListTwoFloor(page_num,page_size);
        return alarmInfoListTwoFloor;
    }

    //未处理的告警列表
    public  List<AlarmDTO> getAlarmOpenList(){
        List<AlarmDTO> alarmOpenList = alarmDao.getAlarmOpenList();
        return alarmOpenList;
    }

    //未处理的一楼
    public  List<AlarmDTO> getAlarmOpenOneFloor(){
        List<AlarmDTO> alarmOpenOneFloor = alarmDao.getAlarmOpenOneFloor();
        return alarmOpenOneFloor;
    }
    //未处理的二楼
    public List<AlarmDTO> getAlarmOpenTwoFloor(){
        List<AlarmDTO> alarmOpenTwoFloor = alarmDao.getAlarmOpenTwoFloor();
        return alarmOpenTwoFloor;
    }
    //未处理的单个车间
    public List<AlarmDTO> getAlarmOpenOneTypeEqp(String device_type){
        List<AlarmDTO> alarmOpenOneTypeEqp = alarmDao.getAlarmOpenOneTypeEqp(device_type);
        return alarmOpenOneTypeEqp;
    }
    //未处理的单个设备
    public List<AlarmDTO> getAlarmOpenOneEqp(String device_name){
        List<AlarmDTO> alarmOpenOneEqp = alarmDao.getAlarmOpenOneEqp(device_name);
        return alarmOpenOneEqp;
    }
}
