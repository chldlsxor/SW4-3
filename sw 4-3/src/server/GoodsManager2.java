package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

class GoodsManager2 extends JFrame {

	// 클라 주문판
	private JPanel mainPanel = new JPanel();

	private JPanel[] subPanel = new JPanel[83];
	// 클라 관리용
	private JButton[] goodsBt = new JButton[81];

	private JPanel gNum = new JPanel();

	private JPanel gBasket = new JPanel();
	private JButton[] gButton = new JButton[9];
	private JLabel[][] gJlb = new JLabel[9][3];
	private JButton[] gBcheck = new JButton[9];

	private JPanel gRet = new JPanel();

	private JLabel gRetLb = new JLabel("총 요금 : 0원");
	private JButton gRetBt = new JButton("주문하기");

	private JLabel jlbtitle = new JLabel("상품 보기", JLabel.CENTER);

	private JLabel[][] jlb = new JLabel[81][4];

	private JButton[] jbt = new JButton[9];

	private Font tFont = new Font("", Font.BOLD, 30);
	private Font rFont = new Font("", Font.BOLD, 20);

	private Border line = BorderFactory.createLineBorder(Color.black, 3);

	private FileDialog fileOpen = new FileDialog(this, "파일 불러오기", FileDialog.LOAD);

	private JScrollPane jsp = new JScrollPane(subPanel[1]);

	private String name;
	private int price;
	private String gName;
	private String gPrice;
	private ImageIcon gIcon;
	private String gNumber;

	private int pageN = 0;
	private String pageC;

	private int ret = 0;
	
	private int retPrice = 0;

	public int check() {
		for (int i = 0; i < jlb.length; i++) {
			if (jlb[i][1].equals("")) {
				ret = i;
				System.out.println(i);
				return ret;
			}
		}
		return ret;
	}

	public void jbtSet() {
		for (int i = 0; i < jbt.length; i++) {
			jbt[i] = new JButton("" + (i + 1));
		}
	}

	public GoodsManager2() {
		AccountManager.readDB();
		this.display(pageN);
		this.event();

		this.setSize(1500, 850);
		this.setTitle("메뉴판");
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void menu() {
		// TODO Auto-generated method stub
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료

		ActionListener btChang = (e) -> {
			// System.out.println(e.getActionCommand());
			pageC = e.getActionCommand();
			pageN = Integer.parseInt(pageC) - 1;
			this.menuReset();
			this.menuSet(pageN);
			this.revalidate();
			this.repaint();
		};

		for (JButton i : jbt) {
			i.addActionListener(btChang);
		}
		
		ActionListener gbt = (e)-> {
		};
		
		for (int i = 0; i < goodsBt.length; i++) {
			int k = i;
			goodsBt[i].addActionListener(e -> {
				String su = JOptionPane.showInputDialog("수량을 입력해 주세요");
				for (int j = 0; j < gJlb.length; j++) {
					if(gJlb[j][0].getText().equals(jlb[k][1].getText())) {
						gJlb[j][1].setText("수량 : "+su);
						int a = Integer.parseInt(su);
						String sPrice = jlb[k][2].getText().substring(5);
						gJlb[j][2].setText(sPrice);
						int b = Integer.parseInt(sPrice);
						ret = ret + (a*b);
						gRetLb.setText("총 요금 : "+(ret)+"원");
						break;
					} 
					if (gJlb[j][0].getText().equals("")) {
						gJlb[j][0].setText(jlb[k][1].getText());
						gJlb[j][1].setText("수량 : "+su);
						int a = Integer.parseInt(su);
						String sPrice = jlb[k][2].getText().substring(5);
						gJlb[j][2].setText(sPrice);
						int b = Integer.parseInt(sPrice);
						ret = ret + (a*b);
						gRetLb.setText("총 요금 : "+(ret)+"원");
						break;
					}
				}
			});
		}
		
		for(int i = 0 ; i < gButton.length ; i ++) {
			int j = i;
			gButton[i].addActionListener(e -> {
				int ch = JOptionPane.showConfirmDialog(mainPanel, "메뉴를 삭제하시겠습니까?","주문 취소", JOptionPane.YES_NO_OPTION);
				switch(ch) {
				case 0 :
					int a = Integer.parseInt(gJlb[j][1].getText().substring(5));
					int b = Integer.parseInt(gJlb[j][2].getText());
					System.out.println("됨도미이? : "+a);
					gJlb[j][0].setText("");
					gJlb[j][1].setText("");
					System.out.println(ret);
					System.out.println("a = "+a);
					System.out.println("b = "+b);
					ret = ret - (a*b);
					System.out.println(ret);
					gRetLb.setText("총 요금 : "+(ret)+"원");
					break;
				}
			});
		}
		
		
		

	}

	public void menuReset() {
		subPanel[1].removeAll();
		subPanel[1].revalidate();

	}

	public void setJlb() {
		for (int i = 0; i < jlb.length; i++) {
			jlb[i][0] = new JLabel();
			jlb[i][1] = new JLabel();
			jlb[i][2] = new JLabel();
			jlb[i][3] = new JLabel();
		}
	}

	private void cBtSet() {
		for (int i = 0; i < goodsBt.length; i++) {
			goodsBt[i] = new JButton();
			goodsBt[i].setLayout(null);
		}
	}

	public void menuSet(int n) {
		for (int i = (n * 9); i < ((n + 1) * 9); i++) {
			System.out.println("n " + n);
			subPanel[1].add(goodsBt[i]);
			goodsBt[i].setBorder(line);
			System.out.println(i);
			System.out.println(AccountManager.account.size());

			try {
				// if (AccountManager.account.size() > i) {
				name = AccountManager.account.get(i + 1).getPName();
				System.out.println(name);
				price = AccountManager.account.get(i + 1).getPprice();
				gIcon = AccountManager.account.get(i + 1).getPIcon();
				gName = "상품명 : " + name;
				gPrice = "가격 : " + price;
				gNumber = "" + (i + 1);

				// } else {
				// gIcon = null;
				// name = "";
				// price = 0;
				// gName = "";
				// gPrice = "";
				// }
				jlb[i][0].setIcon(gIcon);
				jlb[i][0].setBounds(12, 10, 190, 123);
				goodsBt[i].add(jlb[i][0]);

				jlb[i][1].setText(gName);
				jlb[i][1].setBounds(214, 10, 169, 33);
				goodsBt[i].add(jlb[i][1]);

				jlb[i][2].setText(gPrice);
				jlb[i][2].setBounds(214, 81, 169, 33);
				goodsBt[i].add(jlb[i][2]);

				jlb[i][3].setText(gNumber);
				jlb[i][3].setBounds(425, 0, 50, 50);
				jlb[i][3].setFont(tFont);
				goodsBt[i].add(jlb[i][3]);
			} catch (Exception err) {
				System.out.println("i = " + i);
				gIcon = null;
				name = "";
				price = 0;
				gName = "";
				gPrice = "";
				gNumber = "";

				jlb[i][0].setIcon(gIcon);
				jlb[i][0].setBounds(12, 10, 190, 123);
				goodsBt[i].add(jlb[i][0]);

				jlb[i][1].setText(gName);
				jlb[i][1].setBounds(214, 10, 169, 33);
				subPanel[i + 2].add(jlb[i][1]);

				jlb[i][2].setText(gPrice);
				jlb[i][2].setBounds(214, 81, 169, 33);
				goodsBt[i].add(jlb[i][2]);

				jlb[i][3].setBounds(425, 0, 50, 50);
				jlb[i][3].setFont(tFont);
				goodsBt[i].add(jlb[i][3]);
			}
		}
	}

	private void display(int pageN) {
		// mainPanel을 기본 패널로 설정
		this.setLayout(null);
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(1400, 600));
		mainPanel.setBackground(Color.DARK_GRAY);

		// 버튼 레이어 초기황
		this.cBtSet();
		// subPanel 레이어 초기화
		for (int i = 0; i < subPanel.length; i++) {
			subPanel[i] = new JPanel();
			subPanel[i].setLayout(null);
		}
		// 라벨 생성
		this.setJlb();
		// subPanel[1] 그리드 레이어 적용 -> 메뉴판
		// subPanel[1].setLayout(new GridLayout(0, 4, 0, 0));

		// 상품 보기 제목
		subPanel[0].setBounds(12, 10, 1400, 99);
		mainPanel.add(subPanel[0]);
		subPanel[0].setBorder(line);
		subPanel[0].setLayout(null);

		// 제목 라벨
		// jlbtitle.setBounds(12, 10, 1400, 79);
		// jlbtitle.setFont(tFont);
		// subPanel[0].add(jlbtitle);
		// 페이지 버튼으로 교체
		this.jbtSet();
		// jbt[0].setBounds(10, 20, 150, 60);
		// subPanel[0].add(jbt[0]);
		for (int i = 0; i < jbt.length; i++) {
			jbt[i].setBounds(10 + (i * 150), 20, 150, 60);
			subPanel[0].add(jbt[i]);
		}

		// 메뉴판 판넬
		subPanel[1].setBounds(12, 119, 1400, 450);
		mainPanel.add(subPanel[1]);
		subPanel[1].setBorder(line);
		subPanel[1].setLayout(new GridLayout(3, 3));

		// 상품 번호 설정
		// this.gNum();

		// 메뉴 설정
		this.menuSet(pageN);

		// jsp.setBounds(0, 0, 200,300);
		// this.add(jsp);

		// 주문창
		gBasket.setBounds(12, 600, 1200, 200);
		gBasket.setLayout(new GridLayout(0, 3, 0, 0));
		mainPanel.add(gBasket);

		// 주문창에 라벨 추가
		for (int i = 0; i < gButton.length; i++) {
			// 판넬 추가
			gButton[i] = new JButton();
			gBasket.add(gButton[i]);
			gButton[i].setLayout(null);

			// 상품명 표시
			gJlb[i][0] = new JLabel("");
			gJlb[i][0].setBounds(10, 10, 200, 50);
			gButton[i].add(gJlb[i][0]);

			// 수량표시
			gJlb[i][1] = new JLabel("", JLabel.RIGHT);
			gJlb[i][1].setBounds(300, 10, 50, 50);
			gButton[i].add(gJlb[i][1]);
			
			//가격 저장 라벨
			gJlb[i][2] = new JLabel();

		}

		// 주문버튼 , 주문 요금
		gRet.setBounds(1240, 600, 200, 200);
		gRet.setLayout(null);
		mainPanel.add(gRet);

		// 주문 버튼 추가
		gRetBt.setBounds(10, 100, 180, 90);
		gRet.add(gRetBt);

		// 총 요금 라벨 추가
		gRetLb.setBounds(10, 10, 180, 50);
		gRetLb.setFont(rFont);
		gRet.add(gRetLb);

	}
}
