package com.kitri.admin.board.service;

import java.util.List;

import com.kitri.admin.board.model.*;

public interface BoardAdminService {

	List<BoardListDto> getBoardMenu();
	List<CategoryDto> getCategory();
	void makeCategory(String cname);
	List<BoardTypeDto> getBoardType();
	void makeBoard(BoardListDto boardListDto);
	
//	TODO 나중에 카테고리변경, 게시판 변경 만들자!!!!
}
