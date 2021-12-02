package agh.ics.oop;


import java.util.ArrayList;
import java.util.List;

public class MapBoundary implements IPositionChangeObserver{
    List<Vector2d> x=new ArrayList<>();
    List<Vector2d> y=new ArrayList<>();

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(oldPosition!=null) {
            x.remove(oldPosition);
            y.remove(oldPosition);
        }
        x.add(newPosition);
        y.add(newPosition);
        //nie da się porównać typu obiektu, ponieważ nie jest przekazywany przez interfejs
        x.sort((a,b) -> {
            int v=Integer.compare(a.x,b.x);
            if(v!=0)return v;
            return Integer.compare(a.y,b.y);
        });
        y.sort((a,b) -> {
            int v=Integer.compare(a.y,b.y);
            if(v!=0)return v;
            return Integer.compare(a.x,b.x);
        });
    }
}