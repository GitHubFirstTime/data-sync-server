package com.rlc.rlcfmbapi.modules.report.service;

import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.report.dao.EqpWorkingRateDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpWorkingRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpWorkingRateService extends CrudService<EqpWorkingRateDao,EqpWorkingRateDTO> {

    public List<EqpWorkingRateDTO> getXQWorkingRateDaily(){ return dao.getXQWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getXQWorkingRateWeekly(){ return dao.getXQWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getXQWorkingRateMonthly(){ return dao.getXQWorkingRateMonthly(); };

    public List<EqpWorkingRateDTO> getTJWorkingRateDaily(){ return dao.getTJWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getTJWorkingRateWeekly(){ return dao.getTJWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getTJWorkingRateMonthly(){ return dao.getTJWorkingRateMonthly(); };

    public List<EqpWorkingRateDTO> getQXWorkingRateDaily(){ return dao.getQXWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getQXWorkingRateWeekly(){ return dao.getQXWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getQXWorkingRateMonthly(){ return dao.getQXWorkingRateMonthly(); };

    public List<EqpWorkingRateDTO> getFXWorkingRateDaily(){ return dao.getFXWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getFXWorkingRateWeekly(){ return dao.getFXWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getFXWorkingRateMonthly(){ return dao.getFXWorkingRateMonthly(); };
}
