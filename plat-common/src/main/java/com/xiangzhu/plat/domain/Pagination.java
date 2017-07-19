package com.xiangzhu.plat.domain;


import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by liluoqi on 2017/7/9.
 * 分页参数
 */
@XmlRootElement(name = "pagination")
public class Pagination {
    /**
     * 当前页默认为1
     */
    @QueryParam("currentPage")
    private int currentPage = 1;
    /**
     * 页面大小 默认展示10条
     */
    @QueryParam("pageSize")
    private int pageSize = 10;

    private long totalCount = 0;

    public Pagination() {
    }

    public Pagination(int currentPage, int pageSize) {
        if(currentPage>0){
            this.currentPage = currentPage;
        }
        if(pageSize>0){
            this.pageSize = pageSize;
        }
    }

    public Pagination withTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    /**
     * 获取开始索引
     *
     * @return long
     */
    public long getStartIndex() {
        return (currentPage - 1) * pageSize;
    }

    /**
     * 获取查询数量
     *
     * @return long
     */
    public long getTotalSelect() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
