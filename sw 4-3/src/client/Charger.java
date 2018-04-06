package client;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import header.Header;

public class Charger extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JButton order1000 = new JButton("<html>1 시간<br><br>1,000 원</html>");
	private JButton order2500 = new JButton("<html>3 시간<br><br>2,500 원</html>");
	private JButton order4000 = new JButton("<html>5 시간<br><br>4,000 원</html>");
	private JButton order7000 = new JButton("<html>10 시간<br><br>7,000 원</html>");

	ClientManager cmg = new ClientManager();

	public Charger(){	
		this.display();
		this.event();
		
		this.setTitle("충전기");
		this.setSize(500,400);
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setVisible(true);
//		this.setAlwaysOnTop(true);//항상 위에
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(2,2));
		mainPanel.add(order1000);
		mainPanel.add(order2500);
		mainPanel.add(order4000);
		mainPanel.add(order7000);
		order1000.setFont(new Font("",Font.BOLD,20));
		order2500.setFont(new Font("",Font.BOLD,20));
		order4000.setFont(new Font("",Font.BOLD,20));
		order7000.setFont(new Font("",Font.BOLD,20));
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
		if(cmg.receive()) {
			cmg.intSend(money);
			JOptionPane.showMessageDialog(mainPanel, "충전이 완료되었습니다.");
		}
		else
			JOptionPane.showMessageDialog(mainPanel, "존재하지 않는 아이디입니다.");
	}

	public static void main(String[] args) throws IOException {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Charger charger = new Charger();
	}
}