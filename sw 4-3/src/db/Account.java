package db;

public class Account {
	//private int PId;		//제품 아이디
	private String PName;	//제품명
	private int Pprice ;//가격
	private int sellNum;//판매량	
	
	public Account(String pName, int pprice) {
		this.setPName(pName);
		this.setPprice(pprice);
		this.setSellNum(0);
	}
	public void setPName(String pName) {
		PName = pName;
	}
	public String getPName() {
		return PName;
	}
	public void setPId(String pName) {
		PName = pName;
	}
	public int getPprice() {
		return Pprice;
	}
	public void setPprice(int pprice) {
		Pprice = pprice;
	}
	public int getSellNum() {
		return sellNum;
	}
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}
	//해당제품의 판매이익
	public int getPTotalPrice() {
		return this.Pprice*this.sellNum;
	}
	
}
