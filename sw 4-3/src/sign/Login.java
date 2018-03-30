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
	
	private String seatNum;
	private String id;
	private int adult;
	ClientManager cmg = new ClientManager();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("h시 mm분");
	SimpleDateFormat exit22 = new SimpleDateFormat("HH-mm-ss");
	
	String a = exit22.format(today);
	String timeArr[] = a.split("-");
	int timer = (21-Integer.parseInt(timeArr[0]))*3600
			+(59-Integer.parseInt(timeArr[1]))*60
			+(59-Integer.parseInt(timeArr[2]));
	
	public static int timeSet;

//	ClientManager2 cm = new ClientManager2();
	
	public Login(String num, String idInput, int time, int age){	
		seatNum = num;
		id = idInput;
		timeSet = time;
		adult = age;
		this.display();
		this.event();
		this.menu();

		this.setTitle(num+"번 자리");
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
	
	private void event() {
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
				cmg.headerSend(Header.SAVE);
				cmg.send(id);
				Wait wait = new Wait(seatNum);
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
				cmg.headerSend(Header.SAVE);
				cmg.send(id);
				Wait wait = new Wait(seatNum);
				dispose();
			}
		};
		if(adult<20) {
			student.setDaemon(true);
			student.start();
		}
		
		exit.addActionListener(e->{ //종료
			cmg.connect();
			cmg.headerSend(Header.SAVE);
			cmg.send(id);
//			cmg.exit();
			t.stop();
			Wait wait = new Wait(seatNum);
			dispose();
		});
		
		message.addActionListener(e->{ // 메세지 보내기
			cmg.connect();//나중에 다시..
			String str = JOptionPane.showInputDialog(mainPanel,"입력","메세지 보내기",JOptionPane.PLAIN_MESSAGE);
			cmg.headerSend(Header.MESSAGE);
			cmg.send(seatNum);
			cmg.send(str);
		});
		
		startTimeOut.setText(date.format(today)); // 시작시간
	}

//		rogin.addActionListener(e->{
//			//아이디 비번 보내고
//			cmg.connect();
//			cmg.headerSend(Header.LOGIN);
//			System.out.println(idInput.getText());
//			System.out.println(pwInput.getText());
//			cmg.send(idInput.getText());
//			cmg.send(pwInput.getText());
//			//로그인 눌렀을때 정보가 맞으면 종료, 틀리면 메시지 출력
//			if(cmg.receive()) {
//				JOptionPane.showMessageDialog(mainPanel, "로그인 됨.");
//				dispose();
//			}
//			else
//				JOptionPane.showMessageDialog(mainPanel, "틀림.");
//		});
	private void menu() {
		
	}
}