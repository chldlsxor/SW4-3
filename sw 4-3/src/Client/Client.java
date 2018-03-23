package Client;

public class Client {
	public static void main(String[] args) {
		ClientManager cmg = new ClientManager();
		
		cmg.connect();
		System.out.println("접속");
		cmg.send("Hello server");
		System.out.println("보냄");
		System.out.println(cmg.print());
		System.out.println("받음");
	}
}
