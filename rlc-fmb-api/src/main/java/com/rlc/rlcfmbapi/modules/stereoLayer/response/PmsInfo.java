package com.rlc.rlcfmbapi.modules.stereoLayer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rlc.rlcfmbapi.modules.stereoLayer.response.BaseInfo;
import lombok.Data;

/**
 * 设备维护记录
 * */
@Data
@JsonIgnoreProperties
public class PmsInfo extends BaseInfo {

    @JsonProperty("maintenance_time")
    private String maintenanceTime;

    @JsonProperty("next_maintenance_time")
    private String nextMaintenanceTime;

    @JsonProperty("maintenance_type")
    private String maintenanceType;

    @JsonProperty("maintenance_material")
    private String maintenanceMaterial;

}
