package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import db.Member;
import header.Header;

public class FileManager {
	
	public static Map<String, Member> map = new HashMap<>();

    //ȸ����� ����
    public static void saveDB(String id, Member m) {
		map.put(id, m);	
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(new File("DBFile", "Member.txt")))){
			fout.writeObject(map);
	        fout.flush();
		} catch (Exception e) {}
	}
    
    //ȸ����� ��������
	public static void readDB() {
		try (ObjectInputStream fin = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("DBFile", "Member.txt"))));){
           map= (Map<String, Member>) fin.readObject();
        } catch (Exception e) {
            map = new HashMap<>();
       }
	}
	
	//�ش� ���̵��� ��� Ŭ���� ��������
	public static Member getUserClass(String id) {
		return map.get(id);
	}
	
	//����� �̸� ��������
	public static String getUserName(String id) {
		return map.get(id).getName();
	}
	
	//���̵� �ߺ� Ȯ��
	public static boolean IDcheck(String id) {		
		return map.containsKey(id);	//���̵� ���� �ϸ� true
	}
	
	//�α��� ���� ���� Ȯ��
	public static boolean loginCheck(String id, String pw) {		
		return IDcheck(id)&&map.get(id).getPw().equals(pw);	//�α��� �����ϸ� true
	}
	
	//�ð� ����
	public static void chargeUserTime(String id, int money) {
		map.get(id).setTime(map.get(id).getTime()+plusTime(money));
	}
	
	//�ش�ݾ��� �ð� ��ȯ
	public static int plusTime(int money) {
		int time=0;
		if(money==Header.PC1HOUR) {
			time = 3600;
		}else if(money ==Header.PC3HOUR){
			time = 3600*3;
		}else if(money ==Header.PC5HOUR){
			time = 3600*5;
		}else if(money ==Header.PC10HOUR){
			time = 36000;
		}
		return time;
	}
	
	//ȸ���� �¼� ��ȣ ��������
	public static String getUserPCNum(String id) {
		return map.get(id).getPcNum();
	}
	
	//PCNum ����
	public static void setUserPCNum(String id, String PCNum) {
		map.get(id).setPcNum(PCNum);
	}
	
	//ȸ���� �ð� ��������
	public static int getUserTime(String id) {
		return map.get(id).getTime();
	}
	
	public static int getUserMoney(String id) {
		return map.get(id).getMoney();
	}
	
	//ȸ���� ip ��������
	public static String getUserIP(String id) {
		return "192.168.0."+map.get(id).getPcNum();
	}
	public static String getUserBirth(String id) {
		return map.get(id).getBirth();
	}
		
	//ȸ���� ���� �ð� ����
	public static void saveUserTime(String id, int restTime) {
		map.get(id).setTime(restTime);
	}
	
	//ȸ���� ���ݾ� ����
	public static void plusUserMoney(String id, int money) {
		map.get(id).setMoney(map.get(id).getMoney()+money);
	}
	
	//ȸ���� �޸� ����
	public static void setUserMemo(String id, String txt) {
		map.get(id).setMemo(txt);
	}
	
	//ȸ���� �޸� ��������
	public static String getUserMemo(String id) {
		return map.get(id).getMemo();
	}
	
}
