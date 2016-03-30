package dharmik_vinit_connect4;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * This class is used when the user wants to play against the AI.
 * @author Dharmik, Vinit
 * @version 1.0
 */
public class OnePlayerPanel extends JPanel implements ActionListener {

	private int rows = 7;
	private int columns = 7;
	private Board board;
	private JButton[] buttons;
	private JLabel[][] lblChips;
	private boolean winner = false;
	private ImageIcon playerChip;
	private ImageIcon AIChip;
	private ImageIcon xMark;
	Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

	private AIBot AI;

	/**
	 * Constructor: instantiates a panel object.
	 */
	public OnePlayerPanel() {

		setPreferredSize(new Dimension(500, 600));
		setLayout(new GridLayout(rows + 1, columns));

		board = new Board(rows, columns);
		board.setUpTextBoard();

		AI = new AIBot(board, Color.RED, Color.YELLOW);
		createGUIBoard();

		playerChip = new ImageIcon("red-chip.png");
		AIChip = new ImageIcon("yellow-chip.png");
		xMark = new ImageIcon("x-mark.png");
	}

	
	/**
	 * Method: Creates a GUI board in the panel.
	 */
	private void createGUIBoard() {
		buttons = new JButton[columns];
		lblChips = new JLabel[rows][columns];
		for (int i = 0; i < columns; i++) {
			buttons[i] = new JButton("");
			// buttons[i].setFont(new Font("Courier New", Font.BOLD, 60));
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setBackground(Color.RED);
			buttons[i].addActionListener(this);
			buttons[i].setBorder(border);
			add(buttons[i]);
		}

		for (int i = 0; i < rows; i++) {
			for (int x = 0; x < columns; x++) {
				lblChips[i][x] = new JLabel();
				lblChips[i][x].setBorder(border);

				add(lblChips[i][x]);

			}
		}
	}
	/**
	 * Post: changes the buttons array accordingly.
	 */

	/**
	 * Method: Checks which buttons are pressed in the OnePlayerPanel.
	 */
	public void actionPerformed(ActionEvent e) {
		

		if (e.getSource() == buttons[0]) {
			board.updateTextArray(0);

		}
		if (e.getSource() == buttons[1]) {
			board.updateTextArray(1);
		}
		if (e.getSource() == buttons[2]) {
			board.updateTextArray(2);
		}
		if (e.getSource() == buttons[3]) {
			board.updateTextArray(3);
		}
		if (e.getSource() == buttons[4]) {
			board.updateTextArray(4);
		}
		if (e.getSource() == buttons[5]) {
			board.updateTextArray(5);
		}
		if (e.getSource() == buttons[6]) {
			board.updateTextArray(6);
		}
	
		updateGUI();
		checkValidColumn();
		winner = board.checkWinner();
		if (winner) {
			JOptionPane.showMessageDialog(null, "The winner is " + board.getWinner());
			disableAllButtons();
		}
		else
		{
			AI.evaluateBoard();
			
			board.updateTextArray(AI.getFinalPositionX());

		
			updateGUI();
			
			checkValidColumn();
			//setTurnGuides();

			winner = board.checkWinner();
			if (board.isDraw()) {
				disableAllButtons();
				JOptionPane.showMessageDialog(null, "Draw!");
			}
			if (winner) {
				JOptionPane.showMessageDialog(null, "The winner is " + board.getWinner());
				disableAllButtons();
			}
			
		}
		
	}
	/**
	 * Post: If there is a winner, sets the winner variable to true
	 */


	/**
	 * Method: Disables all the buttons on the panel.
	 */
	private void disableAllButtons() {
		for (JButton i : buttons) {
			i.setBackground(Color.DARK_GRAY);
			i.setEnabled(false);
		}

	}

	/**
	 * Method: Updates the GUI board after a turn is played.
	 */
	private void updateGUI() {

		
		Chip[][] txtbrd = board.getTextBoard();
		for (int i = 0; i < txtbrd.length; i++) {
			for (int j = 0; j < txtbrd.length; j++) {
				if (txtbrd[i][j].getColor().equals(Color.RED)) {
					lblChips[i][j].setIcon(playerChip);
				} else if (txtbrd[i][j].getColor().equals(Color.YELLOW)) {
					lblChips[i][j].setIcon(AIChip);
				}
			}
		}
		
	}

	/**
	 * Method: Disables the appropriate button according to whichever column is full.
	 */
	private void checkValidColumn() {
		int columnToRemove = board.checkValidColumn(buttons);
		System.out.println(columnToRemove + " A");
		if (columnToRemove != -1) {			
			buttons[columnToRemove].setIcon(xMark);
			buttons[columnToRemove].setEnabled(false);
		}

	}
	/**
	 * Post: the buttons array updates by disabling the button whose column has been filled.
	 */

}
