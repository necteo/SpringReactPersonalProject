package com.sist.web.dto;

import com.sist.web.entity.WikiBook;

public record BookItem(
		String isbn,
		String title,
		String poster,
		String author,
		int price) {
	
	public BookItem(WikiBook book) {
		this(
			book.getIsbn(), 
			book.getTitle(),
			book.getPoster(),
			book.getAuthor(),
			book.getPrice()
		);
	}
}
