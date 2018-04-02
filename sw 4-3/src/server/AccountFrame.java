package server;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class Win01 extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel gridPanel = new JPanel();
	
	private Date date = new Date();
	private SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd E����");
	private JLabel label_Date = new JLabel(today.format(date));
	private JTable table;
	private String[] title = {"��ǰ��", "��ǰ����","�Ǹŷ�", "�Ǹűݾ�"};
	private String[][] accountData;
	
	
	private JScrollPane scroll = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//main�� �ϴ� �������� �����ڿ��� ����
	public Win01() {
		this.display();//ȭ�� ���� ���� ó��
		this.event();//�̺�Ʈ ���� ó��
		this.menu();//�޴� ���� ó��
		
		this.setTitle("ȸ��");
		this.setSize(500, 400);
//		this.setLocation(100, 100);
		//��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(true);
		//��ܺκ��� ������ �ʵ��� ����
		//this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	private void display() {
		//mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		
		//��� ������Ʈ�� �߰��� mainPanel���� ����
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(label_Date,BorderLayout.NORTH);	//��¥ ǥ��
			
		int row = AccountManager.account.size();		//������ ũ��
		accountData = new String[row+1][4];				//ǥ ���� ������
		for(int i: AccountManager.account.keySet()) {
			accountData[i][0] = AccountManager.getPName(i);	//��ǰ �̸�
			accountData[i][1] = Integer.toString(AccountManager.getPPrice(i));//��ǰ �ݾ�
			accountData[i][2] = Integer.toString(AccountManager.getSellNum(i));//��ǰ �Ǹŷ�
			accountData[i][3] =Integer.toString(AccountManager.calcMoney(i));//��ǰ  �Ǹ� �ݾ�
		}
		accountData[row][0] = "���հ�";
		accountData[row][2] = Integer.toString(AccountManager.totalSellNum());
		accountData[row][3] = Integer.toString(AccountManager.totalPrice());
		
		table = new JTable(accountData,title);
		scroll.setViewportView(table);
		mainPanel.add(scroll, BorderLayout.CENTER);
		
	}

	private void event() {
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	// ���α׷� ����
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����
//		this.setDefaultCloseOperation(HIDE_ON_CLOSE); 	//â ����
		
		//���� �͵��� �� ���� ��� Ŀ���� �̺�Ʈ ����
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void menu() {
		
	}
}

public class AccountFrame {
	public static void main(String[] args) {
		//��Ų ��ġ - ???LookAndFeelŬ������ Swing��ü�� �����ϵ��� �ڵ� ����
//		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");	//awt���� ��..Swing���� ��..
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//â�� ���̻� ���� ������ �ʰ� Ȯ���Ų Ŭ������ �ν��Ͻ��� ����
		Win01 window = new Win01();
	}
}
