package com.rlc.rlcfmbapi.modules.stereoLayer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class AlarmInfoResponse {
    @JsonProperty("alarm_type")
    private String alarm_type;

    @JsonProperty("alarm_status")
    private String alarm_status;

    @JsonProperty("alarm_level")
    private String alarm_level;

    @JsonProperty("alarm_reason_code")
    private String alarm_reason_code;

    @JsonProperty("alarm_time")
    private String alarm_time;

    @JsonProperty("alarm_description")
    private String alarm_description;

    @JsonProperty("alarm_id")
    private String alarm_id;

    @JsonProperty("device_type")
    private String device_type;

    @JsonProperty("device_type_name")
    private String device_type_name;

    @JsonProperty("device_id")
    private String device_id;

    @JsonProperty("device_name")
    private String device_name;
}
