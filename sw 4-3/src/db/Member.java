package db;

import java.io.Serializable;

public class Member implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String pw;
	private String name;
	private String birth;
	private int time;
	private String pcNum;
	
	public Member(String pw, String name, String birth) {
		this.setPw(pw);
		this.setName(name);
		this.setBirth(birth);
		this.setTime(0);
		
	} 
	public String getName() {
		return name;
	}
	
	public void setPcNum(String pcNum) {
		this.pcNum = pcNum;
	}
	
	public String getPcNum() {
		return pcNum;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
}
