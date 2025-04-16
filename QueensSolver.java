import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class QueensSolver {
    
    private static Random random = new Random();
    private static int BOARD_SIZE = 8; // Default, now changeable
    private static int requiredBits;
    
    public static void main(String[] args) {
        // Allow changing board size from command line
        if (args.length > 0) {
            try {
                BOARD_SIZE = Integer.parseInt(args[0]);
                if (BOARD_SIZE < 4) {
                    System.out.println("Board size must be at least 4. Using default size 8.");
                    BOARD_SIZE = 8;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid board size. Using default size 8.");
            }
        }
        
        // Calculate required bits for column representation
        requiredBits = (int) Math.ceil(Math.log(BOARD_SIZE) / Math.log(2));
        
        // Performance benchmarking parameters
        int[] iterationsToTest = {1000, 5000, 10000, 20000};
        
        System.out.println("Solving N-Queens Problem for board size: " + BOARD_SIZE);
        System.out.println("Maximum possible fitness: " + getMaxFitness());
        
        // Benchmark with different iteration counts
        for (int iterations : iterationsToTest) {
            runBenchmark(iterations);
        }
        
        // Final run with optimized iterations
        System.out.println("\n=== FINAL OPTIMIZED RUN ===");
        int optimizedIterations = BOARD_SIZE * BOARD_SIZE * 150; // Scale iterations with board size
        runDetailedSolution(optimizedIterations);
    }
    
    private static void runBenchmark(int iterations) {
        System.out.println("\n--- Testing with " + iterations + " iterations ---");
        
        Instant start = Instant.now();
        String solution = SA(iterations, false); // Run without verbose output
        Instant end = Instant.now();
        
        int fitness = EQ_fitness(solution);
        long timeMs = Duration.between(start, end).toMillis();
        double perfScore = (double)fitness / timeMs * 1000; // Performance score: fitness per second
        
        System.out.println("Time taken: " + timeMs + " ms");
        System.out.println("Final fitness: " + fitness + " / " + getMaxFitness());
        System.out.println("Performance score: " + String.format("%.2f", perfScore) + " fitness/sec");
        System.out.println("Found perfect solution: " + (fitness == getMaxFitness() ? "Yes" : "No"));
    }
    
    private static void runDetailedSolution(int iterations) {
        Instant start = Instant.now();
        String solution = SA(iterations, true); // Run with verbose output
        Instant end = Instant.now();
        
        int fitness = EQ_fitness(solution);
        long timeMs = Duration.between(start, end).toMillis();
        
        System.out.println("\nFinal Solution Stats:");
        System.out.println("Time taken: " + timeMs + " ms");
        System.out.println("Iterations: " + iterations);
        System.out.println("Final fitness: " + fitness + " / " + getMaxFitness());
        System.out.println("Solution: " + solution);
        
        // Display the board
        displayBoard(solution);
        
        // Display conflict metrics if not perfect
        if (fitness < getMaxFitness()) {
            analyzeConflicts(solution);
        }
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
                    String binaryFormat = String.format("%" + requiredBits + "s", binary).replace(" ", "0");
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
            String binaryFormat = String.format("%" + requiredBits + "s", binary).replace(" ", "0");
            sb.append(binaryFormat);
        }
        return sb.toString();
    }
    
    // Calculate maximum possible fitness
    public static int getMaxFitness() {
        // Maximum pairs of queens = n(n-1)/2
        int maxPairs = BOARD_SIZE * (BOARD_SIZE - 1) / 2;
        return maxPairs * 2; // Each pair has 2 potential conflict points
    }
    
    // Calculate the fitness of a solution (higher is better)
    public static int EQ_fitness(String s) {
        if (s == null || s.length() != BOARD_SIZE * requiredBits) {
            return 0; // Invalid solution
        }
        
        int clashes = 0;
        int[] queenCols = new int[BOARD_SIZE];
        
        // Convert binary strings to column positions
        for (int row = 0; row < BOARD_SIZE; row++) {
            String colBinary = s.substring(row * requiredBits, (row * requiredBits) + requiredBits);
            int col = Integer.parseInt(colBinary, 2);
            
            // Handle invalid column positions (can happen with bit mutations)
            if (col >= BOARD_SIZE) {
                col = col % BOARD_SIZE; // Wrap around to valid position
            }
            
            queenCols[row] = col;
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
        
        // Maximum number of possible clashes = n(n-1)/2 pairs × 2 (counting both potential clash types)
        return getMaxFitness() - (clashes * 2);
    }
    
    // Make a small change to a solution (bit flip mutation)
    public static String small_change(String s) {
        if (s == null || s.length() < requiredBits) {
            return initial_point();
        }
        
        char[] before = s.toCharArray();
        
        // Randomly select which queen (row) to modify
        int row = random.nextInt(BOARD_SIZE);
        int bitPosition = (row * requiredBits) + random.nextInt(requiredBits);
        
        // Flip the bit
        before[bitPosition] = (before[bitPosition] == '1') ? '0' : '1';
        
        return new String(before);
    }
    
    // Simulated annealing algorithm
    public static String SA(int iterations, boolean verbose) {
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
        int maxNoImprovement = Math.min(1000, BOARD_SIZE * BOARD_SIZE);
        int noImprovementCount = 0;
        
        // Tracking metrics
        int restarts = 0;
        int improvements = 0;
        int lastPrintedIteration = 0;
        
        // Start the simulated annealing process
        for (int i = 0; i < iterations; i++) {
            // Generate a neighboring solution
            String newSolution = small_change(currentSolution);
            int newFitness = EQ_fitness(newSolution);
            
            // Calculate acceptance probability
            double acceptanceProbability;
            if (newFitness > currentFitness) {
                acceptanceProbability = 1.0; // Always accept better solutions
                improvements++;
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
                    if (bestFitness == getMaxFitness()) {
                        if (verbose) {
                            System.out.println("Perfect solution found at iteration " + (i + 1));
                            System.out.println("Total improvements: " + improvements);
                            System.out.println("Total restarts: " + restarts);
                        }
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
                restarts++;
                
                // Hill climbing phase to improve the random restart
                for (int j = 0; j < BOARD_SIZE * 2; j++) {
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
                
                if (verbose && (restarts % 5 == 0 || restarts < 5)) {
                    System.out.println("Restarted at iteration " + (i + 1) + " with fitness " + currentFitness);
                }
            }
            
            // Cool down temperature
            currentTemp *= coolingRate;
            
            // Print progress periodically
            if (verbose && (i % (iterations/10) == 0 || i == iterations - 1) && i > lastPrintedIteration) {
                System.out.println("Iteration " + (i + 1) + 
                                  ", Temperature: " + String.format("%.6f", currentTemp) + 
                                  ", Best fitness: " + bestFitness + "/" + getMaxFitness() +
                                  ", Restarts: " + restarts);
                lastPrintedIteration = i;
            }
        }
        
        if (verbose) {
            System.out.println("Final metrics:");
            System.out.println("Total improvements: " + improvements);
            System.out.println("Total restarts: " + restarts);
            System.out.println("Improvement rate: " + String.format("%.2f", (double)improvements/iterations*100) + "%");
        }
        
        return bestSolution;
    }
    
    // Analyze conflicts in a solution
    public static void analyzeConflicts(String solution) {
        if (solution == null || solution.length() != BOARD_SIZE * requiredBits) {
            System.out.println("Invalid solution to analyze");
            return;
        }
        
        int[] queenCols = new int[BOARD_SIZE];
        
        // Convert binary strings to column positions
        for (int row = 0; row < BOARD_SIZE; row++) {
            String colBinary = solution.substring(row * requiredBits, (row * requiredBits) + requiredBits);
            int col = Integer.parseInt(colBinary, 2) % BOARD_SIZE;
            queenCols[row] = col;
        }
        
        int columnConflicts = 0;
        int diagonalConflicts = 0;
        
        System.out.println("\nConflict Analysis:");
        
        // Check for column and diagonal clashes
        for (int i = 0; i < BOARD_SIZE - 1; i++) {
            for (int j = i + 1; j < BOARD_SIZE; j++) {
                // Column clash
                if (queenCols[i] == queenCols[j]) {
                    columnConflicts++;
                    System.out.println("Column conflict: Queen at row " + i + " and Queen at row " + j + 
                                      " (both in column " + queenCols[i] + ")");
                }
                
                // Diagonal clash
                if (Math.abs(i - j) == Math.abs(queenCols[i] - queenCols[j])) {
                    diagonalConflicts++;
                    System.out.println("Diagonal conflict: Queen at (" + i + "," + queenCols[i] + ") and Queen at (" + 
                                      j + "," + queenCols[j] + ")");
                }
            }
        }
        
        System.out.println("Total column conflicts: " + columnConflicts);
        System.out.println("Total diagonal conflicts: " + diagonalConflicts);
    }
    
    // Display a solution as a chess board
    public static void displayBoard(String solution) {
        if (solution == null || solution.length() != BOARD_SIZE * requiredBits) {
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
            String colBinary = solution.substring(row * requiredBits, (row * requiredBits) + requiredBits);
            int col = Integer.parseInt(colBinary, 2) % BOARD_SIZE; // Handle overflow
            board[row][col] = 'Q';
        }
        
        // Display the board
        System.out.println("\nBoard representation:");
        
        // Column headers
        System.out.print("  ");
        for (int col = 0; col < BOARD_SIZE; col++) {
            System.out.print(col % 10 + " ");
        }
        System.out.println();
        
        // Top border
        System.out.print(" +");
        for (int col = 0; col < BOARD_SIZE; col++) {
            System.out.print("-+");
        }
        System.out.println();
        
        // Board with queens
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(row % 10 + "|");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(board[row][col] + "|");
            }
            System.out.println();
            
            // Row separator
            System.out.print(" +");
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print("-+");
            }
            System.out.println();
        }
    }
}