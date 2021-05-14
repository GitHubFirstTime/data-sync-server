package com.rlc.rlcfmbapi.modules.mes.service;

import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.mes.dao.EqpDetailDao;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EqpDetailService extends CrudService<EqpDetailDao,EqpDetailDTO> {
    @Autowired
    private EqpDetailDao eqpDetailDao;

    public EqpDetailDTO getEqpDetailsOne(String device_name) {
        return eqpDetailDao.getEQPDetailsOne(device_name);
    }

    public List<EqpDetailDTO> getEQPDetailsList(String eqpType){return eqpDetailDao.getEQPDetailsList(eqpType);}
	
	public EqpDetailDTO getEQPCapacity(String eqpType){return eqpDetailDao.getEQPCapacity(eqpType);}

    public EqpDetailDTO getEQPCapacityOne(String eqpType,String device_name){return eqpDetailDao.getEQPCapacityOne(eqpType,device_name);}

    public List<EqpDetailDTO> getAllEqpRunInfoList(){return eqpDetailDao.getAllEqpRunInfoList();}

    public List<EqpDetailDTO> getEqpRunStatusStatistics(String eqpType){return eqpDetailDao.getEqpRunStatusStatistics(eqpType);}

    public EqpDetailDTO getRemainingTime(String device_name){return eqpDetailDao.getRemainingTime(device_name);}

    public EqpDetailDTO getEqpTotalNumber(String device_type){return eqpDetailDao.getEqpTotalNumber(device_type);}
}
