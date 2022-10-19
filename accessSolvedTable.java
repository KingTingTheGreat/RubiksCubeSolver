import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Random;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class accessSolvedTable {
    
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

    private static String undoMoves(String moves) {
        String undo = "";
        for (int i = moves.length() - 1; i >= 0; i--) {
            undo += getOppositeMove(moves.charAt(i));
        }
        return undo;
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


    public static void main(String[] args) {

        Hashtable<String, String> table = new Hashtable<String, String>();

        long start = System.currentTimeMillis();

        try {

            System.out.println("going to get table from file");

            FileInputStream fis = new FileInputStream("seven_away.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);

            table = (Hashtable<String, String>) ois.readObject();
            ois.close();

            System.out.println("obtained table from file");

        } catch (Exception e) {
            System.out.println("an exception occurred");
            System.out.println(e);
        }

        long end = System.currentTimeMillis();

        System.out.println("time to access hashtable: ");
        System.out.println((end - start) * 0.001);

        // int max_depth = 0;

        // Enumeration<String> enu = table.keys();

        // while (enu.hasMoreElements()) {
        //     String current_state = enu.nextElement();   // states are the keys in this hashtable
            
        //     String moves = table.get(current_state);

        //     if (moves.length() > max_depth) {
        //         max_depth = moves.length();
        //         System.out.print("new max depth found: ");
        //         System.out.println(max_depth);
        //     }
        // }

        char[] moves = {'U', '0', 'u', 'L', '1', 'l', 'F', '2', 'f', 'R', '3', 'r', 'B', '4', 'b', 'D', '5', 'd'};

        boolean success = true;

        long total_time = 0;
        int total_completed = 0;

        for (int i = 0; i < 1000; i++ ) {


            System.out.print(i);
            System.out.print(": ");

            RubiksCube cube = new RubiksCube();
            char[] scramble = getRandomScramble(moves, 7);
            cube.manyMoves(scramble);
            cube.setNumMoves(0);  
            cube.setMoves("");

            long s = System.currentTimeMillis();

            String foundScramble = "";
            if (table.containsKey(cube.getState())) {
                foundScramble = table.get(cube.getState());
            }

            long e = System.currentTimeMillis();

            total_time += (e - s);
            
            String solution = undoMoves(foundScramble);

            cube.manyMoves(solution);

            success = cube.check_solved();

            if (!success) {
                System.out.println("failed a test");
                System.out.println(scramble);
                System.out.println(solution);
                break;
            }

            total_completed++;

        }

        System.out.print("average time to get solution: ");
        total_time *= 0.001;
        System.out.println(total_time / total_completed);

    }




}
