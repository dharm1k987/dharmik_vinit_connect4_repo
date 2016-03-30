package dharmik_vinit_connect4;

/**
 * This interface has methods related to one player and two player GUI
 * @author Dharmik, Vinit
 * @version 1.0
 */
public interface InterfaceGUI {

	/**
	 * Method: Creates a GUI board in the panel.
	 */
	public void createGUIBoard();

	/**
	 * Method: Disables all the buttons on the panel.
	 */
	public void disableAllButtons();

	/**
	 * Method: Changes color to allow the user to know who's turn (red, yellow).
	 */
	public void setTurnGuides();

	/**
	 * Method: Disables the appropriate button according to whichever column is full.
	 */
	public void checkValidColumn();

	/**
	 * Method: Updates the GUI board after a turn is played.
	 */
	public void updateGUI();

}
