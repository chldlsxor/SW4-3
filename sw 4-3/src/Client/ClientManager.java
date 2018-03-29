package Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import db.Member;

public class ClientManager {
	private String ip = "192.168.0.130";
	private int port = 20000;
	private InetAddress inet;
	private Socket socket;
	private Boolean my;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private char header;
	private int time;
	private String str;

	public ClientManager() {
		try {
			inet = InetAddress.getByName(ip);
//			System.out.println("inet 연결");
//			connect();
//			System.out.println("커넥트");
//			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//			System.out.println("out 스트림");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("d");
		}
	}

	public void connect() {
		try {
//			System.out.println("커넥트 진입");
			System.out.println("연결전"+socket);
			socket = new Socket(inet, port);
			System.out.println("연결후"+socket);
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			System.out.println("11111");
			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			System.out.println("22222");
//			System.out.println("소켓 연결완료");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void aaa() {
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exit() {
		try {
			out.close();
			in.close();
			socket.close();
			System.out.println("끊고"+socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(String str) {
		try {
			out.writeObject(str);
			out.flush();
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void headerSend(char header) {
		try {
			out.writeChar(header);
			out.flush();
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void memberSend(Member member) {
		try {
			out.writeObject(member);
			out.flush();
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void intSend(int money) {
		try {
			out.writeInt(money);
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean receive() {
		try {
			my = in.readBoolean();
			Thread.sleep(100);
			return my;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public char headerReceive() {
		try {
			header = in.readChar();
			Thread.sleep(100);
			return header;
		} catch (Exception e) {
			// TODO: handle exception
			return '\0';
		}
	}

	public int plusReceive() {
		try {
			time = in.readInt();
			Thread.sleep(100);
			return time;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	public String strReceive() {
		try {
			str = in.readObject().toString();
			Thread.sleep(100);
			return str;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}


}
