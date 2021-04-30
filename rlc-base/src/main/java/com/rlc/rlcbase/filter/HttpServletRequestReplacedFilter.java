/**
 * Project Name:watchDog
 * File Name:HttpServletRequestReplacedFilter.java
 * Package Name:com.jeeplus.common.filter
 * Date:2019年9月11日下午5:49:12
 * Copyright (c) 2019, http://www.rlctech.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcbase.filter;


import com.rlc.rlcbase.config.RequestReaderHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * ClassName:HttpServletRequestReplacedFilter <br/>
 * HttpServletRequest处理
 * Date: 2019年9月11日 下午5:49:12 <br/>
 * 
 * @author RLC_ZYC
 * @version
 * @since JDK 1.8
 * @see
 */
public class HttpServletRequestReplacedFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("进入HttpServletRequestReplacedFilter的init方法");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			requestWrapper = new RequestReaderHttpServletRequestWrapper((HttpServletRequest) request);
		} 
//		if(request instanceof MultipartHttpServletRequest) {
//			requestWrapper = new RequestReaderHttpServletRequestWrapper((MultipartHttpServletRequest) request);
//		}
		// 获取请求中的流信息，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
		// 在chain.doFiler方法中传递新的request对象
		if (requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}
	}

	@Override
	public void destroy() {

		// TODO Auto-generated method stub

	}

}
