package com.sist.web.entity;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
@DynamicInsert
@DynamicUpdate
public class Comment {
	@Id
	private int no;
	
	@Column(updatable = false, insertable = true)
	private String isbn;
	
	@Column(updatable = false, insertable = true)
	private String id;
	
	private String name;
	private String msg;
	private LocalDate regdate;
}
