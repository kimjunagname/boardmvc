package com.kitri.admin.board.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kitri.admin.board.model.*;
import com.kitri.util.db.DBClose;
import com.kitri.util.db.DBConnection;

public class BoardAdminDaoImpl implements BoardAdminDao {

	private static BoardAdminDao boardAdminDao;

	static {
		boardAdminDao = new BoardAdminDaoImpl();
	}

	private BoardAdminDaoImpl() {
	}

	public static BoardAdminDao getBoardAdminDao() {
		return boardAdminDao;
	}

	@Override
	public List<BoardListDto> getBoardMenu() {
		List<BoardListDto> menu = new ArrayList<BoardListDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select b.bcode, b.bname, b.btype, c.ccode, c.cname, \n");
			sql.append("	case  \n");
			sql.append("		when b.btype = 5 then 'reboard' \n");
			sql.append("		when b.btype = 6 then 'album' \n");
			sql.append("		when b.btype = 7 then 'bbs' \n");
			sql.append("		else 'board' \n");
			sql.append("	end control \n");
			sql.append("from board_list b, category c \n");
			sql.append("where b.ccode = c.ccode \n");
			sql.append("order by bcode asc \n");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardListDto boardListDto = new BoardListDto();
				boardListDto.setBcode(rs.getInt("bcode"));
				boardListDto.setBname(rs.getString("bname"));
				boardListDto.setBtype(rs.getInt("btype"));
				boardListDto.setCcode(rs.getInt("ccode"));
				boardListDto.setCname(rs.getString("cname"));
				boardListDto.setControl(rs.getString("control"));
				
				menu.add(boardListDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		return menu;
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
