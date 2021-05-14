package com.rlc.rlcfmbapi.modules.mes.service;

import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.mes.dao.CrystalBarInfoDao;
import com.rlc.rlcfmbapi.modules.mes.entity.CrystalBarInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CrystalBarInfoService extends CrudService<CrystalBarInfoDao,CrystalBarInfoDTO> {
    @Autowired
    private CrystalBarInfoDao crystalBarInfoDao;

    public List<CrystalBarInfoDTO> getCrystalInfoList(String eqpName) {
        return crystalBarInfoDao.getCrystalInfoList(eqpName);
    }

    public CrystalBarInfoDTO getBufferStationCrystalInfoOne(String eqpName){
        return crystalBarInfoDao.getBufferStationCrystalInfoOne(eqpName);
    }
}
