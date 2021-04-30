/**
 * Project Name:fmb
 * File Name:TokenUtil.java
 * Package Name:com.jeeplus.common.security
 * Date:2020年7月1日下午3:02:51
 * Copyright (c) 2020, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;
import com.rlc.rlcbase.utils.DateUtils;
import com.rlc.rlcbase.utils.Encodes;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Map;

/**
 * ClassName:TokenUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2020年7月1日 下午3:02:51 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class TokenUtil {
	static Logger logger =  LogManager.getLogger(TokenUtil.class);
	static String secret = "XX#$%()(#*!()!KL<><MQLMNQNQJQK gsinnbhjndrow32234545fdf>?N<:{LWPW";
	/**
    * 签发令牌
    * @param id 设备id
    * @param ttlMillis 令牌有效期-分钟
    * @return  String
    */
   public static String createToken(String id, int ttlMillis) {
   	 String token = "";
   	 try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
//			final Map<String, Object> headerClaims = Maps.newHashMap();
//			headerClaims.put(key, value)
			token = JWT.create()
			        .withIssuer("rlc")
			        .withIssuedAt(new Date())
//			        .withHeader(headerClaims)
			        .withSubject(id)
					.withExpiresAt(DateUtils.addMinutes(new Date(), ttlMillis))
			        .sign(algorithm);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("非法参数异常,令牌创建失败",e);
		} catch (JWTCreationException e) {
			logger.error("令牌创建失败",e);
		}
   	 return token;
   }
	/**
    * 验证token
    * @param token
    * @return Map<String,Object> 
    * 			key ---success: true(成功)/false(失败);data: 0(成功)/1(失败--令牌过期)/2(失败--无效令牌);message:提示语;
    *                                   当success=true时 会有markData--设备id
    */
	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> resultMap = Maps.newHashMap();
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("rlc").build(); // Reusable verifier instance
			DecodedJWT jwt = verifier.verify(token);
			String payload = jwt.getPayload();
			String payloadE = Encodes.decodeBase64String(payload);
			// payload = Base64Util.getBase64(payload);
			if (StringUtils.isNotBlank(payloadE)) {
				JSONObject payloadJSONObject = JSONObject.fromObject(payloadE);
				if (payloadJSONObject.containsKey("sub")) {
					String markData = String.valueOf(payloadJSONObject.get("sub"));
					resultMap.put("markData", markData);
				}
			}
			Date date = jwt.getExpiresAt();
			System.out.println("---" + DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss") + " ____:" + payload);
			resultMap.put("success", true);
			resultMap.put("code", 0);
			resultMap.put("data", 0);
			resultMap.put("message", "成功");
			logger.info("有效");
		} catch (TokenExpiredException e) {
			resultMap.put("success", false);
			resultMap.put("code", 1);
			resultMap.put("data", 1);
			resultMap.put("message", "令牌过期");
			logger.error("令牌过期");
		} catch (JWTVerificationException exception) {
			resultMap.put("success", false);
			resultMap.put("code", 2);
			resultMap.put("data", 2);
			resultMap.put("message", "无效令牌");
			logger.error("无效令牌");
		}
		return resultMap;
	}
   public static void main(String[] args) {
		String t = createToken("DDM4001911-02789", 30);
		System.out.println("令牌："+t);
		String tt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1ODQxNjA1OTEsInN1YiI6IkRETTQwMDE5MTEtMDI3ODkiLCJpc3MiOiJybGMiLCJpYXQiOjE1ODQxNTg3OTF9.PT3iDdzXitCqKr4O3ms294Q53npn-WFnqbsymxk8FE4";
		Map<String,Object> resultMap = validateToken(tt);
		System.out.println("验证："+resultMap.get("message"));
	}
}

