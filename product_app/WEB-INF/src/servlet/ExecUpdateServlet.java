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

public class ExecUpdateServlet extends HttpServlet {
	
	// 更新成功時の遷移先（商品一覧ページ）
	private static final String SUCCESS_PAGE = "/list";
	// 更新成功時の遷移先（仕入先一覧ページ）
	private static final String SUCCESS_VENDOR_PAGE = "/vendor_list";
	// 更新失敗時の遷移先（元の商品編集ページ）
	private static final String FAILURE_PAGE = "/WEB-INF/jsp/editPage.jsp";
	// 更新失敗時の遷移先（元の仕入先編集ページ）
	private static final String FAILURE_VENDOR_PAGE = "/WEB-INF/jsp/vendorEditPage.jsp";
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		// リクエストされたURLのパスを取得
		String path = request.getServletPath();
		
		// リクエスト・レスポンスの設定
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		if(path.equals("/update")) {
		
			// フォームから送られたデータを取得
			String id = request.getParameter("id");
			String productCode = request.getParameter("product_code");
			String productName = request.getParameter("product_name");
			String price = request.getParameter("price");
			String stockQuantity = request.getParameter("stock_quantity");
			String vendorCode = request.getParameter("vendor_code");
			
			// 数値型カラムのデータはString型→int型に変換
			int iId, iProductCode, iPrice, iStockQuantity, iVendorCode;
			try {
				iId = Integer.parseInt(id); // ID
				iProductCode = Integer.parseInt(productCode); // 商品コード
				iPrice = Integer.parseInt(price); // 単価
				iStockQuantity = Integer.parseInt(stockQuantity); // 在庫数
				iVendorCode = Integer.parseInt(vendorCode); // 仕入先コード
			} catch (NumberFormatException e) { // 数値変換失敗時
				forwardFailure("商品名以外の項目には、有効な数値を入力してください。", FAILURE_PAGE, request, response);
				return;
			}
			
			// 商品名が正しく取得できている（NULLや空文字でない）かチェック
			if(productName == null || productName.isEmpty()) {
				forwardFailure("有効な商品名を入力してください。",  FAILURE_PAGE,request, response);
				return;
			}
			
			// ここからデータ更新処理
			// DTOのインスタンスを生成し、各カラムのデータをセット
			ProductDto data = new ProductDto();
			data.setId(iId);
			data.setProductCode(iProductCode);
			data.setProductName(productName);
			data.setPrice(iPrice);
			data.setStockQuantity(iStockQuantity);
			data.setVendorCode(iVendorCode);
			
			// 商品データ操作用DAOクラスのインスタンスを生成
			ProductDao product = new ProductDao();
			
			// 商品データを更新
			int rowCnt = product.update(data);
			
			// 結果に応じてメッセージ送信
			if(rowCnt > 0) { // 更新成功時
				forwardSuccess("商品を" + rowCnt + "件編集しました。", SUCCESS_PAGE, request, response);
			} else { // 更新失敗時
				forwardFailure("データベース処理エラーが発生しました。システム管理者に確認してください。", FAILURE_PAGE, request, response);
			}
			
		} else if (path.equals("/vendor_update")) {
			// フォームから送られたデータを取得
			String id = request.getParameter("id");
			String vendorCode = request.getParameter("vendor_code");
			String vendorName = request.getParameter("vendor_name");
			
			// 数値型カラムのデータはString型→int型に変換
			int iId, iVendorCode;
			try {
				iId = Integer.parseInt(id);
				iVendorCode = Integer.parseInt(vendorCode);
			} catch (NumberFormatException e) { // 数値変換失敗時
				forwardFailure("商品名以外の項目には、有効な数値を入力してください。",  FAILURE_VENDOR_PAGE,request, response);
				return;
			}
			
			// 仕入先名が正しく取得できている（NULLや空文字でない）かチェック
			if(vendorName == null || vendorName.isEmpty()) {
				forwardFailure("有効な仕入先名を入力してください。", FAILURE_VENDOR_PAGE, request, response);
				return;
			}
			
			// ここからデータ更新処理
			// DTOのインスタンスを生成し、各カラムのデータをセット
			VendorDto data = new VendorDto();
			data.setId(iId);
			data.setVendorCode(iVendorCode);
			data.setVendorName(vendorName);
			
			// 仕入先データ操作用DAOクラスのインスタンスを生成
			VendorDao vendor = new VendorDao();
			
			// 商品データを更新
			int rowCnt = vendor.update(data);
			
			// 結果に応じてメッセージ送信
			if(rowCnt > 0) { // 更新成功時
				forwardSuccess("仕入先を" + rowCnt + "件編集しました。",  SUCCESS_VENDOR_PAGE,request, response);
			} else { // 更新失敗時
				forwardFailure("データベース処理エラーが発生しました。システム管理者に確認してください。", FAILURE_VENDOR_PAGE, request, response);
			}
		}
	}
	
	// 成功時の画面遷移（フォワード）
	private void forwardSuccess(String message, String successPage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 成功メッセージをリクエストスコープに保存して画面遷移
		request.setAttribute("successMessage", message);
		request.getRequestDispatcher(successPage).forward(request, response);
	}
	
	// 失敗時の画面遷移（フォワード）
	private void forwardFailure(String message, String failurePage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 失敗メッセージをリクエストスコープに保存して画面遷移
		request.setAttribute("failureMessage", message);
		request.getRequestDispatcher(failurePage).forward(request, response);
	}
}
