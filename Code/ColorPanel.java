

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Image;
import javax.imageio.*;
import java.awt.image.*;
	


public class ColorPanel extends JPanel
{
	private Color colour;
    
    public ColorPanel()
    {
    	//setSize(200,200);
    	setBackground(Color.WHITE);
        setVisible(true);
        }
      
    
    
 public void paintComponent(Graphics g)
 {
 	super.paintComponent(g);
 	//this.setBackground(Color.WHITE);
 	
 	g.setColor(Color.BLACK);
 	g.drawRect(0,0,this.getWidth(),this.getHeight());
 	g.setColor(colour);
 //	g.setColor(Color.BLUE);
 	g.fillRect(1,1,(this.getWidth()-2),(this.getHeight()-2));
 	 repaint();
 	 } 
    
    public void setColor(Color newcolour)
    {
     colour=newcolour;
     repaint();	
	}
	
	public Color getColor()
	{
		return(colour);
	}
	
	public Image getImage()
	{
	//	this.pack();
        Image img = this.createImage(this.getWidth(),this.getHeight());
        Graphics imgG = img.getGraphics();
        this.paint(imgG);
		return(img);
	}

}