package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.Member;
import header.Header;

public class ServerManager {
	public static Set<String> idList = new HashSet<>();
	
	private static class Client extends Thread{
		//총괄 기능
		private static List<Client> list = new ArrayList<>();
		
		
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
		
		//클라이언트에게 참거짓을 보내는 기능
		public void send(boolean text) {
			try {
				out.writeBoolean(text);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//클라이언트에게 참거짓을 보내는 기능
//		public void sendHeader(char header) {
//			try {
//				out.writeChar(header);
//				out.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		//클라이언트에게 메세지를 보내는 기능
		public void sendMessage(String text) {
			try {
				//sendHeader(Header.MESSAGE);
				out.writeObject(text);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//클라이언트에게 시간을 보내는 기능
		public void sendTime(int time) {
			try {
				//sendHeader(Header.PLUS);
				out.writeInt(time);
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
					System.out.println("아이디 :" +id);
					System.out.println(FileManager.IDcheck(id));
					send(!FileManager.IDcheck(id));
					if(!FileManager.IDcheck(id)) {				
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
				System.out.println("아이디 : "+id);
				String pw= in.readObject().toString();//비번 받아옴
				System.out.println("비번 : "+pw);
				//map에 아이디랑 비번 존재하는지 보내기
				boolean loginCheck =FileManager.loginCheck(id, pw);
				send(loginCheck);
				if(loginCheck) {
					idList.add(id);
					String PCNum = in.readObject().toString();	//피씨 번호 받음
					FileManager.setPCNUM(id, PCNum);			//멤버에 피씨번호 저장
					
					int availableTime = FileManager.getUserTime(id);	//해당 아이디 가능한 시간 가져옴
					System.out.println("보낸 시간 : "+ availableTime);
					out.writeInt(availableTime);						//헤더 없이 시간 보냄
					out.flush();
					//sendTime(availableTime);							
				}
			}catch(Exception e) {}
		}
		//충전하기
		private void charge() {
			try {
				String id= in.readObject().toString();	//아이디 받아옴
				int money = in.readInt();				//금액 받아옴
				System.out.println(id);
				send(FileManager.IDcheck(id));
				if(FileManager.IDcheck(id)) {	//아이디가 존재하면					
					FileManager.chargeTime(id, money);	//시간 충전
				}			
				//만약 회원아이디가 로그인 된 아이디 리스트에 존재한다면 충전한 시간을 해당 PC로 보내줘야되..
				if(idList.contains(id)) {	
					//sendTime(3600);
					ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
					System.out.println("보낼 클라이언트의 아이피 : "+FileManager.getUserIP(id));
					ssm.sendPCTime(FileManager.plusTime(money));		
					ssm.disconnect();
				}
//				
			}catch(Exception e) {}
		}
		private void order() {
			try {
				String PCNum= in.readObject().toString();//주문자의 피씨 번호 받아옴
				System.out.println(PCNum);
				int PID = in.readInt();	//주문할 메뉴 받아옴
				int num = in.readInt();	//주문할 메뉴 수량 받아옴
				System.out.println(PID);
				//결제완료되면
				AccountManager.addSellNum(PID, num);
					
			}catch(Exception e) {}
		}
		public void message() {
			try {
				String PCNum = in.readObject().toString();
				String message = in.readObject().toString();
				System.out.println(PCNum +"에서 메세지 보냄 ->" + message);
				sendTime(1000);
				sendMessage(message);
				
			} catch (Exception e) {} 
			
		}
		public void save() {
			try {
				String id = in.readObject().toString();
				//남은시간을 Member.setTime으로..
				//추가 : 자리 널로★★★★★★★★★★★★★★★★★
				FileManager.setPCNUM(id, null);
				idList.remove(id);	
				
				
				for(String i : idList) {
					System.out.println(i);
				}	
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
		//메소드 - run()
		@Override
		public void run() {			
			try {
				char header = in.readChar();
				System.out.println("헤더 : "+header);
				if(header==Header.SIGNUP) {		//회원가입 헤더
					signup();
				}
				else if(header==Header.LOGIN) {	//로그인 헤더
					login();
				}
				else if(header ==Header.CHARGE) {
					charge();
				}else if(header==Header.MESSAGE) {
					message();
					//sendMessage("안녕");
				}else if(header ==  Header.SAVE) {
					save();
				}
//				else if(header == Header.ORDER) {
//					order();
//				}
				out.close();
				in.close();			
				socket.close();
				System.out.println("소켓 : "+socket.isConnected());
				
				System.out.print("지금까지 로그인 한 아이디 :  ");
				for(String id : ServerManager.idList) {
					System.out.print(id+" ");
				}
				
				System.out.println("--------------");
			}catch(Exception e) {
			}
			Client.remove(this);
			System.out.println("나가리");
		}
	}
	private Socket socket ;
	
	ServerManager(){
//		스레드 개수를 확인하는 테스트용 스레드
//		Thread th = new Thread(()->{
//			while(true) {
//				System.out.println("스레드 개수 : "+Thread.activeCount());
//				try {Thread.sleep(100);}catch(Exception e) {}
//			}
//		});
//		th.setDaemon(true);
//		th.start();
		
		connect();
	}
	
	public void connect() {	
		try {
			ServerSocket server =new ServerSocket(20000);
			FileManager.readDB();
			Countdown cd = new Countdown();
			cd.setDaemon(true);
			cd.start();
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
