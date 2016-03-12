package dharmik_vinit_connect4;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

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
		for (int i = 0 ; i < rows; i++)
		{
			for (int x = 0; x< columns; x++)
			{
				textBoard[i][x] = new Chip();
			}
		}			
		
	}
	
	public int getTurn()
	{
		return turn;
	}
	public void updateTextArray(int temp) {
	
		
		for (int i = rows - 1; i >= 0; i--) 
		{
			if (textBoard[i][temp].getColor()== Color.BLACK) {
				if (turn % 2 == 0) {
					textBoard[i][temp].setColor(Color.RED);						
					posX = i;
					posY = temp;
					break;
				} else {
					textBoard[i][temp].setColor(Color.BLUE);			
					posX = i;
					posY = temp;
					break;
				}

			}
		}
				
	
		
	}
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void printTextArray() {
		for (int i = 0 ; i < rows; i++)
		{
			for (int x=0; x < columns; x++)
			{
				if (textBoard[i][x].getColor() == Color.RED)
				{
					System.out.print("R ");
				}
				else if (textBoard[i][x].getColor() == Color.BLUE)
				{
					System.out.print("B ");
				}
				else {
					System.out.print("N ");
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		
	}
	public boolean checkWinner() {
		boolean result = false;
		outerloop: for (int i = rows - 1; i >= 0; i--) {
			for (int x = 0; x < columns; x++) {
				if (x <= 3) {
					result = checkRight(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getColor());
						break outerloop;

					}

				}
				
				if (i >= 3) {
					result = checkUp(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getColor());
						break outerloop;
					}
				
				if (x <= 3 && i <= 3) {
					result = checkDiagonalDownRight(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getColor());
						break outerloop;
					}
				}
				if (x >= 3 && i <= 3) {
					result = checkDiagonalDownLeft(i, x, result);
					if (result) {
						setWinner(textBoard[i][x].getColor());
						break outerloop;
					}
				}

			}
		}
		
		
	}
		return result;
	}
	private void setWinner(Color color) {
		winner = color;		
	}
	
	public String getWinner()
	{
		if (winner == Color.RED) { return "Red";}
		else { return "Blue" ;}
	}
	
	public int checkValidColumn(JButton[] buttons) {
		int temp = -1;
		for (int i = 0; i < columns; i++) {
			if (textBoard[0][i].getColor() !=Color.BLACK && buttons[i].isEnabled()!=false) {
				temp = i;
				return temp;
			}
		}
		return temp;

	}
	
	private boolean checkDiagonalDownLeft(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i + 1][x - 1].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 2][x - 2].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 3][x - 3].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	private boolean checkDiagonalDownRight(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i + 1][x + 1].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 2][x + 2].getColor()
				&& textBoard[i][x].getColor() == textBoard[i + 3][x + 3].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	

	private boolean checkUp(int i, int x, boolean result) {
		if (textBoard[i][x].getColor() != Color.BLACK && textBoard[i][x].getColor() == textBoard[i - 1][x].getColor()
				&& textBoard[i][x].getColor() == textBoard[i - 2][x].getColor()
				&& textBoard[i][x].getColor() == textBoard[i - 3][x].getColor()) {
			result = true;
			return result;
		}
		return result;
	}

	

	private boolean checkRight(int i, int x, boolean result) {
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
		for (int i = 0 ; i < columns; i++)
		{
			if (textBoard[0][i].getColor()!=Color.BLACK)
			{
				countOfFull++;
			}
		}
		return countOfFull==columns? true:false;
		
	}
	
		
	}


