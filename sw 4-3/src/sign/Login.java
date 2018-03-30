package sign;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Client.ClientManager;
import header.Header;

public class Login extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JButton order = new JButton("상품 주문");
	private JButton message = new JButton("메세지");
	private JButton exit = new JButton("종료");

	private JLabel startTime = new JLabel("시작 시간",JLabel.CENTER);
	private JLabel startTimeOut = new JLabel("시작 시간",JLabel.CENTER);
	private JLabel restTime = new JLabel("남은 시간",JLabel.CENTER);
	private JLabel restTimeOut = new JLabel("",JLabel.CENTER);
	
	ClientManager cmg = new ClientManager();
	
	Date date = new Date();
	SimpleDateFormat now = new SimpleDateFormat("h시 mm분");
	SimpleDateFormat cal = new SimpleDateFormat("HH-mm-ss");
	
	String nowTime = cal.format(date);
	String timeArr[] = nowTime.split("-");
	int timer = (21-Integer.parseInt(timeArr[0]))*3600
			+(59-Integer.parseInt(timeArr[1]))*60
			+(59-Integer.parseInt(timeArr[2]));
	
	public static int timeSet;
	
	public Login(String pcNum, String id, int time, int age){	
		timeSet = time;
		this.display();
		this.event(pcNum, id, age);

		this.setTitle(pcNum+"번 자리");
		this.setSize(500,400);
		this.setLocation(1000, 50);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(5,2));
		mainPanel.add(order);
		mainPanel.add(message);
		mainPanel.add(startTime);
		mainPanel.add(startTimeOut);
		mainPanel.add(restTime);
		mainPanel.add(restTimeOut);
		mainPanel.add(exit);
		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		startTime.setBorder(line);
		startTimeOut.setBorder(line);
		restTime.setBorder(line);
		restTimeOut.setBorder(line);
	}

	public static void plus(int plus) {
		timeSet += plus;
	}
	
	private void event(String pcNum, String id, int age) {
		Thread t = new Thread() { // 시간출력 스레드
			public void run() {
				while(timeSet>0){
					int hour = timeSet/60;
					int min = timeSet%60;
					restTimeOut.setText(hour+"시간 "+min+"분");
					timeSet--;
					if(timeSet==300)
						System.out.println("선불 이용 시간이 5분 남았습니다.");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
				}
				cmg.connect();
				cmg.headerSend(Header.SAVE);
				cmg.send(id);
				Wait wait = new Wait(pcNum);
				dispose();
			}
		};
		t.setDaemon(true);
		t.start();
		
		Thread student = new Thread() {
			public void run() {
				while(timer>0) {
					timer--;
					System.out.println(timer);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
				}
				cmg.connect();
				cmg.headerSend(Header.SAVE);
				cmg.send(id);
				Wait wait = new Wait(pcNum);
				dispose();
			}
		};
		if(age<20) {
			student.setDaemon(true);
			student.start();
		}
		
		order.addActionListener(e->{
			
		});
		
		exit.addActionListener(e->{ //종료
			cmg.connect();
			cmg.headerSend(Header.SAVE);
			cmg.send(id);
			t.stop();
			if(age<20)
				student.stop();
			Wait wait = new Wait(pcNum);
			dispose();
		});
		
		message.addActionListener(e->{ // 메세지 보내기
			cmg.connect();
			String str = JOptionPane.showInputDialog(mainPanel,"입력","메세지 보내기",JOptionPane.PLAIN_MESSAGE);
			cmg.headerSend(Header.MESSAGE);
			cmg.send(pcNum);
			cmg.send(str);
		});
		
		startTimeOut.setText(now.format(date)); // 시작시간
	}
}