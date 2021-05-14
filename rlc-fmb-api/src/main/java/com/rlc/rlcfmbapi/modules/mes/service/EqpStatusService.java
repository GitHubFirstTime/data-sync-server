package com.rlc.rlcfmbapi.modules.mes.service;

import com.rlc.rlcfmbapi.modules.mes.dao.EqpDetailDao;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpStatusService {
    @Autowired
    private EqpDetailDao eqpDetailDao;

    public EqpDetailDTO getEqpRunStatusInfo(String device_name) {return  eqpDetailDao.getEqpRunStatusInfo(device_name);}

    public EqpDetailDTO getEqpRunStatusStatisticsOne(String device_name) {return eqpDetailDao.getEqpRunStatusStatisticsOne(device_name);}

    public List<EqpDetailDTO> getRunStatusStatistics(String eqpType){return eqpDetailDao.getEqpRunStatusStatistics(eqpType); }
}
