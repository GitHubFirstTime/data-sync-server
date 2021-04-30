/**
 * Project Name:fmb
 * File Name:HttpHelper.java
 * Package Name:com.jeeplus.component
 * Date:2020年7月1日下午3:08:03
 * Copyright (c) 2020, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;

/**
 * ClassName:HttpHelper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2020年7月1日 下午3:08:03 <br/>
 * 
 * @author RLC_ZYC
 * @version
 * @since JDK 1.8
 * @see
 */
public class HttpHelper {
	protected static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	/**
	 * getBodyString: 讀取[body]數據
	 * 
	 * @author RLC_ZYC
	 * @param request
	 * @return
	 * @throws IOException
	 * @return String
	 * @since JDK 1.8
	 */
	public static String getBodyString(HttpServletRequest request) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			request.setCharacterEncoding("UTF-8");
			inputStream = request.getInputStream();
			// reader = request.getReader();
			byte[] check = new byte[1024];
			// if(inputStream.read(check) != -1) {
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println("line:" + line);
				sb.append(line);
			}
			// }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("getBodyString_inputStream.close()", e);
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("getBodyString_reader.close()", e);
				}
			}
		}
		System.out.println("sb.toString():" + sb.toString());
		return sb.toString().trim();
	}

	public static byte[] getBody(HttpServletRequest request) throws IOException {
		InputStream inputStream = null;
		byte[] buf = new byte[2048];
		try {
			inputStream = request.getInputStream();
			int len = 0;
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			while ((len = bufferedInputStream.read(buf)) != -1) {
				byteArrayOutputStream.write(buf, 0, len);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return buf;
	}
}
