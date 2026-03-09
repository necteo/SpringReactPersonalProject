package com.sist.web.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wikibook")
@Getter
@NoArgsConstructor
public class WikiBook {
	@Id
	private String isbn;
	
	private String title;
	private String poster;
	private String author;
	private String translator;
	private int page;
	private int price;
	private LocalDate pubdate;
	private String series;
	private String type;
	private String link;
	private int hit;
}