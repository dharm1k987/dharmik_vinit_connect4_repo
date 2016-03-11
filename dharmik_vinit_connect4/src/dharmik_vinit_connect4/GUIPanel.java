package dharmik_vinit_connect4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class GUIPanel extends JPanel implements ActionListener {
private int rows = 7;
private int columns = 7;
private Board board;
private JButton[] buttons;
private JLabel[][] lblChips;
Border border = BorderFactory.createLineBorder(Color.BLACK, 3);

public GUIPanel()
{
	
	setPreferredSize(new Dimension(500, 600));
	setLayout(new GridLayout(rows+1,columns));
	
	board = new Board(rows,columns);
	board.setUpTextBoard();
	
	createGUIBoard();
}

private void createGUIBoard() {
	buttons = new JButton[columns];
	lblChips = new JLabel[rows][columns];
	for (int i = 0; i<columns; i++)
		{
		buttons[i] = new JButton(String.valueOf(i));
		buttons[i].setFont(new Font("Arial", Font.BOLD, 30));
		buttons[i].setForeground(Color.WHITE);
		buttons[i].addActionListener(this);
		add(buttons[i]);;
		}
	
	for (int i = 0; i < rows; i++) {
		for (int x = 0; x < columns; x++) {
			lblChips[i][x] = new JLabel();
			lblChips[i][x].setBorder(border);

			add(lblChips[i][x]);

		}
	}
	
}

@Override
public void actionPerformed(ActionEvent e) {
	if (e.getSource() == buttons[0])
	{
		board.updateTextArray(0);
	}
	if (e.getSource() == buttons[1])
	{
		board.updateTextArray(1);
	}
	if (e.getSource() == buttons[2])
	{
		board.updateTextArray(2);
	}
	if (e.getSource() == buttons[3])
	{
		board.updateTextArray(3);
	}
	if (e.getSource() == buttons[4])
	{
		board.updateTextArray(4);
	}
	if (e.getSource() == buttons[5])
	{
		board.updateTextArray(5);
	}
	if (e.getSource() == buttons[6])
	{
		board.updateTextArray(6);
	}
	board.printTextArray();
}
}

