package com.jwzt.datagener.model;

public class PageInfo {

	private int pageSize = 40;//搜索每页返回的数据量（最大记录为100，默认为40条）

	private int currentPage = 1; //当前搜索的页数（默认为第一页）
	
	private int pageNum = 0;     //总页数
	
	private int total;           //总记录数

	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotal() {
		return total;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotal(int total) {
		this.total = total;
		this.pageNum = (this.total + pageSize - 1)/pageSize;
		if(currentPage > this.pageNum)
			currentPage = this.pageNum;
		if(currentPage < 1)
			currentPage = 1;
	}
	
	/**获取开位置*/
	public int getStartIndex(){
		
		return (this.currentPage - 1)* pageSize;
	}
	/**获取结束位置*/
	public int getEndIndex(){
		if(this.currentPage*pageSize>=this.total){
			return this.total;
		}else{
			return this.currentPage*pageSize;
		}
	}	
}
