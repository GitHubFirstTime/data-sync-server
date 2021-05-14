package com.rlc.rlcfmbapi.modules.mes.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class CrystalBarInfoDTO extends DataEntity<CrystalBarInfoDTO> {
    /*晶棒信息*/
    private String appId; //晶棒ID
    private String orderNumber; //生产计划
    private String orderNumberColor; //生产计划颜色
    private String totalLength; //晶棒长度
    private String moldType;    //整拼类型
    private String trackInTime; //进机台时间
    private String trackInLocation; //所在设备位置

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumberColor() {
        return orderNumberColor;
    }

    public void setOrderNumberColor(String orderNumberColor) {
        this.orderNumberColor = orderNumberColor;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(String totalLength) {
        this.totalLength = totalLength;
    }

    public String getMoldType() {
        return moldType;
    }

    public void setMoldType(String moldType) {
        this.moldType = moldType;
    }

    public String getTrackInTime() {
        return trackInTime;
    }

    public void setTrackInTime(String trackInTime) {
        this.trackInTime = trackInTime;
    }

    public String getTrackInLocation() {
        return trackInLocation;
    }

    public void setTrackInLocation(String trackInLocation) {
        this.trackInLocation = trackInLocation;
    }
}
