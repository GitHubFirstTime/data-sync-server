package com.rlc.rlccmdbapi.modules.cmdb.dao;

import com.rlc.rlcbase.persistence.CrudDao;
import com.rlc.rlcbase.persistence.annotation.DS;
import com.rlc.rlcbase.persistence.annotation.MyBatisDao;
import com.rlc.rlccmdbapi.modules.cmdb.entity.CmdbModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("cmdbdb")
@MyBatisDao
public interface CmdbModelDao extends CrudDao<CmdbModel> {
    public List<CmdbModel> findModelList(CmdbModel cmdbModel) throws Exception;

    /**
     * 根据modelId查询模型
     * @param modelId
     * @return
     * @throws Exception
     */
    public CmdbModel getByModelId(@Param("modelId") String modelId) throws Exception;

    public List<CmdbModel> getModelByCategory(@Param("modelCategoryId") String modelCategoryId) throws Exception;

    public List<CmdbModel>  findByModelName(@Param("modelName") String modelName) throws Exception;
}
