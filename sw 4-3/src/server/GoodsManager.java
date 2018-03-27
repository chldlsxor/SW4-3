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

class GoodsManager extends JFrame {

	private JPanel mainPanel = new JPanel();

	private JPanel[] subPanel = new JPanel[20];

	private JLabel jlbtitle = new JLabel("��ǰ ����", JLabel.CENTER);

	private JButton[][] jlb = new JButton[18][3];

	private JMenuBar jmb = new JMenuBar();

	private JMenuItem add = new JMenuItem("�߰�", JMenuItem.CENTER);
	private JMenuItem remove = new JMenuItem("����", JMenuItem.CENTER);
	private JMenuItem fix = new JMenuItem("����", JMenuItem.CENTER);

	private Font tFont = new Font("", Font.BOLD, 30);

	private Border line = BorderFactory.createLineBorder(Color.black, 3);

	private ImageIcon ramen = new ImageIcon("image/ramen.jpg");
	private ImageIcon ddug = new ImageIcon("image/ddug.jpg");
	private ImageIcon coca = new ImageIcon("image/coca.JPG");
	private ImageIcon ice = new ImageIcon("image/ice.jpg");
	private ImageIcon sand = new ImageIcon("image/sand.jpg");
	private ImageIcon hotbar = new ImageIcon("image/hotbar.jpg");

	private FileDialog fileOpen = new FileDialog(this, "���� �ҷ�����", FileDialog.LOAD);

	private JScrollPane jsp = new JScrollPane(subPanel[1], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	private String name;
	private int price;
	private String gName;
	private String gPrice;

	public GoodsManager() {
		basicSetting();
		this.display();
		this.event();
		this.menu();

		this.setSize(850, 600);
		this.setTitle("�޴���");
		this.setLocationByPlatform(true);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void basicSetting() {
		AccountManager.account.put(1, new Account("���", 3500));
		AccountManager.account.put(2, new Account("������", 4000));
		AccountManager.account.put(3, new Account("���̽�Ƽ", 1500));
		AccountManager.account.put(4, new Account("�ݶ�", 1000));
		AccountManager.account.put(5, new Account("������ġ", 2500));
		AccountManager.account.put(6, new Account("�ֹ�", 1000));
	}

	private void menu() {
		// TODO Auto-generated method stub
		this.setJMenuBar(jmb);
		jmb.add(add);
		jmb.add(remove);
		jmb.add(fix);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����
		ActionListener btlistener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < jlb.length; i++) {
					for (int j = 0; j < jlb[i].length; j++) {
						if (e.getSource().equals(jlb[i][j])) {
							System.out.println(e.getActionCommand());
							switch (j) {
							case 0:
								fileOpen.setVisible(true);
								System.out.println(fileOpen.getDirectory() + fileOpen.getFile());
								if (fileOpen.getDirectory() == null && fileOpen.getFile() == null) {
									jlb[i][j].setIcon(null);
									break;
								}
								jlb[i][j].setIcon(new ImageIcon(fileOpen.getDirectory() + fileOpen.getFile()));
								// JOptionPane.showMessageDialog(mainPanel, "��� �߰� ���Դϴ�.");
								break;
							case 1:
								String str = JOptionPane.showInputDialog("������ �̸�");
								if (str == null || str.equals("")) {
									jlb[i][j].setText("");
									break;
								}
								String strN = "��ǰ�� : " + str;
								jlb[i][j].setText(strN);
								str = "";
								break;
							case 2:
								String str1 = JOptionPane.showInputDialog("������ ����");
								if (str1 == null || str1.equals("")) {
									jlb[i][j].setText("");
									break;
								}
								String strP = "���� : " + str1;
								jlb[i][j].setText(strP);
								break;
							}
							break;
						}
					}
				}
			}
		};

		for (int i = 0; i < jlb.length; i++) {
			for (int j = 0; j < jlb[i].length; j++) {
				jlb[i][j].addActionListener(btlistener);
			}
		}
	}

	private void display() {
		// mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);

		// subPanel ���̾� �ʱ�ȭ
		for (int i = 0; i < subPanel.length; i++) {
			subPanel[i] = new JPanel();
			subPanel[i].setLayout(null);
		}
		// subPanel[1] �׸��� ���̾� ���� -> �޴���
		subPanel[1].setLayout(new GridLayout(0, 4, 0, 0));

		// ��ǰ ���� ����
		subPanel[0].setBounds(12, 10, 1400, 99);
		mainPanel.add(subPanel[0]);
		subPanel[0].setBorder(line);
		subPanel[0].setLayout(null);

		// ���� ��
		jlbtitle.setBounds(12, 10, 1400, 79);
		jlbtitle.setFont(tFont);
		subPanel[0].add(jlbtitle);

		// �޴��� �ǳ�
		subPanel[1].setBounds(12, 119, 1400, 900);
		mainPanel.add(subPanel[1]);
		subPanel[1].setBorder(line);
		subPanel[1].setLayout(new GridLayout(0, 3, 0, 0));

		// �޴� ����
		for (int i = 0; i < jlb.length; i++) {
			subPanel[1].add(subPanel[i + 2]);
			subPanel[i + 2].setBorder(line);
			System.out.println(AccountManager.account.size());
			
			if(AccountManager.account.size() > i) {
				name = AccountManager.account.get(i + 1).getPName();
				price = AccountManager.account.get(i + 1).getPprice();
				gName = "��ǰ�� : " + name;
				gPrice = "���� : " + price;
			}else {
				name = "";
				price = 0;
				gName = "";
				gPrice = "";
			}
			System.out.println("��ư �߰���");
			jlb[i][0] = new JButton();
			jlb[i][0].setBounds(12, 10, 190, 123);
			subPanel[i + 2].add(jlb[i][0]);
			
			jlb[i][1] = new JButton(gName);
			jlb[i][1].setBounds(214, 10, 169, 33);
			subPanel[i + 2].add(jlb[i][1]);
			
			jlb[i][2] = new JButton(gPrice);
			jlb[i][2].setBounds(214, 81, 169, 33);
			subPanel[i + 2].add(jlb[i][2]);
		}
		// �޴� ���� ����
		jlb[0][0].setIcon(ramen);
		jlb[1][0].setIcon(ddug);
		jlb[2][0].setIcon(ice);
		jlb[3][0].setIcon(coca);
		jlb[4][0].setIcon(sand);
		jlb[5][0].setIcon(hotbar);

		// jsp.setBounds(1600, 10, 17, 538);
		mainPanel.add(jsp);
		// jsp.setPreferredSize(new Dimension(400, 200));

	}
}
