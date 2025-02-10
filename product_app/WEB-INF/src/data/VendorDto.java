package data;

// 仕入先データ受け渡し用DTOクラス
public class VendorDto {
	private int id = 0; // ID
	private int vendorCode = 0; // 仕入先コード
	private String vendorName = ""; // 仕入先名
	
	// ID
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	// 現在の仕入先コードを取得する
	public int getVendorCode() {
		return vendorCode;
	}
	
	// 仕入先コードに新しいデータを設定する
	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}
	
	// 現在の仕入先名を取得する
	public String getVendorName() {
		return vendorName;
	}
	
	// 仕入先名に新しいデータを設定する
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	
}
