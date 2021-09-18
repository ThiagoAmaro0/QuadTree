import java.awt.Point;

public class Rectangle {
    public int x, y, width, height;
    public Rectangle(int _x, int _y, int _w, int _h) {
        x = _x;
        y = _y;
        width = _w; 
        height = _h;
    }

    public boolean Contains(Point p) {
        return(p.x >= x && p.x <= x + width &&
                p.y >= y && p.y <= y + height);
    }

    public boolean CheckCollision(Rectangle other ) {

        return (x + width > other.x && x< other.x + other.width &&
                other.y + other.height > y && other.y < y + height);
    }
}