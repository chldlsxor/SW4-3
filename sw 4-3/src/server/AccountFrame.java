package server;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import header.Header;

class AccountFrame extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel subPanel = new JPanel();
	private JPanel gridPanel = new JPanel();
	private JPanel graphPanel = new JPanel();
	
	private Date date = new Date();
	private JButton graphBtn= new JButton("�׷��� ����");
	private SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd E����");
	private JLabel label_Date = new JLabel(today.format(date));
	private JTable table;
	private String[] title = {"��ǰ��", "��ǰ����","�Ǹŷ�", "�Ǹűݾ�"};
	private String[][] accountData;
	
	private ChartManager cm = new ChartManager();
	private JFreeChart chart = cm.getChart();
	private ChartPanel cp = new ChartPanel(chart);
	
	private JScrollPane scroll = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JScrollPane scroll2 = new JScrollPane(cp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	//main�� �ϴ� �������� �����ڿ��� ����
	public AccountFrame() {
		this.display();//ȭ�� ���� ���� ó��
		this.event();//�̺�Ʈ ���� ó��
		this.menu();//�޴� ���� ó��
		
		this.setTitle("ȸ��");
		this.setSize(500, 400);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	private void display() {
		//mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		
		//��� ������Ʈ�� �߰��� mainPanel���� ����	
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(subPanel,BorderLayout.NORTH);	//��¥ ǥ��
		subPanel.setLayout(new BorderLayout());
		subPanel.add(label_Date,BorderLayout.CENTER);
		subPanel.add(graphBtn,BorderLayout.EAST);
		
		int row = AccountManager.account.size();		//������ ũ��
		
		accountData = new String[row+1][4];				//ǥ ���� ������
		for(int i: AccountManager.account.keySet()) {
			accountData[i][0] = AccountManager.getPName(i);	//��ǰ �̸�
			if(i==Header.PCID) {
				accountData[i][3] =Integer.toString(AccountManager.getTotalPCPrice());//PC�ݾ�
			}
			else {				
				accountData[i][1] = Integer.toString(AccountManager.getPPrice(i));//��ǰ �ݾ�
				accountData[i][2] = Integer.toString(AccountManager.getSellNum(i));//��ǰ �Ǹŷ�
				accountData[i][3] =Integer.toString(AccountManager.calcMoney(i));//��ǰ  �Ǹ� �ݾ�

			}		
		}
		accountData[row][0] = "���հ�";
		accountData[row][2] = Integer.toString(AccountManager.totalSellNum());
		accountData[row][3] = Integer.toString(AccountManager.totalPrice());
		
		DefaultTableModel dtm = new DefaultTableModel(accountData, title) {	//������ ���� ���� �Ұ�
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(dtm);		
		table.getTableHeader().setReorderingAllowed(false);	//�� �̵��Ұ�
		table.getTableHeader().setResizingAllowed(false);	//ũ������ �Ұ�
		scroll.setViewportView(table);
		mainPanel.add(scroll, BorderLayout.CENTER);
		
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����
		
		//�׷��� ��ư -> �׷��� ���
		graphBtn.addActionListener(e->{
			
			JFrame cf=new JFrame("�׷��� ����");
			cf.setContentPane(scroll2);

			cf.setSize(800,400); 
			cf.setVisible(true);
		});
	}

	private void menu() {
		
	}
}
