/**
 * Copyright &copy; 2015-2020 <a href="http://www.xx.org/">xx</a> All rights reserved.
 */
package com.rlc.rlccmdbapi.modules.cmdb.dao;


//import com.baomidou.dynamic.datasource.annotation.DS;
import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlccmdbapi.modules.cmdb.entity.Log;

/**
 * 日志DAO接口
 * @author xx
 * @version 2014-05-16
 */
//@DS("cmdbdb")
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

	public void empty();
}
