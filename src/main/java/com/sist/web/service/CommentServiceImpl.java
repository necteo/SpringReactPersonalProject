package com.sist.web.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.dto.CommentDTO;
import com.sist.web.entity.Comment;
import com.sist.web.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentRepository cRepo;

	@Override
	public List<CommentDTO> commentListData(String isbn) {
		return cRepo.findByIsbn(isbn).stream().map(CommentDTO::new).toList();
	}

	@Override
	public void commentInsert(CommentDTO dto) {
		Comment vo = dto.createComment();
		vo.setRegdate(LocalDate.now());
		cRepo.save(vo);
	}

	@Override
	public void commentDelete(int no) {
		Comment vo = cRepo.findByNo(no).orElseThrow();
		cRepo.delete(vo);
	}

	@Override
	public void commentUpdate(int no, String msg) {
		Comment vo = cRepo.findByNo(no).orElseThrow();
		vo.setMsg(msg);
		vo.setNo(no);
		cRepo.save(vo);
	}
}