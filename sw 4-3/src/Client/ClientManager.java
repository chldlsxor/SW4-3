package Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import db.Member;

public class ClientManager {
	private String ip = "192.168.0.130";
	private int port = 20000;
	private InetAddress inet;
	private Socket socket;
	private Boolean my;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ClientManager() {
		try {
			inet = InetAddress.getByName(ip);
//			System.out.println("inet 연결");
			connect();
//			System.out.println("커넥트");
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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
			socket = new Socket(inet, port);
//			System.out.println("소켓 연결완료");
		} catch (Exception e) {
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
	
	public void dateSend(Date time) {
		try {
//			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			out.writeObject(time);
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean receive() {
		try {
			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
//			System.out.println("in 스트림");
			my = in.readBoolean();
			Thread.sleep(1000);
			return my;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Long timeReceive() {
		try {
			
			return 0L;
		} catch (Exception e) {
			// TODO: handle exception
			return 0L;
		}
	}
	
	public Long dateReceive() {
		
		return 0L;
	}

	public Boolean result() {
		return my;
	}

}
