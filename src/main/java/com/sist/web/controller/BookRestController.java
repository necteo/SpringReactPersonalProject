package com.sist.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.BookItem;
import com.sist.web.dto.ListDTO;
import com.sist.web.dto.MainPageDTO;
import com.sist.web.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BookRestController {
	private final BookService bService;
	
	@GetMapping("/")
	public ResponseEntity<MainPageDTO> mainPage() {
		MainPageDTO dto = bService.mainPageData();
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/book/list/{page}")
	public ResponseEntity<ListDTO<BookItem>> bookList(@PathVariable("page") int page) {
		ListDTO<BookItem> dto = bService.bookListData(page);
		return ResponseEntity.ok(dto);
	}
}
