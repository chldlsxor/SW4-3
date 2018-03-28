package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import db.Account;

class GoodsManager extends JFrame {

	private JPanel mainPanel = new JPanel();

	private JPanel[] subPanel = new JPanel[20];
	private JPanel gNum = new JPanel();

	private JLabel jlbtitle = new JLabel("상품 보기", JLabel.CENTER);

	private JLabel[][] jlb = new JLabel[18][3];

	private JMenuBar jmb = new JMenuBar();

	private JMenuItem add = new JMenuItem("추가", JMenuItem.CENTER);
	private JMenuItem remove = new JMenuItem("삭제", JMenuItem.CENTER);
	private JMenuItem fix = new JMenuItem("수정", JMenuItem.CENTER);
	private JMenuItem reset = new JMenuItem("새로고침", JMenuItem.CENTER);

	private Font tFont = new Font("", Font.BOLD, 30);

	private Border line = BorderFactory.createLineBorder(Color.black, 3);

	private FileDialog fileOpen = new FileDialog(this, "파일 불러오기", FileDialog.LOAD);

	private JScrollPane jsp = new JScrollPane(subPanel[1]);
	
	private String name;
	private int price;
	private String gName;
	private String gPrice;
	private ImageIcon gIcon;

	private int ret = 0;

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

	public GoodsManager() {
		this.display();
		this.event();
		this.menu();

		this.setSize(1500, 600);
		this.setTitle("메뉴판");
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
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

		add.addActionListener(e -> {
			AddManager amg = new AddManager(this,true);
			System.out.println("dd");
			
			this.menuReset();
			this.menuSet();
			this.revalidate();
			this.repaint();
		});

		reset.addActionListener(e -> {
			System.out.println("새로고침");
			this.menuReset();
			this.menuSet();
			this.revalidate();
			this.repaint();
		});

		remove.addActionListener(e -> {
			String gNum = JOptionPane.showInputDialog("삭제할 상품 번호 입력");
			try {
				int size = AccountManager.account.size();
				AccountManager.account.remove(Integer.parseInt(gNum));
				int nSize = AccountManager.account.size();
				// System.out.println(AccountManager.account.size());
				// System.out.println(size);
				// 목록 앞으로 땡김
				for (int i = Integer.parseInt(gNum); i < AccountManager.account.size(); i++) {
					AccountManager.account.put(i, AccountManager.account.get(i + 1));
				}

				// 목록 뒷부분 삭제
				for (int i = size; i > nSize; i--) {
					System.out.println("뒷부분삭제");
					AccountManager.account.remove(i);
				}
				this.menuReset();
				this.menuSet();
				this.revalidate();
				this.repaint();
				JOptionPane.showMessageDialog(this, "상품이 삭제되었습니다.");
			} catch (Exception err) {
				JOptionPane.showMessageDialog(this, "잘 못된 상품 번호입니다.");
			}
		});

		fix.addActionListener(e -> {
			String uNum = JOptionPane.showInputDialog("수정할 상품 번호 입력");
			FixManager fmg = new FixManager(Integer.parseInt(uNum),this,true);
			this.menuReset();
			this.menuSet();
			this.revalidate();
			this.repaint();
		});
	}

	public void menuReset() {
		subPanel[1].removeAll();
		subPanel[1].revalidate();
	}

	public void menuSet() {
		for (int i = 0; i < jlb.length; i++) {
			subPanel[1].add(subPanel[i + 2]);
			subPanel[i + 2].setBorder(line);

			try {
				if (AccountManager.account.size() > i) {
					name = AccountManager.account.get(i + 1).getPName();
					price = AccountManager.account.get(i + 1).getPprice();
					gIcon = AccountManager.account.get(i + 1).getPIcon();
					gName = "상품명 : " + name;
					gPrice = "가격 : " + price;

				} else {
					gIcon = null;
					name = "";
					price = 0;
					gName = "";
					gPrice = "";
				}
				jlb[i][0] = new JLabel(gIcon);
				jlb[i][0].setBounds(12, 10, 190, 123);
				subPanel[i + 2].add(jlb[i][0]);

				jlb[i][1] = new JLabel(gName);
				jlb[i][1].setBounds(214, 10, 169, 33);
				subPanel[i + 2].add(jlb[i][1]);

				jlb[i][2] = new JLabel(gPrice);
				jlb[i][2].setBounds(214, 81, 169, 33);
				subPanel[i + 2].add(jlb[i][2]);
			} catch (Exception err) {
				gIcon = null;
				name = "";
				price = 0;
				gName = "";
				gPrice = "";
				jlb[i][0] = new JLabel(gIcon);
				jlb[i][0].setBounds(12, 10, 190, 123);
				subPanel[i + 2].add(jlb[i][0]);

				jlb[i][1] = new JLabel(gName);
				jlb[i][1].setBounds(214, 10, 169, 33);
				subPanel[i + 2].add(jlb[i][1]);

				jlb[i][2] = new JLabel(gPrice);
				jlb[i][2].setBounds(214, 81, 169, 33);
				subPanel[i + 2].add(jlb[i][2]);
			}
		}
	}

	private void display() {
		// mainPanel을 기본 패널로 설정
		this.setLayout(null);
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(1400, 5000));
		mainPanel.setBackground(Color.DARK_GRAY);

		// subPanel 레이어 초기화
		for (int i = 0; i < subPanel.length; i++) {
			subPanel[i] = new JPanel();
			subPanel[i].setLayout(null);
		}
		// subPanel[1] 그리드 레이어 적용 -> 메뉴판
		subPanel[1].setLayout(new GridLayout(0, 4, 0, 0));

		// 상품 보기 제목
		subPanel[0].setBounds(12, 10, 1400, 99);
		mainPanel.add(subPanel[0]);
		subPanel[0].setBorder(line);
		subPanel[0].setLayout(null);

		// 제목 라벨
		jlbtitle.setBounds(12, 10, 1400, 79);
		jlbtitle.setFont(tFont);
		subPanel[0].add(jlbtitle);

		// 메뉴판 판넬
		subPanel[1].setBounds(12, 119, 1400, 900);
		mainPanel.add(subPanel[1]);
		subPanel[1].setBorder(line);
		subPanel[1].setLayout(new GridLayout(0, 3, 0, 0));

		// 메뉴 설정
		this.menuSet();

		jsp.setBounds(0, 0, 200,300);
		this.add(jsp);

	}

}
