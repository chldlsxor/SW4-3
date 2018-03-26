package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import db.Member;
import header.Header;

public class ServerManager {
	
	private static class Client extends Thread{
		//총괄 기능
		private static List<Client> list = new ArrayList<>();
		private static List<String> idList = new ArrayList<>();
		
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
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			System.out.println("연결 완료");
			Client.add(this);
		}
		
		//클라이언트에게 메세지를 보내는 기능
		public void send(boolean text) {
			try {
				out.writeBoolean(text);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//회원가입
		private void signup() {
			try {
				String id;
				while(true) {
					id= in.readObject().toString();//아이디 받아옴
					System.out.println(id);
					send(FileManager.IDcheck(id));
					if(FileManager.IDcheck(id)) {				
						break;
					}		
					
				}		
				Member member = (Member)in.readObject();
				System.out.println("아이디의 멤버 클래스 : "+member);
				FileManager.saveDB(id, member);
			}catch(Exception e) {}	
		}
		//로그인
		private void login() {
			try {
				String id= in.readObject().toString();//아이디 받아옴
				System.out.println(id);
				String pw= in.readObject().toString();//비번 받아옴
				System.out.println(pw);
				boolean a = false;
				if(FileManager.map.containsKey(id)) {
					if(FileManager.map.get(id).getPw().equals(pw)) {
						a = true;
					}
				}
				idList.add(id);
				send(a);
			}catch(Exception e) {}
		}
		private void charge() {
			try {
				String id= in.readObject().toString();//아이디 받아옴
				System.out.println(id);
				int money = in.readInt();//충전 금액 받아옴
				System.out.println(money);
				
			}catch(Exception e) {}
		}
		
		//메소드 - run()
		@Override
		public void run() {			
			try {
				char header = in.readChar();
				System.out.println(header);
				if(header==Header.SIGNUP) {		//회원가입 헤더
					signup();
				}
				else if(header==Header.LOGIN) {	//로그인 헤더
					login();
				}
				else if(header ==Header.CHARGE) {
					charge();
				}
				in.close();
				socket.close();
				for(int i=0;i<list.size();i++) {
					System.out.println(list.get(i).get);
				}
				
				System.out.println("--------------");
			}catch(Exception e) {
				Client.remove(this);
				System.out.println("나가리");
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
			FileManager.readDB();
			while(true) {
				socket = server.accept();
				System.out.println("연결 가능");
				
				Client c = new Client(socket);
				c.setDaemon(true);
				c.start();
				
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getPort());
			}
			
		} catch (IOException e) {}		
	}
}
