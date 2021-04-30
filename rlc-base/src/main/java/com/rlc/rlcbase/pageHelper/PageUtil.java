package com.rlc.rlcbase.pageHelper;

//import com.github.pagehelper.PageInfo;
import com.rlc.rlcbase.pageHelper.page.Page;
import com.rlc.rlcbase.pageHelper.page.PageInfoListResult;

import java.util.List;

public class PageUtil<T> {
    public static <T> PageInfoListResult<T> makePageListResult(Page page, List<T> dataList) {
        PageInfoListResult<T> pageInfoListResult = new PageInfoListResult();
        //这是自己new的一个Page对象，用来存放分页信息
        Page definePage = new Page(page.getCurrentPage(), page.getPageSize());
        definePage.setTotalSize(page.getTotalSize());
        definePage.setTotalPage(page.getPageSize());
        pageInfoListResult.setDataList(dataList);
        pageInfoListResult.setPage(definePage);
        return pageInfoListResult;
    }
    //
    public static <T> PageInfoListResult<T> makePageListResult(Integer currentPage, Integer pageSize, List<T> dataList) {
        PageInfoListResult<T> pageInfoListResult = new PageInfoListResult();
        Page page = new Page(currentPage, pageSize);
//        PageInfo<T> pageInfo = new PageInfo<T>(dataList);
//        page.setTotalPage(pageInfo.getPages());
////        page.setTotalSize(pageInfo.getTotal());
//        pageInfoListResult.setDataList(dataList);
//        pageInfoListResult.setPage(page);
        return pageInfoListResult;
    }
}