package Connect4;
import java.util.*;

public class Connect4 {

	public static void main(String[] args) {
		int rows = 6, column = 7;
		String[][] arr = new String[rows][column]; 
   
		for (int row = 0; row < arr.length; row++)
		{
			for (int col = 0; col < arr[row].length; col++)
		    {
		    	arr[row][col] = "."; 
		    }
		}
		
		arr[4][6] = "Y";
		arr[5][6] = "R";
		valid_board(arr);
		whose_move(arr);
	}
	
	public static boolean  valid_board_square(char valid_sq){
		switch(valid_sq) {
		  case 'Y':
			  System.out.print("T");
			  return true;	
		  case 'R':
			  System.out.print("T");
			  return true;
		  case '.':
			  System.out.print("T");
			  return true;
		  default:
			  System.out.print("F");
			  return false;
		}

	}
	
	public static boolean  valid_board(String[][] input_board){
		char c;
		boolean ans = true;
		if((input_board.length == 6) && (input_board[0].length == 7)) { // check if array is correct size
			for (int row = 0; row < input_board.length; row++){
				for (int col = 0; col < input_board[row].length; col++){ // use a for loop to check each cell of the array with valid_board_square method
					c = input_board[row][col].charAt(0); // check the char at the current position in the array
					if(valid_board_square(c) == false) { // if method returns false there is an invalid char and array is invalid
						ans = false;
						System.out.print(ans);
						return false;
				    } else if((c == 'R' || c == 'Y') && (row+1 < 6)  && (input_board[row+1][col].charAt(0) == '.')) { // if there is a valid piece on the board that is above the lowest point, check to see if it is floating, if it is the board is invalid
						ans = false;
						System.out.print(ans);
						return false;
				    }
				}
			}
		} else if (!(input_board.length == 6) || !(input_board[0].length == 7)) { // if the array is not the right size return false
			ans = false;
			System.out.print(ans);
			return false;
		}
		System.out.print(ans); // return true if board is has not already been returned false passing through above logic
		return ans;
	}
	
	public static boolean  valid_move(String[][] input_board, int column){
		boolean ans = true;
		if(!valid_board(input_board)) { // if not a valid board return false
			ans = false;
			System.out.print(ans);
			return false;
		} else if (valid_board(input_board) == true) { // otherwise if it is a valid board
			if((column > 6 || column < 0) || (input_board[0][column] != ".")) { // check if the int we are passed is less than 0 or higher than 6
				ans = false;													// then also check if the highest point of the given column is not a '.' if any of this is true, return false
				System.out.print(ans);
				return false;
			}
		}
		System.out.print(ans);
		return ans;
	}
	
	public static List<String> valid_moves(String[][] input_board){
		List<String> move_list = new ArrayList<String>();
		for (int col = 0; col < 7; col++) {
			if(valid_move(input_board,col) == true){
				move_list.add(Integer.toString(col));
			}
		}
		System.out.println(move_list);
		return move_list;
	}
	
	public static char whose_move(String[][] input_board){
		int y_count = 0;
		int r_count = 0;
		char ans = 'Y';
		if(!valid_board(input_board)) { // if not a valid board return false
			ans = '.';
			System.out.print(ans);
			return '.';
		} else if (valid_board(input_board) == true) { // otherwise if it is a valid board
			for (int row = 0; row < input_board.length; row++){
				for (int col = 0; col < input_board[row].length; col++){
					if(input_board[row][col] == "Y") {
						y_count++;
					} else if (input_board[row][col] == "R") {
						r_count++;
					}
				}
			}
		}
		if(y_count < r_count) {
			ans = 'Y';
			System.out.print(ans);
			return 'Y';
		} else if(y_count == r_count) {
			ans = 'R';
			System.out.print(ans);
			return 'R';
		} else if (y_count > r_count) {
			ans = '.';
			System.out.print(ans);
			return '.';
		}
		
		return '.';
	}
	
	
}
