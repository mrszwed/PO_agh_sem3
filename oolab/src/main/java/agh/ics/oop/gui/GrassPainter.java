package agh.ics.oop.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GrassPainter {
    static Image img=new Image(GrassPainter.class.getResource("/grass.png").toString());

    static void draw(GraphicsContext gc, int x, int y, int w, int h){
        gc.drawImage(img, x,y,w,h);
    }
}
