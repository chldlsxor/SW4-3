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
	
	private JButton order = new JButton("��ǰ �ֹ�");
	private JButton message = new JButton("�޼���");
	private JButton exit = new JButton("����");

	private JLabel startTime = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel startTimeOut = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel restTime = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel restTimeOut = new JLabel("",JLabel.CENTER);
	
	private String seatNum;
	private String aaa;
	ClientManager cmg = new ClientManager();
	
	Date today = new Date();
	SimpleDateFormat date = new SimpleDateFormat("h�� mm��");
	
	private int a = 0;
	
	public Login(String num){	
		seatNum = num;
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
	}

	
	
	private void event() {
		Thread t = new Thread() { // �޴� ������
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
		
		
		
		exit.addActionListener(e->{ //����
			cmg.headerSend(Header.SAVE);
			cmg.send(seatNum);
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
		
		
		
		
		Thread t2 = new Thread() { // �ð���� ������
			public void run() {
				while(a>0){
					int hour = a/60;
					int min = a%60;
					restTimeOut.setText(hour+"�ð� "+min+"��");
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