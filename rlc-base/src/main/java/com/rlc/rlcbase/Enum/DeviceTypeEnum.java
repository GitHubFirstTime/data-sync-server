package com.rlc.rlcbase.Enum;

public enum DeviceTypeEnum {
    NORMAL("0","正常状态"),
    FROZEN("1","冻结"),
    BAN("2","禁用"),
    MUTE("3","禁言"),
    SENSITIVE("4","敏感用户"),
    DYF("5","这是创作者的");
    private String typeCode;
    private String typeString;


    public static DeviceTypeEnum getUserTypeByCode(String typeCode){
        for (DeviceTypeEnum stateEnum: values()){
            if(stateEnum.getTypeCode() == typeCode){
                return stateEnum;
            }

        }
        return null;
    }

    /* Constructor */
    DeviceTypeEnum(String typeCode, String typeString) {
        this.typeCode = typeCode;
        this.typeString = typeString;
    }


    /* Getter and Setter */
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }
}
