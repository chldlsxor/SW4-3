package server;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class SeatManager extends JDialog {

	private JPanel mainPanel = new JPanel();
	
	private JLabel uName = new JLabel();
	private JLabel uBirth = new JLabel();
	private JLabel uPay = new JLabel();
	
	private JTextArea ta = new JTextArea();
	
	private JButton msgSend = new JButton("�޼���");
	private JButton save = new JButton("����");
	private JButton cancel = new JButton("���");
	
	private JButton clientExit = new JButton("��� ����");

	//�ڸ��� ����ϰ� �ִ� ������� ID�� �޾ƿ�
	public SeatManager(String id) {
		this.set(id);
		this.display();
		this.event(id);
		this.menu();

		this.setSize(300, 450);
		this.setTitle("�ڸ� ����");
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	//�ڸ� Ŭ�� �� �ڸ� ���� mainPanel ������ ǥ��
	public void set(String id) {
		uName.setText("��         �� : "+FileManager.getUserName(id));
		uBirth.setText("������� 	: "+FileManager.getUserBirth(id));
		uPay.setText("���ݾ� : "+FileManager.getUserMoney(id));
		ta.setText(FileManager.getUserMemo(id));
	}

	private void menu() {
		// TODO Auto-generated method stub

	}

	private void event(String id) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����
		
		//�޼��� ������
		msgSend.addActionListener(e -> {
			String uMsg = JOptionPane.showInputDialog("�޼��� �Է�");
			ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
			ssm.sendMessage(uMsg);
		});
		
		//����
		cancel.addActionListener(e -> {
			dispose();
		});
		
		//��ǰ db ����
		save.addActionListener(e -> {
			FileManager.setUserMemo(id, ta.getText());
			FileManager.saveDB(id, FileManager.getUserClass(id));
			dispose();
		});
		
		//Ŭ�� ���� ����
		clientExit.addActionListener(e -> {
			int choose = JOptionPane.showConfirmDialog(mainPanel, "��� ���� ��Ű�ڽ��ϱ�?","��� ����",JOptionPane.YES_NO_OPTION);
			if(choose == 0 ) {
				ServerSendManager ssm = new ServerSendManager(FileManager.getUserIP(id));
				ssm.sendShutDownPC();
				dispose();
			}
		});
	}
	

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);

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
		msgSend.setBounds(41, 315, 97, 37);
		mainPanel.add(msgSend);
		
		//��� ���� ��ư �߰�
		clientExit.setBounds(148, 315, 97, 37);
		mainPanel.add(clientExit);
		
		//���� ��ư �߰�
		save.setBounds(41, 369, 97, 37);
		mainPanel.add(save);
		
		//��� ��ư �߰�
		cancel.setBounds(148, 369, 97, 37);
		mainPanel.add(cancel);
		

	}

}
