package com.rlc.rlcfmbapi.modules.stereoLayer.common.result;

/**
 * TODO
 * ClassName:StereoResult <br/>
 * Function: 3D返回值 ADD FUNCTION. <br/>
 * Reason:	 3D返回值 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/18 14:10
 * @since JDK 1.8
 */
public class StereoResult {
    public String return_code;
    public String message;
    public String content;

    private StereoResult() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static StereoResult SUCCESS(){
        StereoResult result = new StereoResult();
        result.setReturn_code(StereoResultEnum.SUCCESS.getReturn_code());
        result.setMessage(StereoResultEnum.SUCCESS.getMessage());
        return result;
    }
    public static StereoResult ERROR(){
        StereoResult result = new StereoResult();
        return result;
    }
    public StereoResult CONTENT(String content){
        this.setContent(content);
        return this;
    }
    public StereoResult CODE(String return_code){
        this.setReturn_code(return_code);
        return this;
    }
    public StereoResult MESSAGE(String message){
        this.setMessage(message);
        return this;
    }

    //未知错误
    public static StereoResult UNKNOWNERROR(){
        StereoResult result = new StereoResult();
        result.setReturn_code(StereoResultEnum.UNKNOWN_ERROR.getReturn_code());
        result.setMessage(StereoResultEnum.UNKNOWN_ERROR.getMessage());
        return result;
    }

    //缺少参数
    public static StereoResult MISSINGPARAMERROR(){
        StereoResult result = new StereoResult();
        result.setReturn_code(StereoResultEnum.MISSING_PARAM_ERROR.getReturn_code());
        result.setMessage(StereoResultEnum.MISSING_PARAM_ERROR.getMessage());
        return result;
    }

    //参数错误
    public static StereoResult PARAMERROR(){
        StereoResult result = new StereoResult();
        result.setReturn_code(StereoResultEnum.PARAM_ERROR.getReturn_code());
        result.setMessage(StereoResultEnum.PARAM_ERROR.getMessage());
        return result;
    }
}