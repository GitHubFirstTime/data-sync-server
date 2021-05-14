package com.rlc.rlcfmbapi.modules.stereoLayer.common.result;

/**
 * TODO
 * ClassName:StereoResultEnum <br/>
 * Function: 3D返回值枚举类 ADD FUNCTION. <br/>
 * Reason:	 3D返回值枚举类 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/18 14:19
 * @since JDK 1.8
 */
public enum StereoResultEnum {
    SUCCESS("200","成功"),
    UNKNOWN_ERROR("20001","未知错误"),
    MISSING_PARAM_ERROR("10400","缺少参数"),
    PARAM_ERROR("20003","参数错误");
    private String return_code;
    private String message;

    StereoResultEnum(String return_code, String message) {
        this.return_code = return_code;
        this.message = message;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
