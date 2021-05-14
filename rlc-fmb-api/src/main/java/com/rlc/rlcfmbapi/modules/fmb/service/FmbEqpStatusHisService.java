/**
 * Project Name:fmb
 * File Name:FmbEqpStatusHisService.java
 * Package Name:com.rlc.modules.fmb.service
 * Date:2021年1月7日下午2:19:50
 * Copyright (c) 2021, http://www.xxx.com/ All Rights Reserved.
 *
*/

package com.rlc.rlcfmbapi.modules.fmb.service;

import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.fmb.dao.FmbEqpStatusHisDao;
import com.rlc.rlcfmbapi.modules.fmb.entity.FmbEqpStatusHis;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * ClassName:FmbEqpStatusHisService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2021年1月7日 下午2:19:50 <br/>
 * @author   RLC_ZYC
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Service
@Transactional(readOnly = true)
public class FmbEqpStatusHisService extends CrudService<FmbEqpStatusHisDao, FmbEqpStatusHis> {
    //查询fmb端机台最新状态数据
    public List<FmbEqpStatusHis> getNewStatusByEqp() throws Exception{
        return dao.getNewStatusByEqp();
    }
    //批量新增
    public void insertBatch(List<FmbEqpStatusHis> eqpStatusHisList) throws Exception{
        dao.insertBatch(eqpStatusHisList);
    }
    //批量更新
    public void updateBatch(List<FmbEqpStatusHis> eqpStatusHisList) throws Exception{
        dao.updateBatch(eqpStatusHisList);
    }
}

