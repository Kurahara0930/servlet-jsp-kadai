/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.80
 * Generated at: 2025-02-09 14:31:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import data.VendorDto;
import java.util.Objects;
import java.util.ArrayList;

public final class vendorListPage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.Objects");
    _jspx_imports_classes.add("data.VendorDto");
    _jspx_imports_classes.add("java.util.ArrayList");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPではGET、POST、またはHEADのみが許可されます。 JasperはOPTIONSも許可しています。");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"ja\">\n");
      out.write("<head>\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("<title>商品一覧</title>\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/css/style.css\">\n");
      out.write("\n");
      out.write("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n");
      out.write("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n");
      out.write("<link href=\"https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap\" rel=\"stylesheet\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <header>\n");
      out.write("        <nav>\n");
      out.write("            <a href=\"");
      out.print(request.getContextPath());
      out.write("/\">商品管理アプリ</a>\n");
      out.write("        </nav>\n");
      out.write("    </header>\n");
      out.write("    <main>\n");
      out.write("    	");

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
    	
      out.write("\n");
      out.write("    	<article class=\"products\">\n");
      out.write("    		<h1>商品一覧</h1>\n");
      out.write("    		");

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
    		
      out.write("\n");
      out.write("    		<div class=\"products-ui\">\n");
      out.write("    			<div>\n");
      out.write("    				<a href=\"");
      out.print( request.getContextPath() );
      out.write("/vendor_list?order=desc&keyword=");
      out.print(keyword);
      out.write("\">\n");
      out.write("    					<img src=\"images/desc.png\" alt=\"降順に並べ替え\" class=\"sort-img\">\n");
      out.write("    				</a>\n");
      out.write("    				<a href=\"");
      out.print( request.getContextPath() );
      out.write("/vendor_list?order=asc&keyword=");
      out.print(keyword);
      out.write("\">\n");
      out.write("    					<img src=\"images/asc.png\" alt=\"昇順に並べ替え\" class=\"sort-img\">\n");
      out.write("    				</a>\n");
      out.write("    				<form action=\"");
      out.print( request.getContextPath() );
      out.write("/vendor_list\" method=\"get\" class=\"search-form\">\n");
      out.write("    					<input type=\"hidden\" name=\"order\" value=\"");
      out.print(order);
      out.write("\">\n");
      out.write("    					<input type=\"text\" class=\"search-box\" placeholder=\"仕入先名で検索\" name=\"keyword\" value=\"");
      out.print(keyword);
      out.write("\">\n");
      out.write("    				</form>\n");
      out.write("    			</div>\n");
      out.write("    			<a href=\"");
      out.print( request.getContextPath() );
      out.write("/vendor_register\" class=\"btn vendor-btn\">仕入先登録</a>\n");
      out.write("    		</div>\n");
      out.write("    		<table class=\"products-table\">\n");
      out.write("    			<tr>\n");
      out.write("    				<th class=\"hidden-id\">ID</th>\n");
      out.write("    				<th>仕入先コード</th>\n");
      out.write("    				<th>仕入先名</th>\n");
      out.write("    				<th>編集</th>\n");
      out.write("    				<th>削除</th>\n");
      out.write("    			</tr>\n");
      out.write("    			");

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
    			
      out.write("\n");
      out.write("    		</table>\n");
      out.write("    		<div class=\"pagination\">\n");
      out.write("    		");

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
    		
      out.write("\n");
      out.write("    		</div>\n");
      out.write("    	</article>\n");
      out.write("    </main>\n");
      out.write("    <footer>\n");
      out.write("    	<p class=\"copyright\">&copy; 商品管理アプリ All rights reserved.</p>\n");
      out.write("    </footer>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
