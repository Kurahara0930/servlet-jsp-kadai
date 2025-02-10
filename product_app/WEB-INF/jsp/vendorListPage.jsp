<%@page import="data.VendorDto"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品一覧</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<%-- Google Fontsの読み込み --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
</head>
<body>
    <header>
        <nav>
            <a href="<%=request.getContextPath()%>/">商品管理アプリ</a>
        </nav>
    </header>
    <main>
    	<%
    	// 最後に送信されたデータを取得
    	// 検索キーワード
    	String keyword = request.getParameter("keyword");
    	keyword = Objects.toString(keyword, ""); // NULLは空文字に置き換え
    	
    	// 並び替え方向
    	String order = request.getParameter("order");
    	order = Objects.toString(order, ""); // NULLは空文字に置き換え
    	
    	// 現在のページを取得
    	Integer currentPage = (Integer) request.getAttribute("currentPage");
    	int iCurrentPage = (currentPage != null) ? currentPage : 1; // NULLは1に設定
    	
    	// 必要ページ数を取得
    	Integer totalPages = (Integer) request.getAttribute("totalPages");
    	int iTotalPages = (totalPages != null) ? totalPages : 1; // NULLは1に設定
    	%>
    	<article class="products">
    		<h1>商品一覧</h1>
    		<%
    		// 成功メッセージがあれば表示
    		String successMessage = (String) request.getAttribute("successMessage");
    		if(successMessage != null && !successMessage.isEmpty()) {
    			out.println("<p class='success'>" + successMessage + "</p>");
    		}
    		
    		// 失敗メッセージがあれば表示
    		String failureMessage = (String) request.getAttribute("failureMessage");
    		if(failureMessage != null && !failureMessage.isEmpty()){
    			out.println("<p class='failure'>" + failureMessage + "</p>");
    		}
    		%>
    		<div class="products-ui">
    			<div>
    				<a href="<%= request.getContextPath() %>/vendor_list?order=desc&keyword=<%=keyword%>">
    					<img src="images/desc.png" alt="降順に並べ替え" class="sort-img">
    				</a>
    				<a href="<%= request.getContextPath() %>/vendor_list?order=asc&keyword=<%=keyword%>">
    					<img src="images/asc.png" alt="昇順に並べ替え" class="sort-img">
    				</a>
    				<form action="<%= request.getContextPath() %>/vendor_list" method="get" class="search-form">
    					<input type="hidden" name="order" value="<%=order%>">
    					<input type="text" class="search-box" placeholder="仕入先名で検索" name="keyword" value="<%=keyword%>">
    				</form>
    			</div>
    			<a href="<%= request.getContextPath() %>/vendor_register" class="btn vendor-btn">仕入先登録</a>
    		</div>
    		<table class="products-table">
    			<tr>
    				<th class="hidden-id">ID</th>
    				<th>仕入先コード</th>
    				<th>仕入先名</th>
    				<th>編集</th>
    				<th>削除</th>
    			</tr>
    			<%
    			// 仕入先データリストを取得
    			ArrayList<VendorDto> vendorList = (ArrayList<VendorDto>) request.getAttribute("vendorList");
    			
    			if(vendorList != null){
    				// 仕入先データを1レコードずつ表形式で出力
    				for(VendorDto vendor : vendorList){
    					out.println("<tr>");
    					out.println("<td class='hidden-id'>" + vendor.getId() + "</td>");
    					out.println("<td>" + vendor.getVendorCode() + "</td>");
    					out.println("<td>" + vendor.getVendorName() + "</td>");
    					out.println("<td><a href='" + request.getContextPath() + "/vendor_edit?id="
    								 + vendor.getId() + "'><img src='images/edit.png' alt='編集' class='edit-icon'></a></td>");
    					out.println("<td><a href='" + request.getContextPath() + "/vendor_delete?id=" + vendor.getId()
						 + "' onclick=\"return confirm('この操作は元に戻せません。仕入先名：" + vendor.getVendorName() + "を本当に削除しますか？')\">"
						 + "<img src='images/delete.png' alt='削除' class='delete-icon'></a></td>");
    					out.println("</tr>");
    				}
    			}
    			%>
    		</table>
    		<div class="pagination">
    		<%
    		if(failureMessage == null || failureMessage.isEmpty()) {
	    		if(iCurrentPage > 1) {
	    			out.println("<a class='pagination-link-first' href='" + request.getContextPath() + "/vendor_list?page=1&order=" + order + "&keyword=" + keyword + "'>&lt;最初へ</a>");
	    			out.println("<a class='pagination-link' href='" + request.getContextPath() + "/vendor_list?page=" + (iCurrentPage - 1) + "&order=" + order + "&keyword=" + keyword + "'>前へ</a>");
	    		}
	    			out.println("<span class='pagination-text'>ページ " + iCurrentPage + "/" + iTotalPages + "</span>");
	       		if(iCurrentPage < iTotalPages) {
	       			out.println("<a class='pagination-link' href='" + request.getContextPath() + "/vendor_list?page=" + (iCurrentPage + 1) + "&order=" + order + "&keyword=" + keyword + "'>次へ</a>");
	       			out.println("<a class='pagination-link-last' href='" + request.getContextPath() + "/vendor_list?page=" + iTotalPages + "&order=" + order + "&keyword=" + keyword + "'>最後へ&gt;</a>");
	       		}
    		}
    		%>
    		</div>
    	</article>
    </main>
    <footer>
    	<p class="copyright">&copy; 商品管理アプリ All rights reserved.</p>
    </footer>
</body>
</html>