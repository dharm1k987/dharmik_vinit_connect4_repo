package dharmik_vinit_connect4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class TwoPlayerPanel extends JPanel implements ActionListener {
	private int rows = 7;
	private int columns = 7;
	private Board board;
	private JButton[] buttons;
	private JLabel[][] lblChips;
	private boolean winner = false;
	private ImageIcon redChip;
	private ImageIcon blueChip;	
	Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

	public TwoPlayerPanel() {

		setPreferredSize(new Dimension(500, 600));
		setLayout(new GridLayout(rows + 1, columns));

		board = new Board(rows, columns);
		board.setUpTextBoard();

		createGUIBoard();

		redChip = new ImageIcon("red-chip.png");
		blueChip = new ImageIcon("blue-chip.png");
		
	}

	private void createGUIBoard() {
		buttons = new JButton[columns];
		lblChips = new JLabel[rows][columns];
		for (int i = 0; i < columns; i++) {
			buttons[i] = new JButton(String.valueOf(i));
			buttons[i].setFont(new Font("Arial", Font.BOLD, 30));
			buttons[i].setForeground(Color.WHITE);
			buttons[i].setBackground(Color.RED);
			buttons[i].addActionListener(this);
			add(buttons[i]);
			;
		}

		for (int i = 0; i < rows; i++) {
			for (int x = 0; x < columns; x++) {
				lblChips[i][x] = new JLabel();				
				lblChips[i][x].setBorder(border);
				

				add(lblChips[i][x]);

			}
		}

	}

	@Override
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

		checkValidColumn();
		board.printTextArray();
		winner = board.checkWinner();
		updateGUI();
		setTurnGuides();
		if (board.isDraw()) {
			JOptionPane.showMessageDialog(null, "Draw!");
		}
		if (winner) {
			JOptionPane.showMessageDialog(null, "The winner is " + board.getWinner());
			disableAllButtons();
		}
	}

	private void disableAllButtons() {
		for (JButton i : buttons) {
			i.setEnabled(false);
		}

	}

	private void updateGUI() {
		int posX = board.getPosX();
		int posY = board.getPosY();
		if (board.getTurn() % 2 == 0) {
			lblChips[posX][posY].setIcon(blueChip);
		} else {
			lblChips[posX][posY].setIcon(redChip);
		}
		
	}

	private void setTurnGuides() {
		int turn = board.getTurn();
		Color colorToFill;
		if (turn % 2 == 0) {
			colorToFill = Color.RED;
		} else {
			colorToFill = Color.BLUE;
		}

		for (JButton i : buttons) {
			i.setBackground(colorToFill);
		}

	}

	private void checkValidColumn() {
		int columnToRemove = board.checkValidColumn(buttons);
			if (columnToRemove != -1) {
			buttons[columnToRemove].setEnabled(false);
		}

	}
}
