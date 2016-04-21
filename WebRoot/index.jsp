<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/select.css" type="text/css" />
<link rel="stylesheet" href="css/style.css" type="text/css" />
<title>Video Classification</title>
</head>
<frameset rows="90,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="topframe.jsp" name="topFrame" frameborder="no" scrolling="no" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset name="myFrame" cols="180,*" frameborder="no" border="0" framespacing="0">
    <frame src="leftframe.jsp" name="leftFrame" frameborder="no" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
   <frame src="mainframe.jsp" name="rightFrame" frameborder="no" scrolling="no" noresize="noresize" id="rightFrame" title="rightFrame" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes>
</html>