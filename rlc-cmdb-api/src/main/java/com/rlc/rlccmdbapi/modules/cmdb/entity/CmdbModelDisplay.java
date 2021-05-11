package com.rlc.rlccmdbapi.modules.cmdb.entity;

import com.rlc.rlcbase.persistence.DataEntity;

public class CmdbModelDisplay extends DataEntity<CmdbModelDisplay> {
    private static final long serialVersionUID = 1L;
    private String modelId;

    private String keyWord;//propID

    private String keyValue;

    private String iconId;

    private String bgColor;

    private String borderColor;

    private String mId;//model主键
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
