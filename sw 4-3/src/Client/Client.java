package Client;

import java.util.HashMap;
import java.util.Map;

import db.Member;

public class Client {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("���� ��");
		ClientManager cmg = new ClientManager();
		System.out.println("����");
		Member mem = new Member("master","java","941128");
		cmg.send("Kg����itB");
		System.out.println("����");
//		System.out.println(cmg.receive());
		cmg.memberSend(new Member("master","jv��a","941128"));
		System.out.println("����");
		System.out.println(cmg.receive());
		System.out.println("����");

	}
}
