package com.education.dto.base;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: haojie
 * @qq :1422471205
 * @CreateTime: 2021-10-23-20-49
 */
public class ResponsePageDto<T> implements Serializable {
    private List<T> data;
    private Long total;
    private Integer pageSize;
    private Integer currentPage;
    private Boolean success = true;

    public ResponsePageDto(List<T> data, Long total, Integer pageSize, Integer currentPage) {
        this.data = data;
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.success = true;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrent() {
        return this.currentPage;
    }

    public void setCurrent(Integer current) {
        this.currentPage = currentPage;
    }
}
