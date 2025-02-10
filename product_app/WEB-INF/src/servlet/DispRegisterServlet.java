package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.VendorDao;
import data.VendorDto;

// 商品登録ページ用Servlet
public class DispRegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエストされたURLのパスを取得
		String path = request.getServletPath();
		
		// リクエスト・レスポンスの設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		if(path.equals("/register")) { // 商品登録画面へ
		
			// 仕入先データリストのインスタンスを生成
			ArrayList<VendorDto> vendorList = new ArrayList<VendorDto>();
			
			// 仕入先データ操作用DAOクラスのインスタンスを生成
			VendorDao vendor = new VendorDao();
			
			try {
				// 仕入先データの一覧を取得
				vendorList = vendor.read(0, "", "");
				
				if(vendorList.isEmpty()) {
					//仕入先データリストが空だった場合
					request.setAttribute("failureMessage", "仕入先コードが1件も登録されていません。");
				} else {
					// 取得した仕入先データリストを送る
					request.setAttribute("vendorList", vendorList);
				}
			} catch (SQLException e) { // データベース処理の例外発生時
				request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
			}
			
				// フォワードによる画面遷移
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/registerPage.jsp");
				dispatcher.forward(request, response);
		} else if (path.equals("/vendor_register")) { // 仕入先登録画面へ
			// フォワードによる画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/vendorRegisterPage.jsp");
			dispatcher.forward(request, response);
		}
	}
}
