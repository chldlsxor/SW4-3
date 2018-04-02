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
	
	private JButton order = new JButton("��ǰ �ֹ�");
	private JButton message = new JButton("�޼���");
	private JButton exit = new JButton("����");

	private JLabel startTime = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel startTimeOut = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel restTime = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel restTimeOut = new JLabel("",JLabel.CENTER);
	
	ClientManager cmg = new ClientManager();
	
	Date date = new Date();
	SimpleDateFormat now = new SimpleDateFormat("h�� mm��");
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

		this.setTitle(pcNum+"�� �ڸ�");
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
		Thread t = new Thread() { // �ð���� ������
			public void run() {
				while(timeSet>0){
					int hour = timeSet/60;
					int min = timeSet%60;
					restTimeOut.setText(hour+"�ð� "+min+"��");
					timeSet--;
					if(timeSet==300)
						System.out.println("���� �̿� �ð��� 5�� ���ҽ��ϴ�.");
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
		
		exit.addActionListener(e->{ //����
			cmg.connect();
			cmg.headerSend(Header.SAVE);
			cmg.send(id);
			t.stop();
			if(age<20)
				student.stop();
			Wait wait = new Wait(pcNum);
			dispose();
		});
		
		message.addActionListener(e->{ // �޼��� ������
			cmg.connect();
			String str = JOptionPane.showInputDialog(mainPanel,"�Է�","�޼��� ������",JOptionPane.PLAIN_MESSAGE);
			cmg.headerSend(Header.MESSAGE);
			cmg.send(pcNum);
			cmg.send(str);
		});
		
		startTimeOut.setText(now.format(date)); // ���۽ð�
	}
}