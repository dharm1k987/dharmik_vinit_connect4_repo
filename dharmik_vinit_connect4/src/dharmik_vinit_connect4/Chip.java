package dharmik_vinit_connect4;

import java.awt.Color;

/**
 * This class is the text based 'chip' in the connect 4 board * 
 * @author Dharmik, Vinit
 * @version 1.0
 */
public class Chip {
	private ChipState chipState;

	/**
	 * Default constructor. Set's the intial colour of the chip to black.
	 */
	public Chip() {
		this.chipState = ChipState.EMPTY;
	}

	/**
	 * Accessor method.
	 * @return color of chip.
	 */
	public ChipState getChipState() {
		return chipState;
	}

	/**
	 * Mutator method. 
	 * @param color that a chip must be set to.
	 */
	public void setChipState(ChipState chipState) {
		this.chipState = chipState;
	}

}
