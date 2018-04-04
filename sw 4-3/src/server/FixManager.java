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

	// ±âÁ¸¿¡´Â Component¸¦ Frame¿¡ Á÷Á¢ ¹èÄ¡Çß¾ú´Âµ¥ ÀÌ·¯¸é °ü¸®È¿À²ÀÌ ¶³¾îÁø´Ù.
	// PanelÀ» ¸¸µé¾î¼­ Component¸¦ ¹èÄ¡ÇÒ ¼ö ÀÖµµ·Ï ¼³Á¤ÇÒ ¼ö ÀÖ´Ù(ContentPane)
	private JPanel mainPanel = new JPanel();

	private JButton pic = new JButton();
	private JButton name = new JButton();
	private JButton price = new JButton();
	private JButton add = new JButton("¿Ï·á");
	private JButton cancel = new JButton("Ãë¼Ò");

	private ImageIcon addPic;
	private String addName;
	private String addPrice;

	private FileDialog open = new FileDialog(this, "»çÁø ¼±ÅÃ", FileDialog.LOAD);

	private int num;

	private Boolean picFlag = true;
	private Boolean nameFlag = true;
	private Boolean priceFlag = true;
	private Boolean retFlag = true;

	private String stringRgx = "^[°¡-ÆR]{1,}$";
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
		this.setTitle("»óÇ° ¼öÁ¤");
		// this.setLocation(100, 200);
		// À§Ä¡¸¦ ¿î¿µÃ¼Á¦°¡ °áÁ¤ÇÏµµ·Ï ÇÏÀÚ
		this.setLocationByPlatform(true);
		// »ó´ÜºÎºĞÀÌ ³ª¿ÀÁö ¾Êµµ·Ï ¼³Á¤
		// this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void setting() {
		pic.setIcon(AccountManager.account.get(num).getPIcon());
		name.setText("»óÇ°¸í : " + AccountManager.account.get(num).getPName());
		price.setText("°¡°İ : " + AccountManager.account.get(num).getPprice());

		addPic = AccountManager.account.get(num).getPIcon();
		addName = AccountManager.account.get(num).getPName();
		addPrice = "" + AccountManager.account.get(num).getPprice();
	}

	private void menu() {
		// TODO Auto-generated method stub

	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Ã¢ Á¾·á

		pic.addActionListener(e -> {
			open.setVisible(true);
			if (open.getDirectory() == null && open.getFile() == null) {
				pic.setIcon(null);
				pic.setText("»çÁøÀ» Ãß°¡ÇØ ÁÖ¼¼¿ä");
				picFlag = false;
			} else {
				addPic = new ImageIcon(open.getDirectory() + open.getFile());
				pic.setText("");
				pic.setIcon(addPic);
				picFlag = true;
			}
		});

		name.addActionListener(e -> {
			addName = JOptionPane.showInputDialog("»óÇ° ÀÌ¸§ ÀÔ·Â");
			if (addName == null || addName.equals("")) {
				name.setText("»óÇ° ÀÌ¸§À» Ãß°¡ÇØ ÁÖ¼¼¿ä");
				nameFlag = false;
			} else {
				name.setText("»óÇ°¸í : " + addName);
				nameFlag = true;
			}
		});

		price.addActionListener(e -> {
			addPrice = JOptionPane.showInputDialog("»óÇ° °¡°İ ÀÔ·Â");
			if (Pattern.matches(numRgx, addPrice)) {
				if (addPrice == null || addPrice.equals("")) {
					price.setText("»óÇ° °¡°İÀ» Ãß°¡ÇØ ÁÖ¼¼¿ä");
					priceFlag = false;
				} else {
					price.setText("°¡°İ : " + addPrice);
					priceFlag = true;
				}
			} else {
				JOptionPane.showMessageDialog(mainPanel, "¼ıÀÚ¸¸ ÀÔ·Â °¡´ÉÇÕ´Ï´Ù.");
			}
		});

		add.addActionListener(e -> {
			if (picFlag && nameFlag && priceFlag) {
				retFlag = true;
				System.out.println("in");
				System.out.println(addName);
				AccountManager.account.put(num, new Account(addPic, addName, Integer.parseInt(addPrice)));
				JOptionPane.showMessageDialog(mainPanel, "»óÇ° ¼öÁ¤ ¿Ï·á");
				dispose();
			} else {
				JOptionPane.showMessageDialog(mainPanel, "»óÇ°ÀÇ »çÁø,ÀÌ¸§,°¡°İÀ» ¸ğµÎ ÀÔ·ÂÇØ¾ß ÇÕ´Ï´Ù.");
			}
		});

		cancel.addActionListener(e -> {
			dispose();
		});

	}

	private void display() {
		// TODO Auto-generated method stub
		// mainPanelÀ» ±âº» ÆĞ³Î·Î ¼³Á¤
		this.setContentPane(mainPanel);
		// ¸ğµç ÄÄÆ÷³ÍÆ®ÀÇ Ãß°¡´Â mainPanel¿¡ ¼öÇà

		mainPanel.setLayout(null);
		// »çÁø Ãß°¡ ¹öÆ°
		pic.setBounds(49, 66, 179, 189);
		mainPanel.add(pic);

		// »óÇ° ÀÌ¸§ Ãß°¡ ¹öÆ°
		name.setBounds(308, 66, 255, 60);
		mainPanel.add(name);

		// °¡°İ Ãß°¡ ¹öÆ°
		price.setBounds(308, 195, 255, 60);
		mainPanel.add(price);

		// ¼öÁ¤ ¿Ï·á ¹öÆ°
		add.setBounds(120, 308, 143, 49);
		mainPanel.add(add);

		// Ãë¼Ò ¹öÆ°
		cancel.setBounds(336, 308, 143, 49);
		mainPanel.add(cancel);

	}

}
