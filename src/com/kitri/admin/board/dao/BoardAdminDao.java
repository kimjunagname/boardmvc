package com.kitri.admin.board.dao;

import java.util.List;

import com.kitri.admin.board.model.*;

public interface BoardAdminDao {

	List<BoardListDto> getBoardMenu();
	List<CategoryDto> getCategory();
	void makeCategory(String cname);
	List<BoardTypeDto> getBoardType();
	void makeBoard(BoardListDto boardListDto);
	
}
