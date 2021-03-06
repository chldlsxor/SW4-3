package client;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import db.Member;
import header.Header;

public class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel name = new JLabel("이름");
	private JLabel id = new JLabel("아이디(3~14)");
	private JLabel pw = new JLabel("비밀번호(6~20)");
	private JLabel pwCheck = new JLabel("비밀번호 확인");
	private JLabel birth = new JLabel("생년월일(8자리)");

	private JTextArea nameInput = new JTextArea();
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea pwCheckInput = new JTextArea();
	private JTextArea birthInput = new JTextArea();
	
	private JButton ok = new JButton("확인");
	private JButton no = new JButton("취소");
	private JButton overlap = new JButton("중복 확인");
	
	ClientManager cmg = new ClientManager();
	
	boolean overlapCheck = false;
	
	//정규표현식
	private String idRgx = "^[0-9a-zA-Z]{3,14}$$";
	private String pwRgx = "^[0-9a-zA-z]{6,20}$";
	private String birthRgx = "^[0-9]{8}$";
	private String nameRgx = "^[가-힣]{2,15}$";
	
	
	
	public Sign(){
		cmg.connect();
		cmg.headerSend(Header.SIGNUP);
		this.display();
		this.event();
		
		this.setTitle("회원가입");
		this.setSize(420, 370);
		this.setLocation(500, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//항상 위에
	}
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.add(name).setBounds(10,0,120,40);
		mainPanel.add(nameInput).setBounds(130,10,150,40);
		mainPanel.add(id).setBounds(10,50,120,40);
		mainPanel.add(idInput).setBounds(130,60,150,40);
		mainPanel.add(pw).setBounds(10,100,120,40);
		mainPanel.add(pwInput).setBounds(130,110,150,40);
		mainPanel.add(pwCheck).setBounds(10,150,120,40);
		mainPanel.add(pwCheckInput).setBounds(130,160,150,40);
		mainPanel.add(birth).setBounds(10,200,120,40);
		mainPanel.add(birthInput).setBounds(130,210,150,40);
		mainPanel.add(overlap).setBounds(300,65,90,30);
		mainPanel.add(ok).setBounds(100,280,90,30);
		mainPanel.add(no).setBounds(200,280,90,30);
		
		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		nameInput.setBorder(line);
		idInput.setBorder(line);
		pwInput.setBorder(line);
		pwCheckInput.setBorder(line);
		birthInput.setBorder(line);
	}

	private void event() {
		overlap.addActionListener(e->{
			//중복확인
			//회원db에 같은 아이디가 등록되어있는지 확인후 메시지 출력
			String str=idInput.getText();
			if(Pattern.matches(idRgx, str)) {
				cmg.send(str);//아이디 보내고
				overlapCheck = cmg.receive();//널값 받고
				if(overlapCheck) {
					JOptionPane.showMessageDialog(mainPanel, "사용 가능한 아이디입니다.");
					overlap.setEnabled(false);
				}
				else
					JOptionPane.showMessageDialog(mainPanel, "이미 존재하는 아이디입니다.");
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "아이디는 3~14글자만 가능합니다");
		});
		
		ok.addActionListener(e->{
			//ok버튼 누르면 나머지 정보를 서버에 다 보냄
			//비밀번호 = 비밀번호 확인 검사
			//회원가입이 완료됬는지 널값 받아서 확인창 띄워줌
			if(!Pattern.matches(nameRgx, nameInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "이름을 입력하세요");
			else if(!overlapCheck)
				JOptionPane.showMessageDialog(mainPanel, "아이디 중복확인을 해주세요");
			else if(!Pattern.matches(pwRgx, pwInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "비밀번호는 6~20글자만 가능합니다");
			else if(!pwInput.getText().equals(pwCheckInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "비밀번호를 다시 확인해주세요");
			else if(!Pattern.matches(birthRgx, birthInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "생년월일이 잘못 입력됐습니다");
			else {
				Member m = new Member(pwInput.getText(),nameInput.getText(),birthInput.getText());
				cmg.memberSend(m);
				dispose();
			}
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}