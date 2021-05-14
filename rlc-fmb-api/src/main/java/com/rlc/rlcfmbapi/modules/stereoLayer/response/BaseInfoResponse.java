package com.rlc.rlcfmbapi.modules.stereoLayer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 所有response对象的基类
 * @author gf
 */
@JsonIgnoreProperties
@Data
public class BaseInfoResponse {

    /**
     * 设备类型
     */
    @JsonProperty("device_type")
    private String deviceType;

    /**
     * 设备类型名称
     */
    @JsonProperty("device_type_name")
    private String deviceTypeName;

    /**
     * 设备标识
     */
    @JsonProperty("device_id")
    private String deviceId;

    /**
     * 设备名称
     */
    @JsonProperty("device_name")
    private String deviceName;
}
