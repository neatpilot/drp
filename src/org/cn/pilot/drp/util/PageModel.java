package org.cn.pilot.drp.util;

import java.util.List;

public class PageModel<E> {
	// result list
	private List<E> list;

	// #total records
	private int totalRecords;

	// #/page
	private int pageSize;

	// #page
	private int pageNo;

	/**
	 * return NO(total pages)
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return ((totalRecords - 1) / pageSize + 1);
	}

	/**
	 * return the first page number
	 * 
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * return the last page number
	 * 
	 * @return
	 */
	public int getBottomPageNo() {
		return getTotalPages();
	}

	/**
	 * return previous page number
	 * 
	 * @return
	 */
	public int getPreviousPageNo() {
		return 1 == pageNo ? 1 : (pageNo - 1);
	}

	public int getNextPageNo() {
		return getBottomPageNo() == pageNo ? pageNo : (pageNo + 1);
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
