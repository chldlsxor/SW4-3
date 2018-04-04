package client;

import java.io.IOException;
import java.net.InetAddress;

public class Client {
	public static void main(String[] args) throws IOException {
		InetAddress ip = InetAddress.getLocalHost();
		String num = ip.getHostAddress();
		String numArr[] = num.split("\\.");
		Wait wait = new Wait(numArr[3]);
		ClientManager2 cm = new ClientManager2();
//		Login l = new Login("129","id",1000,30);
//		Sign sign = new Sign();
//		Charger charger = new Charger();
//		Menu menu = new Menu("asdf");
	}
}
