package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;
import com.kitri.member.model.MemberDto;
import com.kitri.util.ParameterCheck;

public class ReboardModifyAction implements Action {

	private static ReboardModifyAction reboardModifyAction;
	
	static {
		reboardModifyAction = new ReboardModifyAction();
	}
	
	private ReboardModifyAction() {}

	public static ReboardModifyAction getReboardModifyAction() {
		return reboardModifyAction;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = "/reboard/writefail.jsp";
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");
		
		
		if(memberDto != null) {
			ReboardDto reboardDto = new ReboardDto();	
			reboardDto.setSubject(request.getParameter("subject"));
			reboardDto.setContent(request.getParameter("content"));
			reboardDto.setSeq(ParameterCheck.naNToZero(request.getParameter("seq")));
			
			int seq = ParameterCheck.naNToZero(request.getParameter("seq"));
			
			System.out.println("subject>>"+ reboardDto.getSubject());
			
			int cnt = ReboardServiceImpl.getReboardService().modifyArticle(reboardDto);
			request.setAttribute("article", reboardDto);
			//글수정 > 컨트롤러 > 액션(여기 - mvmodify) > 서비스 > DAO > modify.jsp > 이동
			
			//path = "/reboard/view.jsp?seq=" + seq + "&";
			path = "/reboard/modifyok.jsp?";
			
		}else {
			path = "/reboard/modifyfail.jsp?";
		}
		return path;
	}
}

