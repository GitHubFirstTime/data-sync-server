package com.rlc.rlcfmbapi.modules.mes.service;

import com.rlc.rlcbase.persistence.annotation.DS;
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

    public EqpDetailDTO getEqpDetailsOne(String device_name) {
        return dao.getEQPDetailsOne(device_name);
    }

    public List<EqpDetailDTO> getEQPDetailsList(String eqpType){return dao.getEQPDetailsList(eqpType);}
	
	public EqpDetailDTO getEQPCapacity(String eqpType){return dao.getEQPCapacity(eqpType);}

    public EqpDetailDTO getEQPCapacityOne(String eqpType,String device_name){return dao.getEQPCapacityOne(eqpType,device_name);}

    public List<EqpDetailDTO> getAllEqpRunInfoList(){return dao.getAllEqpRunInfoList();}

    public List<EqpDetailDTO> getEqpRunStatusStatistics(String eqpType){return dao.getEqpRunStatusStatistics(eqpType);}

    public EqpDetailDTO getRemainingTime(String device_name){return dao.getRemainingTime(device_name);}

    public EqpDetailDTO getEqpTotalNumber(String device_type){return dao.getEqpTotalNumber(device_type);}
}
