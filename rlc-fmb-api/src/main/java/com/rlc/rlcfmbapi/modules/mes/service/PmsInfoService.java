package com.rlc.rlcfmbapi.modules.mes.service;


import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlcfmbapi.modules.mes.dao.PmsInfoDao;
import com.rlc.rlcfmbapi.modules.mes.entity.PmsInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PmsInfoService extends CrudService<PmsInfoDao,PmsInfoDTO> {
    @Autowired
    PmsInfoDao pmsInfoDao;
    /*//分页
    public Page<PmsInfoDTO> findPage(Page<PmsInfoDTO> page, PmsInfoDTO log) {
        return super.findPage(page, log);
    }*/

    public List<PmsInfoDTO> getPmsInfoOne(String equipmentName,int page_num,int page_size){
        List<PmsInfoDTO> pmsInfoOne = pmsInfoDao.getPmsInfoOne(equipmentName,page_num,page_size);
        return pmsInfoOne;
    }

    public PmsInfoDTO getPmsInfoDetail(String sysId){
        PmsInfoDTO pmsInfoDetail = pmsInfoDao.getPmsInfoDetail(sysId);
        return pmsInfoDetail;
    }

    public List<PmsInfoDTO> getPmsOpenInfo(){
        List<PmsInfoDTO> pmsOpenInfo = pmsInfoDao.getPmsOpenInfo();
        return pmsOpenInfo;
    }


}
