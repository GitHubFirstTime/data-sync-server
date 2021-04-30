/**
 * Project Name:fmbReport
 * File Name:ResultCodeEnum.java
 * Package Name:com.rlc.modules.report.common
 * Date:2020年8月18日下午2:03:55
 * Copyright (c) 2020, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.result;
/**
 * ClassName:ResultCodeEnum <br/>
 * 结果类枚举
 * Date:     2020年8月18日 下午2:03:55 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public enum ResultCodeEnum {
	SUCCESS(true,"20000","成功"),
    UNKNOWN_ERROR(false,"20001","未知错误"),
    MISSING_PARAM_ERROR(false,"10400","缺少参数"),
    PARAM_ERROR(false,"20003","参数错误");
    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private String code;
    // 响应信息
    private String message;
    
    ResultCodeEnum(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

