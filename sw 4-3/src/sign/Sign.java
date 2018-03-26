package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import db.Member;

class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel lb1 = new JLabel("이름",JLabel.CENTER);
	private JLabel lb2 = new JLabel("아이디",JLabel.CENTER);
	private JLabel lb3 = new JLabel("비밀번호",JLabel.CENTER);
	private JLabel lb4 = new JLabel("비밀번호 확인",JLabel.CENTER);
	private JLabel lb5 = new JLabel("주민등록번호 앞자리",JLabel.CENTER);
	
	private JTextArea jta1 = new JTextArea();
	private JTextArea jta2 = new JTextArea();
	private JTextArea jta3 = new JTextArea();
	private JTextArea jta4 = new JTextArea();
	private JTextArea jta5 = new JTextArea();
	
	private JButton ok = new JButton("확인");
	private JButton no = new JButton("취소");
	private JButton overlap = new JButton("중복 확인");
	
	public Sign(){
		this.display();
		this.event();
		
		this.setTitle("회원가입");
		this.setSize(500, 400);
		
//		this.setLocation(100, 100);
		//위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(true);
		
		//상단 부분이 나오지 않도록 설정
//		this.setUndecorated(true);
		
		this.setResizable(false);
		this.setVisible(true);
	}
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(7,2));
		//모든 컴포넌트의 추가는 mainPanel에 수행	
//		lb1.setBounds(100,100,100,100);
//		jta1.setBounds(1000, 1000, 1000, 1000);
		mainPanel.add(lb1);
		mainPanel.add(jta1);
		mainPanel.add(lb2);
		mainPanel.add(jta2);
		mainPanel.add(lb3);
		mainPanel.add(jta3);
		mainPanel.add(lb4);
		mainPanel.add(jta4);
		mainPanel.add(lb5);
		mainPanel.add(jta5);
		mainPanel.add(ok);
		mainPanel.add(no);
		mainPanel.add(overlap);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//창 종료

		overlap.addActionListener(e->{
			//중복확인
			//회원db에 같은 아이디가 등록되어있는지 확인후 메시지 출력
			String str=jta2.getText();
			//cmg.send(str);	//아이디 보내고
			// 널값 받아서
			//존재하는지 메시지 출력
			System.out.println(str);
			JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디입니다.");
		});
		
		ok.addActionListener(e->{
			//ok버튼 누르면 나머지 정보를 서버에 다 보냄
			//비밀번호 = 비밀번호 확인 검사
			Member m = new Member(jta3.getText(),jta1.getText(),jta5.getText());
			//cmg.send(m);
			dispose();
			//회원가입이 완료됬는지 널값 받아서 확인창 띄워줌
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}