package com.rlc.rlcfmbapi.modules.mes.dao;


import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDTO;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface EqpDao extends CrudDao<EqpDTO> {

    /**
     * 查询所有机台状态信息
     * @param eqpDTO
     * @return List<EqpDTO>
     * @throws Exception
     */
    List<EqpDTO> findEqpStateList(EqpDTO eqpDTO) throws Exception;

    /**
     * 查询设备当前处理的晶棒信息
     * @param eqpDTO
     * @return List<Map<String,Object>>
     * @throws Exception
     */
    List<Map<String,Object>> findEqpLotList(EqpDTO eqpDTO) throws Exception;

    /**
     * 查询设备当前详情
     * @param xqEqpDTO
     * @return EqpDTO
     * @throws Exception
     */
    EqpDTO getEqpCurrentInfo(EqpDTO xqEqpDTO) throws Exception;
}
