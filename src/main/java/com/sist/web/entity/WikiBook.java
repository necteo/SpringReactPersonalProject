package com.sist.web.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "wikibook")
@Data
public class WikiBook {
	
	@Id
	private String isbn;
	
	private String title;
	private String poster;
	private String author;
	private String translator;
	private int page;
	private int price;
	private Date pubdate;
	private String series;
	private String type;
	private String link;
	private int hit;

}