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
	
	private String seatNum;
	private String id;
	private int adult;
	ClientManager cmg = new ClientManager();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("h�� mm��");
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

		this.setTitle(num+"�� �ڸ�");
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
		
		exit.addActionListener(e->{ //����
			cmg.connect();
			cmg.headerSend(Header.SAVE);
			cmg.send(id);
//			cmg.exit();
			t.stop();
			Wait wait = new Wait(seatNum);
			dispose();
		});
		
		message.addActionListener(e->{ // �޼��� ������
			cmg.connect();//���߿� �ٽ�..
			String str = JOptionPane.showInputDialog(mainPanel,"�Է�","�޼��� ������",JOptionPane.PLAIN_MESSAGE);
			cmg.headerSend(Header.MESSAGE);
			cmg.send(seatNum);
			cmg.send(str);
		});
		
		startTimeOut.setText(date.format(today)); // ���۽ð�
	}

//		rogin.addActionListener(e->{
//			//���̵� ��� ������
//			cmg.connect();
//			cmg.headerSend(Header.LOGIN);
//			System.out.println(idInput.getText());
//			System.out.println(pwInput.getText());
//			cmg.send(idInput.getText());
//			cmg.send(pwInput.getText());
//			//�α��� �������� ������ ������ ����, Ʋ���� �޽��� ���
//			if(cmg.receive()) {
//				JOptionPane.showMessageDialog(mainPanel, "�α��� ��.");
//				dispose();
//			}
//			else
//				JOptionPane.showMessageDialog(mainPanel, "Ʋ��.");
//		});
	private void menu() {
		
	}
}