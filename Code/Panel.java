import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

public class Panel extends JPanel
{
	public double xval, yval,xval2, yval2,Id;	
	public boolean translateNode=false, isNode=false, isRectangle=false, halfEdge=false, isEdge=false,isDragged=false,left=false,isWeighted=false;
	public double percent,multiple=1;
	public String label,string;	
	public int x;
	
    public Graph graph;
    public CloseButtonTabbedPane tabPane=new CloseButtonTabbedPane();
    public Node node,node2;
    public Edge edge;
    public UserLog userLog;
    public UserToolBar userToolBar;        
	public Properties properties;	
           
    public JPopupMenu popupMenu1,popupMenu2;                         
    public JButton nodeName,edgeName,nodeColor,edgeColor,nodeDelete,edgeDelete,nodeLocation,edgeLocation,nodeIndex,edgeIndex,weight,shape;
    public Container container1,container2;
    public JTextField status;
   
    Panel(int newId,UserToolBar newUserToolBar)
    {    	
    	graph=new Graph();
    	node=new Node(graph.getDefaultShape(), graph.getNodeDefaultColor());
    	node2=new Node(graph.getDefaultShape(), graph.getNodeDefaultColor());
    	edge=new Edge(graph.getEdgeDefaultColor());
    	popupMenu1=new JPopupMenu();
    	popupMenu2=new JPopupMenu();
		properties=new Properties();
		userLog=new UserLog();        
        userToolBar=newUserToolBar;
        userToolBar.addGraph(graph,"graph"+Id);
        graph.setUserToolBarObject(userToolBar);
        graph.setUserLog(userLog);
        
        NameHandler nameHandler=new NameHandler();
        ColorHandler colorHandler=new ColorHandler();
        DeleteHandler deleteHandler=new DeleteHandler();
        
        status=new JTextField(20);
        status.setEditable(false);
        
        container1=new Container();
        container1.setLayout(new GridLayout(6,1));
        
        container2=new Container();
        container2.setLayout(new GridLayout(6,1));
        
        nodeName=new JButton("");
        nodeName.addActionListener(nameHandler);        
        nodeIndex=new JButton("");        
		nodeColor=new JButton("");
		nodeColor.addActionListener(colorHandler);		
		shape=new JButton("");
		nodeDelete=new JButton("Delete");
		nodeDelete.addActionListener(deleteHandler);
		nodeLocation=new JButton("");		

        container1.add(nodeName);
        container1.add(nodeIndex);
        container1.add(nodeColor);        
        container1.add(nodeLocation);
        container1.add(shape);
        container1.add(nodeDelete);		  
        popupMenu1.add(container1);                          
        
        edgeName=new JButton("");
        edgeName.addActionListener(nameHandler);
        edgeIndex=new JButton("");
        edgeColor=new JButton("");
        edgeColor.addActionListener(colorHandler);
        weight=new JButton("");
        edgeLocation=new JButton("");
        edgeDelete=new JButton("Delete");
        edgeDelete.addActionListener(deleteHandler);
        
        container2.add(edgeName);
        container2.add(edgeIndex);
        container2.add(edgeColor);
        container2.add(weight);
        container2.add(edgeLocation);
        container2.add(edgeDelete);
        popupMenu2.add(container2);
        
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
		add(status,BorderLayout.SOUTH); 					                                       
        
        
        shape.addActionListener(
        	new ActionListener()
        		{
        			public void actionPerformed(ActionEvent event)
        			{
        				string=JOptionPane.showInputDialog(null,"Enter the shape of the node\n1. Circle\n2. Square\n3. Rectangle\n4. Oval","Shape",
        														JOptionPane.INFORMATION_MESSAGE);
        				node.setShape(Integer.parseInt(string));
        				repaint();
        			}	
        		}
        	);
        	
        weight.addActionListener(
        	new ActionListener()
        	{
        		public void actionPerformed(ActionEvent event)
        		{
        			string=JOptionPane.showInputDialog(null,"Enter the weight of the edge","Weight",JOptionPane.INFORMATION_MESSAGE);
        			try
        			{
        				x=Integer.parseInt(string);
        			}
        			catch(NumberFormatException e)
        			{
        				JOptionPane.showMessageDialog(null,"Incorrect value entered","Error",JOptionPane.ERROR_MESSAGE);
        				x=1;
        			}
        			edge.setWeight(x);
        		}
        	}
        );
                                   
        
        addMouseMotionListener(
				new MouseMotionListener()
				{
					public void mouseMoved(MouseEvent me)
					{
						status.setText("X: "+(int)(me.getX()/multiple)+"Y: "+(int)(me.getY()/multiple));						
					}
					
					public void mouseDragged(MouseEvent me)
					{
						status.setText("X: "+(int)(me.getX()/multiple)+"Y: "+(int)(me.getY()/multiple));
						isDragged=true;
						if(isNode)
						{
							halfEdge=true;
							xval2=me.getX()/multiple;
							yval2=me.getY()/multiple;							
						}
						if(isRectangle)
						{
							xval2=me.getX()/multiple;
							yval2=me.getY()/multiple;							
						}
						if(translateNode)
						{
							node.translate((int)(me.getX()/multiple),(int)(me.getY()/multiple));
						}
						if(isEdge && edge.getSelected())
						{
							edge.setIsStraight(false);
							edge.setCtrX((int)(me.getX()/multiple));
							edge.setCtrY((int)(me.getY()/multiple));
						}
						repaint();						
					}
				}
		);
		
		addMouseListener(
				new MouseListener()
				{
					public void mouseClicked(MouseEvent me)
					{										
						if(me.getModifiersEx()==0)
						{
							if((graph.checkCoordinates(me.getX()/multiple, me.getY()/multiple))==null)
                            {
                                graph.createNode(me.getX()/multiple, me.getY()/multiple);                                                                                                                      
                            }
                            else if(graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple) instanceof Node)
                            {
                            	node=(Node)graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple);                            	
                            	if(node.isSelected)
                            		node.setSelected(false);
                            	else
                            	{                            	
                            		node.setSelected(true);                            		
                            		properties.setProperties(node);
                            	}                            	
                            }
                            else if(graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple) instanceof Edge)
                            {
                            	edge=(Edge)graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple);
                            	if(edge.isSelected)
                            		edge.setSelected(false);
                            	else
                            	{                            	
                            		edge.setSelected(true);
                            		properties.setProperties(edge);
                            	}
                            } 
                            	left=true;
						}
						else
						{
							if((graph.checkCoordinates(me.getX()/multiple, me.getY()/multiple)) instanceof Node)
                                    {
                                        node=(Node)(graph.checkCoordinates(me.getX()/multiple, me.getY()/multiple));
                                        isEdge=false;
                                        nodeName.setText(node.getLabel());
                                        nodeIndex.setText("Index: "+node.getIndex());
										nodeColor.setText("RGB: "+node.getColor().hashCode());                                     
										nodeLocation.setText(node.getx()+","+node.gety());
										
										if(node.getShape()==1)
											string="Circle";
										else if(node.getShape()==2)
											string="Square";
										else if(node.getShape()==3)
											string="Rectangle";
										else
											string="Oval";
										
										shape.setText(string);											
										popupMenu1.show(getRootPane(),(int)(me.getX()/multiple+30),(int)(me.getY()/multiple+50));
                                        repaint();
                                        
									}
									else if((graph.checkCoordinates(me.getX()/multiple, me.getY()/multiple)) instanceof Edge)
									{
                                        edge=((Edge)(graph.checkCoordinates(me.getX()/multiple, me.getY()/multiple)) );
                                        isEdge=true;
                                        edgeName.setText(edge.getLabel());
                                        edgeIndex.setText("Index: "+edge.getIndex());
                                        edgeColor.setText("RGB: "+edge.getColor().hashCode());
                                        weight.setText(""+edge.getWeight());
                                        edgeLocation.setText(edge.startNode.getx()+","+edge.startNode.gety()+"-"+edge.endNode.getx()+","+edge.endNode.gety());
                                        popupMenu2.show(getRootPane(),(int)(me.getX()/multiple+30),(int)(me.getY()/multiple+50));
										repaint();
									}
						}
						repaint();				
					}
					
					public void mousePressed(MouseEvent me)
					{										
						if((graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple) instanceof Node))
						{
							
							if(me.getModifiersEx()==1024)
							{
								node=(Node)graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple);
								isNode=true;
								left=true;
								if(node.isSelected)
									translateNode=true;							
							}
						}												
						else if(graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple)==null)
						{
							isRectangle=true;
							xval=me.getX()/multiple;
							yval=me.getY()/multiple;								
						}
						if((graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple) instanceof Edge))
						{
							if(me.getModifiersEx()==1024)
							{
								edge=(Edge)graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple);
								isEdge=true;
							}
						}
					}
					
					public void mouseReleased(MouseEvent me)
					{
						if((graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple) instanceof Node) && left && isNode &&isDragged)
						{
							node2=(Node)graph.checkCoordinates(me.getX()/multiple,me.getY()/multiple);								
							
							if(isWeighted)
							{
								string=JOptionPane.showInputDialog(null,"Enter the weight of the edge","Weight",JOptionPane.INFORMATION_MESSAGE);
        						try
        						{
        						x=Integer.parseInt(string);
        						}
        						catch(NumberFormatException e)
        						{
        							JOptionPane.showMessageDialog(null,"Incorrect value entered","Error",JOptionPane.ERROR_MESSAGE);
        							x=1;
        						}
        						graph.createEdge(node,node2,x);
							}
							else
								graph.createEdge(node,node2);
						}						
							
						isNode=false;
						isEdge=false;
						halfEdge=false;
						translateNode=false;
						left=false;
						if(isRectangle)
						{						
							isRectangle=false;
							if(isDragged)
							{
								graph.getObjectsInsideRectangle((int)xval,(int)yval,(int)xval2,(int)yval2);
								isDragged=false;
							}
						}
						isDragged=false;						
						repaint();
					}
					
					public void mouseEntered(MouseEvent me)
					{
						
					}
					
					public void mouseExited(MouseEvent me)
					{
						
					}
				}
		);
    }
        
                                                                                                            
    public void setZoomPercent(double newPercent)
    {       		
    	percent=newPercent;
       	graph.setZoom((float)percent);
       	repaint();       	    
    }     
       	
    public Graph getGraph()
	{	
    	return graph;
    }   
       	
	public Properties getProperties()
	{
		return properties;
	}	

	public void setMultiple(double val)
	{
		multiple=val;
	}
	
	public void setIsWeighted(boolean directed)
	{
		isWeighted=directed;
	}
	
	public boolean getIsWeighted()
	{
		return isWeighted;
	}
	           
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);	
        
		graph.drawAllEdges(g);
		graph.drawAllNodes(g); 
		graph.drawGrid(g);		
			
		if(halfEdge)
		{
			g.drawLine((int)(node.getx()*multiple),(int)(node.gety()*multiple),(int)(xval2*multiple),(int)(yval2*multiple));
		}	
		if(isRectangle)
		{
			graph.drawRectangle((int)(xval*multiple),(int)(yval*multiple),(int)(xval2*multiple),(int)(yval2*multiple),g);
		}										                               
    }
    
    private class NameHandler implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
    	{
    		repaint();
                if(isEdge)
                {
                    label=JOptionPane.showInputDialog(null,"Enter the name of the node","Label",JOptionPane.INFORMATION_MESSAGE);
                    edge.setLabel(label);
                }
                else
                {
                    label=JOptionPane.showInputDialog(null,"Enter the name of the edge","Label",JOptionPane.INFORMATION_MESSAGE);
                    node.setLabel(label);
                }
                
                repaint();
    	}
    }
    
    private class ColorHandler implements ActionListener
    {
    	public void actionPerformed(ActionEvent ae)
                {
                	repaint();                	
                    if(isEdge)
                    {
                        edge.setColor(JColorChooser.showDialog(null, edge.getLabel(), edge.getColor()));
                    }
                    else
                    {
                        node.setColor(JColorChooser.showDialog(null, node.getLabel(), node.getColor()));
                    }
                    repaint();
                }
    }
    
    private class DeleteHandler implements ActionListener
    {
    	public void actionPerformed(ActionEvent ae)
                {
                	repaint();
                    if(isEdge)
                    {
                        graph.deleteEdge(edge);                        
                    }
                    else
                    {
                        graph.deleteNode(node);                        
                    }                                 
                    repaint();
                }
    }
}