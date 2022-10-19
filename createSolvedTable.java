import java.util.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class createSolvedTable {

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
            char[] invalid_moves = {'R', '3', 'r'};
            return !contains(move2, invalid_moves);
        }  

        else if (move1 == 'B' || move1 == '4' || move1 == 'b') {
            char[] invalid_moves = {'B', '4', 'b'};
            return !contains(move2, invalid_moves);
        }

        else if (move1 == 'D' || move1 == '5' || move1 == 'd') {
            char[] invalid_moves = {'D', '5', 'd'};
            return !contains(move2, invalid_moves);
        }

        return true;
    }

    private static boolean contains(char checkFor, char[] checkIn) {
        for (int i = 0; i < checkIn.length; i++) {
            if (checkIn[i] == checkFor) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {

        char[] moves = {'U', '0', 'u', 'L', '1', 'l', 'F', '2', 'f', 'R', '3', 'r', 'B', '4', 'b', 'D', '5', 'd'};

        RubiksCube solvedcube = new RubiksCube();
        String solvedstate = solvedcube.getState();
        String solvedmoves = solvedcube.getMoves();
    
        Hashtable<String, String> solvedtable = new Hashtable<String, String>(100000, 0.75f);
    
        solvedtable.put(solvedstate, solvedmoves);

        LLQueue<String> nextToSearch = new LLQueue<String>();
        nextToSearch.insert(solvedstate + solvedmoves);

        int count = 0;

        int depth = 0;
    
        // while (depth < 8) {
        while (nextToSearch.getSize() > 0) {
            RubiksCube currentcube = new RubiksCube(nextToSearch.remove());
            String current_moves = currentcube.getMoves();

            count++;
            if (count % 100000 == 0) {
                System.out.print("count: ");
                System.out.println(count);
                System.out.print("queue size: ");
                System.out.println(nextToSearch.getSize());
            }


            if (current_moves.length() > depth) {
                depth = current_moves.length();
                System.out.print("depth: ");
                System.out.println(depth);
            }


            char prevMove = ' ';
            if (current_moves != "") {
                prevMove = current_moves.charAt(current_moves.length() - 1);
            }

            for (int i = 0; i < moves.length; i++) {
                char currentmove = moves[i];

                if (!validMove(prevMove, currentmove)) {
                    continue;
                }
                RubiksCube copycube = currentcube.getDeepcopyCube();
                copycube.move(currentmove);
                String copystate = copycube.getState();
                String copymoves = copycube.getMoves();

                if (!solvedtable.containsKey(copystate)) {
                    solvedtable.put(copystate, copymoves);
                }


                if (depth < 6) {
                    nextToSearch.insert(copycube.getState() + copycube.getMoves());
                }

            }


        }

        System.out.print("total states checked: ");
        System.out.println(count);
        System.out.print("final queue size: ");
        System.out.println(nextToSearch.getSize());
        System.out.print("number of entries in table: ");
        System.out.println(solvedtable.size());

        try {
            FileOutputStream fos = new FileOutputStream("six_away.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(solvedtable);
            oos.close();

        } catch (Exception e) {
            System.out.println("an exception occurred");
            System.out.println(e);
        }

        System.out.println("finished creating and saving table.");

    }
    





}
