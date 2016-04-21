<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		
		<link rel="stylesheet" type="text/css" href="<%=path%>/css/play.css" />
		<link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" />
		<script language="JavaScript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
		<script language="JavaScript" src="<%=path%>/js/mediaelement.js"></script>
		<title>View video</title>
		<style type="text/css"> 

.demo{margin:60px auto; text-align:center}
.audio{margin-top:20px}
</style> 
</head>
 
<body>
  <form method="post">
<div id="main">
   <h4 class="top_title"> </h4>
   <div class="demo">
         <video width="360" height="203" id="player1" src="<%=path%>/${requestScope.video.vurl}" type="video/mp4" controls="controls"></video>
   </div>
 <div class="formbody">
    <ul class="forminfo">
   
         <li><input name="" type="button" class="btn" onclick="window.history.go(-1);" value="Back"/></li>
    </ul>
    </div> 
</div> 

  
</form>
</body>
 

</html>

<script type="text/javascript">
	   
 
   MediaElement('player1', {success: function(me) {

	me.play();
	
 
}});

 
</script>
