package com.common.base.vo.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.common.base.constant.CommonConstant;

/**
 * 分页Model
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class PageListVo<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 第几页
	 */
	private int currPage = 1;

	/**
	 * 每页条数
	 */
	private int pageSize = CommonConstant.PAGE_SIZE;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 总数
	 */
	private long total;

	/**
	 * 结果对象
	 */
	private List<T> rows = new ArrayList<T>();

	public PageListVo() {
	}

	public PageListVo(long total, List<T> datas) {
		this.total = total;
		this.rows = datas;
		this.pageCount = (((int) this.total - 1) / this.pageSize) + 1;
	}

	public PageListVo(long total, List<T> datas, int currPage, int pageSize) {
		this.total = total;
		this.rows = datas;
		this.pageCount = (((int) this.total - 1) / this.pageSize) + 1;
		this.currPage = currPage;
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		if (currPage <= 0) {
			this.currPage = 1;
		} else {
			this.currPage = currPage;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		if (this.total > 0 && this.pageSize > 0 && this.pageCount == 0) {
			this.pageCount = (((int) this.total - 1) / this.pageSize) + 1;
		}
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
