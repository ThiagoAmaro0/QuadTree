import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

public class Particles {
    Point pos;
    int vX;
    int vY;
    Random rand;
    int rad = 2;
    Color color;
    Particles lastContact;
    public Particles(Point _pos) {
        rand = new Random();
        pos = _pos;
        vX = rand.nextInt(3) - 1;
        vY = rand.nextInt(3) - 1;
        while(vX == 0 && vY == 0) {
            vX = rand.nextInt(3) - 1;
            vY = rand.nextInt(3) - 1;
        }
        color = Color.red;
    }
    
    public void Update() {
        pos = new Point(vX+pos.x, vY+pos.y);
        if((pos.x <= Main.canvas.rect.x && vX < 0)|| (pos.x >= Main.canvas.rect.width + Main.canvas.rect.x && vX > 0))
            vX *= -1;
        if((pos.y <= Main.canvas.rect.y && vY < 0)|| (pos.y >= Main.canvas.rect.height + Main.canvas.rect.y && vY > 0))
            vY *= -1;
        
    }
    
    public boolean CheckCollision(Particles p ) {
        float dist = Dist(p.pos, pos);
        if(dist<=rad*3 && p!=lastContact) {
            lastContact = p;
            vX*=-1;
            vY*=-1;
            return true;
        }
        else
            return false;
    }
    
    float Dist(Point p1, Point p2) {
        return (float)Point.distance(p1.x+rad, p1.y+rad, p2.x+rad, p2.y+rad);
    }
    
    public void Draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(pos.x - rad, pos.y - rad, rad*2, rad*2);
    }
    
    public void SetColor(Color c) {
        color = c;
    }
}