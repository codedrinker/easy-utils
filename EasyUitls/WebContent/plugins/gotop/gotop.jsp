<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'gotop.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<%@ include file="/commons/include.jsp"%>
<script type="text/javascript" src="${ctx }/jquery/go-top.js"></script>
<script type="text/javascript" src="${ctx }/jquery/jquery-1.4.2.js"></script>
<script type="text/javascript">
	(new GoTop()).init({
		pageWidth : 1000,
		nodeId : 'go-top',
		nodeWidth : 50,
		distanceToBottom : 125,
		distanceToPage : 20,
		hideRegionHeight : 130,
		text : 'Top'
	});
</script>
<style type="text/css">
a#go-top {
	background: url(images/gotop.gif) no-repeat;
}

a#go-top:hover {
	background: url(images/gotop.gif) no-repeat;
}

a#go-top {
	background: #E6E6E6;
	width: 50px;
	height: 25px;
	text-align: center;
	text-decoration: none;
	line-height: 25px;
	color: #999;
}

a#go-top:hover {
	background: #CCC;
	color: #333;
}
</style>
</head>

<body>
	<div style="text-align: center; width:1000px; margin: auto;">
		<div style="width: 1000px;height: 1000px; background-color:gray;"></div>
	</div>
	<a href="" id="go-top"><img src="images/gotop.gif"></a>
</body>
</html>
