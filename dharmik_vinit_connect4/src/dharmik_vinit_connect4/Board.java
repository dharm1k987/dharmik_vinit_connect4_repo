package dharmik_vinit_connect4;

import java.awt.Color;

public class Board {
private Chip[][] textBoard;
private int rows;
private int columns;
private static int turn = 0;
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
	
	public void updateTextArray(int temp) {
	
		
		for (int i = rows - 1; i >= 0; i--) 
		{
			if (textBoard[i][temp].getColor()== Color.BLACK) {
				if (turn % 2 == 0) {
					textBoard[i][temp].setColor(Color.RED);						
					
					break;
				} else {
					textBoard[i][temp].setColor(Color.BLUE);			
				
					break;
				}

			}
		}
				
		turn++;
		
	}
	public void printTextArray() {
		for (int i = 0 ; i < rows; i++)
		{
			for (int x=0; x < columns; x++)
			{
				if (textBoard[i][x].getColor() == Color.RED)
				{
					System.out.print("R");
				}
				else if (textBoard[i][x].getColor() == Color.BLUE)
				{
					System.out.print("B");
				}
				else {
					System.out.print("N");
				}
			}
			System.out.print("\n");
		}
		
	}

}
