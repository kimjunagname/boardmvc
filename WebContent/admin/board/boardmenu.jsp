<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/template/top.jsp"%>
<style>
.menu_list {
	width: 200px;
}
.menu_heading {
	padding: 5px 10px;
	cursor: pointer;
	position: relative;
	font-weight: bolder;
	text-align: left;
	background-color: #969696;
}

.menu_body {
	display: none;
	text-align: left;
}

.menu_body a {
	display: block;
	background-color: #d1d1d1;
	padding: 10px;
	text-decoration: none;
}

.menu_body a:hover {
	color: #000000;
	text-decoration: underline;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#menu p.menu_heading").click(function() {
		$(this).next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
	});
});

</script>
<div class="menu_list" id="menu">
<c:set var="idx" value="0"/>
<c:forEach varStatus="i" var="board" items="${menulist}">
	<c:if test="${idx != board.ccode}">
	<p class="menu_heading">${board.cname}</p>
	<c:set var="idx" value="${board.ccode}"/>
	<div class="menu_body">
	</c:if>	
	<a href="${root}/${board.control}?act=listarticle&bcode=${board.bcode}&pg=1&key=&word=">
	${board.bname}
	</a>
	<c:if test="${i.index < menulist.size() - 1}">
		<c:if test="${menulist.get(i.index + 1).ccode != idx}">
	</div>
		</c:if>
	</c:if>
</c:forEach>
</div>
<%@ include file="/commons/template/bottom.jsp"%>