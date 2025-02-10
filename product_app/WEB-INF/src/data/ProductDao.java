package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

// 商品データ操作用DAOクラス
public class ProductDao {

    //接続用の情報をフィールドに定数として定義
    private static final String URL = "jdbc:mariadb://localhost/java_db_app"; 
    private static final String USER_NAME = "root";
    private static final String PASSWORD = ""; 
    
    // 商品データの作成
    public int create(ProductDto data) {
    	int rowCnt = 0; // 更新レコード数
    	
    	// INSERT文のフォーマット
    	String sql = "INSERT INTO products("
    			+ " product_code, product_name, price, stock_quantity, vendor_code" +
    			") VALUES(?, ?, ?, ?, ?);";
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setInt(1, data.getProductCode());
			statement.setString(2, data.getProductName());
			statement.setInt(3, data.getPrice());
			statement.setInt(4, data.getStockQuantity());
			statement.setInt(5, data.getVendorCode());
			
			// SQL文を実行
			rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生 : " + e.getMessage());
		}
    	
    	return rowCnt; // 更新レコード数を返す
    }
    
    // 商品データの読み取り
    public ArrayList<ProductDto> read(int id, String order, String keyword) throws SQLException{
    
    	// SELECT文のフォーマット
    	String sql = "SELECT * FROM products";
    	
    	// 取得したデータを格納するためのリスト
    	ArrayList<ProductDto> dataList = new ArrayList<ProductDto>();
    
    	// 文字列のNULL対策（データがなければ空文字に置き換え）
    	order = Objects.toString(order, "");
    	keyword = Objects.toString(keyword, "");
    	
    	// データベース接続
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
			
    		// 引数に合わせてSELECT文に条件を追加
    		if(id > 0) {
    			// IDが指定されている場合
    			sql += " WHERE id = ?";
    		} else {
				// 検索ワードが存在する場合
    			if(!keyword.isEmpty()) {
    				sql += " WHERE product_name LIKE ?";
    			}
    			
    			// 並べ替え方向に合わせてORDER BYを付加
    			if("asc".equals(order)) {
    				sql += " ORDER BY updated_at ASC";
    			} else if("desc".equals(order)) {
    				sql += " ORDER BY updated_at DESC";
    			}
			}
    		// 末尾にセミコロンを付与
    		sql += ";";
    		
    		// SQL文の送信準備
    		try(PreparedStatement statement = con.prepareStatement(sql)) {
				
    			// セットパラメータ用インデックス番号
    			int paramIdx = 0;
    			
    			if(id > 0) { // IDが指定されている場合
    				statement.setInt(++paramIdx, id); // IDに置き換え
    			} else if (!keyword.isEmpty()) { // 検索キーワードが存在する場合
					// 検索キーワードに置き換え（%を両端に付加して部分一致検索）
    				statement.setString(++paramIdx, "%" + keyword + "%");
				}
    			
    			// SQL文を実行
    			ResultSet result = statement.executeQuery();
    			
    			// SQLクエリの実行結果を抽出
    			while (result.next()) {
					// DTOのインスタンスに各カラムのデータをセット
    				ProductDto productDto = new ProductDto();
    				productDto.setId(result.getInt("id"));
    				productDto.setProductCode(result.getInt("product_code"));
    				productDto.setProductName(result.getString("product_name"));
    				productDto.setPrice(result.getInt("price"));
    				productDto.setStockQuantity(result.getInt("stock_quantity"));
    				productDto.setVendorCode(result.getInt("vendor_code"));
    				
    				// リストにデータを追加
    				dataList.add(productDto);
				}
			}
		}
    	
    	return dataList; // データリストを返す
    
    }
    
    // 商品データの読み取り（ページネーション用）
    public ArrayList<ProductDto> read(int id, String order, String keyword, Integer limit, Integer page) throws SQLException{
    
    	// SELECT文のフォーマット
    	String sql = "SELECT * FROM products ";
    	
    	// 取得したデータを格納するためのリスト
    	ArrayList<ProductDto> dataList = new ArrayList<ProductDto>();
    
    	// 文字列のNULL対策（データがなければ空文字に置き換え）
    	order = Objects.toString(order, "");
    	keyword = Objects.toString(keyword, "");
    	
    	// 表示件数のNULL対策（指定がない場合は10件まで表示）
    	int iLimit = (limit != null) ? limit : 10;
    	
    	// ページ数のNULL対策（最低でも1ページ）
    	int iPage = (page != null) ? page : 1;
    	
    	
    	// データベース接続
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
			
    		// 引数に合わせてSELECT文に条件を追加
    		if(id > 0) {
    			// IDが指定されている場合
    			sql += " WHERE id = ? ";
    		} else {
				// 検索ワードが存在する場合
    			if(!keyword.isEmpty()) {
    				sql += " WHERE product_name LIKE ? ";
    			}
    			
    			// 並べ替え方向に合わせてORDER BYを付加
    			if("asc".equals(order)) {
    				sql += " ORDER BY updated_at ASC ";
    			} else if("desc".equals(order)) {
    				sql += " ORDER BY updated_at DESC ";
    			}
			}
    		
    		// ページネーションを追加
    		sql += "LIMIT ? OFFSET ?;"; // 取得件数と開始位置を指定（OFFSETで指定した行+1行目～LIMITで指定した件数分取得）
    		
    		// SQL文の送信準備
    		try(PreparedStatement statement = con.prepareStatement(sql)) {
				
    			// セットパラメータ用インデックス番号
    			int paramIdx = 0;
    			
    			if(id > 0) { // IDが指定されている場合
    				statement.setInt(++paramIdx, id); // IDに置き換え
    			} else if (!keyword.isEmpty()) { // 検索キーワードが存在する場合
					// 検索キーワードに置き換え（%を両端に付加して部分一致検索）
    				statement.setString(++paramIdx, "%" + keyword + "%");
				}
    			
    			// ページネーション用
    			statement.setInt(++paramIdx, iLimit); // 表示件数
    			statement.setInt(++paramIdx, (iPage - 1) * iLimit); // 先頭から何件スキップするか（スキップ+1行目からlimit件数分取得）
    			
    			// SQL文を実行
    			ResultSet result = statement.executeQuery();
    			
    			// SQLクエリの実行結果を抽出
    			while (result.next()) {
					// DTOのインスタンスに各カラムのデータをセット
    				ProductDto productDto = new ProductDto();
    				productDto.setId(result.getInt("id"));
    				productDto.setProductCode(result.getInt("product_code"));
    				productDto.setProductName(result.getString("product_name"));
    				productDto.setPrice(result.getInt("price"));
    				productDto.setStockQuantity(result.getInt("stock_quantity"));
    				productDto.setVendorCode(result.getInt("vendor_code"));
    				
    				// リストにデータを追加
    				dataList.add(productDto);
				}
			}
		}
    	
    	return dataList; // データリストを返す
    
    }
    
    // ページネーション用に検索結果の商品数を取得
    public int getTotalProductCount(String keyword) throws SQLException{
    	
    	// SELECT文のフォーマット
    	String sql = "SELECT COUNT(*) FROM products";
    	
		// 検索ワードが存在する場合
		if(!keyword.isEmpty()) {
			sql += "WHERE product_name LIKE ?";
		}
		
		sql += ";"; // 末尾にセミコロンを付与
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			if (!keyword.isEmpty()) {
				statement.setString(1, "%" + keyword + "%");
			}
			
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				return result.getInt(1);
			}
		}
    	
    	return 0;
    }
    
    // 商品データの更新
    public int update(ProductDto data) {
    	int rowCnt = 0; // 更新レコード数
    	
    	// UPDATE文のフォーマット
    	String sql = "UPDATE products " +
		    	"SET product_code = ?, " +
		    	"  product_name = ?, " +
		    	"  price = ?, " +
		    	"  stock_quantity = ?, " +
		    	"  vendor_code = ? " +
		    	"WHERE id = ?;";
    	
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			
    		
    		// SQL文の?を更新するデータで置き換える
    		statement.setInt(1, data.getProductCode());
    		statement.setString(2, data.getProductName());
    		statement.setInt(3, data.getPrice());
    		statement.setInt(4, data.getStockQuantity());
    		statement.setInt(5, data.getVendorCode());
    		statement.setInt(6, data.getId());
    		
    		// SQL文を実行
    		rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生 : " + e.getMessage());
		}
    	
    	return rowCnt; // 更新レコード数を返す
    }
    
    // 商品データの削除
    public int delete(int id) {
    	int rowCnt = 0; // 更新レコード数
    	
    	// DELETE文のフォーマット
    	String sql = "DELETE FROM products WHERE id = ?;";
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			
    		// SQL文の?を更新するデータで置き換える
    		statement.setInt(1, id);
    		
    		// SQL文を実行
    		rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生 : " + e.getMessage());
		}
    	
    	return rowCnt; // 更新レコード数を返す
    }
}
