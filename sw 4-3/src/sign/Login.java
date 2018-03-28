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
	private String aaa;
	ClientManager cmg = new ClientManager();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("h�� mm��");
	
	private int timeSet;
	
	public Login(String num, String idInput, int time){	
		seatNum = num;
		id = idInput;
		timeSet = time;
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

	private void event() {
		cmg.connect();//���߿� �ٽ�..
		Thread t = new Thread() { // �޴� ������
			public void run() {
				while(true) {
					char header = cmg.headerReceive();
//					char header = 'z';
					System.out.println("�̰�1?"+header);
					if(header == Header.PLUS)
						timeSet+=cmg.plusReceive();
					if(header == Header.MESSAGE) {
						aaa = cmg.strReceive();
						System.out.println(aaa);
						JOptionPane.showMessageDialog(mainPanel, aaa);
						cmg.aaa();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
				}
			}
		};
		t.setDaemon(true);
		t.start();

		Thread t2 = new Thread() { // �ð���� ������
			public void run() {
				while(timeSet>0){
					int hour = timeSet/60;
					int min = timeSet%60;
					restTimeOut.setText(hour+"�ð� "+min+"��");
					timeSet--;
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
		t2.setDaemon(true);
		t2.start();
		
		exit.addActionListener(e->{ //����
			cmg.headerSend(Header.SAVE);
			cmg.send(id);
//			cmg.exit();
			t.stop();
//			t2.stop();
			Wait wait = new Wait(seatNum);
			dispose();
		});
		
		message.addActionListener(e->{ // �޼��� ������
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