package sign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import Client.ClientManager;
import header.Header;

public class Wait extends JFrame{

	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JLabel id = new JLabel("아이디",JLabel.CENTER);
	private JLabel pw = new JLabel("비밀번호",JLabel.CENTER);
	private JLabel card = new JLabel("카드번호",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea cardInput = new JTextArea();
	
	private JButton rogin = new JButton("로그인");
	private JButton signUp = new JButton("회원가입");
	private JButton aaa = new JButton("충전기");

	private String seatNum;
	
	ClientManager cmg = new ClientManager();
	
	public Wait(String num){
		seatNum = num;
		this.display();
		this.event();
		
		this.setTitle(num+"번 자리 대기 화면");
		this.setSize(500,400);
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		p.setLayout(new GridLayout(5,2));
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
		p.add(aaa);
		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		id.setBorder(line);
		idInput.setBorder(line);
		pw.setBorder(line);
		pwInput.setBorder(line);
		card.setBorder(line);
		cardInput.setBorder(line);
	}

	private void event() {
		
		signUp.addActionListener(e->{
			//회원가입 창 띄우기
			Sign sign = new Sign();
		});
		aaa.addActionListener(e->{
			Charger charger = new Charger();
		});
		
		rogin.addActionListener(e->{
			//아이디 비번 보내고
			System.out.println("1");
			cmg.connect();
			System.out.println("2");
			cmg.headerSend(Header.LOGIN);
			System.out.println("3");
			cmg.send(idInput.getText());
			cmg.send(pwInput.getText());
			System.out.println("4");
			//로그인 눌렀을때 정보가 맞으면 종료, 틀리면 메시지 출력
			if(cmg.receive()) {
				cmg.send(seatNum);
				int time = cmg.plusReceive();
				if(time==0)
					JOptionPane.showMessageDialog(mainPanel, "시간을 충전하고 오세요");
				else {
//					cmg.exit();
					Login login = new Login(seatNum,idInput.getText(),time);					
					dispose();
				}
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "틀림.");
		});
	}
}