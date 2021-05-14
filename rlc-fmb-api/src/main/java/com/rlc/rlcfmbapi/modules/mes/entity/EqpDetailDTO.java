package com.rlc.rlcfmbapi.modules.mes.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class EqpDetailDTO extends DataEntity<EqpDetailDTO> {
    private String eqpName;//名称
    private String eqpType;//mes机台类型
    private String eqpArea;//设备物理位置
    private String eqpRunState;//设备运行状态
    private String eqpDispatchMode;//派工模式
    private String eqpControlMode;//控制模式
    private String eqpTransportMode; //运送模式
    private String floor;//楼层
    private String recipeName;//配方名称
    private String currentCapacity;//当前容量
    private String totalCapacity;//总容量
    private String remainingTime;//剩余加工时间
    /*运行状态*/
    private String totalNumber; //设备数量

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

    public String getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(String currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public String getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public String toString() {
        return "EqpDetailDTO{" +
                "eqpName='" + eqpName + '\'' +
                ", eqpType='" + eqpType + '\'' +
                ", eqpArea='" + eqpArea + '\'' +
                ", eqpRunState='" + eqpRunState + '\'' +
                ", eqpDispatchMode='" + eqpDispatchMode + '\'' +
                ", eqpControlMode='" + eqpControlMode + '\'' +
                ", eqpTransportMode='" + eqpTransportMode + '\'' +
                ", floor='" + floor + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", currentCapacity='" + currentCapacity + '\'' +
                ", totalCapacity='" + totalCapacity + '\'' +
                ", remainingTime='" + remainingTime + '\'' +
                ", totalNumber='" + totalNumber + '\'' +
                '}';
    }
}
