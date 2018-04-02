package server;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class SeatManager extends JDialog {

	// �������� Component�� Frame�� ���� ��ġ�߾��µ� �̷��� ����ȿ���� ��������.
	// Panel�� ���� Component�� ��ġ�� �� �ֵ��� ������ �� �ִ�(ContentPane)
	private JPanel mainPanel = new JPanel();
	
	private JLabel uName = new JLabel();
	private JLabel uBirth = new JLabel();
	private JLabel uPay = new JLabel();
	
	private JTextArea ta = new JTextArea();
	
	private JButton msgSend = new JButton("�޼��� ������");
	private JButton save = new JButton("����");
	private JButton cancel = new JButton("���");
	

	public SeatManager(String id) {
		this.set(id);
		this.display();
		this.event(id);
		this.menu();

		this.setSize(300, 450);
		this.setTitle("�ڸ� ����");
		// this.setLocation(100, 200);
		// ��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(true);
		// ��ܺκ��� ������ �ʵ��� ����
		// this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void menu() {
		// TODO Auto-generated method stub

	}

	private void event(String id) {
		// this.setDefaultCloseOperation(EXIT_ON_CLOSE);//���α׷� ����
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����
		// this.setDefaultCloseOperation(HIDE_ON_CLOSE);//â����
		// ���� �͵��� �� ���� ��� Ŀ���� �̺�Ʈ ����
		// this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//�⺻ �̺�Ʈ ����
		
		msgSend.addActionListener(e -> {
			String uMsg = JOptionPane.showInputDialog("�޼��� �Է�");
			JOptionPane.showMessageDialog(this, FileManager.getUserIP(id)+"");
			ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
			ssm.sendMessage(uMsg);
		});
		
		cancel.addActionListener(e -> {
			dispose();
		});
		
		save.addActionListener(e -> {
			FileManager.saveDB(id, FileManager.map.get(id));
		});
		

	}
	
	public void set(String id) {
		uName.setText("��         �� : "+FileManager.getUserName(id));
		uBirth.setText("������� 	: "+FileManager.getUserBirth(id));
		uPay.setText("���ݾ� : ");
		ta.setText(FileManager.map.get(id).getMemo());
	}

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);

		// ��� ������Ʈ�� �߰��� mainPanel�� ����
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.DARK_GRAY);
		
		//����� �̸� ǥ�� ��
		uName.setBounds(12, 10, 266, 32);
		uName.setForeground(Color.WHITE);
		mainPanel.add(uName);
		
		//����� ������� ǥ�� ��
		uBirth.setBounds(12, 52, 266, 32);
		uBirth.setForeground(Color.WHITE);
		mainPanel.add(uBirth);
		
		//����� ���ݾ� ǥ�� ��
		uPay.setBounds(12, 94, 266, 32);
		uPay.setForeground(Color.WHITE);
		mainPanel.add(uPay);
		
		//�޸� �κ� �߰�
		ta.setBounds(12, 136, 266, 169);
		mainPanel.add(ta);
		
		//�޼��� ������ ��ư �߰�
		msgSend.setBounds(41, 315, 204, 44);
		mainPanel.add(msgSend);
		
		//���� ��ư �߰�
		save.setBounds(41, 369, 97, 37);
		mainPanel.add(save);
		
		//��� ��ư �߰�
		cancel.setBounds(148, 369, 97, 37);
		mainPanel.add(cancel);
		

	}

}
