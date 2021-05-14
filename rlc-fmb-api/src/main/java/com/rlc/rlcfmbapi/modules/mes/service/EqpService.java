package com.rlc.rlcfmbapi.modules.mes.service;

import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.mes.dao.EqpDao;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class EqpService extends CrudService<EqpDao, EqpDTO> {
    @Autowired
    private EqpDao xqEqpDao;
    public Page<EqpDTO> findPage(Page<EqpDTO> page, EqpDTO log) {
             return super.findPage(page, log);
    }
    /**
     * 查询所有机台状态信息
     * @param eqpDTO
     * @return List<EqpDTO>
     * @throws Exception
     */
    public List<EqpDTO> findEqpStateList(EqpDTO eqpDTO) throws Exception{
        try {
            return dao.findEqpStateList(eqpDTO);
        } catch (Exception e) {
            logger.error("getEqpCurrentInfo",e);
            return null;
        }
    }

    /**
     * 查询设备当前处理的晶棒信息
     * @param eqpDTO
     * @return List<Map<String,Object>>
     * @throws Exception
     */
    public List<Map<String,Object>> findEqpLotList(EqpDTO eqpDTO) throws Exception{
        try {
            return dao.findEqpLotList(eqpDTO);
        } catch (Exception e) {
            logger.error("getEqpCurrentInfo",e);
            return null;
        }
    }

    /**
     * 查询设备当前详情
     * @param xqEqpDTO
     * @return EqpDTO
     * @throws Exception
     */
    public EqpDTO getEqpCurrentInfo(EqpDTO xqEqpDTO) throws Exception{
        try {
            return dao.getEqpCurrentInfo(xqEqpDTO);
        } catch (Exception e) {
            logger.error("getEqpCurrentInfo",e);
            return null;
        }
    }
}