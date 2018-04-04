package client;

import java.awt.Color;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import db.Member;
import header.Header;

public class Sign extends JFrame{
	private JPanel mainPanel = new JPanel();
	
	private JLabel name = new JLabel("ÀÌ¸§");
	private JLabel id = new JLabel("¾ÆÀÌµð(3~14)");
	private JLabel pw = new JLabel("ºñ¹Ð¹øÈ£(6~20)");
	private JLabel pwCheck = new JLabel("ºñ¹Ð¹øÈ£ È®ÀÎ");
	private JLabel birth = new JLabel("»ý³â¿ùÀÏ(8ÀÚ¸®)");

	private JTextArea nameInput = new JTextArea();
	private JTextArea idInput = new JTextArea();
	private JTextArea pwInput = new JTextArea();
	private JTextArea pwCheckInput = new JTextArea();
	private JTextArea birthInput = new JTextArea();
	
	private JButton ok = new JButton("È®ÀÎ");
	private JButton no = new JButton("Ãë¼Ò");
	private JButton overlap = new JButton("Áßº¹ È®ÀÎ");
	
	ClientManager cmg = new ClientManager();
	
	boolean overlapCheck = false;
	
	//Á¤±ÔÇ¥Çö½Ä
	private String idRgx = "^[a-z][0-9a-zA-Z]{2,13}$$";
	private String pwRgx = "^[0-9a-zA-z]{6,20}$";
	private String birthRgx = "^[0-9]{8}$";
	private String nameRgx = "^[°¡-ÆR]{2,15}$";
	
	
	
	public Sign(){
		cmg.connect();
		cmg.headerSend(Header.SIGNUP);
		this.display();
		this.event();
		
		this.setTitle("È¸¿ø°¡ÀÔ");
		this.setSize(420, 370);
		this.setLocation(500, 150);
		this.setResizable(false);
		this.setVisible(true);
		this.setAlwaysOnTop(true);//Ç×»ó À§¿¡
	}
	private void display() {
		this.setContentPane(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.add(name).setBounds(10,0,120,40);
		mainPanel.add(nameInput).setBounds(130,10,150,40);
		mainPanel.add(id).setBounds(10,50,120,40);
		mainPanel.add(idInput).setBounds(130,60,150,40);
		mainPanel.add(pw).setBounds(10,100,120,40);
		mainPanel.add(pwInput).setBounds(130,110,150,40);
		mainPanel.add(pwCheck).setBounds(10,150,120,40);
		mainPanel.add(pwCheckInput).setBounds(130,160,150,40);
		mainPanel.add(birth).setBounds(10,200,120,40);
		mainPanel.add(birthInput).setBounds(130,210,150,40);
		mainPanel.add(overlap).setBounds(300,65,90,30);
		mainPanel.add(ok).setBounds(100,280,90,30);
		mainPanel.add(no).setBounds(200,280,90,30);
		
		Border line = BorderFactory.createLineBorder(Color.BLACK,1);
		nameInput.setBorder(line);
		idInput.setBorder(line);
		pwInput.setBorder(line);
		pwCheckInput.setBorder(line);
		birthInput.setBorder(line);
	}

	private void event() {
		overlap.addActionListener(e->{
			//Áßº¹È®ÀÎ
			//È¸¿ødb¿¡ °°Àº ¾ÆÀÌµð°¡ µî·ÏµÇ¾îÀÖ´ÂÁö È®ÀÎÈÄ ¸Þ½ÃÁö Ãâ·Â
			String str=idInput.getText();
			if(Pattern.matches(idRgx, str)) {
				cmg.send(str);//¾ÆÀÌµð º¸³»°í
				overlapCheck = cmg.receive();//³Î°ª ¹Þ°í
				if(overlapCheck) {
					JOptionPane.showMessageDialog(mainPanel, "»ç¿ë °¡´ÉÇÑ ¾ÆÀÌµðÀÔ´Ï´Ù.");
					overlap.setEnabled(false);
				}
				else
					JOptionPane.showMessageDialog(mainPanel, "ÀÌ¹Ì Á¸ÀçÇÏ´Â ¾ÆÀÌµðÀÔ´Ï´Ù.");
			}
			else
				JOptionPane.showMessageDialog(mainPanel, "¾ÆÀÌµð´Â 3~14±ÛÀÚ¸¸ °¡´ÉÇÕ´Ï´Ù");
		});
		
		ok.addActionListener(e->{
			//ok¹öÆ° ´©¸£¸é ³ª¸ÓÁö Á¤º¸¸¦ ¼­¹ö¿¡ ´Ù º¸³¿
			//ºñ¹Ð¹øÈ£ = ºñ¹Ð¹øÈ£ È®ÀÎ °Ë»ç
			//È¸¿ø°¡ÀÔÀÌ ¿Ï·á‰ç´ÂÁö ³Î°ª ¹Þ¾Æ¼­ È®ÀÎÃ¢ ¶ç¿öÁÜ
			if(!Pattern.matches(nameRgx, nameInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "ÀÌ¸§À» ÀÔ·ÂÇÏ¼¼¿ä");
			else if(!overlapCheck)
				JOptionPane.showMessageDialog(mainPanel, "¾ÆÀÌµð Áßº¹È®ÀÎÀ» ÇØÁÖ¼¼¿ä");
			else if(!Pattern.matches(pwRgx, pwInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "ºñ¹Ð¹øÈ£´Â 6~20±ÛÀÚ¸¸ °¡´ÉÇÕ´Ï´Ù");
			else if(!pwInput.getText().equals(pwCheckInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "ºñ¹Ð¹øÈ£¸¦ ´Ù½Ã È®ÀÎÇØÁÖ¼¼¿ä");
			else if(!Pattern.matches(birthRgx, birthInput.getText()))
				JOptionPane.showMessageDialog(mainPanel, "»ý³â¿ùÀÏÀÌ Àß¸ø ÀÔ·ÂµÆ½À´Ï´Ù");
			else {
				Member m = new Member(pwInput.getText(),nameInput.getText(),birthInput.getText());
				cmg.memberSend(m);
				dispose();
			}
		});
		
		no.addActionListener(e->{
			dispose();
		});
	}
}