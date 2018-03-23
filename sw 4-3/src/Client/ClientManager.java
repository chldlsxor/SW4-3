package Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

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
			connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("d");
		}
	}

	public void connect() {
		try {
			socket = new Socket(ip, port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String str) {
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			out.writeObject(str);
			out.flush();
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void memberSend(Member member) {
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			out.writeObject(member);
			out.flush();
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dateSend(Date time) {
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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
			my = in.readBoolean();
			Thread.sleep(1000);
			return my;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Long dateReceive() {
		
		return 0L;
	}

	public Boolean result() {
		return my;
	}

}
