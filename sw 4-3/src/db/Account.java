package db;

public class Account {
	//private int PId;		//��ǰ ���̵�
	private String PName;	//��ǰ��
	private int Pprice ;//����
	private int sellNum;//�Ǹŷ�	
	
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
	//�ش���ǰ�� �Ǹ�����
	public int getPTotalPrice() {
		return this.Pprice*this.sellNum;
	}
	
}
