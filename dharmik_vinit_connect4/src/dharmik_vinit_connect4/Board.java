package dharmik_vinit_connect4;



import javax.swing.JButton;

/**
 * Class is the backbone of the GUI board. Board class is text based board.
 * @author Dharmik, Vinit
 *
 */
public class Board {
	//
	private Chip[][] textBoard;
	private int rows;
	private int columns;
	private ChipState winner;
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
	 * Method: Sets up a new 'Board' of type 'Chip'. Adds chips to each spot.
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
	 * the JFrame closes, and another mode is selected.
	 * @param turn - turn to be set
	 */
	public static void setTurn(int turn)
	{
		Board.turn = turn;
	}

	/**
	 * Method: Updates the text board Array after the user clicks a button.
	 * @param temp - the column they clicked.
	 */
	public void updateTextArray(int temp) {
	
		for (int i = rows - 1; i >= 0; i--) {
			if (textBoard[i][temp].getChipState() == ChipState.EMPTY) {
				if (turn%2 == 0) {
					textBoard[i][temp].setChipState(ChipState.PLAYER1);
					posX = i;
					posY = temp;
					break;
				} else {
					
					textBoard[i][temp].setChipState(ChipState.PLAYER2);
					posX = i;
					posY = temp;
					break;
				}
				

			}
		}
		updateTurn();

	}

	/**
	 * Method: returns the x position of the chip
	 * @return x position
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Method: returns the y position of the chip
	 * @return y position
	 */
	public int getPosY() {
		return posY;
	}	

	
	/**
	 * Method: Returns the ChipState of a specified chip.
	 * @param x - the x array index.
	 * @param y - the y array index.
	 * @return the ChipState of the chip.
	 */
	public ChipState getChipState(int x, int y) {
		try {
			return textBoard[y][x].getChipState();
		} catch (NullPointerException e) {
			return ChipState.EMPTY;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * Accessor method.
	 * @return textBoard
	 */
	public Chip[][] getTextBoard () {
		return textBoard;
	}

	/**
	 * Method: Evaluates text board and checks for a winner.
	 * @return true or false
	 */
	public boolean checkWinner() {
		boolean result = false;
		outerloop: for (int i = rows - 1; i >= 0; i--) {
			for (int x = 0; x < columns; x++) {
				if (x <= 3) {
					result = checkHorizontal(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getChipState());
						break outerloop;

					}

				}

				if (i >= 3) {
					result = checkVertical(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getChipState());
						break outerloop;
					}
				}

					if (x <= 3 && i <= 3) {
						result = checkDiagonalRight(i, x, result);
						if (result) {
							setWinner(textBoard[i][x].getChipState());
							break outerloop;
						}
					}
					if (x >= 3 && i <= 3) {
						result = checkDiagonalLeft(i, x, result);
						if (result) {
							setWinner(textBoard[i][x].getChipState());
							break outerloop;
						}
					}

				
			}

		}
		return result;
	}

	/**
	 * Mutator method. Sets the winner (colour).
	 * @param ChipState - the ChipState who won.
	 */
	private void setWinner(ChipState chipState) {
		winner = chipState;
	}

	/**
	 * Accessor method. 
	 * @return the Color string of the winner.
	 */
	public String getWinner() {
		if (winner == ChipState.PLAYER1) {
			return "Red";
		} else {
			return "Yellow";
		}
	}

	/**
	 * Method: Checks to see if a column is full, in which 
	 * case it must be disabled.
	 * @param buttons - the GUI array which the user clicks as their column.
	 * @return the column index to be disabled. -1, means none.
	 */
	public int checkValidColumn(JButton[] buttons) {
		int temp = -1;
		for (int i = 0; i < 7; i++) {
			if (textBoard[0][i].getChipState() != ChipState.EMPTY && buttons[i].isEnabled() != false) {
				temp = i;				
				return temp;
			}
		}
		return temp;

	}

	/**
	 * Method: Checks the board to see winner from
	 * a left - diagonal perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkDiagonalLeft(int i, int x, boolean result) {
		if (textBoard[i][x].getChipState() != ChipState.EMPTY
				&& textBoard[i][x].getChipState() == textBoard[i + 1][x - 1].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i + 2][x - 2].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i + 3][x - 3].getChipState()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Method: Checks the board to see winner 
	 * from a right - diagonal perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkDiagonalRight(int i, int x, boolean result) {
		if (textBoard[i][x].getChipState() != ChipState.EMPTY
				&& textBoard[i][x].getChipState() == textBoard[i + 1][x + 1].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i + 2][x + 2].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i + 3][x + 3].getChipState()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Method: Checks the board to see winner from a vertical perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkVertical(int i, int x, boolean result) {
		if (textBoard[i][x].getChipState() != ChipState.EMPTY && textBoard[i][x].getChipState() == textBoard[i - 1][x].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i - 2][x].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i - 3][x].getChipState()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Method: Checks the board to see winner from a horizontal perspective.
	 * @param i - the board's row index.
	 * @param x - the board's column index.
	 * @param result - value to be returned. 
	 * @return true or false, if there is a match.
	 */
	private boolean checkHorizontal(int i, int x, boolean result) {
		if (textBoard[i][x].getChipState() != ChipState.EMPTY && textBoard[i][x].getChipState() == textBoard[i][x + 1].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i][x + 2].getChipState()
				&& textBoard[i][x].getChipState() == textBoard[i][x + 3].getChipState()) {
			result = true;
			return result;
		}
		return result;
	}

	/**
	 * Method: Increments turn.
	 */
	public void updateTurn() {
		turn++;

	}

	/**
	 * Method: Checks to see if the game is a draw.
	 * @return true or false
	 */
	public boolean isDraw() {
		int countOfFull = 0;
		for (int i = 0; i < columns; i++) {
			if (textBoard[0][i].getChipState() != ChipState.EMPTY) {
				countOfFull++;
			}
		}
		return countOfFull == columns ? true : false;

	}

}
