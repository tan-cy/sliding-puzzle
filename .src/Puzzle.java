import java.util.Arrays;
import java.util.Random;

public class Puzzle {
	int[][] grid;
	// constructor 
	public Puzzle(int size) {
		grid = new int[size][size];
		addNumbers(size);
		shuffle();
	}
	
	/**
	 * Puts puzzle numbers to a grid
	 * @param size = the length of the puzzle
	 */
	public void addNumbers(int size) {				
		int counter = 1;
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				this.grid[row][col] = counter; 
				counter++;
			}
		}
		this.grid[size-1][size-1] = 0; // sets empty piece 
	}
	
	
	public int[] findEmptyPos() {
		boolean found = false; 
		int[] pos = new int[2];
		int row = 0;
		int col = 0;
		
		while (!found && row < this.grid.length) {
			if (this.grid[row][col] == 0) {
				pos[0] = row;
				pos[1] = col;	
				found = true;
			}
			if (col < this.grid.length) {
				col++;
				if (col == this.grid.length && row < this.grid.length-1) {
					col = 0; 
					row++;
				}
			}
		}
		return pos;
	}
	public int[] getAdjacentNums(int eRow, int eCol) {
		// [0] top, [1] left, [2] right, [3] bottom
		int[] adjacentNums = new int[4];
		int dne = -1;
		
		
		if (eRow > 0) {
			adjacentNums[0] = this.grid[eRow-1][eCol]; // top button 
		}
		else{
			adjacentNums[0] = dne;
		}
		if (eCol > 0) {
			adjacentNums[1] = this.grid[eRow][eCol-1]; // left button
		}
		else{
			adjacentNums[1] = dne;			
		}
		if (eCol < this.grid.length-1) {
			adjacentNums[2] = this.grid[eRow][eCol+1]; // right button
		}
		else{
			adjacentNums[2] = dne;
		}
		if (eRow < this.grid.length-1) {
			adjacentNums[3] = this.grid[eRow+1][eCol]; // bottom button
		}
		else{
			adjacentNums[3] = dne;
		}
		return adjacentNums;
	}
	
	public void shuffleText(int buttonText, int eRow, int eCol) {
		if (buttonText != 0){
			int[] adjacentButtons = getAdjacentNums(eRow, eCol);
			for (int i = 0; i < 4; i++) {
				if (buttonText == adjacentButtons[i]){
					this.grid[eRow][eCol] = buttonText;
					if (i == 0) {
						this.grid[eRow-1][eCol] = 0;
					}
					else if (i == 1) {
						this.grid[eRow][eCol-1] = 0;
					}
					else if (i == 2) {
						this.grid[eRow][eCol+1] = 0;
					}
					else if (i == 3) {
						this.grid[eRow+1][eCol] = 0;
					}
					
				}
			}	
		}
	}
	public void shuffle() {
		Random r = new Random();
		for (int i = 0; i < Math.pow(this.grid.length,6); i++) { 
			int[] emptyPos = findEmptyPos();
			int eRow = emptyPos[0];
			int eCol = emptyPos[1];
			int[] adjacentNums = getAdjacentNums(eRow, eCol);
			int randNum = r.nextInt(4);
			
			if (adjacentNums[randNum] != -1) {
				shuffleText(adjacentNums[randNum], eRow, eCol);
			}
		}
	}
	
	
//	/*
//	 * given array,
//	 * returns shuffled array
//	 */
//	public int[] shuffle(int[] nums) {
//		Random r = new Random();
//		int idx = 0;
//		for (int i = 0; i<Math.pow(nums.length,3); i++) {
//			int randInt = r.nextInt(nums.length);
//			int temp = nums[randInt];
//			nums[randInt] = nums[idx];
//			nums[idx] = temp;
//			idx ++; 
//			if (idx >= nums.length) {
//				idx = 0;
//			}
//		}
//		return nums;
//	}
	
	/* 
	 * prints grid
	 */
	public String toString() {
		int formatSpaces = (int) (Math.log10(Math.pow(this.grid.length,2))+3);
		String print = "";
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid.length; j++) {
				print = print + String.format("%1$"+formatSpaces+"s", String.valueOf(this.grid[i][j])); 
			}
			print += "\n";
		}
		return print;
	}
	
	public static void main (String[] arg) {
		Puzzle puz = new Puzzle(4);
		System.out.print(puz);
		puz = new Puzzle(12);
		System.out.print(puz);
	} // end of main
}
