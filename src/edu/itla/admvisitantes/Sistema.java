package edu.itla.admvisitantes;

import javax.swing.UIManager;

import edu.itla.admvisitantes.iu.login.Login;

public class Sistema {
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Login().setVisible(true);
	}
}
