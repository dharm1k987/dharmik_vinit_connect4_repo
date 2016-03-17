package dharmik_vinit_connect4;

import javax.swing.*;
//
public class GUIDriver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Connect 4");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MenuPanel());
		
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

}
