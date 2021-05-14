package com.rlc.rlccmdbapi.modules.cmdb.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlccmdbapi.modules.cmdb.entity.User;

/**
 * @author rlc_zyc
 * @version 1.0
 * @description: TODO
 * @date 2021/5/6 18:36
 */
@MyBatisDao
public interface UserDao  extends CrudDao<User> {
}
