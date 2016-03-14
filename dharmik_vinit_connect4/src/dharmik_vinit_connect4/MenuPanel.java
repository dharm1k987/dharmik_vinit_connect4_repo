package dharmik_vinit_connect4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public  class MenuPanel extends JPanel implements ActionListener {

	private JButton onePlayerBtn;
	private JButton twoPlayerBtn;
	private JButton helpBtn;
	
	public MenuPanel() {
		this.onePlayerBtn = new JButton("One Player Mode");
		this.twoPlayerBtn = new JButton("Two Player Mode");
		this.helpBtn = new JButton("Help");
		
		
		add(onePlayerBtn);
		add(twoPlayerBtn);
		add(helpBtn);
		
		onePlayerBtn.addActionListener(this);
		twoPlayerBtn.addActionListener(this);
		helpBtn.addActionListener(this);
		
	}
	
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == onePlayerBtn) {
			JFrame frame2 = new JFrame("Connect 4");
			frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame2.getContentPane().add(new OnePlayerPanel());
			frame2.pack();
			frame2.setVisible(true);
			frame2.setResizable(false);
			
		}
		if (e.getSource() == twoPlayerBtn) {
			JFrame frame2 = new JFrame("Connect 4");
			frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame2.getContentPane().add(new TwoPlayerPanel());
			frame2.pack();
			frame2.setVisible(true);
			frame2.setResizable(false);
		}
	}

	
}
