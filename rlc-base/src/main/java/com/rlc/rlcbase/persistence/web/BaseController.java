package com.rlc.rlcbase.persistence.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}