<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kitri.member.model.MemberDto"%>
<%
String root = request.getContextPath();

MemberDto memberDto = new MemberDto();
memberDto.setId("java1");
memberDto.setName("김정남");
memberDto.setEmail1("java1");
memberDto.setEmail2("naver.com");

session.setAttribute("userInfo", memberDto);

response.sendRedirect(root + "/boardadmin?act=boardmenu");
%>    

<!-- 
1번
DB >> 레벨 / 스텝 보고
대부분 문제는 DB에 넣을떄 문제 -->