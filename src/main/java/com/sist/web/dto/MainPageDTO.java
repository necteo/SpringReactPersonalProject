package com.sist.web.dto;

import java.util.List;

import lombok.Data;

@Data
public class MainPageDTO {
	private BookItem main;
	private List<BookItem> newList;
	private List<BookItem> hitList;
}
