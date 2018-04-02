package sign;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
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
	
	private Panel p = new Panel();
	
	private JLabel id = new JLabel("���̵�",JLabel.CENTER);
	private JLabel pw = new JLabel("��й�ȣ",JLabel.CENTER);
	private JLabel card = new JLabel("ī���ȣ",JLabel.CENTER);
	private JLabel pcNum = new JLabel("�ڸ���ȣ",JLabel.CENTER);
	private JLabel pcNumInput = new JLabel("",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea cardInput = new JTextArea();
	
	private JButton rogin = new JButton("�α���");
	private JButton signUp = new JButton("ȸ������");
	private JButton charger = new JButton("������");

	ClientManager cmg = new ClientManager();

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	double height = screenSize.getHeight();
	
	public Wait(String num){
		this.display();
		this.event(num);
		
		this.setTitle(num+"�� �ڸ� ��� ȭ��");
		this.setSize((int)width,(int)height);
//		this.setLocation(1000, 250);
		this.setLocation(0, 0);
		this.setUndecorated(true);//��ܹ� ���ֱ�
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//�׻� ����
	}
	
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(p, BorderLayout.EAST);
		
		p.setLayout(new GridLayout(6,2));
		p.add(pcNum);
		p.add(pcNumInput);
		p.add(id);
		p.add(idInput);
		p.add(pw);
		p.add(pwInput);
		p.add(card);
		p.add(cardInput);
		p.add(rogin);
		p.add(signUp);
		p.add(charger);
		
//		mainPanel.setLayout(new GridLayout(5,2));
//		mainPanel.add(id);
//		mainPanel.add(idInput);
//		mainPanel.add(pw);
//		mainPanel.add(pwInput);
//		mainPanel.add(card);
//		mainPanel.add(cardInput);
//		mainPanel.add(rogin);
//		mainPanel.add(signUp);
//		mainPanel.add(charger);

		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		pcNum.setBorder(line);
		pcNumInput.setBorder(line);
		id.setBorder(line);
		idInput.setBorder(line);
		pw.setBorder(line);
		pwInput.setBorder(line);
		card.setBorder(line);
		cardInput.setBorder(line);
	}

	private void event(String num) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pcNumInput.setText(num);
		
		signUp.addActionListener(e->{
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
				int age = year + 1 - (Integer.parseInt(cmg.strReceive())/10000);//2018-1993=25
				if(age<20 && (timer>=22 || timer<=8)) {
					JOptionPane.showMessageDialog(mainPanel, "�̼����� �̿� �Ұ� �ð��Դϴ�");
					cmg.send("-1");
				}
				else {
					cmg.send(num);
					int time = cmg.plusReceive();
					if(time<60)
						JOptionPane.showMessageDialog(mainPanel, "�ð��� �����ϰ� ������");
					else {
						Login login = new Login(num,idInput.getText(),time,age);					
						dispose();
					}
				}
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "Ʋ��.");
		});
	}
}