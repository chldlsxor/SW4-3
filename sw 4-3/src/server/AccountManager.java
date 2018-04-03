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
	
	//회계디비 저장
    public static void saveDB(int Pid, Account a) {
		account.put(Pid, a);	
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(new File("DBFile", "Account.txt")))){
			fout.writeObject(account);
	        fout.flush();
		} catch (Exception e) {}
	}
    
    //회계디비 가져오기
	public static void readDB() {
		try (ObjectInputStream fin = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("DBFile", "Account.txt"))));){
			account= (Map<Integer, Account>) fin.readObject();
        } catch (Exception e) {
        	account = new HashMap<>();
       }
//		account.put(Header.PCID, new Account(null, "PC시간", 0));
	}
	//해당 제품의 Account가져오기
	public static Account getAccount(int PID) {
		return account.get(PID);
	}
	
	//해당 제품의 판매량 추가
	public static void addSellNum(int PID, int num) {
		account.get(PID).setSellNum(account.get(PID).getSellNum()+num);
	}
	
	//특정 제품 특정 수량 만킄 구매시 금액 계산
	public static int calcOrderMoney(int PID, int PNum) {
		return account.get(PID).getPprice()*PNum;
	}
	//해당 제품 이름 가져오기
	public static String getPName(int PID) {
		return account.get(PID).getPName();
	}	
	//해당 제품 가격 가져오기
	public static int getPPrice(int PID) {
		account.get(PID).setTotalPrice();
		return account.get(PID).getPprice();
	}	
	
	//해당 제품 총 판매량 계산
	public static int getSellNum(int PID) {
		return account.get(PID).getSellNum();
	}	
	
	//해당 제품 총 판매 금액 계산
	public static int calcMoney(int PID) {
		return account.get(PID).getTotalPrice();
	}
		
	//피씨 수익 계산 (PID==0)
	public static int getTotalPCPrice() {
		return account.get(Header.PCID).getTotalPrice();
	}	
	
	//피씨 수익 추가 (Header.PCID)
	public static void addTotalPCPrice(int money) {
		account.get(Header.PCID).setTotalPrice(money);
	}
	
	//총  판매 수량
	public static int totalSellNum() {
		int total=0;
		for(Integer PID : account.keySet()) {
			if(AccountManager.account.get(PID)!=null) {
				total +=account.get(PID).getSellNum();
			}		
		}
		return total;
	}
	
	//총판매이익
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
