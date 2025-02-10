package data;

// 商品データ受け渡し用DTOクラス
public class ProductDto {

	private int id = 0; // ID
	private int productCode = 0; // 商品コード
	private String productName = ""; // 商品名
	private int price = 0; // 単価
	private int stockQuantity = 0; // 在庫数
	private int vendorCode = 0; // 仕入先コード
	
	// 各カラムのゲッター（データ取得メソッド）とセッター（データ設定メソッド）を実装
	
	// ID
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	// 商品コード
	public int getProductCode() {
		return productCode;
	}
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	
	// 商品名
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	// 単価
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	// 在庫数
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	// 仕入先コード
	public int getVendorCode() {
		return vendorCode;
	}
	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	
	
}
