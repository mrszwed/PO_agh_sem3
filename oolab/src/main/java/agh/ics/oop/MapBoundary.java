package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    TreeSet<Vector2d>x=new TreeSet<>((a,b) -> {
            int v=Integer.compare(a.x,b.x);
            if(v!=0)return v;
            return Integer.compare(a.y,b.y);
        });
    TreeSet<Vector2d>y=new TreeSet<>((a,b) -> {
            int v=Integer.compare(a.y,b.y);
            if(v!=0)return v;
            return Integer.compare(a.x,b.x);
        });

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(oldPosition!=null) {
            x.remove(oldPosition);
            y.remove(oldPosition);
        }
        x.add(newPosition);
        y.add(newPosition);

    }
    public int getMaxX(){
        return x.last().x;
    }
    public int getMinX(){
        return x.first().x;
    }
    public int getMaxY(){
        return y.last().y;
    }
    public int getMinY(){
        return y.first().y;
    }

    public String toString(){
        StringBuilder s=new StringBuilder();
        s.append("[");
        s.append(getMinX());
        s.append(" , ");
        s.append(getMinY());
        s.append(" , ");
        s.append(getMaxX());
        s.append(" , ");
        s.append(getMaxY());
        s.append("]");
        return s.toString();
    }
}