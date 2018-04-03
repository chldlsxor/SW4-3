package client;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import header.Header;

public class Charger extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JButton order1000 = new JButton("1시간 추가");
	private JButton order2500 = new JButton("3시간 추가");
	private JButton order4000 = new JButton("5시간 추가");
	private JButton order7000 = new JButton("10시간 추가");

	ClientManager cmg = new ClientManager();

	public Charger(){	
		this.display();
		this.event();
		
		this.setTitle("충전기");
		this.setSize(500,400);
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//항상 위에
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
		String id = JOptionPane.showInputDialog(mainPanel,"","아이디를 입력하세요",JOptionPane.PLAIN_MESSAGE);
		cmg.connect();
		cmg.headerSend(Header.CHARGE);
		cmg.send(id);
		cmg.intSend(money);
		if(cmg.receive()) {
			JOptionPane.showMessageDialog(mainPanel, "충전이 완료되었습니다.");
		}
		else
			JOptionPane.showMessageDialog(mainPanel, "존재하지 않는 아이디입니다.");
	}

}