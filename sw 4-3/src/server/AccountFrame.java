package server;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import header.Header;

class AccountFrame extends JFrame{
	private JPanel mainPanel = new JPanel();
	private JPanel gridPanel = new JPanel();
	
	private Date date = new Date();
	private SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd E요일");
	private JLabel label_Date = new JLabel(today.format(date));
	private JTable table;
	private String[] title = {"제품명", "제품가격","판매량", "판매금액"};
	private String[][] accountData;
	
	
	private JScrollPane scroll = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//main에 하던 설정들을 생성자에서 진행
	public AccountFrame() {
		this.display();//화면 구성 관련 처리
		this.event();//이벤트 관련 처리
		this.menu();//메뉴 관련 처리
		
		this.setTitle("회계");
		this.setSize(500, 400);
		this.setLocationByPlatform(true);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	private void display() {
		//mainPanel을 기본 패널로 설정
		this.setContentPane(mainPanel);
		
		//모든 컴포넌트의 추가는 mainPanel에서 진행	
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(label_Date,BorderLayout.NORTH);	//날짜 표시
			
		int row = AccountManager.account.size();		//데이터 크기
		
		accountData = new String[row+1][4];				//표 내용 데이터
		for(int i: AccountManager.account.keySet()) {
			accountData[i][0] = AccountManager.getPName(i);	//제품 이름
			if(i==Header.PCID) {
				accountData[i][3] =Integer.toString(AccountManager.getTotalPCPrice());//PC금액
			}
			else {				
				accountData[i][1] = Integer.toString(AccountManager.getPPrice(i));//제품 금액
				accountData[i][2] = Integer.toString(AccountManager.getSellNum(i));//제품 판매량
				accountData[i][3] =Integer.toString(AccountManager.calcMoney(i));//제품  판매 금액

			}		
		}
		accountData[row][0] = "총합계";
		accountData[row][2] = Integer.toString(AccountManager.totalSellNum());
		accountData[row][3] = Integer.toString(AccountManager.totalPrice());
		
		DefaultTableModel dtm = new DefaultTableModel(accountData, title) {	//데이터 내용 수정 불가
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(dtm);		
		table.getTableHeader().setReorderingAllowed(false);	//열 이동불가
		table.getTableHeader().setResizingAllowed(false);	//크기조절 불가
		scroll.setViewportView(table);
		mainPanel.add(scroll, BorderLayout.CENTER);
		
	}

	private void event() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료
	}

	private void menu() {
		
	}
}
