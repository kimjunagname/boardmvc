<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/template/top.jsp"%>
<%@ include file="/commons/board_common.jsp"%>
<%@ include file="/commons/confirm.jsp"%>
<script type="text/javascript">
function listArticle(pg) {
	document.getElementById("act").value = "listarticle";
	document.getElementById("bcode").value = "${bcode}";
	document.getElementById("pg").value = pg;
	document.getElementById("key").value = "${key}";
	document.getElementById("word").value = "${word}";
	
	document.commonform.action = "${root}/reboard";
	document.commonform.submit();
}
</script>
<table width="100%" cellpadding="6" cellspacing="2" border="0"
	bgcolor="#ffffff" style="border: #e1e1e1 solid 1px">
	<tr>
		<td class="bg_board_title" width="100%"><img
			src="${root}/img/board/icon_arrow_08.gif" width="3" height="11"
			border="0" align="absmiddle" hspace="6" vspace="6"> <b>게시판</b>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#e1e1e1"
			style="overflow: hidden; padding: 0px;"></td>
	</tr>
	<tr>
		<td class="bg_menu" width="100%" style="padding: 25px" height="35"
			align="center"><b>게시물이 등록되었습니다.</b><br>
		<br>

		<div align="center">
			<a href="">
			<img src="${root}/img/board/b_wirtecf.gif" width="91" height="21" border="0" align="absmiddle" alt="작성한 글 확인" hspace="10"></a>
			
			<a href="javascript:listArticle('${pg}');">
			<img src="${root}/img/board/poll_listbu1.gif" width="62" height="21" border="0" align="absmiddle" alt="목록보기" hspace="10"></a>
		</td>
	</tr>
</table>
<br>
<%@ include file="/commons/template/bottom.jsp"%>