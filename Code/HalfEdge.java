import java.awt.*;

public class HalfEdge
{
    public int x1,x2,y1,y2;

    HalfEdge()
    {
    }

    public void setX1(int x)
    {
        x1=x;
    }

    public void setY1(int y)
    {
        y1=y;
    }

    public void setX2(int x)
    {
        x2=x;
    }

    public void setY2(int y)
    {
        y2=y;
    }


    public void drawHalfEdge(Graphics g)
    {
    	Graphics2D g2=(Graphics2D)g;     	
    	g2.setPaint(Color.YELLOW);
    	g2.setStroke(new BasicStroke(8));	
    		
        g2.drawLine(x1, y1, x2, y2);
        System.out.println("Here");
        
    }
}