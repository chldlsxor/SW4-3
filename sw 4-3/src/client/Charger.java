package client;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import header.Header;

public class Charger extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JButton order1000 = new JButton("1�ð� �߰�");
	private JButton order2500 = new JButton("3�ð� �߰�");
	private JButton order4000 = new JButton("5�ð� �߰�");
	private JButton order7000 = new JButton("10�ð� �߰�");

	ClientManager cmg = new ClientManager();

	public Charger(){	
		this.display();
		this.event();
		
		this.setTitle("������");
		this.setSize(500,400);
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//�׻� ����
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(2,2));
		mainPanel.add(order1000);
		mainPanel.add(order2500);
		mainPanel.add(order4000);
		mainPanel.add(order7000);
	}

	private void event() {
		order1000.addActionListener(e->{
			charge(Header.PC1HOUR);
		});
		order2500.addActionListener(e->{
			charge(Header.PC3HOUR);
		});
		order4000.addActionListener(e->{
			charge(Header.PC5HOUR);
		});
		order7000.addActionListener(e->{
			charge(Header.PC10HOUR);
		});
	}
	
	private void charge(int money) {
		String id = JOptionPane.showInputDialog(mainPanel,"","���̵� �Է��ϼ���",JOptionPane.PLAIN_MESSAGE);
		cmg.connect();
		cmg.headerSend(Header.CHARGE);
		cmg.send(id);
		cmg.intSend(money);
		if(cmg.receive()) {
			JOptionPane.showMessageDialog(mainPanel, "������ �Ϸ�Ǿ����ϴ�.");
		}
		else
			JOptionPane.showMessageDialog(mainPanel, "�������� �ʴ� ���̵��Դϴ�.");
	}

}