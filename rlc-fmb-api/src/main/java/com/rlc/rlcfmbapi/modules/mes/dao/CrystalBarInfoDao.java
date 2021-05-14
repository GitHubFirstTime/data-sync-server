package com.rlc.rlcfmbapi.modules.mes.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.mes.entity.CrystalBarInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface CrystalBarInfoDao extends CrudDao<CrystalBarInfoDTO> {
    /*拿到设备上晶棒信息*/
    List<CrystalBarInfoDTO> getCrystalInfoList(@Param("eqpName") String eqpName);
    /*XQ对应的缓存工位上的晶棒信息*/
    CrystalBarInfoDTO getBufferStationCrystalInfoOne(@Param("eqpName") String eqpName);
}
