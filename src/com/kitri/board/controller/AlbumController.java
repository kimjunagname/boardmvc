package com.kitri.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.factory.BoardActionFactory;
import com.kitri.util.*;

@WebServlet("/album")
public class AlbumController extends HttpServlet {
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
				path = "/album/write.jsp?" + queryString;
				PageMove.redirect(request, response, path);
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