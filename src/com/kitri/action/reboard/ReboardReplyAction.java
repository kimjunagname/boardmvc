package com.kitri.action.reboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.action.Action;

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
		// TODO Auto-generated method stub
		return null;
	}

}
