package server;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class OrderManager extends JFrame{
	
	// �������� Component�� Frame�� ���� ��ġ�߾��µ� �̷��� ����ȿ���� ��������.
	// Panel�� ���� Component�� ��ġ�� �� �ֵ��� ������ �� �ִ�(ContentPane)
	private JPanel mainPanel = new JPanel();
	
	//<html> ���� <br> ����...<br></html>
	//<br> �ٹٲ�
	//<html> ����
	//</html> ����
	private JLabel jtf = new JLabel();

	private JButton cancel = new JButton("Ȯ��");
	
	public OrderManager(String title) {
		super();
		this.display();
		this.event();
		this.menu();
		
		this.setSize(300, 450);
		this.setTitle(title);
//		this.setLocation(100, 200);
		//��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(true);
		//��ܺκ��� ������ �ʵ��� ����
//		this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void menu() {
		// TODO Auto-generated method stub
		
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);//â ����

		cancel.addActionListener(e -> {
			dispose();
		});
		
	}

	private void display() {
		// TODO Auto-generated method stub
		//mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		//��� ������Ʈ�� �߰��� mainPanel�� ����
		
		//�ֹ� ���� �ؽ�Ʈ �ʵ�
		jtf.setBounds(12, 10, 266, 349);
		mainPanel.add(jtf);
		
		//��� ��ư
		cancel.setBounds(67, 369, 150, 37);
		mainPanel.add(cancel);
	}
	
	
	
	
}

//http://bisuanytime.blogspot.kr/2016/10/java-mvc-jlist.html -> ����Ʈ ����� (jlist ����)