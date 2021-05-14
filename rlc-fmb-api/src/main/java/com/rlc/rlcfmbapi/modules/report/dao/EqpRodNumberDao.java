package com.rlc.rlcfmbapi.modules.report.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpRodNumberDTO;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface EqpRodNumberDao extends CrudDao<EqpRodNumberDTO> {

    EqpRodNumberDTO getEqpRodNumber(@Param("device_type") String device_type);
}
