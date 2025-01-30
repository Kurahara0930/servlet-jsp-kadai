<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Servlet/JSPの基礎を学ぼう</title>
</head>
<body>
	<%
	String userName = (String) request.getAttribute("userName");
	String userEmail = (String) request.getAttribute("userEmail");
	String userAddress = (String) request.getAttribute("userAddress");
	String userPhoneNumber = (String) request.getAttribute("userPhoneNumber");
	
	userName = Objects.toString(userName, "");
	userEmail = Objects.toString(userName, "");
	userAddress = Objects.toString(userName, "");
	userPhoneNumber = Objects.toString(userName, "");
 	%>
	<h2>個人情報入力フォーム</h2>
	<form action="<%= request.getContextPath() %>/confirm" method="post">
		<table>
			<tr>
				<td>氏名</td>
				<td><input type="text" name="name" value=<%= userName %>></td>
			</tr>
			<tr>
				<td>メールアドレス</td>
				<td><input type="text" name="email" value=<%= userEmail %>></td>
			</tr>
			<tr>
				<td>住所</td>
				<td><input type="text" name="address" value=<%= userAddress %>></td>
			</tr>
			<tr>
				<td>電話番号</td>
				<td><input type="text" name="phone_number" value=<%= userPhoneNumber %>></td>
			</tr>
		</table><br>
		<input type="submit" value="送信">
	</form>
</body>
</html>