package dharmik_vinit_connect4;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

/**
 * Class is the backbone of the GUI board. Board class is text based board.
 * @author Dharmik, Vinit
 *
 */
public class Board {
	private Chip[][] textBoard;
	private int rows;
	private int columns;
	private Color winner;
	private static int turn = 0;
	private int posX;
	private int posY;

	/**
	 * Default constructor.
	 * @param rows - the number rows of the connect 4 board.
	 * @param columns - the number of columns of the connect 4 board.
	 */
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		textBoard = new Chip[rows][columns];
	}

	/**
	 * Method sets up a new 'Board' of type 'Chip'. Adds chips to each spot.
	 */
	public void setUpTextBoard() {
		for (int i = 0; i < rows; i++) {
			for (int x = 0; x < columns; x++) {
				textBoard[i][x] = new Chip();
			}
		}

	}

	/**
	 * Accessor method.
	 * @return turn value (P1 or P2).
	 */
	public int getTurn() {
		return turn;
	}
	
	/**
	 * Mutator method. This method is static because the MenuPanel should be 
	 * able to access it. It is needed to change the turn to 0 (red), when 
	 * the JFrame closes.
	 * @param turn - turn to be set
	 */
	public static void setTurn(int turn)
	{
		Board.turn = turn;
	}

	/**
	 * Updates the text board Array after the user clicks a button.
	 * @param temp - the column they clicked.
	 */
	public void updateTextArray(int temp) {
	
		for (int i = rows - 1; i >= 0; i--) {
			if (textBoard[i][temp].getColor() == Color.BLACK) {
				if (turn%2 == 0) {
					textBoard[i][temp].setColor(Color.RED);
					posX = i;
					posY = temp;
					break;
				} else {
					
					textBoard[i][temp].setColor(Color.YELLOW);
					posX = i;
					posY = temp;
					break;
				}
				

			}
		}
		updateTurn();

	}

	/**
	 * 
	 * @return
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * 
	 * @return
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	public boolean isOccupied(int x) {
		if(textBoard[0][x].equals(Color.RED)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the colour of a specified chip.
	 * @param x - the x array index.
	 * @param y - the y array index.
	 * @return the colour of the chip.
	 */
	public Color getColor(int x, int y) {
		try {
			return textBoard[y][x].getColor();
		} catch (NullPointerException e) {
			return Color.BLACK;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Chip[][] getTextBoard () {
		return textBoard;
	}

	/**
	 * Evaluates text board and checks for a winner.
	 * @return true or false
	 */
	public boolean checkWinner() {
		boolean result = false;
		outerloop: for (int i = rows - 1; i >= 0; i--) {
			for (int x = 0; x < columns; x++) {
				if (x <= 3) {
					result = checkHorizontal(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getColor());
						break outerloop;

					}

				}

				if (i >= 3) {
					result = checkVertical(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getColor());
						break outerloop;
					}
				}

					if (x <= 3 && i <= 3) {
						result = checkDiagonalRight(i, x, result);
						if (result) {
							setWinner(textBoard[i][x].getColor());
							break outerloop;
						}
					}
					if (x >= 3 && i <= 3) {
						result = checkDiagonalLeft(i, x, result);
						if (result) {
							setWinner(textBoard[i][x].getColor());
							break outerloop;
						}
					}

				
			}

		}
		return result;
	}

	/**
	 * Mutator method. Sets the winner (colour).
	 * @param color - the colour who won.
	 */
	private void setWinner(Color color) {
		winner = color;
	}

	/**
	 * Accessor method. 
	 * @return the colour who won.
	 */
	public String getWinner() {
		if (winner == Color.RED) {
			return "Red";
		} else {
			return "Yellow";
		}
	}

	/**
	 * Checks to see if a column is full, in which case it must be disabled.
	 * @param buttons - the GUI array which the user clicks as their column.
	 * @return the column index to be disabled. -1, means none.
	 */
	public int checkValidColumn(JButton[] buttons) {
		int temp = -1;
		for (int i = 0; i < 7; i++) {
			if (textBoard[0][i].getColor() != Color.BLACK && buttons[i].isEnabled() != false) {
				temp = i;
				System.out.println("column to be disabled is "+i+"\n");
				return temp;
			}
		}
		return temp;

	}

	/**
	 * Checks the board to see winner from a left - diagonal perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkDiagonalLeft(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK
				&& textBoard[i][x].getColor() == textBoard[i + 1][x - 1].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 2][x - 2].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 3][x - 3].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Checks the board to see winner from a right - diagonal perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkDiagonalRight(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK
				&& textBoard[i][x].getColor() == textBoard[i + 1][x + 1].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 2][x + 2].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 3][x + 3].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Checks the board to see winner from a vertical perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkVertical(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i - 1][x].getColor()
				&& textBoard[i][x].getColor() == textBoard[i - 2][x].getColor()
				&& textBoard[i][x].getColor() == textBoard[i - 3][x].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Checks the board to see winner from a horizontal perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkHorizontal(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i][x + 1].getColor()
				&& textBoard[i][x].getColor() == textBoard[i][x + 2].getColor()
				&& textBoard[i][x].getColor() == textBoard[i][x + 3].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Increments turn.
	 */
	public void updateTurn() {
		turn++;

	}

	/**
	 * Method checks to see if the game is a draw.
	 * @return true or false
	 */
	public boolean isDraw() {
		int countOfFull = 0;
		for (int i = 0; i < columns; i++) {
			if (textBoard[0][i].getColor() != Color.BLACK) {
				countOfFull++;
			}
		}
		return countOfFull == columns ? true : false;

	}

}
