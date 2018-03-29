package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Client.ClientManager;
import header.Header;

public class Charger extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JButton order = new JButton("1�ð� �߰�");

	ClientManager cmg = new ClientManager();

	public Charger(){	
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("������");
		this.setSize(500,400);
		this.setLocation(1000, 50);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(5,2));
		mainPanel.add(order);
	}

	private void event() {
		order.addActionListener(e->{
			String id = JOptionPane.showInputDialog(mainPanel,"���̵� �Է��ϼ���","1�ð� ����",JOptionPane.PLAIN_MESSAGE);
			cmg.connect();
			cmg.headerSend(Header.CHARGE);
			cmg.send(id);
			if(cmg.receive())
				JOptionPane.showMessageDialog(mainPanel, "������ �Ϸ�Ǿ����ϴ�.");
			else
				JOptionPane.showMessageDialog(mainPanel, "�������� �ʴ� ���̵��Դϴ�.");
		});
	}

	private void menu() {
		
	}
}