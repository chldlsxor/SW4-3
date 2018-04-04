package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import header.Header;

public class ServerSendManager{

	private int port =30001;
	private InetAddress inet;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public ServerSendManager(String ip) {
		try {
			this.inet = InetAddress.getByName(ip);
			this.socket = new Socket(inet, port);
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {}	
	}
	
	//�޼��� ������
	public void sendMessage(String str) {
		try {
			out.writeChar(Header.MESSAGE);
			System.out.println(Header.MESSAGE);
			out.flush();
			out.writeObject(str);
			out.flush();
		} catch (IOException e) {}
		disconnect();
	}
	
	//�Ǿ� �ð� ������
	public void sendPCTime(int PCTime) {
		try {
			out.writeChar(Header.CHARGE);
			out.flush();
			out.writeInt(PCTime);
			out.flush();
		} catch (IOException e) {}
		disconnect();
	}
	
	//PC���� ���� ��ȣ ������
	public void sendShutDownPC() {
		try {
			out.writeChar(Header.SHUTDOWN);	//�Ǿ� ���� ���� ��ȣ
			out.flush();
		} catch (IOException e) {}
		disconnect();
		
	}
	
	//���� ����
	public void disconnect() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {}
	}
}
