package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

class CounterFrame extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel subPanel = new JPanel();
	private JPanel subPanel1 = new JPanel();
	private JPanel subPanel2 = new JPanel();
	private JPanel subPanel3 = new JPanel();
	private JPanel subPanel4 = new JPanel();
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu file = new JMenu("파일(f)");
	private JMenu setting = new JMenu("상품(g)");
	
	private JMenuItem exit = new JMenuItem("종료");
	
	private JMenuItem view = new JMenuItem("보기");
	private JMenuItem add = new JMenuItem("추가");
	private JMenuItem remove = new JMenuItem("삭제");
	private JMenuItem fix = new JMenuItem("수정");
	
	private JButton[] btList = new JButton[4]; 
	private JButton calculate = new JButton("정산");
	private JButton management = new JButton("상품 관리");
	
	private JLabel jlbJari = new JLabel("1 번 자리");
	private JLabel jlbUserId = new JLabel("아이디 : kim");
	private JLabel jlbUserName = new JLabel("이름 : min");
	
	private JLabel jlbUserStartT = new JLabel("시작 시간 : 13시30분");
	private JLabel jlbUserUseT = new JLabel("사용 시간 : 00시40분");
	private JLabel jlbUserPrice = new JLabel("정산 요금 : 1000원");
	
	
	
	private Border line = BorderFactory.createLineBorder(Color.BLACK,3);
	private Font font = new Font("",Font.BOLD,30);
	
	
	public CounterFrame() {
		btset();
		this.display();
		this.event();
		this.menu();
		
		System.out.println(btList.length);
		this.setSize(1280, 800);
		this.setTitle("KG PC방 - 관리자");
//		this.setLocation(100, 200);
		//위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(true);
		//상단부분이 나오지 않도록 설정
//		this.setUndecorated(true);
		this.setResizable(true);
		this.setVisible(true);
	}
	

	private void btset() {
		for(int i = 0; i < 4 ; i++) {
			btList[i] = new JButton();
			btList[i].setLayout(new BorderLayout());
			btList[i].add(new JLabel((i+1) + "번"),BorderLayout.NORTH);
			btList[i].add(new JLabel("빈 자리"),BorderLayout.CENTER);
		}
		System.out.println(btList[3]);
	}

	private void menu() {
		// TODO Auto-generated method stub
		this.setJMenuBar(jmb);
		jmb.add(file);
		jmb.add(setting);
		file.add(exit);
		setting.add(view);
		setting.add(add);
		setting.add(remove);
		setting.add(fix);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//창 종료
		
		view.addActionListener(e -> {
			GoodsManager gmg = new GoodsManager();
		});
		add.addActionListener(e -> {
			
		});
		remove.addActionListener(e -> {
			
		});
		fix.addActionListener(e -> {
			
		});
	}

	private void display() {
		// TODO Auto-generated method stub
		//mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.darkGray);
		
		//좌석 판넬
		subPanel.setBounds(203, 20, 304, 441);
		mainPanel.add(subPanel);
		
		//좌석 버튼 추가
		subPanel.setLayout(new GridLayout(2, 2));
		for(int i = 0 ; i < 4 ; i++) {
			subPanel.add(btList[i]);
		}
		
		//회원 정보 판넬
		subPanel1.setBounds(0, 20, 189, 441);
		mainPanel.add(subPanel1);
		subPanel1.setLayout(null);
		subPanel1.setBackground(Color.darkGray);
		subPanel1.setBorder(line);
		
		//정산 버튼 출력 
		calculate.setBounds(40, 362, 102, 69);
		subPanel1.add(calculate);
		
		//좌석 표시 라벨
		subPanel2.setBounds(12, 10, 165, 69);
		subPanel1.add(subPanel2);
		subPanel2.setLayout(null);
		jlbJari.setBounds(12, 10, 141, 54);
		subPanel2.add(jlbJari);
		subPanel2.setBackground(Color.darkGray);
		subPanel2.setBorder(line);
		
		//회원 정보 판넬
		subPanel3.setBounds(12, 89, 165, 95);
		subPanel1.add(subPanel3);
		subPanel3.setLayout(null);
		subPanel3.setBackground(Color.darkGray);
		subPanel3.setBorder(line);
		
		//유저 id 라벨
		jlbUserId.setBounds(18, 19, 129,29);
		subPanel3.add(jlbUserId);
		
		//유저 name 라벨
		jlbUserName.setBounds(18, 56, 129, 29);
		subPanel3.add(jlbUserName);
		
		//회원 시간 판넬
		subPanel4.setBounds(12, 206, 165, 146);
		subPanel1.add(subPanel4);
		subPanel4.setLayout(null);
		subPanel4.setBackground(Color.darkGray);
		subPanel4.setBorder(line);
		
		//시작 시간 라벨
		jlbUserStartT.setBounds(12, 20, 141, 28);
		subPanel4.add(jlbUserStartT);
		
		//사용 시간 라벨
		jlbUserUseT.setBounds(12, 58, 141, 35);
		subPanel4.add(jlbUserUseT);
		
		//정산 요금
		jlbUserPrice.setBounds(12, 103, 141, 33);
		subPanel4.add(jlbUserPrice);
		
		//폰트, 색상 설정
		jlbJari.setFont(font);
		jlbJari.setForeground(Color.BLACK);
		
	}	
}
