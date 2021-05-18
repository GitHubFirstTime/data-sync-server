package com.rlc.rlcfmbapi.modules.report.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpAcceptedGoodsRateDTO;

import java.util.List;

@MyBatisDao
@DS("reportdb")
public interface EqpAcceptedGoodsRateDao extends CrudDao<EqpAcceptedGoodsRateDTO> {
    //今日良品率
    public List<EqpAcceptedGoodsRateDTO> getGoodsRateDaily();
    //本周良品率
    public List<EqpAcceptedGoodsRateDTO> getGoodsRateWeekly();
    //本月良品率
    public List<EqpAcceptedGoodsRateDTO> getGoodsRateMonthly();
}
