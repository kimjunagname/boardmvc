package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;
import com.kitri.member.model.MemberDto;
import com.kitri.util.ParameterCheck;

public class ReboardViewAction implements Action {

	private static ReboardViewAction reboardViewAction;
	
	static {
		reboardViewAction = new ReboardViewAction();
	}
	
	private ReboardViewAction() {}

	public static ReboardViewAction getReboardViewAction() {
		return reboardViewAction;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "/index.jsp?";
		HttpSession session = request.getSession(); 
		MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");
		if(memberDto != null) { 
			int seq = ParameterCheck.naNToZero(request.getParameter("seq"));
			if(seq != 0) {
				ReboardDto reboardDto = ReboardServiceImpl.getReboardService().viewArticle(seq);
				request.setAttribute("article", reboardDto);
				path = "/reboard/view.jsp?";
			}
		} else {
			path = "/index.jsp?";//TODO 나중에 로그인 페이지로 이동시켜라.
		}
		return path;
	}

}
