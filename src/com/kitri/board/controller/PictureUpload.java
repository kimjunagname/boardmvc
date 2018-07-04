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

    
   //���� ���ý�Ʈ > jsp�� ���ø����̼�, ���尴ü
   @Override
   public void init(ServletConfig config) throws ServletException{
      //ServletConfig
	   super.init(config);
	   ServletContext context = config.getServletContext();
	   rootDirectory = context.getRealPath("/upload");

	   //context >> (������Ʈ �ȿ��� ���� �����ϴ� ��ġ�� �����Ͷ�)
	   System.out.println("rootDirectory >>>>>>>> " + rootDirectory);
	   //uploade���� ���� ��θ� ����ؾ� �Ѵ�. //javax ���
	   //���ø����̼� = ������Ʈ(���尴ü)
	   
	   //ServletContext�� �������̽��� new ����
	   //getServletContext �� ������ �ִ� getRealPath���
	   //Hrrpservlet�� ����ϸ�, ���� �Ϲ����� ������ �����´�.
	   //������, web.xml �� ������� �ʾƼ�, �����߻�, �׷���  Servletcontext�� ���ؼ� 
	   //������Ʈ ���� �����
	   maxPostSize = 5 * 1024 * 1024; //1 ����Ʈ��
	   encoding = "UTF-8";
	   //ISO-8859-1 �ѱ�ó�� �ȵ�
   }
    
    
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      DateFormat df = new SimpleDateFormat("yyMMdd");
      
      String today = df.format(new Date()); //today is ���� ��¥
      // ���ε忡�ٰ� ���� �ø���, ��¥ ���� �����ϴ°�
      //������
      String saveDirectory = rootDirectory + File.separator + today;
      //File.separator - ������ OS ���� ������ �ڵ� ����
      //������ : \\
      //������ : /
      //���� �����
      File dir = new File(saveDirectory);
      //�̰ɰ����� ���� ��ü�� ������
      if(!dir.exists()) {
    	  dir.mkdirs();
    	  //makdirs >> �߰��� 
    	  
    	  //�����ϸ� �ȸ����, �������� ������ �����
    	  //dir�� �������� ������(������ ����)
      }
     

   MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());    
   System.out.println("����>>>>>>>>>>>>>" + multi.getParameter("subject"));
   System.out.println("����>>>>>>>>>>>>>" + multi.getParameter("content"));
   System.out.println("���������̸�>>>>>>>>>>>>>" + multi.getParameter("picture"));
   System.out.println("���������̸�>>>>>>>>>>>>>" + multi.getOriginalFileName("picture"));
   
   System.out.println("�������������̸�>>>>>>>>>>>>>" + multi.getParameter("picture"));
   System.out.println("�������������̸�>>>>>>>>>>>>>" + multi.getFilesystemName("picture"));

   System.out.println("bcode>>>>>>>>>>>>>" + multi.getParameter("bcode"));
   
   //DB ����  - ����, ����, �������� �̸�, �������������̸�, 
   //pictureUpload �� �ѹ��� ����Ǳ� ������ �ϳ��� �Ѱ��� / reboardWrite�� ���� �ϸ�ȴ�.
   //�״���ʹ� �Խ��� �����ؼ� ������ �Ѵ�.
   //<img src="/upload/${arcticle.savefolder}/${article.originalPictuer}"> >>> ������ ����̵ȴ�.
   //��� ��Ƽ��
   //�ٹ���Ƽ��
   //���� >> DAO
   //�ҷ�����
   
   
   }

}