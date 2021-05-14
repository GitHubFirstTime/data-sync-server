package com.rlc.rlcfmbapi.modules.report.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class EqpOutputDTO extends DataEntity<EqpOutputDTO> {
    //产量
    private String qty;
    //车间类型
    private String trackInLocation;
    //时间点
    private String dayTime;
    //排序
    private String sort;

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTrackInLocation() {
        return trackInLocation;
    }

    public void setTrackInLocation(String trackInLocation) {
        this.trackInLocation = trackInLocation;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "EqpOutputDTO{" +
                "qty='" + qty + '\'' +
                ", trackInLocation='" + trackInLocation + '\'' +
                ", dayTime='" + dayTime + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
