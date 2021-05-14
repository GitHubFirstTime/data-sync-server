package com.rlc.rlcfmbapi.modules.report.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class EqpAcceptedGoodsRateDTO extends DataEntity<EqpAcceptedGoodsRateDTO> {
    //良品率
    private String fpyYc;
    //时间点
    private String dayTime;
    //排序
    private String sort;

    public String getFpyYc() {
        return fpyYc;
    }

    public void setFpyYc(String fpyYc) {
        this.fpyYc = fpyYc;
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
        return "EqpAcceptedGoodsRateDTO{" +
                "fpyYc='" + fpyYc + '\'' +
                ", dayTime='" + dayTime + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
