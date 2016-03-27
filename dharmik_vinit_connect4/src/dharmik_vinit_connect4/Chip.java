package dharmik_vinit_connect4;

import java.awt.Color;

/**
 * This class is the text based 'chip' in the connect 4 board * 
 * @author Dharmik, Vinit
 * @version 1.0
 */
public class Chip {
	private Color color;

	/**
	 * Default constructor. Set's the intial colour of the chip to black.
	 */
	public Chip() {
		this.color = Color.BLACK;
	}

	/**
	 * Accessor method.
	 * @return color of chip.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Mutator method. 
	 * @param color that a chip must be set to.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
