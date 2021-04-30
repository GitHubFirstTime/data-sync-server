/**
 * Project Name:watchDog
 * File Name:RequestReaderHttpServletRequestWrapper.java
 * Package Name:com.jeeplus.common.utils
 * Date:2019年9月11日下午5:44:30
 * Copyright (c) 2019, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.config;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ClassName:RequestReaderHttpServletRequestWrapper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2019年9月11日 下午5:44:30 <br/>
 * 
 * @author RLC_ZYC
 * @version
 * @since JDK 1.8
 * @see
 */
public class RequestReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;

	public RequestReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {

		super(request);
//		body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
		body = HttpHelper.getBody(request);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
	}
}
