package sign;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import Client.ClientManager;
import header.Header;

public class Wait extends JFrame{

	private JPanel mainPanel = new JPanel();
	
	private JLabel id = new JLabel("���̵�",JLabel.CENTER);
	private JLabel pw = new JLabel("��й�ȣ",JLabel.CENTER);
	private JLabel card = new JLabel("ī���ȣ",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea cardInput = new JTextArea();
	
	private JButton rogin = new JButton("�α���");
	private JButton signUp = new JButton("ȸ������");
	private JButton charger = new JButton("������");

	private String pcNum;
	
	ClientManager cmg = new ClientManager();
	
	public Wait(String num){
		pcNum = num;
		this.display();
		this.event();
		
		this.setTitle(num+"�� �ڸ� ��� ȭ��");
		this.setSize(500,400);
		this.setLocation(1000, 250);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new GridLayout(5,2));

		mainPanel.add(id);
		mainPanel.add(idInput);
		mainPanel.add(pw);
		mainPanel.add(pwInput);
		mainPanel.add(card);
		mainPanel.add(cardInput);
		mainPanel.add(rogin);
		mainPanel.add(signUp);
		mainPanel.add(charger);

		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		id.setBorder(line);
		idInput.setBorder(line);
		pw.setBorder(line);
		pwInput.setBorder(line);
		card.setBorder(line);
		cardInput.setBorder(line);
	}

	private void event() {
		
		signUp.addActionListener(e->{
			//ȸ������ â ����
			Sign sign = new Sign();
		});
		charger.addActionListener(e->{
			Charger charger = new Charger();
		});
		
		rogin.addActionListener(e->{
			Date today = new Date();
			SimpleDateFormat exit22 = new SimpleDateFormat("HH");
			SimpleDateFormat adult = new SimpleDateFormat("yyyy");
			int timer = Integer.parseInt(exit22.format(today));//16��
			int year = Integer.parseInt(adult.format(today));//2018��
			//���̵� ��� ������
			cmg.connect();
			cmg.headerSend(Header.LOGIN);
			cmg.send(idInput.getText());
			cmg.send(pwInput.getText());
			//�α��� �������� ������ ������ ����, Ʋ���� �޽��� ���
			if(cmg.receive()) {
				//���̸� �޾Ƽ� �ð�üũ
				String birth = cmg.strReceive();
				int age = year + 1 - Integer.parseInt(adult.format(birth));//2018-1993=25
				System.out.println(age);
				if(age<20 && (timer>=17 || timer<=8)) {
					JOptionPane.showMessageDialog(mainPanel, "�̼����� �̿� �Ұ� �ð��Դϴ�");
					cmg.send("-1");
				}
				else {
					cmg.send(pcNum);
					int time = cmg.plusReceive();
					if(time<60)
						JOptionPane.showMessageDialog(mainPanel, "�ð��� �����ϰ� ������");
					else {
//						cmg.exit();
						Login login = new Login(pcNum,idInput.getText(),time,age);					
						dispose();
					}
				}
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "Ʋ��.");
		});
	}
}