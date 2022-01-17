package simulation;

import java.util.Random;

public class Orientation {  // enum nie byłby lepszy?
    int angle = 0;

    public String toString() {
        return Integer.toString(angle);
    }


    public Vector2d toUnitVector() {
        return switch (angle) {
            case 0 -> new Vector2d(1, 0);    // nowy wektor co wywołanie
            case 1 -> new Vector2d(1, 1);
            case 2 -> new Vector2d(0, 1);
            case 3 -> new Vector2d(-1, 1);
            case 4 -> new Vector2d(-1, 0);
            case 5 -> new Vector2d(-1, -1);
            case 6 -> new Vector2d(0, -1);
            case 7 -> new Vector2d(1, -1);
            default -> new Vector2d(0, 0);   // w takiej sytuacji tylko wyjątek

        };
    }

    public void rotate(int v) {
        angle += v;
        angle = angle % 8;
    }
}
