<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Servlet/JSPの基礎を学ぼう</title>
</head>
<body>

	<h2>入力内容をご確認ください。</h2>
	<p>問題なければ「確定」、修正する場合は「キャンセル」をクリックしてください。</p>
	<table border="1">
		<tr>
			<th>項目</th>
			<th>入力内容</th>
		</tr>
		<tr>
			<td>氏名</td>
			<td>${name}</td>
		</tr>
		<tr>
			<td>メールアドレス</td>
			<td>${email}</td>
		</tr>
		<tr>
			<td>住所</td>
			<td>${address}</td>
		</tr>
		<tr>
			<td>電話番号</td>
			<td>${phoneNumber}</td>
		</tr>
	</table>
	
	<%
	// バリデーションチェック結果
	ArrayList<String> errorList = (ArrayList<String>) request.getAttribute("errorList");
	
	// エラーメッセージがあれば確定ボタン非活性
	String disabled = (errorList != null) ? "disabled" : "";
	
	%>
	
	<p>
		<button onClick="location.href='<%= request.getContextPath() %>/complete';" <%= disabled %>>確定</button>
		<button onClick="history.back()">キャンセル</button>
	</p>
	
	<%
	if(errorList != null){
		for(String errorMessage : errorList){
			out.println("<font color=\"red\">" + errorMessage + "</font><br>");
		}
	}
	%>
</body>
</html>