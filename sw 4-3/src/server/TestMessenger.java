package server;

public class TestMessenger {

	public static void main(String[] args) {
		ServerSendManager mm = new ServerSendManager("192.168.0.129");
		mm.sendPCTime(1000);
		mm.sendMessage("���̿�");
		
	}
}
