package Client;

import java.util.HashMap;
import java.util.Map;

import db.Member;

public class Client {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("접속 전");
		ClientManager cmg = new ClientManager();
		 System.out.println("접속");
		 Member mem = new Member("master","java","941128");
		 cmg.send("KgitBank");
		 System.out.println("전송");
//		 System.out.println(cmg.receive());
		 cmg.memberSend(new Member("master","java","941128"));
		 System.out.println("보냄");
		 System.out.println(cmg.receive());
		 System.out.println("받음");

	}
}
