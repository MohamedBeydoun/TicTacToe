import java.util.Scanner;
import java.util.*;

public class TicTacToe {

	//This method creates an nxn array
	public static char[][] createBoard(int n) {
		
		char[][] board = new char[n][n];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = ' ';
			}
		}
		return board;
	}

	//This method prints out a board based on a 2D array as input
	public static void printBoard(char[][] board) {
		
		for (int i = 0; i < board.length; i++) {
			System.out.print("+");
			for (int j = 0; j < board.length; j++) {
				if(j == board.length-1) {
					System.out.println("-+");
				}
				else
					System.out.print("-+");
			}
			
			for (int j = 0; j < board.length; j++) {
				if (j == board.length-1) {
					System.out.println("|" + board[i][j] + "|");
				}
				else {
					System.out.print("|" + board[i][j]);
				}
			}
			
			if (i == board.length-1) {
				System.out.print("+");
				for (int j = 0; j < board.length; j++) {
					if(j == board.length-1) {
						System.out.println("-+");
					}
					else
						System.out.print("-+");
				}
			}
		}
		
	}
	
	//this method writes a specific character at a specific location onto the board
	public static void writeOnBoard(char[][] board, char newCharacter, int x, int y) {
			board[x][y] = newCharacter;
	}
	
	//a method used in conjunction with writeOnBoard to verify input conditions
	public static boolean checkConditions(char[][] board, int x, int y) {
		
		if ((x > board.length - 1 || x < 0) && (y > board.length - 1 || y < 0)) {
			System.out.println("Error! row and column coordinates are out of bounds. Try again:");
			return false;
		}
		if (x > board.length - 1 || x < 0) {
			System.out.println("Error! row coordinate is out of bounds. Try again:");
			return false;
		}
		if (y > board.length - 1 || y < 0) {
			System.out.println("Error! column coordinate is out of bounds. Try again:");
			return false;
		}
		//if this condition is first, the method doesn't work (investigate)
		if (board[x][y] != ' ') {
			System.out.println("Error! This location is already in use. Try again:");
			return false;
		}
		else
			return true;
	}
	
	//this method takes as input a board and asks the player for their move, then verifies conditions
	//using the checkConditions method. If the conditions are clear, writeOnBoard is used to save the move.
	public static void getUserMove(char[][] board) {
		
		Scanner s = new Scanner(System.in);
		
		int x = 0;
		int y = 0;
		boolean safetyX = false;
		boolean safetyY = false;
		boolean checkConditions = false;
		
		while (checkConditions == false) {
			do {
				try {
					System.out.println("row coordinate: ");
					x = s.nextInt();
					safetyX = true;
				} 
				catch (Exception e) {
					System.out.println("The value you entered is not an integer, try again");
					s.next();
				}
			} while (safetyX == false);
			
			do {
				try {
					System.out.println("column coordinate: ");
					y = s.nextInt();
					safetyY = true;
				} 
				catch (Exception e) {
					System.out.println("The value you entered is not an integer, try again");
					s.next();
				}
			} while (safetyY == false);
			
			if (checkConditions(board, x, y) == false)
				continue;
			checkConditions = true;
		}
		writeOnBoard(board, 'x', x, y);
	}
	
	//this helper method takes a 2D array and isolates a selected row
	//into a 1D array.
	public static char[] horizontal (char[][] board, int row) {
		char[] horizontal = new char[board.length];
		for (int i = 0; i < board.length; i++) {
			horizontal[i] = board[row][i];
			}
		return horizontal;
	}
	
	//this helper method takes a 2D array and isolates a selected column
	//into a 1D array.
	public static char[] vertical (char[][] board, int column) {
		char[] vertical = new char[board.length];
		for (int i = 0; i < board.length; i++) {
			vertical[i] = board[i][column];
			}
		return vertical;
	}
	
	//this helper method takes a 2D array and isolates the diagonal-up array.
	public static char[] diagonalDown (char[][] board) {
		char[] diagonalDown = new char[board.length];
		for (int i = 0; i < board.length; i++) {				
			diagonalDown[i] = board[i][i];
		}
		return diagonalDown;
	}
	
	//this helper method takes a 2D array and isolates the diagonal-down array.
	public static char[] diagonalUp (char[][] board) {
		char[] diagonalUp = new char[board.length];
		for (int i = board.length - 1; i >= 0; i--) {
			for (int j = 0; j < board.length; j++) {
				if (j == board.length - (i+1))
					diagonalUp[i] = board[i][j];
			}
		}
		return diagonalUp;
	}
	
	//this method checks for an obvious move for either winning or blocking
	//e.g. if the opponent has two characters on the first row, the AI will block the 3rd spot.
	public static boolean checkForObviousMove(char[][] board) {
		
		//wins
		for (int i = 0; i < board.length; i++) {
			//checking for horizontal wins
			char[] row = horizontal(board, i);
			int counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (row[j] == 'o')
					counter++;
			}
			if (counter == board.length - 1) {
				int x = i;
				int y = -1;
				for (int j = 0; j < board.length; j++) {
					if (row[j] == ' ')
						y = j;
				}
				if (y != -1) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
		
			//checking for vertical wins
			char[] column = vertical(board, i);
			counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (column[j] == 'o')
					counter++;
			}
			if (counter == board.length - 1) {
				int x = -1;
				int y = i;
				for (int j = 0; j < board.length; j++) {
					if (column[j] == ' ')
						x = j;
				}
				if (x != -1) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
				
			//checking diagonalDown wins
			char[] diagonalDown = diagonalDown(board);
			counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (diagonalDown[j] == 'o') 
					counter++;
			}
			if (counter == board.length - 1) {
				int x = -1;
				int y = -1;
				for (int j = 0; j < board.length; j++) {
					if (diagonalDown[j] == ' ') {
						x = j;
						y = j;
					}
				}
				if ((x != -1) && (y != -1)) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
				
			//checking diagonalUp wins
			char[] diagonalUp = diagonalUp(board);
			counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (diagonalUp[j] == 'o') 
					counter++;
			}
			if (counter == board.length - 1) {
				int x = -1;
				int y = -1;
				for (int j = 0; j < board.length; j++) {
					if (diagonalUp[j] == ' ') {
						x = j;
						y = board.length - 1 - j;
					}
				}
				if ((x != -1) && (y != -1)) {
					writeOnBoard(board, 'x', x, y);
					return true;
				}
			}
		}
		//blocks
		for (int i = 0; i < board.length; i++) {
			//checking for horizontal blocks
			char[] row = horizontal(board, i);
			int counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (row[j] == 'x')
					counter++;
			}
			if (counter == board.length - 1) {
				int x = i;
				int y = -1;
				for (int j = 0; j < board.length; j++) {
					if (row[j] == ' ')
						y = j;
				}
				if (y != -1) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
		
			//checking for vertical blocks
			char[] column = vertical(board, i);
			counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (column[j] == 'x')
					counter++;
			}
			if (counter == board.length - 1) {
				int x = -1;
				int y = i;
				for (int j = 0; j < board.length; j++) {
					if (column[j] == ' ')
						x = j;
				}
				if (x != -1) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
				
			//checking diagonalDown blocks
			char[] diagonalDown = diagonalDown(board);
			counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (diagonalDown[j] == 'x') 
					counter++;
			}
			if (counter == board.length - 1) {
				int x = -1;
				int y = -1;
				for (int j = 0; j < board.length; j++) {
					if (diagonalDown[j] == ' ') {
						x = j;
						y = j;
					}
				}
				if ((x != -1) && (y != -1)) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
		
			//checking diagonalUp blocks
			char[] diagonalUp = diagonalUp(board);
			counter = 0;
			for (int j = 0; j < board.length; j++) {
				if (diagonalUp[j] == 'x') 
					counter++;
			}
			if (counter == board.length - 1) {
				int x = -1;
				int y = -1;
				for (int j = 0; j < board.length; j++) {
					if (diagonalUp[j] == ' ') {
						x = j;
						y = board.length - 1 - j;
					}
				}
				if ((x != -1) && (y != -1)) {
					writeOnBoard(board, 'o', x, y);
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean checkForEmptySpace(char[][] board, int x, int y) {
				if (board[x][y] == ' ') {
					return true;
		}
		return false;
	}
	
	//this method checks for obvious move using checkForObviousMoves then:
	//if there is an obvious move, the obvious move is played
	//if not,  random move is played depending on the board spot availability.
	public static void getAIMove(char[][] board) {
		
		if (checkForObviousMove(board) == true) {
		}
		else {
			int x = (int)(Math.random()*board.length);
			int y = (int)(Math.random()*board.length);
			while(checkForEmptySpace(board, x, y) == false) {
				x = (int)(Math.random()*board.length);
				y = (int)(Math.random()*board.length);
			}
			writeOnBoard(board, 'o', x, y);
		}
	}
	
	//this method finds a winner based on who reached board.legnth first
	//(horizontally, vertically, and diagonally).
	public static char checkForWinner(char[][] board) {
		
		char winner = ' ';
		
		//check for horizontal wins
		for (int i = 0; i < board.length; i++) {
			char[] rowWinner = horizontal(board, i);
			int counterO = 0;
			int counterX = 0;
			for (int j = 0; j < board.length; j++) {
				if (rowWinner[j] == 'o') 
					counterO++;
				if (rowWinner[j] == 'x')
					counterX++;
			}
			if (counterO == board.length) {
				winner = 'o';
				return winner;
			}
			if (counterX == board.length) {
				winner = 'x';
				return winner;
			}
		}
		//check for vertical wins
		for (int i = 0; i < board.length; i++) {
			char[] columnWinner = vertical(board, i);
			int counterO = 0;
			int counterX = 0;
			for (int j = 0; j < board.length; j++) {
				if (columnWinner[j] == 'o') 
					counterO++;
				if (columnWinner[j] == 'x')
					counterX++;
			}
			if (counterO == board.length) {
				winner = 'o';
				return winner;
			}
			if (counterX == board.length) {
				winner = 'x';
				return winner;
			}
		}
		
		//check for diagonal down wins
		char[] diagonalDownWinner = diagonalDown(board);
		int counterO = 0;
		int counterX = 0;
		for (int i = 0; i < board.length; i++) {
			if (diagonalDownWinner[i] == 'o') 
					counterO++;
			if (diagonalDownWinner[i] == 'x')
					counterX++;
			if (counterO == board.length) {
				winner = 'o';
				return winner;
			}
			if (counterX == board.length) {
				winner = 'x';
				return winner;
			}
		}
		//check for diagonal up wins
		char[] diagonalUpWinner = diagonalUp(board);
		counterO = 0;
		counterX = 0;
		for (int i = 0; i < board.length; i++) {
			if (diagonalUpWinner[i] == 'o') 
					counterO++;
			if (diagonalUpWinner[i] == 'x')
					counterX++;
			if (counterO == board.length) {
				winner = 'o';
				return winner;
			}
			if (counterX == board.length) {
				winner = 'x';
				return winner;
			}
		}
		return winner;
	}
	
	public static void play() {
		
		Scanner s = new Scanner(System.in);
		System.out.println("What is your name?");
		String name = s.next();
		System.out.println("Welcome " + name + "!");
		int n = 0;
		boolean safetyN = false;
		
		//check if the user entry for the board size is valid
		do {
			try {
				System.out.println("What board size would you like to play on?");
				n = s.nextInt();
				if (n < 1) {
					System.out.println("The value you entered is negative or invalid. Please Try again.");
					continue;
				}
				safetyN = true;
			}
			catch(Exception e) {
				System.out.println("The dimension you entered is not a number. Please try again.");
				s.next();
			}
		}while(safetyN == false);
		
		//simulating tic-tac-toe turns starting randomly by flipping a coin
		char[][] board = createBoard(n);
		int coinFlip = (int)(Math.random()*2);
		System.out.println("\nCoin toss rules: The AI is heads, the player is tails\nNote: the rows and columns start at 0, not 1\n");
		if(coinFlip == 0) {
			System.out.println("The result of the coin toss was tails, the player starts:");
			printBoard(board);
		}
		else {
			System.out.println("The result of the coin toss was heads, the AI starts:");
		}
		
		//if the coin flip is 0, the counter will start at 0 and the player will have
		//the first turn (next turn will be at every time the counter is even). If the coin flip
		//is 1, the AI starts and plays every time the counter odd. This keeps going until all 
		//board slots are taken (counter = (n*n) + coinFlip)
		for (int counter = coinFlip; counter < (n*n) + coinFlip; counter++) {
			if(counter%2 == 0) {
				System.out.println("Enter move coordinates");
				getUserMove(board);
				System.out.println("You played:");
				printBoard(board);
				if(checkForWinner(board) != ' ') {
					break;
				}
			}
			if (counter%2 != 0) {
				getAIMove(board);
				System.out.println("The AI played:");
				printBoard(board);
				if(checkForWinner(board) != ' ') {
					break;
				}
			}
		}
		
		//printing end-game message
		if (checkForWinner(board) == 'o') {
			System.out.println("\nYOU LOST! (against a weak little AI)");
		}
		else if (checkForWinner(board) == 'x') {
			System.out.println("\nYOU WON! (against a weak little AI)");
		}
		else {
			System.out.println("\nDRAW! (against a weak little AI)");
		}
	}
	
	public static void main(String[] args) {
		play();
	}
}

