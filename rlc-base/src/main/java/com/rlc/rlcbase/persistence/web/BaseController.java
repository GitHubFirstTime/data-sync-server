package com.rlc.rlcbase.persistence.web;

import com.rlc.rlcbase.utils.ServletUtils;
import com.rlc.rlcbase.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

public abstract class BaseController {
    /**
     * 日志对象
     */
    protected Logger logger =  LogManager.getLogger(getClass());
    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected void debug(String msg) {
        logger.debug(msg);
    }
    protected void info(String msg) {
        logger.info(msg);
    }
    protected void error(String msg) {
        logger.error(msg);
    }

    protected void error(Exception e) {
        error(e.getMessage(), e);
    }

    protected void error(String msg, Exception e) {
        logger.error(e.getMessage(), e);
    }
    /**
     * 获取request
     */
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession()
    {
        return getRequest().getSession();
    }
    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }
}