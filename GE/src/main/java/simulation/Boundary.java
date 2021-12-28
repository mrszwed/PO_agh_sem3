package simulation;

public class Boundary {
    int xmin;
    int xmax;
    int ymin;
    int ymax;
    public int getWidth(){
        return xmax-xmin;
    }
    public int getHeight(){
        return ymax-ymin;
    }
    public boolean contains(Vector2d pos){
        if(pos.x>=xmin && pos.x<xmax && pos.y>=ymin && pos.y<ymax)return true;
        return false;
    }
    public boolean contains(int x,int y){
        if(x>=xmin && x<xmax && y>=ymin && y<ymax)return true;
        return false;
    }
    public String toString(){
        return String.format("(%d %d,%d %d) w:%d h%d",xmin,ymin,xmax,ymax,getWidth(),getHeight());
    }
}
