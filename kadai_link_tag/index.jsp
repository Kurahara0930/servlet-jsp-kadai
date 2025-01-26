<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Servlet/JSP_課題</title>
</head>
<body>
	<a href="<%= request.getContextPath() %>/top?name=侍太郎">名前「侍太郎」をServletに送信</a>
	<% String name = (String) request.getAttribute("samurai"); 
	if (name != null) { %>
		<p>Servletからデータを受信しました:  <%= name %>さん、こんにちは！</p>
	<% } %>
</body>
</html>