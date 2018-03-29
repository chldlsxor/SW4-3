package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import db.Account;

class CounterFrame extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel subPanel = new JPanel();
	private JPanel subPanel1 = new JPanel();
	private JPanel subPanel2 = new JPanel();
	private JPanel subPanel3 = new JPanel();
	private JPanel subPanel4 = new JPanel();
	private JPanel goodsView = new JPanel();
	
	private JMenuBar jmb = new JMenuBar();
	private JMenu file = new JMenu("파일(f)");
	private JMenu setting = new JMenu("상품(g)");
	
	private JMenuItem exit = new JMenuItem("종료");
	
	private JMenuItem view = new JMenuItem("보기");
	
	private JButton[] btList = new JButton[60]; 
	private JLabel[][] btlb = new JLabel[60][4];
	
	private JButton msg = new JButton("메세지 보내기");
	
	private JButton calculate = new JButton("정산");
	private JButton management = new JButton("상품 관리");
	
	private JLabel jlbJari = new JLabel();
	private JLabel jlbUserId = new JLabel("아이디 : kim");
	private JLabel jlbUserName = new JLabel("이름 : min");
	
	private JLabel jlbUserStartT = new JLabel("시작 시간 : 13시30분");
	private JLabel jlbUserUseT = new JLabel("사용 시간 : 00시40분");
	private JLabel jlbUserPrice = new JLabel("정산 요금 : 1000원");
	
	
	private ImageIcon ramen = new ImageIcon("image/ramen.jpg");
	private ImageIcon ddug = new ImageIcon("image/ddug.jpg");
	private ImageIcon coca = new ImageIcon("image/coca.JPG");
	private ImageIcon ice = new ImageIcon("image/ice.jpg");
	private ImageIcon sand = new ImageIcon("image/sand.jpg");
	private ImageIcon hotbar = new ImageIcon("image/hotbar.jpg");
	
	
	private Border line = BorderFactory.createLineBorder(Color.BLACK,3);
	private Font font = new Font("",Font.BOLD,30);
	
	private Dimension win = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	public CounterFrame() {
		btset();
		basicSetting();
		this.display();
		this.event();
		this.menu();
		
		System.out.println(btList.length);
		
		this.setSize(win.width, win.height);
		this.setTitle("KG PC방 - 관리자");
//		this.setLocation(100, 200);
		//위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(false);
		//상단부분이 나오지 않도록 설정
		this.setUndecorated(true);
		this.setResizable(true);
		this.setVisible(true);
	}
	

	private void btset() {
		for(int i = 0; i < btList.length ; i++) {
			btList[i] = new JButton();
			btList[i].setLayout(null);
			btlb[i][0] = new JLabel((i+1) + "번");
			btlb[i][0].setBounds(5, 5, 50,15);
			btList[i].add(btlb[i][0]);
			btlb[i][1] = new JLabel("빈 자리");
			btlb[i][1].setBounds(45, 0, 200,200);
			btList[i].add(btlb[i][1]);
			btlb[i][2] = new JLabel("회원 이름 : ");
			btlb[i][2].setBounds(5, 20, 100, 50);
			btList[i].add(btlb[i][2]);
			btlb[i][3] = new JLabel("남은 시간 : ");
			btlb[i][3].setBounds(5, 40, 100, 50);
			btList[i].add(btlb[i][3]);
		}
	}
	
	public void basicSetting() {
		AccountManager.account.put(1, new Account(ramen,"라면", 3500));
		AccountManager.account.put(2, new Account(ddug,"떡볶이", 4000));
		AccountManager.account.put(3, new Account(ice,"아이스티", 1500));
		AccountManager.account.put(4, new Account(coca,"콜라", 1000));
		AccountManager.account.put(5, new Account(sand,"샌드위치", 2500));
		AccountManager.account.put(6, new Account(hotbar,"핫바", 1000));
		AccountManager.account.put(7, new Account(ramen,"라면", 3500));
		
		AccountManager.account.put(8, new Account(ddug,"떡볶이", 4000));
		AccountManager.account.put(9, new Account(ice,"아이스티", 1500));
		AccountManager.account.put(10, new Account(coca,"콜라", 1000));
		AccountManager.account.put(11, new Account(sand,"샌드위치", 2500));
		AccountManager.account.put(12, new Account(hotbar,"핫바", 1000));
		AccountManager.account.put(13, new Account(ramen,"라면", 3500));
		AccountManager.account.put(14, new Account(ddug,"떡볶이", 4000));
		AccountManager.account.put(15, new Account(ice,"아이스티", 1500));
		AccountManager.account.put(16, new Account(coca,"콜라", 1000));
		AccountManager.account.put(17, new Account(sand,"샌드위치", 2500));
		
		AccountManager.account.put(18, new Account(hotbar,"핫바", 1000));
		AccountManager.account.put(19, new Account(ramen,"라면", 3500));
		AccountManager.account.put(20, new Account(ddug,"떡볶이", 4000));
		AccountManager.account.put(21, new Account(ice,"아이스티", 1500));
		AccountManager.account.put(22, new Account(coca,"콜라", 1000));
		AccountManager.account.put(23, new Account(sand,"샌드위치", 2500));
		AccountManager.account.put(24, new Account(hotbar,"핫바", 1000));
	}

	private void menu() {
		// TODO Auto-generated method stub
		this.setJMenuBar(jmb);
		jmb.add(file);
		jmb.add(setting);
		file.add(exit);
		setting.add(view);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//창 종료
		
		ActionListener btls = (e) ->{
			System.out.println(e.getSource());
			for(int i = 0 ; i < btList.length ; i ++) {
				if(btList[i] == e.getSource()) {
					jlbJari.setText(btlb[i][0].getText()+ " 자리");
					break;
				}
			}
		};
		
		MouseInputListener mil = new MouseInputAdapter() {	
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() > 1) {
					SeatManager smg = new SeatManager(); 
				}
			}
		};
		
		for(JButton i : btList) {
			i.addActionListener(btls);
			i.addMouseListener(mil);
		}
		
		exit.addActionListener(e -> {
			System.exit(0);
		});
		
		view.addActionListener(e -> {
			GoodsManager gmg = new GoodsManager();
		});
		
		management.addActionListener(e -> {
			GoodsManager gmg = new GoodsManager();
		});

	}

	private void display() {
		// TODO Auto-generated method stub
		//mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.darkGray);
		
		//좌석 판넬
		subPanel.setBounds(203, 20, win.width-500, win.height-20);
		mainPanel.add(subPanel);
		
		//좌석 버튼 추가
		subPanel.setLayout(new GridLayout(0,10,0,0));
		for(int i = 0 ; i < btList.length ; i++) {
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
		
		//메세지 보내기 버튼 출력
		msg.setBounds(12, 500, 160, 60);
		mainPanel.add(msg);
		
		//상품 관리 버튼 출력
		management.setBounds(12, 600, 160, 60);
		mainPanel.add(management);
		
		//폰트, 색상 설정
		jlbJari.setFont(font);
		jlbJari.setForeground(Color.BLACK);
		
		//상품 주문상황 판넬 추가
		goodsView.setBounds(1315, 20, 275, 800);
		mainPanel.add(goodsView);
		
	}	
}
