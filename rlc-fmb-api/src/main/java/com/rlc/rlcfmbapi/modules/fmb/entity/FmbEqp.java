package com.rlc.rlcfmbapi.modules.fmb.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class FmbEqp extends DataEntity<FmbEqp> {
    private String eqpCode;//机台id
    private String eqpName;//机台名称
    private String eqpType;//机台类型
    /**
     * 接管状态 0:未接管；1：已接管'
     */
    private String takeOver;
//    private String eqpValid;//机台有效性0:有效；1:无效
    /**
     * 控制模式
     */
    private String controlMode;
    /**
     * 派工模式
     */
    private String dispatchMode;
    /**
     * 运送模式
     */
    private String transportMode;
    /**
     * 机台状态
     */
    private String eqpState;

    private String mesState;//机台有效性0:有效；1:无效

    public String getEqpCode() {
        return eqpCode;
    }

    public void setEqpCode(String eqpCode) {
        this.eqpCode = eqpCode;
    }

    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public String getEqpType() {
        return eqpType;
    }

    public String getMesState() {
        return mesState;
    }

    public void setMesState(String mesState) {
        this.mesState = mesState;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

//    public String getEqpValid() {
//        return eqpValid;
//    }
//
//    public void setEqpValid(String eqpValid) {
//        this.eqpValid = eqpValid;
//    }

    public String getTakeOver() {
        return takeOver;
    }

    public void setTakeOver(String takeOver) {
        this.takeOver = takeOver;
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode;
    }

    public String getEqpState() {
        return eqpState;
    }

    public void setEqpState(String eqpState) {
        this.eqpState = eqpState;
    }

    public String getDispatchMode() {
        return dispatchMode;
    }

    public void setDispatchMode(String dispatchMode) {
        this.dispatchMode = dispatchMode;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }
}