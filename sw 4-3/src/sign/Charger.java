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
	
	private JButton order = new JButton("1시간 추가");

	ClientManager cmg = new ClientManager();

	public Charger(){	
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("충전기");
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
			String id = JOptionPane.showInputDialog(mainPanel,"아이디를 입력하세요","1시간 충전",JOptionPane.PLAIN_MESSAGE);
			cmg.connect();
			cmg.headerSend(Header.CHARGE);
			cmg.send(id);
			if(cmg.receive())
				JOptionPane.showMessageDialog(mainPanel, "충전이 완료되었습니다.");
			else
				JOptionPane.showMessageDialog(mainPanel, "존재하지 않는 아이디입니다.");
		});
	}

	private void menu() {
		
	}
}