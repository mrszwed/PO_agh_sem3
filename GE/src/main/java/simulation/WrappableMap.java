package simulation;

public class WrappableMap extends RectangularMap{

    WrappableMap(int height, int width, int jungleHeight, int jungleWidth) {
        super(height, width, jungleHeight, jungleWidth);
    }

    @Override
    void applyBoundaryConstraint(Animal a, Vector2d previousPosition){
        int x=a.position.x;
        int y=a.position.y;
        if(a.position.x< getMapBoundary().xmin)x= getMapBoundary().xmax-1;
        if(a.position.y< getMapBoundary().ymin)y= getMapBoundary().ymax-1;
        if(a.position.x>= getMapBoundary().xmax)x= getMapBoundary().xmin;
        if(a.position.y>= getMapBoundary().ymax)y= getMapBoundary().ymin;
        if(x!=a.position.x || y!=a.position.y)a.position=new Vector2d(x,y);
    }
}
