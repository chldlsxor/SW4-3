package server;

import java.util.HashMap;
import java.util.Map;

import db.Account;

public class AccountManager {
	public static Map<Integer, Account> account = new HashMap<>();
	
	//�Ǹŷ� �߰�
	public static void addSellNum(int PID, int num) {
		account.get(PID).setSellNum(account.get(PID).getSellNum()+num);
	}
	
	//���Ǹ�����
	public static int totalPrice() {
		int total=0;
		for(Integer PID : account.keySet()) {
			total +=account.get(PID).getPTotalPrice();
		}
		return total;
	}

}
