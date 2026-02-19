package com.sist.web.dto;

import java.util.List;

public record BookDetailPageDTO(
		BookDetailDTO detail,
		List<CommentDTO> comments) {

}
