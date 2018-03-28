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
import header.Header;

public class FileManager {
	
	public static Map<String, Member> map = new HashMap<>();

    //ȸ����� ����
    public static void saveDB(String id, Member m) {
		map.put(id, m);	
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(new File("DBFile", "Member.txt")))){
			fout.writeObject(map);
	        fout.flush();
		} catch (Exception e) {
			System.out.println("����...");
		}
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
	
	//���̵� �ߺ� Ȯ��
	public static boolean IDcheck(String id) {		
		System.out.println("����!! "+!map.containsKey(id));
		return !map.containsKey(id);	//���̵� ���� �ϸ� false
	}
	
	//�ð� ����
	public static void chargeTime(String id, int money) {
		int time= map.get(id).getTime();
		if(money==Header.PC1HOUR) {
			time += 3600;
		}else {
			time+=0;
		}
		map.get(id).setTime(time);
	}
	//PCNum�߰�
	public static void setPCNUM(String id, String PCNum) {
		map.get(id).setPcNum(PCNum);
	}
	//ȸ���� �ð� ������
	public static int getUserTime(String id) {
		return map.get(id).getTime();
	}
	//ȸ���� ���� �ð� ����
	public static void saveTime(String id, int restTime) {
		map.get(id).setTime(restTime);
	}
	
	
}
