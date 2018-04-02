package server;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class SeatManager extends JDialog {

	// 기존에는 Component를 Frame에 직접 배치했었는데 이러면 관리효율이 떨어진다.
	// Panel을 만들어서 Component를 배치할 수 있도록 설정할 수 있다(ContentPane)
	private JPanel mainPanel = new JPanel();
	
	private JLabel uName = new JLabel();
	private JLabel uBirth = new JLabel();
	private JLabel uPay = new JLabel();
	
	private JTextArea ta = new JTextArea();
	
	private JButton msgSend = new JButton("메세지 보내기");
	private JButton save = new JButton("저장");
	private JButton cancel = new JButton("취소");
	

	public SeatManager(String id) {
		this.set(id);
		this.display();
		this.event(id);
		this.menu();

		this.setSize(300, 450);
		this.setTitle("자리 정보");
		// this.setLocation(100, 200);
		// 위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(true);
		// 상단부분이 나오지 않도록 설정
		// this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void menu() {
		// TODO Auto-generated method stub

	}

	private void event(String id) {
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);//프로그램 종료
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료
		// this.setDefaultCloseOperation(HIDE_ON_CLOSE);//창숨김
		// 위의 것들이 다 싫을 경우 커스텀 이벤트 설정
		// this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//기본 이벤트 방지
		
		msgSend.addActionListener(e -> {
			String uMsg = JOptionPane.showInputDialog("메세지 입력");
			JOptionPane.showMessageDialog(this, FileManager.getUserIP(id)+"");
			ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
			ssm.sendMessage(uMsg);
		});
		
		cancel.addActionListener(e -> {
			dispose();
		});
		
		save.addActionListener(e -> {
			FileManager.saveDB(id, FileManager.map.get(id));
		});
		

	}
	
	public void set(String id) {
		uName.setText("이         름 : "+FileManager.getUserName(id));
		uBirth.setText("생년월일 	: "+FileManager.getUserBirth(id));
		uPay.setText("사용금액 : ");
		ta.setText(FileManager.map.get(id).getMemo());
	}

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);

		// 모든 컴포넌트의 추가는 mainPanel에 수행
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
		msgSend.setBounds(41, 315, 204, 44);
		mainPanel.add(msgSend);
		
		//저장 버튼 추가
		save.setBounds(41, 369, 97, 37);
		mainPanel.add(save);
		
		//취소 버튼 추가
		cancel.setBounds(148, 369, 97, 37);
		mainPanel.add(cancel);
		

	}

}
