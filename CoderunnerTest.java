import java.util.Random;

public class CoderunnerTest {
	
	public static void main(String[] args) {
		//char c = 'Q';
		//System.out.println(valid_char(c));
		
		int cols = 8;
		int rows = 8;
		char[][] board = new char[rows][cols];
		for (int row = 0; row < rows; row++) {
		    for (int col = 0; col < cols; col++) {
		        board[row][col] = '.';
		    }
		}
		
		board [0][7] = 'Q';
		board [1][6] = 'Q';
		board [2][5] = 'Q';
		board [3][4] = 'Q';
		board [4][3] = 'Q';
		board [5][2] = 'Q';
		board [6][1] = 'Q';
		board [7][0] = 'Q';
			
		//System.out.println(valid_board(board));
		//System.out.println(binary_convert(board));
		
		//System.out.println(initial_point());
		
		//String solution = "011000100110000111000101"; // Example solution
	        
	   
	   // System.out.println(small_change(solution));
	    
	    String solution = SA(10000); // Example solution
	    System.out.println(solution);
	    
	    int fitness = EQ_fitness(solution);
	    System.out.println("Fitness: " + fitness);
	    
	    
	}
	
	//Q1 Check Character 
	public static boolean  valid_char(char c){
		if (c == '.') {
			return true;
		}
		if (c== 'Q'){
			return true;
		}
		return false;
	}
	
	//Q2 Valid Board 
	public static boolean  valid_board(char[][] input_board){
		if (input_board == null) {
			return false;
		}
		if (input_board.length != 8 || input_board[0].length != 8) {
			return false;
		}
		
		int rows = input_board.length;
		int cols = input_board[0].length;
		int qCount = 0;
		for (int row = 0; row < rows; row++) {
		    for (int col = 0; col < cols; col++) {
		    	char c = input_board[row][col];
		        if (c != '.'){
		        	if (c != 'Q'){
		        		return false;
		        	}
		        	if (c == 'Q'){
		        		qCount++;
		        	}
		        } 
		    }
		}
		if (qCount != 8) {
			return false;
		}
		
		return true;
	}
	
	//Q3 Generate Binary
	public static String binary_convert(char[][] input_board) {
		//if (input_board == null) {}
		String s = "";
		
		for (int row = 0; row < input_board.length; row++) {
		    for (int col = 0; col < input_board[0].length; col++) {
				if (input_board[row][col] == 'Q') {
			    	String binary = Integer.toBinaryString(col);
					String binaryFormat = String.format("%3s", binary).replace(" ", "0");
					s += binaryFormat;
				}
		    }
		}
		return s;
	}
	
	//Q4 Initial Starting Point 
	public static String initial_point() {
		String s = "";
		for (int i = 0; i < 8; i++) {
			Random random = new Random();
	        int randomNumber = random.nextInt(8);
	        String binary = Integer.toBinaryString(randomNumber);
			String binaryFormat = String.format("%3s", binary).replace(" ", "0");
			s += binaryFormat;
		}
		return s;
	}
	
	//Q5 Fitness Function 
	public static int EQ_fitness(String s) {
		int clashes = 0;
		String[] queenAt = new String[8];
		
        // Calculate the total number of clashes
        for (int i = 0; i < s.length(); i+=3) {
        	String q = s.substring(i, Math.min(s.length(), i + 3));
        	for (int j = 0; j < 8; j++) {
        		if (queenAt[j] != null && queenAt[j].equals(q)) {
        			clashes += 2;
        		}
        		
        		if ( queenAt[j] != null && q != null && Math.abs(j - i/3) == (Math.abs(Integer.parseInt(q, 2) - Integer.parseInt(queenAt[j], 2)))) {
        			clashes += 2;
        		}
        	}
        	queenAt[i/3] = q;
        }
        
        // Calculate the fitness value
        return 56 - clashes;
	}
	
	//Q6 Small Change
	public static String small_change(String s) {
		char[] before = s.toCharArray(); 
		Random random = new Random();
		int randomNumber = random.nextInt(24);

		if (before[randomNumber] == '1') {
		    before[randomNumber] = '0';
		} else if (before[randomNumber] == '0') {
		    before[randomNumber] = '1';
		}

		String after = new String(before); 
		return after;
	}
	
	//Q7 Solving the Problem
	public static String SA(int iter) {
		String s  = initial_point();
		if( iter > 1) {
			int f = EQ_fitness(s);
			Random random = new Random();
			
			double initial_temp = 5000;
			double Titer = 0.00001;
			double cooling = Math.pow(Titer / initial_temp, 1.0/iter);
			double current_temp = initial_temp;
			
			for(int i = 0; i<iter; i++) {
				String s2 = small_change(s);
				int f2 = EQ_fitness(s2);
			
				System.out.println("f: " + f);
				System.out.println("f2: " + f2 );
				
				if(f2 > f) {
					s = s2;
					f = f2;
				} else if (f2 <= f) {
					double pr = Math.exp(-(f2 -f) / current_temp);
					System.out.println("acceptance: " + pr);
					
					double random_number = random.nextDouble();
					if (!Double.isInfinite(pr) && pr > random_number) {
						s = s2;
						f = f2;
					}
				}
				System.out.println("current temp: " + current_temp);
				if(current_temp > 0) {
					current_temp = current_temp * cooling;	
					 if (current_temp < 0) {
						 current_temp = 0;
					 }
				}
				System.out.println("iteration: " + (i+1) + "\n");
			}
		}
		return s;
	}
}
