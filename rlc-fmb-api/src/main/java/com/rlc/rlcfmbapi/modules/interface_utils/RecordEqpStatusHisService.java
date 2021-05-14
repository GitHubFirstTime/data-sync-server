package com.rlc.rlcfmbapi.modules.interface_utils;

import com.google.common.collect.Lists;
import com.rlc.rlcbase.kafka.ProducerService;
import com.rlc.rlcbase.result.ResultData;
import com.rlc.rlcfmbapi.modules.fmb.entity.FmbEqpStatusHis;
import com.rlc.rlcfmbapi.modules.fmb.service.FmbEqpStatusHisService;
import com.rlc.rlcfmbapi.modules.mes.entity.EqpDTO;
import com.rlc.rlcfmbapi.modules.mes.service.EqpService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * TODO
 * ClassName:SyncEqpDataService <br/>
 * Function: 同步机台数据的service ADD FUNCTION. <br/>
 * Reason:	 同步机台数据的service ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/14 9:42
 * @since JDK 1.8
 */
@Component
public class RecordEqpStatusHisService {

    Logger logger =  LogManager.getLogger(RecordEqpStatusHisService.class);
    @Autowired
    private EqpService xqEqpService;
    @Autowired
    private FmbEqpStatusHisService eqpStatusHisService;
    @Autowired
    private ProducerService producerService;
    public ResultData Sync_EqpStatusData(String eqpType){
        String msg = "";
        try {
            EqpDTO eqpDTO = new EqpDTO();
            eqpDTO.setEqpType(eqpType);
            List<EqpDTO> xqEqpDTOList = xqEqpService.findList(eqpDTO);
            List<FmbEqpStatusHis> fmbEqpStatusHisList = eqpStatusHisService.getNewStatusByEqp();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("QuartzJob1----xqEqpDTOList="+xqEqpDTOList.size()+",fmbEqpStatusHisList"+fmbEqpStatusHisList.size()+"," + sdf.format(new Date()));

            //获取mes脱管机台
            List<FmbEqpStatusHis> fmbEqpList_add = Lists.newArrayList();//需要新增的机台状态
            List<FmbEqpStatusHis> fmbEqpStatusList_update = Lists.newArrayList();//需要修改机台状态持续时间信息
            fmbEqpList_add = checkEqpStatus_add(xqEqpDTOList,fmbEqpStatusHisList);
            fmbEqpStatusList_update = checkEqpStatus_update(xqEqpDTOList,fmbEqpStatusHisList);


            //机台状态变更——需新增数据
            if (CollectionUtils.isNotEmpty(fmbEqpList_add)){
                saveNewEqpStatus(fmbEqpList_add);
                logger.info("共有{}台机台状态变更，检测时间:{}",fmbEqpList_add.size(),sdf.format(new Date()));
                msg = msg+"\n共有"+fmbEqpList_add.size()+"台机台状态变更";
            }

            //机台状态未变更，信息变更
            if (CollectionUtils.isNotEmpty(fmbEqpStatusList_update)){
                updateBatchByEqpStatusDura(fmbEqpStatusList_update);
                logger.info("共有{}台机台状态未变更，检测时间:{}",fmbEqpStatusList_update.size(),sdf.format(new Date()));
                msg = msg+"\n共有"+fmbEqpStatusList_update.size()+"台机台状态未变更";
            }
            return ResultData.OK().message("机台状态同步成功\n"+msg);
        } catch (Exception e) {
            logger.error("机台状态检测失败",e);
            return ResultData.ERROR().message("机台状态检测失败");
        }
    }

    /**
     * 检查fmbeqp 有效性
     * @param xqEqpDTOList
     * @param fmbEqpStatusHisList
     */
    public List<FmbEqpStatusHis> checkEqpStatus_update(List<EqpDTO> xqEqpDTOList, List<FmbEqpStatusHis> fmbEqpStatusHisList){
//        List<FmbEqpStatusHis> addEqpStatusHisList = Lists.newArrayList();
        List<FmbEqpStatusHis> updatedEqpStatusHisList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(fmbEqpStatusHisList)){
            for (int i = 0; i < fmbEqpStatusHisList.size(); i++) {
                int token = 0;
                for (int j = 0; j < xqEqpDTOList.size(); j++) {
                    if (Objects.equals(xqEqpDTOList.get(j).getId(),fmbEqpStatusHisList.get(i).getEqpId())
                            &&Objects.equals(StringUtils.isEmpty(xqEqpDTOList.get(j).getEqpRunState())?"":xqEqpDTOList.get(j).getEqpRunState().toUpperCase(),StringUtils.isEmpty(fmbEqpStatusHisList.get(i).getEqpStatus())?"":fmbEqpStatusHisList.get(i).getEqpStatus())){
                        token++;
                        break;
                    }
                }
                if (token>0){//状态未变更，需要update 状态时间的数据
                    fmbEqpStatusHisList.get(i).preUpdate();
                    Long durationL = fmbEqpStatusHisList.get(i).getUpdateDate().getTime()-fmbEqpStatusHisList.get(i).getCreateDate().getTime();
                    fmbEqpStatusHisList.get(i).setStatusDuration(durationL);
                    updatedEqpStatusHisList.add(fmbEqpStatusHisList.get(i));
                }
            }
        }
        return updatedEqpStatusHisList;
    }

    /**
     * 需新增的机台状态
     * @param xqEqpDTOList
     * @param fmbEqpStatusHisList
     * @return addEqpStatusHisList 需要新增的状态数据
     */
    public List<FmbEqpStatusHis> checkEqpStatus_add(List<EqpDTO> xqEqpDTOList, List<FmbEqpStatusHis> fmbEqpStatusHisList){
        List<FmbEqpStatusHis> addEqpStatusHisList = Lists.newArrayList();
        List<EqpDTO> xqEqpDTOList_temp = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(fmbEqpStatusHisList)){
            for (int j = 0; j < xqEqpDTOList.size(); j++) {
                    int token = 0;
                for (int i = 0; i < fmbEqpStatusHisList.size(); i++) {
                    if (Objects.equals(xqEqpDTOList.get(j).getId(),fmbEqpStatusHisList.get(i).getEqpId())
                            &&!Objects.equals(StringUtils.isEmpty(xqEqpDTOList.get(j).getEqpRunState())?"":xqEqpDTOList.get(j).getEqpRunState().toUpperCase(),StringUtils.isEmpty(fmbEqpStatusHisList.get(i).getEqpStatus())?"":fmbEqpStatusHisList.get(i).getEqpStatus())){
                        token++;
                        break;
                    }
                }
                if (token>0){//状态变更的数据_需新增
                    FmbEqpStatusHis eqpStatusHis1 = new FmbEqpStatusHis();
                    eqpStatusHis1.setEqpId(xqEqpDTOList.get(j).getId());
                    eqpStatusHis1.setEqpName(xqEqpDTOList.get(j).getEqpName());
                    eqpStatusHis1.setEqpStatus(StringUtils.isEmpty(xqEqpDTOList.get(j).getEqpRunState())?"":xqEqpDTOList.get(j).getEqpRunState().toUpperCase());
                    eqpStatusHis1.setEqpId(xqEqpDTOList.get(j).getId());
                    eqpStatusHis1.preInsert();
                    eqpStatusHis1.preUpdate();
                    Long durationL = eqpStatusHis1.getUpdateDate().getTime()-eqpStatusHis1.getCreateDate().getTime();
                    eqpStatusHis1.setStatusDuration(durationL);
                    addEqpStatusHisList.add(eqpStatusHis1);
                }
            }
        }else{
            for (int i = 0; i < xqEqpDTOList.size(); i++) {
                FmbEqpStatusHis eqpStatusHis = new FmbEqpStatusHis();
                eqpStatusHis.setEqpId(xqEqpDTOList.get(i).getId());
                eqpStatusHis.setEqpName(xqEqpDTOList.get(i).getEqpName());
                eqpStatusHis.setEqpStatus(StringUtils.isEmpty(xqEqpDTOList.get(i).getEqpRunState())?"":xqEqpDTOList.get(i).getEqpRunState().toUpperCase());
                eqpStatusHis.setEqpId(xqEqpDTOList.get(i).getId());
                eqpStatusHis.preInsert();
                eqpStatusHis.preUpdate();
                Long durationL = eqpStatusHis.getUpdateDate().getTime()-eqpStatusHis.getCreateDate().getTime();
                eqpStatusHis.setStatusDuration(durationL);
                addEqpStatusHisList.add(eqpStatusHis);
            }
        }
        return addEqpStatusHisList;
    }

    /**
     * 插入新机台状态
     * @param fmbEqpStatusHisList
     */
    public void saveNewEqpStatus(List<FmbEqpStatusHis> fmbEqpStatusHisList){
        try {
            eqpStatusHisService.insertBatch(fmbEqpStatusHisList);
        }catch (Exception e){
            logger.error("saveNewEqpStatus,",e);
        }
    }

    /**
     * 更新机台状态持续时间
     * @param fmbEqpStatusHisList
     */
    public void updateBatchByEqpStatusDura(List<FmbEqpStatusHis> fmbEqpStatusHisList){
        try {
            eqpStatusHisService.updateBatch(fmbEqpStatusHisList);
        } catch (Exception e) {
            logger.error("updateBatchByEqpStatusDura",e);
        }
    }

}