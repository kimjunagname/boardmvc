package com.kitri.admin.board.service;

import java.util.List;

import com.kitri.admin.board.dao.BoardAdminDaoImpl;
import com.kitri.admin.board.model.*;

public class BoardAdminServiceImpl implements BoardAdminService {

	private static BoardAdminService boardAdminService;
	
	static {
		boardAdminService = new BoardAdminServiceImpl();
	}
	
	private BoardAdminServiceImpl() {}
	
	public static BoardAdminService getBoardAdminService() {
		return boardAdminService;
	}

	@Override
	public List<BoardListDto> getBoardMenu() {
		return BoardAdminDaoImpl.getBoardAdminDao().getBoardMenu();
	}

	@Override
	public List<CategoryDto> getCategory() {
		return null;
	}

	@Override
	public void makeCategory(String cname) {

	}

	@Override
	public List<BoardTypeDto> getBoardType() {
		return null;
	}

	@Override
	public void makeBoard(BoardListDto boardListDto) {

	}

}
