package com.drcome.project.common.service;

import lombok.Data;

@Data
public class PageDTO {
	
	int currentPage; 
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	
	//기본생성자
	public PageDTO() {};
	
	
	public PageDTO(int currentPage, int total) {
		
	    
		this.total = total;
		this.endPage=(int)(Math.ceil(currentPage/3.0)) * 3;  //페이지버튼 3개씩 
		this.startPage = this.endPage -2;
		
		int realEnd = (int)Math.ceil(total/5.0);
		
		if(realEnd< this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage >1;
		this.next = this.endPage < realEnd;
		
	}
	
}
