package com.rlc.rlccmdbapi.modules.cmdb.entity.vo;

import com.rlc.rlccmdbapi.modules.cmdb.entity.CmdbModel;
import lombok.Data;

import java.util.List;

@Data
public class ModelCategoryVO {
    private String name;
    List<CmdbModel> childList;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CmdbModel> getChildList() {
        return childList;
    }

    public void setChildList(List<CmdbModel> childList) {
        this.childList = childList;
    }

    public ModelCategoryVO(String name, List<CmdbModel> childList) {
        this.name = name;
        this.childList = childList;
    }
}
