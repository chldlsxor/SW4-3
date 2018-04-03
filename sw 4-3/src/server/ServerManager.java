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

import javax.swing.JOptionPane;

import db.Member;
import header.Header;

public class ServerManager extends Thread{
	
	public static List<String> idList = new ArrayList<>();
	
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
				boolean loginCheck = false;
				if(FileManager.loginCheck(id, pw) && !idList.contains(id)) loginCheck = true;
				System.out.println("loginCheck : "+loginCheck);
				
				send(loginCheck);
				
				if(loginCheck) {
					out.writeObject(FileManager.getUserBirth(id));//생년월일 보냄		//★★★★★미성년자면 클라이언트는 피씨번호 안보내는데 그럼 어케 되..?
					String PCNum = in.readObject().toString();	//피씨 번호 받음	//미성년자면 0보낼꺼
					System.out.println("PCNum : "+PCNum);
					if(!PCNum.equals("-1")) {					//미성년자가 아니면
						
						int availableTime = FileManager.getUserTime(id);	//해당 아이디 가능한 시간 가져옴
						System.out.println("보낸 시간 : "+ availableTime);
						CounterFrame.seatCheck[Integer.parseInt(PCNum)-120] = true;
						CounterFrame.userIdUse[Integer.parseInt(PCNum)-120] = id;
						out.writeInt(availableTime);						//헤더 없이 시간 보냄
						out.flush();
						if(availableTime>60) {							
							idList.add(id);								//로그인한 회원리스트에 추가하고 카운트 다운 시작	
							FileManager.setUserPCNum(id, PCNum);			//해당 아이디 멤버에 피씨번호 저장
						}
					}				
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
					FileManager.chargeUserTime(id, money);	//시간 충전
					FileManager.plusUserMoney(id, money);	//사용금액 갱신
					AccountManager.addTotalPCPrice(money);	//회계에 PC 사용금액 추가
					AccountManager.saveDB(Header.PCID,AccountManager.getAccount(Header.PCID) );
				}			
				//만약 회원아이디가 로그인 된 아이디 리스트에 존재한다면 충전한 시간을 해당 PC로 보내줘야되..
				if(idList.contains(id)) {	
					//sendTime(3600);
					ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
					System.out.println("보낼 클라이언트의 아이피 : "+FileManager.getUserIP(id));
					ssm.sendPCTime(FileManager.plusTime(money));		
//					ssm.disconnect();
					CounterFrame.time(id, FileManager.getUserTime(id));
				}	
			}catch(Exception e) {}
		}
		
		//주문받기
		private void order() {
			try {
				String id= in.readObject().toString();//주문자의 아이디 받아옴->피씨 번호 알아올 수 있당..
				System.out.println(FileManager.getUserPCNum(id));	//피씨 번호 표시
				int Menunum = in.readInt();//메뉴 종류 개수 받아오기
				for(int i=0;i<Menunum;i++) {
					int PID = in.readInt();	//주문할 메뉴 받아옴
					int PNum = in.readInt();	//주문할 메뉴 수량 받아옴
					System.out.println(PID+"를 "+PNum +"개 주문");
					AccountManager.addSellNum(PID, PNum);//주문한 제품이랑 수량갱신
					//해당아이디 가 지금까지 얼마 썻는지 갱신
					int money = AccountManager.calcOrderMoney(PID, PNum);
					FileManager.plusUserMoney(id, money);
					AccountManager.saveDB(PID, AccountManager.getAccount(PID));
					CounterFrame.orderCheck = true;
					CounterFrame.orderId.add(id);
					CounterFrame.orderInfo.add(PID);
					CounterFrame.orderInfo.add(PNum);
				}			
			}catch(Exception e) {}
		}
		
		
		//메세지 받기
		public void message() {
			try {
				String PCNum = in.readObject().toString();		//피씨 번호 받
				String message = in.readObject().toString();	//메세지 받
				JOptionPane.showMessageDialog(null, message,PCNum+" 자리",JOptionPane.PLAIN_MESSAGE);
				System.out.println(PCNum +"에서 메세지 보냄 ->" + message);				
			} catch (Exception e) {} 
			
		}
		
		//client 피씨 종료 시  갱신 정보 저장하기
		public void save() {
			try {
				String id = in.readObject().toString();
				CounterFrame.seatCheck[Integer.parseInt(FileManager.getUserPCNum(id))-120] = false;
				FileManager.setUserPCNum(id, null);	// 해당아이디의 Member에 저장된 피씨자리를 Null로
				idList.remove(id);				// countdown 스레드에 아이디가 빠지면서 시간이 줄지 않고 그대로 저장
//				CounterFrame.useCheck[Integer.parseInt(FileManager.getUserPCNum(id))-120] = true;
				
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
				if(header==Header.SIGNUP) signup();
				else if(header==Header.LOGIN) login();
				else if(header ==Header.CHARGE) charge();
				else if(header==Header.MESSAGE) message();
				else if(header ==  Header.SAVE) save();
				else if(header == Header.ORDER) order();

				out.close();
				in.close();			
				socket.close();
				
				System.out.print("지금까지 로그인 한 아이디 :  ");
				for(String id : ServerManager.idList) {
					System.out.print(id+" ");
				}
				
				System.out.println("--------------");
			}catch(Exception e) {}
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
		
//		connect();
	}
	
	public void run() {	
		try {
			ServerSocket server =new ServerSocket(20000);
			FileManager.readDB();
			AccountManager.readDB();
			Countdown cd = new Countdown();
			cd.setDaemon(true);
			cd.start();
			while(true) {
				socket = server.accept();
				System.out.println("연결 가능");
				
				Client c = new Client(socket);
				c.setDaemon(true);
				c.start();
				
				System.out.println("Server에게 신호 보낸 client의 ip : "+socket.getInetAddress());
				System.out.println("Server에게 신호 보낸 client의 port : "+socket.getPort());
			}
			
		} catch (IOException e) {}		
	}
}
