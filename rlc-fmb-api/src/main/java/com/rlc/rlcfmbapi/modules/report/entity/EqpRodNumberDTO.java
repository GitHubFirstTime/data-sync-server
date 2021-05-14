package com.rlc.rlcfmbapi.modules.report.entity;


import com.rlc.rlcbase.persistence.DataEntity;

public class EqpRodNumberDTO extends DataEntity<EqpRodNumberDTO> {
    //设备类型
    private String eqpType;
    //晶棒数量
    private String rodNumber;

    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public String getRodNumber() {
        return rodNumber;
    }

    public void setRodNumber(String rodNumber) {
        this.rodNumber = rodNumber;
    }

    @Override
    public String toString() {
        return "EqpRodNumberDTO{" +
                "eqpType='" + eqpType + '\'' +
                ", rodNumber='" + rodNumber + '\'' +
                '}';
    }
}
