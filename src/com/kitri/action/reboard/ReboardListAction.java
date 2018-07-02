package com.kitri.action.reboard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.action.Action;
import com.kitri.board.model.ReboardDto;
import com.kitri.board.service.ReboardServiceImpl;
import com.kitri.common.service.CommonServiceImpl;
import com.kitri.util.PageNavigation;
import com.kitri.util.ParameterCheck;

public class ReboardListAction implements Action {

	private static ReboardListAction reboardListAction;
	
	static {
		reboardListAction = new ReboardListAction();
	}
	
	private ReboardListAction() {}

	public static ReboardListAction getReboardListAction() {
		return reboardListAction;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bcode = ParameterCheck.naNToZero(request.getParameter("bcode"));
		int pg = ParameterCheck.naNToOne(request.getParameter("pg"));
		String key = ParameterCheck.nullToBlank(request.getParameter("key"));
		String word = ParameterCheck.nullToBlank(request.getParameter("word"));
		
		List<ReboardDto> list = ReboardServiceImpl.getReboardService().listArticle(bcode, pg, key, word);
		PageNavigation navigator = CommonServiceImpl.getCommonService().getPageNavigation(bcode, pg, key, word);
		navigator.setRoot(request.getContextPath());
		navigator.makeNavigator();
		
		request.setAttribute("list", list);
		request.setAttribute("navigator", navigator);
		
		return "/reboard/list.jsp?";
	}

}















