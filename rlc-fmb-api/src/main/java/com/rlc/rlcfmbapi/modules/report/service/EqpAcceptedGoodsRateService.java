package com.rlc.rlcfmbapi.modules.report.service;

import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.report.dao.EqpAcceptedGoodsRateDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpAcceptedGoodsRateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpAcceptedGoodsRateService extends CrudService<EqpAcceptedGoodsRateDao,EqpAcceptedGoodsRateDTO> {
    @Autowired
    private EqpAcceptedGoodsRateDao eqpAcceptedGoodsRateDao;

    public List<EqpAcceptedGoodsRateDTO> getGoodsRateDaily(){ return eqpAcceptedGoodsRateDao.getGoodsRateDaily(); };

    public List<EqpAcceptedGoodsRateDTO> getGoodsRateWeekly(){ return eqpAcceptedGoodsRateDao.getGoodsRateWeekly(); };

    public List<EqpAcceptedGoodsRateDTO> getGoodsRateMonthly(){ return eqpAcceptedGoodsRateDao.getGoodsRateMonthly(); };
}
