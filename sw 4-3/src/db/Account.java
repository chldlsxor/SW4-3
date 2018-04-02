package db;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private int PId;		//제품 아이디
	private ImageIcon PIcon;
	private String PName;	//제품명
	private int Pprice ;//가격
	private int sellNum;//판매량	
	private int totalPrice;//해당제품 총 판매 금액
	
	public Account(ImageIcon PIcon, String pName, int pprice) {
		this.setPIcon(PIcon);
		this.setPName(pName);
		this.setPprice(pprice);
		this.setSellNum(0);
		this.setTotalPrice(0);
	}
	public ImageIcon getPIcon() {
		return PIcon;
	}
	public void setPIcon(ImageIcon pIcon) {
		PIcon = pIcon;
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
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice() {
		this.totalPrice = this.Pprice*this.sellNum;
	}
	//PC계산용
	public void setTotalPrice(int price) {
		this.totalPrice +=price;
	}

}
