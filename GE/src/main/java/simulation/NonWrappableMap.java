package simulation;

public class NonWrappableMap extends RectangularMap {
    NonWrappableMap(int height, int width, int jungleHeight, int jungleWidth) {
        super(height, width, jungleHeight, jungleWidth);
    }

    @Override
    void applyBoundaryConstraint(Animal a, Vector2d previousPosition){
        boolean outside=false;
        if(a.position.x< getMapBoundary().xmin)outside=true;
        if(a.position.y< getMapBoundary().ymin)outside=true;
        if(a.position.x>= getMapBoundary().xmax)outside=true;
        if(a.position.y>= getMapBoundary().ymax)outside=true;
        if(outside)a.position=previousPosition;
    }
}
