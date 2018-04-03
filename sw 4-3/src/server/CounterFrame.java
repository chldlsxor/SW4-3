package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

class CounterFrame extends JFrame {
	public static Boolean[] seatCheck = new Boolean[60];
	public static Boolean orderCheck = false;
	public static Boolean[] useCheck = new Boolean[60];
	public static List<String> orderId = new ArrayList();
	public static List<Integer> orderInfo = new ArrayList<>();

	public static void time(String id, int time) {
		btlb[Integer.parseInt(FileManager.getUserPCNum(id)) - 120][3].setText(
				"시간 : " + FileManager.getUserTime(id) / 3600 + " : " + FileManager.getUserTime(id) % 3600 / 60);
	}

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

	private JMenuItem gSave = new JMenuItem("상품 저장");

	// 자리 표시 버튼 / 자리 버튼 내 텍스트
	private JButton[] btList = new JButton[60];
	public static JLabel[][] btlb = new JLabel[60][6];

	// 주문 내역 라벨
	private JLabel orderLb = new JLabel("주문 내역");

	// 주문 내역 추가 버튼
	private JButton[] btGList = new JButton[20];
	private List<String> orderList = new ArrayList<>();

	private JButton bExit = new JButton("종료");

	private JButton msg = new JButton("주문 추가");

	private JButton calculate = new JButton("정산");
	private JButton management = new JButton("상품 관리");

	private JLabel jlbJari = new JLabel();
	private JLabel jlbUserId = new JLabel("아이디 : kim");
	private JLabel jlbUserName = new JLabel("이름 : min");

	private JLabel jlbUserStartT = new JLabel("시작 시간 : 13시30분");
	private JLabel jlbUserUseT = new JLabel("사용 시간 : 00시40분");
	private JLabel jlbUserPrice = new JLabel("정산 요금 : 1000원");

	private Border line = BorderFactory.createLineBorder(Color.BLACK, 3);
	private Font font = new Font("", Font.BOLD, 30);

	private int myTestCnt = 1;

	private Dimension win = Toolkit.getDefaultToolkit().getScreenSize();

	private Iterator<String> userId;

	private SimpleDateFormat f = new SimpleDateFormat("hh : mm");
	private String startTime;

	// 회원 아이디 자리마다 저장
	private String[] uId = new String[60];

	// private ServerManager sm;

	public CounterFrame() {
		ServerManager sm = new ServerManager();
		sm.setDaemon(true);
		sm.start();
		btset();
		gOpen();
		booleanSet();
		this.display();
		this.event();
		this.menu();

		System.out.println(btList.length);

		this.setSize(win.width, win.height);
		this.setTitle("KG PC방 - 관리자");
		// this.setLocation(100, 200);
		// 위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(false);
		// 상단부분이 나오지 않도록 설정
		this.setUndecorated(true);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void gOpen() {
		AccountManager.readDB();
	}

	private void btset() {
		for (int i = 0; i < btList.length; i++) {
			btList[i] = new JButton();
			btList[i].setLayout(null);
			btlb[i][0] = new JLabel((i + 120) + "번");
			btlb[i][0].setBounds(5, 5, 50, 15);
			btList[i].add(btlb[i][0]);
			btlb[i][1] = new JLabel("빈 자리");
			btlb[i][1].setBounds(45, 0, 200, 200);
			btList[i].add(btlb[i][1]);
			btlb[i][2] = new JLabel("회원 이름 : ");
			btlb[i][2].setBounds(5, 20, 100, 50);
			btList[i].add(btlb[i][2]);
			btlb[i][3] = new JLabel("남은 시간 : ");
			btlb[i][3].setBounds(5, 40, 100, 50);
			btList[i].add(btlb[i][3]);
			// 회원 아이디 저장
			btlb[i][4] = new JLabel();
			// 시작 시간 저장
			btlb[i][5] = new JLabel();
		}

		for (int i = 0; i < btGList.length; i++) {
			// btGList.add(new JButton(i+"번 주문"));
			btGList[i] = new JButton();
		}

	}

	private void menu() {
		// TODO Auto-generated method stub
		this.setJMenuBar(jmb);
		jmb.add(file);
		jmb.add(setting);
		file.add(exit);
		setting.add(view);
		setting.add(gSave);
	}

	private void orderReset() {
		// 주문 내역 초기화
		for (JButton j : btGList) {
			j.setText("");
		}

		// 주문 내역 다시 받아옴
		for (int l = 0; l < orderList.size(); l++) {
			btGList[l].setText(orderList.get(l));
		}
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료

		// for(String i : sm.idList) {
		// System.out.println(i);
		// }

		gSave.addActionListener(e -> {
			for (int Pid : AccountManager.account.keySet()) {
				AccountManager.saveDB(Pid, AccountManager.account.get(Pid));
			}
		});

		bExit.addActionListener(e -> {
			// seatCheck[(int)(Math.random()*60)] = true;

			System.exit(0);
		});

		msg.addActionListener(e -> {
			orderList.add((myTestCnt++) + "번 주문");
			this.orderReset();
		});

		calculate.addActionListener(e -> {
			AccountFrame afr = new AccountFrame();
		});

		// 버튼 누르면 오른쪽에 회원 정보 표시
		ActionListener btls = (e) -> {
			System.out.println(e.getSource());
			for (int i = 0; i < btList.length; i++) {
				if (btList[i] == e.getSource()) {
					jlbJari.setText(btlb[i][0].getText() + " 자리");
					jlbUserName.setText(btlb[i][2].getText());
					jlbUserStartT.setText(btlb[i][5].getText());
					jlbUserUseT.setText(btlb[i][3].getText());
					jlbUserPrice.setText("");
					break;
				}
			}
		};

		MouseInputListener mil = new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() > 1) {
					for (int i = 0; i < btList.length; i++) {
						if (e.getSource().equals(btList[i])) {
							SeatManager smg = new SeatManager(btlb[i][4].getText());
						}
					}
				}
			}
		};

		for (JButton i : btGList) {
			i.addActionListener(e -> {
				if (!e.getActionCommand().equals("")) {
					int uc = JOptionPane.showConfirmDialog(mainPanel, "주문 내역을 삭제 하시겠습니까?", "주문 내역 삭제",
							JOptionPane.YES_NO_OPTION);
					// 선택된 주문 삭제
					if (uc == 0) {
						for (int j = 0; j < btGList.length; j++) {
							if (btGList[j] == e.getSource()) {
								btGList[j].setText("");
								orderList.remove(j);
								this.orderReset();
								JOptionPane.showMessageDialog(mainPanel, "주문 내역을 삭제하였습니다.");
								break;
							}
						}
					}

				} else {
					JOptionPane.showMessageDialog(mainPanel, "주문 내역이 없습니다.");
				}
			});
		}

		for (JButton i : btList) {
			i.addActionListener(btls);
			i.addMouseListener(mil);
		}

		exit.addActionListener(e -> {
			System.exit(0);
		});

		view.addActionListener(e -> {
			GoodsManager gmg = new GoodsManager(0);
		});

		management.addActionListener(e -> {
			GoodsManager gmg = new GoodsManager(0);
		});

		Thread t = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					while (true) {
						userId = ServerManager.idList.iterator();
						if (userId.hasNext()) {
							String idU = userId.next();
							// mainReset();
							for (int i = 0; i < btList.length; i++) {
								if (seatCheck[i] && useCheck[i]) {
									System.out.println("==================여기야여기================"+idU);
									uId[i] = idU;
									btlb[i][5].setText(f.format(new Date()));
									btlb[i][4].setText(uId[i]);
									btlb[i][1].setText("사용중");
									btlb[i][2].setText("이름 : " + FileManager.getUserName(uId[i]));
									btlb[i][3].setText("시간 : " + FileManager.getUserTime(uId[i]) / 3600 + " : "
											+ FileManager.getUserTime(uId[i]) % 3600 / 60);
									btList[i].setBackground(Color.pink);
									
									useCheck[i] = false;
								}
								if (orderCheck) {
									System.out.println(orderId.size());
									for (int k = 0; k < orderId.size(); k++) {
										orderList.add(FileManager.getUserPCNum(orderId.get(k)) + " 번 "
												+ AccountManager.getPName(orderInfo.get(k * 2)) + " / "
												+ orderInfo.get(k * 2 + 1) + " / " + f.format(new Date()));
										orderCheck = false;
										orderReset();
									}
									orderId.removeAll(orderId);
									orderInfo.removeAll(orderInfo);
								}
							}
							// subPanel2.repaint();
						}

						mainReset();
						for (int i = 0; i < btList.length; i++) {
							if (!seatCheck[i]) {
								btlb[i][1].setText("");
								btlb[i][2].setText("");
								btlb[i][3].setText("");
								btlb[i][4].setText("");
								btlb[i][5].setText("");
								btList[i].setBackground(Color.WHITE);
								useCheck[i] = true;

							}
							// subPanel2.repaint();
						}
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		// 화면 갱신
		WindowListener wl = new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				t.setDaemon(true);
				t.start();

			}
		};
		this.addWindowListener(wl);

	}

	private void booleanSet() {
		for (int i = 0; i < seatCheck.length; i++) {
			seatCheck[i] = false;
			useCheck[i] = true;
		}
	}

	private void mainReset() {
		subPanel2.removeAll();
		subPanel2.revalidate();
	}

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.darkGray);

		// 좌석 판넬
		subPanel.setBounds(203, 20, win.width - 500, win.height - 20);
		mainPanel.add(subPanel);

		// 좌석 버튼 추가
		subPanel.setLayout(new GridLayout(0, 10, 0, 0));
		for (int i = 0; i < btList.length; i++) {
			subPanel.add(btList[i]);
		}

		// 회원 정보 판넬
		subPanel1.setBounds(0, 20, 189, 376);
		mainPanel.add(subPanel1);
		subPanel1.setLayout(null);
		subPanel1.setBackground(Color.darkGray);
		subPanel1.setBorder(line);

		// 좌석 표시 라벨
		subPanel2.setBounds(12, 10, 165, 69);
		subPanel1.add(subPanel2);
		subPanel2.setLayout(null);
		jlbJari.setBounds(12, 10, 141, 54);
		subPanel2.add(jlbJari);
		subPanel2.setBackground(Color.DARK_GRAY);
		subPanel2.setBorder(line);

		// 회원 정보 판넬
		subPanel3.setBounds(12, 89, 165, 95);
		subPanel1.add(subPanel3);
		subPanel3.setLayout(null);
		subPanel3.setBackground(Color.darkGray);
		subPanel3.setBorder(line);

		// 유저 id 라벨
		jlbUserId.setBounds(18, 19, 129, 29);
		subPanel3.add(jlbUserId);

		// 유저 name 라벨
		jlbUserName.setBounds(18, 56, 129, 29);
		subPanel3.add(jlbUserName);

		// 회원 시간 판넬
		subPanel4.setBounds(12, 206, 165, 146);
		subPanel1.add(subPanel4);
		subPanel4.setLayout(null);
		subPanel4.setBackground(Color.darkGray);
		subPanel4.setBorder(line);

		// 시작 시간 라벨
		jlbUserStartT.setBounds(12, 20, 141, 28);
		subPanel4.add(jlbUserStartT);

		// 사용 시간 라벨
		jlbUserUseT.setBounds(12, 58, 141, 35);
		subPanel4.add(jlbUserUseT);

		// 정산 요금 라벨
		jlbUserPrice.setBounds(12, 103, 141, 33);
		subPanel4.add(jlbUserPrice);

		// 정산 버튼 출력
		calculate.setBounds(12, 450, 160, 60);
		mainPanel.add(calculate);

		// 메세지 보내기 버튼 출력
		msg.setBounds(12, 550, 160, 60);
		mainPanel.add(msg);

		// 상품 관리 버튼 출력
		management.setBounds(12, 650, 160, 60);
		mainPanel.add(management);

		// 종료 버튼 출력
		bExit.setBounds(12, 750, 160, 60);
		mainPanel.add(bExit);

		// 폰트, 색상 설정
		jlbJari.setFont(font);
		jlbJari.setForeground(Color.BLACK);
		jlbUserName.setForeground(Color.black);
		jlbUserId.setForeground(Color.BLACK);
		jlbUserStartT.setForeground(Color.black);
		jlbUserUseT.setForeground(Color.black);

		// 상품 주문상황 판넬 추가
		goodsView.setLayout(new GridLayout(0, 1, 0, 0));
		goodsView.setBounds(1315, 20, 275, 800);
		mainPanel.add(goodsView);

		// 주문 내역 버튼 추가
		for (JButton i : btGList) {
			goodsView.add(i);
		}

		if (orderList.size() <= btGList.length) {
			for (int i = 0; i < orderList.size(); i++) {
				btGList[i].setText(orderList.get(i));
			}
		} else {
			for (int i = 0; i < btGList.length; i++) {
				btGList[i].setText(orderList.get(i));
			}
		}

	}
}
