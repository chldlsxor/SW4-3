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
import db.Member;

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
	}
	
	//판매량 추가
	public static void addSellNum(int PID, int num) {
		account.get(PID).setSellNum(account.get(PID).getSellNum()+num);
	}
	
	//판매 금액 계산
	public static int calcMoney(int PID, int PNum) {
		return account.get(PID).getPprice()*PNum;
	}
	
	//총판매이익
	public static int totalPrice() {
		int total=0;
		for(Integer PID : account.keySet()) {
			total +=account.get(PID).getPTotalPrice();
		}
		return total;
	}
	

}
