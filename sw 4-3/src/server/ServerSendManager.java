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
	private int port =30000;
	private InetAddress inet;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public ServerSendManager(String ip) {
		try {
			this.inet = InetAddress.getByName(ip);
			this.socket = new Socket(inet, getPort());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("연결 완료");
	}
	
	//client의 포트 가져오기
	public int getPort() {
		return port;
	}
	//메세지 보내기
	public void sendMessage(String str) {
		try {
			out.writeChar(Header.MESSAGE);
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
