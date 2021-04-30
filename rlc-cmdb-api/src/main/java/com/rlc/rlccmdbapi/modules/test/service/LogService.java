/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.rlccmdbapi.modules.test.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcbase.utils.DateUtils;
import com.rlc.rlccmdbapi.modules.test.dao.LogDao;
import com.rlc.rlccmdbapi.modules.test.entity.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 日志Service
 * @author xx
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {

	/*
	 * @Autowired private LogDao logDao;
	 */
	@DS("fmbdb")
	public Page<Log> findPage(Page<Log> page, Log log) {

		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}

		return super.findPage(page, log);

	}
	
	/**
	 * 删除全部数据
	 * @param
	 */
	@Transactional(readOnly = false)
	public void empty(){
		
		dao.empty();
	}

}
