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
	public static String[] userIdUse = new String[60];

	public static void time(String id, int time) {
		btlb[Integer.parseInt(FileManager.getUserPCNum(id)) - 120][3].setText(
				"�ð� : " + FileManager.getUserTime(id) / 3600 + " : " + FileManager.getUserTime(id) % 3600 / 60);
	}

	private JPanel mainPanel = new JPanel();
	private JPanel subPanel = new JPanel();
	private JPanel subPanel1 = new JPanel();
	private JPanel subPanel2 = new JPanel();
	private JPanel subPanel3 = new JPanel();
	private JPanel subPanel4 = new JPanel();
	private JPanel goodsView = new JPanel();

	private JMenuBar jmb = new JMenuBar();
	private JMenu file = new JMenu("����(f)");
	private JMenu setting = new JMenu("��ǰ(g)");

	private JMenuItem exit = new JMenuItem("����");

	private JMenuItem view = new JMenuItem("����");

	private JMenuItem gSave = new JMenuItem("��ǰ ����");

	// �ڸ� ǥ�� ��ư / �ڸ� ��ư �� �ؽ�Ʈ
	private JButton[] btList = new JButton[60];
	public static JLabel[][] btlb = new JLabel[60][6];

	// �ֹ� ���� �߰� ��ư
	private JButton[] btGList = new JButton[20];
	private List<String> orderList = new ArrayList<>();

	private JButton bExit = new JButton("����");

	private JButton msg = new JButton("�ֹ� �߰�");

	private JButton calculate = new JButton("����");
	private JButton management = new JButton("��ǰ ����");

	private JLabel jlbJari = new JLabel("�ڸ� ����", JLabel.CENTER);
	private JLabel jlbUserId = new JLabel("���̵� : ");
	private JLabel jlbUserName = new JLabel("�̸� : ");

	private JLabel jlbUserStartT = new JLabel("���� �ð� : ");
	private JLabel jlbUserUseT = new JLabel("��� �ð� : ");
	private JLabel jlbUserPrice = new JLabel("");

	private Border line = BorderFactory.createLineBorder(Color.BLACK, 3);

	private int myTestCnt = 1;

	private Dimension win = Toolkit.getDefaultToolkit().getScreenSize();

	private List<String> userId;

	private SimpleDateFormat f = new SimpleDateFormat("hh : mm");

	// ȸ�� ���̵� �ڸ����� ����
	private String[] uId = new String[60];

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
		this.setTitle("KG PC�� - ������");
		// this.setLocation(100, 200);
		// ��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(false);
		// ��ܺκ��� ������ �ʵ��� ����
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
			btlb[i][0] = new JLabel((i + 120) + "��");
			btlb[i][0].setBounds(5, 5, 50, 15);
			btList[i].add(btlb[i][0]);
			btlb[i][1] = new JLabel("");
			btlb[i][1].setBounds(45, 0, 200, 200);
			btList[i].add(btlb[i][1]);
			btlb[i][2] = new JLabel("");
			btlb[i][2].setBounds(5, 20, 100, 50);
			btList[i].add(btlb[i][2]);
			btlb[i][3] = new JLabel("");
			btlb[i][3].setFont(new Font("", Font.PLAIN, 12));
			btlb[i][3].setBounds(5, 40, 100, 50);
			btList[i].add(btlb[i][3]);
			// ȸ�� ���̵� ����
			btlb[i][4] = new JLabel();
			// ���� �ð� ����
			btlb[i][5] = new JLabel();
		}

		for (int i = 0; i < btGList.length; i++) {
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
		// �ֹ� ���� �ʱ�ȭ
		for (JButton j : btGList) {
			j.setText("");
		}

		// �ֹ� ���� �ٽ� �޾ƿ�
		for (int l = 0; l < orderList.size(); l++) {
			btGList[l].setText(orderList.get(l));
		}
	}

	@SuppressWarnings("null")
	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����

		gSave.addActionListener(e -> {
			for (int Pid : AccountManager.account.keySet()) {
				AccountManager.saveDB(Pid, AccountManager.account.get(Pid));
			}
		});

		bExit.addActionListener(e -> {

			System.exit(0);
		});

		msg.addActionListener(e -> {
			orderList.add((myTestCnt++) + "�� �ֹ�");
			this.orderReset();
		});

		calculate.addActionListener(e -> {
			AccountFrame afr = new AccountFrame();
		});

		// ��ư ������ �����ʿ� ȸ�� ���� ǥ��
		ActionListener btls = (e) -> {
			System.out.println(e.getSource());
			for (int i = 0; i < btList.length; i++) {
				if (btList[i] == e.getSource()) {
					jlbUserId.setText("���̵� : " + uId[i]);
					jlbUserName.setText(btlb[i][2].getText());
					jlbUserStartT.setText("���� �ð� : " + btlb[i][5].getText());
					jlbUserUseT.setText(btlb[i][3].getText());
					jlbUserPrice.setText("��� �ݾ� : " + FileManager.getUserMoney(uId[i]));
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
					int uc = JOptionPane.showConfirmDialog(mainPanel, "�ֹ� ������ ���� �Ͻðڽ��ϱ�?", "�ֹ� ���� ����",
							JOptionPane.YES_NO_OPTION);
					// ���õ� �ֹ� ����
					if (uc == 0) {
						for (int j = 0; j < btGList.length; j++) {
							if (btGList[j] == e.getSource()) {
								btGList[j].setText("");
								orderList.remove(j);
								this.orderReset();
								JOptionPane.showMessageDialog(mainPanel, "�ֹ� ������ �����Ͽ����ϴ�.");
								break;
							}
						}
					}

				} else {
					JOptionPane.showMessageDialog(mainPanel, "�ֹ� ������ �����ϴ�.");
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
						userId = ServerManager.idList;
						// String idU = ;
						// mainReset();
						for (int i = 0; i < btList.length; i++) {
							if (seatCheck[i] && useCheck[i]) {

								// ó�� �α��� �� ��쿡�� ���� �ڸ� �迭�� �߰�
								uId[i] = userIdUse[i];
								btlb[i][5].setText(f.format(new Date()));
								btlb[i][4].setText(userIdUse[i]);
								btlb[i][1].setText("�����");
								btlb[i][2].setText("�̸� : " + FileManager.getUserName(userIdUse[i]));
								btlb[i][3].setText("���� �ð� : " + FileManager.getUserTime(userIdUse[i]) / 3600 + " : "
										+ FileManager.getUserTime(userIdUse[i]) % 3600 / 60);
								btList[i].setBackground(Color.pink);

								useCheck[i] = false;
							}

							if (orderCheck) {
								System.out.println(orderId.size());
								for (int k = 0; k < orderId.size(); k++) {
									orderList.add(FileManager.getUserPCNum(orderId.get(k)) + " �� "
											+ AccountManager.getPName(orderInfo.get(k * 2)) + " / "
											+ orderInfo.get(k * 2 + 1) + " / " + f.format(new Date()));
									orderCheck = false;
									orderReset();
								}
								orderId.removeAll(orderId);
								orderInfo.removeAll(orderInfo);
							}
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
						}
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		// ȭ�� ����
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
		// mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.darkGray);

		// �¼� �ǳ�
		subPanel.setBounds(203, 20, win.width - 500, win.height - 20);
		mainPanel.add(subPanel);

		// �¼� ��ư �߰�
		subPanel.setLayout(new GridLayout(0, 10, 0, 0));
		for (int i = 0; i < btList.length; i++) {
			subPanel.add(btList[i]);
		}

		// ȸ�� ���� �ǳ�
		subPanel1.setBounds(0, 20, 189, 376);
		mainPanel.add(subPanel1);
		subPanel1.setLayout(null);
		subPanel1.setBackground(Color.darkGray);
		subPanel1.setBorder(line);

		// �¼� ǥ�� ��
//		subPanel2.setBounds(12, 10, 165, 69);
//		subPanel1.add(subPanel2);
//		subPanel2.setLayout(null);
//		jlbJari.setBounds(12, 10, 141, 54);
//		subPanel2.add(jlbJari);
//		subPanel2.setBackground(Color.DARK_GRAY);
//		subPanel2.setBorder(line);

		// ȸ�� ���� �ǳ�
		subPanel3.setBounds(12, 50, 165, 95);
		subPanel1.add(subPanel3);
		subPanel3.setLayout(null);
		subPanel3.setBackground(Color.darkGray);
		subPanel3.setBorder(line);

		// ���� id ��
		jlbUserId.setBounds(18, 19, 129, 29);
		subPanel3.add(jlbUserId);

		// ���� name ��
		jlbUserName.setBounds(18, 56, 129, 29);
		subPanel3.add(jlbUserName);

		// ȸ�� �ð� �ǳ�
		subPanel4.setBounds(12, 206, 165, 146);
		subPanel1.add(subPanel4);
		subPanel4.setLayout(null);
		subPanel4.setBackground(Color.darkGray);
		subPanel4.setBorder(line);

		// ���� �ð� ��
		jlbUserStartT.setBounds(12, 20, 141, 28);
		subPanel4.add(jlbUserStartT);

		// ��� �ð� ��
		jlbUserUseT.setBounds(12, 58, 141, 35);
		subPanel4.add(jlbUserUseT);

		// ���� ��� ��
		jlbUserPrice.setBounds(12, 103, 141, 33);
		subPanel4.add(jlbUserPrice);

		// ���� ��ư ���
		calculate.setBounds(12, 500, 160, 60);
		mainPanel.add(calculate);

		// ��ǰ ���� ��ư ���
		management.setBounds(12, 600, 160, 60);
		mainPanel.add(management);

		// ���� ��ư ���
		bExit.setBounds(12, 700, 160, 60);
		mainPanel.add(bExit);

		// ��Ʈ, ���� ����
		jlbJari.setFont(new Font("", Font.BOLD, 18));
		jlbJari.setForeground(Color.BLACK);
		jlbUserName.setForeground(Color.black);
		jlbUserId.setForeground(Color.BLACK);
		jlbUserStartT.setForeground(Color.black);
		jlbUserUseT.setForeground(Color.black);
		jlbUserPrice.setForeground(Color.black);

		// ��ǰ �ֹ���Ȳ �ǳ� �߰�
		goodsView.setLayout(new GridLayout(0, 1, 0, 0));
		goodsView.setBounds(1315, 20, 275, 800);
		mainPanel.add(goodsView);

		// �ֹ� ���� ��ư �߰�
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
