package com.kitri.board.service;

import java.util.List;

import com.kitri.board.model.ReboardDto;

public interface ReboardService {
	
	int writeArticle(ReboardDto reboardDto);
	int replyArticle(ReboardDto reboardDto);
	List<ReboardDto> listArticle(int bcode, int pg, String key, String word);
	ReboardDto viewArticle(int seq);
	ReboardDto getArticle(int seq);
	int modifyArticle(ReboardDto reboardDto);
	int deleteArticle(ReboardDto reboardDto);
	
}
