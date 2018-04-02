package sign;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Client.ClientManager;
import db.Account;
import header.Header;

public class Menu extends JFrame {
	private Map<Integer, Account> account = new HashMap<>();
    
	ClientManager cmg = new ClientManager();
	// Ŭ�� �ֹ���
	private JPanel mainPanel = new JPanel();

	private JPanel[] subPanel = new JPanel[83];
	// Ŭ�� ������
	private JButton[] goodsBt = new JButton[81];

	private JPanel gBasket = new JPanel();
	private JButton[] gButton = new JButton[9];
	private JLabel[][] gJlb = new JLabel[9][4];

	private int cnt = 0;
	
	private JPanel gRet = new JPanel();

	private JLabel gRetLb = new JLabel("�� ��� : 0��");
	private JButton gRetBt = new JButton("�ֹ��ϱ�");

	private JLabel[][] jlb = new JLabel[81][4];

	private JButton[] jbt = new JButton[9];

	private Font tFont = new Font("", Font.BOLD, 30);
	private Font rFont = new Font("", Font.BOLD, 20);

	private Border line = BorderFactory.createLineBorder(Color.black, 3);

	private String name;
	private int price;
	private String gName;
	private String gPrice;
	private ImageIcon gIcon;
	private String gNumber;

	private int pageN = 0;
	private String pageC;

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

	public void jbtSet() {
		for (int i = 0; i < jbt.length; i++) {
			jbt[i] = new JButton("" + (i + 1));
		}
	}

	public Menu(String id) {
		readDB();
		this.display(pageN);
		this.event(id);

		this.setSize(1500, 850);
		this.setTitle("�޴���");
		this.setLocation(10,10);
		this.setResizable(false);
		this.setVisible(true);
	}
	 //ȸ���� ��������
	private void readDB() {
		try (ObjectInputStream fin = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("DBFile", "Account.txt"))));){
			account= (Map<Integer, Account>) fin.readObject();
        } catch (Exception e) {
        	account = new HashMap<>();
       }
	}
	
	private void event(String id) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����

		gRetBt.addActionListener(e->{
			if(cnt==0) return;
			cmg.connect();
			cmg.headerSend(Header.ORDER);
			cmg.send(id);
			cmg.intSend(cnt);
			System.out.println(cnt);
			for(int i=0;i<cnt;i++) {
				cmg.intSend(Integer.parseInt(gJlb[i][3].getText()));//�Ľ���Ʈ
				cmg.intSend(Integer.parseInt(gJlb[i][1].getText().substring(5)));//:�������� ©�� �ڿ���
			}
			dispose();
		});
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
		
		for (int i = 0; i < goodsBt.length; i++) {
			int k = i;
			goodsBt[i].addActionListener(e -> {
				String su = JOptionPane.showInputDialog("������ �Է��� �ּ���");
				if(su == null) return;
				for (int j = 0; j < gJlb.length; j++) {
					if(gJlb[j][0].getText().equals(jlb[k][1].getText())) {
						int a = Integer.parseInt(gJlb[j][1].getText().substring(5));
						int b = Integer.parseInt(gJlb[j][2].getText());
						gJlb[j][0].setText("");
						gJlb[j][1].setText("");
						gJlb[j][3].setText("");
						ret = ret - (a*b);
						cnt--;
					} 
					if (gJlb[j][0].getText().equals("")) {
						gJlb[j][0].setText(jlb[k][1].getText());
						gJlb[j][1].setText("���� : "+su);
						int a = Integer.parseInt(su);
						String sPrice = jlb[k][2].getText().substring(5);
						gJlb[j][2].setText(sPrice);
						int b = Integer.parseInt(sPrice);
						System.out.println("a"+a+" b "+b);
						ret = ret + (a*b);
						gRetLb.setText("�� ��� : "+(ret)+"��");
						gJlb[j][3].setText(jlb[k][3].getText());
						System.out.println(gJlb[j][3].getText());
						cnt++;
						break;
					}
				}
			});
		}
		
		for(int i = 0 ; i < gButton.length ; i ++) {
			int j = i;
			gButton[i].addActionListener(e -> {
				int ch = JOptionPane.showConfirmDialog(mainPanel, "�޴��� �����Ͻðڽ��ϱ�?","�ֹ� ���", JOptionPane.YES_NO_OPTION);
				switch(ch) {
				case 0 :
					int a = Integer.parseInt(gJlb[j][1].getText().substring(5));
					int b = Integer.parseInt(gJlb[j][2].getText());
					System.out.println("�ʵ�����? : "+a);
					gJlb[j][0].setText("");
					gJlb[j][1].setText("");
					System.out.println(ret);
					System.out.println("a = "+a);
					System.out.println("b = "+b);
					ret = ret - (a*b);
					System.out.println(ret);
					gRetLb.setText("�� ��� : "+(ret)+"��");
					gJlb[j][3].setText("");
					cnt--;
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
			System.out.println("������-1 : " + n);
			subPanel[1].add(goodsBt[i]);
			goodsBt[i].setBorder(line);
			System.out.println("��ǰ��ȣ : "+i);

			try {
				// if (gm2.account.size() > i) {
				name = account.get(i + 1).getPName();
				System.out.println("��ǰ�� : "+ name);
				price = account.get(i + 1).getPprice();
				gIcon = account.get(i + 1).getPIcon();
				gName = "��ǰ�� : " + name;
				gPrice = "���� : " + price;
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
		// mainPanel�� �⺻ �гη� ����
		this.setLayout(null);
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(1400, 600));
		mainPanel.setBackground(Color.DARK_GRAY);

		// ��ư ���̾� �ʱ�Ȳ
		this.cBtSet();
		// subPanel ���̾� �ʱ�ȭ
		for (int i = 0; i < subPanel.length; i++) {
			subPanel[i] = new JPanel();
			subPanel[i].setLayout(null);
		}
		// �� ����
		this.setJlb();
		// subPanel[1] �׸��� ���̾� ���� -> �޴���
		// subPanel[1].setLayout(new GridLayout(0, 4, 0, 0));

		// ��ǰ ���� ����
		subPanel[0].setBounds(12, 10, 1400, 99);
		mainPanel.add(subPanel[0]);
		subPanel[0].setBorder(line);
		subPanel[0].setLayout(null);

		// ���� ��
		// jlbtitle.setBounds(12, 10, 1400, 79);
		// jlbtitle.setFont(tFont);
		// subPanel[0].add(jlbtitle);
		// ������ ��ư���� ��ü
		this.jbtSet();
		// jbt[0].setBounds(10, 20, 150, 60);
		// subPanel[0].add(jbt[0]);
		for (int i = 0; i < jbt.length; i++) {
			jbt[i].setBounds(10 + (i * 150), 20, 150, 60);
			subPanel[0].add(jbt[i]);
		}

		// �޴��� �ǳ�
		subPanel[1].setBounds(12, 119, 1400, 450);
		mainPanel.add(subPanel[1]);
		subPanel[1].setBorder(line);
		subPanel[1].setLayout(new GridLayout(3, 3));

		// ��ǰ ��ȣ ����
		// this.gNum();

		// �޴� ����
		this.menuSet(pageN);

		// jsp.setBounds(0, 0, 200,300);
		// this.add(jsp);

		// �ֹ�â
		gBasket.setBounds(12, 600, 1200, 200);
		gBasket.setLayout(new GridLayout(0, 3, 0, 0));
		mainPanel.add(gBasket);

		// �ֹ�â�� �� �߰�
		for (int i = 0; i < gButton.length; i++) {
			// �ǳ� �߰�
			gButton[i] = new JButton();
			gBasket.add(gButton[i]);
			gButton[i].setLayout(null);

			// ��ǰ�� ǥ��
			gJlb[i][0] = new JLabel("");
			gJlb[i][0].setBounds(10, 10, 200, 50);
			gButton[i].add(gJlb[i][0]);

			// ����ǥ��
			gJlb[i][1] = new JLabel("", JLabel.RIGHT);
			gJlb[i][1].setBounds(300, 10, 50, 50);
			gButton[i].add(gJlb[i][1]);
			
			//���� ���� ��
			gJlb[i][2] = new JLabel();
			
			//Ű�� ����
			gJlb[i][3] = new JLabel();
		}

		// �ֹ���ư , �ֹ� ���
		gRet.setBounds(1240, 600, 200, 200);
		gRet.setLayout(null);
		mainPanel.add(gRet);

		// �ֹ� ��ư �߰�
		gRetBt.setBounds(10, 100, 180, 90);
		gRet.add(gRetBt);

		// �� ��� �� �߰�
		gRetLb.setBounds(10, 10, 180, 50);
		gRetLb.setFont(rFont);
		gRet.add(gRetLb);
	}
}
