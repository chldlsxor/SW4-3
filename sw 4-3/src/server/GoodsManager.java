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

	private JLabel jlbtitle = new JLabel("��ǰ ����", JLabel.CENTER);

	private JLabel[][] jlb = new JLabel[18][3];

	private JMenuBar jmb = new JMenuBar();

	private JMenuItem add = new JMenuItem("�߰�", JMenuItem.CENTER);
	private JMenuItem remove = new JMenuItem("����", JMenuItem.CENTER);
	private JMenuItem fix = new JMenuItem("����", JMenuItem.CENTER);
	private JMenuItem reset = new JMenuItem("���ΰ�ħ", JMenuItem.CENTER);

	private Font tFont = new Font("", Font.BOLD, 30);

	private Border line = BorderFactory.createLineBorder(Color.black, 3);

	private FileDialog fileOpen = new FileDialog(this, "���� �ҷ�����", FileDialog.LOAD);

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
		this.setTitle("�޴���");
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
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����

		add.addActionListener(e -> {
			AddManager amg = new AddManager(this,true);
			System.out.println("dd");
			
			this.menuReset();
			this.menuSet();
			this.revalidate();
			this.repaint();
		});

		reset.addActionListener(e -> {
			System.out.println("���ΰ�ħ");
			this.menuReset();
			this.menuSet();
			this.revalidate();
			this.repaint();
		});

		remove.addActionListener(e -> {
			String gNum = JOptionPane.showInputDialog("������ ��ǰ ��ȣ �Է�");
			try {
				int size = AccountManager.account.size();
				AccountManager.account.remove(Integer.parseInt(gNum));
				int nSize = AccountManager.account.size();
				// System.out.println(AccountManager.account.size());
				// System.out.println(size);
				// ��� ������ ����
				for (int i = Integer.parseInt(gNum); i < AccountManager.account.size(); i++) {
					AccountManager.account.put(i, AccountManager.account.get(i + 1));
				}

				// ��� �޺κ� ����
				for (int i = size; i > nSize; i--) {
					System.out.println("�޺κл���");
					AccountManager.account.remove(i);
				}
				this.menuReset();
				this.menuSet();
				this.revalidate();
				this.repaint();
				JOptionPane.showMessageDialog(this, "��ǰ�� �����Ǿ����ϴ�.");
			} catch (Exception err) {
				JOptionPane.showMessageDialog(this, "�� ���� ��ǰ ��ȣ�Դϴ�.");
			}
		});

		fix.addActionListener(e -> {
			String uNum = JOptionPane.showInputDialog("������ ��ǰ ��ȣ �Է�");
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
					gName = "��ǰ�� : " + name;
					gPrice = "���� : " + price;

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
		// mainPanel�� �⺻ �гη� ����
		this.setLayout(null);
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(1400, 5000));
		mainPanel.setBackground(Color.DARK_GRAY);

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
		this.menuSet();

		jsp.setBounds(0, 0, 200,300);
		this.add(jsp);

	}

}
