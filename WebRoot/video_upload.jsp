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
		<link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" />
		<script language="JavaScript" src="<%=path%>/js/jquery-1.9.1.min.js"></script>
		<title></title>
		<style type="text/css"> 
.align-center{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 
</style> 
</head>
<body>

  <form method="post" enctype="multipart/form-data" >
   <div class="rightinfo">
	    <ul class="imglist">
	    <c:forEach items="${requestScope.videoList}" var="video" varStatus="sta">
	    <li class="selected">
	    <span> <img src="<%=path%>/image/img06.png" alt="video"/></span>
	    <h2><a href="#">${video.vname}</a></h2>
	    <p><a href="<%=path%>/playVideo?id=${video.id}">Classification</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=path%>/delVideo?id=${video.id}">Delete</a></p>
	    </li>
	    </c:forEach>
	    </ul>
   </div>
    <div class="formbody">
	    <div class="formtitle"><span>Upload Video</span></div>
	    <ul class="forminfo">
	    <li><label>Video Title</label><input id="videoName" name="videoName" type="text" class="dfinput" /><i>The title does not exceed 30 characters.</i></li>
	    <li><label>Video File</label><input id="videoFile" name="file" type="file"/><i></i></li>
	    <li><label>&nbsp;</label><input name="" type="button" class="btn" onclick="submitForm();" value="Upload"/></li>
	    </ul>
    </div>
    </form>
</body>

</html>

<script language="javascript">


    function submitForm(){
        var videoName = $('#videoName').val();
        var videoFile = $('#videoFile').val();
        if(videoName==''){
            alert("The title of video cannot be NULL!");
            return;
        }
        if(videoFile==''){
            alert("Please choose video file!");
            return;
        }
    	document.forms[0].action = "<%=path%>/uploadVideo";
		document.forms[0].submit();
    }

</script>