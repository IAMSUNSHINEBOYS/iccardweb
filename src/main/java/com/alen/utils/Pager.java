package com.alen.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * 分页工具类
 * 
 * @author LK
 * @version 1.0
 * @date 2020/5/15 18:09
 */
public class Pager {

	private int currentPage = 1;// 当前页
	private int pageSize = 18;// 每页大小
	private Page<?> page;// 分页插件
	private String orderBys = ""; // 排序
	private Set<String> sortable = new HashSet<String>();// 可排序属性
	private Set<String> distinctOrder = new HashSet<String>();// 除去重复属性排序

	public void setCurrentPage(Integer currentPage) {
		if (currentPage != null)
			this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize != null && pageSize <= 200)
			this.pageSize = pageSize;
	}

	public void setBigPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean getIsFirst() {
		return 1 == currentPage ? true : false;
	}

	public boolean getHasPrevious() {
		return getIsFirst() ? false : true;
	}

	public boolean getIsLast() {
		return getCurrentPage() == getTotalPage() ? false : true;
	}

	public boolean getHasNext() {
		return getIsLast() ? false : true;
	}

	public int getTotalPage() {
		return page != null ? page.getPages() : 0;
	}

	public long getTotalSize() {
		return page != null ? page.getTotal() : 0;
	}

	public void startPage() {
		startPage(true);
	}

	/**
	 * 添加可排序字段
	 * 
	 * @param fields
	 *            排序字段
	 */
	public Pager addSortable(String... fields) {
		if (fields != null) {
			for (String field : fields) {
				sortable.add(field);
			}
		}
		return this;
	}

	/**
	 * 添加排序
	 * 
	 * @param field
	 *            排序字段
	 * @param order
	 *            0降序、1升序
	 */
	public Pager addOrder(String field, int order) {
		if (sortable.contains(field) && (order == 0 || order == 1)) {
			boolean asc = order == 1;
			if (distinctOrder.add(field)) {
				if (orderBys.length() == 0) {
					orderBys = field + (asc ? " ASC" : " DESC");
				} else {
					orderBys += ", " + field + (asc ? " ASC" : " DESC");
				}
			}
		}
		return this;
	}

	public void startPage(boolean count) {
		page = PageHelper.startPage(currentPage, pageSize);
		page.setCount(count);
		if (orderBys.length() == 0){
			orderBys="current_timestamp";
		}
		PageHelper.orderBy(orderBys);
	}

	public void exportPage() {
		if (getIsFirst()) {
			startPage(true);
		} else {
			startPage(false);
		}
	}
}
