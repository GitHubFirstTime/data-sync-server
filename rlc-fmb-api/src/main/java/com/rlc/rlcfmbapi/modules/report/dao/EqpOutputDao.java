package com.rlc.rlcfmbapi.modules.report.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpOutputDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
@DS("reportdb")
public interface EqpOutputDao extends CrudDao<EqpOutputDTO> {
    //今日产量统计数据
    List<EqpOutputDTO> getOutputDaily(@Param("eqpType") String eqpType);
    //本周产量统计数据
    List<EqpOutputDTO> getOutputWeekly(@Param("eqpType") String eqpType);
    //本月产量统计数据
    List<EqpOutputDTO> getOutputMonthly(@Param("eqpType") String eqpType);
}
