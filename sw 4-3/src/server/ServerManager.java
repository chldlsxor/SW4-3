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
	public static List<String> idList = new ArrayList<>();
	
	private static class Client extends Thread{
		//�Ѱ� ���
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
			System.out.println("���� �Ϸ�");
			Client.add(this);
			
		}
		
		//Ŭ���̾�Ʈ���� �������� ������ ���
		public void send(boolean text) {
			try {
				out.writeBoolean(text);
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Ŭ���̾�Ʈ���� �������� ������ ���
//		public void sendHeader(char header) {
//			try {
//				out.writeChar(header);
//				out.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		//Ŭ���̾�Ʈ���� �޼����� ������ ���
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
		//Ŭ���̾�Ʈ���� �ð��� ������ ���
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
		//ȸ������
		private void signup() {
			try {
				String id;
				while(true) {
					id= in.readObject().toString();//���̵� �޾ƿ�
					System.out.println("���̵� :" +id);
					System.out.println(FileManager.IDcheck(id));
					send(FileManager.IDcheck(id));
					if(FileManager.IDcheck(id)) {				
						break;
					}		
					
				}		
				System.out.println("�Դ�?");
				Member member = (Member)in.readObject();
				System.out.println("���̵��� ��� Ŭ���� : "+member);
				FileManager.saveDB(id, member);
			}catch(Exception e) {}	
		}
		//�α���
		private void login() {
			try {
				String id= in.readObject().toString();//���̵� �޾ƿ�
				System.out.println("���̵� : "+id);
				String pw= in.readObject().toString();//��� �޾ƿ�
				System.out.println("��� : "+pw);
				boolean loginCheck = false;
				if(FileManager.map.containsKey(id)) {
					if(FileManager.map.get(id).getPw().equals(pw)) {
						loginCheck = true;
					}
				}
				
				idList.add(id);
				send(loginCheck);
				if(loginCheck) {
					String PCNum = in.readObject().toString();	//�Ǿ� ��ȣ ����
					FileManager.setPCNUM(id, PCNum);			//����� �Ǿ���ȣ ����
					
					int availableTime = FileManager.getUserTime(id);	//�ش� ���̵� ������ �ð� ������
					System.out.println("���� �ð� : "+ availableTime);
					out.writeInt(availableTime);						//��� ���� �ð� ����
					out.flush();
					//sendTime(availableTime);							
				}
			}catch(Exception e) {}
		}
		//�����ϱ�
		private void charge() {
			try {
				String id= in.readObject().toString();	//���̵� �޾ƿ�
				System.out.println(id);
				boolean isID= false;
				if(FileManager.map.containsKey(id)) {	//���̵� �����ϸ�
					isID = true;						
					FileManager.chargeTime(id, 1000);	//�ð� ����
				}
				//System.out.println(FileManager.map.get(id).getTime());
				System.out.println(isID);
				send(isID);
				//���� ȸ�����̵� �α��� �� ���̵� ����Ʈ�� �����Ѵٸ� ������ �ð��� �ش� PC�� ������ߵ�..
//				if(idList.contains(id)) {	
//					sendTime(3600);
//				}
//				
			}catch(Exception e) {}
		}
		private void order() {
			try {
				String PCNum= in.readObject().toString();//�ֹ����� �Ǿ� ��ȣ �޾ƿ�
				System.out.println(PCNum);
				int PID = in.readInt();	//�ֹ��� �޴� �޾ƿ�
				int num = in.readInt();	//�ֹ��� �޴� ���� �޾ƿ�
				System.out.println(PID);
				//�����Ϸ�Ǹ�
				AccountManager.addSellNum(PID, num);
					
			}catch(Exception e) {}
		}
		public void message() {
			try {
				String PCNum = in.readObject().toString();
				String message = in.readObject().toString();
				System.out.println(PCNum +"���� �޼��� ���� ->" + message);
				sendTime(1000);
				sendMessage(message);
				
			} catch (Exception e) {} 
			
		}
		public void save() {
			try {
				String id = in.readObject().toString();
				//�����ð��� Member.setTime����..
				idList.remove(id);	
				for(int i=0;i<idList.size();i++) {
					System.out.println(idList.get(i));
				}	
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		
		//�޼ҵ� - run()
		@Override
		public void run() {			
			try {
				char header = in.readChar();
				System.out.println("��� : "+header);
				if(header==Header.SIGNUP) {		//ȸ������ ���
					signup();
				}
				else if(header==Header.LOGIN) {	//�α��� ���
					login();
				}
				else if(header ==Header.CHARGE) {
					charge();
				}else if(header==Header.MESSAGE) {
					message();
					//sendMessage("�ȳ�");
				}else if(header ==  Header.SAVE) {
					save();
				}
//				else if(header == Header.ORDER) {
//					order();
//				}
				out.close();
				in.close();			
				socket.close();
				System.out.println("���� : "+socket.isConnected());
				
				System.out.print("���ݱ��� �α��� �� ���̵� :  ");
				for(String id : ServerManager.idList) {
					System.out.print(id+" ");
				}
				
				System.out.println("--------------");
			}catch(Exception e) {
			}
			Client.remove(this);
			System.out.println("������");
		}
	}
	private Socket socket ;
	
	ServerManager(){
//		������ ������ Ȯ���ϴ� �׽�Ʈ�� ������
//		Thread th = new Thread(()->{
//			while(true) {
//				System.out.println("������ ���� : "+Thread.activeCount());
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
				System.out.println("���� ����");
				
				Client c = new Client(socket);
				c.setDaemon(true);
				c.start();
				
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getPort());
			}
			
		} catch (IOException e) {}		
	}
}
