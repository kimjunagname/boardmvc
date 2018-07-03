package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;

public class ReboardMoveReplyAction implements Action{

	
    private static ReboardMoveReplyAction reboardMoveReplyAction;
	
	static {
		reboardMoveReplyAction = new ReboardMoveReplyAction();
	}
	
	private ReboardMoveReplyAction() {}

	
	public static ReboardMoveReplyAction getReboardMoveReplyAction() {
		return reboardMoveReplyAction;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int seq = Integer.parseInt(request.getParameter("seq"));
		ReboardDto reboardDto = ReboardServiceImpl.getReboardService().getArticle(seq);
		request.setAttribute("article", reboardDto);
		return "/reboard/reply.jsp?";
	}

}
