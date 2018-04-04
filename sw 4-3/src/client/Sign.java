package client;

import java.awt.Color;
import java.util.regex.Pattern;

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
	
	private JLabel name = new JLabel("�̸�");
	private JLabel id = new JLabel("���̵�(3~14)");
	private JLabel pw = new JLabel("��й�ȣ(6~20)");
	private JLabel pwCheck = new JLabel("��й�ȣ Ȯ��");
	private JLabel birth = new JLabel("�������(8�ڸ�)");

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
	
	//����ǥ����
	private String idRgx = "^[a-z][0-9a-zA-Z]{2,13}$$";
	private String pwRgx = "^[0-9a-zA-z]{6,20}$";
	private String birthRgx = "^[0-9]{8}$";
	private String nameRgx = "^[��-�R]{2,15}$";
	
	
	
	public Sign(){
		cmg.connect();
		cmg.headerSend(Header.SIGNUP);
		this.display();
		this.event();
		
		this.setTitle("ȸ������");
		this.setSize(420, 370);
		this.setLocation(500, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//�׻� ����
	}
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.add(name).setBounds(10,0,120,40);
		mainPanel.add(nameInput).setBounds(130,10,150,40);
		mainPanel.add(id).setBounds(10,50,120,40);
		mainPanel.add(idInput).setBounds(130,60,150,40);
		mainPanel.add(pw).setBounds(10,100,120,40);
		mainPanel.add(pwInput).setBounds(130,110,150,40);
		mainPanel.add(pwCheck).setBounds(10,150,120,40);
		mainPanel.add(pwCheckInput).setBounds(130,160,150,40);
		mainPanel.add(birth).setBounds(10,200,120,40);
		mainPanel.add(birthInput).setBounds(130,210,150,40);
		mainPanel.add(overlap).setBounds(300,65,90,30);
		mainPanel.add(ok).setBounds(100,280,90,30);
		mainPanel.add(no).setBounds(200,280,90,30);
		
		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		nameInput.setBorder(line);
		idInput.setBorder(line);
		pwInput.setBorder(line);
		pwCheckInput.setBorder(line);
		birthInput.setBorder(line);
	}

	private void event() {
		overlap.addActionListener(e->{
			//�ߺ�Ȯ��
			//ȸ��db�� ���� ���̵� ��ϵǾ��ִ��� Ȯ���� �޽��� ���
			String str=idInput.getText();
			if(Pattern.matches(idRgx, str)) {
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
			if(!Pattern.matches(nameRgx, nameInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "�̸��� �Է��ϼ���");
			else if(!overlapCheck)
				JOptionPane.showMessageDialog(mainPanel, "���̵� �ߺ�Ȯ���� ���ּ���");
			else if(!Pattern.matches(pwRgx, pwInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "��й�ȣ�� 6~20���ڸ� �����մϴ�");
			else if(!pwInput.getText().equals(pwCheckInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "��й�ȣ�� �ٽ� Ȯ�����ּ���");
			else if(!Pattern.matches(birthRgx, birthInput.getText()))
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