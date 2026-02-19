package com.sist.web.dto;

import java.time.format.DateTimeFormatter;

import com.sist.web.entity.WikiBook;

public record BookDetailDTO(
		String isbn,
		String title,
		String author,
		String translator,
		int page,
		int price,
		String pubdate,
		String series,
		String type,
		String link,
		int hit,
		String poster) {

	public BookDetailDTO(WikiBook book) {
		this(
			book.getIsbn(),
			book.getTitle(),
			book.getAuthor(),
			book.getTranslator(),
			book.getPage(),
			book.getPrice(),
			book.getPubdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
			book.getSeries(),
			book.getType(),
			book.getLink(),
			book.getHit(),
			book.getPoster()
		);
	}
}
