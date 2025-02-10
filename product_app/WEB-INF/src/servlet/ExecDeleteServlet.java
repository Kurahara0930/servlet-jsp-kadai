package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.VendorDao;

public class ExecDeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエストされたURLのパスを取得
		String path = request.getServletPath();
		
        // リクエスト・レスポンスの設定
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        if(path.equals("/delete")) {
        
	        // フォームから送られたデータを取得
	        String id = request.getParameter("id");
	        
	        // IDをString型→Int型に変換
	        int iId;
	        try {
	        	iId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				request.setAttribute("failureMessage", "内部エラーが発生しました。システム管理者に確認してください。");
				request.getRequestDispatcher("/list").forward(request, response);
				return;
			}
	        
	        // ここからデータ削除処理
	        // 商品データ操作用DAOクラスのインスタンスを生成
	        ProductDao product = new ProductDao();
	        
	        // 商品データを削除
	        int rowCnt = product.delete(iId);
	        
	        // 結果に応じてメッセージを送信
	        if (rowCnt > 0) { // 削除成功時
	        	request.setAttribute("successMessage", "商品を" + rowCnt + "件削除しました。");
			} else { // 削除失敗時
				request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
			}
	        
	        // フォワードによる画面遷移
	        request.getRequestDispatcher("/list").forward(request, response);
	       
        } else if(path.equals("/vendor_delete")) {
        	
        	// フォームから送られたデータを取得
	        String id = request.getParameter("id");
	        
	        // IDをString型→Int型に変換
	        int iId;
	        try {
	        	iId = Integer.parseInt(id);
			} catch (NumberFormatException e) {
				request.setAttribute("failureMessage", "内部エラーが発生しました。システム管理者に確認してください。");
				request.getRequestDispatcher("/vendor_list").forward(request, response);
				return;
			}
	        
	        // ここからデータ削除処理
	        // 仕入先データ操作用DAOクラスのインスタンスを生成
	        VendorDao vendor = new VendorDao();
	        
	        // 商品データを削除
	        int rowCnt = vendor.delete(iId);
	        
	        // 結果に応じてメッセージを送信
	        if (rowCnt > 0) { // 削除成功時
	        	request.setAttribute("successMessage", "仕入先を" + rowCnt + "件削除しました。");
			} else { // 削除失敗時
				request.setAttribute("failureMessage", "データベース処理エラーが発生しました。システム管理者に確認してください。");
			}
	        
	        // フォワードによる画面遷移
	        request.getRequestDispatcher("/vendor_list").forward(request, response);
        }
	}
}
