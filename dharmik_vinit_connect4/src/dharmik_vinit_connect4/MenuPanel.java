package dharmik_vinit_connect4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public  class MenuPanel extends JPanel implements ActionListener {

	private JButton onePlayerBtn;
	private JButton twoPlayerBtn;
	private JButton helpBtn;
	private Font btnFont;
	private JLabel background;
	
	public MenuPanel() {
		setPreferredSize(new Dimension(500, 600));
		setLayout(null);		
	
		btnFont = new Font("Tahoma", Font.BOLD, 20);
		
		this.onePlayerBtn = new JButton("One Player");
		this.twoPlayerBtn = new JButton("Two Players");
		this.helpBtn = new JButton("Help");
		
		onePlayerBtn.setFont(btnFont);
		onePlayerBtn.setBounds(38, 430, 196, 62);
		onePlayerBtn.setBackground(Color.WHITE);
		
		twoPlayerBtn.setFont(btnFont);
		twoPlayerBtn.setBounds(274, 430, 196, 62);
		twoPlayerBtn.setBackground(Color.WHITE);
		
		helpBtn.setFont(btnFont);
		helpBtn.setBounds(144, 503, 196, 62);
		helpBtn.setBackground(Color.ORANGE);
		
		onePlayerBtn.setFont(btnFont);
		twoPlayerBtn.setFont(btnFont);
		helpBtn.setFont(btnFont);
	
		background = new JLabel("");
		background.setIcon(new ImageIcon("background.png"));
		background.setBounds(15, 20, 484, 561);
		
		add(onePlayerBtn);
		add(twoPlayerBtn);
		add(helpBtn);
		add(background);

		onePlayerBtn.addActionListener(this);
		twoPlayerBtn.addActionListener(this);
		helpBtn.addActionListener(this);
		
	}
	
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == onePlayerBtn) {
			
			JFrame frame2 = new JFrame("Connect 4 - 1P");
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.getContentPane().add(new OnePlayerPanel());
			frame2.pack();
			frame2.setVisible(true);
			frame2.setResizable(false);
			
		}
		if (e.getSource() == twoPlayerBtn) {
			JFrame frame2 = new JFrame("Connect 4 - 2P");
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.getContentPane().add(new TwoPlayerPanel());
			frame2.pack();
			frame2.setVisible(true);
			frame2.setResizable(false);
		}
		if (e.getSource() == helpBtn)
		{
			JOptionPane.showMessageDialog(null, "Make a 4 in a row, either diagonally, vertically, or horizontally");
		}
	}

	
}
