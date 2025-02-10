package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.ProductDto;
import data.VendorDao;
import data.VendorDto;

// 商品一覧ページ用Servlet
public class DispListServlet extends HttpServlet {
	
	// 表示件数（ページネーション用）
	private static final int LIMIT = 5;

	// GETメソッドのリクエスト受信時に実行されるメソッド
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// リクエストされたURLのパスを取得
		String path = request.getServletPath();
		
		// リクエスト・レスポンスの設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		if(path.equals("/list")) { // 商品一覧ページへの遷移処理
		
			// JSPからのリクエストデータ取得
			String order = request.getParameter("order");
			order = Objects.toString(order, ""); // NULLは空文字に置き換え
			
			String keyword = request.getParameter("keyword");
			keyword = Objects.toString(keyword, ""); // NULLは空文字に置き換え
			
			
			String page = request.getParameter("page");
			int iPage = (page != null) ? Integer.parseInt(page) : 1; // 最低でも1ページに設定
			
			
			// 商品データリストのインスタンスを生成
			ArrayList<ProductDto> productList = new ArrayList<ProductDto>();
			// 商品データ取得件数
			int totalProductCnt;
			// 商品データの総表示ページ
			Integer totalPages;
			
			// 商品データ操作用DAOクラスのインスタンスを生成
			ProductDao product = new ProductDao();
			
			try {
				// 商品データの一覧を取得（ID指定なし）
				productList = product.read(0, order, keyword, LIMIT, iPage);
				// 商品データの一覧件数を取得
				totalProductCnt = product.getTotalProductCount(keyword);
				// 商品データを表示させる為に必要なページ数を取得（計算には小数点を用いる必要がある為double型へキャスト後計算した後、Math.ceilで切り上げるがdouble型を返す為totalPagesに格納する際int型へキャスト）
				totalPages = (int) Math.ceil( (double) totalProductCnt / LIMIT );
				// 最低でも1ページに設定
				int iTotalPages = (totalPages != null) ? totalPages : 1;
				
				if(productList.isEmpty() && iTotalPages == 0) {
					// 商品データリストが空だった場合はメッセージを送る
					request.setAttribute("failureMessage", "該当するデータが見つかりませんでした。");
				} else {
					// 取得した商品データリストを送る
					request.setAttribute("productList", productList);
					// 現在のページ
					request.setAttribute("currentPage", iPage);
					// 必要ページ数
					request.setAttribute("totalPages", iTotalPages);
				}
			} catch (SQLException e) {
				request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
			}
			
			// フォワードによる画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listPage.jsp");
			dispatcher.forward(request, response);
			
		} else if(path.equals("/vendor_list")) { // 仕入先一覧ページへの遷移処理
			
			// JSPからのリクエストデータ取得
			String order = request.getParameter("order");
			order = Objects.toString(order, ""); // NULLは空文字に置き換え
			
			String keyword = request.getParameter("keyword");
			keyword = Objects.toString(keyword, ""); // NULLは空文字に置き換え
			
			String page = request.getParameter("page");
			int iPage = (page != null) ? Integer.parseInt(page) : 1; // 最低でも1ページに設定
			
			// 仕入先データリストのインスタンスを生成
			ArrayList<VendorDto> vendorList = new ArrayList<VendorDto>();
			// 商品データ取得件数
			int totalVendorCnt;
			// 商品データの総表示ページ
			Integer totalPages;
			
			// 商品データ操作用DAOクラスのインスタンスを生成
			VendorDao vendor = new VendorDao();
			
			try {
				// 仕入先データの一覧を取得
				vendorList = vendor.read(0, order, keyword, LIMIT, iPage);
				// 商品データの一覧件数を取得
				totalVendorCnt = vendor.getTotalVendorCount(keyword);
				// 商品データを表示させる為に必要なページ数を取得（計算には小数点を用いる必要がある為double型へキャスト後計算した後、Math.ceilで切り上げるがdouble型を返す為totalPagesに格納する際int型へキャスト）
				totalPages = (int) Math.ceil( (double) totalVendorCnt / LIMIT );
				// 最低でも1ページに設定
				int iTotalPages = (totalPages != null) ? totalPages : 1;
				
				if(vendorList.isEmpty() && iTotalPages == 0) {
					if(!keyword.isEmpty()) {
						request.setAttribute("failureMessage", "該当データが登録されていません。");
					} else {
						request.setAttribute("failureMessage", "仕入先データが登録されていません。");
					}
				} else {
					// 取得した仕入先データリストを送る
					request.setAttribute("vendorList", vendorList);
					// 現在のページ
					request.setAttribute("currentPage", iPage);
					// 必要ページ数
					request.setAttribute("totalPages", iTotalPages);
				}
			} catch (SQLException e) {
				request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
			}
			
			// フォワードによる画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/vendorListPage.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	// POSTメソッドのリクエスト受信時に実行されるメソッド
	// ServletのdoPost()メソッドから遷移した場合のみ呼び出される
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエスト・レスポンスの設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// Servletからの成功メッセージ取得
		String successMessage = (String) request.getAttribute("successMessage");
		
		if(successMessage != null && !successMessage.isEmpty()) {
			// 商品一覧ページのJSPへ成功メッセージを受け渡すために再設定
			request.setAttribute("successMessage", successMessage);
		}
		
		// doGet()メソッドと同様のデータ取得処理を行う
		doGet(request, response);
	}
}
