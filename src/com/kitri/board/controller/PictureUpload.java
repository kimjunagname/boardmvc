package com.kitri.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/picup")
public class PictureUpload extends HttpServlet {
   private static final long serialVersionUID = 1L;
    
   private String rootDirectory;
   private int maxPostSize;
   private String encoding;

    
   //서블릿 컨택스트 > jsp의 어플리케이션, 내장객체
   @Override
   public void init(ServletConfig config) throws ServletException{
      //ServletConfig
	   super.init(config);
	   ServletContext context = config.getServletContext();
	   rootDirectory = context.getRealPath("/upload");

	   //context >> (프로젝트 안에서 실제 저장하는 위치를 가져와라)
	   System.out.println("rootDirectory >>>>>>>> " + rootDirectory);
	   //uploade상의 실제 경로를 사용해야 한다. //javax 사용
	   //어플리케이션 = 프로젝트(내장객체)
	   
	   //ServletContext는 인터페이스라서 new 못함
	   //getServletContext 가 가지고 있는 getRealPath사용
	   //Hrrpservlet을 사용하면, 가장 일반적인 서블릿을 가져온다.
	   //하지만, web.xml 를 고려하지 않아서, 문제발생, 그래서  Servletcontext를 통해서 
	   //프로젝트 실제 저장됨
	   maxPostSize = 5 * 1024 * 1024; //1 바이트임
	   encoding = "UTF-8";
	   //ISO-8859-1 한글처리 안됨
   }
    
    
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DateFormat df = new SimpleDateFormat("yyMMdd");
      
      String today = df.format(new Date()); //today is 오늘 날짜
      // 업로드에다가 파일 올리면, 날짜 별로 정리하는것
      //만들어라
      String saveDirectory = rootDirectory + File.separator + today;
      //File.separator - 윈도우 OS 따라 구분자 자동 생성
      //윈도우 : \\
      //나머지 : /
      //폴더 만들기
      File dir = new File(saveDirectory);
      //이걸가지고 파일 객체를 만들어라
      if(!dir.exists()) {
    	  dir.mkdirs();
    	  //makdirs >> 중간에 
    	  
    	  //존재하면 안만들고, 존재하지 않으면 만든다
    	  //dir이 존재하지 않으면(파일이 으면)
      }
     

   MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());    
   System.out.println("제목>>>>>>>>>>>>>" + multi.getParameter("subject"));
   System.out.println("내용>>>>>>>>>>>>>" + multi.getParameter("content"));
   System.out.println("원본파일이름>>>>>>>>>>>>>" + multi.getParameter("picture"));
   System.out.println("원본파일이름>>>>>>>>>>>>>" + multi.getOriginalFileName("picture"));
   
   System.out.println("실제저장파일이름>>>>>>>>>>>>>" + multi.getParameter("picture"));
   System.out.println("실제저장파일이름>>>>>>>>>>>>>" + multi.getFilesystemName("picture"));

   System.out.println("bcode>>>>>>>>>>>>>" + multi.getParameter("bcode"));
   
   //DB 저장  - 제목, 내용, 원본파일 이름, 실제저장파일이름, 
   //pictureUpload 는 한번만 실행되기 때문에 하나만 한거임 / reboardWrite거 참고 하면된다.
   //그담부터는 게시판 참고해서 만들어야 한다.
   //<img src="/upload/${arcticle.savefolder}/${article.originalPictuer}"> >>> 사진이 출력이된다.
   //멤버 디티오
   //앨범디티오
   //서비스 >> DAO
   //불러오기
   
   
   }

}