package server;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class SeatManager extends JDialog {

	private JPanel mainPanel = new JPanel();
	
	private JLabel uName = new JLabel();
	private JLabel uBirth = new JLabel();
	private JLabel uPay = new JLabel();
	
	private JTextArea ta = new JTextArea();
	
	private JButton msgSend = new JButton("메세지");
	private JButton save = new JButton("저장");
	private JButton cancel = new JButton("취소");
	
	private JButton clientExit = new JButton("사용 종료");

	//자리를 사용하고 있는 사용자의 ID를 받아옴
	public SeatManager(String id) {
		this.set(id);
		this.display();
		this.event(id);
		this.menu();

		this.setSize(300, 450);
		this.setTitle("자리 정보");
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	//자리 클릭 시 자리 정보 mainPanel 우측에 표시
	public void set(String id) {
		uName.setText("이         름 : "+FileManager.getUserName(id));
		uBirth.setText("생년월일 	: "+FileManager.getUserBirth(id));
		uPay.setText("사용금액 : "+FileManager.getUserMoney(id));
		ta.setText(FileManager.getUserMemo(id));
	}

	private void menu() {
		// TODO Auto-generated method stub

	}

	private void event(String id) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료
		
		//메세지 보내기
		msgSend.addActionListener(e -> {
			String uMsg = JOptionPane.showInputDialog("메세지 입력");
			ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
			ssm.sendMessage(uMsg);
		});
		
		//종료
		cancel.addActionListener(e -> {
			dispose();
		});
		
		//상품 db 저장
		save.addActionListener(e -> {
			FileManager.setUserMemo(id, ta.getText());
			FileManager.saveDB(id, FileManager.getUserClass(id));
			dispose();
		});
		
		//클라 강제 종료
		clientExit.addActionListener(e -> {
			int choose = JOptionPane.showConfirmDialog(mainPanel, "사용 종료 시키겠습니까?","사용 종료",JOptionPane.YES_NO_OPTION);
			if(choose == 0 ) {
				ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
				ssm.sendShutDownPC();
				dispose();
			}
		});
	}
	

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);

		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.DARK_GRAY);
		
		//사용자 이름 표시 라벨
		uName.setBounds(12, 10, 266, 32);
		uName.setForeground(Color.WHITE);
		mainPanel.add(uName);
		
		//사용자 생년월일 표시 라벨
		uBirth.setBounds(12, 52, 266, 32);
		uBirth.setForeground(Color.WHITE);
		mainPanel.add(uBirth);
		
		//사용자 사용금액 표시 라벨
		uPay.setBounds(12, 94, 266, 32);
		uPay.setForeground(Color.WHITE);
		mainPanel.add(uPay);
		
		//메모 부분 추가
		ta.setBounds(12, 136, 266, 169);
		mainPanel.add(ta);
		
		//메세지 보내기 버튼 추가
		msgSend.setBounds(41, 315, 97, 37);
		mainPanel.add(msgSend);
		
		//사용 종료 버튼 추가
		clientExit.setBounds(148, 315, 97, 37);
		mainPanel.add(clientExit);
		
		//저장 버튼 추가
		save.setBounds(41, 369, 97, 37);
		mainPanel.add(save);
		
		//취소 버튼 추가
		cancel.setBounds(148, 369, 97, 37);
		mainPanel.add(cancel);
		

	}

}
