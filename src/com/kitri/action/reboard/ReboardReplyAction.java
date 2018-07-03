package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;
import com.kitri.member.model.MemberDto;
import com.kitri.util.ParameterCheck;

public class ReboardReplyAction implements Action {

	private static ReboardReplyAction reboardReplyAction;
	
	static {
		reboardReplyAction = new ReboardReplyAction();
	}
	
	private ReboardReplyAction() {}

	public static ReboardReplyAction getReboardReplyAction() {
		return reboardReplyAction;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "/reboard/writefail.jsp";
		HttpSession session = request.getSession(); 
		MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");
		if(memberDto != null) { 
			ReboardDto reboardDto = new ReboardDto();	
			reboardDto.setId(memberDto.getId());
			reboardDto.setName(memberDto.getName());
			reboardDto.setEmail(memberDto.getEmail1() + "@" + memberDto.getEmail2());
			reboardDto.setSubject(request.getParameter("subject"));
			reboardDto.setContent(request.getParameter("content"));
			reboardDto.setBcode(ParameterCheck.naNToZero(request.getParameter("bcode")));
			//넘어오는값이 당연히 있어야 하기때문에 에러를 발생시켜야 한다.
			//0값 체크를 하면 안된다.
			//원글의 ref, lev, step, pseq
			reboardDto.setRef(Integer.parseInt(request.getParameter("ref")));
			reboardDto.setLev(Integer.parseInt(request.getParameter("lev")));
			reboardDto.setStep(Integer.parseInt(request.getParameter("step")));
			reboardDto.setPseq(Integer.parseInt(request.getParameter("pseq")));
			
			//새글이아니고, 리플라이이다.
			int seq = ReboardServiceImpl.getReboardService().replyArticle(reboardDto);
			if(seq != 0) {
				path = "/reboard/writeok.jsp?seq=" + seq + "&";
			}
		} else {
			path = "/index.jsp?";//TODO 나중에 로그인 페이지로 이동시켜라.
		}
		return path;
	}
		
}
