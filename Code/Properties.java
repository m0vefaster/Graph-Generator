import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

public class Properties extends JPanel
{
//private JLabel index,colour,label,shape,weight,multipleselection;
private JTextField indexText,labelText,weightText;
private JComboBox shapeText;
private JButton colourText;
Node node;
Edge edge;
ColorPanel colorPanel=new ColorPanel();
Dimension d=new Dimension(100,30);
JLabel index=new JLabel("Index");
//index.setText("Index");
JLabel label=new JLabel("Label");
JLabel shape=new JLabel("Shape");
JLabel colour=new JLabel("Colour");
JLabel weight=new JLabel("Weight");
JLabel multipleselection=new JLabel("MULTIPLE ELEMENTS SELECTED");
String allShapes[]={"Circle","Square","Rectangle","Oval"};
ColorFrame colorFrame=new ColorFrame();
private GridBagLayout layout=new GridBagLayout();
private GridBagConstraints constraints=new GridBagConstraints();
TitledBorder title;

Properties()
{
	title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"PROPERTIES");
	title.setTitleJustification(TitledBorder.LEFT);
	title.setTitlePosition(TitledBorder.ABOVE_TOP);
	this.setBorder(title);
}

// super("Properties");
//Vector allSelectedObjects=graph.getAllSelectedObjects();
//		 colourText=new JButton();
	//	 colourText.setColor(new Color(node.getColor());
	
//			add(index);
//			add(indexText);
//			add(label);
//			add(labelText);
//			add(shape);
//			add(shapeText);
//			add(colour);
//			add(colourText); 

//}// if (allSelectedObjects.size()==0)
// removeAll();
// else
// if (allSelectedObjects.size()>1)
 
 
public void setProperties(Object obj) 
{System.out.println("htdhtdhdhdthdhddhbd");
 if (obj==null)
 {
   removeAll();
   add(multipleselection);
	System.out.println("Blank");
 }
 else if(obj instanceof Node)
 {node=(Node)obj;
 	System.out.println("hree i sannsldfn");
   //if((graph.checkCoordinates(me.getX(), me.getY())) instanceof Node)   
 	  removeAll();
	 // setLayout(new GridLayout(4,4));
		setLayout(layout);
   
   		//constraints.fill=GridBagConstraints.BOTH;
   		index.setPreferredSize(d);
   		label.setPreferredSize(d);
   		shape.setPreferredSize(d);
   		colour.setPreferredSize(d);
	    indexText=new JTextField();
	    indexText.setPreferredSize(d);
	   indexText.setText(""+node.getIndex());
		System.out.println(node.getIndex());
		 indexText.setEditable(false);
	  	 labelText=new JTextField(node.getLabel());
	  	 labelText.setPreferredSize(d);
		labelText.addActionListener(
                new ActionListener()
               {
                   public void actionPerformed(ActionEvent event)
                  {
					String s=labelText.getText();
					node.setLabel(s);         
                  }
               });
               
		System.out.println(node.getLabel());
		 shapeText=new JComboBox(allShapes);
		 shapeText.setSelectedIndex((node.getShape())-1);
		 shapeText.addItemListener(
		 	new ItemListener()
		 	{
		 	public void itemStateChanged(ItemEvent e)
		 	{
		 		if (e.getStateChange()==ItemEvent.SELECTED)
		 		{
		 			node.setShape((shapeText.getSelectedIndex())+1);
		 			
		 			}
		 	}	
		 	});
		 	
		 	colorFrame.setColor(node.getColor());
		 	Icon icon =new ImageIcon(colorFrame.getImage());
		 	colourText=new JButton(icon);
		 	colourText.setPreferredSize(d);
		 	colourText.addActionListener(
		 		new ActionListener()
		 		{
		 		public void actionPerformed(ActionEvent e)
		 		{
		 			Color colour=JColorChooser.showDialog(null, node.getLabel(), node.getColor());
		 			if (colour!=null)
		 			{
		 			node.setColor(colour);
		 			//node.repaint();
		 			colorFrame.setColor(colour);
		 			Icon icon =new ImageIcon(colorFrame.getImage());
		 			colourText.setIcon(icon);
		 			}}
		 		});
		 			
			addComponent(index,1,2,2,1);
			addComponent(indexText,1,6,2,1);
			addComponent(label,4,2,2,1);
			addComponent(labelText,4,6,2,1);
			addComponent(shape,1,10,2,1);
			addComponent(shapeText,1,13,2,1);
			addComponent(colour,4,10,2,1);
			addComponent(colourText,4,13,2,1);
			
			this.revalidate();
			}		
   else if (obj instanceof Edge)
	 
	 {
	 edge=(Edge)obj;
	  removeAll();
	  setLayout(layout);
   
   		//constraints.fill=GridBagConstraints.BOTH;
   		index.setPreferredSize(d);
   		label.setPreferredSize(d);
   		weight.setPreferredSize(d);
   		colour.setPreferredSize(d);
	    
     indexText=new JTextField(""+edge.getIndex());
		indexText.setPreferredSize(d);

	    indexText.setEditable(false);
	  	 labelText=new JTextField(edge.getLabel());
	  	 labelText.setPreferredSize(d);
	  	  labelText.addActionListener(
                new ActionListener()
               {
                   public void actionPerformed(ActionEvent event)
                  {
					String s=labelText.getText();
					edge.setLabel(s);         
                  }
               });
        
		 weightText=new JTextField(edge.getWeight());
		 weightText.setPreferredSize(d);
		 weightText.addActionListener(
                new ActionListener()
               {
                   public void actionPerformed(ActionEvent event)
                  {
					String s=weightText.getText();
					try
					{
					int n=Integer.parseInt(s);
					edge.setWeight(n); 
					}
					catch(NumberFormatException e)
					{
						
					}        
                  }  
               });
        
        weightText.addKeyListener(
        	new KeyListener()
        	{
        	   public void keyPressed(KeyEvent e)
        	   {
        	   	String s=e.getKeyText(e.getKeyCode());
        	   	 char c=s.charAt(0);
        	   	 int ascii=(int)c;
        	   	 //if(Integer.valueOf(e.getKeyText(e.getKeyCode())).intValue()<58 && Integer.valueOf(e.getKeyText(e.getKeyCode())).intValue()>47)
        	   	 if((ascii<65 || ascii>90))
        	   	 {
        	   	 	weightText.setText(weightText.getText());}
        	   	  else
        	   	  {
        	   	  	String p=weightText.getText();
        	   	  	weightText.setText(p.substring(0,(p.length())));
        	   	  }
        	   	  
        	   }
        	   
        	   public void keyReleased(KeyEvent e)
        	   {
        	   	 String s=e.getKeyText(e.getKeyCode());
        	   	 char c=s.charAt(0);
        	   	 int ascii=(int)c;
        	   	 //if(Integer.valueOf(e.getKeyText(e.getKeyCode())).intValue()<58 && Integer.valueOf(e.getKeyText(e.getKeyCode())).intValue()>47)
        	   	 if(ascii<65 || ascii>90)
        	   	 {
        	   	 	System.out.println("The ascii is "+ascii);
        	   	 	weightText.setText(weightText.getText());//+e.getKeyText(e.getKeyCode())); 
        	   	 	}
        	   	 else
        	   	 {
        	   	   	
        	   	 	System.out.println("The ascii is "+ascii);
        	   	   	String p=weightText.getText();
        	   	  	weightText.setText(p.substring(0,(p.length()-1)));
        	   	  	}
        	   	
        	   }
        	   
        	   public void keyTyped(KeyEvent e)
        	   {
        	   	System.out.println(weightText.getText()+"------"+e.getKeyChar()+"-------"+Integer.valueOf(e.getKeyChar()).intValue());
        	  	 if((Integer.valueOf(e.getKeyChar())).intValue()<65 || (Integer.valueOf(e.getKeyChar())).intValue()>90)
        	   	 {	System.out.println("======================"+weightText.getText());
        	   	 	weightText.setText(weightText.getText());//+e.getKeyChar());
        	   	 	 }
        	   	 else 
        	   	 	weightText.setText(weightText.getText());	 
        	   }	
        	}
        	
        	);
		 colorFrame.setColor(edge.getColor());
		 	Icon icon =new ImageIcon(colorFrame.getImage());
		 	colourText=new JButton(icon);
		 	colourText.setPreferredSize(d);
		 	colourText.addActionListener(
		 		new ActionListener()
		 		{
		 		public void actionPerformed(ActionEvent e)
		 		{
		 			Color colour=JColorChooser.showDialog(null, edge.getLabel(), edge.getColor());
		 			if (colour!=null)
		 			{
		 			edge.setColor(colour);
		 			//node.repaint();
		 			colorFrame.setColor(colour);
		 			Icon icon =new ImageIcon(colorFrame.getImage());
		 			colourText.setIcon(icon);
		 			}}
		 		});
			addComponent(index,1,2,2,1);
			addComponent(indexText,1,6,2,1);
			addComponent(label,4,2,2,1);
			addComponent(labelText,4,6,2,1);
			addComponent(weight,1,10,2,1);
			addComponent(weightText,1,13,2,1);
			addComponent(colour,4,10,2,1);
			addComponent(colourText,4,13,2,1);
			
			this.revalidate();
			
			}
	
		}
		
		public Image getImage()
	{
	//	this.pack();
		colorPanel.repaint();
        if (colorPanel.isDisplayable())
        	System.out.println("NNNNNNUUUUUULLLLLL");
        Image img = colorPanel.createImage(colorPanel.getWidth(),colorPanel.getHeight());
        System.out.println("The Colour at present is"+colour);
        if(colorPanel==null)
        	System.out.println("No colourPanel is not nullllll");	
        Graphics imgG = img.getGraphics();
        colorPanel.paint(imgG);
		return(img);  
	}
	
	public void addComponent(Component component,int row,int column,int width,int height)
	{
		constraints.gridx=column;
		constraints.gridy=row;
		constraints.gridwidth=width;
		constraints.gridheight=height;
		layout.setConstraints(component,constraints);
		add(component);
		
	}
}
