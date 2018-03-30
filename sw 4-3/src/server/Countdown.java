package server;

public class Countdown extends Thread{
	
	@Override
	public void run() {
		
		while(true) {
			for(String id : ServerManager.idList) {
//				System.out.println("이름 : "+FileManager.map.get(id).getName());
				FileManager.map.get(id).setTime(FileManager.map.get(id).getTime()-1);
//				System.out.println(id+"의 시간 : "+FileManager.map.get(id).getTime());		
				FileManager.saveDB(id, FileManager.map.get(id));
			}
			
			//System.out.println("남은 시간 : "+ time);	
			try {		
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
	}
	

}
