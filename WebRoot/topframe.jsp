<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet" href="css/common.css" type="text/css" />
<title>Video Classification System</title>
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(function(){	
	//top nav
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	
</script>
</head>

<body style="background:url(image/topbg.gif) repeat-x;">

    <div class="topleft">
     <img src="image/logo1.png" alt="logo" title="System HomePage" /> 
    </div>
</body>
</html>

