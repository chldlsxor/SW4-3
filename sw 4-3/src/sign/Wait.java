package sign;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Client.ClientManager;
import header.Header;

public class Wait extends JFrame{

	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JLabel id = new JLabel("���̵�",JLabel.CENTER);
	private JLabel pw = new JLabel("��й�ȣ",JLabel.CENTER);
	private JLabel card = new JLabel("ī���ȣ",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea cardInput = new JTextArea();
	
	private JButton rogin = new JButton("�α���");
	private JButton signUp = new JButton("ȸ������");

	ClientManager cmg = new ClientManager();
	
	public Wait(){
		this.display();
		this.event();
		
		this.setTitle("��� ȭ��");
		this.setSize(500,400);
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		p.setLayout(new GridLayout(4,2));
		mainPanel.add(p,BorderLayout.EAST);
		
//		mainPanel.add(id);
//		mainPanel.add(idInput);
//		mainPanel.add(pw);
//		mainPanel.add(pwInput);
//		mainPanel.add(card);
//		mainPanel.add(cardInput);
//		mainPanel.add(rogin);
//		mainPanel.add(signUp);
		
		p.add(id);
		p.add(idInput);
		p.add(pw);
		p.add(pwInput);
		p.add(card);
		p.add(cardInput);
		p.add(rogin);
		p.add(signUp);
	}

	private void event() {
		
		signUp.addActionListener(e->{
			//ȸ������ â ����
			Sign sign = new Sign();
		});
		
		rogin.addActionListener(e->{
			//���̵� ��� ������
			cmg.connect();
			cmg.headerSend(Header.LOGIN);
			cmg.send(idInput.getText());
			cmg.send(pwInput.getText());
			//�α��� �������� ������ ������ ����, Ʋ���� �޽��� ���
			if(cmg.receive()) {
				Login login = new Login();
				dispose();
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "Ʋ��.");
		});
	}
}