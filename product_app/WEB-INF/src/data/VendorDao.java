package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

// 仕入先データ操作用DAOクラス
public class VendorDao {

	// 操作用の情報をフィールドに定数として定義
    private static final String URL = "jdbc:mariadb://localhost/java_db_app";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    
    // 仕入先データの作成
    public int create(VendorDto data) {
    	int rowCnt = 0; // 更新レコード数
    	
    	// INSERT文のフォーマット
    	String sql = "INSERT INTO vendors(vendor_code, vendor_name) VALUES(?,?);";
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL,USER_NAME,PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setInt(1, data.getVendorCode());
			statement.setString(2, data.getVendorName());
			
			// SQL文を実行
			rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
    	
    	// 更新レコード数を返す
		return rowCnt;
    	
    }
    
    // 仕入先データの読み取り
    public ArrayList<VendorDto> read(int id, String order, String keyword) throws SQLException {
    	
    	//SELECT文のフォーマット
    	String sql = "SELECT * FROM vendors";
    	
    	// 取得したデータを格納するためのリスト
    	ArrayList<VendorDto> dataList = new ArrayList<VendorDto>();
    	
    	// 文字列のNULL対策（データがなければ空文字に置き換え）
    	order = Objects.toString(order, "");
    	keyword = Objects.toString(keyword, "");
    	
    	
    	// データベース接続 & SQLの準備
    	try(Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
    		
			// 引数に合わせてSELECT文に条件を追加
    		if(id > 0) {
    			// vendorCodeが指定されている場合
    			sql += " WHERE id = ?";
    		} else {
				
    			if (!keyword.isEmpty()) {
					sql += " WHERE vendor_name LIKE ?";
				}
    			
    			if (!order.isEmpty()) {
    	    		// 並べ変え方向に合わせてORDER BYを付加
    	    		if("asc".equals(order)) {
    	    			sql += " ORDER BY updated_at ASC";
    	    		} else if("desc".equals(order)) {
    	    			sql += " ORDER BY updated_at DESC";
    	    		}
				}
			}
    		sql += ";"; // 文末にセミコロンを付加
    	
	    	// SQL文の送信準備
	    	try(PreparedStatement statement = con.prepareStatement(sql)) {
				// ?があれば置き換え
	    		if(id > 0) { // IDが指定されている場合
	    			statement.setInt(1, id); // IDに置き換え
	    		} else {
					if (!keyword.isEmpty()) { // 検索キーワードが存在する場合
						statement.setString(1, "%" + keyword + "%");
					}
				}
	    		
				// SQL文を実行
				ResultSet result = statement.executeQuery();
				
				// SQLクエリの実行結果を抽出
				while (result.next()) {
					// DTOのインスタンスにデータをセット
					VendorDto vendorDto = new VendorDto();
					vendorDto.setId(result.getInt("id"));
					vendorDto.setVendorCode(result.getInt("vendor_code"));
					vendorDto.setVendorName(result.getString("vendor_name"));
					
					// リストにデータを追加
					dataList.add(vendorDto);
				}
	    	}
    	}
    	
    	return dataList; // データリストを返す
    }
    
    // 仕入先データの読み取り（ページネーション用）
    public ArrayList<VendorDto> read(int id, String order, String keyword, Integer limit, Integer page) throws SQLException{
    
    	// SELECT文のフォーマット
    	String sql = "SELECT * FROM vendors ";
    	
    	// 取得したデータを格納するためのリスト
    	ArrayList<VendorDto> dataList = new ArrayList<VendorDto>();
    
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
    				sql += " WHERE vendor_name LIKE ? ";
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
    				VendorDto vendorDto = new VendorDto();
    				vendorDto.setId(result.getInt("id"));
    				vendorDto.setVendorCode(result.getInt("vendor_code"));
    				vendorDto.setVendorName(result.getString("vendor_name"));
    				
    				// リストにデータを追加
    				dataList.add(vendorDto);
				}
			}
		}
    	
    	return dataList; // データリストを返す
    
    }
    
    // ページネーション用に検索結果の仕入先数を取得
    public int getTotalVendorCount(String keyword) throws SQLException{
    	
    	// SELECT文のフォーマット
    	String sql = "SELECT COUNT(*) FROM vendors";
    	
		// 検索ワードが存在する場合
		if(!keyword.isEmpty()) {
			sql += "WHERE vendor_name LIKE ?";
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
    
    // 仕入先データの更新
    public int update(VendorDto data) {
    	int rowCnt = 0; // 更新レコード数
    	
    	// UPDATA文のフォーマット
    	String sql = "UPDATE vendors SET vendor_code = ?, vendor_name = ? WHERE id = ?;";
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL,USER_NAME,PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setInt(1, data.getVendorCode());
			statement.setString(2, data.getVendorName());
			statement.setInt(3, data.getId());
    	    
			// SQL文を実行
		    rowCnt = statement.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println("エラー発生：" + e.getMessage());
    	}
	
    	// 更新レコード数を返す
    	return rowCnt;
    }
    
    // 仕入先データの削除
    public int delete(int id) {
    	int rowCnt = 0; // 更新レコード数
    	
    	// DELETE文のフォーマット
    	String sql = "DELETE FROM vendors WHERE id = ?;";
    	
    	// データベース接続 & SQL文の送信準備
    	try(Connection con = DriverManager.getConnection(URL,USER_NAME,PASSWORD); PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setInt(1, id);
			
			// SQL文を実行
			rowCnt = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}
    	
    	// 更新レコード数を返す
		return rowCnt;
    }
}
