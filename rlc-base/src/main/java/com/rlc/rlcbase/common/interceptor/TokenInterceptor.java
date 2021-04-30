/**
 * Project Name:
 * File Name:TokenInterceptor.java
 * Package Name:com.rlc.modules.fmb.interceptor
 * Date:2020年7月1日下午3:02:51
 * Copyright (c) 2020, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.common.interceptor;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rlc.rlcbase.config.HttpHelper;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlcbase.security.Digests;
import com.rlc.rlcbase.security.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:TokenInterceptor <br/>
 * Function: 令牌拦截验证验证
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2019年9月9日 下午4:52:16 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
	Gson gson = new GsonBuilder().create();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String methodName = ((HandlerMethod) handler).getMethod().getName();
		boolean bool = false;
		response.setCharacterEncoding("utf-8");
		if(java.util.Objects.equals("POST", request.getMethod())) {//post请求
			log.info("进入令牌拦截器，请求方法为：{}，请求方式POST",methodName);
			Map<String, Object> resultMap = Maps.newHashMap();
			String body = "";
		    body = HttpHelper.getBodyString(request);
			String appId = "";//jsonObject.getString("appId");
			Enumeration<?> enum1 = request.getHeaderNames();//获取请求头
	        while (enum1.hasMoreElements()) {
	            String key = (String) enum1.nextElement();
	            if(java.util.Objects.equals("x-guid", key)) {
	            	appId = request.getHeader(key);
	            	break;
	            }
	        }
	        String authorizationStr = "";
	        /*while (enum1.hasMoreElements()) {
	            String key = (String) enum1.nextElement();
	            if(java.util.Objects.equals("Authorization", key)) {
	            	authorizationStr = request.getHeader(key);
	            	break;
	            }
	        }*/
	        authorizationStr = request.getHeader("Authorization");
	        if(StringUtils.isNotBlank(authorizationStr)) {
	        	log.info("authorizationStr___"+authorizationStr);
				String signContent = null;//签名内容
				try {
					signContent = authorizationStr.split(" ")[1];
				} catch (Exception e) {
					log.error("authorizationStr",e);
					if (e instanceof ArrayIndexOutOfBoundsException){
						Map<String, Object> resultMap0 = Maps.newHashMap();
						ResultData stereoResult = ResultData.ERROR().message("無签名信息").code("400");
						responseMessage(response, response.getWriter(), gson.toJson(stereoResult));
					}
				}
				String jwt=null;
				try {
					jwt = authorizationStr.split(" ")[2];
					log.info(methodName+"方法的jwt:{}",jwt);
				} catch (Exception e) {
					log.error("jwt 认证失败,缺少令牌");
	        		Map<String, Object> resultMap1 = Maps.newHashMap();
	        		resultMap1.put("code", 401);
	        		resultMap1.put("msg", "ERROR_MISSING_JWT");
	        		resultMap1.put("desc", "jwt 认证失败,缺少令牌");
	        		resultMap1.put("data", new JSONObject());
		        	responseMessage(response, response.getWriter(), resultMap1);
				}
	        	boolean jwtBool = false;
	        	Map<String,Object> validateTokenMap = Maps.newHashMap();
	        	if(StringUtils.isNotBlank(jwt)) {
	        		validateTokenMap = validateToken(jwt, appId, bool, request, response);
	        		jwtBool = (boolean) validateTokenMap.get("success");
	        	}
	        	if(jwtBool) {//令牌通过
	        		if(StringUtils.isNotBlank(signContent)) {
	        			if(StringUtils.isNotBlank(body)&&!"null".equals(body)&&!"NULL".equals(body)) {
	        		    	JSONObject bodyJSONObject = JSONObject.fromObject(body);
	        		    	log.info("TokenInterceptor_body不为空时："+replaceEnter(bodyJSONObject.toString()));
	        		    	String sign = Digests.encoderByMd5(appId+"\n"+replaceEnter(bodyJSONObject.toString())+"\n"+"fmb-api");
	        		    	if(Objects.equal(sign, signContent)) {
	        		    		bool = true;
	        		    		log.info("签名验证通过");
	        		    	}else {
	        		    		log.error("TokenInterceptor签名错误，接收：{},生成：{}",signContent,sign);
	        		    		resultMap.put("code", 400);
	    	        			resultMap.put("msg", "");
	    	        			resultMap.put("desc", "签名错误");
	    	        			resultMap.put("data", new JSONObject());
	    	        			responseMessage(response, response.getWriter(), resultMap);
	        		    	}
	        			}else {
//	        				JSONObject bodyJSONObject = JSONObject.fromObject("{}");
	        		    	String sign = Digests.encoderByMd5(appId+"\n"+""+"\n"+"fmb-api");
	        		    	if(Objects.equal(sign, signContent)) {
	        		    		bool = true;
	        		    		log.info("签名验证通过");
	        		    	}else {
	        		    		log.error("TokenInterceptor body is blank 时 签名错误，接收：{},生成：{}",signContent,sign);
	        		    		resultMap.put("code", 400);
	    	        			resultMap.put("msg", "");
	    	        			resultMap.put("desc", "签名错误");
	    	        			resultMap.put("data", new JSONObject());
	    	        			responseMessage(response, response.getWriter(), resultMap);
	        		    	}
//	        				log.error("缺少参数");
//	        				resultMap.put("code", 10400);
//	        	        	resultMap.put("msg", "ERROR_MISSING_PARAMS");
//	        	        	resultMap.put("desc", "缺少参数");
//	        	        	resultMap.put("data", new JSONObject());
//	        	        	responseMessage(response, response.getWriter(), resultMap);
	        			}
//					
	        		}else {
	        			log.error("无签名信息，签名错误");
	        			resultMap.put("code", 400);
	        			resultMap.put("msg", "");
	        			resultMap.put("desc", "签名错误");
	        			resultMap.put("data", new JSONObject());
	        			responseMessage(response, response.getWriter(), resultMap);
	        		}
	        	}else {//无令牌信息
	        		log.error("jwt 认证失败");
	        		Map<String, Object> resultMap1 = Maps.newHashMap();
	        		resultMap1.put("success", false);
	        		resultMap1.put("code", 401);
	        		resultMap1.put("msg", "ERROR_MISSING_JWT");
	        		resultMap1.put("desc", "jwt 认证失败");
	        		resultMap1.put("data", new JSONObject());
		        	responseMessage(response, response.getWriter(), resultMap1);
	        	}
	        }else {
	        	log.error("无Authorization信息");
	        	resultMap.put("success", false);
	        	resultMap.put("code", 400);
	        	resultMap.put("msg", "无Authorization信息");
	        	resultMap.put("desc", "无Authorization信息");
	        	resultMap.put("data", new JSONObject());
	        	responseMessage(response, response.getWriter(), resultMap);
	        }
			
		   /* if(StringUtils.isNotBlank(body)) {
		    	JSONObject bodyJSONObject = JSONObject.fromObject(body);
		    	if(!bodyJSONObject.containsKey("token")) {
//		    		Map<String, Object> resultMap = Maps.newHashMap();
		    		resultMap.put("success", false);
		    	    resultMap.put("data", 4);
		    	    resultMap.put("message", "无令牌");
		    		responseMessage(response, response.getWriter(), resultMap);
		    	}
		    	if(!bodyJSONObject.containsKey("appId")) {
//		    		Map<String, Object> resultMap = Maps.newHashMap();
		    		resultMap.put("success", false);
		    	    resultMap.put("data", 5);
		    	    resultMap.put("message", "缺少设备识别码");
		    	    responseMessage(response, response.getWriter(), resultMap);
		    	}
		    	if(bodyJSONObject.containsKey("token")&&bodyJSONObject.containsKey("appId")) {
		    		String token = bodyJSONObject.getString("token");
					String appId = bodyJSONObject.getString("appId");
					Map<String,Object> validateTokenMap = validateToken(token, appId, bool, request, response);
					bool = (boolean) validateTokenMap.get("success");
		    	}
		    }*/
		}else {//get请求
			log.info("进入tokenintercept--get");
			log.info("进入令牌拦截器，请求方法为：{}，请求方式GET",methodName);
			String token = request.getParameter("token");
			String appId = request.getParameter("appId");
			String authorizationStr = request.getHeader("Authorization");
			log.info("GET请求方式之签名信息{}",authorizationStr);
			Map map = request.getParameterMap();
			JSONObject getMethodParamsJsonObject = new JSONObject();
			if (map != null && !map.isEmpty()) {
	            Set<String> keySet = map.keySet();
	            for (String key : keySet) {               
	                    String[] values = (String[]) map.get(key);
	                    for (String value : values) {
	                    	log.info("> " + key + "=" + value);
	                    }              
	            }
	        }
//			String sign = Digests.encoderByMd5(appId+"\n"+replaceEnter(bodyJSONObject.toString())+"\n"+"fmb-api");
//			bool = true;
			Map<String,Object> validateTokenMap = validateToken(token, appId, bool, request, response);
			bool = (boolean) validateTokenMap.get("success");
			log.info("令牌通过");
		}
		return bool;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
    //请求不通过，返回错误信息给客户端
    private void responseMessage(HttpServletResponse response, PrintWriter out, Map<String, Object> resultMap) {
        response.setContentType("application/json; charset=utf-8");  
        JSONObject json = JSONObject.fromObject(resultMap);
        out.print(json);
        out.flush();
        out.close();
    }
	private void responseMessage(HttpServletResponse response, PrintWriter out, String json) {
		response.setContentType("application/json; charset=utf-8");
		out.print(json);
		out.flush();
		out.close();
	}
    /**
     * validateToken:
     * 令牌验证方法.<br/>
     * @author RLC_ZYC
     * @param token
     * @param appId
     * @param bool
     * @param request
     * @param response
     * @return Map<String,Object>
     * @since JDK 1.8
     */
    private Map<String, Object> validateToken(String token,String appId,boolean bool,HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> resultMap = Maps.newHashMap();
    	try {
			if(StringUtils.isNotBlank(token)&&StringUtils.isNotBlank(appId)) {
				String objectId = appId;
				resultMap = TokenUtil.validateToken(token);
				String markData = "";
				if((boolean) resultMap.get("success")) {
					if(resultMap.containsKey("markData")) {
						markData = String.valueOf(resultMap.get("markData"));
						if(Objects.equal(markData, objectId)) {
							resultMap.put("success", true);
							bool = true;
							log.info("validateToken:令牌验证通过");
						}else {
							bool = false;
							resultMap.put("success", false);
							resultMap.put("data", 3);
							resultMap.put("message", "令牌与设备不匹配");
							log.error("validateToken:令牌与设备不匹配");
						}
					}
				}else {
					if(java.util.Objects.equals(String.valueOf(resultMap.get("data")), "1")) {
						Map<String, Object> resultMap1 = Maps.newHashMap();
		        		resultMap1.put("code", 401);
		        		resultMap1.put("msg", "ERROR_MISSING_JWT");
		        		resultMap1.put("desc", "jwt过期，认证失败");
		        		resultMap1.put("data", new JSONObject());
		        		log.error("validateToken:jwt过期，认证失败,");
			        	responseMessage(response, response.getWriter(), resultMap1);
					}else {
						Map<String, Object> resultMap1 = Maps.newHashMap();
		        		resultMap1.put("code", 401);
		        		resultMap1.put("msg", "ERROR_MISSING_JWT");
		        		resultMap1.put("desc", "jwt 认证失败");
		        		resultMap1.put("data", new JSONObject());
		        		log.error("validateToken:jwt 认证失败");
			        	responseMessage(response, response.getWriter(), resultMap1);
					}
				}
			}else {
				bool = false;
				resultMap.put("success", false);
//				if(StringUtils.isBlank(token)) {
//					resultMap.put("message", "无令牌");
//				}
//				if(StringUtils.isBlank(appId)) {
					resultMap.put("message", "无令牌");
//				}
				log.error("validateToken:无令牌");
				responseMessage(response, response.getWriter(), resultMap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return resultMap;
    }
    /**
     * replaceEnter:
     * 替换回车换行
     * @author RLC_ZYC
     * @param str
     * @return String
     * @since JDK 1.8
     */
    public static String replaceEnter(String str){
        String reg ="[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
}