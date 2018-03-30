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
import javax.swing.JTextArea;
import javax.swing.border.Border;

import Client.ClientManager;
import header.Header;

public class Wait extends JFrame{

	private JPanel mainPanel = new JPanel();
	
	private JLabel id = new JLabel("아이디",JLabel.CENTER);
	private JLabel pw = new JLabel("비밀번호",JLabel.CENTER);
	private JLabel card = new JLabel("카드번호",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea cardInput = new JTextArea();
	
	private JButton rogin = new JButton("로그인");
	private JButton signUp = new JButton("회원가입");
	private JButton charger = new JButton("충전기");

	private String pcNum;
	
	ClientManager cmg = new ClientManager();
	
	public Wait(String num){
		pcNum = num;
		this.display();
		this.event();
		
		this.setTitle(num+"번 자리 대기 화면");
		this.setSize(500,400);
		this.setLocation(1000, 250);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(5,2));

		mainPanel.add(id);
		mainPanel.add(idInput);
		mainPanel.add(pw);
		mainPanel.add(pwInput);
		mainPanel.add(card);
		mainPanel.add(cardInput);
		mainPanel.add(rogin);
		mainPanel.add(signUp);
		mainPanel.add(charger);

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
		charger.addActionListener(e->{
			Charger charger = new Charger();
		});
		
		rogin.addActionListener(e->{
			Date today = new Date();
			SimpleDateFormat exit22 = new SimpleDateFormat("HH");
			SimpleDateFormat adult = new SimpleDateFormat("yyyy");
			int timer = Integer.parseInt(exit22.format(today));//16시
			int year = Integer.parseInt(adult.format(today));//2018년
			//아이디 비번 보내고
			cmg.connect();
			cmg.headerSend(Header.LOGIN);
			cmg.send(idInput.getText());
			cmg.send(pwInput.getText());
			//로그인 눌렀을때 정보가 맞으면 종료, 틀리면 메시지 출력
			if(cmg.receive()) {
				//나이를 받아서 시간체크
				String birth = cmg.strReceive();
				int age = year + 1 - Integer.parseInt(adult.format(birth));//2018-1993=25
				System.out.println(age);
				if(age<20 && (timer>=17 || timer<=8)) {
					JOptionPane.showMessageDialog(mainPanel, "미성년자 이용 불가 시간입니다");
					cmg.send("-1");
				}
				else {
					cmg.send(pcNum);
					int time = cmg.plusReceive();
					if(time<60)
						JOptionPane.showMessageDialog(mainPanel, "시간을 충전하고 오세요");
					else {
//						cmg.exit();
						Login login = new Login(pcNum,idInput.getText(),time,age);					
						dispose();
					}
				}
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "틀림.");
		});
	}
}