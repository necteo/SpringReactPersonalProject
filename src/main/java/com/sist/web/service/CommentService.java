package com.sist.web.service;

import java.util.List;

import com.sist.web.dto.CommentDTO;

public interface CommentService {
	public List<CommentDTO> commentListData(String isbn);
	public void commentInsert(CommentDTO dto);
	public void commentDelete(int no);
	public void commentUpdate(int no, String msg);
}