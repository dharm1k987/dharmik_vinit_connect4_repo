package dharmik_vinit_connect4;

import java.awt.Color;

public class AImodule {
	
	int[] chipsInRow;
	int AIposX;
	int AIposY;
	
	int playerPosX;
	int playerPosY;
	
	Board board;
	
	
	public AImodule(Board board) {
		this.board = board;
		this.chipsInRow = new int[7];
		
	}
	
	

	public void evaluate () {
		evaluateDefensive();	
		
	}
	
	private void evaluateDefensive () {
		int[] choices = new int[8];
		
		for (int i = 0; i < 7; i++) {
			playerPosX = board.getPosY();
			playerPosY = board.getPosX();
			
			
			
			for (int j = 0; j < chipsInRow.length; j++) {
				chipsInRow[j] = evaluateDirection(j+1);
				
			}
			
			
			
			
			
			choices[i] = chooseMove();
		}
		
	}
	
	private int chooseMove() {
		int[] movesX = new int[7];
		int[] movesY = new int[7];
		
		
		
		movesX[0] = playerPosX + chipsInRow[0];
		movesY[0] = playerPosY - chipsInRow[0];
		
		movesX[1] = playerPosX + chipsInRow[1];
		movesY[1] = playerPosY;
		
		movesX[2] = playerPosX + chipsInRow[2];
		movesY[2] = playerPosY + chipsInRow[2];
		
		movesX[3] = playerPosX;
		movesY[3] = playerPosY - chipsInRow[3];
		
		movesX[4] = playerPosX - chipsInRow[4];
		movesY[4] = playerPosY + chipsInRow[4];
		
		movesX[5] = playerPosX - chipsInRow[5];
		movesY[5] = playerPosY;
		
		movesX[6] = playerPosX - chipsInRow[6];
		movesY[6] = playerPosY - chipsInRow[6];
		
		for (int i = 0; i < movesX.length; i++) {
			assessValidity(movesX, movesY, i);
			System.out.println(movesX[i]+"--> "+movesY[i]);
		}
		
		return setOptimalPosition(movesX, movesY);
		
		
		
	}
	
	private int setOptimalPosition(int[] movesX, int[] movesY) {
		int highestInRowIndex = 0;
		for (int i = 0; i < movesX.length; i++) {
			if ((movesX[i] != 100) || (movesY[i] != 100)) {
				if (chipsInRow[i] >= chipsInRow[highestInRowIndex]) {
					highestInRowIndex = i;
				}
			}
		}
		
		
		return movesX[highestInRowIndex];
		
		
		
	}


	public int getAIposX(int x) { return AIposX; }
	
	private void assessValidity(int[] movesX, int[] movesY, int index) {
		
		if ((movesX[index] < 0) || (movesX[index] > 6) || (movesY[index] < 0) || (movesY[index] > 6)) {
			movesX[index] = 100;
			movesY[index] = 100;
		} else if (index == 3) {
			
		} else {
			try {
				if (((board.getColor(movesX[index], movesY[index]+1).equals(Color.RED)) 
						|| (board.getColor(movesX[index], movesY[index]+1).equals(Color.YELLOW))) 
						&& (board.getColor(movesX[index], movesY[index]).equals(Color.BLACK))) {
					
				} else {
					movesX[index] = 100;
					movesY[index] = 100;
				}
			} catch (NullPointerException e) {
				
			}
		}
		
	}
	
	



	public int getAIposX() { return AIposX; }
	public int getAIposY() { return AIposY; }
	
	private int evaluateDirection(int directionValue) {
		int inARow = 1;
		int count = 0;
		int redChips = 0;
		
		int pY1 = playerPosY;
		int pX1 = playerPosX;
		
		try {
			while ((board.getColor(pX1, pY1).equals(Color.RED)) && (count < 3)) {
				
				pY1 = checkDirection(directionValue, 0, pY1);
				pX1 = checkDirection(directionValue, 1, pX1);
				count ++;
				if (board.getColor(pX1, pY1).equals(Color.RED)) {
					redChips += 1;
				}
				
				
			}
		} catch (NullPointerException e) {
			
		} 
		
		inARow += redChips;
		
		return inARow;
	}
	
	private int checkDirection (int directionValue, int domainValue, int value) {
		if (directionValue == 4) {
			if (domainValue == 0) {
				value += 1;
			}
		} else if (directionValue == 1) {
			if (domainValue == 0) {
				value -= 1;
			} else if (domainValue == 1) {
				value += 1;
			}
		} else if (directionValue == 2) {
			if (domainValue == 1) {
				value += 1;
			}
		} else if (directionValue == 3) {
			if (domainValue == 0) {
				value += 1;
			} else if (domainValue == 1) {
				value += 1;
			}
		} else if (directionValue == 5) {
			if (domainValue == 0) {
				value += 1;
			} else if (domainValue == 1) {
				value -= 1;
			}
		} else if (directionValue == 6) {
			if (domainValue == 1) {
				value -= 1;
			}
		} else if (directionValue == 7) {
			if (domainValue == 0) {
				value -= 1;
			} else if (domainValue == 1) {
				value -= 1;
			}
		}
		return value;
	}
	
	
	
	
	
	
	/*private int evaluateDiagonalUpRight (int pX, int pY) {
	int inARow = 1;
	int count = 0;
	int redChips = 0;
	
	int pY1 = pY;
	int pX1 = pX;
	
	int pY2 = pY;
	int pX2 = pX;
	
	try {
		while ((board.getColor(pX1, pY1).equals(Color.RED)) && (count < 3)) {
			pY1 -= 1;
			pX1 += 1;
			count ++;
			if (board.getColor(pX1, pY1).equals(Color.RED)) {
				redChips += 1;
			}
			
		}
	} catch (NullPointerException e) {
		System.out.println("You are checking out of the board, diagonal up right");
	}
	
	count = 0;
	
	
	inARow += redChips;
	
	return inARow;
}

private int evaluateDiagonalDownLeft(int pX, int pY) {
	int inARow = 1;
	int count = 0;
	int redChips = 0;
	
	int pY1 = pY;
	int pX1 = pX;
	
	try {
		while ((board.getColor(pX1, pY1).equals(Color.RED)) && (count < 3)) {
			pY1 += 1;
			pX1 -= 1;
			count ++;
			if (board.getColor(pX1, pY1).equals(Color.RED)) {
				redChips += 1;
			}
			
			
		}
	} catch (NullPointerException e) {
		System.out.println("You are checking out of the board, diagonal down left");
	} 
	
	inARow += redChips;
	
	return inARow;
}

private int evaluateHorizontalRight(int pX, int pY) {
	int inARow = 1;
	int count = 0;
	int redChips = 0;
	
	int pY1 = pY;
	int pX1 = pX;
	
	try {
		while ((board.getColor(pX1, pY1).equals(Color.RED)) && (count < 3)) {
			
			pX1 += 1;
			count ++;
			if (board.getColor(pX1, pY1).equals(Color.RED)) {
				redChips += 1;
			}
			
			
		}
	} catch (NullPointerException e) {
		System.out.println("You are checking out of the board, horizontal right");
	} 
	
	inARow += redChips;
	
	return inARow;
}

private int evaluateHorizontalLeft(int pX, int pY) {
	int inARow = 1;
	int count = 0;
	int redChips = 0;
	
	int pY1 = pY;
	int pX1 = pX;
	
	try {
		while ((board.getColor(pX1, pY1).equals(Color.RED)) && (count < 3)) {
			
			pX1 -= 1;
			count ++;
			if (board.getColor(pX1, pY1).equals(Color.RED)) {
				redChips += 1;
			}
			
			
		}
	} catch (NullPointerException e) {
		System.out.println("You are checking out of the board, horizontal left");
	} 
	
	inARow += redChips;
	
	return inARow;
}



private int evaluateVerticalDown (int pX, int pY) {
	int inARow = 1;
	int count = 0;
	int redChips = 0;
	
	int pY1 = pY;
	int pX1 = pX;
	
	try {
		while ((board.getColor(pX1, pY1).equals(Color.RED)) && (count < 3)) {
			
			pY1 += 1;
			count ++;
			if (board.getColor(pX1, pY1).equals(Color.RED)) {
				redChips += 1;
			}
			
			
		}
	} catch (NullPointerException e) {
		System.out.println("You are checking out of the board, horizontal left");
	} 
	
	inARow += redChips;
	
	return inARow;
} */
	
}	
	