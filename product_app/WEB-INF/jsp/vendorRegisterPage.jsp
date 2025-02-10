<%@page import="java.util.Objects"%>
<%@page import="data.VendorDto"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>仕入先登録</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<%-- Google Fontsの読み込み --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
	<header>
		<nav>
			<a href="<%=request.getContextPath() %>/">商品管理アプリ</a>
		</nav>
	</header>
	<main>
		<% // 最後に送信されたデータを取得
		String vendorCode = request.getParameter("vendor_code");
		String vendorName = request.getParameter("vendor_name");
		
		// NULLは空文字に置き換え
		vendorCode = Objects.toString(vendorCode, "");
		vendorName = Objects.toString(vendorName, "");

		%>
		<article class="registration">
			<h1>仕入先登録</h1>
			<%
			// 失敗メッセージがあれば表示
			String failureMessage = (String) request.getAttribute("failureMessage");
			if(failureMessage != null && !failureMessage.isEmpty()){
				out.println("<p class='failure'>" + failureMessage + "</p>");
			}
			%>
			<div class="back">
				<a href="<%=request.getContextPath() %>/vendor_list" class="btn">&lt; 戻る</a>
			</div>
			<form action="<%=request.getContextPath() %>/vendor_create" method="post" class="registration-form">
				<div>
					<label for="vendor_code">仕入先コード</label>
					<input type="number" id="vendor_code" name="vendor_code" min="0" max="100000000" value="<%= vendorCode %>" required>
					<label for="vendor_name">仕入先名</label>
					<input type="text" id="vendor_name" name="vendor_name" maxlength="50" value="<%= vendorName %>" required>
				</div>
				<button type="submit" class="submit-btn" name="submit" value="create">登録</button>
			</form>
		</article>
	</main>
	<footer>
		<p class="copyright">&copy; 商品管理アプリ All rights reserved.</p>
	</footer>
</body>
</html>