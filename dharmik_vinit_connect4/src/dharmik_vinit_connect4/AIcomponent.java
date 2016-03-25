package dharmik_vinit_connect4;

import java.awt.Color;
import java.util.ArrayList;

public class AIcomponent {
	
	private Board board;
	private int[] AIPosX;
	private int[] AIPosY;
	private int finalPositionX;
	private int defensiveScoringPosition;
	private int offensiveScoringPosition;
	private int offensiveScore;
	private int defensiveScore;
	
	
	public AIcomponent(Board board) {
		this.board = board;
		AIPosX = new int[7];
		for (int i = 0; i < AIPosX.length; i++) {
			AIPosX[i] = i;
		}
		AIPosY = new int[7];
		for (int i = 0; i < AIPosY.length; i++) {
			AIPosY[i] = 6;
		}
		
	}
	
	public void evaluateBoard() {
		defensiveScoringPosition = evaluatePossiblePositions(Color.RED);
		
		offensiveScoringPosition = evaluatePossiblePositions(Color.YELLOW);
		
		
		offensiveScore = getHighestInRow(offensiveScoringPosition, getCorrespondingY(offensiveScoringPosition), Color.YELLOW);
		defensiveScore = getHighestInRow(defensiveScoringPosition, getCorrespondingY(defensiveScoringPosition), Color.RED);
		
		//System.out.println("defensive Score: "+defensiveScore);
		System.out.println("offensive Score: "+offensiveScore);
		System.out.println("defensive Score: "+defensiveScore);
		
		if ((offensiveScore > defensiveScore) && (defensiveScore <= 2)) {
			finalPositionX = offensiveScoringPosition;
			System.out.println("offensive");
		} else if ((defensiveScore >= offensiveScore) && (!board.isOccupied(defensiveScoringPosition))){
			finalPositionX = defensiveScoringPosition;
			System.out.println("defensive");
		} else {
			finalPositionX = defensiveScoringPosition;
			System.out.println("defensive 2");
		}
		
		
		
		
	}
	
	public int getCorrespondingY (int x) {
		int y = 0;
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
	
	private int evaluatePossiblePositions(Color color) {
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
		
		for (int i = 0; i < AIPosX.length; i++) {
			position[i] = i;
			positionScore[i] = getHighestInRow(AIPosX[i], AIPosY[i], color);
		}
		for(int i = 0; i < AIPosX.length; i++) {
			//System.out.println("At "+i+", "+positionScore[i]);
		}
		int highestScoringPosition = 0;
		int highScore = 0;
		//ArrayList<Integer> highScoringPositions = new ArrayList<Integer>();
		for (int i = 0; i < AIPosX.length; i++) {
			//System.out.println("Position score: "+positionScore[i]+", HS: "+highScore);
			
			if (positionScore[i] >= highScore) {
				
				highestScoringPosition = i;
				highScore = positionScore[i];
				
			} 
		}
		/*sort(highScoringPositions);
		System.out.println("Highest scoring position: "+highestScoringPosition);
		/*for (int i = 0; i < highScoringPositions.size(); i++) {
			if (board.isOccupied(highScoringPositions.get(i))) {
				highScoringPositions.remove(i);
			}
		}*/
		//System.out.println("HSP: "+highestScoringPosition);
		return highestScoringPosition;
		//System.out.println("Final position: "+finalPositionX);
		
	}
	
	private void sort(ArrayList<Integer> ar) {
				
				int k = 0;
				int index = 0;
				for (int i = 0; i < ar.size(); i++) {
					index = i;
					k = ar.get(i);
					for (int j = i+1; j < ar.size(); j++) {
						if (ar.get(j) < k) {
							index = j;
							k = ar.get(j);
							
						}
					}
					int temp = ar.get(index);
					ar.set(index, i);
					ar.set(i, temp);					
				}
	}
	
	public int getAIPosX() { return finalPositionX; }
	
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
		System.out.println("-------");
		
		int highestDirectionScore = 0;
		
		for (int i = 0; i < twoWayDirectionScore.length; i++) {
			if (twoWayDirectionScore[i] > highestDirectionScore) {
				highestDirectionScore = twoWayDirectionScore[i];
			}
		}
		
			//System.out.println(highestDirectionScore);
	
		
		return highestDirectionScore;
	}
	
	
	
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
	
	private int checkDirection (int directionValue, int domainValue, int value) {
		if (directionValue == 3) {
			if (domainValue == 0) {
				value += 1;
			}
		} else if (directionValue == 0) {
			if (domainValue == 0) {
				value -= 1;
			} else if (domainValue == 1) {
				value += 1;
			}
		} else if (directionValue == 1) {
			if (domainValue == 1) {
				value += 1;
			}
		} else if (directionValue == 2) {
			if (domainValue == 0) {
				value += 1;
			} else if (domainValue == 1) {
				value += 1;
			}
		} else if (directionValue == 4) {
			if (domainValue == 0) {
				value += 1;
			} else if (domainValue == 1) {
				value -= 1;
			}
		} else if (directionValue == 5) {
			if (domainValue == 1) {
				value -= 1;
			}
		} else if (directionValue == 6) {
			if (domainValue == 0) {
				value -= 1;
			} else if (domainValue == 1) {
				value -= 1;
			}
		}
		return value;
	}
	
	
}
