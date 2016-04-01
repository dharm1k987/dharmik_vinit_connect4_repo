package dharmik_vinit_connect4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Class serves as AI 'thinking class' where it analyzes the best move.
 * @author Dharmik, Vinit
 * @version 1.0
 */
public class AIBot {
	
	private Board board;
	private int[] AIPosX;
	private int[] AIPosY;
	private int finalPositionX;
	private ChipState playerChipState;
	private ChipState AIChipState;
	private int positionsWithOffScore[][];
	private int positionsWithDefScore[][];
	private int numberOfValidMoves;

	
	/**
	 * Method: used to initiate the AI bot.
	 * @param board: Assumes it is a Board object
	 * @param playerChipState: Assumes it is a Color object
	 * @param AIChipState: Assumes it is a Color object
	 */
	public AIBot(Board board, ChipState playerChipState, ChipState AIChipState) {
		this.board = board;
		this.numberOfValidMoves = 7;
		this.playerChipState = playerChipState;
		this.AIChipState = AIChipState;
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

		
		evaluatePositions(playerChipState);
		evaluatePositions(AIChipState);
		
		//filters array of all valid moves.
		numberOfValidMoves = 7;
		filterInvalidMoves();
		filterStupidMove(); 
		
		
		//Sorted here.
		sort(positionsWithOffScore);
		sort(positionsWithDefScore);
		

		
		
		
		
		setFinalPositionX();
		
		
		
	
	}
	/**
	 * Post: changes the positionsWithOffScore array, positionsWithDefScore, numberOfValidMoves, and the finalPositionX
	 * with the appropriate methods used.  
	 */
	
	/**
	 * Method: sets the scores of the positions where the whole column is full to -1, which will
	 * be lower than the lowest possible score from the board.
	 */
	public void filterInvalidMoves () {
		
		for (int i = 0; i < positionsWithOffScore[0].length; i++) {
			if (getCorrespondingY(i)+1 == 0) {
				numberOfValidMoves -= 1;
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
		int numberOfStupidMoves = 0;
		for (int i = 0; i < positionsWithOffScore[0].length; i++) {
			if ((getHighestInRow(i, getCorrespondingY(i)-1, playerChipState) >= 3) && (((getHighestInRow(i, getCorrespondingY(i), playerChipState) != 3) && (getHighestInRow(i, getCorrespondingY(i), AIChipState) != 3)))) {
				numberOfStupidMoves += 1;
				
			}
		}
		
		
		if (numberOfValidMoves != numberOfStupidMoves) {
			for (int i = 0; i < positionsWithOffScore[0].length; i++) {
				if ((getHighestInRow(i, getCorrespondingY(i)-1, playerChipState) >= 3) && (((getHighestInRow(i, getCorrespondingY(i), playerChipState) != 3) && (getHighestInRow(i, getCorrespondingY(i), AIChipState) != 3)))) {
					positionsWithOffScore[1][i] = -1;
					positionsWithDefScore[1][i] = -1;
					System.out.println("Position which is stupid: "+positionsWithOffScore[0][i]);
				}
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
	

	/**
	 * Method:	uses the positionWithOffScore and positionWithDefScore arrays to make a series of comparisons
	 * and determine the most appropriate move for the AI. Sets this move to the final move to be used. Both the
	 * arrays are already in order of highest scoring position to lowest scoring position
	 */
	public void setFinalPositionX() {
		System.out.print("AI Evaluation:  ");
		System.out.println("Offensive Score at "+positionsWithOffScore[0][0]+" is "+positionsWithOffScore[1][0]);
		System.out.println("Defensive Score at "+positionsWithDefScore[0][0]+" is "+positionsWithDefScore[1][0]);
		
		if ((positionsWithOffScore[1][0] > positionsWithDefScore[1][0]) && (positionsWithDefScore[1][0] <= 2)) {
			finalPositionX = positionsWithOffScore[0][0];
			System.out.println("AI plays offensively at "+finalPositionX);
		} else if  (((positionsWithDefScore[1][0] == positionsWithOffScore[1][0])) && (positionsWithOffScore[1][0] == 3)) {
			finalPositionX = positionsWithOffScore[0][0];
			System.out.println("AI plays offensively at "+finalPositionX);
		}
		else if ((positionsWithDefScore[1][0] >= positionsWithOffScore[1][0])){
			finalPositionX = positionsWithDefScore[0][0];
			System.out.println("AI plays defensively at "+finalPositionX);
		} else {
			finalPositionX = positionsWithDefScore[0][0];
			System.out.println("AI plays defensively at "+finalPositionX);
		}
		System.out.println(" ");
	}
	/**
	 * Post: finalPositionX has been changed to a value between 0-6, as chosen by this method.
	 */
	

	

	
	/**
	 * 
	 * @param x: Assumes it is an integer value between 0-6 inclusively. x position in the connect 4 grid. 
	 * @return: y-value in the grid at which there is an empty spot.
	 */
	public int getCorrespondingY (int x) {
		int y = 6;
		for (int j = 0; j < AIPosY.length; j++) {
			try {
				if (!board.getChipState(x, j).equals(ChipState.EMPTY)) {
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
	private void evaluatePositions(ChipState playerChipState2) {
		
		
		for (int l = 0; l <  AIPosX.length; l++) {
			innerloop: for (int j = 0; j < AIPosY.length; j++) {
				try {
					if (!board.getChipState(l, j).equals(ChipState.EMPTY)) {
						AIPosY[l] = j-1;
						break innerloop;
					}
				} catch (Exception e) {
					AIPosY[l] = j-1;
					break innerloop;
				}
			}
		}		
		if (playerChipState2.equals(playerChipState)) {
			for (int i = 0; i < AIPosX.length; i++) {
				positionsWithDefScore[0][i] = i; 
				positionsWithDefScore[1][i] = getHighestInRow(AIPosX[i], AIPosY[i], playerChipState2);
			}
		} else if (playerChipState2.equals(AIChipState)) {
			for (int i = 0; i < AIPosX.length; i++) {
				positionsWithOffScore[0][i] = i; 
				positionsWithOffScore[1][i] = getHighestInRow(AIPosX[i], AIPosY[i], playerChipState2);
			}
		}
		
	}
	/**
	 * @Post: changes the positionsWithDefScore and positionsWithOffScore 2D arrays to add values of 
	 * positions and values of the score there are given.
	 */
	

	/**
	 * @Method: returns the final x-position (integer).
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
	private int getHighestInRow (int positionX, int positionY, ChipState playerChipState2) {
		int[] directionScore = new int[7];
		
		for (int j = 0; j < directionScore.length; j++) {
			directionScore[j] = evaluateDirection(j, positionX, positionY, playerChipState2);
			
			
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
	private int evaluateDirection(int directionValue, int positionX, int positionY, ChipState playerChipState2) {
		int inARow = 0;
		int count = 0;
		int playerChips = 0;
		
		int pY1 = positionY;
		int pX1 = positionX;
		
		
		try {
			loop: while ((count < 4)) {
				
				
				
				
				if (board.getChipState(checkDirection(directionValue, 1, pX1), 
						checkDirection(directionValue, 0, pY1)).equals(playerChipState2)) {
					
					pY1 = checkDirection(directionValue, 0, pY1);
					pX1 = checkDirection(directionValue, 1, pX1); 
					playerChips += 1;
					
				}
				else if (!board.getChipState(pX1, pY1).equals(playerChipState2)) {
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
	 * Method: Used in the evaluateDirection() method. Used to increment to the next location in the grid
	 * according to whichever direction the check for highest in the row is headed.
	 * @param directionValue: should be an integer between 0-6, inclusively, for each direction from a given location
	 * in the grid.
	 * @param domainValue: Should be 0 or 1. (0 = vertical, and 1 = horizontal)
	 * @param axisValue: incremented by 1. Assumed to be an integer.
	 * @return: returns an integer.
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
