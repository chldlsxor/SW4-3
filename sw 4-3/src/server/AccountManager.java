package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import db.Account;
import header.Header;

public class AccountManager {
	public static Map<Integer, Account> account = new HashMap<>();
	
	//ȸ���� ����
    public static void saveDB(int Pid, Account a) {
		account.put(Pid, a);	
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(new File("DBFile", "Account.txt")))){
			fout.writeObject(account);
	        fout.flush();
		} catch (Exception e) {}
	}
    
    //ȸ���� ��������
	public static void readDB() {
		try (ObjectInputStream fin = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("DBFile", "Account.txt"))));){
			account= (Map<Integer, Account>) fin.readObject();
        } catch (Exception e) {
        	account = new HashMap<>();
       }
//		account.put(Header.PCID, new Account(null, "PC�ð�", 0));
	}
	//�ش� ��ǰ�� Account��������
	public static Account getAccount(int PID) {
		return account.get(PID);
	}
	
	//�ش� ��ǰ�� �Ǹŷ� �߰�
	public static void addSellNum(int PID, int num) {
		account.get(PID).setSellNum(account.get(PID).getSellNum()+num);
	}
	
	//Ư�� ��ǰ Ư�� ���� ���� ���Ž� �ݾ� ���
	public static int calcOrderMoney(int PID, int PNum) {
		return account.get(PID).getPprice()*PNum;
	}
	//�ش� ��ǰ �̸� ��������
	public static String getPName(int PID) {
		return account.get(PID).getPName();
	}	
	//�ش� ��ǰ ���� ��������
	public static int getPPrice(int PID) {
		account.get(PID).setTotalPrice();
		return account.get(PID).getPprice();
	}	
	
	//�ش� ��ǰ �� �Ǹŷ� ���
	public static int getSellNum(int PID) {
		return account.get(PID).getSellNum();
	}	
	
	//�ش� ��ǰ �� �Ǹ� �ݾ� ���
	public static int calcMoney(int PID) {
		return account.get(PID).getTotalPrice();
	}
		
	//�Ǿ� ���� ��� (PID==0)
	public static int getTotalPCPrice() {
		return account.get(Header.PCID).getTotalPrice();
	}	
	
	//�Ǿ� ���� �߰� (Header.PCID)
	public static void addTotalPCPrice(int money) {
		account.get(Header.PCID).setTotalPrice(money);
	}
	
	//��  �Ǹ� ����
	public static int totalSellNum() {
		int total=0;
		for(Integer PID : account.keySet()) {
			if(AccountManager.account.get(PID)!=null) {
				total +=account.get(PID).getSellNum();
			}		
		}
		return total;
	}
	
	//���Ǹ�����
	public static int totalPrice() {
		int total=0;
		for(Integer PID : account.keySet()) {
			if(AccountManager.account.get(PID)!=null) {
				total +=account.get(PID).getTotalPrice();
			}			
		}
		return total;
	}
}
