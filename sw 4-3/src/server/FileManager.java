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

    //회원디비 저장
    public static void saveDB(String id, Member m) {
		map.put(id, m);	
		try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(new File("DBFile", "Member.txt")))){
			fout.writeObject(map);
	        fout.flush();
		} catch (Exception e) {}
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
	
	//해당 아이디의 멤버 클래스 가져오기
	public static Member getUserClass(String id) {
		return map.get(id);
	}
	
	//사용자 이름 가져오기
	public static String getUserName(String id) {
		return map.get(id).getName();
	}
	
	//아이디 중복 확인
	public static boolean IDcheck(String id) {		
		return map.containsKey(id);	//아이디 존재 하면 true
	}
	
	//로그인 가능 여부 확인
	public static boolean loginCheck(String id, String pw) {		
		return IDcheck(id)&&map.get(id).getPw().equals(pw);	//로그인 가능하면 true
	}
	
	//시간 충전
	public static void chargeUserTime(String id, int money) {
		map.get(id).setTime(map.get(id).getTime()+plusTime(money));
	}
	
	//해당금액의 시간 반환
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
	
	//회원의 좌석 번호 가져오기
	public static String getUserPCNum(String id) {
		return map.get(id).getPcNum();
	}
	
	//PCNum 갱신
	public static void setUserPCNum(String id, String PCNum) {
		map.get(id).setPcNum(PCNum);
	}
	
	//회원의 시간 가져오기
	public static int getUserTime(String id) {
		return map.get(id).getTime();
	}
	
	public static int getUserMoney(String id) {
		return map.get(id).getMoney();
	}
	
	//회원의 ip 가져오기
	public static String getUserIP(String id) {
		return "192.168.0."+map.get(id).getPcNum();
	}
	public static String getUserBirth(String id) {
		return map.get(id).getBirth();
	}
		
	//회원의 남은 시간 저장
	public static void saveUserTime(String id, int restTime) {
		map.get(id).setTime(restTime);
	}
	
	//회원의 사용금액 저장
	public static void plusUserMoney(String id, int money) {
		map.get(id).setMoney(map.get(id).getMoney()+money);
	}
	
	//회원의 메모 저장
	public static void setUserMemo(String id, String txt) {
		map.get(id).setMemo(txt);
	}
	
	//회원의 메모 가져오기
	public static String getUserMemo(String id) {
		return map.get(id).getMemo();
	}
	
}
