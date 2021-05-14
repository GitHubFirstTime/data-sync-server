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
    @Autowired
    private EqpWorkingRateDao eqpWorkingRateDao;

    public List<EqpWorkingRateDTO> getXQWorkingRateDaily(){ return eqpWorkingRateDao.getXQWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getXQWorkingRateWeekly(){ return eqpWorkingRateDao.getXQWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getXQWorkingRateMonthly(){ return eqpWorkingRateDao.getXQWorkingRateMonthly(); };

    public List<EqpWorkingRateDTO> getTJWorkingRateDaily(){ return eqpWorkingRateDao.getTJWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getTJWorkingRateWeekly(){ return eqpWorkingRateDao.getTJWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getTJWorkingRateMonthly(){ return eqpWorkingRateDao.getTJWorkingRateMonthly(); };

    public List<EqpWorkingRateDTO> getQXWorkingRateDaily(){ return eqpWorkingRateDao.getQXWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getQXWorkingRateWeekly(){ return eqpWorkingRateDao.getQXWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getQXWorkingRateMonthly(){ return eqpWorkingRateDao.getQXWorkingRateMonthly(); };

    public List<EqpWorkingRateDTO> getFXWorkingRateDaily(){ return eqpWorkingRateDao.getFXWorkingRateDaily(); };

    public List<EqpWorkingRateDTO> getFXWorkingRateWeekly(){ return eqpWorkingRateDao.getFXWorkingRateWeekly(); };

    public List<EqpWorkingRateDTO> getFXWorkingRateMonthly(){ return eqpWorkingRateDao.getFXWorkingRateMonthly(); };
}
