package dharmik_vinit_connect4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

/**
 * Class is a GUI menu, which allows the user to pick the game type (1p/2p).
 * Implements ActionListener.
 * @author Dharmik, Vinit
 * @version 1.0
 */
public  class MenuPanel extends JPanel implements ActionListener {

	private JButton onePlayerBtn;
	private JButton twoPlayerBtn;
	private JButton helpBtn;
	private Font btnFont;
	private JLabel background;
	
	/**
	 * Default constructor. Creates GUI menu.
	 */
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
	
	/**
	 * Action Listener method from ActionListener class handles events.
	 */
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == onePlayerBtn || e.getSource() == twoPlayerBtn) {
			JFrame frame2;
			if (e.getSource() == onePlayerBtn)
			{
			frame2 = new JFrame("Connect 4 - 1P");
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.getContentPane().add(new OnePlayerPanel());
			frame2.pack();
			frame2.setVisible(true);
			frame2.setResizable(false);
			}
			else
			{		frame2 = new JFrame("Connect 4 - 2P");
					frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame2.getContentPane().add(new TwoPlayerPanel());
					frame2.pack();
					frame2.setVisible(true);
					frame2.setResizable(false);
					
					
				}
			frame2.addWindowListener(new WindowAdapter() {
			
		    public void windowClosing(WindowEvent e) {
		        Board.setTurn(0);
		        }		    
			});
		
			
		}
		
		if (e.getSource() == helpBtn)
		{
			JOptionPane.showMessageDialog(null, "Make a 4 in a row, either diagonally, vertically, or horizontally.");
		}
		
		
	}
	
	
	
}
