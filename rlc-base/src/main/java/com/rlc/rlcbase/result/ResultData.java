/**
 * Project Name:fmbReport
 * File Name:ResultData.java
 * Package Name:com.rlc.modules.report.common
 * Date:2020年8月18日下午2:05:29
 * Copyright (c) 2020, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.result;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * ClassName:ResultData <br/>
 * 统一结果类
 * Date:     2020年8月18日 下午2:05:29 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ResultData {
	private Boolean success;

    private String code;

    private String message;

    private Map<String, Object> data = Maps.newHashMap();

    private Object result;
    // 构造器私有
	private ResultData() {
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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    // 通用返回成功
	public static ResultData OK() {
		ResultData r = new ResultData();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
	}
    // 通用返回失败，未知错误
    public static ResultData ERROR() {
        ResultData r = new ResultData();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }
    // 设置结果，形参为结果枚举
    public static ResultData setResult(ResultCodeEnum result) {
        ResultData r = new ResultData();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /**------------使用链式编程，返回类本身-----------**/

    // 自定义返回数据
    public ResultData data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }

    // 通用设置data
    public ResultData data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    // 自定义状态信息
    public ResultData message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public ResultData code(String code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public ResultData success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    // 自定义状态信息
    public ResultData result(Object result) {
        this.setResult(result);
        return this;
    }
}

