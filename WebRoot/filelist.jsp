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
		<title>File List</title>
		<style type="text/css"> 
.align-center{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 
</style> 
		<script type="text/javascript">
		$(function(){	
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
 <body>
 
 
	<div class="place">
	    <span>Current classification：</span>
	    <ul class="placeul">
	    <li>${requestScope.type}</li>
	    </ul>
	</div>
    
    
    <table class="filetable">
	    <thead>
	    	<tr>
	        <th>Title</th>
	        <th>Upload date</th>
	        <th>Type</th>
	        <th>Operation</th>
	        </tr>    	
	    </thead>
	    <tbody>
		    <c:forEach items="${requestScope.videoList}" var="video" varStatus="sta">
		    	<tr>
		        <td><img src="image/f01.png" />${video.vname}</td>
		        <td>${video.createDate}</td>
		        <td>${video.vtype}</td>
		        <td><a href="<%=path%>/viewVideo?id=${video.id}">Play</a>&nbsp;&nbsp;&nbsp;&nbsp;
		        <a href="<%=path%>/delVideo?id=${video.id}">Delete</a></td>
		        </tr>
		    </c:forEach>
	    </tbody>
    </table>
    
    <div class="formbody">
	    <ul class="forminfo">
	         <li><input name="" type="button" class="btn" onclick="window.history.go(-1);" value="Back"/></li>
	    </ul>
    </div>  
     </body>
</html>
