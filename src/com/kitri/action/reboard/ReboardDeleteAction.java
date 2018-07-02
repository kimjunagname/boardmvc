package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;
import com.kitri.member.model.MemberDto;
import com.kitri.util.ParameterCheck;

public class ReboardDeleteAction implements Action {

	private static ReboardDeleteAction reboardDeleteAction;
	
	static {
		reboardDeleteAction = new ReboardDeleteAction();
	}
	
	private ReboardDeleteAction() {}

	public static ReboardDeleteAction getReboardDeleteAction() {
		return reboardDeleteAction;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = "/reboard/writefail.jsp";
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");

		if(memberDto != null) {
			ReboardDto reboardDto = new ReboardDto();	
			reboardDto.setSeq(ParameterCheck.naNToZero(request.getParameter("seq")));
		
			System.out.println("ReboardDel subject>>"+ reboardDto.getSubject());
			
			int cnt = ReboardServiceImpl.getReboardService().deleteArticle(reboardDto);
			System.out.println("DelAction>>>>>" + cnt);
			request.setAttribute("article", reboardDto);
			
			if(cnt != 0) {
			System.out.println("act - /reboard/deleteok.jsp 여기서 실행");
			path = "/reboard/deleteok.jsp?";
			
			}else {
			path = "/reboard/writefail.jsp?";
			System.out.println("act - /reboard/writefail.jsp 여기서 실행");
			
			}
		}else {
			path = "/index.jsp?";
			System.out.println("act - /index.jsp 여기서 실행");
	}
	return path;

	}
}








