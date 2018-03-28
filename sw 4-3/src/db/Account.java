package db;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private int PId;		//��ǰ ���̵�
	private ImageIcon PIcon;
	private String PName;	//��ǰ��
	private int Pprice ;//����
	private int sellNum;//�Ǹŷ�	
	
	public Account(ImageIcon PIcon, String pName, int pprice) {
		this.setPIcon(PIcon);
		this.setPName(pName);
		this.setPprice(pprice);
		this.setSellNum(0);
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
	//�ش���ǰ�� �Ǹ�����
	public int getPTotalPrice() {
		return this.Pprice*this.sellNum;
	}
	
}
