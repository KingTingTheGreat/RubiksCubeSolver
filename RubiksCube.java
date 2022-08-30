public class RubiksCube {
    
    private Face up;
    private Face down;
    private Face right;
    private Face left;
    private Face front;
    private Face back;

    private String moves;

    private int num_moves;
 
    /*
     * constructor for RubiksCube class
     */
    public RubiksCube(char[] r, char[] l, char[] u, char[] d, char[] f, char[] b) {
        up = new Face(u);
        down = new Face(d);
        right = new Face(r);
        left = new Face(l);
        front = new Face(f);
        back = new Face(b);

        moves = "";
        num_moves = 0;
    }

    public boolean check_solved() {
        if (!right.check_solved()) {
            return false;
        }
        if (!left.check_solved()) {
            return false;
        }
        if (!up.check_solved()) {
            return false;
        }
        if (!down.check_solved()) {
            return false;
        }
        if (!front.check_solved()) {
            return false;
        }
        if (!back.check_solved()) {
            return false;
        }
        return true;
    }

    public int getNumMoves() {
        return num_moves;
    }

    public void setNumMoves(int number) {
        num_moves = number;
    }

    public String getMoves() {
        return moves;
    }

    public void setMoves(String newMoves) {
        moves = newMoves;
    }

    public RubiksCube getDeepcopyCube() {

        char[] r = right.getDeepcopyArr();
        char[] l = left.getDeepcopyArr();
        char[] u = up.getDeepcopyArr();
        char[] d = down.getDeepcopyArr();
        char[] f = front.getDeepcopyArr();
        char[] b = back.getDeepcopyArr();

        RubiksCube deepcopyCube = new RubiksCube(r, l, u, d, f, b);
        deepcopyCube.setMoves(getMoves());
        deepcopyCube.setNumMoves(getNumMoves());

        return deepcopyCube;
    }

    /*
     * turns the right side of this RubiksCube object
     */
    public void R(int i) {
        char f2 = front.get(2);
        char f5 = front.get(5);
        char f8 = front.get(8);

        char d2 = down.get(2);
        char d5 = down.get(5);
        char d8 = down.get(8);

        char b6 = back.get(6);
        char b3 = back.get(3);
        char b0 = back.get(0);

        char u8 = up.get(8);
        char u5 = up.get(5);
        char u2 = up.get(2);

        if (i == 1) {
            right.clockwise();

            up.set(2, f2);
            up.set(5, f5);
            up.set(8, f8);

            front.set(2, d2);
            front.set(5, d5);
            front.set(8, d8);

            down.set(2, b6);
            down.set(5, b3);
            down.set(8, b0);

            back.set(0, u8);
            back.set(3, u5);
            back.set(6, u2);
        }

        else if (i == 2) {
            right.twoTurns();

            up.set(2, d2);
            up.set(5, d5);
            up.set(8, d8);

            front.set(2, b6);
            front.set(5, b3);
            front.set(8, b0);

            down.set(2, u2);
            down.set(5, u5);
            down.set(8, u8);

            back.set(0, f8);
            back.set(3, f5);
            back.set(6, f2);
        }

        else if (i == 3) {
            right.counter();

            up.set(2, b6);
            up.set(5, b3);
            up.set(8, b0);

            front.set(2, u2);
            front.set(5, u5);
            front.set(8, u8);

            down.set(2, f2);
            down.set(5, f5);
            down.set(8, f8);

            back.set(0, d8);
            back.set(3, d5);
            back.set(6, d2);
        }
    }

    /*
     * turns the left side of this RubiksCube object
     */
    public void L(int i) {
        char b8 = back.get(8);
        char b5 = back.get(5);
        char b2 = back.get(2);

        char u0 = up.get(0);
        char u3 = up.get(3);
        char u6 = up.get(6);

        char f0 = front.get(0);
        char f3 = front.get(3);
        char f6 = front.get(6);
        
        char d6 = down.get(6);
        char d3 = down.get(3);
        char d0 = down.get(0);

        if (i == 1) {
            left.clockwise();

            up.set(0, b8);
            up.set(3, b5);
            up.set(6, b2);

            front.set(0, u0);
            front.set(3, u3);
            front.set(6, u6);

            down.set(0, f0);
            down.set(3, f3);
            down.set(6, f6);

            back.set(2, d6);
            back.set(5, d3);
            back.set(8, d0);
        }

        else if (i == 2) {
            left.twoTurns();

            up.set(0, d0);
            up.set(3, d3);
            up.set(6, d6);

            front.set(0, b8);
            front.set(3, b5);
            front.set(6, b2);

            down.set(0, u0);
            down.set(3, u3);
            down.set(6, u6);

            back.set(2, f6);
            back.set(5, f3);
            back.set(8, f0);
        }

        else if (i == 3) {
            left.counter();

            up.set(0, f0);
            up.set(3, f3);
            up.set(6, f6);

            front.set(0, d0);
            front.set(3, d3);
            front.set(6, d6);

            down.set(0, b8);
            down.set(3, b5);
            down.set(6, b2);

            back.set(2, u6);
            back.set(5, u3);
            back.set(8, u0);
        }
    }

    /*
     * turns the up side of this RubiksCube object
     */
    public void U(int i) {
        char b0 = back.get(0);
        char b1 = back.get(1);
        char b2 = back.get(2);

        char r0 = right.get(0);
        char r1 = right.get(1);
        char r2 = right.get(2);

        char f0 = front.get(0);
        char f1 = front.get(1);
        char f2 = front.get(2);

        char l0 = left.get(0);
        char l1 = left.get(1);
        char l2 = left.get(2);

        if (i == 1) {
            up.clockwise();

            right.set(0, b0);
            right.set(1, b1);
            right.set(2, b2);

            front.set(0, r0);
            front.set(1, r1);
            front.set(2, r2);

            left.set(0, f0);
            left.set(1, f1);
            left.set(2, f2);


            back.set(0, l0);
            back.set(1, l1);
            back.set(2, l2);
        }

        if (i == 2) {
            up.twoTurns();

            right.set(0, l0);
            right.set(1, l1);
            right.set(2, l2);

            front.set(0, b0);
            front.set(1, b1);
            front.set(2, b2);

            left.set(0, r0);
            left.set(1, r1);
            left.set(2, r2);

            back.set(0, f0);
            back.set(1, f1);
            back.set(2, f2);
        }

        else if (i == 3) {
            up.counter();

            right.set(0, f0);
            right.set(1, f1);
            right.set(2, f2);

            front.set(0, l0);
            front.set(1, l1);
            front.set(2, l2);

            left.set(0, b0);
            left.set(1, b1);
            left.set(2, b2);

            back.set(0, r0);
            back.set(1, r1);
            back.set(2, r2);
        }
    }
    
    /*
     * turns the down side of this RubiksCube object
     */
    public void D(int i) {
        char f6 = front.get(6);
        char f7 = front.get(7);
        char f8 = front.get(8);

        char l6 = left.get(6);
        char l7 = left.get(7);
        char l8 = left.get(8);
        
        char b6 = back.get(6);
        char b7 = back.get(7);
        char b8 = back.get(8);

        char r6 = right.get(6);
        char r7 = right.get(7);
        char r8 = right.get(8);

        if (i == 1) {
            down.clockwise();

            right.set(6, f6);
            right.set(7, f7);
            right.set(8, f8);

            front.set(6, l6);
            front.set(7, l7);
            front.set(8, l8);

            left.set(6, b6);
            left.set(7, b7);
            left.set(8, b8);

            back.set(6, r6);
            back.set(7, r7);
            back.set(8, r8);
        }

        else if (i == 2) {
            down.twoTurns();

            right.set(6, l6);
            right.set(7, l7);
            right.set(8, l8);

            front.set(6, b6);
            front.set(7, b7);
            front.set(8, b8);

            left.set(6, r6);
            left.set(7, r7);
            left.set(8, r8);

            back.set(6, f6);
            back.set(7, f7);
            back.set(8, f8);
        }

        else if (i == 3) {
            down.counter();

            right.set(6, b6);
            right.set(7, b7);
            right.set(8, b8);

            front.set(6, r6);
            front.set(7, r7);
            front.set(8, r8);

            left.set(6, f6);
            left.set(7, f7);
            left.set(8, f8);

            back.set(6, l6);
            back.set(7, l7);
            back.set(8, l8);
        }
    }

    /*
     * turns the front side of this RubiksCube object
     */
    public void F(int i) {
        char l2 = left.get(2);
        char l5 = left.get(5);
        char l8 = left.get(8);

        char r6 = right.get(6);
        char r3 = right.get(3);
        char r0 = right.get(0);

        char u6 = up.get(6);
        char u7 = up.get(7);
        char u8 = up.get(8);

        char d0 = down.get(0);
        char d1 = down.get(1);
        char d2 = down.get(2);

        if (i == 1) {
            front.clockwise();

            up.set(6, l8);
            up.set(7, l5);
            up.set(8, l2);

            down.set(0, r6);
            down.set(1, r3);
            down.set(2, r0);

            right.set(0, u6);
            right.set(3, u7);
            right.set(6, u8);

            left.set(2, d0);
            left.set(5, d1);
            left.set(8, d2);
        }

        else if (i == 2) {
            front.twoTurns();

            up.set(6, d2);
            up.set(7, d1);
            up.set(8, d0);

            down.set(0, u8);
            down.set(1, u7);
            down.set(2, u6);

            right.set(0, l8);
            right.set(3, l5);
            right.set(6, l2);

            left.set(2, r6);
            left.set(5, r3);
            left.set(8, r0);
        }

        else if (i == 3) {
            front.counter();

            up.set(6, r0);
            up.set(7, r3);
            up.set(8, r6);

            down.set(0, l2);
            down.set(1, l5);
            down.set(2, l8);

            right.set(0, d2);
            right.set(3, d1);
            right.set(6, d0);

            left.set(2, u8);
            left.set(5, u7);
            left.set(8, u6);
        }
    }

    /*
     * turns the back side of this RubiksCube object
     */
    public void B(int i) {
        char r2 = right.get(2);
        char r5 = right.get(5);
        char r8 = right.get(8);

        char l0 = left.get(0);
        char l3 = left.get(3);
        char l6 = left.get(6);

        char d8 = down.get(8);
        char d7 = down.get(7);
        char d6 = down.get(6);

        char u2 = up.get(2);
        char u1 = up.get(1);
        char u0 = up.get(0);

        if (i == 1) {
            back.clockwise();

            up.set(0, r2);
            up.set(1, r5);
            up.set(2, r8);

            down.set(6, l0);
            down.set(7, l3);
            down.set(8, l6);

            right.set(2, d8);
            right.set(5, d7);
            right.set(8, d6);

            left.set(0, u2);
            left.set(3, u1);
            left.set(6, u0);
        }

        else if (i == 2) {
            back.twoTurns();

            up.set(0, d8);
            up.set(1, d7);
            up.set(2, d6);

            down.set(6, u2);
            down.set(7, u1);
            down.set(8, u0);

            right.set(2, l6);
            right.set(5, l3);
            right.set(8, l0);

            left.set(0, r8);
            left.set(3, r5);
            left.set(6, r2);
        }

        else if (i == 3) {
            back.counter();

            up.set(0, l6);
            up.set(1, l3);
            up.set(2, l0);

            down.set(6, r8);
            down.set(7, r5);
            down.set(8, r2);

            right.set(2, u0);
            right.set(5, u1);
            right.set(8, u2);

            left.set(0, d6);
            left.set(3, d7);
            left.set(6, d8);
        }
    }

    /* 
     * calls the appropriate side method from the six above based on input
     */
    public void move(String move) {

        moves += move;
        num_moves++;

        if (move.equals("R"))
            R(1);
        if (move.equals("R2"))
            R(2);
        if (move.equals("R*"))
            R(3);

        if (move.equals("L"))
            L(1);
        if (move.equals("L2"))
            L(2);
        if (move.equals("L*"))
            L(3);
        
        if (move.equals("U"))
            U(1);
        if (move.equals("U2"))
            U(2);
        if (move.equals("U*"))
            U(3);

        if (move.equals("D"))
            D(1);
        if (move.equals("D2"))
            D(2);
        if (move.equals("D*"))
            D(3);
        
        if (move.equals("F"))
            F(1);
        if (move.equals("F2"))
            F(2);
        if (move.equals("F*"))
            F(3);
        
        if (move.equals("B"))
            B(1);
        if (move.equals("B2"))
            B(2);
        if (move.equals("B*"))
            B(3);
    }

    /*
     * allows a list of moves to be inputted
     */
    public void manyMoves(String[] moves) {
        for (int i = 0; i < moves.length; i++) {
            move(moves[i]);
        }
    }

    /*
     * calls the appropriate side method from the six that the move() method calls to undo the input
     */
    public void undo(String move) {

        num_moves--;
        if (moves.charAt(moves.length() - 1) == '*' || moves.charAt(moves.length() - 1) == '2') {
            moves = moves.substring(moves.length() - 2);
        }
        else {
            moves = moves.substring(moves.length() - 1);
        }

        if (move.equals("R"))
            R(3);
        if (move.equals("R2"))
            R(2);
        if (move.equals("R*"))
            R(1);

        if (move.equals("L"))
            L(3);
        if (move.equals("L2"))
            L(2);
        if (move.equals("L*"))
            L(1);
        
        if (move.equals("U"))
            U(3);
        if (move.equals("U2"))
            U(2);
        if (move.equals("U*"))
            U(1);

        if (move.equals("D"))
            D(3);
        if (move.equals("D2"))
            D(2);
        if (move.equals("D*"))
            D(1);
        
        if (move.equals("F"))
            F(3);
        if (move.equals("F2"))
            F(2);
        if (move.equals("F*"))
            F(1);
        
        if (move.equals("B"))
            B(3);
        if (move.equals("B2"))
            B(2);
        if (move.equals("B*"))
            B(1);
    }


    public static void main(String args[]) {

        String[] moves = {"U", "U*", "U2", "R", "R*", "R2", "L", "L*", "L2", "F", "F*", "F2", "B", "B*", "B2", "D", "D*", "D2"};

        char[] r = {'b', 'r', 't', 'i', 't', 'b', 'i', 't', 'w'};
        char[] l = {'o', 'w', 'g', 'p', 'i', 'p', 'y', 'o', 'g'};
        char[] u = {'o', 'y', 't', 'w', 'w', 'r', 'g', 'r', 'b'};
        char[] d = {'g', 'p', 'i', 'o', 'b', 't', 'y', 'r', 'w'};
        char[] f = {'g', 'r', 'b', 'p', 'y', 'i', 'g', 'p', 'i'};
        char[] b = {'t', 'y', 'o', 'b', 'o', 'p', 'w', 'r', 'y'};

        RubiksCube cube = new RubiksCube(r, l, u, d, f, b);


        if (cube.check_solved()) {
            System.out.println("cube is already solved");
            return;
        }



        // LLQueue<RubiksCube> nextToSearch = new LLQueue<RubiksCube>();

        // nextToSearch.insert(cube);

        // int depth = cube.getNumMoves();

        // //  change to depth first so memory limit isn't reached
        // while (true) {

        //     boolean done = false;

        //     // System.out.println(nextToSearch.getSize());

        //     RubiksCube current_cube = nextToSearch.remove();

        //     char prevFace = ' ';
        //     if (current_cube.getMoves() != "") {
        //         prevFace = current_cube.getMoves().charAt(current_cube.getMoves().length() - 1);
        //         if (prevFace == '*' || prevFace == '2') {
        //             prevFace = current_cube.getMoves().charAt(current_cube.getMoves().length() - 2);
        //         }
        //     }

        //     if (depth < current_cube.getNumMoves()) {
        //         depth = current_cube.getNumMoves();
        //         System.out.print("current depth: ");
        //         System.out.println(Integer.toString(depth));
        //     }

        //     for (int i = 0; i < moves.length; i++) {
        //         if (moves[i].charAt(0) != prevFace) {
        //             RubiksCube copycube = current_cube.getDeepcopyCube();
        //             copycube.move(moves[i]);
        //             if (copycube.check_solved()) {
        //                 System.out.println(copycube.getMoves());
        //                 done = true;
        //                 break;
        //             }
        //             nextToSearch.insert(copycube);
        //         }
        //     }

        //     if (done) {
        //         break;
        //     }

        // }

        
        LLStack<RubiksCube> nextToSearch = new LLStack<RubiksCube>();

        nextToSearch.push(cube);

        int depth = cube.getNumMoves();

        int num_searched = 0;

        boolean done = false;

        while (!done) {

            // boolean done = false;

            // System.out.println(nextToSearch.getSize());

            RubiksCube current_cube = nextToSearch.pop();
            num_searched++;
            System.out.println(num_searched);

            char prevFace = ' ';
            if (current_cube.getMoves() != "") {
                prevFace = current_cube.getMoves().charAt(current_cube.getMoves().length() - 1);
                if (prevFace == '*' || prevFace == '2') {
                    prevFace = current_cube.getMoves().charAt(current_cube.getMoves().length() - 2);
                }
            }

            if (depth != current_cube.getNumMoves()) {
                depth = current_cube.getNumMoves();
                System.out.print("current depth: ");
                System.out.println(Integer.toString(depth));
            }

            for (int i = 0; i < moves.length; i++) {
                String currentMove = moves[i];
                char currentFace = currentMove.charAt(0);

                // don't turn the same face twice in a row
                if (currentFace == prevFace) {
                    continue;
                }

                // always do R before L, F before B, and U before D
                if (prevFace == 'R' && currentFace == 'L') {
                    continue;
                }
                if (prevFace == 'F' && currentFace == 'B') {
                    continue;
                }
                if (prevFace == 'U' && currentFace == 'D') {
                    continue;
                } 

                RubiksCube copycube = current_cube.getDeepcopyCube();
                    copycube.move(moves[i]);
                    if (copycube.check_solved()) {
                        System.out.println(copycube.getMoves());
                        done = true;
                        break;
                    }
                    if (copycube.getNumMoves() < 18) {
                        nextToSearch.push(copycube);
                    }
            }
            if (done) {
                break;
            }
        }
    }
}



