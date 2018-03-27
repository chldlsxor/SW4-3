package sign;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.ClientManager;
import header.Header;

public class Login extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JButton order = new JButton("상품 주문");
	private JButton message = new JButton("메세지");
	private JButton exit = new JButton("종료");

	private JLabel startTime = new JLabel("시작 시간",JLabel.CENTER);
	private JLabel startTimeOut = new JLabel("시작 시간",JLabel.CENTER);
	private JLabel restTime = new JLabel("남은 시간",JLabel.CENTER);
	private JLabel restTimeOut = new JLabel("",JLabel.CENTER);
	
	private String seatNum;
	private String aaa;
	ClientManager cmg = new ClientManager();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("h시 mm분");
	
	private int a = 0;
	
	public Login(String num){	
		seatNum = num;
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
	}

	
	
	private void event() {
		Thread t = new Thread() { // 받는 스레드
			public void run() {
				while(true) {
					cmg.connect();
					char header = cmg.headerReceive();
//					System.out.println(header);
					if(header == Header.PLUS)
						a+=cmg.plusReceive();
					if(header == Header.MESSAGE) {
						aaa = cmg.strReceive();
						System.out.println(aaa);
						JOptionPane.showMessageDialog(mainPanel, aaa);
						cmg.aaa();
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
		
		
		
		exit.addActionListener(e->{ //종료
			cmg.headerSend(Header.SAVE);
			cmg.send(seatNum);
			Wait wait = new Wait(seatNum);
			dispose();
		});
		
		message.addActionListener(e->{ // 메세지 보내기
			String str = JOptionPane.showInputDialog(mainPanel,"입력","메세지 보내기",JOptionPane.PLAIN_MESSAGE);
			cmg.headerSend(Header.MESSAGE);
			cmg.send(seatNum);
			cmg.send(str);
		});
		
		startTimeOut.setText(date.format(today)); // 시작시간
		
		
		
		
		Thread t2 = new Thread() { // 시간출력 스레드
			public void run() {
				while(a>0){
					int hour = a/60;
					int min = a%60;
					restTimeOut.setText(hour+"시간 "+min+"분");
					a--;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
				}
				Wait wait = new Wait(seatNum);
				dispose();
			}
		};
		t2.setDaemon(true);
		t2.start();
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