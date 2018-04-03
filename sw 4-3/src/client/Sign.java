package client;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import db.Member;
import header.Header;

public class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel name = new JLabel("�̸�",JLabel.CENTER);
	private JLabel id = new JLabel("���̵�(3~14)",JLabel.CENTER);
	private JLabel pw = new JLabel("��й�ȣ(6~20)",JLabel.CENTER);
	private JLabel pwCheck = new JLabel("��й�ȣ Ȯ��",JLabel.CENTER);
	private JLabel birth = new JLabel("�������(8�ڸ�)",JLabel.CENTER);
	
	private JTextArea nameInput = new JTextArea();
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea pwCheckInput = new JTextArea();
	private JTextArea birthInput = new JTextArea();
	
	private JButton ok = new JButton("Ȯ��");
	private JButton no = new JButton("���");
	private JButton overlap = new JButton("�ߺ� Ȯ��");
	
	ClientManager cmg = new ClientManager();
	
	boolean overlapCheck = false;
	
	public Sign(){
		cmg.connect();
		cmg.headerSend(Header.SIGNUP);
		this.display();
		this.event();
		
		this.setTitle("ȸ������");
		this.setSize(500, 400);
		this.setLocation(500, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//�׻� ����
	}
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(7,2));
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
		
		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		name.setBorder(line);
		nameInput.setBorder(line);
		id.setBorder(line);
		idInput.setBorder(line);
		pw.setBorder(line);
		pwInput.setBorder(line);
		pwCheck.setBorder(line);
		pwCheckInput.setBorder(line);
		birth.setBorder(line);
		birthInput.setBorder(line);
	}

	private void event() {
		overlap.addActionListener(e->{
			//�ߺ�Ȯ��
			//ȸ��db�� ���� ���̵� ��ϵǾ��ִ��� Ȯ���� �޽��� ���
			String str=idInput.getText();
			if(str.length()>=3 && str.length()<=14) {
				cmg.send(str);//���̵� ������
				overlapCheck = cmg.receive();//�ΰ� �ް�
				if(overlapCheck) {
					JOptionPane.showMessageDialog(mainPanel, "��� ������ ���̵��Դϴ�.");
					overlap.setEnabled(false);
				}
				else
					JOptionPane.showMessageDialog(mainPanel, "�̹� �����ϴ� ���̵��Դϴ�.");
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "���̵�� 3~14���ڸ� �����մϴ�");
		});
		
		ok.addActionListener(e->{
			//ok��ư ������ ������ ������ ������ �� ����
			//��й�ȣ = ��й�ȣ Ȯ�� �˻�
			//ȸ�������� �Ϸ����� �ΰ� �޾Ƽ� Ȯ��â �����
			if(nameInput.getText().equals(""))
				JOptionPane.showMessageDialog(mainPanel, "�̸��� �Է��ϼ���");
			else if(!overlapCheck)
				JOptionPane.showMessageDialog(mainPanel, "���̵� �ߺ�Ȯ���� ���ּ���");
			else if(pwInput.getText().length()<6 || pwInput.getText().length()>20)
				JOptionPane.showMessageDialog(mainPanel, "��й�ȣ�� 6~20���ڸ� �����մϴ�");
			else if(!pwInput.getText().equals(pwCheckInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "��й�ȣ�� �ٽ� Ȯ�����ּ���");
			else if(birthInput.getText().length()!=8)
				JOptionPane.showMessageDialog(mainPanel, "��������� �߸� �Էµƽ��ϴ�");
			else {
				Member m = new Member(pwInput.getText(),nameInput.getText(),birthInput.getText());
				cmg.memberSend(m);
				dispose();
			}
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}