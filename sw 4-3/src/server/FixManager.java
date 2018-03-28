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

class FixManager extends JDialog {

	// 기존에는 Component를 Frame에 직접 배치했었는데 이러면 관리효율이 떨어진다.
	// Panel을 만들어서 Component를 배치할 수 있도록 설정할 수 있다(ContentPane)
	private JPanel mainPanel = new JPanel();

	private JButton pic = new JButton();
	private JButton name = new JButton();
	private JButton price = new JButton();
	private JButton add = new JButton("완료");
	private JButton cancel = new JButton("취소");

	private ImageIcon addPic;
	private String addName;
	private String addPrice;

	private FileDialog open = new FileDialog(this, "사진 선택", FileDialog.LOAD);
	
	private int num;
	
	private Boolean picFlag = true;
	private Boolean nameFlag = true;
	private Boolean priceFlag = true;
	private Boolean retFlag = true;
	
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

	public FixManager(int num,JFrame jf ,Boolean b) {
		super(jf,b);
		this.num = num;
		this.setting();
		
		this.display();
		this.event();
		this.menu();

		this.setSize(630, 430);
		this.setTitle("상품 수정");
		// this.setLocation(100, 200);
		// 위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(true);
		// 상단부분이 나오지 않도록 설정
		// this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
	}

	
	private void setting() {
		pic.setIcon(AccountManager.account.get(num).getPIcon());
		name.setText("상품명 : "+AccountManager.account.get(num).getPName());
		price.setText("가격 : "+AccountManager.account.get(num).getPprice());
		
		addPic = AccountManager.account.get(num).getPIcon();
		addName = AccountManager.account.get(num).getPName();
		addPrice = ""+AccountManager.account.get(num).getPprice();
	}
	
	private void menu() {
		// TODO Auto-generated method stub

	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료

		pic.addActionListener(e -> {
			open.setVisible(true);
			if (open.getDirectory() == null && open.getFile() == null) {
				pic.setIcon(null);
				pic.setText("사진을 추가해 주세요");
				picFlag = false;
			} else {
				addPic = new ImageIcon(open.getDirectory() + open.getFile());
				pic.setText("");
				pic.setIcon(addPic);
				picFlag = true;
			}
		});

		name.addActionListener(e -> {
			addName = JOptionPane.showInputDialog("상품 이름 입력");
			if (addName == null || addName.equals("")) {
				name.setText("상품 이름을 추가해 주세요");
				nameFlag = false;
			}else {
				name.setText("상품명 : "+addName);
				nameFlag = true;
			}
		});
		
		price.addActionListener(e ->{
			addPrice = JOptionPane.showInputDialog("상품 가격 입력");
			if (addPrice == null || addPrice.equals("")) {
				price.setText("상품 가격을 추가해 주세요");
				priceFlag = false;
			}else {
				price.setText("가격 : "+addPrice);
				priceFlag = true;
			}
		});

		add.addActionListener(e ->{
			if(picFlag && nameFlag && priceFlag) {
				retFlag = true;
				System.out.println("in");
				System.out.println(addName);
				AccountManager.account.put(num, new Account(addPic,addName,Integer.parseInt(addPrice)));
				JOptionPane.showMessageDialog(mainPanel, "상품 수정 완료");
				dispose();
			}else {
				JOptionPane.showMessageDialog(mainPanel, "상품의 사진,이름,가격을 모두 입력해야 합니다.");
			}
		});
		
		cancel.addActionListener(e -> {
			dispose();
		});
		
	}

	private void display() {
		// TODO Auto-generated method stub
		// mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);
		// 모든 컴포넌트의 추가는 mainPanel에 수행

		mainPanel.setLayout(null);
		// 사진 추가 버튼
		pic.setBounds(49, 66, 179, 189);
		mainPanel.add(pic);

		// 상품 이름 추가 버튼
		name.setBounds(308, 66, 255, 60);
		mainPanel.add(name);

		// 가격 추가 버튼
		price.setBounds(308, 195, 255, 60);
		mainPanel.add(price);

		// 수정 완료 버튼
		add.setBounds(120, 308, 143, 49);
		mainPanel.add(add);

		// 취소 버튼
		cancel.setBounds(336, 308, 143, 49);
		mainPanel.add(cancel);

	}

}
