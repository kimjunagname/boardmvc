package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;
import com.kitri.member.model.MemberDto;
import com.kitri.util.ParameterCheck;

public class ReboardMoveModifyAction implements Action {

	private static ReboardMoveModifyAction reboardMoveModifyAction;
	
	static {
		reboardMoveModifyAction = new ReboardMoveModifyAction();
	}
	
	private ReboardMoveModifyAction() {}

	public static ReboardMoveModifyAction getReboardMoveModifyAction() {
		return reboardMoveModifyAction;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path ="/index.jsp?";
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");
		if(memberDto != null) {
			int seq = ParameterCheck.naNToZero(request.getParameter("seq"));
			System.out.println("ReboardMoveModifyAction seq >>>>>" + seq);
			
			ReboardDto reboardDto = ReboardServiceImpl.getReboardService().getArticle(seq);
			request.setAttribute("article", reboardDto);
			
			//글수정 > 컨트롤러 > 액션(여기 - mvmodify) > 서비스 > DAO > modify.jsp > 이동
			
			
			path = "/reboard/modify.jsp?seq=" + seq + "&";
			
		}else {
			path = "/index.jsp";
		}
		return path;
	}
}
