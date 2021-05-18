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
    public List<EqpAcceptedGoodsRateDTO> getGoodsRateDaily(){ return dao.getGoodsRateDaily(); };

    public List<EqpAcceptedGoodsRateDTO> getGoodsRateWeekly(){ return dao.getGoodsRateWeekly(); };

    public List<EqpAcceptedGoodsRateDTO> getGoodsRateMonthly(){ return dao.getGoodsRateMonthly(); };
}
