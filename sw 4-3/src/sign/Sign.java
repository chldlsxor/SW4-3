package sign;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import db.Member;

class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel lb1 = new JLabel("�̸�",JLabel.CENTER);
	private JLabel lb2 = new JLabel("���̵�",JLabel.CENTER);
	private JLabel lb3 = new JLabel("��й�ȣ",JLabel.CENTER);
	private JLabel lb4 = new JLabel("��й�ȣ Ȯ��",JLabel.CENTER);
	private JLabel lb5 = new JLabel("�ֹε�Ϲ�ȣ ���ڸ�",JLabel.CENTER);
	
	private JTextArea jta1 = new JTextArea();
	private JTextArea jta2 = new JTextArea();
	private JTextArea jta3 = new JTextArea();
	private JTextArea jta4 = new JTextArea();
	private JTextArea jta5 = new JTextArea();
	
	private JButton ok = new JButton("Ȯ��");
	private JButton no = new JButton("���");
	private JButton overlap = new JButton("�ߺ� Ȯ��");
	
	public Sign(){
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
//		lb1.setBounds(100,100,100,100);
//		jta1.setBounds(1000, 1000, 1000, 1000);
		mainPanel.add(lb1);
		mainPanel.add(jta1);
		mainPanel.add(lb2);
		mainPanel.add(jta2);
		mainPanel.add(lb3);
		mainPanel.add(jta3);
		mainPanel.add(lb4);
		mainPanel.add(jta4);
		mainPanel.add(lb5);
		mainPanel.add(jta5);
		mainPanel.add(ok);
		mainPanel.add(no);
		mainPanel.add(overlap);
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//â ����

		overlap.addActionListener(e->{
			//�ߺ�Ȯ��
			//ȸ��db�� ���� ���̵� ��ϵǾ��ִ��� Ȯ���� �޽��� ���
			String str=jta2.getText();
			//cmg.send(str);	//���̵� ������
			// �ΰ� �޾Ƽ�
			//�����ϴ��� �޽��� ���
			System.out.println(str);
			JOptionPane.showMessageDialog(mainPanel, "�̹� �����ϴ� ���̵��Դϴ�.");
		});
		
		ok.addActionListener(e->{
			//ok��ư ������ ������ ������ ������ �� ����
			//��й�ȣ = ��й�ȣ Ȯ�� �˻�
			Member m = new Member(jta3.getText(),jta1.getText(),jta5.getText());
			//cmg.send(m);
			dispose();
			//ȸ�������� �Ϸ����� �ΰ� �޾Ƽ� Ȯ��â �����
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}