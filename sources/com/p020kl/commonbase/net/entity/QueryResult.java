package com.p020kl.commonbase.net.entity;

import com.p020kl.commonbase.bean.BaseMeasureEntity;
import java.util.List;

/* renamed from: com.kl.commonbase.net.entity.QueryResult */
public class QueryResult<T extends BaseMeasureEntity> {
    private int curPage;
    private boolean firstPage;
    private boolean lastPage;
    private int pages;
    private List<T> rows;
    private int rowsSize;
    private int total;

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int i) {
        this.total = i;
    }

    public int getPages() {
        return this.pages;
    }

    public void setPages(int i) {
        this.pages = i;
    }

    public int getCurPage() {
        return this.curPage;
    }

    public void setCurPage(int i) {
        this.curPage = i;
    }

    public boolean isFirstPage() {
        return this.firstPage;
    }

    public void setFirstPage(boolean z) {
        this.firstPage = z;
    }

    public boolean isLastPage() {
        return this.lastPage;
    }

    public void setLastPage(boolean z) {
        this.lastPage = z;
    }

    public int getRowsSize() {
        return this.rowsSize;
    }

    public void setRowsSize(int i) {
        this.rowsSize = i;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> list) {
        this.rows = list;
    }
}
