package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

<<<<<<< HEAD
=======
import Client.ClientManager;
>>>>>>> refs/remotes/origin/chldlsxor
import db.Member;

class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel name = new JLabel("이름",JLabel.CENTER);
	private JLabel id = new JLabel("아이디",JLabel.CENTER);
	private JLabel pw = new JLabel("비밀번호",JLabel.CENTER);
	private JLabel pwCheck = new JLabel("비밀번호 확인",JLabel.CENTER);
	private JLabel birth = new JLabel("주민등록번호 앞자리",JLabel.CENTER);
	
	private JTextArea nameInput = new JTextArea();
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea pwCheckInput = new JTextArea();
	private JTextArea birthInput = new JTextArea();
	
	private JButton ok = new JButton("확인");
	private JButton no = new JButton("취소");
	private JButton overlap = new JButton("중복 확인");
	
	ClientManager cmg = new ClientManager();
	
	public Sign(){
		cmg.connect();
		
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
//		name.setBounds(100,100,100,100);
//		nameInput.setBounds(1000, 1000, 1000, 1000);
		mainPanel.add(name);
		mainPanel.add(nameInput);
		mainPanel.add(id);
		mainPanel.add(idInput);
		mainPanel.add(pw);
		mainPanel.add(pwInput);
		mainPanel.add(pwCheck);
		mainPanel.add(pwCheckInput);
		mainPanel.add(birth);
		mainPanel.add(birthInput);
		mainPanel.add(ok);
		mainPanel.add(no);
		mainPanel.add(overlap);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//창 종료

		overlap.addActionListener(e->{
			//중복확인
			//회원db에 같은 아이디가 등록되어있는지 확인후 메시지 출력
			String str=idInput.getText();
			cmg.send(str);//아이디 보내고
			if(cmg.receive())// 널값 받아서
				JOptionPane.showMessageDialog(mainPanel, "사용 가능한 아이디입니다.");
			else
				JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디입니다.");
		});
		
		ok.addActionListener(e->{
			//ok버튼 누르면 나머지 정보를 서버에 다 보냄
			//비밀번호 = 비밀번호 확인 검사
			Member m = new Member(pwInput.getText(),nameInput.getText(),birthInput.getText());
			if(pwInput.getText().equals(pwCheck.getText())) {
				cmg.memberSend(m);
				dispose();
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "비밀번호,비밀번호 확인 다름");
			//회원가입이 완료됬는지 널값 받아서 확인창 띄워줌
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}