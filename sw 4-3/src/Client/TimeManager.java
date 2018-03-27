package Client;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeManager {
	// ClientManager cmg = new ClientManager();
	private Long time;
	private Date start;
	private Date now;
	private Date end;
	private Boolean flag = false;

	private Format f = new SimpleDateFormat("h:mm");

	public void setTime(Long time) {
		this.time = time;
	}
	
	public Long getTime() {
		return time;
	}

	public Boolean getFlag() {
		return flag;
	}

	public TimeManager() {
		// cmg.connect();
		// time = cmg.timeReceive();
		start = new Date();
		time = 90L;
		this.counter();

	}

	public void timeShow() {
	}

	public void counter() {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					now = new Date();
					time -= ((now.getTime() - start.getTime()) / 1000);
					start = now;
					if (time == 0) {
						flag = true;
						break;
					}
					System.out.println("남은 시간" + time / 360 + " / " + ((time / 60) + 1));

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		t.setDaemon(true);
		t.start();
	}

	public static void main(String[] args) {
		TimeManager tmg = new TimeManager();
		System.out.println("");
		if (tmg.getFlag()) {
			System.out.println("사용시간이 다 되었습니다.");
		}
	}
}
