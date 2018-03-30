package server;

import java.awt.FileDialog;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.Account;

class AddManager extends JDialog {

	// �������� Component�� Frame�� ���� ��ġ�߾��µ� �̷��� ����ȿ���� ��������.
	// Panel�� ���� Component�� ��ġ�� �� �ֵ��� ������ �� �ִ�(ContentPane)
	private JPanel mainPanel = new JPanel();

	private JButton pic = new JButton("������ �߰��� �ּ���");
	private JButton name = new JButton("��ǰ �̸��� �߰��� �ּ���");
	private JButton price = new JButton("��ǰ ������ �߰��� �ּ���");
	private JButton add = new JButton("�Ϸ�");
	private JButton cancel = new JButton("���");

	private ImageIcon addPic;
	private String addName;
	private String addPrice;

	private FileDialog open = new FileDialog(this, "���� ����", FileDialog.LOAD);
	

	private Boolean picFlag = false;
	private Boolean nameFlag = false;
	private Boolean priceFlag = false;
	private Boolean retFlag = false;
	
	public ImageIcon getAddPic() {
		return addPic;
	}
	
	public void setAddPic(ImageIcon addPic) {
		this.addPic = addPic;
	}
	
	public String getAddName() {
		return addName;
	}
	
	public void setAddName(String addName) {
		this.addName = addName;
	}
	
	public String getAddPrice() {
		return addPrice;
	}
	
	public void setAddPrice(String addPrice) {
		this.addPrice = addPrice;
	}
	
	public Boolean getRetFlag() {
		return retFlag;
	}

	public AddManager(JFrame jf,boolean modal) {
		super(jf, modal);
		this.display();
		this.event();
		this.menu();

		this.setSize(630, 430);
		this.setTitle("��ǰ �߰�");
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

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// â ����

		pic.addActionListener(e -> {
			open.setVisible(true);
			if (open.getDirectory() == null && open.getFile() == null) {
				pic.setIcon(null);
				pic.setText("������ �߰��� �ּ���");
				picFlag = false;
			} else {
				addPic = new ImageIcon(open.getDirectory() + open.getFile());
				pic.setText("");
				pic.setIcon(addPic);
				picFlag = true;
			}
		});

		name.addActionListener(e -> {
			addName = JOptionPane.showInputDialog("��ǰ �̸� �Է�");
			if (addName == null || addName.equals("")) {
				name.setText("��ǰ �̸��� �߰��� �ּ���");
				nameFlag = false;
			}else {
				name.setText("��ǰ�� : "+addName);
				nameFlag = true;
			}
		});
		
		price.addActionListener(e ->{
			addPrice = JOptionPane.showInputDialog("��ǰ ���� �Է�");
			if (addPrice == null || addPrice.equals("")) {
				price.setText("��ǰ ������ �߰��� �ּ���");
				priceFlag = false;
			}else {
				price.setText("���� : "+addPrice);
				priceFlag = true;
			}
		});

		add.addActionListener(e ->{
			if(picFlag && nameFlag && priceFlag) {
				retFlag = true;
				AccountManager.account.put(AccountManager.account.size()+1, new Account(addPic,addName,Integer.parseInt(addPrice)));
				JOptionPane.showMessageDialog(mainPanel, "��ǰ �߰� �Ϸ�");
				dispose();
			}else {
				JOptionPane.showMessageDialog(mainPanel, "��ǰ�� ����,�̸�,������ ��� �߰��ؾ� �մϴ�.");
			}
		});
		
		cancel.addActionListener(e -> {
			dispose();
		});
	}

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel�� �⺻ �гη� ����
		this.setContentPane(mainPanel);
		// ��� ������Ʈ�� �߰��� mainPanel�� ����

		mainPanel.setLayout(null);
		// ���� �߰� ��ư
		pic.setBounds(49, 66, 179, 189);
		mainPanel.add(pic);

		// ��ǰ �̸� �߰� ��ư
		name.setBounds(308, 66, 255, 60);
		mainPanel.add(name);

		// ���� �߰� ��ư
		price.setBounds(308, 195, 255, 60);
		mainPanel.add(price);

		// �߰� ��ư
		add.setBounds(120, 308, 143, 49);
		mainPanel.add(add);

		// ��� ��ư
		cancel.setBounds(336, 308, 143, 49);
		mainPanel.add(cancel);

	}

}