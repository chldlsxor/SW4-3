package server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class OrderManager extends JFrame{
	
	// 기존에는 Component를 Frame에 직접 배치했었는데 이러면 관리효율이 떨어진다.
	// Panel을 만들어서 Component를 배치할 수 있도록 설정할 수 있다(ContentPane)
	private JPanel mainPanel = new JPanel();
	
	//<html> 내용 <br> 내용...<br></html>
	//<br> 줄바꿈
	//<html> 시작
	//</html> 종료
	private JLabel jtf = new JLabel();

	private JButton cancel = new JButton("확인");
	
	public OrderManager(String title) {
		super();
		this.display();
		this.event();
		this.menu();
		
		this.setSize(300, 450);
		this.setTitle(title);
//		this.setLocation(100, 200);
		//위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(true);
		//상단부분이 나오지 않도록 설정
//		this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void menu() {
		// TODO Auto-generated method stub
		
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//창 종료

		cancel.addActionListener(e -> {
			dispose();
		});
		
	}

	private void display() {
		// TODO Auto-generated method stub
		//mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		//모든 컴포넌트의 추가는 mainPanel에 수행
		
		//주문 내역 텍스트 필드
		jtf.setBounds(12, 10, 266, 349);
		mainPanel.add(jtf);
		
		//취소 버튼
		cancel.setBounds(67, 369, 150, 37);
		mainPanel.add(cancel);
	}
	
	
	
	
}

//http://bisuanytime.blogspot.kr/2016/10/java-mvc-jlist.html -> 리스트 만들기 (jlist 갱신)