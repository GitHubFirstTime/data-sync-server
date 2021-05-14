package com.rlc.rlcfmbapi.modules.mes.entity;

import com.rlc.rlcbase.persistence.DataEntity;

import java.util.List;
import java.util.Map;

/**
 * 机台dto
 */
public class EqpDTO extends DataEntity<EqpDTO> {
    private String eqpName;//名称
    private String eqpType;//mes机台类型
    private String eqpTypeName;//mes机台类型
    private String eqpArea;//设备物理位置
    private String eqpRunState;//设备运行状态
    private String eqpDispatchMode;//派工模式
    private String eqpControlMode;//控制模式
    /**
     * 运送模式
     */
    private String eqpTransportMode;

    private String floor;//楼层
    private String recipeName;//配方名称
//    private String jobInTime;//进机台时间
//    private String lotId;//晶棒id

    private List<Map<String,Object>> currentLotList;//当前机台的晶棒信息
    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public String getEqpTypeName() {
        return eqpTypeName;
    }

    public void setEqpTypeName(String eqpTypeName) {
        this.eqpTypeName = eqpTypeName;
    }

    public String getEqpArea() {
        return eqpArea;
    }

    public void setEqpArea(String eqpArea) {
        this.eqpArea = eqpArea;
    }

    public String getEqpRunState() {
        return eqpRunState;
    }

    public void setEqpRunState(String eqpRunState) {
        this.eqpRunState = eqpRunState;
    }

    public String getEqpDispatchMode() {
        return eqpDispatchMode;
    }

    public void setEqpDispatchMode(String eqpDispatchMode) {
        this.eqpDispatchMode = eqpDispatchMode;
    }

    public String getEqpControlMode() {
        return eqpControlMode;
    }

    public void setEqpControlMode(String eqpControlMode) {
        this.eqpControlMode = eqpControlMode;
    }

    public String getEqpTransportMode() {
        return eqpTransportMode;
    }

    public void setEqpTransportMode(String eqpTransportMode) {
        this.eqpTransportMode = eqpTransportMode;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<Map<String, Object>> getCurrentLotList() {
        return currentLotList;
    }

    public void setCurrentLotList(List<Map<String, Object>> currentLotList) {
        this.currentLotList = currentLotList;
    }

//    public String getJobInTime() {
//        return jobInTime;
//    }
//
//    public void setJobInTime(String jobInTime) {
//        this.jobInTime = jobInTime;
//    }

//    public String getLotId() {
//        return lotId;
//    }
//
//    public void setLotId(String lotId) {
//        this.lotId = lotId;
//    }
}