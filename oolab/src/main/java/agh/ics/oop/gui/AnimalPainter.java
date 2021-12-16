package agh.ics.oop.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimalPainter {
    static Image img=new Image(GrassPainter.class.getResource("/lizard.png").toString());

    static void draw(GraphicsContext gc, int x, int y, int w, int h, String dir){
        double angle=switch(dir){
            case ">" -> 90;
            case "v" -> 180;
            case "<" -> -90;
            case "^" -> 0;
            default -> 0;
        };
        gc.save();
        gc.translate(x+w/2, y+h/2);
        gc.rotate(angle);
        gc.drawImage(img, -w/2,-h/2,w,h);
        gc.restore();
    }
}
