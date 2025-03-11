import java.util.Random;

public class QueensSolver {
    
    private static final int BOARD_SIZE = 8;
    private static final Random random = new Random();
    
    public static void main(String[] args) {
        // Run the optimized simulated annealing algorithm
        String solution = SA(10000);
        System.out.println("Best solution: " + solution);
        
        // Display the solution fitness
        int fitness = EQ_fitness(solution);
        System.out.println("Fitness: " + fitness + " (max possible: 56)");
        
        // Display the solution as a chess board
        displayBoard(solution);
    }
    
    // Check if character is valid for the board
    public static boolean valid_char(char c) {
        return c == '.' || c == 'Q';
    }
    
    // Check if the board is valid
    public static boolean valid_board(char[][] input_board) {
        if (input_board == null) {
            return false;
        }
        if (input_board.length != BOARD_SIZE || input_board[0].length != BOARD_SIZE) {
            return false;
        }
        
        int qCount = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                char c = input_board[row][col];
                if (c != '.') {
                    if (c != 'Q') {
                        return false;
                    }
                    qCount++;
                } 
            }
        }
        return qCount == BOARD_SIZE;
    }
    
    // Convert board to binary string representation
    public static String binary_convert(char[][] input_board) {
        if (!valid_board(input_board)) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < BOARD_SIZE; row++) {
            boolean foundQueen = false;
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (input_board[row][col] == 'Q') {
                    String binary = Integer.toBinaryString(col);
                    String binaryFormat = String.format("%3s", binary).replace(" ", "0");
                    sb.append(binaryFormat);
                    foundQueen = true;
                    break;
                }
            }
            // Ensure every row has a queen
            if (!foundQueen) {
                return null; // Invalid board - missing queen in a row
            }
        }
        return sb.toString();
    }
    
    // Generate a random initial solution
    public static String initial_point() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            int randomCol = random.nextInt(BOARD_SIZE);
            String binary = Integer.toBinaryString(randomCol);
            String binaryFormat = String.format("%3s", binary).replace(" ", "0");
            sb.append(binaryFormat);
        }
        return sb.toString();
    }
    
    // Calculate the fitness of a solution (higher is better)
    public static int EQ_fitness(String s) {
        if (s == null || s.length() != BOARD_SIZE * 3) {
            return 0; // Invalid solution
        }
        
        int clashes = 0;
        int[] queenCols = new int[BOARD_SIZE];
        
        // Convert binary strings to column positions
        for (int row = 0; row < BOARD_SIZE; row++) {
            String colBinary = s.substring(row * 3, (row * 3) + 3);
            queenCols[row] = Integer.parseInt(colBinary, 2);
        }
        
        // Check for column and diagonal clashes
        for (int i = 0; i < BOARD_SIZE - 1; i++) {
            for (int j = i + 1; j < BOARD_SIZE; j++) {
                // Column clash
                if (queenCols[i] == queenCols[j]) {
                    clashes++;
                }
                
                // Diagonal clash
                if (Math.abs(i - j) == Math.abs(queenCols[i] - queenCols[j])) {
                    clashes++;
                }
            }
        }
        
        // Maximum number of possible clashes = 28 non-row clashes × 2 (counting both queens in clash)
        return 56 - (clashes * 2);
    }
    
    // Make a small change to a solution (bit flip mutation)
    public static String small_change(String s) {
        if (s == null || s.length() < 3) {
            return initial_point();
        }
        
        char[] before = s.toCharArray();
        
        // Randomly select which queen (row) to modify
        int row = random.nextInt(BOARD_SIZE);
        int bitPosition = (row * 3) + random.nextInt(3);
        
        // Flip the bit
        before[bitPosition] = (before[bitPosition] == '1') ? '0' : '1';
        
        return new String(before);
    }
    
    // Simulated annealing algorithm
    public static String SA(int iterations) {
        // Generate initial solution
        String currentSolution = initial_point();
        String bestSolution = currentSolution;
        int currentFitness = EQ_fitness(currentSolution);
        int bestFitness = currentFitness;
        
        // Parameters for simulated annealing
        double initialTemp = 100.0;
        double finalTemp = 0.001;
        double coolingRate = Math.pow(finalTemp / initialTemp, 1.0 / iterations);
        double currentTemp = initialTemp;
        
        // Number of iterations without improvement before restarting
        int maxNoImprovement = 1000;
        int noImprovementCount = 0;
        
        // Start the simulated annealing process
        for (int i = 0; i < iterations; i++) {
            // Generate a neighboring solution
            String newSolution = small_change(currentSolution);
            int newFitness = EQ_fitness(newSolution);
            
            // Calculate acceptance probability
            double acceptanceProbability;
            if (newFitness > currentFitness) {
                acceptanceProbability = 1.0; // Always accept better solutions
            } else {
                // For worse solutions, calculate probability based on temperature
                acceptanceProbability = Math.exp((newFitness - currentFitness) / currentTemp);
            }
            
            // Decide whether to accept the new solution
            if (acceptanceProbability > random.nextDouble()) {
                currentSolution = newSolution;
                currentFitness = newFitness;
                
                // Update best solution if current is better
                if (currentFitness > bestFitness) {
                    bestSolution = currentSolution;
                    bestFitness = currentFitness;
                    noImprovementCount = 0; // Reset counter when we find improvement
                    
                    // Early termination if we found a perfect solution (no clashes)
                    if (bestFitness == 56) {
                        System.out.println("Perfect solution found at iteration " + (i + 1));
                        break;
                    }
                } else {
                    noImprovementCount++;
                }
            } else {
                noImprovementCount++;
            }
            
            // Restart from a new random solution if stuck
            if (noImprovementCount >= maxNoImprovement) {
                String restartSolution = initial_point();
                int restartFitness = EQ_fitness(restartSolution);
                
                // Hill climbing phase to improve the random restart
                for (int j = 0; j < 100; j++) {
                    String neighbor = small_change(restartSolution);
                    int neighborFitness = EQ_fitness(neighbor);
                    
                    if (neighborFitness > restartFitness) {
                        restartSolution = neighbor;
                        restartFitness = neighborFitness;
                    }
                }
                
                currentSolution = restartSolution;
                currentFitness = restartFitness;
                noImprovementCount = 0;
                
                // If the restart found a better solution, update best
                if (currentFitness > bestFitness) {
                    bestSolution = currentSolution;
                    bestFitness = currentFitness;
                }
                
                System.out.println("Restarted at iteration " + (i + 1) + " with fitness " + currentFitness);
            }
            
            // Cool down temperature
            currentTemp *= coolingRate;
            
            // Print progress periodically
            if (i % 1000 == 0 || i == iterations - 1) {
                System.out.println("Iteration " + (i + 1) + 
                                  ", Temperature: " + String.format("%.6f", currentTemp) + 
                                  ", Best fitness: " + bestFitness);
            }
        }
        
        return bestSolution;
    }
    
    // Display a solution as a chess board
    public static void displayBoard(String solution) {
        if (solution == null || solution.length() != BOARD_SIZE * 3) {
            System.out.println("Invalid solution to display");
            return;
        }
        
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
        
        // Initialize empty board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = '.';
            }
        }
        
        // Place queens
        for (int row = 0; row < BOARD_SIZE; row++) {
            String colBinary = solution.substring(row * 3, (row * 3) + 3);
            int col = Integer.parseInt(colBinary, 2);
            board[row][col] = 'Q';
        }
        
        // Display the board
        System.out.println("\nBoard representation:");
        System.out.println("  0 1 2 3 4 5 6 7");
        System.out.println(" +-+-+-+-+-+-+-+-+");
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + "|");
            }
            System.out.println("\n +-+-+-+-+-+-+-+-+");
        }
    }
}