package com.rlc.rlcfmbapi.modules.mes.service;

import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.mes.dao.EqpDao;
import com.rlc.rlcfmbapi.modules.mes.dao.EqpDetailDao;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDTO;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpStatusService extends CrudService<EqpDetailDao, EqpDetailDTO> {

    public EqpDetailDTO getEqpRunStatusInfo(String device_name) {return  dao.getEqpRunStatusInfo(device_name);}

    public EqpDetailDTO getEqpRunStatusStatisticsOne(String device_name) {return dao.getEqpRunStatusStatisticsOne(device_name);}

    public List<EqpDetailDTO> getRunStatusStatistics(String eqpType){return dao.getEqpRunStatusStatistics(eqpType); }
}
