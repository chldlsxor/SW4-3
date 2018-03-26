package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

<<<<<<< HEAD
=======
import Client.ClientManager;
>>>>>>> refs/remotes/origin/chldlsxor
import db.Member;

class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel name = new JLabel("�̸�",JLabel.CENTER);
	private JLabel id = new JLabel("���̵�",JLabel.CENTER);
	private JLabel pw = new JLabel("��й�ȣ",JLabel.CENTER);
	private JLabel pwCheck = new JLabel("��й�ȣ Ȯ��",JLabel.CENTER);
	private JLabel birth = new JLabel("�ֹε�Ϲ�ȣ ���ڸ�",JLabel.CENTER);
	
	private JTextArea nameInput = new JTextArea();
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea pwCheckInput = new JTextArea();
	private JTextArea birthInput = new JTextArea();
	
	private JButton ok = new JButton("Ȯ��");
	private JButton no = new JButton("���");
	private JButton overlap = new JButton("�ߺ� Ȯ��");
	
	ClientManager cmg = new ClientManager();
	
	public Sign(){
		cmg.connect();
		
		this.display();
		this.event();
		
		this.setTitle("ȸ������");
		this.setSize(500, 400);
		
//		this.setLocation(100, 100);
		//��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(true);
		
		//��� �κ��� ������ �ʵ��� ����
//		this.setUndecorated(true);
		
		this.setResizable(false);
		this.setVisible(true);
	}
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(7,2));
		//��� ������Ʈ�� �߰��� mainPanel�� ����	
//		name.setBounds(100,100,100,100);
//		nameInput.setBounds(1000, 1000, 1000, 1000);
		mainPanel.add(name);
		mainPanel.add(nameInput);
		mainPanel.add(id);
		mainPanel.add(idInput);
		mainPanel.add(pw);
		mainPanel.add(pwInput);
		mainPanel.add(pwCheck);
		mainPanel.add(pwCheckInput);
		mainPanel.add(birth);
		mainPanel.add(birthInput);
		mainPanel.add(ok);
		mainPanel.add(no);
		mainPanel.add(overlap);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//â ����

		overlap.addActionListener(e->{
			//�ߺ�Ȯ��
			//ȸ��db�� ���� ���̵� ��ϵǾ��ִ��� Ȯ���� �޽��� ���
			String str=idInput.getText();
			cmg.send(str);//���̵� ������
			if(cmg.receive())// �ΰ� �޾Ƽ�
				JOptionPane.showMessageDialog(mainPanel, "��� ������ ���̵��Դϴ�.");
			else
				JOptionPane.showMessageDialog(mainPanel, "�̹� �����ϴ� ���̵��Դϴ�.");
		});
		
		ok.addActionListener(e->{
			//ok��ư ������ ������ ������ ������ �� ����
			//��й�ȣ = ��й�ȣ Ȯ�� �˻�
			Member m = new Member(pwInput.getText(),nameInput.getText(),birthInput.getText());
			if(pwInput.getText().equals(pwCheck.getText())) {
				cmg.memberSend(m);
				dispose();
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "��й�ȣ,��й�ȣ Ȯ�� �ٸ�");
			//ȸ�������� �Ϸ����� �ΰ� �޾Ƽ� Ȯ��â �����
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}