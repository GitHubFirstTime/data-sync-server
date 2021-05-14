package com.rlc.rlcfmbapi.modules.interface_utils;

import org.apache.commons.lang3.StringUtils;

public class EqpTypeTranslateUtils {

    public static String englishToChinese(String name){
        if(StringUtils.isEmpty(name)){
            return "";
        }
        switch (name){
            case "YLK":
                return "原料库";
            case "GHK":
                return "固化库";
            case "LTK":
                return "单晶库";
            case "CPK":
                return "成品库";
            case "ZB":
                return "粘棒机";
            case "XQ":
                return "线切机";
            case "TJ":
                return "脱胶机";
            case "QX":
                return "清洗机";
            case "FX":
                return "分选机";
            case "SBZ":
                return "小包装";
            case "MBZ":
                return "中包装";
            case "LBZ":
                return "大包装";
            case "ZZ":
                return "蒸煮";
             default:
                return name;
        }
    }

}
