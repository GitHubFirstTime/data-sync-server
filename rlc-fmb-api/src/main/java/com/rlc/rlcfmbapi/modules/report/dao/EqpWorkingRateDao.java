package com.rlc.rlcfmbapi.modules.report.dao;


import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpWorkingRateDTO;

import java.util.List;

@MyBatisDao
@DS("reportdb")
public interface EqpWorkingRateDao extends CrudDao<EqpWorkingRateDTO> {
    //线切今日良品率
    public List<EqpWorkingRateDTO> getXQWorkingRateDaily();
    //线切本周良品率
    public List<EqpWorkingRateDTO> getXQWorkingRateWeekly();
    //线切本月良品率
    public List<EqpWorkingRateDTO> getXQWorkingRateMonthly();

    //脱胶今日良品率
    public List<EqpWorkingRateDTO> getTJWorkingRateDaily();
    //脱胶本周良品率
    public List<EqpWorkingRateDTO> getTJWorkingRateWeekly();
    //脱胶本月良品率
    public List<EqpWorkingRateDTO> getTJWorkingRateMonthly();

    //清洗今日良品率
    public List<EqpWorkingRateDTO> getQXWorkingRateDaily();
    //清洗本周良品率
    public List<EqpWorkingRateDTO> getQXWorkingRateWeekly();
    //清洗本月良品率
    public List<EqpWorkingRateDTO> getQXWorkingRateMonthly();

    //分选今日良品率
    public List<EqpWorkingRateDTO> getFXWorkingRateDaily();
    //分选本周良品率
    public List<EqpWorkingRateDTO> getFXWorkingRateWeekly();
    //清洗本月良品率
    public List<EqpWorkingRateDTO> getFXWorkingRateMonthly();
}
