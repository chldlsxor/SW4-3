package server;

import java.awt.FileDialog;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import db.Account;

class FixManager extends JDialog {

	// �������� Component�� Frame�� ���� ��ġ�߾��µ� �̷��� ����ȿ���� ��������.
	// Panel�� ���� Component�� ��ġ�� �� �ֵ��� ������ �� �ִ�(ContentPane)
	private JPanel mainPanel = new JPanel();

	private JButton pic = new JButton();
	private JButton name = new JButton();
	private JButton price = new JButton();
	private JButton add = new JButton("�Ϸ�");
	private JButton cancel = new JButton("���");

	private ImageIcon addPic;
	private String addName;
	private String addPrice;

	private FileDialog open = new FileDialog(this, "���� ����", FileDialog.LOAD);

	private int num;

	private Boolean picFlag = true;
	private Boolean nameFlag = true;
	private Boolean priceFlag = true;
	private Boolean retFlag = true;

	private String stringRgx = "^[��-�R]{1,}$";
	private String numRgx = "^[0-9]{1,}$";

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

	public FixManager(int num, JFrame jf, Boolean b) {
		super(jf, b);
		this.num = num;
		this.setting();

		this.display();
		this.event();
		this.menu();

		this.setSize(630, 430);
		this.setTitle("��ǰ ����");
		// this.setLocation(100, 200);
		// ��ġ�� �ü���� �����ϵ��� ����
		this.setLocationByPlatform(true);
		// ��ܺκ��� ������ �ʵ��� ����
		// this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void setting() {
		pic.setIcon(AccountManager.account.get(num).getPIcon());
		name.setText("��ǰ�� : " + AccountManager.account.get(num).getPName());
		price.setText("���� : " + AccountManager.account.get(num).getPprice());

		addPic = AccountManager.account.get(num).getPIcon();
		addName = AccountManager.account.get(num).getPName();
		addPrice = "" + AccountManager.account.get(num).getPprice();
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
			} else {
				name.setText("��ǰ�� : " + addName);
				nameFlag = true;
			}
		});

		price.addActionListener(e -> {
			addPrice = JOptionPane.showInputDialog("��ǰ ���� �Է�");
			if (Pattern.matches(numRgx, addPrice)) {
				if (addPrice == null || addPrice.equals("")) {
					price.setText("��ǰ ������ �߰��� �ּ���");
					priceFlag = false;
				} else {
					price.setText("���� : " + addPrice);
					priceFlag = true;
				}
			} else {
				JOptionPane.showMessageDialog(mainPanel, "���ڸ� �Է� �����մϴ�.");
			}
		});

		add.addActionListener(e -> {
			if (picFlag && nameFlag && priceFlag) {
				retFlag = true;
				System.out.println("in");
				System.out.println(addName);
				AccountManager.account.put(num, new Account(addPic, addName, Integer.parseInt(addPrice)));
				JOptionPane.showMessageDialog(mainPanel, "��ǰ ���� �Ϸ�");
				dispose();
			} else {
				JOptionPane.showMessageDialog(mainPanel, "��ǰ�� ����,�̸�,������ ��� �Է��ؾ� �մϴ�.");
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

		// ���� �Ϸ� ��ư
		add.setBounds(120, 308, 143, 49);
		mainPanel.add(add);

		// ��� ��ư
		cancel.setBounds(336, 308, 143, 49);
		mainPanel.add(cancel);

	}

}
