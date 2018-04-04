package server;

public class Countdown extends Thread{
	
	@Override
	public void run() {
		
		while(true) {
			for(String id : ServerManager.idList) {
				FileManager.map.get(id).setTime(FileManager.map.get(id).getTime()-1);	
				FileManager.saveDB(id, FileManager.map.get(id));
			}
			try {		
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
		
	}
	

}
