package com.rlc.rlcbase.pageHelper.page;

import com.rlc.rlcbase.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Page<T> {
    /**
     * 当前页码
     */
    private Integer currentPage = 1;
    /**
     * 每页数据条数
     */
    private Integer pageSize = 10;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总条数
     */
    private Integer totalSize=0;
    private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc
    // 分页结果
    private List<T> list;

    public Page() {
        this.pageSize = -1;
    }
    /**
     * 构造方法
     * @param request 传递 repage 参数，来记住页码
     * @param response 用于设置 Cookie，记住页码
     */
    public Page(HttpServletRequest request, HttpServletResponse response){
        this(request, response, -2);
    }

    /**
     * 构造方法
     * @param request 传递 repage 参数，来记住页码
     * @param response 用于设置 Cookie，记住页码
     * @param defaultPageSize 默认分页大小，如果传递 -1 则为不分页，返回所有数据
     */
    public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize){
        // 设置页码参数（传递repage参数，来记住页码）
        String no = request.getParameter("pageNo");
        if (StringUtils.isNumeric(no)){
            CookieUtils.setCookie(response, "pageNo", no);
            this.setCurrentPage(Integer.parseInt(no));
        }else if (request.getParameter("repage")!=null){
            no = CookieUtils.getCookie(request, "pageNo");
            if (StringUtils.isNumeric(no)){
                this.setCurrentPage(Integer.parseInt(no));
            }
        }
        // 设置页面大小参数（传递repage参数，来记住页码大小）
        String size = request.getParameter("pageSize");
        if (StringUtils.isNumeric(size)){
            CookieUtils.setCookie(response, "pageSize", size);
            this.setPageSize(Integer.parseInt(size));
        }else if (request.getParameter("repage")!=null){
            no = CookieUtils.getCookie(request, "pageSize");
            if (StringUtils.isNumeric(size)){
                this.setPageSize(Integer.parseInt(size));
            }
        }else if (defaultPageSize != -2){
            this.pageSize = defaultPageSize;
        }
        // 设置排序参数
        String orderBy = request.getParameter("orderBy");
        if (StringUtils.isNotBlank(orderBy)){
            this.setOrderBy(orderBy);
        }
    }
    public Page(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
//        this.totalPage = totalPage;
        this.totalPage = (int)(totalSize / (this.pageSize < 1 ? 20 : this.pageSize) );
    }

    public Integer getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    /**
     * 获取 Hibernate FirstResult
     */
    public int getFirstResult(){
        int firstResult = (getCurrentPage() - 1) * getPageSize();
        if (firstResult >= getTotalSize()) {
            firstResult = 0;
        }
        return firstResult;
    }
    /**
     * 获取 Hibernate MaxResults
     */
    public int getMaxResults(){
        return getPageSize();
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}