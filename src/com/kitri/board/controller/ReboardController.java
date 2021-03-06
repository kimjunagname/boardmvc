package com.kitri.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.factory.BoardActionFactory;
import com.kitri.util.*;

@WebServlet("/reboard")
public class ReboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		int bcode = ParameterCheck.naNToZero(request.getParameter("bcode"));
		int pg = ParameterCheck.naNToOne(request.getParameter("pg"));
		String key = ParameterCheck.nullToBlank(request.getParameter("key"));
		String word = ParameterCheck.nullToBlank(request.getParameter("word"));
		String queryString = "bcode=" + bcode + "&pg=" + pg + "&key=" + key + "&word=" + Encoder.urlUtf(word);
		System.out.println(queryString);
		
		String path = "/index.jsp";
		if(bcode == 0) {
			System.out.println("전체 목록 가라...");
			PageMove.redirect(request, response, path);
		} else {		
			if("mvwrite".equals(act)) {
				path = "/reboard/write.jsp?" + queryString;
				PageMove.redirect(request, response, path);
			} else if("writearticle".equals(act)) {
				path = BoardActionFactory.getReboardWriteAction().execute(request, response);
				path += queryString;
				PageMove.redirect(request, response, path);
			} else if("listarticle".equals(act)) {
				path = BoardActionFactory.getReboardListAction().execute(request, response);
				path += queryString;
				PageMove.forward(request, response, path);
				
			} else if("viewarticle".equals(act)) {
				path = BoardActionFactory.getReboardViewAction().execute(request, response);
				path += queryString;
				PageMove.forward(request, response, path);
			} else if("mvmodify".equals(act)) {
				
				//글수정 > 컨트롤러(여기 - mvmodify) > 액션 > 서비스 > DAO > modify.jsp > 이동
				
				path = BoardActionFactory.getReboardMoveModifyAction().execute(request, response);
				path += queryString;
				PageMove.forward(request, response, path);
				
			} else if("modifyarticle".equals(act)) {
			// >> 수정 dao 작성 필요
				
				path = BoardActionFactory.getReboardModifyAction().execute(request, response);
				path += queryString;
				PageMove.forward(request, response, path);
			
			} else if("deletearticle".equals(act)) {
				
				path = BoardActionFactory.getReboardDeleteAction().execute(request, response);
				path += queryString;
				PageMove.forward(request, response, path);
			
			} else if("mvreply".equals(act)) {
				//답변형 게시판 시작
				//membermvc - boardmvc 차이점 : 서비스, dao는 그대로, 
				//단, controller에서 기능을 엑션클래스가 많아지고, else if에 길이가 늘어나는 문제점
				//스프링은 class 대신 메소드로 호출 하고, else if대신 메소드화
				
				path = BoardActionFactory.getReboardMoveReplyAction().execute(request, response);
				path += queryString;
				PageMove.forward(request, response, path);
			
			
			}else if("replyarticle".equals(act)) {
				path = BoardActionFactory.getReboardReplyAction().execute(request, response);
				path += queryString;
				PageMove.redirect(request, response, path);
				//wartiarticle 참고
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} else if("".equals(act)) {
				
			} 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(KitriConstance.ENCODING);
		doGet(request, response);
	}

}