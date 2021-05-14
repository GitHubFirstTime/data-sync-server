package com.rlc.rlcfmbapi.modules.mes.dao;

import java.util.List;
import java.util.Map;

/**
 * TODO
 * ClassName:EqpCurrentLotDao <br/>
 * Function: 当前机台晶棒信息 ADD FUNCTION. <br/>
 * Reason:	 当前机台晶棒信息 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/27 17:51
 * @since JDK 1.8
 */
public interface EqpCurrentLotDao {
    /**
     * 查询指定机台中晶棒信息
     * @param paramsMap
     * @return List<Map<String,Object>>
     * @throws Exception
     */
    public List<Map<String,Object>> getLotDetailByEqp(Map<String, Object> paramsMap) throws Exception;
}
