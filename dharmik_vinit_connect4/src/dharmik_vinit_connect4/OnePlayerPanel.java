package dharmik_vinit_connect4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class OnePlayerPanel extends JPanel implements ActionListener {

	private int rows = 7;
	private int columns = 7;
	private Board board;
	private JButton[] buttons;
	private JLabel[][] lblChips;
	private boolean winner = false;
	private ImageIcon playerChip;
	private ImageIcon AIChip;
	Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

	private AIcomponent AI;

	public OnePlayerPanel() {

		setPreferredSize(new Dimension(500, 600));
		setLayout(new GridLayout(rows + 1, columns));

		board = new Board(rows, columns);
		board.setUpTextBoard();

		// AI= new AImodule(board);
		AI = new AIcomponent(board);
		createGUIBoard();

		playerChip = new ImageIcon("red-chip.png");
		AIChip = new ImageIcon("yellow-chip.png");
	}

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

		AI.evaluateBoard();
		board.updateTextArray(AI.getAIPosX());

		board.printTextArray();

		updateGUI();
		checkValidColumn();
		setTurnGuides();

		// board.updateTextArray(AI.getAIposX());

		winner = board.checkWinner();
		if (board.isDraw()) {
			JOptionPane.showMessageDialog(null, "Draw!");
		}
		if (winner) {
			JOptionPane.showMessageDialog(null, "The winner is " + board.getWinner());
			disableAllButtons();
		}
		checkValidColumn();
	}

	private void disableAllButtons() {
		for (JButton i : buttons) {
			i.setBackground(Color.DARK_GRAY);
			i.setEnabled(false);
		}

	}

	private void updateGUI() {
		int posX = board.getPosX();
		int posY = board.getPosY();
		/*
		 * if (board.getTurn() % 2 == 0) { lblChips[posX][posY].setIcon(AIChip);
		 * } else { lblChips[posX][posY].setIcon(playerChip); }
		 */
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

	private void setTurnGuides() {

		for (JButton i : buttons) {
			i.setBackground(new Color(255, 0, 0));
		}

	}

	private void checkValidColumn() {
		int columnToRemove = board.checkValidColumn(buttons);
		System.out.println(columnToRemove + " A");
		if (columnToRemove != -1) {
			buttons[columnToRemove].setEnabled(false);
		}

	}

}
