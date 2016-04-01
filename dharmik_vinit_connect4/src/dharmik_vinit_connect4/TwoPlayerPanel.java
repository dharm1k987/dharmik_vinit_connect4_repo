package dharmik_vinit_connect4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * This class is used when the user wants to play against another user.
 * @author Dharmik, Vinit
 * @version 1.0
 */
public class TwoPlayerPanel extends JPanel implements ActionListener {
	private int rows = 7;
	private int columns = 7;
	private Board board;
	private JButton[] buttons;
	private JLabel[][] lblChips;
	private boolean winner = false;
	private ImageIcon redChip;
	private ImageIcon yellowChip;		
	private ImageIcon redTurnGuide;
	private ImageIcon yellowTurnGuide;
	private Border border;

	public TwoPlayerPanel() {

		setPreferredSize(new Dimension(500, 600));
		setLayout(new GridLayout(rows + 1, columns));

		board = new Board(rows, columns);
		board.setUpTextBoard();
		border = BorderFactory.createLineBorder(Color.BLACK, 3);
		

		redChip = new ImageIcon("red-chip.png");
		yellowChip = new ImageIcon("yellow-chip.png");		
		redTurnGuide = new ImageIcon("color-guide-red.png");
		yellowTurnGuide = new ImageIcon("color-guide-yellow.png");
		createGUIBoard();
		
	}

	/**
	 * Method: Creates a GUI board in the panel.
	 */
	private void createGUIBoard() {
		buttons = new JButton[columns];
		lblChips = new JLabel[rows][columns];
		for (int i = 0; i < columns; i++) {
			buttons[i] = new JButton();			
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setIcon(redTurnGuide);
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
	
		setTurnGuides();
		if (board.isDraw()) {
		
			JOptionPane.showMessageDialog(null, "Draw!");
			disableAllButtons();
		}
		if (winner) {
		
			JOptionPane.showMessageDialog(null, "The winner is " + board.getWinner());
			disableAllButtons();
		}
	}

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
		int posX = board.getPosX();
		int posY = board.getPosY();
		if (board.getTurn() % 2 == 0) {
			lblChips[posX][posY].setIcon(yellowChip);
		} else {
			lblChips[posX][posY].setIcon(redChip);
		}
		
	}

	/**
	 * Method: Changes color to allow the user to know who's turn (red, yellow).
	 */
	private void setTurnGuides() {
		int turn = board.getTurn();
		ImageIcon iconToChoose;
		if (turn % 2 == 0) {
			iconToChoose = redTurnGuide;
		} else {
			iconToChoose = yellowTurnGuide;
		}

		for (JButton i : buttons) {
			i.setIcon(iconToChoose);
		}

	}
	
	/**
	 * Method: Disables the appropriate button according to whichever column is full.
	 */
	private void checkValidColumn() {
		int columnToRemove = board.checkValidColumn(buttons);
			if (columnToRemove != -1) {				
			buttons[columnToRemove].setEnabled(false);
		}

	}
	
	
	
}
