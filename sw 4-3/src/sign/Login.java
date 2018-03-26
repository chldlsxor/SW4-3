package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel p = new JPanel();
	
	private JButton move = new JButton("자리 이동");
	private JButton order = new JButton("상품 주문");
	private JButton message = new JButton("메세지");
	private JButton info = new JButton("회원 정보");
	private JButton exit = new JButton("종료");

	private JLabel fee = new JLabel("요금",JLabel.CENTER);
	private JLabel time = new JLabel("남은 시간",JLabel.CENTER);
	private JLabel startTime = new JLabel("시작 시간",JLabel.CENTER);

//	ClientManager cmg = new ClientManager();
	
	public Login(){	
		this.display();
		this.event();
		this.menu();
		
		this.setTitle("바탕 화면");
		this.setSize(500,400);
		this.setLocation(1000, 50);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(4,2));

		mainPanel.add(move);
		mainPanel.add(order);
		mainPanel.add(message);
		mainPanel.add(info);
		mainPanel.add(exit);
		mainPanel.add(fee);
		mainPanel.add(time);
		mainPanel.add(startTime);
	}

	private void event() {
		exit.addActionListener(e->{
			Wait wait = new Wait();
			dispose();
		});
		
		message.addActionListener(e->{
			
		});
//		signUp.addActionListener(e->{
//			//회원가입 창 띄우기
//			Sign sign = new Sign();
//		});
//		
//		rogin.addActionListener(e->{
//			//아이디 비번 보내고
//			cmg.connect();
//			cmg.headerSend(Header.LOGIN);
//			System.out.println(idInput.getText());
//			System.out.println(pwInput.getText());
//			cmg.send(idInput.getText());
//			cmg.send(pwInput.getText());
//			//로그인 눌렀을때 정보가 맞으면 종료, 틀리면 메시지 출력
//			if(cmg.receive()) {
//				JOptionPane.showMessageDialog(mainPanel, "로그인 됨.");
//				dispose();
//			}
//			else
//				JOptionPane.showMessageDialog(mainPanel, "틀림.");
//		});
	}
	
	private void menu() {
		
	}
}