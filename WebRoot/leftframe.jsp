<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" />
			<script language="JavaScript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
			<title>Left Nav</title>
			<script type="text/javascript">
			$(function(){	
				//Nav switch
				$(".menuson li").click(function(){
					$(".menuson li.active").removeClass("active")
					$(this).addClass("active");
				});
				
				$('.title').click(function(){
					var $ul = $(this).next('ul');
					$('dd').find('ul').slideUp();
					if($ul.is(':visible')){
						$(this).next('ul').slideUp();
					}else{
						$(this).next('ul').slideDown();
					}
				});
			})	
			</script>
	</head>

	<body style="background:#f0f9fd;">
	    <dl class="leftmenu">
	    <dd>
	    <div class="title">
	    <span><img src="image/leftico01.png" alt="lefticon" /></span>Function List
	    </div>
	    	<ul class="menuson">
		        <li><cite></cite><a href="<%=path%>/mainframe.jsp" target="rightFrame">Video Collection</a><i></i></li>
		        <li><cite></cite><a href="<%=path%>/uploadVideo?type=query" target="rightFrame">Upload Video</a><i></i></li>
	        </ul>    
	    </dd>
	    </dl>
	</body>
</html>
