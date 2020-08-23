import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Frame extends Puzzle implements ActionListener{
	static GraphicsConfiguration gc;

	private JFrame frame; 
	private JButton[][] buttonGrid; 
	
	// constructor
	public Frame(int size) {
		super(size);
		
		
		frame = new JFrame(gc);	
		buttonGrid = new JButton[this.grid.length][this.grid.length];
		
		frame.setLayout(new GridLayout(this.grid.length, this.grid.length));
		addButtons();
		
		
		frame.setTitle("Sliding Puzzle");
		frame.setSize(800, 800);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	/**
	 * Adds buttons to the frame based on an int[][] Puzzle grid
	 */
	
	public void addButtons() {
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				String buttonValue = "";
				if (this.grid[i][j] != 0) {
					buttonValue = String.valueOf(this.grid[i][j]);
				}
				else {
					buttonValue = " "; 
				}
				JButton b = new JButton(buttonValue);
			    b.addActionListener(this);
				frame.add(b);
				buttonGrid[i][j] = b;
			}
		}
	}
	
	/**
	 * Finds the empty button
	 * @return the row and col index of the empty button
	 */
	public int[] findEmpty() {
		boolean found = false; 
		int[] pos = new int[2];
		int row = 0;
		int col = 0;
		
		while (!found && row < buttonGrid.length) {
			if (buttonGrid[row][col].getText().equals(" ")) {
				pos[0] = row;
				pos[1] = col;	
				found = true;
			}
			if (col < buttonGrid.length) {
				col++;
				if (col == buttonGrid.length && row < buttonGrid.length-1) {
					col = 0; 
					row++;
				}
			}
		}
		return pos;
	}
	
	/**
	 * Gets the adjacent values of the empty button 
	 * @param eRow = empty button row index
	 * @param eCol = empty button col index
	 * @return list of buttons that are adjacent to the empty button 
	 */
	public JButton[] getAdjacent(int eRow, int eCol) {
		// [0] top, [1] left, [2] right, [3] bottom
		JButton[] adjacentNums = new JButton[4];
		JButton dne = new JButton("-1");
		
		if (eRow > 0) {
			adjacentNums[0] = buttonGrid[eRow-1][eCol]; // top button 
		}
		else{
			adjacentNums[0] = dne;
		}
		if (eCol > 0) {
			adjacentNums[1] = buttonGrid[eRow][eCol-1]; // left button
		}
		else{
			adjacentNums[1] = dne;			
		}
		if (eCol < buttonGrid.length-1) {
			adjacentNums[2] = buttonGrid[eRow][eCol+1]; // right button
		}
		else{
			adjacentNums[2] = dne;
		}
		if (eRow < buttonGrid.length-1) {
			adjacentNums[3] = buttonGrid[eRow+1][eCol]; // bottom button
		}
		else{
			adjacentNums[3] = dne;
		}
		return adjacentNums;
	}
	
	/**
	 * Checks if button given is one of the adjacent buttons to the empty,
	 * switches empty and given adjacent button locations.
	 * @param buttonText = text on the given button
	 * @param row = empty button's row index
	 * @param col = empty button's col index
	 */
	public void changeEmptyWithAdjacentText(String buttonText, int eRow, int eCol) {
		if (buttonText != " "){
			JButton[] adjacentButtons = getAdjacent(eRow, eCol);
			for (int i = 0; i < 4; i++) {
				if (buttonText.equals(adjacentButtons[i].getText())){
					buttonGrid[eRow][eCol].setText(buttonText);
					if (i == 0) {
						buttonGrid[eRow-1][eCol].setText(" ");
					}
					else if (i == 1) {
						buttonGrid[eRow][eCol-1].setText(" ");
					}
					else if (i == 2) {
						buttonGrid[eRow][eCol+1].setText(" ");
					}
					else if (i == 3) {
						buttonGrid[eRow+1][eCol].setText(" ");
					}
					
				}
			}	
		}
	}
	
	/**
	 * Checks if the board is in order 
	 * @return true if puzzle is solved
	 */
	public boolean checkWin() {
		int row = 0;
		int col = 0;
		int num = 1;
		int lastNum = (int)(Math.pow(buttonGrid.length,2)-2);
		
		while (row < buttonGrid.length) {
			while (col < buttonGrid.length && num < lastNum) {
				if (!(buttonGrid[row][col].getText()).equals(String.valueOf(num))) {
					
					return false;
				}
				col++;
				num++;
			}
			row++;
			col = 0;
		}
		
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String buttonText = button.getText();
		int[] emptyButtonPos = findEmpty();
		int eRow = emptyButtonPos[0];
		int eCol = emptyButtonPos[1];
		changeEmptyWithAdjacentText(buttonText, eRow, eCol);
		if ((eRow == buttonGrid.length-2 && eCol == buttonGrid.length-1)
			|| (eRow == buttonGrid.length-1 && eCol == buttonGrid.length-2)) {
			if (checkWin()) {
				JOptionPane.showMessageDialog(null, "You completed a " + 
						buttonGrid.length + " x " + buttonGrid.length +
						" puzzle!","Win!",JOptionPane.PLAIN_MESSAGE);
			}
		}
    } 
	
	public static void main(String[] args){
		Frame puzzle = new Frame(4);
		
	}//end of main

}
