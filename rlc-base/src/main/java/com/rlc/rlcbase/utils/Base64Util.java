package com.rlc.rlcbase.utils;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
//import sun.misc.*;

public class Base64Util {
	protected static Logger logger = LoggerFactory.getLogger(Base64Util.class);
	static Base64.Decoder decoder = Base64.getDecoder();
    static Base64.Encoder encoder = Base64.getEncoder();
	// 加密
	@SuppressWarnings("restriction")
	public static String ENCODE(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = encoder.encodeToString(b);
		}
		return s;
	}

	// 解密
	@SuppressWarnings("restriction")
	public static String DECODE(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			try {
//				b = decoder.decode(s);
				b = Base64.getMimeDecoder().decode(s);
				result = new String(b, "UTF-8");
				logger.info("getBase64 result1：{}",result);
			} catch (Exception e) {
				logger.error("getBase64 错误：",e);
			}
		}
		logger.info("getBase64 result2：{}",result);
		return result;
	}

	public static void main(String[] args) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("appId", "DDM8001912-09648");
		paramsJson.put("sn", "4f4Cf42fC33f-bED6-8nd6En-9f9Gf92fG26f");
		System.out.printf("adada:%s",ENCODE(paramsJson.toString()));
//		System.out.println(getBase64("eyJjbWQiOiJvcGVuX2Rvb3IiLCJzdWNjZXNzIjoxLCJndWlkIjoiRERNNDAwMTkxMS0wMjc4OSIsIm1lc3NhZ2UiOiIiLCJzbiI6IjRmNDFDYTIyYWFDYS03QjduLUVFZG41RS05ZjkxR2cyMmdnR2ciLCJ2ZXJzaW9uIjoiOC4xLjAuNCJ9"));
//		System.out.println(JSONObject.fromObject(Encodes.unescapeHtml("{\"id\":\"23\",\"image_url\":\"\\u0014\",\"error\":\"无图片\"}")).get("id"));
	}
}
