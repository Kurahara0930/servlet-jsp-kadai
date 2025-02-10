package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.ProductDao;
import data.ProductDto;
import data.VendorDao;
import data.VendorDto;

// 商品データの追加機能用Servlet
public class ExecCreateServlet extends HttpServlet {
	
	// 登録成功時の遷移先（商品一覧ページ）
	private static final String SUCCESS_PAGE = "/list";
	// 登録成功時の遷移先（仕入先一覧ページ）
	private static final String SUCCESS_VENDOR_PAGE = "/vendor_list";
	
	// 登録失敗時の遷移先（元の商品登録ページ）
	private static final String FAILURE_PAGE = "/WEB-INF/jsp/registerPage.jsp";
	// 登録失敗時の遷移先（元の仕入先登録ページ）
	private static final String FAILURE_VENDOR_PAGE = "/WEB-INF/jsp/vendorRegisterPage.jsp";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエストされたURLのパスを取得
		String path = request.getServletPath();
		
		// リクエスト・レスポンスの設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		if(path.equals("/create")) { // 商品登録処理
		
			// フォームから送られたデータを取得
			String productCode = request.getParameter("product_code");
			String productName = request.getParameter("product_name");
			String price = request.getParameter("price");
			String stockQuantity = request.getParameter("stock_quantity");
			String vendorCode = request.getParameter("vendor_code");
			
			// 数値型カラムのデータはString型→Intに変換
			int iProductCode , iPrice, iStockQuantity, iVendorCode;
			try {
				iProductCode = Integer.parseInt(productCode); // 商品コード
				iPrice = Integer.parseInt(price); // 単価
				iStockQuantity = Integer.parseInt(stockQuantity); // 在庫数
				iVendorCode = Integer.parseInt(vendorCode); // 仕入先コード
			} catch (NumberFormatException e) { // 数値変換失敗時
				forwardFailure("商品名以外の項目は、有効な数値を入力してください。", FAILURE_PAGE, request, response);
				return;
			}
			
			// 商品名が正しく取得できている（NULLや空文字でない）かチェック
			if(productName == null || productName.isEmpty()) {
				forwardFailure("有効な商品名を入力してください。", FAILURE_PAGE, request, response);
				return;
			}
			
			// ここからデータ追加処理
			// DTOのインスタンスを生成し、各カラムのデータをセット
			ProductDto data = new ProductDto();
			data.setProductCode(iProductCode);
			data.setProductName(productName);
			data.setPrice(iPrice);
			data.setStockQuantity(iStockQuantity);
			data.setVendorCode(iVendorCode);
			
			// 商品データ操作用DAOクラスのインスタンスを生成
			ProductDao product = new ProductDao();
			
			// 商品データを追加
			int rowCnt = product.create(data);
			
			// 結果に応じてメッセージを送信
			if(rowCnt > 0) { // 登録成功時
				forwardSuccess("商品を" + rowCnt + "件登録しました。", SUCCESS_PAGE, request, response);
			} else { // 登録失敗時
				forwardFailure("データベース処理エラーが発生しました。システム管理者に確認してください。" , FAILURE_PAGE, request, response);
			}
			
		} else if (path.equals("/vendor_create")) { // 仕入先登録処理
			
			// フォームから送られたデータを取得
			String vendorCode = request.getParameter("vendor_code"); 
			String vendorName = request.getParameter("vendor_name");
			
			// 数値型カラムはString型→Int型に変換
			int iVendorCode;
			try {
				iVendorCode = Integer.parseInt(vendorCode);
			} catch (NumberFormatException e) { // 数値変換失敗時
				forwardFailure("仕入先コードには、有効な数値を入力してください。", FAILURE_VENDOR_PAGE, request, response);
				return;
			}
			
			// 仕入先名が正しく取得できている（NULLや空文字がない）かチェック
			if(vendorName == null || vendorName.isEmpty()) {
				forwardFailure("有効な仕入先名を入力してください。", FAILURE_VENDOR_PAGE, request, response);
				return;
			}
			
			// ここからデータ追加処理
			// DTOのインスタンスを生成し、各カラムのデータをセット
			VendorDto data = new VendorDto();
			data.setVendorCode(iVendorCode);
			data.setVendorName(vendorName);
			
			// 仕入先データ操作用DAOクラスのインスタンスを生成
			VendorDao vendor = new VendorDao();
			
			// 仕入先データを追加
			int rowCnt = vendor.create(data);
			
			// 結果に応じてメッセージを送信
			if(rowCnt > 0) { // 登録成功時
				forwardSuccess("仕入先を" + rowCnt + "件登録しました。", SUCCESS_VENDOR_PAGE, request, response);
			} else { // 登録失敗時
				forwardFailure("データベース処理エラーが発生しました。システム管理者に確認してください。" , FAILURE_VENDOR_PAGE, request, response);
			}

		}

	}
	
	// 成功時の画面遷移（フォワード）
	private void forwardSuccess(String message, String successPage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 成功メッセージをリクエストスコープに保存して画面遷移
		request.setAttribute("successMessage", message);
		// 画面遷移
		request.getRequestDispatcher(successPage).forward(request, response);
	}
	
	// 失敗時の画面遷移（フォワード）
	private void forwardFailure(String message, String failurePage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 成功メッセージをリクエストスコープに保存して画面遷移
		request.setAttribute("failureMessage", message);
		// 画面遷移
		request.getRequestDispatcher(failurePage).forward(request, response);
	}
}
