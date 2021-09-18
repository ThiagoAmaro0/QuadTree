import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
public class QuadTree {
    int capacity;
    Rectangle rect;
    ArrayList<Particles> particles;
    
    QuadTree ne;
    QuadTree nw;
    QuadTree se;
    QuadTree sw;
    
    boolean subdivided;
    
    public QuadTree(Rectangle _rect, int _capacity) {
        capacity = _capacity;
        rect = _rect;
        particles = new ArrayList<Particles>();

        subdivided = false;
    }
    
    void Subdivide() {
        subdivided = true;
        ne = new QuadTree(new Rectangle(rect.x + rect.width/2, rect.y, rect.width/2, rect.height/2), capacity);
        nw = new QuadTree(new Rectangle(rect.x, rect.y, rect.width/2, rect.height/2), capacity);
        se = new QuadTree(new Rectangle(rect.x + rect.width/2, rect.y + rect.height/2, rect.width/2, rect.height/2), capacity);
        sw = new QuadTree(new Rectangle(rect.x, rect.y + rect.height/2, rect.width/2, rect.height/2), capacity);
        
    }
    
    public void Insert(Particles p) {
        if(!rect.Contains(p.pos))
            return;
        
        if(particles.size()<capacity) {
            particles.add(p);
        }
        else {
            if(!subdivided) {
                Subdivide();
            }
            
            ne.Insert(p);
            nw.Insert(p);
            se.Insert(p);
            sw.Insert(p);
        }    
    }
    
    public ArrayList<Particles> Query(Rectangle r, ArrayList<Particles> list){
        
        if(list == null)
            list = new ArrayList<Particles>();
        if(!r.CheckCollision(rect))
            return null;
        
        for(Particles p : particles) {
            if(r.Contains(p.pos))
                list.add(p);
        }
        
        if(subdivided) {
            ne.Query(r, list);
            nw.Query(r, list);
            se.Query(r, list);
            sw.Query(r, list);
        }
        return list;
    }
    
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.white);
        g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
        if(subdivided) {
            ne.draw(g);
            nw.draw(g);
            se.draw(g);
            sw.draw(g);
        }
    }
}