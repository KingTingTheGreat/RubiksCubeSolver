// import java.util.Enumeration;
// import java.util.Random;
import java.util.*;

public class SolverHashtable {

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


    private static String printSolutionSolo(RubiksCube solvedcube) {
        String solution = solvedcube.getMoves();
        String solution_standard = convertToStandard(solution);
        System.out.print("solution: ");
        System.out.println(solution_standard);
        return solution;
    }


    private static String printSolutionTables(RubiksCube solvedcube, boolean f_prog, Hashtable<String, String> current_table, Hashtable<String, String> other_table) {
        String solved_state = solvedcube.getState();

        String first_half = solvedcube.getMoves();
        String second_half = "";
        String reversed_secondhalf = other_table.get(solved_state).substring(1);

        if (!f_prog) {
            first_half = other_table.get(solved_state).substring(1);
            reversed_secondhalf = solvedcube.getMoves();
        }

        for (int i = reversed_secondhalf.length() - 1; i >= 0; i--) {
            second_half += getOppositeMove(reversed_secondhalf.charAt(i));
        }

        String solution = first_half + second_half;
        String solution_standard = convertToStandard(solution);
        System.out.print("solution: ");
        System.out.println(solution_standard);
        
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

    
    private static Hashtable<String, String> createHashtable(RubiksCube cube) {
        String cube_key = cube.getState();
        String cube_value = "f" + cube.getMoves(); // the "f" indicates that we have not yet searched from this state
        
        Hashtable<String, String> hashtable = new Hashtable<String, String>(1000000, 0.75f);
        hashtable.put(cube_key, cube_value);
        return hashtable;
    }


    public static void main(String args[]) {

        char[] moves = {'U', '0', 'u', 'L', '1', 'l', 'F', '2', 'f', 'R', '3', 'r', 'B', '4', 'b', 'D', '5', 'd'};

        // char[] u = {'g', 'g', 'r', 'r', 'w', 'w', 'b', 'o', 'w'};
        // char[] l = {'r', 'b', 'w', 'r', 'o', 'y', 'g', 'w', 'y'};
        // char[] f = {'r', 'w', 'b', 'o', 'g', 'g', 'r', 'b', 'b'};
        // char[] r = {'o', 'b', 'w', 'o', 'r', 'g', 'o', 'w', 'o'};
        // char[] b = {'g', 'r', 'y', 'y', 'b', 'y', 'w', 'b', 'o'};
        // char[] d = {'b', 'o', 'y', 'r', 'y', 'g', 'y', 'y', 'g'};


        // testing on random scrambles
        RubiksCube cube = new RubiksCube();
        char[] scramble = getRandomScramble(moves, 5);
        String scramble_str = convertToStandard(String.valueOf(scramble));
        System.out.print("generated scramble: ");
        System.out.println(scramble_str);
        cube.manyMoves(scramble);
        cube.setNumMoves(0);  
        cube.setMoves("");

        System.out.println(cube);


        if (cube.check_solved()) {
            System.out.println("cube is already solved");
            return;
        }

        Hashtable<String, String> forward = createHashtable(cube);
        Hashtable<String, String> backward = createHashtable(new RubiksCube());


        int current_depth = cube.getNumMoves();
        // int max_depth_sofar = -1;


        int counter = 0;

        // boolean indicating which table is currently being progressed
        // true = forward, false = backward
        boolean f_prog = true;
        boolean done = false;

        String solution = "";

        long start = System.currentTimeMillis();

        while (current_depth <= 10) {

            System.out.println(f_prog);

            Enumeration<String> enu = forward.keys();
            Hashtable<String, String> current_table = forward;
            Hashtable<String, String> other_table = backward;
            if (f_prog) {
                System.out.println("forward table");
            }
            if (!f_prog) {
                System.out.println("backward table");
                enu = backward.keys();
                current_table = backward;
                other_table = forward;
            }

            System.out.println(current_table.size());
            System.out.println(other_table.size());

            while (enu.hasMoreElements()) {
                String current_state = enu.nextElement();
                String current_data = current_table.get(current_state);
                String current_moves = current_data.substring(1);

                // check if current cube has already been searched from
                if (current_data.charAt(0) == 't') {
                    continue;
                }

                counter++;
                if (counter % 100000 == 0) {
                    System.out.print("states checked: ");
                    System.out.println(counter);
                }

                RubiksCube current_cube = new RubiksCube(current_state + current_moves);

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
                    String copyCube_moves = copyCube.getMoves();

                    if (copyCube.check_solved() || other_table.containsKey(copyCube_state)) {   // a solution has been found
                        long end = System.currentTimeMillis();
                        double mult = 0.001;
                        System.out.print("time to complete: ");
                        System.out.print((end - start) * mult);
                        System.out.println('s');
                        
                        System.out.println("solved the cube\n\nscramble: " + scramble_str);
                        
                        // solved without using the other table
                        if (copyCube.check_solved()) {
                            System.out.println("did not use tables");
                            solution = printSolutionSolo(copyCube);
                            done = true;
                            break;
                        }

                        // solved using the other table
                        if (other_table.containsKey(copyCube_state)) {
                            System.out.println("used the tables");
                            solution = printSolutionTables(copyCube, f_prog, current_table, other_table);
                            done = true;
                            break;
                        }                    
                        done = true;
                        break;
                    }

                    current_table.put(copyCube_state, "f" + copyCube_moves);
                }

                if (done) {
                    break;
                }

                // could maybe change this to remove? do we need to keep this in the table? we might not
                current_table.replace(current_state, "t" + current_moves);
            }

            if (done) {
                break;
            }

            // flip f_prog so we check the other table on the next pass through
            f_prog = !f_prog;

        }

        System.out.println();
        cube.manyMoves(solution);
        System.out.print("length of solution: ");
        System.out.println(cube.getNumMoves());
        System.out.print("successfully solved: ");
        System.out.println(cube.check_solved());
        
    }
}
