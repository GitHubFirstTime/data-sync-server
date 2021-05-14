/**
 * Project Name:fmb
 * File Name:FmbEqpStatusHisDao.java
 * Package Name:com.rlc.modules.fmb.dao
 * Date:2021年1月7日下午2:18:47
 * Copyright (c) 2021, http://www.xxx.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcfmbapi.modules.fmb.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.fmb.entity.FmbEqpStatusHis;

import java.util.List;

/**
 * ClassName:FmbEqpStatusHisDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2021年1月7日 下午2:18:47 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@MyBatisDao
public interface FmbEqpStatusHisDao extends CrudDao<FmbEqpStatusHis> {
    public List<FmbEqpStatusHis> getNewStatusByEqp() throws Exception;
    //批量新增
    public void insertBatch(List<FmbEqpStatusHis> eqpStatusHisList) throws Exception;
    //批量更新
    public void updateBatch(List<FmbEqpStatusHis> eqpStatusHisList) throws Exception;
}

