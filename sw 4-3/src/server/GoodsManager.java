package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

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

import db.Account;
import header.Header;

class GoodsManager extends JFrame {

	// 서버 상품 관리
	private JPanel mainPanel = new JPanel();

	private JPanel[] subPanel = new JPanel[83];
	private JPanel gNum = new JPanel();

	private JLabel jlbtitle = new JLabel("상품 보기", JLabel.CENTER);

	private JLabel[][] jlb = new JLabel[81][4];

	private JButton[] jbt = new JButton[9];

	private JMenuBar jmb = new JMenuBar();

	private JMenuItem add = new JMenuItem("추가", JMenuItem.CENTER);
	private JMenuItem remove = new JMenuItem("삭제", JMenuItem.CENTER);
	private JMenuItem fix = new JMenuItem("수정", JMenuItem.CENTER);
	private JMenuItem reset = new JMenuItem("전체 삭제", JMenuItem.CENTER);

	private Font tFont = new Font("", Font.BOLD, 30);

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

	private String numRgx = "^[0-9]{1,}$";

	
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

	//상품 번호
	public void jbtSet() {
		for (int i = 0; i < jbt.length; i++) {
			jbt[i] = new JButton("" + (i + 1));
		}
	}

	public GoodsManager(int ch) {
		switch (ch) {
		case 0:
			this.display(pageN);
			this.event();
			this.menu();

			this.setSize(1500, 700);
			this.setTitle("메뉴판");
			this.setLocationByPlatform(true);
			this.setResizable(false);
			this.setVisible(true);
			break;
		}
	}

	private void menu() {
		// TODO Auto-generated method stub
		this.setJMenuBar(jmb);
		jmb.add(add);
		jmb.add(remove);
		jmb.add(fix);
		jmb.add(reset);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료

		//페이지 교체 이벤트
		ActionListener btChang = (e) -> {
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

		//추가
		add.addActionListener(e -> {
			AddManager amg = new AddManager(this, true);
			this.menuReset();
			this.menuSet(pageN);
			subPanel[1].repaint();
			subPanel[1].revalidate();
		});

		//전체 삭제
		reset.addActionListener(e -> {
			int choose = JOptionPane.showConfirmDialog(mainPanel, "전체 삭제하시겠습니까?", "전체삭제", JOptionPane.YES_NO_OPTION);
			if (choose == 0) {
				AccountManager.account.clear();
				AccountManager.account.put(Header.PCID, new Account(null, "PC시간", 0));
				this.menuReset();
				this.menuSet(pageN);
				subPanel[1].repaint();
				subPanel[1].revalidate();
			}
		});

		//제거
		remove.addActionListener(e -> {
			String gNum = JOptionPane.showInputDialog("삭제할 상품 번호 입력");
			if (gNum.equals("0")) {
				JOptionPane.showMessageDialog(this, "0번 상품은 삭제하실 수 없습니다.");
				return;
			}
			try {
				if (Pattern.matches(numRgx, gNum)) {
					
					int size = AccountManager.account.size();
					
					AccountManager.account.remove(Integer.parseInt(gNum));

					int nSize = AccountManager.account.size();
					
					// 목록 앞으로 땡김
					for (int i = Integer.parseInt(gNum); i < AccountManager.account.size(); i++) {
						AccountManager.account.put(i, AccountManager.account.get(i + 1));
					}

					// 목록 뒷부분 삭제
					for (int i = size; i > nSize; i--) {
						// System.out.println("뒷부분삭제");
						AccountManager.account.remove(i);
					}
					
					this.menuReset();
					this.menuSet(pageN);
					mainPanel.revalidate();
					mainPanel.repaint();
					JOptionPane.showMessageDialog(this, "상품이 삭제되었습니다.");
				} else {
					JOptionPane.showMessageDialog(this, "잘 못된 상품 번호입니다.");
				}
			} catch (Exception err) {
				JOptionPane.showMessageDialog(this, "잘 못된 상품 번호입니다.");
			}
		});

		//수정
		fix.addActionListener(e -> {
			String uNum = JOptionPane.showInputDialog("수정할 상품 번호 입력");
			if (Pattern.matches(numRgx, uNum)) {
				FixManager fmg = new FixManager(Integer.parseInt(uNum), this, true);
				this.menuReset();
				this.menuSet(pageN);
				subPanel[1].revalidate();
				subPanel[1].repaint();
			} else {
				JOptionPane.showMessageDialog(this, "잘 못된 상품 번호입니다.");
			}
		});
	}

	//판넬 제거
	public void menuReset() {
		subPanel[1].removeAll();
		subPanel[1].revalidate();

	}

	//라벨 초기화
	public void setJlb() {
		for (int i = 0; i < jlb.length; i++) {
			jlb[i][0] = new JLabel();
			jlb[i][1] = new JLabel();
			jlb[i][2] = new JLabel();
			jlb[i][3] = new JLabel();
		}
	}

	//페이지 별로 메뉴 출력
	public void menuSet(int n) {
		for (int i = (n * 9)+1; i < ((n + 1) * 9)+1; i++) {
			subPanel[1].add(subPanel[i + 2]);
			subPanel[i + 2].setBorder(line);

			try {
				name = AccountManager.account.get(i).getPName();
				price = AccountManager.account.get(i).getPprice();
				gIcon = AccountManager.account.get(i).getPIcon();
				gName = "상품명 : " + name;
				gPrice = "가격 : " + price;
				gNumber = "" + (i);

				jlb[i][0].setIcon(gIcon);
				jlb[i][0].setBounds(12, 10, 190, 123);
				subPanel[i + 2].add(jlb[i][0]);

				jlb[i][1].setText(gName);
				jlb[i][1].setBounds(214, 10, 169, 33);
				subPanel[i + 2].add(jlb[i][1]);

				jlb[i][2].setText(gPrice);
				jlb[i][2].setBounds(214, 81, 169, 33);
				subPanel[i + 2].add(jlb[i][2]);

				jlb[i][3].setText(gNumber);
				jlb[i][3].setBounds(425, 0, 50, 50);
				jlb[i][3].setFont(tFont);
				subPanel[i + 2].add(jlb[i][3]);
			} catch (Exception err) {
				gIcon = null;
				name = "";
				price = 0;
				gName = "";
				gPrice = "";
				gNumber = "";

				jlb[i][0].setIcon(gIcon);
				jlb[i][0].setBounds(12, 10, 190, 123);
				subPanel[i + 2].add(jlb[i][0]);

				jlb[i][1].setText(gName);
				jlb[i][1].setBounds(214, 10, 169, 33);
				subPanel[i + 2].add(jlb[i][1]);

				jlb[i][2].setText(gPrice);
				jlb[i][2].setBounds(214, 81, 169, 33);
				subPanel[i + 2].add(jlb[i][2]);

				jlb[i][3].setBounds(425, 0, 50, 50);
				jlb[i][3].setFont(tFont);
				subPanel[i + 2].add(jlb[i][3]);
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

		// subPanel 레이어 초기화
		for (int i = 0; i < subPanel.length; i++) {
			subPanel[i] = new JPanel();
			subPanel[i].setLayout(null);
		}
		// 라벨 생성
		this.setJlb();

		// 상품 보기 제목
		subPanel[0].setBounds(12, 10, 1400, 99);
		mainPanel.add(subPanel[0]);
		subPanel[0].setBorder(line);
		subPanel[0].setLayout(null);

		this.jbtSet();

		for (int i = 0; i < jbt.length; i++) {
			jbt[i].setBounds(10 + (i * 150), 20, 150, 60);
			subPanel[0].add(jbt[i]);
		}

		// 메뉴판 판넬
		subPanel[1].setBounds(12, 119, 1400, 500);
		mainPanel.add(subPanel[1]);
		subPanel[1].setBorder(line);
		subPanel[1].setLayout(new GridLayout(3, 3));

		// 메뉴 설정
		this.menuSet(pageN);

	}
}
