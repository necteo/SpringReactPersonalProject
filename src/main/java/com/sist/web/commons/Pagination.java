package com.sist.web.commons;

import java.util.List;

import com.sist.web.dto.ListDTO;

public class Pagination {
	private static final int BLOCK = 10;

	public static final int MAIN_BLOCK= 6;
	public static final int ROW_SIZE = 12;
	
	public static <T> ListDTO<T> createPagination(List<T> list, int page, int totalpage) {
		ListDTO<T> dto = new ListDTO<>();
		dto.setList(list);
		dto.setCurpage(page);
		dto.setTotalpage(totalpage);
		dto.setStartPage((page - 1) / BLOCK * BLOCK + 1);
		int endPage = (page - 1) / BLOCK * BLOCK + BLOCK;
		dto.setEndPage(endPage > totalpage ? totalpage : endPage);
		return dto;
	}
}
