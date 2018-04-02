package sign;

import java.io.IOException;
import java.net.InetAddress;

<<<<<<< HEAD
//import Client.ClientManager2;

=======
>>>>>>> branch 'master' of https://github.com/chldlsxor/SW4-3
public class Client {
	public static void main(String[] args) throws IOException {
		InetAddress ip = InetAddress.getLocalHost();
		String num = ip.getHostAddress();
		String numArr[] = num.split("\\.");
		Wait wait = new Wait(numArr[3]);
<<<<<<< HEAD
		Login login = new Login(numArr[3]);
		Charger charger = new Charger();
		JOptionPane.showInputDialog("ют╥б");
		ClientManager2 cm = new ClientManager2();
=======
//		Charger charger = new Charger();
//		ClientManager2 cm = new ClientManager2();
//		GoodsManager gm = new GoodsManager();
>>>>>>> branch 'master' of https://github.com/chldlsxor/SW4-3
	}
}
