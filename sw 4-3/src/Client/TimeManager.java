package Client;

import java.util.Date;

public class TimeManager {
//	ClientManager cmg = new ClientManager();
	private Long time;
	private Date start;
	private Date now;
//	private Date end;
	private Boolean flag = false;
	private String ret = "";

//	private Format f = new SimpleDateFormat("h:mm");

	public void setTime(Long time) {
		this.time = time;
	}
	
	public Long getTime() {
		return time;
	}

	public Boolean getFlag() {
		return flag;
	}

	public TimeManager(long a) {
		// cmg.connect();
		// time = cmg.timeReceive();
		start = new Date();
		time = a;
	}

	public String timeShow() {
		return ret;
	}

	public void counter() {
			now = new Date();
			time -= ((now.getTime() - start.getTime()) / 1000);
			start = now;
			if (time == 0) {
				flag = true;
			}
			Long h = time/360;
			Long m = (time/60)+1;
			ret = h+" : "+m;
			System.out.println("남은 시간 = " + ret);
					
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(time);
	}

//	public static void main(String[] args) {
//		TimeManager tmg = new TimeManager();
//		System.out.println("");
//		if (tmg.getFlag()) {
//			System.out.println("사용시간이 다 되었습니다.");
//		}
//	}
}
