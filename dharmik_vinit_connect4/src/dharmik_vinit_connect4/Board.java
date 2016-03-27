package dharmik_vinit_connect4;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;


public class Board {
	private Chip[][] textBoard;
	private int rows;
	private int columns;
	private Color winner;
	private static int turn = 0;
	private int posX;
	private int posY;

	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		textBoard = new Chip[rows][columns];
	}

	public void setUpTextBoard() {
		for (int i = 0; i < rows; i++) {
			for (int x = 0; x < columns; x++) {
				textBoard[i][x] = new Chip();
			}
		}

	}

	public int getTurn() {
		return turn;
	}

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

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	public boolean isOccupied(int x) {
		if(textBoard[0][x].equals(Color.RED)){
			return true;
		} else {
			return false;
		}
	}
	
	public Color getColor(int x, int y) {
		try {
			return textBoard[y][x].getColor();
		} catch (NullPointerException e) {
			return Color.BLACK;
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public Chip[][] getTextBoard () {
		return textBoard;
	}


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

	private void setWinner(Color color) {
		winner = color;
	}

	public String getWinner() {
		if (winner == Color.RED) {
			return "Red";
		} else {
			return "Yellow";
		}
	}

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

	private boolean checkVertical(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i - 1][x].getColor()
				&& textBoard[i][x].getColor() == textBoard[i - 2][x].getColor()
				&& textBoard[i][x].getColor() == textBoard[i - 3][x].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	private boolean checkHorizontal(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i][x + 1].getColor()
				&& textBoard[i][x].getColor() == textBoard[i][x + 2].getColor()
				&& textBoard[i][x].getColor() == textBoard[i][x + 3].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	public void updateTurn() {
		turn++;

	}

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
