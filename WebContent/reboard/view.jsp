<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/template/top.jsp"%>
<%@ include file="/commons/board_common.jsp"%>
<%@ include file="/commons/confirm.jsp"%>
<script>
function moveWrite() {
	document.getElementById("act").value = "mvwrite";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = "1";
	document.getElementById("key").value = "";
	document.getElementById("word").value = "";
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
}

function listArticle(pg) {
	document.getElementById("act").value = "listarticle";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = pg;
	document.getElementById("key").value = "${key}";
	document.getElementById("word").value = "${word}";
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
}

function totalArticle() {
	document.getElementById("act").value = "listarticle";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = '1';
	document.getElementById("key").value = "";
	document.getElementById("word").value = "";
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
}


function moveWrite() {
	document.getElementById("act").value = "mvmodify";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = '1';
	document.getElementById("key").value = "";
	document.getElementById("word").value = "";
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
	
}

function moveModify() {
	document.getElementById("act").value = "mvmodify";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = '1';
	document.getElementById("key").value = "";
	document.getElementById("word").value = "";
	document.getElementById("seq").value = "${article.seq}";

	alert("seq >>>> " +"${article.seq}");
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
	
	//글수정(여기 - mvmodify) > 컨트롤러 > 액션 > 서비스 > DAO > modify.jsp > 이동
	
	//reboard 삭제 > board 삭제
	//conn.autocommit = fail / commit / catch roolbark
}

function deleteArticle() {
	document.getElementById("act").value = "deletearticle";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = '1';
	document.getElementById("key").value = "";
	document.getElementById("word").value = "";
	document.getElementById("seq").value = "${article.seq}";

	alert("seq >>>> " +"${article.seq}");
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
	//replay 에 답변이 있으면, 삭제 못하게 해야 한다.(if replay =!0)
	//reboard 삭제 > board 삭제
	//conn.autocommit = fail / commit / catch roolbark
}
//현재 글번호를 가져간다
function moveReply(seq) {
	document.getElementById("act").value = "mvreply";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = '1';
	document.getElementById("key").value = "";
	document.getElementById("word").value = "";
	document.getElementById("seq").value = seq;
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
}
</script>
<!-- title -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td><img src="${root}/img/board/m_icon_board.gif" width="9"
			height="9" border="0" align="absmiddle" style="margin-top: -2px">
		<b>자유게시판</b> &nbsp;<font style="font-size: 8pt">|</font>&nbsp; 자유로운 글을
		올리는 공간입니다<br>
		</td>
		<td align="right"></td>
	</tr>
	<tr>
		<td colspan="2" height="19"></td>
	</tr>
</table>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td valign="bottom" nowrap>
			<a href="javascript:moveWrite();">
			<img src="${root}/img/board/btn_write_01.gif" width="64" height="22" border="0" align="absmiddle" alt="글쓰기"></a> 
			<a href="javascript:moveReply('${article.seq}');">
			<img src="${root}/img/board/btn_reply.gif" width="40" height="22" border="0" align="absmiddle" alt="답글"></a>
			
		<c:if test="${article.id == userInfo.id}">
			<a href="javascript:moveModify('${article.seq}');">
			<img src="${root}/img/board/btn_modify.gif"	border="0" align="absmiddle" alt="글수정"></a> 
			
			<a href="javascript:deleteArticle('${article.seq}');">
			<img src="${root}/img/board/btn_delete.gif"	border="0" align="absmiddle" alt="글삭제"></a>
		</c:if>	
		
		</td>
		<td valign="bottom" width="100%" style="padding-left: 4px"></td>
		<td align="right" nowrap valign="bottom">
		<a href="javascript:totalArticle();">최신목록</a> <font color="#c5c5c5">|</font>
		<a href="javascript:listArticle('${pg}');">목록</a> <font color="#c5c5c5">|</font>

		<a href="javascript:goBbsRead();"><img
			src="${root}/img/board/icon_up.gif" border="0" align="absmiddle"
			hspace="3">윗글</a> <font color="#c5c5c5">|</font> <a
			href="javascript:goBbsRead();">아랫글<img
			src="${root}/img/board/icon_down.gif" border="0" align="absmiddle"
			hspace="3"></a></td>
	</tr>
	<tr>
		<td colspan="3" height="5" style="padding: 0px"></td>
	</tr>
</table>

<table border="0" cellpadding="5" cellspacing="0" width="100%">
	<tr>
		<td class="bg_board_title_02" colspan="2" height="2"
			style="overflow: hidden; padding: 0px"></td>
	</tr>
	<tr height="28">
		<td class="bg_board_title" colspan="2" style="padding-left: 14px">
		<b><font class="text"> ${article.subject} </font></b></td>
	</tr>
	<tr>
		<td class="bg_board_title_02" colspan="2" height="1"
			style="overflow: hidden; padding: 0px"></td>
	</tr>
	<tr height="26">
		<td width="100%" style="padding-left: 14px">
		<font class="stext">번호:</font> 
		<font class="text_commentnum">${article.seq}</font> &nbsp; 
		<font class="stext">글쓴이 :</font>
	    <a href="javascript:;" onClick="showSideView();" class="link_board_02">${article.name}</a><br>
		</td>
		<td style="padding-right: 14px" nowrap class="stext">조회 : <font
			class="text_commentnum">${article.hit}</font> &nbsp; 스크랩 : <font
			class="text_commentnum">0</font> &nbsp; 날짜 : <font
			class="text_commentnum">${article.logtime}</font></td>
	</tr>
	<tr>
		<td class="bg_board_title_02" colspan="2" height="1"
			style="overflow: hidden; padding: 0px"></td>
	</tr>
</table>

<table border="0" cellpadding="15" cellspacing="0" width="100%">
	<tr valign="top">
		<td bgcolor="#ffffff" width="100%" class="text"
			style="padding-bottom: 8px; line-height: 1.3" id="clix_content">



		<P>${article.content}</P>



		</td>
		<td nowrap valign="top" align="right" style="padding-left: 0px">

		</td>
	</tr>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td class="bg_board_title_02" height="1"
			style="overflow: hidden; padding: 0px"></td>
	</tr>
</table>

<!-- 하단 페이징 -->
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td colspan="3" height="5" style="padding: 0px"></td>
	</tr>
	<tr valign="top">
		<td nowrap>
			<a href="javascript:moveWrite();">
			<img src="${root}/img/board/btn_write_01.gif" width="64" height="22" border="0" align="absmiddle" alt="글쓰기"></a> 
			
			<a href="javascript:moveReply('${article.seq}');">
			<img src="${root}/img/board/btn_reply.gif" width="40" height="22" border="0" align="absmiddle" alt="답글"></a>
		</td>
		
		<td style="padding-left: 4px" width="100%">
			<a href=""	target="new">
			<img src="${root}/img/board/btn_print.gif"	width="30" height="18" border="0" align="absmiddle" alt="인쇄"></a>
		</td>
		
		<td align="right" nowrap><a href="javascript:totalArticle();">최신목록</a>
			<font color="#c5c5c5">|</font> 
			<a href="javascript:listArticle('${pg}');">목록</a>
			<font color="#c5c5c5">|</font> 
			<a href="javascript:goBbsRead();">
			<img src="${root}/img/board/icon_up.gif" border="0" align="absmiddle" hspace="3">윗글</a> 
			<font color="#c5c5c5">|</font> 
			<a href="javascript:goBbsRead();">아랫글
			<img src="${root}/img/board/icon_down.gif" border="0" align="absmiddle" hspace="3"></a>
		</td>
	</tr>
</table>
<br>
