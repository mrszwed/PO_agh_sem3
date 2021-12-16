package agh.ics.oop.gui;
import agh.ics.oop.Grass;
import agh.ics.oop.GrassField;
import agh.ics.oop.MapBoundary;
import agh.ics.oop.Vector2d;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Cell;
import javafx.scene.paint.Color;

import java.util.Vector;

public class MyCanvas extends Canvas {
    GrassField map;
    MyCanvas(GrassField map){
        super();
        this.map=map;
        setManaged(false);
    }
    MyCanvas(GrassField map, int width, int height){
        super(width,height);
        this.map=map;
        setManaged(false);
    }

    public boolean isResizable(){return true;}
    public double prefWidth(double height){return getWidth();}
    public double prefHeight(double width){return getHeight();}

    void draw(){
        GraphicsContext gc=this.getGraphicsContext2D();
        drawShapes(gc);
    }

    void redraw(){
        GraphicsContext gc=this.getGraphicsContext2D();
        gc.clearRect(0,0,getWidth(),getHeight());
        drawShapes(gc);
    }



    static final int CELL_SIZE = 40;
    void drawShapes(GraphicsContext gc){
        System.out.println(map.getBoundary().toString());
        System.out.println(map.toString());
//        gc.fillRect(0,0,getWidth(),getHeight());
        gc.setFill(Color.ALICEBLUE);
        gc.setStroke(Color.BLACK);
        MapBoundary b=map.getBoundary();
        int dx=-b.getMinX()+1;
        int dy=-b.getMinY()+1;
        for(int i=b.getMinY()-1; i<=b.getMaxY(); i++){
            for(int j=b.getMinX()-1; j<=b.getMaxX(); j++){
//                System.out.print("*");
                int x=(j+dx)*CELL_SIZE;
                int y=(i+dy)*CELL_SIZE;

                if(i==b.getMinY()-1 || j==b.getMinX()-1 )gc.setFill(Color.BISQUE);
                else  gc.setFill(Color.ALICEBLUE);
                gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);    //wypelnia

                gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);  //drukuje border
            }
//            System.out.println(": "+i);
        }
        for(int i=b.getMinX(); i<=b.getMaxX(); i++){
            int x=(i+dx)*CELL_SIZE;
            gc.strokeText(String.format("%d",i),x+15, 25);
        }
        int n=b.getMaxY()+b.getMinY();
//        n-=1;
        for(int j=b.getMinY(); j<=b.getMaxY(); j++){
            int y=(n-j+dy)*CELL_SIZE;
            gc.strokeText(String.format("%d",j),10, y+20);
        }
//        dx--;
//        dy--;
        for(int i=b.getMinX(); i<=b.getMaxX(); i++){
            for(int j=b.getMinY(); j<=b.getMaxY(); j++) {
                drawObject(gc,new Vector2d(i,j));
            }
        }
    }

    void drawObject(GraphicsContext gc, Vector2d position){
        if (!this.map.isOccupied(position))return;
        System.out.println("painting at "+position);
        Object object = this.map.objectAt(position);
        String rep=object.toString();   //reprezentacja obiektu
        MapBoundary b=map.getBoundary();
        int dx=-b.getMinX()+1;
        int dy=-b.getMinY()+1;
        int n=b.getMaxY()-b.getMinY();

        int x= (position.x+dx)*CELL_SIZE;
        int y= (n-(position.y+dy)+2)*CELL_SIZE;
        if(rep.equals("*") ){
            GrassPainter.draw(gc, x, y, CELL_SIZE-2,CELL_SIZE-2);
            gc.strokeText(position.toString(), x,y);
        }
        else{
            AnimalPainter.draw(gc,x,y,CELL_SIZE-2, CELL_SIZE-2,rep);
            gc.strokeText(position.toString(), x,y);
        }
    }

}
