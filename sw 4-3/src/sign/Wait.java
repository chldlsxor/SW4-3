package sign;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import Client.ClientManager;
import header.Header;

public class Wait extends JFrame{

	private JPanel mainPanel = new JPanel();
	
	private Panel p = new Panel();
	
	private JLabel id = new JLabel("���̵�",JLabel.CENTER);
	private JLabel pw = new JLabel("��й�ȣ",JLabel.CENTER);
	private JLabel pcNum = new JLabel("�ڸ���ȣ",JLabel.CENTER);
	private JLabel pcNumInput = new JLabel("",JLabel.CENTER);
	
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	
	private JButton rogin = new JButton("�α���");
	private JButton signUp = new JButton("ȸ������");
	private JButton charger = new JButton("������");
	private JButton exit = new JButton("���α׷� ����");
	
	private JLabel display = new JLabel();
	private ImageIcon i1 = new ImageIcon("image/1600x900.jpg");
	
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
//		mainPanel.setLayout(new BorderLayout());
//		mainPanel.add(p, BorderLayout.EAST);
		mainPanel.setLayout(null);
		mainPanel.add(p);
		mainPanel.add(display);
		display.setIcon(i1);
		display.setBounds(0, 0, (int)width, (int)height);
		p.setBounds((int)width*6/10, (int)height*3/10, (int)width*3/10, (int)height*5/10);
		
		p.setLayout(new GridLayout(5,2));
		p.add(pcNum);
		p.add(pcNumInput);
		p.add(id);
		p.add(idInput);
		p.add(pw);
		p.add(pwInput);
		p.add(rogin);
		p.add(signUp);
		p.add(charger);
		p.add(exit);

		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		pcNum.setBorder(line);
		pcNumInput.setBorder(line);
		id.setBorder(line);
		idInput.setBorder(line);
		pw.setBorder(line);
		pwInput.setBorder(line);
	}

	private void event(String num) {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.ALT_DOWN_MASK);
		Action altF4Action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(mainPanel, "��Ʈf4 ��������");
			}
		};
		mainPanel.getInputMap().put(altF4, "f4");
		mainPanel.getActionMap().put("f4", altF4Action);
		rogin.getInputMap().put(altF4, "f4");
		rogin.getActionMap().put("f4", altF4Action);
		signUp.getInputMap().put(altF4, "f4");
		signUp.getActionMap().put("f4", altF4Action);
		charger.getInputMap().put(altF4, "f4");
		charger.getActionMap().put("f4", altF4Action);
		idInput.getInputMap().put(altF4, "f4");
		idInput.getActionMap().put("f4", altF4Action);
		pwInput.getInputMap().put(altF4, "f4");
		pwInput.getActionMap().put("f4", altF4Action);
		
		exit.addActionListener(e->{
			System.exit(0);
		});
		
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