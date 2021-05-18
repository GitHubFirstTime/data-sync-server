package com.rlc.rlcfmbapi.modules.report.service;

import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.report.dao.EqpOutputDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpOutputService extends CrudService<EqpOutputDao,EqpOutputDTO> {

    public List<EqpOutputDTO> getOutputDaily(String eqpType){
        List<EqpOutputDTO> outputDaily = dao.getOutputDaily(eqpType);
        return outputDaily;
    }

    public List<EqpOutputDTO> getOutputWeekly (String eqpType){
        List<EqpOutputDTO> outputWeekly = dao.getOutputWeekly(eqpType);
        return outputWeekly;
    }

    public List<EqpOutputDTO> getOutputMonthly(String eqpType){
        List<EqpOutputDTO> outputWeekly = dao.getOutputMonthly(eqpType);
        return outputWeekly;
    }
}
