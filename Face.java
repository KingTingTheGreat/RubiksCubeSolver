public class Face {
    
    private char[] colors;
    private char color;
    private char[] color_order = {'w', 'b', 'y', 'r', 'g', 'o', 'p', 't', 'i'};

    public Face (char[] face_colors) {
        if (face_colors.length != 9) {
            throw new IllegalArgumentException();
        }
        colors = face_colors;
        color = face_colors[4];
    }

    public char get(int index) {
        return colors[index];
    }

    public void set(int index, char color) {
        colors[index] = color;
    }

    public char faceColor() {
        return color;
    }

    public char[] getDeepcopyArr() {
        char[] deepcopy = new char[colors.length];
        for (int i = 0; i < colors.length; i++) {
            deepcopy[i] = colors[i];
        }
        return deepcopy;
    }

    public void clockwise() {
        //corners
        char c6 = colors[6];
        char c0 = colors[0];
        char c8 = colors[8];
        char c2 = colors[2];
        colors[0] = c6;
        colors[2] = c0;
        colors[6] = c8;
        colors[8] = c2;

        // edges 
        char e3 = colors[3];
        char e7 = colors[7];
        char e1 = colors[1];
        char e5 = colors[5];
        colors[1] = e3;
        colors[3] = e7;
        colors[5] = e1;
        colors[7] = e5;

        // center stays the same
    }

    public void counter() {
        // corners 
        char c2 = colors[2];
        char c8 = colors[8];
        char c0 = colors[0];
        char c6 = colors[6];
        colors[0] = c2;
        colors[2] = c8;
        colors[6] = c0;
        colors[8] = c6;

        // edges 
        char e5 = colors[5];
        char e1 = colors[1];
        char e7 = colors[7];
        char e3 = colors[3];
        colors[1] = e5;
        colors[3] = e1;
        colors[5] = e7;
        colors[7] = e3;

        // center stays the same
    }

    public void twoTurns() {
        // corners
        char c8 = colors[8];
        char c6 = colors[6];
        char c2 = colors[2];
        char c0 = colors[0];
        colors[0] = c8;
        colors[2] = c6;
        colors[6] = c2;
        colors[8] = c0;

        // edges 
        char e7 = colors[7];
        char e5 = colors[5];
        char e3 = colors[3];
        char e1 = colors[1];
        colors[1] = e7;
        colors[3] = e5;
        colors[5] = e3;
        colors[7] = e1;

        // ccenter stays the same;
    }

    public boolean check_solved() {
        char color = colors[0];
        for (int i = 1; i < colors.length; i++) {
            if (colors[i] != color) {
                return false;
            }
        }
        return true;
    }

    public boolean check_solved_mole() {
        boolean[] contains_color = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < colors.length; i++) {
            char current_color = colors[i];

            for (int j = 0; j < color_order.length; j++) {
                if (color_order[j] == current_color) {
                    if (contains_color[j]) {
                        return false;
                    }
                    contains_color[j] = true;
                    break; // breaks from the nested for-loop
                }
            }

        }
        return true;
    }



}
