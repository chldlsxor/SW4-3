package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

import header.Header;

public class ServerSendManager{

	private String ip ;
	private int port =30001;
	private InetAddress inet;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public ServerSendManager(String ip) {
		try {
			System.out.println(ip);
			System.out.println("1");
			this.inet = InetAddress.getByName(ip);
			System.out.println("2");
			this.socket = new Socket(inet, port);
			System.out.println("3");
			this.out = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("4");
			this.in = new ObjectInputStream(socket.getInputStream());
			System.out.println("연결완료1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("연결 완료2");
	}
	
	//client의 포트 가져오기
	public int getPort() {
		return port;
	}
	//메세지 보내기
	public void sendMessage(String str) {
		System.out.println("실행");
		try {
			out.writeChar(Header.MESSAGE);
			System.out.println(Header.MESSAGE);
			out.flush();
			out.writeObject(str);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconnect();
	}
	//피씨 시간 보내기
	public void sendPCTime(int PCTime) {
		try {
			out.writeChar(Header.CHARGE);
			out.flush();
			out.writeInt(PCTime);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		disconnect();
		
	}
	public void disconnect() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
