package com.rlc.rlcfmbapi.modules.fmb.service;

import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.fmb.dao.FmbEqpDao;
import com.rlc.rlcfmbapi.modules.fmb.entity.FmbEqp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional(readOnly = true)
public class FmbEqpService extends CrudService<FmbEqpDao, FmbEqp> {
    @DS("fmbdb")
    public List<FmbEqp> findList(FmbEqp fmbEqp){
        return super.findList(fmbEqp);
    }
    public void insertBatch(List<FmbEqp> fmbEqpList) throws Exception{
        dao.insertBatch(fmbEqpList);
    }
    /**
     * 修改机台在mes中是否脱管
     * @param fmbEqpList
     * @throws Exception
     */
    public void updateEqpOutManageBatch(List<FmbEqp> fmbEqpList) throws Exception{
        dao.updateEqpOutManageBatch(fmbEqpList);
    }

    /**
     * 机台信息在mes中有变更
     * 目前只支持名称变更更改
     * @param fmbEqpList
     * @throws Exception
     */
    public void updateBatchByMesData(List<FmbEqp> fmbEqpList) throws Exception{
        dao.updateBatchByMesData(fmbEqpList);
    }
}