package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import header.Header;

public class Wait extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private Panel p = new Panel();
	private JLabel p2 = new JLabel();
	
	private JLabel id = new JLabel("아이디",JLabel.RIGHT);
	private JLabel pw = new JLabel("비밀번호",JLabel.RIGHT);
	private JLabel pcNum = new JLabel("",JLabel.CENTER);
	private JLabel bar = new JLabel("KG PC방 사용자 로그인",JLabel.CENTER);
	private JLabel notice = new JLabel("KG PC방 입니다.    감사합니다",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	
	private JButton rogin = new JButton("로그인");
	private JButton signUp = new JButton("회원가입");
	private JButton charger = new JButton("충전기");
	private JButton exit = new JButton("종료");
	
	private JLabel display = new JLabel();
	private ImageIcon i1 = new ImageIcon("image/1600x900.jpg");
	
	ClientManager cmg = new ClientManager();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	
	public Wait(String num){
		this.display();
		this.event(num);
		
		this.setTitle(num+"번 자리 대기 화면");
		this.setSize((int)width,(int)height);
//		this.setLocation(1000, 250);
		this.setLocation(0, 0);
		this.setUndecorated(true);//상단바 없애기
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//항상 위에
	}
	
	private void display() {
		this.setContentPane(mainPanel);
//		mainPanel.setLayout(new BorderLayout());
//		mainPanel.add(p, BorderLayout.EAST);
		mainPanel.setLayout(null);
		mainPanel.add(p);
		mainPanel.add(display);
		display.setIcon(i1);
		display.setBounds(0, 0, (int)width, (int)height);
		p.setBounds((int)width*6/10, (int)height*5/20, (int)width*5/20, (int)height*5/10+50);
		
//		p.setLayout(new GridLayout(5,2));
//		p.add(pcNum);
//		p.add(pcNumInput);
//		p.add(id);
//		p.add(idInput);
//		p.add(pw);
//		p.add(pwInput);
//		p.add(rogin);
//		p.add(signUp);
//		p.add(charger);
//		p.add(exit);
		p.setLayout(new BorderLayout());
		p.add(bar,BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p2.setLayout(null);
		p2.add(pcNum).setBounds(140,20,120,70);
		p2.add(id).setBounds(20,110,100,30);
		p2.add(idInput).setBounds(130,110,150,30);
		p2.add(pw).setBounds(20,150,100,30);
		p2.add(pwInput).setBounds(130,150,150,30);
		p2.add(rogin).setBounds(300,115,80,60);
		p2.add(signUp).setBounds(220,200,90,30);
		p2.add(notice).setBounds(50,240,300,150);
		p2.add(exit).setBounds(300,400,80,50);
		pcNum.setFont(new Font("",Font.BOLD,50));
		
		Border line = BorderFactory.createLineBorder(Color.BLACK,3);
		Border title = BorderFactory.createTitledBorder(line,"공지사항");
		notice.setBorder(title);
		Border title2 = BorderFactory.createTitledBorder(line,"-");
		p2.setBorder(title2);
		
		Border line2 = BorderFactory.createLineBorder(Color.BLACK,1);
		idInput.setBorder(line2);
		pwInput.setBorder(line2);
	}

	private void event(String num) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.ALT_DOWN_MASK);
		Action altF4Action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(mainPanel, "알트f4 누르지마");
			}
		};
		mainPanel.getInputMap().put(altF4, "f4");
		mainPanel.getActionMap().put("f4", altF4Action);
		rogin.getInputMap().put(altF4, "f4");
		rogin.getActionMap().put("f4", altF4Action);
		signUp.getInputMap().put(altF4, "f4");
		signUp.getActionMap().put("f4", altF4Action);
		charger.getInputMap().put(altF4, "f4");
		charger.getActionMap().put("f4", altF4Action);
		idInput.getInputMap().put(altF4, "f4");
		idInput.getActionMap().put("f4", altF4Action);
		pwInput.getInputMap().put(altF4, "f4");
		pwInput.getActionMap().put("f4", altF4Action);
		
		exit.addActionListener(e->{
			System.exit(0);
		});
		
		pcNum.setText(num);
		
		signUp.addActionListener(e->{
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
				int age = year + 1 - (Integer.parseInt(cmg.strReceive())/10000);//2018-1993=25
				if(age<20 && (timer>=22 || timer<=8)) {
					JOptionPane.showMessageDialog(mainPanel, "미성년자 이용 불가 시간입니다");
					cmg.send("-1");
				}
				else {
					cmg.send(num);
					int time = cmg.plusReceive();
					if(time<60)
						JOptionPane.showMessageDialog(mainPanel, "시간을 충전하고 오세요");
					else {
						Login login = new Login(num,idInput.getText(),time,age);					
						dispose();
					}
				}
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "틀림.");
		});
	}
}