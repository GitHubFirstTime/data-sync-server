package com.rlc.rlcfmbapi.modules.mesUat.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.mesUat.entity.AlarmDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
@DS("mesuatdb")
public interface AlarmDao extends CrudDao<AlarmDTO> {
    //告警详情
    public AlarmDTO getAlarmDetail(@Param("toolId") String toolId, @Param("alarmId") String alarmId);
    //单台设备
    public List<AlarmDTO> getAlarmInfo(@Param("page_num") int page_num, @Param("page_size") int page_size, @Param("toolId") String toolId);
    //单个车间
    public List<AlarmDTO> getAlarmInfoList(@Param("page_num") int page_num, @Param("page_size") int page_size, @Param("device_type") String device_type);
    //已处理一楼
    public List<AlarmDTO> getAlarmInfoListOneFloor(@Param("page_num") int page_num, @Param("page_size") int page_size);
    //已处理二楼
    public List<AlarmDTO> getAlarmInfoListTwoFloor(@Param("page_num") int page_num, @Param("page_size") int page_size);
    //全场景
    public List<AlarmDTO> getAlarmInfoListAll(@Param("page_num") int page_num, @Param("page_size") int page_size);
    //查询当前未关闭的告警信息
    public List<AlarmDTO> getAlarmOpenList();
    //未处理一楼
    public List<AlarmDTO> getAlarmOpenOneFloor();
    //未处理二楼
    public List<AlarmDTO> getAlarmOpenTwoFloor();
    //未处理单个车间
    public List<AlarmDTO> getAlarmOpenOneTypeEqp(@Param("device_type") String device_type);
    //未处理单个设备
    public List<AlarmDTO> getAlarmOpenOneEqp(@Param("device_name") String device_name);
    //未处理全部

}
