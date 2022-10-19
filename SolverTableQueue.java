// import java.util.Enumeration;
// import java.util.Random;
import java.util.*;
import java.io.*;

public class SolverTableQueue {

    /*
     * returns a boolean representing whether move2 is a "valid" move given the previous move, move1
     * a "valid" move is one that does not turn the same face and abides by the "face move order"
     * face move order: up before down, left before right, front before back
     */
    private static boolean validMove(char move1, char move2) {
        if (move1 == ' ') {
            return true;
        }

        if (move1 == 'U' || move1 == '0' || move1 == 'u') {
            char[] invalid_moves = {'U', '0', 'u'};
            return !contains(move2, invalid_moves);
        }

        else if (move1 == 'L' || move1 == '1' || move1 == 'l') {
            char[] invalid_moves = {'L', '1', 'l'};
            return !contains(move2, invalid_moves);
        }

        else if (move1 == 'F' || move1 == '2' || move1 == 'f') {
            char[] invalid_moves = {'F', '2', 'f'};
            return !contains(move2, invalid_moves);
        }
        
        else if (move1 == 'R' || move1 == '3' || move1 == 'r') {
            char[] invalid_moves = {'R', '3', 'r', 'L', '1', 'l'};
            return !contains(move2, invalid_moves);
        }  

        else if (move1 == 'B' || move1 == '4' || move1 == 'b') {
            char[] invalid_moves = {'B', '4', 'b', 'F', '2', 'f'};
            return !contains(move2, invalid_moves);
        }

        else if (move1 == 'D' || move1 == '5' || move1 == 'd') {
            char[] invalid_moves = {'D', '5', 'd', 'U', '0', 'u'};
            return !contains(move2, invalid_moves);
        }

        return true;
    }


    /*
     * returns boolean representing whether the char checkFor appears in the array checkIn
     */
    private static boolean contains(char checkFor, char[] checkIn) {
        for (int i = 0; i < checkIn.length; i++) {
            if (checkIn[i] == checkFor) {
                return true;
            }
        }
        return false;
    }


    private static String convertToStandard(String moves) {
        String standardized = "";
        for (int i = 0; i < moves.length(); i++) {
            char current = moves.charAt(i);
            if (current <= 90 && current >= 65)
                standardized += current;

            char[] double_turns = {'0', '1', '2', '3', '4', '5'};
            if (contains(current, double_turns)) {
                if (current == '0') 
                    standardized += "U2";
                else if (current == '1') 
                    standardized += "L2";
                else if (current == '2')
                    standardized += "F2";
                else if (current == '3') 
                    standardized += "R2";
                else if (current == '4') 
                    standardized += "B2";
                else if (current == '5') 
                    standardized += "D2";
            }

            if (current >= 97 && current <= 122) {
                current -= 32;
                standardized += (char) current;
                standardized += '*';
            }

            standardized += ' ';
        }
        return standardized;
    }

    /*
     * returns a char representing the opposite move of the input
     */
    private static char getOppositeMove(char move) {

        if (move == '0' || move == '1' || move == '2' || move == '3' || move == '4' || move == '5') 
            return move;
        int opposite;
        if (move <= 90) 
            opposite = move + 32;
        else 
            opposite = move - 32;
        return (char)opposite;
    }


    private static String printSolution(RubiksCube solvedcube, Hashtable<String, String> table) {
        String solved_state = solvedcube.getState();

        String first_half = solvedcube.getMoves();
        String second_half = "";
        String reversed_secondhalf = table.get(solved_state);

        for (int i = reversed_secondhalf.length() - 1; i >= 0; i--) {
            second_half += getOppositeMove(reversed_secondhalf.charAt(i));
        }

        String solution = first_half + second_half;
        // String solution_standard = convertToStandard(solution);
        // System.out.print("solution: ");
        // System.out.println(solution_standard);
        
        // debuggging
        // System.out.println("first half: " + first_half);
        // System.out.println("second half: " + second_half);

        return solution;
    }


    private static char[] getRandomScramble(char[] possible_moves, int length) {
        char[] scramble = new char[length];
        Random random = new Random();
        for (int i = 0; i < scramble.length; i++) {
            scramble[i] = possible_moves[random.nextInt(18)];
            while (i != 0 && !validMove(scramble[i - 1], scramble[i])) {
                scramble[i] = possible_moves[random.nextInt(18)];
            }
        } 
        return scramble;
    }

    
    private static Hashtable<String, String> getHashtable() {
        long start = System.currentTimeMillis();
        Hashtable<String, String> table = new Hashtable<String, String>();
        try {
            System.out.println("going to get table from file");

            FileInputStream fis = new FileInputStream("six_away.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);

            table = (Hashtable<String, String>) ois.readObject();
            ois.close();

            System.out.println("obtained table from file");

        } catch (Exception e) {
            System.out.println("an exception occurred");
            System.out.println(e);
        }
        long end = System.currentTimeMillis();
        double mult = 0.001;
        System.out.print("time to obtain table: ");
        System.out.println((end - start) * mult);
        return table;
    }


    private static RubiksCube getScrambledCube(char[] moves, int length) {
        RubiksCube cube = new RubiksCube();
        char[] scramble = getRandomScramble(moves, length);
        String scramble_str = convertToStandard(String.valueOf(scramble));
        cube.manyMoves(scramble);
        cube.setNumMoves(0);  
        cube.setMoves("");
        // System.out.print("generated scramble: ");
        // System.out.println(scramble_str);
        // System.out.println(cube);

        return cube;
    }


    private static String getSolution(RubiksCube cube, Hashtable<String, String> table, char[] moves, int max_depth) {
        if (cube.check_solved()) {
            System.out.println("cube is already solved");
            return cube.getMoves();
        }

        // check if the current scramble is already in the table; don't need to search from the cube if this is the case
        if (table.containsKey(cube.getState())) {
            String solution = printSolution(cube, table);
            return solution;
        }


        LLQueue<String> nextToSearch = new LLQueue<String>();
        nextToSearch.insert(cube.getData());
        int current_depth = cube.getNumMoves();

        while (current_depth <= max_depth) {

            String current_data = nextToSearch.remove();

            RubiksCube current_cube = new RubiksCube(current_data);
            String current_moves = current_cube.getMoves();

            current_depth = current_moves.length();

            char prevMove = ' ';
            if (current_moves != "") {
                prevMove = current_moves.charAt(current_moves.length() - 1);
            }

            for (int i = 0; i < moves.length; i++) {
                char next_move = moves[i];

                // check if the current move is valid according to the rules we've established
                if (!validMove(prevMove, next_move)) {
                    continue;
                }

                RubiksCube copyCube = current_cube.getDeepcopyCube();
                copyCube.move(next_move);
                String copyCube_state = copyCube.getState();

                if (copyCube.check_solved() || table.containsKey(copyCube_state)) {   // a solution has been found
                    
                    String solution = printSolution(copyCube, table);
                                    
                    return solution;
                }

                nextToSearch.insert(copyCube.getData());
            }

        }
        System.out.println("failed to find a solution");
        return "";
    }


    public static void main(String args[]) {

        char[] moves = {'U', '0', 'u', 'L', '1', 'l', 'F', '2', 'f', 'R', '3', 'r', 'B', '4', 'b', 'D', '5', 'd'};

        // char[] u = {'g', 'g', 'r', 'r', 'w', 'w', 'b', 'o', 'w'};
        // char[] l = {'r', 'b', 'w', 'r', 'o', 'y', 'g', 'w', 'y'};
        // char[] f = {'r', 'w', 'b', 'o', 'g', 'g', 'r', 'b', 'b'};
        // char[] r = {'o', 'b', 'w', 'o', 'r', 'g', 'o', 'w', 'o'};
        // char[] b = {'g', 'r', 'y', 'y', 'b', 'y', 'w', 'b', 'o'};
        // char[] d = {'b', 'o', 'y', 'r', 'y', 'g', 'y', 'y', 'g'};

        Hashtable<String, String> table = getHashtable();

        for (int i = 0; i < 100; i++) {
            // System.out.println();
            RubiksCube scrambledCube = getScrambledCube(moves, 12);

            String solution = getSolution(scrambledCube, table, moves, 6);

            scrambledCube.manyMoves(solution);

            boolean success = scrambledCube.check_solved();

            if (success) {
                System.out.println("successfully solved");
            }

            if (!success) {
                System.out.println("received incorrect solution");
                System.out.println("FAILFAILFAILFAILFAILFAILFAILFAILFAIL");
                break;
            }

        }


        // System.out.println();
        // cube.manyMoves(solution);
        // System.out.print("length of solution: ");
        // System.out.println(cube.getNumMoves());
        // System.out.print("successfully solved: ");
        // System.out.println(cube.check_solved());
        
    }
}
