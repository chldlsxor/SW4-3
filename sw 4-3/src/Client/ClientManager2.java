package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import header.Header;
import sign.Login;

public class ClientManager2 {
	private static class Client extends Thread{
		
		private Socket socket;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		
		public Client(Socket socket) throws IOException {
			this.socket = socket;
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			
		}
		public void message() {
			try {
				String message = in.readObject().toString();
				System.out.println("내용 : " + message);		
				JOptionPane.showMessageDialog(null, message);
			} catch (Exception e) {} 
		}
		private void charge() {
			try {
				int time = in.readInt();
				JOptionPane.showMessageDialog(null, time);
				Login.plus(time);
				System.out.println(time);
			}catch(Exception e) {}
		}
		public void run() {
			try {
				char header = in.readChar();
				System.out.println("헤더 : "+header);
				if(header==Header.MESSAGE) {
					message();
				}
				else if(header==Header.CHARGE) {
					charge();
				}
				System.out.println("aaa");
				out.close();
				in.close();			
				socket.close();
			}catch(Exception e) {}
		}
	}
	public ClientManager2(){
		connectWait();
	}
	
	public void connectWait() {
		try {
			ServerSocket server = new ServerSocket(30000);
			while(true) {
				Socket socket = server.accept();
				System.out.println("연결 가능");
				
				Client c = new Client(socket);
				c.setDaemon(true);
				c.start();
				
				System.out.println(socket.getInetAddress());
				System.out.println(socket.getPort());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
