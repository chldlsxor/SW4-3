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
		
		//ȸ������
		private void signup() {
			try {
				String id;
				while(true) {
					id= in.readObject().toString();//���̵� �޾ƿ�
					System.out.println("���̵� :" +id);
					System.out.println(FileManager.IDcheck(id));
					send(!FileManager.IDcheck(id));
					if(!FileManager.IDcheck(id)) {				
						break;
					}							
				}		
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
				//map�� ���̵�� ��� �����ϴ��� ������
				boolean loginCheck = false;
				if(FileManager.loginCheck(id, pw) && !idList.contains(id)) loginCheck = true;
				System.out.println("loginCheck : "+loginCheck);
				
				send(loginCheck);
				
				if(loginCheck) {
					out.writeObject(FileManager.getUserBirth(id));//������� ����		//�ڡڡڡڡڹ̼����ڸ� Ŭ���̾�Ʈ�� �Ǿ���ȣ �Ⱥ����µ� �׷� ���� ��..?
					String PCNum = in.readObject().toString();	//�Ǿ� ��ȣ ����	//�̼����ڸ� 0������
					System.out.println("PCNum : "+PCNum);
					if(!PCNum.equals("-1")) {					//�̼����ڰ� �ƴϸ�
						
						int availableTime = FileManager.getUserTime(id);	//�ش� ���̵� ������ �ð� ������
						System.out.println("���� �ð� : "+ availableTime);
						CounterFrame.seatCheck[Integer.parseInt(PCNum)-120] = true;
						CounterFrame.userIdUse[Integer.parseInt(PCNum)-120] = id;
						out.writeInt(availableTime);						//��� ���� �ð� ����
						out.flush();
						if(availableTime>60) {							
							idList.add(id);								//�α����� ȸ������Ʈ�� �߰��ϰ� ī��Ʈ �ٿ� ����	
							FileManager.setUserPCNum(id, PCNum);			//�ش� ���̵� ����� �Ǿ���ȣ ����
						}
					}				
				}
			}catch(Exception e) {}
		}
		
		//�����ϱ�
		private void charge() {
			try {
				String id= in.readObject().toString();	//���̵� �޾ƿ�
				int money = in.readInt();				//�ݾ� �޾ƿ�
				System.out.println(id);
				send(FileManager.IDcheck(id));
				if(FileManager.IDcheck(id)) {	//���̵� �����ϸ�					
					FileManager.chargeUserTime(id, money);	//�ð� ����
					FileManager.plusUserMoney(id, money);	//���ݾ� ����
					AccountManager.addTotalPCPrice(money);	//ȸ�迡 PC ���ݾ� �߰�
					AccountManager.saveDB(Header.PCID,AccountManager.getAccount(Header.PCID) );
				}			
				//���� ȸ�����̵� �α��� �� ���̵� ����Ʈ�� �����Ѵٸ� ������ �ð��� �ش� PC�� ������ߵ�..
				if(idList.contains(id)) {	
					//sendTime(3600);
					ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
					System.out.println("���� Ŭ���̾�Ʈ�� ������ : "+FileManager.getUserIP(id));
					ssm.sendPCTime(FileManager.plusTime(money));		
//					ssm.disconnect();
					CounterFrame.time(id, FileManager.getUserTime(id));
				}	
			}catch(Exception e) {}
		}
		
		//�ֹ��ޱ�
		private void order() {
			try {
				String id= in.readObject().toString();//�ֹ����� ���̵� �޾ƿ�->�Ǿ� ��ȣ �˾ƿ� �� �ִ�..
				System.out.println(FileManager.getUserPCNum(id));	//�Ǿ� ��ȣ ǥ��
				int Menunum = in.readInt();//�޴� ���� ���� �޾ƿ���
				for(int i=0;i<Menunum;i++) {
					int PID = in.readInt();	//�ֹ��� �޴� �޾ƿ�
					int PNum = in.readInt();	//�ֹ��� �޴� ���� �޾ƿ�
					System.out.println(PID+"�� "+PNum +"�� �ֹ�");
					AccountManager.addSellNum(PID, PNum);//�ֹ��� ��ǰ�̶� ��������
					//�ش���̵� �� ���ݱ��� �� ������ ����
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
		
		
		//�޼��� �ޱ�
		public void message() {
			try {
				String PCNum = in.readObject().toString();		//�Ǿ� ��ȣ ��
				String message = in.readObject().toString();	//�޼��� ��
				JOptionPane.showMessageDialog(null, message,PCNum+" �ڸ�",JOptionPane.PLAIN_MESSAGE);
				System.out.println(PCNum +"���� �޼��� ���� ->" + message);				
			} catch (Exception e) {} 
			
		}
		
		//client �Ǿ� ���� ��  ���� ���� �����ϱ�
		public void save() {
			try {
				String id = in.readObject().toString();
				CounterFrame.seatCheck[Integer.parseInt(FileManager.getUserPCNum(id))-120] = false;
				FileManager.setUserPCNum(id, null);	// �ش���̵��� Member�� ����� �Ǿ��ڸ��� Null��
				idList.remove(id);				// countdown �����忡 ���̵� �����鼭 �ð��� ���� �ʰ� �״�� ����
//				CounterFrame.useCheck[Integer.parseInt(FileManager.getUserPCNum(id))-120] = true;
				
				for(String i : idList) {
					System.out.println(i);
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
				if(header==Header.SIGNUP) signup();
				else if(header==Header.LOGIN) login();
				else if(header ==Header.CHARGE) charge();
				else if(header==Header.MESSAGE) message();
				else if(header ==  Header.SAVE) save();
				else if(header == Header.ORDER) order();

				out.close();
				in.close();			
				socket.close();
				
				System.out.print("���ݱ��� �α��� �� ���̵� :  ");
				for(String id : ServerManager.idList) {
					System.out.print(id+" ");
				}
				
				System.out.println("--------------");
			}catch(Exception e) {}
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
				System.out.println("���� ����");
				
				Client c = new Client(socket);
				c.setDaemon(true);
				c.start();
				
				System.out.println("Server���� ��ȣ ���� client�� ip : "+socket.getInetAddress());
				System.out.println("Server���� ��ȣ ���� client�� port : "+socket.getPort());
			}
			
		} catch (IOException e) {}		
	}
}
