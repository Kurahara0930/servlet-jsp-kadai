<%@page import="data.ProductDto"%>
<%@page import="data.VendorDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>仕入先編集</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<%-- Google Fontsの読み込み --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
    <header>
        <nav>
            <a href="<%= request.getContextPath() %>/">商品管理アプリ</a>
        </nav>
    </header>
    <main>
    	<% // 最後に送信されたデータを取得
    	String id = request.getParameter("id");
    	String vendorCode = request.getParameter("vendor_code");
    	String vendorName = request.getParameter("vendor_name");
    	
    	// NULLは空文字に置き換え
    	id = Objects.toString(id, "");
    	vendorCode = Objects.toString(vendorCode,"");
    	vendorName = Objects.toString(vendorName,"");
    	
    	// 仕入先データリストを取得
    	ArrayList<VendorDto> vendorList = (ArrayList<VendorDto>) request.getAttribute("vendorList");
    	
    	if( vendorList != null && !vendorList.isEmpty() ) {
    		// 編集対象のレコードは1件だけのため、先頭要素だけを取得すればＯＫ
    		VendorDto vendorData = vendorList.get(0);
    		
    		// 編集対象のデータを各変数にセット
    		id = Integer.toString(vendorData.getId());
    		vendorCode = Integer.toString(vendorData.getVendorCode());
    		vendorName = vendorData.getVendorName();
    	}
    	%>
    	<article class="registration">
    		<h1>仕入先編集</h1>
    		<%
    		// 失敗メッセージがあれば表示
    		String failureMessage = (String) request.getAttribute("failureMessage");
    		if(failureMessage != null && !failureMessage.isEmpty()){
    			out.println("<p class='failure'>" + failureMessage + "</p>");
    		}
    		%>
    		<div class="back">
    			<a href="<%= request.getContextPath() %>/vendor_list" class="btn">&lt; 戻る</a>
    		</div>
    		<form action="<%= request.getContextPath() %>/vendor_update?id=<%= id %>" method="post" class="registration-form">
    			<div>
					<label for="vendor_code">仕入先コード</label>
					<input type="number" id="vendor_code" name="vendor_code" min="0" max="100000000" value="<%= vendorCode %>" required>
					<label for="vendor_name">仕入先名</label>
					<input type="text" id="vendor_name" name="vendor_name" maxlength="50" value="<%= vendorName %>" required>
				</div>
				<button type="submit" class="submit-btn" name="submit" value="update">更新</button>
    		</form>
    	</article>
    </main>
	<footer>
		<p class="copyright">&copy; 商品管理アプリ All right reserved.</p>
	</footer>
</body>
</html>