package com.sist.web.service;

import com.sist.web.dto.BookItem;
import com.sist.web.dto.ListDTO;
import com.sist.web.dto.MainPageDTO;

public interface BookService {
	MainPageDTO mainPageData();
	ListDTO<BookItem> bookListData(int page);
}
