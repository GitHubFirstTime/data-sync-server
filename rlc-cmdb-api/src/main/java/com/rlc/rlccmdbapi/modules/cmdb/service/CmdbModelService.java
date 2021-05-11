package com.rlc.rlccmdbapi.modules.cmdb.service;

import com.google.common.collect.Lists;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.persistence.service.CrudService;
import com.rlc.rlccmdbapi.modules.cmdb.dao.CmdbModelDao;
import com.rlc.rlccmdbapi.modules.cmdb.entity.CmdbModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CmdbModelService extends CrudService<CmdbModelDao, CmdbModel> {
    public Page<CmdbModel> findPage(Page<CmdbModel> page, CmdbModel cmdbModel) {
        return super.findPage(page, cmdbModel);
    }
    public List<CmdbModel> findModelList(CmdbModel cmdbModel){
        List<CmdbModel> modelList = Lists.newArrayList();
        try {
            modelList=dao.findModelList(cmdbModel);
        } catch (Exception e) {
            error("模型列表查询失败",e);
        }
        return modelList;
    }
    public CmdbModel getByModelId(String modelId){
        CmdbModel cmdbModel=new CmdbModel();
        try {
            cmdbModel = dao.getByModelId(modelId);
        } catch (Exception e) {
            error("getByModelId",e);
        }
        return cmdbModel;
    }

    public List<CmdbModel> getModelByCategory(String modelCategoryId){
        List<CmdbModel> modelList = Lists.newArrayList();
        try {
            modelList = dao.getModelByCategory(modelCategoryId);
        } catch (Exception e) {
            error("getModelByCategory",e);
        }
        return modelList;
    }
//    @Transactional(rollbackFor = Exception.class)
//    public void deleted(CmdbModel cmdbModel){
//        try {
//            dao.delete(cmdbModel);
//            int a = 1/0;
//            deleteModelProp(cmdbModel);
//        } catch (Exception e) {
//            error("delete",e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//    }

    public List<CmdbModel>  findByModelName(String modelName) throws Exception{
        return dao.findByModelName(modelName);
    }
}
