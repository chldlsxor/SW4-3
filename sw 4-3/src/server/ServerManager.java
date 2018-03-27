package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.Member;

public class ServerManager {
	
	private static class Client extends Thread{
		//�Ѱ� ���
		private static List<Client> list = new ArrayList<>();
		private Map<String, Member> map = new HashMap<>();
		
		public static void add(Client c) {
			list.add(c);
		}
		public static void remove(Client c) {
			list.remove(c);
		}
	
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;
	
		public Client(Socket socket) throws IOException {
			this.socket = socket;
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			
			Client.add(this);
		}
		
		//Ŭ���̾�Ʈ���� �޼����� ������ ���
		public void send(boolean text) {
			try {
				out.writeBoolean(text);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//�޼ҵ� - run()
		@Override
		public void run() {
			try {
				
				System.out.println("�޾ƿñ��?");
				String str1 = in.readObject().toString();
				System.out.println(str1);
//				String str2 = in.readObject().toString();
//				System.out.println(str2);
				Member str2 = (Member)in.readObject();
				System.out.println("���̵��� ���"+str2);
				//map.put(str1, str2);
				
				//System.out.println("���� �޼��� : "+str1 +"���̵��� ���"+map.get(str1));
//				if(str1!=null) {
//					send(true);
//				}else {
//					send(false);
//				}
				in.close();
				socket.close();
				System.out.println("--------------");
			}catch(Exception e) {
				Client.remove(this);
				System.out.println("������");
			}
		}
	}
	
	ServerManager(){
		connect();
	}
	

	private Socket socket ;
	public void connect() {	
		try {
			ServerSocket server =new ServerSocket(20000);
			while(true) {
				socket = server.accept();
				System.out.println("���� �Ϸ�");
				
				Client c = new Client(socket);
				c.setDaemon(true);
				c.start();
				
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getPort());
			}
			
		} catch (IOException e) {}		
	}
}
