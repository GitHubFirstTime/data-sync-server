package com.rlc.rlcfmbapi.modules.report.service;

import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.report.dao.EqpRodNumberDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpRodNumberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EqpRodNumberService extends CrudService<EqpRodNumberDao,EqpRodNumberDTO> {
    public EqpRodNumberDTO getEqpRodNumber(String device_type){
        EqpRodNumberDTO eqpRodNumberDTO = new EqpRodNumberDTO();
        try {
            eqpRodNumberDTO = dao.getEqpRodNumber(device_type);
        } catch (Exception e) {
            logger.error("getEqpRodNumber error!",e);
        }
        return eqpRodNumberDTO;
    }
}
