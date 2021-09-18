import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Canvas extends JFrame {
    public Rectangle rect;
    QuadTree qTree;
    int particleCount = 500; //100=364.47  200=1229.12    300=2597.01   400=4439.65  500=7080.76
    Random rand;
    ArrayList<Particles> particles;
    Point point;
    boolean sync;
    float delta;
    float med;
    int frames;
    public Canvas(Point _point){        
        point = _point;
        rect = new Rectangle(10, 31, point.x, point.y);            
        
        rand = new Random();
        particles = new ArrayList<Particles>();
        for(int i = 0; i < particleCount; i++) {
            Point p = new Point(rand.nextInt(point.x) ,rand.nextInt(point.y));
            particles.add(new Particles(p));
        }
        
        ActionListener update = new ActionListener() 
        {
            public void actionPerformed(ActionEvent ev)
            {   
                if(sync) {
                    sync = false;
                    qTree = new QuadTree(rect,4);
                    for(Particles p : particles) {
                        p.Update();
                    }
                    for(Particles p : particles) {
                        qTree.Insert(p);
                    }
                    frames++;
                    med += delta;
                    if(frames == 100) {
                        med = med/frames;
                        System.out.println(med);
                    }
                    repaint();
                }
            }
        };  
        new Timer(40, update).start();
        
        setTitle("QuadTree");
        setSize(point.x+20, point.y+42);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(qTree != null) {
            delta = 0;        
            Graphics2D g2d = (Graphics2D) g;
            g2d.setBackground(Color.black);
            g2d.clearRect(0, 0, getSize().width, getSize().height);
        
            g2d.setStroke(new BasicStroke(0));
            for(Particles p : particles) {
                for(Particles other : qTree.Query(new Rectangle(p.pos.x-50,p.pos.y-50,100,100), null)) {
                    //for(Particles other : particles) {
                    delta++;
                    if(p != other && p.CheckCollision(other)) {
                        p.SetColor(Color.blue);
                        other.SetColor(Color.blue);
                    }
                    else {
                        p.SetColor(Color.red);
                        other.SetColor(Color.red);
                    }
                }
                p.Draw(g2d);
            }
        }
        sync  =true;
    }
}