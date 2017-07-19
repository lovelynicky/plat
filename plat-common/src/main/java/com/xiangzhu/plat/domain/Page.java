package com.xiangzhu.plat.domain;

import java.util.List;

/**
 * Created by liluoqi on 2017/7/9.
 * 分页结果
 */
public class Page<T extends BaseModel> {
    private long totalCount;
    private int totalPages;
    private int currentPage;
    private int previousPage;
    private int nextPage;
    private int lastPage;
    private int firstPage;
    private int pageSize;
    private List<T> data;

    public Page() {
        this.pageSize = 10;
        this.currentPage = 1;
        this.firstPage = 1;
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(Pagination pagination) {
        this.pageSize = pagination.getPageSize();
        this.currentPage = pagination.getCurrentPage();
        this.firstPage = 1;
        if (pagination.getTotalCount() > 0) {
            this.withTotalCount(pagination.getTotalCount());
        }
    }

    public Page withTotalCount(long totalCount) {
        this.totalCount = totalCount;
        int divided = Long.valueOf(this.totalCount / pageSize).intValue();
        this.totalPages = this.totalCount % this.pageSize == 0 ? divided : divided + 1;
        this.nextPage = this.totalPages > 1 && currentPage < totalPages ? this.currentPage + 1 : 1;
        this.lastPage = this.totalPages;
        this.previousPage = this.currentPage == 1 ? this.currentPage : this.currentPage - 1;
        return this;
    }

    public Page withData(List<T> data) {
        this.data = data;
        return this;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
