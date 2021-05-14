package com.rlc.rlcfmbapi.modules.mes.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.mes.entity.PmsInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface PmsInfoDao extends CrudDao<PmsInfoDTO> {
    //维保信息列表
    public List<PmsInfoDTO> getPmsInfoOne(@Param("equipmentName") String equipmentName, @Param("page_num") int page_num, @Param("page_size") int page_size);
    //单条维保信息详情
    public PmsInfoDTO getPmsInfoDetail(@Param("sysId") String sysId);
    //未处理的维保信息
    public List<PmsInfoDTO> getPmsOpenInfo();
}
