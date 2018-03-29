package sign;

import java.io.IOException;
import java.net.InetAddress;

import Client.ClientManager2;

public class Client {
	public static void main(String[] args) throws IOException {
		InetAddress ip = InetAddress.getLocalHost();
		String num = ip.getHostAddress();
		String numArr[] = num.split("\\.");
		Wait wait = new Wait(numArr[3]);
//		Login login = new Login(numArr[3]);
		Charger charger = new Charger();
//		JOptionPane.showInputDialog("ют╥б");
		ClientManager2 cm = new ClientManager2();
	}
}
