/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.rlccmdbapi.modules.fmb.dao;


//import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlccmdbapi.modules.fmb.entity.Fmblog;

/**
 * 日志DAO接口
 * @author xx
 * @version 2014-05-16
 */
@DS("fmbdb")
@MyBatisDao
public interface FmbLogDao extends CrudDao<Fmblog> {

	public void empty();
}
