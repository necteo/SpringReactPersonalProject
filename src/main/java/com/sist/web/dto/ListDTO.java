package com.sist.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class ListDTO<T> {
	List<T> list;
	int curpage, totalpage, startPage, endPage;
}
