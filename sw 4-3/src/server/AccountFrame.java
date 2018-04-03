package server;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class AccountFrame extends JFrame{
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
	public AccountFrame() {
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
			
		//int row = AccountManager.account.size();		//������ ũ��
//		System.out.println("��ı��ƾƾ�"+row);
//		for(int i :AccountManager.account.keySet()) {
//			System.out.println("---->"+i+"��° "/*+AccountManager.getPName(i)+" "+AccountManager.getSellNum(i)+"  "+AccountManager.calcMoney(i)*/);
//		}
		int rowCount=0;
		for(int i: AccountManager.account.keySet()) {
			if(AccountManager.account.get(i)!=null) {
				rowCount++;
			}		
		}
		
		accountData = new String[rowCount+1][4];				//ǥ ���� ������
		for(int i: AccountManager.account.keySet()) {
			if(AccountManager.account.get(i)!=null) {
				System.out.println("---->"+i+"��° "+AccountManager.getPName(i)+" "+AccountManager.getSellNum(i)+"  "+AccountManager.calcMoney(i));
				accountData[i][0] = AccountManager.getPName(i);	//��ǰ �̸�
				if(i==0) {
					accountData[i][3] =Integer.toString(AccountManager.getTotalPCPrice());//��ǰ  �Ǹ� �ݾ�
				}
				else {				
					accountData[i][1] = Integer.toString(AccountManager.getPPrice(i));//��ǰ �ݾ�
					accountData[i][2] = Integer.toString(AccountManager.getSellNum(i));//��ǰ �Ǹŷ�
					accountData[i][3] =Integer.toString(AccountManager.calcMoney(i));//��ǰ  �Ǹ� �ݾ�
				}
			}		
		}
		accountData[rowCount][0] = "���հ�";
		accountData[rowCount][2] = Integer.toString(AccountManager.totalSellNum());
		accountData[rowCount][3] = Integer.toString(AccountManager.totalPrice());
		DefaultTableModel dtm = new DefaultTableModel(accountData, title) {	//������ ���� ���� �Ұ�
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(dtm);		
		table.getTableHeader().setReorderingAllowed(false);	//�� �̵��Ұ�
		table.getTableHeader().setResizingAllowed(false);	//ũ������ �Ұ�..�־���..?
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
