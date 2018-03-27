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
		//�Ѱ� ���
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
		//Ŭ���̾�Ʈ���� �޼����� ������ ���
		public void sendMessage(String text) {
			try {
				out.writeChar('f');
				out.flush();
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
				out.writeChar('e');
				out.flush();
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
					String PCNum = in.readObject().toString();//�Ǿ� ��ȣ �����				
					FileManager.setPCNUM(id, PCNum);
					sendTime(1000);
					//FileManager.set
					
				}
			}catch(Exception e) {}
		}
		//�����ϱ�
		private void charge() {
			try {
				String id= in.readObject().toString();//���̵� �޾ƿ�
				System.out.println(id);
				int money = in.readInt();//���� �ݾ� �޾ƿ�
				System.out.println(money);
				FileManager.chargeTime(id, money);
				
				
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
				}else if(header=='f') {
					message();
					//sendMessage("�ȳ�");
				}
//				else if(header == Header.ORDER) {
//					order();
//				}
				in.close();
				socket.close();
				
				System.out.print("���ݱ��� �α��� �� ���̵� :  ");
				for(int i=0;i<idList.size();i++) {
					System.out.print(idList.get(i)+" ");
				}
				
				System.out.println("--------------");
			}catch(Exception e) {
				Client.remove(this);
				System.out.println("������");
			}
		}
	}
	private Socket socket ;
	
	ServerManager(){
		connect();
	}
	
	public void connect() {	
		try {
			ServerSocket server =new ServerSocket(20000);
			FileManager.readDB();
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
