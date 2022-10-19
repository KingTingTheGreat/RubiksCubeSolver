import java.util.Random;

public class SolverQueue {

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




    public static void main(String args[]) {

        char[] moves = {'U', '0', 'u', 'L', '1', 'l', 'F', '2', 'f', 'R', '3', 'r', 'B', '4', 'b', 'D', '5', 'd'};

        // char[] u = {'g', 'g', 'r', 'r', 'w', 'w', 'b', 'o', 'w'};
        // char[] l = {'r', 'b', 'w', 'r', 'o', 'y', 'g', 'w', 'y'};
        // char[] f = {'r', 'w', 'b', 'o', 'g', 'g', 'r', 'b', 'b'};
        // char[] r = {'o', 'b', 'w', 'o', 'r', 'g', 'o', 'w', 'o'};
        // char[] b = {'g', 'r', 'y', 'y', 'b', 'y', 'w', 'b', 'o'};
        // char[] d = {'b', 'o', 'y', 'r', 'y', 'g', 'y', 'y', 'g'};


        // debugging
        Random random = new Random();
        RubiksCube cube = new RubiksCube();
        char[] scramble = new char[5];
        String scramble_str = "";
        for (int i = 0; i < scramble.length; i++) {
            scramble[i] = moves[random.nextInt(18 )];
            String current_scramble_move = scramble[i] + "";
            while (i != 0 && !validMove(scramble[i - 1], scramble[i])) {
                scramble[i] = moves[random.nextInt(18)];
                current_scramble_move = scramble[i] + "";
            }
            scramble_str += convertToStandard(current_scramble_move);
            scramble_str += " ";

        }
        System.out.println(scramble_str);
        cube.manyMoves(scramble);
        cube.setNumMoves(0);  
        cube.setMoves("");

        System.out.println(cube);


        if (cube.check_solved()) {
            System.out.println("cube is already solved");
            return;
        }

        LLQueue<String> nextToSearch = new LLQueue<String>();
        // LLQueue<RubiksCube> nextToSearch = new LLQueue<RubiksCube>();

        nextToSearch.insert(cube.getData());

        int current_depth = cube.getNumMoves();
        int max_depth_sofar = -1;

        int counter = 0;

        long start = System.currentTimeMillis();
        //  change to depth first so memory limit isn't reached
        while (current_depth < 10) {

            boolean done = false;

            // System.out.println(nextToSearch.getSize());

            String data = nextToSearch.remove();
            RubiksCube current_cube = new RubiksCube(data);
            current_depth = current_cube.getNumMoves();


            char prevMove = ' ';
            if (current_cube.getMoves() != "") {
                String current_moves = current_cube.getMoves();
                prevMove = current_moves.charAt(current_moves.length() - 1);
            }

            if (current_depth > max_depth_sofar) {
                max_depth_sofar = current_depth;
                System.out.print("current depth: ");
                System.out.println(Integer.toString(current_depth));
            }

            for (int i = 0; i < moves.length; i++) {
                char current_move = moves[i];
                if (validMove(prevMove, current_move)) {

                    counter++;
                    if (counter % 500000 == 0) {
                        System.out.print("num searched: ");
                        System.out.println(counter);
                        System.out.print("queue size: ");
                        System.out.println(nextToSearch.getSize());
                    }

                    RubiksCube copyCube = current_cube.getDeepcopyCube();
                    copyCube.move(current_move);
                    

                    // // debugging
                    // if (copyCube.getMoves().equals("URur")) {
                    //     System.out.println("should be solved now");
                    //     System.out.println(copyCube.getData());
                    // }


                    if (copyCube.check_solved()) {
                    	long end = System.currentTimeMillis();
                    	double mult = 0.001;
                    	System.out.print("time to complete: ");
                    	System.out.print((end - start) * mult);
                    	System.out.println('s');
                    	
                        System.out.println("solved the cube");
                        System.out.print("scramble: ");
                        System.out.println(scramble_str);
                        System.out.print("solution: ");
                        System.out.println(copyCube.getMoves());
                        System.out.println(convertToStandard(copyCube.getMoves()));
                        done = true;
                        break;
                    }

                    nextToSearch.insert(copyCube.getData());

                }

            }

            if (done) {
                // add code to get solution from the cube. should be very easy
                break;
            }

        }

    }
}
