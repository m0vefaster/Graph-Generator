
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.Image;
import javax.imageio.*;
import java.awt.image.*;
	
public class ColorFrame extends JFrame
{
	ColorPanel colorPanel;

	ColorFrame()
	{
	colorPanel=new ColorPanel();
	colorPanel.setVisible(true);
	add(colorPanel);
	this.pack();
	setSize(50,42);
	}
	
 	public void setColor(Color colour)
 	{
 	 colorPanel.setColor(colour);
 	 colorPanel.repaint();
 	// colorPanel.revalidate();
 	// repaint();
 	}
 	
	public Image getImage()
	{
		return(colorPanel.getImage());
	}	

}


