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
	private JMenu file = new JMenu("����(f)");
	private JMenu setting = new JMenu("��ǰ(g)");
	
	private JMenuItem exit = new JMenuItem("����");
	
	private JMenuItem view = new JMenuItem("����");
	private JMenuItem add = new JMenuItem("�߰�");
	private JMenuItem remove = new JMenuItem("����");
	private JMenuItem fix = new JMenuItem("����");
	
	private JButton[] btList = new JButton[4]; 
	private JButton calculate = new JButton("����");
	private JButton management = new JButton("��ǰ ����");
	
	private JLabel jlbJari = new JLabel("1 �� �ڸ�");
	private JLabel jlbUserId = new JLabel("���̵� : kim");
	private JLabel jlbUserName = new JLabel("�̸� : min");
	
	private JLabel jlbUserStartT = new JLabel("���� �ð� : 13��30��");
	private JLabel jlbUserUseT = new JLabel("��� �ð� : 00��40��");
	private JLabel jlbUserPrice = new JLabel("���� ��� : 1000��");
	
	
	
	private Border line = BorderFactory.createLineBorder(Color.BLACK,3);
	private Font font = new Font("",Font.BOLD,30);
	
	
	public CounterFrame() {
		btset();
		this.display();
		this.event();
		this.menu();
		
		System.out.println(btList.length);
		this.setSize(1280, 800);
		this.setTitle("KG PC�� - ������");
//		this.setLocation(100, 200);
		//��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(true);
		//��ܺκ��� ������ �ʵ��� ����
//		this.setUndecorated(true);
		this.setResizable(true);
		this.setVisible(true);
	}
	

	private void btset() {
		for(int i = 0; i < 4 ; i++) {
			btList[i] = new JButton();
			btList[i].setLayout(new BorderLayout());
			btList[i].add(new JLabel((i+1) + "��"),BorderLayout.NORTH);
			btList[i].add(new JLabel("�� �ڸ�"),BorderLayout.CENTER);
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
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//â ����
		
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
		//mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.darkGray);
		
		//�¼� �ǳ�
		subPanel.setBounds(203, 20, 304, 441);
		mainPanel.add(subPanel);
		
		//�¼� ��ư �߰�
		subPanel.setLayout(new GridLayout(2, 2));
		for(int i = 0 ; i < 4 ; i++) {
			subPanel.add(btList[i]);
		}
		
		//ȸ�� ���� �ǳ�
		subPanel1.setBounds(0, 20, 189, 441);
		mainPanel.add(subPanel1);
		subPanel1.setLayout(null);
		subPanel1.setBackground(Color.darkGray);
		subPanel1.setBorder(line);
		
		//���� ��ư ��� 
		calculate.setBounds(40, 362, 102, 69);
		subPanel1.add(calculate);
		
		//�¼� ǥ�� ��
		subPanel2.setBounds(12, 10, 165, 69);
		subPanel1.add(subPanel2);
		subPanel2.setLayout(null);
		jlbJari.setBounds(12, 10, 141, 54);
		subPanel2.add(jlbJari);
		subPanel2.setBackground(Color.darkGray);
		subPanel2.setBorder(line);
		
		//ȸ�� ���� �ǳ�
		subPanel3.setBounds(12, 89, 165, 95);
		subPanel1.add(subPanel3);
		subPanel3.setLayout(null);
		subPanel3.setBackground(Color.darkGray);
		subPanel3.setBorder(line);
		
		//���� id ��
		jlbUserId.setBounds(18, 19, 129,29);
		subPanel3.add(jlbUserId);
		
		//���� name ��
		jlbUserName.setBounds(18, 56, 129, 29);
		subPanel3.add(jlbUserName);
		
		//ȸ�� �ð� �ǳ�
		subPanel4.setBounds(12, 206, 165, 146);
		subPanel1.add(subPanel4);
		subPanel4.setLayout(null);
		subPanel4.setBackground(Color.darkGray);
		subPanel4.setBorder(line);
		
		//���� �ð� ��
		jlbUserStartT.setBounds(12, 20, 141, 28);
		subPanel4.add(jlbUserStartT);
		
		//��� �ð� ��
		jlbUserUseT.setBounds(12, 58, 141, 35);
		subPanel4.add(jlbUserUseT);
		
		//���� ���
		jlbUserPrice.setBounds(12, 103, 141, 33);
		subPanel4.add(jlbUserPrice);
		
		//��Ʈ, ���� ����
		jlbJari.setFont(font);
		jlbJari.setForeground(Color.BLACK);
		
	}	
}
