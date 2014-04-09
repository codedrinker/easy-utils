<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form method=get action="http://www.google.com/search">
	<input type=text name=q> <input type=submit name=btnG
		value="Google 搜索"> <input type=hidden name=ie value=UTF-8>
	<input type=hidden name=oe value=GB2312> <input type=hidden
		name=hl value=zh-CN> <input type=hidden name=domains
		value="www.hicc.cn"> <input type=hidden name=sitesearch
		value="www.hicc.cn">
</form>
<!--Baidu站内搜索开始-->
<form action="http://www.baidu.com/baidu">
	<input type=text name=word> <input type="submit"
		value="Baidu 搜索"> <input name=tn type=hidden value="bds">
	<input name=cl type=hidden value="3"> <input name=ct
		type=hidden value="2097152"> <input name=si type=hidden
		value="www.hicc.cn">
</form>


