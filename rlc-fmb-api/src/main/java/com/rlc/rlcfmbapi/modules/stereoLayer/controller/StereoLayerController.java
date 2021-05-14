package com.rlc.rlcfmbapi.modules.stereoLayer.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rlc.rlcbase.persistence.web.BaseController;
import com.rlc.rlcfmbapi.modules.interface_utils.EqpTypeTranslateUtils;
import com.rlc.rlcfmbapi.modules.report.dao.EqpAcceptedGoodsRateDao;
import com.rlc.rlcfmbapi.modules.report.entity.EqpAcceptedGoodsRateDTO;
import com.rlc.rlcfmbapi.modules.report.entity.EqpWorkingRateDTO;
import com.rlc.rlcfmbapi.modules.report.service.EqpRodNumberService;
import com.rlc.rlcfmbapi.modules.report.service.EqpWorkingRateService;
import com.rlc.rlcfmbapi.modules.stereoLayer.response.AlarmInfoResponse;
import com.rlc.rlcfmbapi.modules.stereoLayer.response.PmsInfoResponse;
import com.rlc.rlcfmbapi.modules.mes.entity.*;
import com.rlc.rlcfmbapi.modules.mes.service.*;
import com.rlc.rlcfmbapi.modules.mesUat.entity.AlarmDTO;
import com.rlc.rlcfmbapi.modules.mesUat.service.AlarmService;
import com.rlc.rlcfmbapi.modules.report.entity.EqpOutputDTO;
import com.rlc.rlcfmbapi.modules.report.service.EqpOutputService;
import com.rlc.rlcfmbapi.modules.stereoLayer.common.result.StereoResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO
 * ClassName:StereoLayerController <br/>
 * Function: 3D展示层数据接口 ADD FUNCTION. <br/>
 * Reason:	 3D展示层数据接口 ADD REASON. <br/>
 *
 * @author RLC_ZYC
 * @version 1.0
 * @date 2020/9/18 13:56
 * @since JDK 1.8
 */
@RestController
//@RequestMapping(value = "v1")
public class StereoLayerController extends BaseController {
    Gson gson = new GsonBuilder().create();
    @Autowired
    private EqpService xqEqpService;
    @Autowired
    private EqpDetailService eqpDetailService;
    @Autowired
    private CrystalBarInfoService crystalBarInfoService;
    @Autowired
    private EqpStatusService eqpStatusService;
    @Autowired
    private PmsInfoService pmsInfoService;
    @Autowired
    private EqpOutputService eqpOutputService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private EqpAcceptedGoodsRateDao eqpAcceptedGoodsRateDao;
    @Autowired
    private EqpWorkingRateService eqpWorkingRateService;
    @Autowired
    private EqpRodNumberService eqpRodNumberService;

    /*@RequestMapping("auth/getToken")
    public StereoResult getToken(String appId){
        String token = TokenUtil.createToken(appId,30);
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(token);
    }*/
    /**
     * 获取设备容量
     * @param device_type
     * @param device_name
     * @return StereoResult
     */
    @RequestMapping("/eqpCapacity")
    public StereoResult eqpCapacity(String device_type,@Nullable String device_name){
        if(null != device_name && null != device_type){
            JSONObject job = new JSONObject();
            try {
                EqpDetailDTO eqpCapacityOne = eqpDetailService.getEQPCapacityOne(device_type, device_name);
                job.put("device_type",eqpCapacityOne.getEqpType());
                job.put("device_type_name", EqpTypeTranslateUtils.englishToChinese(eqpCapacityOne.getEqpType()));
                job.put("device_id",eqpCapacityOne.getId());
                job.put("device_name",eqpCapacityOne.getEqpName());
                job.put("current_capacity",eqpCapacityOne.getCurrentCapacity());
                job.put("total_capacity",eqpCapacityOne.getCurrentCapacity());
            }catch (Exception e){
                StereoResult.UNKNOWNERROR().MESSAGE("eqpCapacity error is"+e.toString());
            }
            return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
        }else if(null != device_type){
            JSONObject job = new JSONObject();
            try{
                EqpDetailDTO eqpCapacity = eqpDetailService.getEQPCapacity(device_type);
                job.put("device_type",eqpCapacity.getEqpType());
                job.put("device_type_name",EqpTypeTranslateUtils.englishToChinese(eqpCapacity.getEqpType()));
                if(device_type.equalsIgnoreCase("LTK")){
                    job.put("current_capacity","1802");
                    job.put("total_capacity","1441");
                }else if(device_type.equalsIgnoreCase("CPK")){
                    job.put("current_capacity","864");
                    job.put("total_capacity","691");
                }else  if(device_type.equalsIgnoreCase("XQ")){
                    job.put("current_capacity",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
                    //job.put("total_capacity","148"); current_capacity
                    job.put("total_capacity",null == eqpDetailService.getEqpTotalNumber(device_type)? 0:Integer.parseInt(eqpDetailService.getEqpTotalNumber(device_type).getTotalNumber()));
                }else if(device_type.equalsIgnoreCase("FX")){
                    job.put("total_capacity",null == eqpDetailService.getEqpTotalNumber(device_type)? 0:Integer.parseInt(eqpDetailService.getEqpTotalNumber(device_type).getTotalNumber()));
                    job.put("current_capacity",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
                }else if(device_type.equalsIgnoreCase("TJ")){
                    job.put("total_capacity",null == eqpDetailService.getEqpTotalNumber(device_type)? 0:Integer.parseInt(eqpDetailService.getEqpTotalNumber(device_type).getTotalNumber()));
                    job.put("current_capacity",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
                }else if(device_type.equalsIgnoreCase("GHK")){
                    job.put("total_capacity",null == eqpDetailService.getEqpTotalNumber(device_type)? 0:Integer.parseInt(eqpDetailService.getEqpTotalNumber(device_type).getTotalNumber()));
                    job.put("current_capacity",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
                }else if(device_type.equalsIgnoreCase("ZB")){
                    job.put("total_capacity",null == eqpDetailService.getEqpTotalNumber(device_type)? 0:Integer.parseInt(eqpDetailService.getEqpTotalNumber(device_type).getTotalNumber()));
                    job.put("current_capacity",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
                }else if(device_type.equalsIgnoreCase("QX")){
                    job.put("total_capacity",null == eqpDetailService.getEqpTotalNumber(device_type)? 0:Integer.parseInt(eqpDetailService.getEqpTotalNumber(device_type).getTotalNumber()));
                    job.put("current_capacity",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
                }
            }catch (Exception e){
                StereoResult.UNKNOWNERROR().MESSAGE("eqpCapacity error is"+e.toString());
            }
            return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
        }else {
            return StereoResult.PARAMERROR().MESSAGE("参数为空");
        }
    }

    /**
     * 设备产量
     * @param device_type
     * @return StereoResult
     */
    @RequestMapping("/eqpOutput")
    public StereoResult eqpOutput(String device_type, String period){
        try{
            List<EqpOutputDTO> output = null;
            if(period.equals("week")){
                output = eqpOutputService.getOutputWeekly(device_type);
            }else if(period.equals("day")){
                output = eqpOutputService.getOutputDaily(device_type);
            }else if(period.equals("month")){
                output = eqpOutputService.getOutputMonthly(device_type);
            }
            JSONArray array = new  JSONArray();
            for (int i = 0; i < output.size(); i++) {
                if(!output.get(i).getQty().equals("0")){
                    JSONObject obj = new JSONObject();
                    obj.put("device_type",output.get(i).getTrackInLocation());
                    obj.put("device_type_name",EqpTypeTranslateUtils.englishToChinese(output.get(i).getTrackInLocation()));
                    obj.put("value",output.get(i).getQty());
                    obj.put("time",output.get(i).getDayTime());
                    obj.put("sort",output.get(i).getSort());
                    array.add(obj);
                }
            }
            return StereoResult.SUCCESS().MESSAGE("设备产量数据获取成功").CONTENT(array.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("eqpOutput error is:"+e.getMessage());
        }

    }

    /**
     * 设备机台利用率
     * @param device_type
     * @return
     */
    @RequestMapping("/eqpWorkingRate")
    public StereoResult eqpWorkingRate(String device_type, String period){
        try{
            List<EqpWorkingRateDTO> workingRate = null;
            if(period.equals("week")){
                if(device_type.equals("XQ")){
                    workingRate = eqpWorkingRateService.getXQWorkingRateWeekly();
                }else if(device_type.equals("TJ")) {
                    workingRate = eqpWorkingRateService.getTJWorkingRateWeekly();
                }else if(device_type.equals("FX")){
                    workingRate = eqpWorkingRateService.getFXWorkingRateWeekly();
                }else if(device_type.equals("QX")){
                    workingRate = eqpWorkingRateService.getQXWorkingRateWeekly();
                }
            }else if(period.equals("day")){
                if(device_type.equals("XQ")){
                    workingRate = eqpWorkingRateService.getXQWorkingRateDaily();
                }else if(device_type.equals("TJ")) {
                    workingRate = eqpWorkingRateService.getTJWorkingRateDaily();
                }else if(device_type.equals("FX")){
                    workingRate = eqpWorkingRateService.getTJWorkingRateDaily();
                }else if(device_type.equals("QX")){
                    workingRate = eqpWorkingRateService.getTJWorkingRateDaily();
                }
            }else if(period.equals("month")){
                if(device_type.equals("XQ")){
                    workingRate = eqpWorkingRateService.getXQWorkingRateMonthly();
                }else if(device_type.equals("TJ")) {
                    workingRate = eqpWorkingRateService.getTJWorkingRateMonthly();
                }else if(device_type.equals("FX")){
                    workingRate = eqpWorkingRateService.getFXWorkingRateMonthly();
                }else if(device_type.equals("QX")){
                    workingRate = eqpWorkingRateService.getQXWorkingRateMonthly();
                }
            }
            JSONArray array = new  JSONArray();
            if(null!=workingRate && workingRate.size()>0){
                for (int i = 0; i < workingRate.size(); i++) {
                    if(null != workingRate.get(i).getFpyYc() && !workingRate.get(i).getFpyYc().equals("0") ){
                        JSONObject obj = new JSONObject();
                        obj.put("device_type",device_type);
                        obj.put("device_type_name",EqpTypeTranslateUtils.englishToChinese(device_type));
                        obj.put("value",workingRate.get(i).getFpyYc());
                        obj.put("time",workingRate.get(i).getDayTime());
                        obj.put("sort",workingRate.get(i).getSort());
                        array.add(obj);
                    }
                }
            }
            return StereoResult.SUCCESS().MESSAGE("机台利用率数据获取成功").CONTENT(array.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("eqpWorkingRate error is:"+e.getMessage());
        }
    }

    /**
     * 设备良品率
     * @param device_type
     * @param period
     * @return
     */
    @RequestMapping("/eqpAcceptedGoodsRate")
    public StereoResult eqpAcceptedGoodsRate(String device_type,String period){
        try{
            List<EqpAcceptedGoodsRateDTO> output = null;
            if(period.equals("week")){
                output = eqpAcceptedGoodsRateDao.getGoodsRateWeekly();
            }else if(period.equals("day")){
                output = eqpAcceptedGoodsRateDao.getGoodsRateDaily();
            }else if(period.equals("month")){
                output = eqpAcceptedGoodsRateDao.getGoodsRateMonthly();
            }
            JSONArray array = new  JSONArray();
            for (int i = 0; i < output.size(); i++) {
                if(null != output.get(i).getFpyYc() && !output.get(i).getFpyYc().equals("0") ){
                    JSONObject obj = new JSONObject();
                    obj.put("device_type",device_type);
                    obj.put("device_type_name",EqpTypeTranslateUtils.englishToChinese(device_type));
                    obj.put("value",output.get(i).getFpyYc());
                    obj.put("time",output.get(i).getDayTime());
                    obj.put("sort",output.get(i).getSort());
                    array.add(obj);
                }
            }
            return StereoResult.SUCCESS().MESSAGE("设备良品率数据获取成功").CONTENT(array.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("eqpAcceptedGoodsRate error is:"+e.getMessage());
        }
    }

    /**
     * 获取机台信息
     * @param
     * @return
     */
    @RequestMapping("/eqpDetail")
    public StereoResult eqpDetail(String device_name,String is_buffer_station){
		if(StringUtils.isBlank(device_name)){
            return StereoResult.MISSINGPARAMERROR().MESSAGE("缺少参数");
        }
        if( Boolean.valueOf(is_buffer_station) ){
            String eqpType = eqpDetailService.getEqpDetailsOne(device_name).getEqpType();
            String eqpName = eqpDetailService.getEqpDetailsOne(device_name).getEqpName();
            EqpDetailDTO eqpDetailsOne = eqpDetailService.getEqpDetailsOne(device_name);
            JSONObject eqpObj = new JSONObject();
            if(eqpType.equals("XQ")){
                try{
                    EqpDetailDTO eqpDetailDTO = eqpDetailService.getEqpDetailsOne(device_name);
                    eqpObj.put("device_id", eqpDetailDTO.getId());
                    eqpObj.put("device_name", eqpDetailDTO.getEqpName());
                    eqpObj.put("device_type", eqpDetailDTO.getEqpType());
                    eqpObj.put("device_type_name", EqpTypeTranslateUtils.englishToChinese(eqpDetailDTO.getEqpType()));
                    eqpObj.put("device_floor", eqpDetailDTO.getFloor());
                    eqpObj.put("running_mode", eqpDetailDTO.getEqpRunState());
                    eqpObj.put("send_mode", eqpDetailDTO.getEqpDispatchMode());
                    eqpObj.put("control_mode", eqpDetailDTO.getEqpControlMode());
                    eqpObj.put("receipe_code", eqpDetailDTO.getRecipeName());
                    //剩余加工工时
                    eqpObj.put("remaining_time", null == eqpDetailService.getRemainingTime(device_name)? null : (115 - Integer.parseInt(eqpDetailService.getRemainingTime(device_name).getRemainingTime())/60)+"分");
                    //理论加工工时
                    eqpObj.put("theory_time", "115分");
                    //实际加工工时
                    eqpObj.put("actual_time",null == eqpDetailService.getRemainingTime(device_name)? null :Integer.parseInt(eqpDetailService.getRemainingTime(device_name).getRemainingTime()) / 60 +"分");
                    CrystalBarInfoDTO bufferStationCrystalInfoOne = crystalBarInfoService.getBufferStationCrystalInfoOne(eqpName);
                    if(null != bufferStationCrystalInfoOne){
                        JSONObject crystalBarInfo = new JSONObject();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
                        crystalBarInfo.put("crystal_bar_id",bufferStationCrystalInfoOne .getAppId());
                        crystalBarInfo.put("produce_plan",bufferStationCrystalInfoOne .getOrderNumber());
                        crystalBarInfo.put("produce_plan_color",bufferStationCrystalInfoOne .getOrderNumberColor());
                        crystalBarInfo.put("rod_length",bufferStationCrystalInfoOne .getTotalLength());
                        crystalBarInfo.put("mold_type",bufferStationCrystalInfoOne .getMoldType());
                        String s = bufferStationCrystalInfoOne .getTrackInTime().substring(0,15);
                        Date date = sdf.parse(s);
                        crystalBarInfo.put("enter_machine_time",date.getTime());
                        eqpObj.put("crystal_bar_info",crystalBarInfo);
                    }else {
                        return StereoResult.UNKNOWNERROR().MESSAGE("线切机缓存工位暂无晶棒");
                    }

                }catch (Exception e){
                    StereoResult.UNKNOWNERROR().MESSAGE(""+e.getMessage());
                }
            }else {
                return StereoResult.UNKNOWNERROR().MESSAGE("缓存工位不属于线切机");
            }
        }
        JSONObject job = new JSONObject();
        try{
            EqpDetailDTO eqpDetailDTO = eqpDetailService.getEqpDetailsOne(device_name);
            String eqpName = eqpDetailDTO.getEqpName();
            job.put("device_id", eqpDetailDTO.getId());
            job.put("device_name", eqpDetailDTO.getEqpName());
            job.put("device_type",eqpDetailDTO.getEqpType());
            job.put("device_type_name",EqpTypeTranslateUtils.englishToChinese(eqpDetailDTO.getEqpType()) );
            job.put("device_floor", eqpDetailDTO.getFloor());
            job.put("running_mode", eqpDetailDTO.getEqpRunState());
            job.put("send_mode", eqpDetailDTO.getEqpDispatchMode());
            job.put("control_mode", eqpDetailDTO.getEqpControlMode());
            job.put("receipe_code", eqpDetailDTO.getRecipeName());
            //剩余加工工时
            job.put("remaining_time", null == eqpDetailService.getRemainingTime(device_name)? null : (150 - Integer.parseInt(eqpDetailService.getRemainingTime(device_name).getRemainingTime()) / 60)+"分");
            //理论加工工时
            job.put("theory_time", "150分");
            //实际加工工时
            job.put("actual_time", null == eqpDetailService.getRemainingTime(device_name)? null :Integer.parseInt(eqpDetailService.getRemainingTime(device_name).getRemainingTime()) /60 +"分");
            List<CrystalBarInfoDTO> crystalInfoList = crystalBarInfoService.getCrystalInfoList(eqpName);
            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
            for (int i = 0; i < crystalInfoList.size(); i++) {
                CrystalBarInfoDTO crystalBarInfoOne = crystalInfoList.get(i);
                JSONObject crystalBarInfo = new JSONObject();
                crystalBarInfo.put("crystal_bar_id",crystalBarInfoOne.getAppId());
                crystalBarInfo.put("produce_plan",crystalBarInfoOne.getOrderNumber());
                crystalBarInfo.put("produce_plan_color",crystalBarInfoOne.getOrderNumberColor());
                crystalBarInfo.put("rod_length",crystalBarInfoOne.getTotalLength());
                crystalBarInfo.put("mold_type",crystalBarInfoOne.getMoldType());
                String s = crystalBarInfoOne.getTrackInTime().substring(0,15);
                Date date = sdf.parse(s);
                crystalBarInfo.put("enter_machine_time",date.getTime());
                jsonArray.add(crystalBarInfo);
            }
            job.put("crystal_bar_info",jsonArray);
        }catch (Exception e){
            StereoResult.UNKNOWNERROR().MESSAGE(""+e.getMessage());
        }
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
    }

    /**
     * 设备维护信息列表
     * @param device_name
     * @param page_num
     * @param page_size
     * @return
     */
    @RequestMapping("/eqpPMSList")
    public StereoResult eqpPMSList(int page_num,int page_size,String device_name){
        try{
            List<PmsInfoDTO> pmsInfoOne = pmsInfoService.getPmsInfoOne(device_name,page_num,page_size);
            JSONObject obj = new JSONObject();
            obj.put("page_num",page_num);
            obj.put("page_size",page_size);
            obj.put("total_num",pmsInfoOne.size()==0?"0":pmsInfoOne.get(0).getTotal());
            JSONArray array = new JSONArray();

            for (int i = 0; i < pmsInfoOne.size(); i++) {
                PmsInfoResponse response = new PmsInfoResponse();
                response.setMaintenanceTime(String.valueOf(Timestamp.valueOf(pmsInfoOne.get(i).getMaintenceTime()).getTime()));
                response.setNextMaintenanceTime(String.valueOf(Timestamp.valueOf(pmsInfoOne.get(i).getNextMaintenceTime()).getTime()));
                response.setMaintenanceMaterial(pmsInfoOne.get(i).getMaintenceMaterial());
                response.setDeviceType(pmsInfoOne.get(i).getDeviceType());
                response.setDeviceTypeName(pmsInfoOne.get(i).getDeviceTypeName());
                response.setDeviceName(pmsInfoOne.get(i).getDeviceName());
                response.setPsmId(pmsInfoOne.get(i).getPmsId());
                array.add(response);
            }
            obj.put("pms_info_list",array.toString());
            return StereoResult.SUCCESS().MESSAGE("设备维护信息获取成功").CONTENT(obj.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("设备维护信息获取失败"+e.getMessage());
        }

    }

    /**
     * 设备维护信息详情
     * @param pmsId
     * @return
     */
    @RequestMapping("/eqpPMSDetail")
    public StereoResult eqpPMSDetail(String pmsId){
        try{
            PmsInfoDTO pmsInfoDetail = pmsInfoService.getPmsInfoDetail(pmsId);
            JSONObject obj = new JSONObject();
            obj.put("device_name",pmsInfoDetail.getDeviceName());
            obj.put("device_type",pmsInfoDetail.getDeviceType());
            obj.put("device_type_name",pmsInfoDetail.getDeviceTypeName());
            obj.put("maintence_type",pmsInfoDetail.getMaintenceType());
            obj.put("maintence_time",pmsInfoDetail.getMaintenceTime());
            obj.put("next_maintence_time",String.valueOf(Timestamp.valueOf(pmsInfoDetail.getMaintenceTime()).getTime()));
            obj.put("maintence_material",String.valueOf(Timestamp.valueOf(pmsInfoDetail.getNextMaintenceTime()).getTime()));
            return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(obj.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("统计数据获取失败"+e.getMessage());
        }
    }

    /**
     * 设备告警列表
     * @param device_type
     * @param page_num
     * @param page_size
     * @return
     */
    @RequestMapping("/eqpAlarmList")
    public StereoResult eqpAlarmList(int page_num,int page_size,@Nullable String device_type,@Nullable String device_name,@Nullable String floor){
        try{
            List<AlarmDTO> alarmInfoList = null;
            if(null != device_name){
                //单台设备告警列表
                alarmInfoList = alarmService.getAlarmInfo(page_num,page_size,device_name);
            }else if(null != device_type){
                //车间设备告警列表
                alarmInfoList = alarmService.getAlarmInfoList(page_num,page_size,device_type);
            }else if(null != floor){
                if(floor.equals("1")){
                    //一楼告警列表
                    alarmInfoList = alarmService.getAlarmInfoListOneFloor(page_num,page_size);
                }else if(floor.equals("2")){
                    //二楼楼告警列表
                    alarmInfoList = alarmService.getAlarmInfoListTwoFloor(page_num,page_size);
                }
            }
            JSONObject obj = new JSONObject();
            obj.put("page_num",page_num);
            obj.put("page_size",page_size);
            obj.put("total_num",alarmInfoList.size()==0?"0":alarmInfoList.get(0).getTotal());
            JSONArray array = new JSONArray();
            for (AlarmDTO alarm:alarmInfoList){
                AlarmInfoResponse response = new AlarmInfoResponse();
                response.setDevice_name(alarm.getDeviceName());
                response.setDevice_type(alarm.getDeviceType());
                response.setDevice_type_name(alarm.getDeviceTypeName());
                response.setAlarm_id(alarm.getAlarmId());
                response.setAlarm_reason_code(alarm.getAlarmReasonCode());
                response.setAlarm_status(alarm.getAlarmStatus());
                response.setAlarm_level(alarm.getAlarmLevel());
                response.setAlarm_description(alarm.getAlarmDescription());
                response.setAlarm_type(alarm.getAlarmType());
                response.setAlarm_time(String.valueOf(Timestamp.valueOf(alarm.getAlarmTime()).getTime()));
                array.add(response);
            }
            obj.put("alarm_list_info",array.toString());
            return StereoResult.SUCCESS().MESSAGE("设备已处理告警信息获取成功").CONTENT(obj.toString());

        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("eqpAlarmList error is"+e.getMessage());
        }
    }
    /**
     * 设备告警详情
     * @param device_name
     * @param alarm_id
     * @return
     */
    @RequestMapping("/eqpAlarmDetail")
    public StereoResult eqpAlarmDetail(String device_name, String alarm_id){
        try{
            AlarmDTO alarmDetail = alarmService.getAlarmDetail(device_name, alarm_id);
            JSONObject job = new JSONObject();
            job.put("device_type",alarmDetail.getDeviceName());
            job.put("device_type_name",alarmDetail.getDeviceTypeName());
            job.put("device_name",alarmDetail.getDeviceName());
            job.put("alarm_type",alarmDetail.getAlarmType());
            job.put("alarm_status",alarmDetail.getAlarmStatus());
            job.put("alarm_level",alarmDetail.getAlarmLevel());
            job.put("alarm_reason_code",alarmDetail.getAlarmReasonCode());
            job.put("alarm_time",alarmDetail.getAlarmTime());
            job.put("alarm_description",alarmDetail.getAlarmDescription());
            job.put("alarm_id",alarmDetail.getAlarmId());
            return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("eqpAlarmDetail error is:"+e.getMessage());
        }
    }

    /**
     * 设备运行状态
     * @param device_name
     * @return
     */
    @RequestMapping("/eqpRunStatus")
    public StereoResult eqpRunStatus(String device_name){
		StringUtils.isBlank(device_name);
        JSONObject job = null;
        try{
            EqpDetailDTO eqpRunStatusInfo = eqpStatusService.getEqpRunStatusInfo(device_name);
            job = new JSONObject();
            job.put("running_status", eqpRunStatusInfo.getEqpRunState());
            job.put("device_id", eqpRunStatusInfo.getId());
            job.put("device_name", eqpRunStatusInfo.getEqpName());
            job.put("device_type", eqpRunStatusInfo.getEqpType());
            job.put("device_type_name", EqpTypeTranslateUtils.englishToChinese(eqpRunStatusInfo.getEqpType()));
        }catch (Exception e){
            return StereoResult.UNKNOWNERROR().MESSAGE("eqpRunStatus error is:"+e.getMessage());
        }
        return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
    }
    /**
     * 设备运行状态统计
     * @return
     */
    @RequestMapping("/eqpRunStatusStatistics")
    public StereoResult eqpRunStatusStatistics(@RequestBody Map<String ,Object> map){
        if(map.containsKey("device_types")){
            JSONObject job = new JSONObject();
            JSONArray eqpTypes = JSONArray.fromObject(map.get("device_types"));
            for (int i = 0; i < eqpTypes.size(); i++) {
                String eqpType = eqpTypes.get(i).toString();
                try{
                    List<EqpDetailDTO> runStatusStatistics = eqpDetailService.getEqpRunStatusStatistics(eqpType);
                    JSONArray jsonArray = new JSONArray();
                    for (int j = 0; j < runStatusStatistics.size() ; j++) {
                        EqpDetailDTO eqp = runStatusStatistics.get(j);
                        JSONObject eqpRunStatusInfo = new JSONObject();
                        eqpRunStatusInfo.put("device_status",eqp.getEqpRunState().toUpperCase());
                        eqpRunStatusInfo.put("device_num",eqp.getTotalNumber());
                        jsonArray.add(eqpRunStatusInfo);
                    }
                    job.put(eqpType,jsonArray);
                }catch (Exception e){
                    StereoResult.UNKNOWNERROR().MESSAGE("eqpRunStatusStatistics error is:"+e.getMessage());
                }
            }
            return StereoResult.SUCCESS().MESSAGE("统计数据获取成功").CONTENT(job.toString());
        } else{
            return StereoResult.PARAMERROR(); }
    }
    /**
     * 所有设备运行状态
     * @return
     */
    @RequestMapping("/getAllEqpRunStatus")
    public StereoResult getAllEqpRunStatus(){
        List<EqpDetailDTO> allEqpRunInfoList = eqpDetailService.getAllEqpRunInfoList();
        if(CollectionUtils.isNotEmpty(allEqpRunInfoList)){
            JSONArray jsonArray = new JSONArray();
            for (EqpDetailDTO eqp:allEqpRunInfoList){
                JSONObject jsonObject = new JSONObject();
                String str;
                if(null==eqp.getEqpRunState()||eqp.getEqpRunState().equals("Others")||eqp.getEqpRunState().equals("Other")){
                    str = "STOP";
                }else {
                    str = eqp.getEqpRunState().toUpperCase();
                }
                jsonObject.put("running_status",str);
                jsonObject.put("device_name",eqp.getEqpName());
                jsonObject.put("device_type",eqp.getEqpType());
                jsonObject.put("device_type_name",null==eqp.getEqpType()?"":EqpTypeTranslateUtils.englishToChinese(eqp.getEqpType()));
                jsonArray.add(jsonObject);
            }
            return StereoResult.SUCCESS().MESSAGE("获取所有设备运行状态数据成功").CONTENT(jsonArray.toString());
        }else {
            return StereoResult.ERROR().MESSAGE("获取所有设备运行状态数据失败");
        }
    }
    /*
    *晶棒数量
    * @param device_type
    * @return
    * */
    @RequestMapping("/getRodNum")
    public StereoResult getRodNum(String device_type){
        JSONObject obj = new JSONObject();
        obj.put("device_type",device_type);
        obj.put("device_type_name",EqpTypeTranslateUtils.englishToChinese(device_type));
        obj.put("rod_number",null == eqpRodNumberService.getEqpRodNumber(device_type) ? 0 : eqpRodNumberService.getEqpRodNumber(device_type).getRodNumber());
        return StereoResult.SUCCESS().MESSAGE("获取晶棒数量成功").CONTENT(obj.toString());
    }
    /*
    * 未处理的告警列表
    * @param floor
    * @param device_type
    * @param device_name
    * @return
    * */
    @RequestMapping("/eqpAlarmOpenList")
    public StereoResult eqpAlarmOpenList(@Nullable String floor,@Nullable String device_type,@Nullable String device_name){
        List<AlarmDTO> alarmOpenList = null;
        try {
            if(null == floor && null == device_type && null == device_name){
                alarmOpenList = alarmService.getAlarmOpenList();
            }else if(null != floor){
                if(floor.equals("1")){
                    alarmOpenList = alarmService.getAlarmOpenOneFloor();
                }else if(floor.equals("2")){
                    alarmOpenList = alarmService.getAlarmOpenTwoFloor();
                }
            }else if(null != device_type){
                alarmOpenList = alarmService.getAlarmOpenOneTypeEqp(device_type);
            }else if(null != device_name){
                alarmOpenList = alarmService.getAlarmOpenOneEqp(device_name);
            }
            JSONObject obj = new JSONObject();
            JSONArray array = new JSONArray();
            for (AlarmDTO alarmOpen:alarmOpenList){
                AlarmInfoResponse response = new AlarmInfoResponse();
                response.setDevice_name(alarmOpen.getDeviceName());
                response.setDevice_type(alarmOpen.getDeviceType());
                response.setDevice_type_name(alarmOpen.getDeviceTypeName());
                response.setAlarm_id(alarmOpen.getAlarmId());
                response.setAlarm_reason_code(alarmOpen.getAlarmReasonCode());
                response.setAlarm_status(alarmOpen.getAlarmStatus());
                response.setAlarm_level(alarmOpen.getAlarmLevel());
                response.setAlarm_description(alarmOpen.getAlarmDescription());
                response.setAlarm_type(alarmOpen.getAlarmType());
                response.setAlarm_time(String.valueOf(Timestamp.valueOf(alarmOpen.getAlarmTime()).getTime()));
                array.add(response);
            }
            obj.put("alarm_list_info",array.toString());
            return StereoResult.SUCCESS().MESSAGE("获取未处理告警信息数据成功").CONTENT(obj.toString());
        }catch (Exception e){
            return StereoResult.ERROR().MESSAGE("获取未处理告警信息数据失败").MESSAGE(e.getMessage());
        }
    }
}