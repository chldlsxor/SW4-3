package server;
import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

class Win01 extends JFrame{
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
	public Win01() {
		this.display();//화면 구성 관련 처리
		this.event();//이벤트 관련 처리
		this.menu();//메뉴 관련 처리
		
		this.setTitle("회계");
		this.setSize(500, 400);
//		this.setLocation(100, 100);
		//위치를 운영체제가 결정하도록 하자
		this.setLocationByPlatform(true);
		//상단부분이 나오지 않도록 설정
		//this.setUndecorated(true);
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
			accountData[i][1] = Integer.toString(AccountManager.getPPrice(i));//제품 금액
			accountData[i][2] = Integer.toString(AccountManager.getSellNum(i));//제품 판매량
			accountData[i][3] =Integer.toString(AccountManager.calcMoney(i));//제품  판매 금액
		}
		accountData[row][0] = "총합계";
		accountData[row][2] = Integer.toString(AccountManager.totalSellNum());
		accountData[row][3] = Integer.toString(AccountManager.totalPrice());
		
		table = new JTable(accountData,title);
		scroll.setViewportView(table);
		mainPanel.add(scroll, BorderLayout.CENTER);
		
	}

	private void event() {
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	// 프로그램 종료
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 창 종료
//		this.setDefaultCloseOperation(HIDE_ON_CLOSE); 	//창 숨김
		
		//위의 것들이 다 싫을 경우 커스텀 이벤트 설정
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void menu() {
		
	}
}

public class AccountFrame {
	public static void main(String[] args) {
		//스킨 설치 - ???LookAndFeel클래스를 Swing전체에 설정하도록 코드 구현
//		try {
//			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");	//awt에는 없..Swing에만 있..
//			
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//창을 더이상 직접 만들지 않고 확장시킨 클래스의 인스턴스를 생성
		Win01 window = new Win01();
	}
}
