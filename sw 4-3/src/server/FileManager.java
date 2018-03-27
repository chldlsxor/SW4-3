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

    //회원디비 저장
    public static void saveDB(String id, Member m) {
		map.put(id, m);	
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(new File("DBFile", "Member.txt")))){
			fout.writeObject(map);
	        fout.flush();
		} catch (Exception e) {
			System.out.println("실패...");
		}
	}
    
    //회원디비 가져오기
	public static void readDB() {
		try (ObjectInputStream fin = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("DBFile", "Member.txt"))));){
           map= (Map<String, Member>) fin.readObject();
        } catch (Exception e) {
            map = new HashMap<>();
       }
	}
	
	//아이디 중복 확인
	public static boolean IDcheck(String id) {		
		return !map.containsKey(id);	//아이디 존재 하면 false
	}
	
	//시간 충전
	public static void chargeTime(String id, int money) {
		int time;
		if(money==Header.PC1HOUR) {
			time = 60;
		}else {
			time=0;
		}
		map.get(id).setTime(time);
	}
	//PCNum추가
	public static void setPCNUM() {
		//map.get(id,)
	}
	
}
