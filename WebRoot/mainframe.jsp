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
		<style type="text/css"> 
.align-center{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 
</style> 
		<script type="text/javascript">
		$(function(){	
			//Nav Swtich
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


 
    <div class="formbody">
    <div class="formtitle"><span>Classification</span></div>
 
    <div class="toolsli"  >
    <ul class="toollist">
    <li><a href="<%=path%>/listVideo?type=100"><img src="<%=path%>/image/i01.png" alt="car"/></a><h2>Car</h2></li>
    <li><a href="<%=path%>/listVideo?type=101"><img src="<%=path%>/image/i02.png" alt="Finance"/></a><h2>Finance</h2></li>
    <li><a href="<%=path%>/listVideo?type=102"><img src="<%=path%>/image/i03.png" alt="IT"/></a><h2>IT</h2></li>
    <li><a href="<%=path%>/listVideo?type=103"><img src="<%=path%>/image/i04.png" alt="Health"/></a><h2>Health</h2></li>
    <li><a href="<%=path%>/listVideo?type=104"><img src="<%=path%>/image/i05.png" alt="Sports"/></a><h2>Sports</h2></li>
    <li><a href="<%=path%>/listVideo?type=105"><img src="<%=path%>/image/i06.png" alt="Travel"/></a><h2>Travel</h2></li>
    <li><a href="<%=path%>/listVideo?type=106"><img src="<%=path%>/image/i07.png" alt="Education"/></a><h2>Education</h2></li>
    <li><a href="<%=path%>/listVideo?type=107"><img src="<%=path%>/image/i08.png" alt="Employment"/></a><h2>Employment</h2></li>
    <li><a href="<%=path%>/listVideo?type=108"><img src="<%=path%>/image/i09.png" alt="Culture"/></a><h2>Culture</h2></li>
    <li><a href="<%=path%>/listVideo?type=109"><img src="<%=path%>/image/i10.png" alt="Military"/></a><h2>Military</h2></li>
    <li><a href="<%=path%>/listVideo?type=110"><img src="<%=path%>/image/i11.png" alt="Other"/></a><h2>Other</h2></li>
    </ul>
    </div>
     </div>
</html>
