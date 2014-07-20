<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'lazyload.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="../jquery/jquery-1.4.2.js"></script>
<script type="text/javascript" src="../jquery/jquery.lazyload.js"></script>
<script type="text/javascript">
	$(function() {
		$("img").lazyload({
			placeholder : "images/grey.jpg",
			effect : "fadeIn"
		});
	});
	$(function() {
		$("img").lazyload({
		effect : "fadeIn"
		});
		});

</script>

</head>
<%--http://www.cnblogs.com/happily/archive/2011/10/20/2219098.html学习来源--%>
<body style="table-layout: auto;text-align: center;">
	<%
		for (int i = 0; i < 200; i++) {
	%>
	<div
		style="width: 150px;height: 150px; float: left; border: 1px; border-color: blue;">
		<img src="images/wangchunlei.jpg" width="150px" height="150px">
	</div>
	<%
		}
	%>
</body>
</html>
