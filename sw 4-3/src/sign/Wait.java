package sign;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class Wait extends JFrame{

	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JLabel lb1 = new JLabel("아이디",JLabel.CENTER);
	private JLabel lb2 = new JLabel("비밀번호",JLabel.CENTER);
	private JLabel lb3 = new JLabel("카드번호",JLabel.CENTER);
	
	private JTextArea jta1 = new JTextArea();
	private JTextArea jta2 = new JTextArea();
	private JTextArea jta3 = new JTextArea();
	
	private JButton rogin = new JButton("로그인");
	private JButton signUp = new JButton("회원가입");
	
	private ImageIcon img = new ImageIcon();
	
	private String str;
//	ClientManager cmg = new ClientManager();
	
	public Wait(){
		this.display();
		this.event();
		this.menu();
		
//		cmg.connect();
		this.setTitle("대기 화면");
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
		
//		mainPanel.add(lb1);
//		mainPanel.add(jta1);
//		mainPanel.add(lb2);
//		mainPanel.add(jta2);
//		mainPanel.add(lb3);
//		mainPanel.add(jta3);
//		mainPanel.add(rogin);
//		mainPanel.add(signUp);
		
		p.add(lb1);
		p.add(jta1);
		p.add(lb2);
		p.add(jta2);
		p.add(lb3);
		p.add(jta3);
		p.add(rogin);
		p.add(signUp);
	}

	private void event() {
		
		signUp.addActionListener(e->{
			//회원가입 창 띄우기
			Sign sign = new Sign();
		});
		
		rogin.addActionListener(e->{
			//아이디 비번 보내고
			System.out.println(jta1.getText());
			System.out.println(jta2.getText());
//			cmg.send();
			//로그인 눌렀을때 정보가 맞으면 종료, 틀리면 메시지 출력
			dispose();
		});
	}
	
	private void menu() {
		
	}
}