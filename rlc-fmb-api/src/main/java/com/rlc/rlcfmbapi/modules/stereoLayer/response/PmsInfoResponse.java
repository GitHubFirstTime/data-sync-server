package com.rlc.rlcfmbapi.modules.stereoLayer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rlc.rlcfmbapi.modules.stereoLayer.response.BaseInfoResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备维护记录
 *
 * @author gf
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties
public class PmsInfoResponse extends BaseInfoResponse {

    /**
     * 维护时间
     */
    @JsonProperty("maintenance_time")
    private String maintenanceTime;

    /**
     * 下一次维护时间
     */
    @JsonProperty("next_maintenance_time")
    private String nextMaintenanceTime;

    /**
     * 维护类型
     */
    @JsonProperty("maintenance_type")
    private String maintenanceType;

    /**
     * 维护材料
     */
    @JsonProperty("maintenance_material")
    private String maintenanceMaterial;

    /**
     * 维修标识
     */
    @JsonProperty("pms_id")
    private String psmId;
}
