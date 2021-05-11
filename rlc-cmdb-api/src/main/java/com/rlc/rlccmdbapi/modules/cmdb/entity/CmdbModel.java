package com.rlc.rlccmdbapi.modules.cmdb.entity;

import com.rlc.rlcbase.persistence.DataEntity;

import java.util.Date;

/**
 * 模型
 */
public class CmdbModel extends DataEntity<CmdbModel> {
    private String modelId;

    private String modelName;

    private String modelIcon;

    private String modelCategoryId;

    private String modelTags;

    private Integer modelVer;

    private String modelBase;

    //列表排序用
    private Date categoryCreateDate;
    //显示用字段，多个名称‘/’分隔
    private String modelCategoryNames;
    private String topCategoryId;//分类顶级分类ID
    //
    private CmdbModelDisplay modelDisplay;
//    private CmdbModelBgColor modelBgColor;
//    private CmdbModelBorder modelBorder;
    //标签检索用字段
    private String tagSearch;
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelIcon() {
        return modelIcon;
    }

    public void setModelIcon(String modelIcon) {
        this.modelIcon = modelIcon;
    }

    public String getModelCategoryId() {
        return modelCategoryId;
    }

    public void setModelCategoryId(String modelCategoryId) {
        this.modelCategoryId = modelCategoryId;
    }

    public String getModelTags() {
        return modelTags;
    }

    public void setModelTags(String modelTags) {
        this.modelTags = modelTags;
    }

    public Integer getModelVer() {
        return modelVer;
    }

    public void setModelVer(Integer modelVer) {
        this.modelVer = modelVer;
    }

    public String getModelBase() {
        return modelBase;
    }

    public void setModelBase(String modelBase) {
        this.modelBase = modelBase;
    }

    public String getTagSearch() {
        return tagSearch;
    }

    public void setTagSearch(String tagSearch) {
        this.tagSearch = tagSearch;
    }

    public String getModelCategoryNames() {
        return modelCategoryNames;
    }

    public void setModelCategoryNames(String modelCategoryNames) {
        this.modelCategoryNames = modelCategoryNames;
    }

    public Date getCategoryCreateDate() {
        return categoryCreateDate;
    }

    public void setCategoryCreateDate(Date categoryCreateDate) {
        this.categoryCreateDate = categoryCreateDate;
    }

    public String getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(String topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    public CmdbModelDisplay getModelDisplay() {
        return modelDisplay;
    }

    public void setModelDisplay(CmdbModelDisplay modelDisplay) {
        this.modelDisplay = modelDisplay;
    }

 /*   public CmdbModelBgColor getModelBgColor() {
        return modelBgColor;
    }

    public void setModelBgColor(CmdbModelBgColor modelBgColor) {
        this.modelBgColor = modelBgColor;
    }

    public CmdbModelBorder getModelBorder() {
        return modelBorder;
    }

    public void setModelBorder(CmdbModelBorder modelBorder) {
        this.modelBorder = modelBorder;
    }*/
}
