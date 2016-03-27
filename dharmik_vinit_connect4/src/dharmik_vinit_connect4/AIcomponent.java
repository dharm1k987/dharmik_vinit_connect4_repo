package dharmik_vinit_connect4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AIcomponent {
	
	private Board board;
	private int[] AIPosX;
	private int[] AIPosY;
	private int finalPositionX;
	private int defensiveScoringPosition;
	private int offensiveScoringPosition;
	private int offensiveScore;
	private int defensiveScore;
	private int positionsWithOffScore[][];
	private int positionsWithDefScore[][];

	
	
	public AIcomponent(Board board) {
		this.board = board;
		AIPosX = new int[7];
		positionsWithOffScore = new int[2][7];
		positionsWithDefScore = new int[2][7];
		for (int i = 0; i < AIPosX.length; i++) {
			AIPosX[i] = i;
		}
		AIPosY = new int[7];
		for (int i = 0; i < AIPosY.length; i++) {
			AIPosY[i] = 6;
		}
		
	}
	
	/**
	 * Method: runs when called to set a position at which AI can put the chip.
	 * Return: Void
	 */
	public void evaluateBoard() {

		
		evaluatePositions(Color.RED);
		evaluatePositions(Color.YELLOW);
		
		//filters all the valid moves
		filterInvalidMoves();
		filterStupidMove(); // possible change this order???
		
		//need to sort
		sort(positionsWithOffScore);
		sort(positionsWithDefScore);
		

		//System.out.println("defensive Score: "+defensiveScore);
		System.out.println("Offense: At "+positionsWithOffScore[0][0]+", "+positionsWithOffScore[1][0]);
		System.out.println("Defense: At "+positionsWithDefScore[0][0]+", "+positionsWithDefScore[1][0]);
		//System.out.println("defensive Score: "+defensiveScore);
		
		setFinalPositionX();
		
		//finalPositionX = offensiveScoringPosition;
		
	
	}
	/**
	 * Post: 
	 */
	
	/**
	 * Method: sets the scores of the positions where the whole column is full to -1, which will
	 * be lower than the lowest possible score from the board.
	 */
	public void filterInvalidMoves () {
		
		for (int i = 0; i < positionsWithOffScore[0].length; i++) {
			if (getCorrespondingY(i)+1 == 0) {
				
				positionsWithOffScore[1][i] = -1;
				positionsWithDefScore[1][i] = -1;
			}
		}
	}
	/**
	 * 
	 * @Post positionsWithOffScore/positionsWithDefScore: if the y-values of the possible x-positions
	 * are less than 0 (meaning: the whole column is full), the score of the position is set to -1.
	 */
	
	/**
	 * @Method: sets the scores of positions where, by putting a chip, it would allow the player to put
	 * a chip on top to achieve four in a row, to -1. It's a stupid move.
	 */
	public void filterStupidMove() {
		for (int i = 0; i < positionsWithOffScore[0].length; i++) {
			if ((getHighestInRow(i, getCorrespondingY(i)-1, Color.RED) >= 3) && (((getHighestInRow(i, getCorrespondingY(i), Color.RED) != 3) && (getHighestInRow(i, getCorrespondingY(i), Color.YELLOW) != 3)))) {
				positionsWithOffScore[1][i] = -1;
				positionsWithDefScore[1][i] = -1;
				System.out.println("Position which is stupid: "+positionsWithOffScore[0][i]);
			}
		}
	}
	/**
	 * @Post: the positions where there is a stupid move, it's second array component (the score) is set
	 * to -1
	 */
	
	
	/**
	 * @Method: sorts 2D arrays from greatest to least value according to the second array in the 2D array
	 * First array: The first array is to keep track of the positions with their corresponding scores
	 * Second array: contains values which are the scores at each position
	 */
	private void sort(int[][] ar) {
		
		int k = 0;
		int index = 0;
		
		for (int i = 0; i < ar[0].length; i++) {
			index = i;
			k = ar[1][i];
			for (int j = i+1; j < ar[0].length; j++) {
				if (ar[1][j] >= k) {
					index = j;
					k = ar[1][j];
					
				}
			}
			//Swap the scores.
			int temp = ar[1][index];
			ar[1][index] = ar[1][i];
			ar[1][i] = temp;
			
			//Swap the same x positions.
			int temp2 = ar[0][index];
			ar[0][index] = ar[0][i];
			ar[0][i] = temp2;					
		}
	}
	/**
	 * Post: The given 2D array is sorted according to it's second array component.
	 */
	

	
	public void setFinalPositionX() {
		
		if ((positionsWithOffScore[1][0] > positionsWithDefScore[1][0]) && (positionsWithDefScore[1][0] <= 2)) {
			finalPositionX = positionsWithOffScore[0][0];
			System.out.println("offensive"+", "+finalPositionX);
		} else if  (((positionsWithDefScore[1][0] == positionsWithOffScore[1][0])) && (positionsWithOffScore[1][0] == 3)) {
			finalPositionX = positionsWithOffScore[0][0];
			System.out.println("offensive 2"+", "+finalPositionX);
		}
		else if ((positionsWithDefScore[1][0] >= positionsWithOffScore[1][0])){
			finalPositionX = positionsWithDefScore[0][0];
			System.out.println("defensive"+", "+finalPositionX);
		} else {
			finalPositionX = positionsWithDefScore[0][0];
			System.out.println("defensive"+", "+finalPositionX);
		}
		
	}
	

	

	
	/**
	 * 
	 * @param x: Assumes it is an integer value between 0-6 inclusively. x position in the connect 4 grid. 
	 * @return: y-value in the grid at which there is an empty spot.
	 */
	public int getCorrespondingY (int x) {
		int y = 6;
		for (int j = 0; j < AIPosY.length; j++) {
			try {
				if (!board.getColor(x, j).equals(Color.BLACK)) {
					return j-1;
				}
			} catch (Exception e) {
				return j-1;
			}
		}
		return y;
	}
	
	/**
	 * @Method: Evaluates the possible positions at which AI can put chip, and rates the position
	 * by how many chips it blocks if used defensively, or how many chip in a row it forms if used
	 * offensively.
	 * @param color: Assumes it is a valid Color object. 
	 */
	private void evaluatePositions(Color color) {
		int[] position = new int[AIPosX.length];
		int[] positionScore = new int[AIPosX.length];
		
		for (int l = 0; l <  AIPosX.length; l++) {
			innerloop: for (int j = 0; j < AIPosY.length; j++) {
				try {
					if (!board.getColor(l, j).equals(Color.BLACK)) {
						AIPosY[l] = j-1;
						break innerloop;
					}
				} catch (Exception e) {
					AIPosY[l] = j-1;
					break innerloop;
				}
			}
		}		
		if (color.equals(Color.RED)) {
			for (int i = 0; i < AIPosX.length; i++) {
				positionsWithDefScore[0][i] = i; // Don't need.
				positionsWithDefScore[1][i] = getHighestInRow(AIPosX[i], AIPosY[i], color);
			}
		} else if (color.equals(Color.YELLOW)) {
			for (int i = 0; i < AIPosX.length; i++) {
				positionsWithOffScore[0][i] = i; // Don't need.
				positionsWithOffScore[1][i] = getHighestInRow(AIPosX[i], AIPosY[i], color);
			}
		}
		
		//DON'T NEED FROM HERE
		
	}
	/**
	 * @Post: changes the positionsWithDefScore and positionsWithOffScore 2D arrays to add values of 
	 * positions and values of the score there are given.
	 */
	

	/**
	 * @Method: returns the final x-position (integer).
	 * @return
	 */
	public int getFinalPositionX() { return finalPositionX; }
	
	
	/**
	 * 
	 * @param positionX: Assumes it is an integer value between 0-6
	 * @param positionY: Assumes it is an integer value between 0-6
	 * @param color: Assumes it is a Valid Color object
	 * @Method: Evaluates the highest amount of chips of a given color in a row. It retrieves this value
	 * by assessing all the 7 (excluding top) directions from a given location in the grid and checking
	 * how many in a row it has. It then adds the parallel directions (i.e. left and right). Finally,
	 * it returns the highest score from all the possible directions. 
	 */
	private int getHighestInRow (int positionX, int positionY, Color color) {
		int[] directionScore = new int[7];
		
		for (int j = 0; j < directionScore.length; j++) {
			directionScore[j] = evaluateDirection(j, positionX, positionY, color);
			
			
		}
		int[] twoWayDirectionScore = new int[4];
		
		for (int k = 0; k < twoWayDirectionScore.length-1; k++) {
			twoWayDirectionScore[k] = directionScore[k] + directionScore[k+4];
			//System.out.println(directionScore[k]+" "+directionScore[k+4]);
		}
		twoWayDirectionScore[twoWayDirectionScore.length-1] = directionScore[3];
		//System.out.println(twoWayDirectionScore[3]);
		//System.out.println("-------");
		
		int highestDirectionScore = 0;
		
		for (int i = 0; i < twoWayDirectionScore.length; i++) {
			if (twoWayDirectionScore[i] > highestDirectionScore) {
				highestDirectionScore = twoWayDirectionScore[i];
			}
		}
		
			//System.out.println("Highest direction score: "+highestDirectionScore);
	
		
		return highestDirectionScore;
	}
	
	
	/**
	 * 
	 * @param directionValue: Assumes it is an integer value between 0-6
	 * @param positionX: Assumes it is an integer value between 0-6
	 * @param positionY: Assumes it is an integer value between 0-6
	 * @param color: Assumes it is a valid Color object
	 * @Method: Given a location in the grid and a direction value, checks how many chips of a color
	 * there are in a row.
	 */
	private int evaluateDirection(int directionValue, int positionX, int positionY, Color color) {
		int inARow = 0;
		int count = 0;
		int playerChips = 0;
		
		int pY1 = positionY;
		int pX1 = positionX;
		
		
		try {
			loop: while ((count < 4)) {
				
				
				
				
				if (board.getColor(checkDirection(directionValue, 1, pX1), 
						checkDirection(directionValue, 0, pY1)).equals(color)) {
					
					pY1 = checkDirection(directionValue, 0, pY1);
					pX1 = checkDirection(directionValue, 1, pX1); 
					playerChips += 1;
					
				}
				else if (!board.getColor(pX1, pY1).equals(color)) {
					break loop;
				}
				count ++;
			}
		} catch (NullPointerException e) {
			//System.out.println("Caught a null");
		} 
		
		inARow += playerChips;
		
		
		return inARow;
	}
	
	/**
	 * 
	 * @param directionValue: 
	 * @param domainValue: Should be 0 or 1.
	 * @param axisValue: 
	 * @return
	 */
	private int checkDirection (int directionValue, int domainValue, int axisValue) {
		if (directionValue == 3) {
			if (domainValue == 0) {
				axisValue += 1;
			}
		} else if (directionValue == 0) {
			if (domainValue == 0) {
				axisValue -= 1;
			} else if (domainValue == 1) {
				axisValue += 1;
			}
		} else if (directionValue == 1) {
			if (domainValue == 1) {
				axisValue += 1;
			}
		} else if (directionValue == 2) {
			if (domainValue == 0) {
				axisValue += 1;
			} else if (domainValue == 1) {
				axisValue += 1;
			}
		} else if (directionValue == 4) {
			if (domainValue == 0) {
				axisValue += 1;
			} else if (domainValue == 1) {
				axisValue -= 1;
			}
		} else if (directionValue == 5) {
			if (domainValue == 1) {
				axisValue -= 1;
			}
		} else if (directionValue == 6) {
			if (domainValue == 0) {
				axisValue -= 1;
			} else if (domainValue == 1) {
				axisValue -= 1;
			}
		}
		return axisValue;
	}
	
	
}
