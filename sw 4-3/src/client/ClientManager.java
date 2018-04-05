package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import db.Member;

public class ClientManager {
	private String ip;
	private int port = 20000;
	private InetAddress inet;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ClientManager() {
		try {
			System.out.println(getIP());
			inet = InetAddress.getByName(getIP());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			socket = new Socket(inet, port);
//			System.out.println("socket : "+socket);
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
//			System.out.println("소켓 연결완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		try {
			out.close();
			in.close();
			socket.close();
//			System.out.println("끊고"+socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String str) {
		try {
			out.writeObject(str);
			out.flush();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void headerSend(char header) {
		try {
			out.writeChar(header);
			out.flush();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void memberSend(Member member) {
		try {
			out.writeObject(member);
			out.flush();
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void intSend(int money) {
		try {
			out.writeInt(money);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean receive() {
		try {
			boolean ok = in.readBoolean();
			Thread.sleep(100);
			return ok;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public char headerReceive() {
		try {
			char header = in.readChar();
			Thread.sleep(100);
			return header;
		} catch (Exception e) {
			return '\0';
		}
	}

	public int plusReceive() {
		try {
			int time = in.readInt();
			Thread.sleep(100);
			return time;
		} catch (Exception e) {
			return 0;
		}
	}
	public String strReceive() {
		try {
			String str = in.readObject().toString();
			Thread.sleep(100);
			return str;
		} catch (Exception e) {
			return null;
		}
	}
	public String getIP() {
		String ip;
		try (BufferedReader fin = new BufferedReader(
                new FileReader(new File("DBFile", "ip.txt")));){
          ip = fin.readLine();
        } catch (Exception e) {
        	ip = null;
       }
		return ip;
	}
}
