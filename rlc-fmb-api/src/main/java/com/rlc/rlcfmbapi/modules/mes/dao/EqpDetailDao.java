package com.rlc.rlcfmbapi.modules.mes.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface EqpDetailDao extends CrudDao<EqpDetailDTO> {
    /*拿到一条设备信息*/
    EqpDetailDTO getEQPDetailsOne(@Param("device_name") String device_name);

    /*拿到某一类设备信息*/
    List<EqpDetailDTO> getEQPDetailsList(@Param("eqpType") String eqpType);

    /*一台设备运行状态*/
    EqpDetailDTO getEqpRunStatusInfo(@Param("device_name") String device_name);

    /*运行状态汇总信息*/
    List<EqpDetailDTO> getEqpRunStatusStatistics(@Param("eqpType") String eqpType);

    /*单台设备运行状态汇总*/
    EqpDetailDTO getEqpRunStatusStatisticsOne(@Param("device_name") String device_name);
	
	/*设备容量信息*/
    EqpDetailDTO getEQPCapacity(@Param("eqpType") String eqpType);

    /*单台设备容量信息*/
    EqpDetailDTO getEQPCapacityOne(@Param("eqpType") String eqpType, @Param("device_name") String device_name);

    /*所有设备运行状态信息*/
    List<EqpDetailDTO> getAllEqpRunInfoList();

    /*设备剩余加工时间信息*/
    EqpDetailDTO getRemainingTime(@Param("device_name") String device_name);

    /*设备数量*/
    EqpDetailDTO getEqpTotalNumber(@Param("device_type") String device_type);

}
