import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.border.*;

public class ClosetabbedpaneTest extends JFrame
{
	public ClosetabbedpaneTest()
	{ 
		ColorPanel pane=new ColorPanel();
		pane.setVisible(true);		
		add(pane);
		this.pack();		
		pane.setColor(Color.RED);	
		Image img = pane.createImage(pane.getWidth(),pane.getHeight());        
        Graphics imgC = img.getGraphics();
        pane.paint(imgC);
        Icon icon = new ImageIcon(img);
        JButton click= new JButton(icon);
        add(click);
	 	pane.setVisible(true);
	}

   	public static void main(String args[])
	{
	    MainLayout ml=new MainLayout();
		ml.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		  
		ml.setSize(600,400);
		ml.setVisible(true);
	}
}
