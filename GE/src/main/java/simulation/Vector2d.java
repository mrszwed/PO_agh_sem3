package simulation;

public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString(){
        StringBuilder s=new StringBuilder();
        s.append("(");
        s.append(x);
        s.append(",");
        s.append(y);
        s.append(")");
        return s.toString();
    }

    Vector2d add(Vector2d other){
        return new Vector2d(x+other.x, y+other.y);
    }

    public int hashCode(){
        return Integer.hashCode(x)+7727*Integer.hashCode(y);
    }

    public boolean equals(Object other){
        if(this==other)return true;
        if (!(other instanceof Vector2d))return false;
        Vector2d vother=(Vector2d)other;
        if(x==vother.x && y==vother.y)return true;
        return false;
    }
    Vector2d opposite(){
       return new Vector2d(-x,-y);
    }

}
