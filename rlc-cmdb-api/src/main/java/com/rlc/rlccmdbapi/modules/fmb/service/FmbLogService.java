/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.rlccmdbapi.modules.fmb.service;

import com.rlc.rlcbase.common.IdGen;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcbase.utils.DateUtils;
import com.rlc.rlccmdbapi.modules.cmdb.dao.LogDao;
import com.rlc.rlccmdbapi.modules.fmb.dao.FmbLogDao;
import com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog;
import com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 日志Service
 * @author xx
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class FmbLogService extends CrudService<FmbLogDao, Fmblog> {

	 @Autowired
	 @Lazy
	 @DS("cmdbdb")
	 private LogDao logDao;
	@DS("fmbdb")
	public Page<Fmblog> findPage(Page<Fmblog> page, Fmblog fmblog) {

		// 设置默认时间范围，默认当前月
		if (fmblog.getBeginDate() == null){
			fmblog.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (fmblog.getEndDate() == null){
			fmblog.setEndDate(DateUtils.addMonths(fmblog.getBeginDate(), 1));
		}

		try {
			return super.findPage(page, fmblog);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 删除全部数据
	 * @param
	 */
	@Transactional(readOnly = false)
	public void empty(){
		
		dao.empty();
	}
	public void saveAll() throws Exception{
		com.rlc.rlccmdbapi.modules.cmdb.entity.Log log = new com.rlc.rlccmdbapi.modules.cmdb.entity.Log();
		log.setId(IdGen.uuid());
		log.setIsNewRecord(true);
		log.setType("1");
		log.setTitle("cmdb事务测试--log2");

		com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog fmbLog = new com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog();
		fmbLog.setTitle("fmb事务测试--log2");
		fmbLog.setId(IdGen.uuid());
		fmbLog.setIsNewRecord(true);
		fmbLog.setType("1");
		logDao.insert(log);
		int a=0;
		int x = 1/a;
		dao.insert(fmbLog);
	}
	@DS("fmbdb")
	public void saveFmbLog(){
		com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog fmbLog = new com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog();
		fmbLog.setTitle("fmb事务测试--log2");
		fmbLog.setId(IdGen.uuid());
		fmbLog.setIsNewRecord(true);
		fmbLog.setType("1");
		dao.insert(fmbLog);
	}
}
