package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JButton move = new JButton("�ڸ� �̵�");
	private JButton order = new JButton("��ǰ �ֹ�");
	private JButton message = new JButton("�޼���");
	private JButton info = new JButton("ȸ�� ����");
	private JButton exit = new JButton("����");

	private JLabel fee = new JLabel("���",JLabel.CENTER);
	private JLabel time = new JLabel("���� �ð�",JLabel.CENTER);
	private JLabel startTime = new JLabel("���� �ð�",JLabel.CENTER);

//	ClientManager cmg = new ClientManager();
	
	public Login(){	
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("���� ȭ��");
		this.setSize(500,400);
		this.setLocation(1000, 50);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(4,2));

		mainPanel.add(move);
		mainPanel.add(order);
		mainPanel.add(message);
		mainPanel.add(info);
		mainPanel.add(exit);
		mainPanel.add(fee);
		mainPanel.add(time);
		mainPanel.add(startTime);
	}

	private void event() {
		exit.addActionListener(e->{
			Wait wait = new Wait();
			dispose();
		});
		
		message.addActionListener(e->{
			
		});
//		signUp.addActionListener(e->{
//			//ȸ������ â ����
//			Sign sign = new Sign();
//		});
//		
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
	}
	
	private void menu() {
		
	}
}