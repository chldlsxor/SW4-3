package Client;

public class Client {
	public static void main(String[] args) {
		ClientManager cmg = new ClientManager();
		
		cmg.connect();
		System.out.println("����");
		cmg.send("Hello server");
		System.out.println("����");
		System.out.println(cmg.print());
		System.out.println("����");
	}
}
