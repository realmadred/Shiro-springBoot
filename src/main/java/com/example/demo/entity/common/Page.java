package com.example.demo.entity.common;

import java.io.Serializable;

/**
 * @auther Administrator
 * @date 2017/9/26
 * @description 分页查询
 */
public class Page implements Serializable {

    private static final long serialVersionUID = -1404251225704729903L;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private Integer page = DEFAULT_PAGE;
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public Page() {
    }

    public Page(final Integer page, final Integer pageSize) {
        this.page = page;
        setPageSize(pageSize);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize > 100 ? 100 : pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize ;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
