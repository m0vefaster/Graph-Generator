import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;
import java.nio.*;
import java.nio.channels.*;
import java.lang.*;
import javax.swing.*;

public class Graph implements Cloneable,Serializable 
{
	public Vector allNodes;
	public Vector allEdges;
	public boolean isFirstTime;
	public int numNodes;
	public int numEdges;
	public String s;
	public Edge edge;
	public int nodeIndex;
	public int edgeIndex;
	public double max;
	public int numRows,numCols,rowHeight,colWidth;
	public int i,j,k,l;
	public String title;
	public double tempX,tempY;
	public String label;
	public boolean isBound;
	public float percent;
	public Vector allSelectedObjects;
 	public UserLog userLog;
 	public UserToolBar userToolBar;
	public FileInputStream fin;
	public FileOutputStream fout;
	public FileChannel fchan;
	public long fsize;
	public MappedByteBuffer mbuf;
	public int DEFAULTSHAPE=1;
	public Color defalutNodeColor=Color.blue;
	public Color defaultEdgeColor=Color.yellow;
	public int maxX,maxY;

    public Graph() 
    {
    	allNodes=new Vector();
    	allEdges=new Vector();
    	numEdges=0;
    	numNodes=0;
    	nodeIndex=0;
    	edgeIndex=0;
    	numRows=0;
    	numCols=0;
    	label="";
    	isBound=false;
    	percent=1;
    	allSelectedObjects=new Vector();
     }

    public Graph(String newLabel)
    {
       	allNodes=new Vector();
    	allEdges=new Vector();
    	numEdges=0;
    	numNodes=0;
    	nodeIndex=0;
    	edgeIndex=0;
    	numRows=0;
    	numCols=0;
    	label=newLabel;
    	percent=1;
    	allSelectedObjects=new Vector();
    }


    public void setTitle(String newLabel)
    {
    	label=newLabel;
    }
    public void createNode(double drawX,double drawY)
    {
    	Node node=new Node(DEFAULTSHAPE, defalutNodeColor);
    	numNodes+=1;
    	nodeIndex+=1;
    	node.setIndex(nodeIndex);
    	node.setShowCoordinate(true);
    	node.setShowLabel(true);
    	node.setX(drawX);
    	node.setY(drawY);
    	node.changeProperties(percent);

    	allNodes.addElement(node);
    	userToolBar.addElementToGraph(this,node);
    	userLog.addToUserLog("Created a node with index:"+nodeIndex);
    }

    public void createEdge(Node newStartNode,Node newEndNode)
    {
        Node startNode;
        Node endNode;
    	startNode=newStartNode;
   	    endNode=newEndNode;
    	Edge edge=new Edge(defaultEdgeColor);
    	numEdges+=1;
    	edgeIndex+=1;
    	edge.setCenterLocation(startNode,endNode);
    	edge.setIndex(edgeIndex);
    	edge.changeProperties(percent);
    	edge.setSelected(false);
    	allEdges.addElement(edge);
    	userLog.addToUserLog("Created a edge with index:"+edgeIndex);
    	
    	for(i=0;i<allNodes.size();i++)
    	{
    		if(edge.startNode==allNodes.elementAt(i) || edge.endNode==allNodes.elementAt(i))    		
    			((Node)(allNodes.elementAt(i))).addIncidentEdge(edge);    		
    	}
    		userToolBar.addElementToGraph(this,edge);    
   }
     
   public void setNodeIndex(int newIndex)
   {
   		nodeIndex=newIndex;
   }

   public void setEdgeIndex(int newIndex)
   {
   		edgeIndex=newIndex;
   }

    public void setIsBound(boolean newIsBound)
    {
    	isBound=newIsBound;
    }

    public boolean getIsBound()
    {
    	return isBound;
    }

    public Vector getAllNodes()
    {
    	return allNodes;
    }

    public Vector getAllEdges()
    {
    	return allEdges;
    }

    public void setGraphTitle(String newTitle)
    {
    	 title=newTitle;    
    }

    public int getNumNodes()
    {
    	return numNodes;
    }

    public int getNumEdges()
    {
    	return numEdges;
    }

    public void drawRectangle(int startX,int startY,int endX,int endY,Graphics g)
    {
    	int temp;
    	
    	if(startX>endX)
    	{
    		temp=startX;
    		startX=endX;
    		endX=temp;
    	}

    	if(startY>endY)
    	{
    		temp=startY;
    		startY=endY;
    		endY=temp;
    	}

    	Graphics2D g2=(Graphics2D)g;    	
    	g2.setPaint(Color.GREEN);
    	g2.draw(new Rectangle2D.Float(startX, startY, endX-startX,endY-startY));
    }

    public void getObjectsInsideRectangle(int startX,int startY,int endX,int endY)
    {
    	double tempX,tempY;
    	int temp;
    	if(startX>endX)
    	{
    		temp=startX;
    		startX=endX;
    		endX=temp;
    	}

    	if(startY>endY)
    	{
    		temp=startY;
    		startY=endY;
    		endY=temp;
    	}

    	for(i=0;i<allNodes.size();i++)
  		{

  		  tempX=((Node)(allNodes.elementAt(i))).getX();
  		  tempY=((Node)(allNodes.elementAt(i))).getY();

  		  if(tempX>startX && tempX<endX && tempY>startY && tempY<endY )
  		  {
  		  	
  		  	((Node)(allNodes.elementAt(i))).setSelected(true);
  		  	addToAllSelectedObjects((Node)(allNodes.elementAt(i)));
  		  }
  		}

  		for(i=0;i<allEdges.size();i++)
  		{

  		  tempX=((Edge)(allEdges.elementAt(i))).getX();
  		  tempY=((Edge)(allEdges.elementAt(i))).getY();  		  

  		  if(tempX>startX && tempX<endX && tempY>startY && tempY<endY )
  		  {
	  		    ((Edge)(allEdges.elementAt(i))).setSelected(true);
  		   		addToAllSelectedObjects(((Edge)(allEdges.elementAt(i))));
  		  }
  		}
    }

    public void deleteNode(Node newNode)
  	{
  		for(i=0;i<allNodes.size();i++)
  		{
  			if((((Node)(allNodes.elementAt(i))).getIndex())==(((Node)(newNode)).getIndex()))
  			{  				 
  				 allNodes.removeElementAt(i);
   				 numNodes-=1;
  				 break;
  			}
  		}  	 
		userLog.addToUserLog("Deleted a node with index:"+((Node)(newNode)).getIndex(),newNode);
  	 	userToolBar.removeNode(this,newNode);
  	 	refreshEdges(newNode);
  	}

  	public void deleteEdge(Edge newEdge)
  	{
  		edge=newEdge;
  		for(i=0;i<allEdges.size();i++)
  		{
  			if(((Edge)(allEdges.elementAt(i)))==newEdge)
  			{
  				allEdges.removeElementAt(i);
  				numEdges-=1;  			
  				break;
  			}
  		}  	 
  	 	userLog.addToUserLog("Deleted a edge with index:"+((Edge)(newEdge)).getIndex(),newEdge);
  	 	userToolBar.removeEdge(this,edge);
  	 	refreshNodes(newEdge);
  	}

   public void refreshEdges(Node newNode)
   {
   		Vector tempEdges=newNode.getIncidentEdges();
   		int max=tempEdges.size();
   	 	
   	 	for(i=0;i<allEdges.size();i++)
   	 	{   	   
	   	      if(((Edge)(allEdges.elementAt(i))).getStartNodeIndex()==((Node)(newNode)).getIndex()   || ((Edge)(allEdges.elementAt(i))).getEndNodeIndex()==((Node)(newNode)).getIndex() )
   		      {
   	      		  userToolBar.removeEdge(this,(Edge)(allEdges.elementAt(i)));
   	      		  userLog.addToUserLogSubEntry("Deleted a incident edge with index:"+((Edge)(allEdges.elementAt(i))).getIndex()+" of node index:"+((Node)newNode).getIndex(),allEdges.elementAt(i));
   	 			  allEdges.removeElementAt(i);
   	 			  numEdges-=1;
   	 			  i=i-1;
   	       	  }
   		}
  }

   public void refreshNodes(Edge newEdge)
   {   
   }
   
   public void setGridValues(int newGridX,int newGridY,int newRowHeight,int newColWidth)
   {
   		 numRows=newGridX;
   		 numCols=newGridY;
   		 rowHeight=newRowHeight;
   		 colWidth=newColWidth;
   }
   
   public void drawGrid(Graphics g)
   {
   		g.setColor(Color.GRAY);
   		for(i=1;i<numRows-1;i++)
   		{
   			g.drawLine(0,rowHeight*i,1280,rowHeight*i);
   		}

   		for(i=1;i<numCols-1;i++)
   		{
   			g.drawLine(colWidth*i,0,colWidth*i,800);
   		}   		
  }

  public String getTitle()
  {
  		return label;
  }
   	
  public void drawAllNodes(Graphics g)
  {
   		for(i=0;i<allNodes.size();i++)
   		{
   			((Node)(allNodes.elementAt(i))).drawNode(g);
   		}
  }

   	public void drawAllEdges(Graphics g)
   	{
   		for(i=0;i<allEdges.size();i++)
   		{   		
   			((Edge)(allEdges.elementAt(i))).drawEdge(g);
   		}
   	}

   	public void setNumNodes(int newNumNodes)
   	{
   		numNodes=newNumNodes;
   	}

   	public void setNumEdges(int newNumEdges)
   	{
   		numEdges=newNumEdges;
   	}

   	public void setAllNodes(Vector newAllNodes)
   	{
   		allNodes=newAllNodes;
   	}

   	public void setAllEdges(Vector newAllEdges)
   	{
   		allEdges=newAllEdges;
   	}
   	public Object checkCoordinates(double newX,double newY)
   	{
   	    tempX=newX;
   	    tempY=newY;
   		for(i=0;i<allNodes.size();i++)
   		{

   		  	if(((Node)(allNodes.elementAt(i))).contains(tempX,tempY))
   		  	{
   		  		return allNodes.elementAt(i);
   		  	}
   		}

   		for(i=0;i<allEdges.size();i++)
   		{
   		  if(((Edge)(allEdges.elementAt(i))).contains(tempX,tempY))
   		  {
   		  	return allEdges.elementAt(i);
   		  }
   		}
   		return null;
   	}


   	public void loadFromFile(File newFile)
   	{
   		int temp=0;
   		String newLabel;
   		String fileName;
   		char character;   		

   		try
   		{
   			fileName=newFile.getAbsolutePath();
   			FileReader file=new FileReader(newFile);
   			BufferedReader br=new BufferedReader(file);
   			
   		    this.setTitle(br.readLine());   			
   			this.setGridValues(changeInt(br.readLine()),changeInt(br.readLine()),changeInt(br.readLine()),changeInt(br.readLine()));
   			this.setNumNodes(temp=changeInt(br.readLine()));
   			this.setNumEdges(changeInt(br.readLine()));
   			Vector allNodes=new Vector();
   			Vector allEdges=new Vector();
   		   	Vector<Integer> allEdgesIndex=new Vector<Integer>(this.numEdges);

   			for(i=0;i<this.numNodes;i++)
   			{   			
   				Node node=new Node();
   				node.setIndex(changeInt(br.readLine()));
   				node.setLabel(br.readLine());
   				node.setX(changeDouble(br.readLine()));
   				node.setY(changeDouble(br.readLine()));
   				node.setColor(new Color(changeInt(br.readLine())));
   				node.setShape(changeInt(br.readLine()));
   				s=br.readLine();

   				for(j=0;j<s.length();j++)
   				{
   					if((s.charAt(j))==(':'))
   					{
   						j+=1;
   						while(j<s.length())
   						{
   							k=j;
   							String edgeIndex="";
   							while(k<s.length()&& s.charAt(k)!=',')
   							{
   							    edgeIndex+=s.charAt(k);
   							    k++;
   							}
   						    	temp=Integer.valueOf(edgeIndex).intValue();
   						    	if(!allEdgesIndex.contains(temp))
   						    	{
	   						    	 Edge edge=new Edge();
   							    	 edge.setIndex(temp);
   							    	 userToolBar.addElementToGraph(this,edge);
   							    	 allEdges.addElement(edge);
   							    	 node.addIncidentEdge(edge);
   							    	 allEdgesIndex.addElement(temp);
   						    	}
   						    	else
   						    	{   	
   						    	}
   						    	j=k+1;
   						}
   					}
   				}
   				userToolBar.addElementToGraph(this,node);
   				allNodes.addElement(node);
   			}
   			this.setAllNodes(allNodes);
   			this.setAllEdges(allEdges);
   			this.setNodeIndex(changeNodeIndex());
   			this.setEdgeIndex(changeEdgeIndex());
   			
   			for(i=0;i<this.getNumEdges();i++)
   			{   			
   				int startNodeIndex=0;
   				int endNodeIndex=0;
   				newLabel=br.readLine();
   				temp=changeInt(br.readLine());
   				
   				for(j=0;j<this.getNumEdges();j++)
   				{
   					if(((Edge)(allEdges.elementAt(j))).getIndex()==temp)
   					{
   					   	break;
   					}
   				}
   				((Edge)(allEdges.elementAt(j))).setLabel(newLabel);
   				((Edge)(allEdges.elementAt(j))).setStartNodeIndex(changeInt(br.readLine()));
   				((Edge)(allEdges.elementAt(j))).setEndNodeIndex(changeInt(br.readLine()));
   				
   				for(k=0;k<allNodes.size();k++)
   				{
   					if(((Node)(allNodes.elementAt(k))).getIndex()== ((Edge)(allEdges.elementAt(j))).getStartNodeIndex())
   					{   				
   						startNodeIndex=k;
   					}

   					if(((Node)(allNodes.elementAt(k))).getIndex()== ((Edge)(allEdges.elementAt(j))).getEndNodeIndex())
   					{
   						endNodeIndex=k;
   					}
   				}
   			
   				((Edge)(allEdges.elementAt(j))).setCenterLocation(((Node)(allNodes.elementAt(startNodeIndex))),((Node)(allNodes.elementAt(endNodeIndex))));
   				temp=changeInt(br.readLine());
   				((Edge)(allEdges.elementAt(j))).setColor(new Color(temp));
   				((Edge)(allEdges.elementAt(j))).setIsStraight(changeBoolean(br.readLine()));  				
   			}
   		}
 	    catch(IOException e)
 	    {
 	    }  	      	   	     
   	    this.setZoom(1);   	
   	}

   	public void saveToFile(String fileName)
   	{
   	    try
   	    {
   	   	 	String data="";
	   	    data+="Name of the Graph:"+label+"\n";
   		    data+="Number of Rows:"+numRows+"\n";
   	    	data+="Number of Columns:"+numCols+"\n";
   	    	data+="Height of Rows:"+rowHeight+"\n";
   	    	data+="Width of Columns:"+colWidth+"\n";
   	    	data+="Number of Nodes:"+numNodes+"\n";
   	    	data+="Number of Edges:"+numEdges+"\n";

   	    	for(i=0;i<numNodes;i++)
   	    	{
	   	    	data+="Index:"+((Node)(allNodes.elementAt(i))).getIndex()+"\n";
   		    	data+="Label:"+((Node)(allNodes.elementAt(i))).getLabel()+"\n";
   	    		data+="X-location:"+((Node)(allNodes.elementAt(i))).getX()+"\n";
   	    		data+="Y-location:"+((Node)(allNodes.elementAt(i))).getY()+"\n";
	   	    	data+="Color(RGB):"+((Node)(allNodes.elementAt(i))).getColor().getRGB()+"\n";
   		    	data+="Shape:"+((Node)(allNodes.elementAt(i))).getShape()+"\n";
   	    		Vector incidentEdges=((Node)(allNodes.elementAt(i))).getIncidentEdges();
   	    		data+="Incident Edges Index's:";

	   	    	for(j=0;j<incidentEdges.size();j++)
   	    		{
	   	    	   data+=((Edge)(incidentEdges.elementAt(j))).getIndex()+",";
   		        }
   	        	data+="\n";
   	    	}

   	    	for(i=0;i<numEdges;i++)
   	    	{
   	    		data+="Label:"+((Edge)(allEdges.elementAt(i))).getLabel()+"\n";
	   	    	data+="Index:"+((Edge)(allEdges.elementAt(i))).getIndex()+"\n";
   		    	data+="Start Node Index:"+((Edge)(allEdges.elementAt(i))).getStartNodeIndex()+"\n";
   	    	    data+="End Node Index:"+((Edge)(allEdges.elementAt(i))).getEndNodeIndex()+"\n";
				data+="Color(RGB):"+((Edge)(allEdges.elementAt(i))).getColor().getRGB()+"\n";
   	        	data+="Is Straight?:"+((Edge)(allEdges.elementAt(i))).getIsStraight()+"\n";
   	    	}

   	     	char buffer[]=new char[data.length()];
   	     	data.getChars(0,data.length(),buffer,0);
   	     	FileWriter fw=new FileWriter(fileName);
   	     	BufferedWriter file = new BufferedWriter(fw);
   	     	
   	     	for(i=0;i<buffer.length;i++)
   	     	{
   	     		file.write(buffer[i]);
   	     	}
   	     	file.close();
   	    }
   	    catch(IOException e)
   	    {
   	    }
   	}

   	public int changeInt(String data)
   	{
      	for(k=0;k<data.length();k++)
   		{
   			if((data.charAt(k))==(':'))
   			{   			
				k+=1;
				data=data.substring(k);   				
   				return ((Integer.valueOf( data)).intValue());
   			}
   		}
   		return 0;
   	}

   	public double changeDouble(String data)
   	{
      	for(k=0;k<data.length();k++)
   		{
   			if((data.charAt(k))==(':') &&(k<data.length()))
   			{   			
			    k+=1;
   			    data=data.substring(k);   	
				return  Double.valueOf(data).doubleValue();
			}
		}
   		   return 0.0;
   	}

   	public boolean changeBoolean(String data)
   	{
	    for(k=0;k<data.length();k++)
   		{
   			if((data.charAt(k))==(':'))
   			{   	
				k+=1;
				if((data.endsWith("false")))
				{
   					return false;
   				}
   				else
   				{   						
   					return true;
   				}
   			}
   		}
   		return false;
   	}

   	public void rotateAllNodes(double angle)
   	{
   		for(i=0;i<allNodes.size();i++)
  		{
  		   ((Node)(allNodes.elementAt(i))).rotate(angle);
  		}
   	}

   	public void rotateAllNodesThrough(int tempX,int tempY,double angle)
   	{
   		for(i=0;i<allNodes.size();i++)
  		{
  		   ((Node)(allNodes.elementAt(i))).rotateThrough(tempX,tempY,angle);
  		}
   	}

   	public void translateAllNodes(int tempX,int tempY)
   	{
   		for(i=0;i<allNodes.size();i++)
  		{
  		   ((Node)(allNodes.elementAt(i))).translate(tempX,tempY);
  		}
   	}

   	public void removeSelected()
   	{   		
   		for(k=0;k<allNodes.size();k++)
  		{
  		   if (((Node)(allNodes.elementAt(k))).getSelected())
  		   {
  		   	    deleteNode(((Node)(allNodes.elementAt(k))));  		   	  
  		   	   	k=k-1;
  		   }  		   	
  		}

  		for(k=0;k<allEdges.size();k++)
  		{  			
  			if (((Edge)(allEdges.elementAt(k))).getSelected())
  		    {
	  		   	deleteEdge(((Edge)(allEdges.elementAt(k))));
  			   	k=k-1;
  		    }
  		}
   	}

   	public void unSelectAll()
   	{
   		for(k=0;k<allNodes.size();k++)
  		{
  		   if (((Node)(allNodes.elementAt(k))).getSelected())
  		   	    ((Node)(allNodes.elementAt(k))).setSelected(false);
  		}

  		for(k=0;k<allEdges.size();k++)
  		{
  		   if (((Edge)(allEdges.elementAt(k))).getSelected())
  		   	  ((Edge)(allEdges.elementAt(k))).setSelected(false);
  		}
   	}

    public void selectAll()
    {
    	for(k=0;k<allNodes.size();k++)
  		{
  		   if (!((Node)(allNodes.elementAt(k))).getSelected())
  		   	    ((Node)(allNodes.elementAt(k))).setSelected(true);
  		}

  		for(k=0;k<allEdges.size();k++)
  		{
  		   if (!((Edge)(allEdges.elementAt(k))).getSelected())
  		   	  ((Edge)(allEdges.elementAt(k))).setSelected(true);
  		}
    }

    public void removeAll()
    {
    	for(k=0;k<allEdges.size();k++)
  		{
  			deleteEdge(((Edge)(allEdges.elementAt(k))));
  		   	k=k-1;
  		}

    	for(k=0;k<allNodes.size();k++)
  		{
  		   deleteNode(((Node)(allNodes.elementAt(k))));
  		   k=k-1;
  		}
    }

    public void removeGenerated()
    {
    	for(k=0;k<allNodes.size();k++)
  		{

  		   if (((Node)(allNodes.elementAt(k))).getGenerated())
  		   {
  		   		deleteNode(((Node)(allNodes.elementAt(k))));
	  		   k=k-1;
  			}
  		}
  		for(k=0;k<allEdges.size();k++)
  		{
  		   if (((Edge)(allEdges.elementAt(k))).getGenerated())
  		   {
  		   	  deleteEdge(((Edge)(allEdges.elementAt(k))));
  		   	  k=k-1;
  		   }
  		}
    }

   public boolean checkForDuplicateEdges()
   {
   		Edge sortedEdges[] = sortEdges();
    	
    	for ( int i=0; i<sortedEdges.length-1; i++ )
    	{
      		if ( sortedEdges[i].equals(sortedEdges[i+1]) )
		    {
		        return true;
      		}
    	}
    	return false;
  }

  public Edge[] sortEdges( )
  {
    	int count[] = new int[allNodes.size()];
    	Edge sortedEdges[] = new Edge[allEdges.size()];
    	Edge sortedEdges2[] = new Edge[allEdges.size()];
    	int i=0;
    	for ( i=0; i<allNodes.size(); i++ )
    	{
      		count[i] = 0;
		    ((Node)allNodes.elementAt(i)).setIndex(i+1);
    	}
    	for ( i=0; i<allEdges.size(); i++ )
    	{
      		count[((Edge)allEdges.elementAt(i)).getHigherIndex()-1]++;
    	}
    	for ( i=1; i<allNodes.size(); i++ )
    	{
      		count[i]+=count[i-1];
    	}
    	for ( i=allEdges.size()-1; i>=0; i-- )
    	{
      		sortedEdges[count[((Edge)allEdges.elementAt(i)).getHigherIndex()-1]-1] = (Edge)allEdges.elementAt(i);
      		count[((Edge)allEdges.elementAt(i)).getHigherIndex()-1]--;
    	}

    	for ( i=0; i<allNodes.size(); i++ )
    	{
      		count[i] = 0;
	    }
	    for ( i=0; i<allEdges.size(); i++ )
    	{
      		count[sortedEdges[i].getLowerIndex()-1]++;
    	}
    	for ( i=1; i<allNodes.size(); i++ )
    	{
      		count[i]+=count[i-1];
	    }
    	for ( i=allEdges.size()-1; i>=0; i-- )
    	{
      		sortedEdges2[count[sortedEdges[i].getLowerIndex()-1]-1] = sortedEdges[i];
		    count[sortedEdges[i].getLowerIndex()-1]--;
    	}
    	return sortedEdges2;
  }
  
  public void makeGeneratedNodes(int extra)
  {
  		java.util.Random rand = new java.util.Random();
  		
  		for(i=0;i<extra;i++)
  		{
  			j = rand.nextInt(1280);
  			k = rand.nextInt(800);
  			createNode(j,k);
  		}
  	}

 public void makeGeneratedEdges(int extra)
 {
  		java.util.Random rand = new java.util.Random();

  		for(i=0;i<extra;i++)
  		{
  			do
  			{
  				do
  				{
  					j = rand.nextInt(allNodes.size()+extra);
  					k = rand.nextInt(allNodes.size()+extra);
  					createNode(j,k);
  				}while(j!=k);
  		   		createEdge(((Node)(allNodes.elementAt(j))),((Node)(allNodes.elementAt(k))));
  		    }while(!checkForDuplicateEdges());
  	  	}
  	}

  	public void setZoom(float newPercent)
  	{
  		percent=newPercent;

  		for(i=0;i<allNodes.size();i++)
  		{
  			((Node)(allNodes.elementAt(i))).changeProperties(percent);
  		}

  		for(i=0;i<allEdges.size();i++)
  		{
  			((Edge)(allEdges.elementAt(i))).changeProperties(percent);
  		}

  	}

  	public float getZoomEnable()
  	{
  		return percent;
  	}

   public void setUserToolBarObject(UserToolBar newUserToolBar)
   {
   		userToolBar=newUserToolBar;
   }

   public void setUserLog(UserLog newUserLog)
   {
   		userLog=newUserLog;
   		userLog.setGraph(this);
   }

   public double maxX()
   {
   		max=((Node)(allNodes.elementAt(0))).getX();

  		for(i=0;i<allNodes.size();i++)
  		{
  			if(((Node)(allNodes.elementAt(i))).getX()>max)
  				max=((Node)(allNodes.elementAt(i))).getX();
  		}

  		for(i=0;i<allEdges.size();i++)
  		{
  			if(((Edge)(allEdges.elementAt(i))).getX()>max)
  				max=((Edge)(allEdges.elementAt(i))).getX();
  		}
  	return max;
   }

   public double maxY()
   {
   		max=((Node)(allNodes.elementAt(0))).getY();
  		for(i=0;i<allNodes.size();i++)
  		{
  			if(((Node)(allNodes.elementAt(i))).getY()>max)
  				max=((Node)(allNodes.elementAt(i))).getY();
  		}

  		for(i=0;i<allEdges.size();i++)
  		{
  			if(((Edge)(allEdges.elementAt(i))).getX()>max)
  				max=((Edge)(allEdges.elementAt(i))).getX();
  		}
  		return max;
   }

   public void undo()
   {
   		userLog.setUndo();
   		String data="";
   	    data+="Name of the Graph:"+label+"\n";
   	    data+="Number of Rows:"+numRows+"\n";
   	    data+="Number of Columns:"+numCols+"\n";
   	    data+="Height of Rows:"+rowHeight+"\n";
   	    data+="Width of Columns:"+colWidth+"\n";
   	    data+="Number of Nodes:"+numNodes+"\n";
   	    data+="Number of Edges:"+numEdges+"\n";   	   
   	    
   	    for(i=0;i<numNodes;i++)
   	    {
   	    	data+="Index:"+((Node)(allNodes.elementAt(i))).getIndex()+"\n";
   	    	data+="Label:"+((Node)(allNodes.elementAt(i))).getLabel()+"\n";
   	    	data+="X-location:"+((Node)(allNodes.elementAt(i))).getX()+"\n";
   	    	data+="Y-location:"+((Node)(allNodes.elementAt(i))).getY()+"\n";
   	    	data+="Color(RGB):"+((Node)(allNodes.elementAt(i))).getColor().getRGB()+"\n";

   	    	Vector incidentEdges=((Node)(allNodes.elementAt(i))).getIncidentEdges();
   	    	data+="Incident Edges Index's:";
   	    	for(j=0;j<incidentEdges.size();j++)
   	    	{
   	    	   data+=((Edge)(incidentEdges.elementAt(j))).getIndex()+",";
   	        }
   	        data+="\n";
   	    }

   	    for(i=0;i<numEdges &numEdges!=0;i++)
   	    {
   	    	data+="Label:"+((Edge)(allEdges.elementAt(i))).getLabel()+"\n";
   	    	data+="Index:"+((Edge)(allEdges.elementAt(i))).getIndex()+"\n";
   	    	data+="Start Node Index:"+((Edge)(allEdges.elementAt(i))).getStartNodeIndex()+"\n";
   	        data+="End Node Index:"+((Edge)(allEdges.elementAt(i))).getEndNodeIndex()+"\n";   	   
   	    	data+="Color(RGB):"+((Edge)(allEdges.elementAt(i))).getColor().getRGB()+"\n";
   	        data+="Is Straight?:"+((Edge)(allEdges.elementAt(i))).getIsStraight()+"\n";
   	    }   	
   }

   public void redo()
   {
   		String data="";
   	    data+="Name of the Graph:"+label+"\n";
   	    data+="Number of Rows:"+numRows+"\n";
   	    data+="Number of Columns:"+numCols+"\n";
   	    data+="Height of Rows:"+rowHeight+"\n";
   	    data+="Width of Columns:"+colWidth+"\n";
   	    data+="Number of Nodes:"+numNodes+"\n";
   	    data+="Number of Edges:"+numEdges+"\n";
   	       	    
   	    for(i=0;i<numNodes;i++)
   	    {
   	    	data+="Index:"+((Node)(allNodes.elementAt(i))).getIndex()+"\n";
   	    	data+="Label:"+((Node)(allNodes.elementAt(i))).getLabel()+"\n";
   	    	data+="X-location:"+((Node)(allNodes.elementAt(i))).getX()+"\n";
   	    	data+="Y-location:"+((Node)(allNodes.elementAt(i))).getY()+"\n";
   	    	data+="Color(RGB):"+((Node)(allNodes.elementAt(i))).getColor().getRGB()+"\n";

   	    	Vector incidentEdges=((Node)(allNodes.elementAt(i))).getIncidentEdges();
   	    	data+="Incident Edges Index's:";
   	    	for(j=0;j<incidentEdges.size();j++)
   	    	{
   	    	   data+=((Edge)(incidentEdges.elementAt(j))).getIndex()+",";
   	        }
   	        data+="\n";
   	    }

   	    for(i=0;i<numEdges &numEdges!=0;i++)
   	    {
   	    	data+="Label:"+((Edge)(allEdges.elementAt(i))).getLabel()+"\n";
   	    	data+="Index:"+((Edge)(allEdges.elementAt(i))).getIndex()+"\n";
   	    	data+="Start Node Index:"+((Edge)(allEdges.elementAt(i))).getStartNodeIndex()+"\n";
   	        data+="End Node Index:"+((Edge)(allEdges.elementAt(i))).getEndNodeIndex()+"\n";   	    
   	    	data+="Color(RGB):"+((Edge)(allEdges.elementAt(i))).getColor().getRGB()+"\n";
   	        data+="Is Straight?:"+((Edge)(allEdges.elementAt(i))).getIsStraight()+"\n";
   	    }
   		userLog.setRedo();  
		data="";
   	    data+="Name of the Graph:"+label+"\n";
   	    data+="Number of Rows:"+numRows+"\n";
   	    data+="Number of Columns:"+numCols+"\n";
   	    data+="Height of Rows:"+rowHeight+"\n";
   	    data+="Width of Columns:"+colWidth+"\n";
   	    data+="Number of Nodes:"+numNodes+"\n";
   	    data+="Number of Edges:"+numEdges+"\n";
   	       	   
   	    for(i=0;i<numNodes;i++)
   	    {
   	    	data+="Index:"+((Node)(allNodes.elementAt(i))).getIndex()+"\n";
   	    	data+="Label:"+((Node)(allNodes.elementAt(i))).getLabel()+"\n";
   	    	data+="X-location:"+((Node)(allNodes.elementAt(i))).getX()+"\n";
   	    	data+="Y-location:"+((Node)(allNodes.elementAt(i))).getY()+"\n";
   	    	data+="Color(RGB):"+((Node)(allNodes.elementAt(i))).getColor().getRGB()+"\n";

   	    	Vector incidentEdges=((Node)(allNodes.elementAt(i))).getIncidentEdges();
   	    	data+="Incident Edges Index's:";
   	    	for(j=0;j<incidentEdges.size();j++)
   	    	{
   	    	   data+=((Edge)(incidentEdges.elementAt(j))).getIndex()+",";
   	        }
   	        data+="\n";
   	    }

   	    for(i=0;i<numEdges &numEdges!=0;i++)
   	    {
   	    	data+="Label:"+((Edge)(allEdges.elementAt(i))).getLabel()+"\n";
   	    	data+="Index:"+((Edge)(allEdges.elementAt(i))).getIndex()+"\n";
   	    	data+="Start Node Index:"+((Edge)(allEdges.elementAt(i))).getStartNodeIndex()+"\n";
   	        data+="End Node Index:"+((Edge)(allEdges.elementAt(i))).getEndNodeIndex()+"\n";   	   
   	    	data+="Color(RGB):"+((Edge)(allEdges.elementAt(i))).getColor().getRGB()+"\n";
   	        data+="Is Straight?:"+((Edge)(allEdges.elementAt(i))).getIsStraight()+"\n";
   	    }   	
   }

   public void createEdge(Edge edge)
   {
   		allEdges.addElement(edge);
   		numEdges+=1;
   		for(i=0;i<allNodes.size();i++)
   		{
   			if(((Node)(allNodes.elementAt(i))).getIndex()==((Edge)(edge)).getStartNodeIndex())
   			{   			
	   			((Node)(allNodes.elementAt(i))). addIncidentEdge(edge);
   			}

   			if(((Node)(allNodes.elementAt(i))).getIndex()==((Edge)(edge)).getEndNodeIndex())
   			{   			
   				((Node)(allNodes.elementAt(i))). addIncidentEdge(edge);
	   		}
   		}
   		userToolBar.addElementToGraph(this,edge);
   		userLog.addToUserLog("Created a edge with index:"+edge.getIndex());
   }

   public void createNode(Node node)
   {
   		allNodes.addElement(node);
   		numNodes+=1;
   		userToolBar.addElementToGraph(this,node);
   		userLog.addToUserLog("Created a node with index:"+node.getIndex());
   }

   public void deleteNode(int newIndex)
   {   	
	   	Node newNode;
   		for(i=0;i<allNodes.size();i++)
  		{
  			if(((Node)(allNodes.elementAt(i))).getIndex()==newIndex)
  			{
  			 	userLog.addToUserLog("Deleted a node with index:"+((Node)(allNodes.elementAt(i))).getIndex(),allNodes.elementAt(i));
  	            userToolBar.removeNode(this,(Node)(allNodes.elementAt(i)));
  	            newNode=(Node)(allNodes.elementAt(i));
  		  		allNodes.removeElementAt(i);
  		  		numNodes-=1;
  		  		refreshEdges(newNode);
  		    }
  		}
   }

   public void deleteEdge(int newIndex)
   {
   	for(i=0;i<allEdges.size();i++)
  		{
  			if(((Edge)(allEdges.elementAt(i))).getIndex()==newIndex)
  			{
  				userLog.addToUserLog("Deleted a edge with index:"+((Edge)(allEdges.elementAt(i))).getIndex(),allEdges.elementAt(i));
  	            userToolBar.removeEdge(this,(Edge)(allEdges.elementAt(i)));
  				allEdges.removeElementAt(i);
  		        numEdges-=1;
  		    }
  		}
   }

   public void createIncidentEdge(Edge newEdge,char index)
   {
   		boolean start=false,end=false;
   		for(i=0;i<allNodes.size();i++)
  		{
           	if(((Node)(allNodes.elementAt(i))).getIndex()==newEdge.getEndNodeIndex())
           		end=true;
           	if(((Node)(allNodes.elementAt(i))).getIndex()==newEdge.getStartNodeIndex())
           		start=true;
  		}
  		if(String.valueOf(((Edge)(newEdge)).getIndex()).equals(String.valueOf(index)))
  		{
  			start=true;
  		}

  		if(String.valueOf(((Edge)(newEdge)).getIndex()).equals(String.valueOf(index)))
  		{
  			end=true;
  		}

  		if(start && end)
  		{
    	  	for(i=0;i<allNodes.size();i++)
  		    {
  				if(String.valueOf(((Node)(allNodes.elementAt(i))).getIndex()).equals(String.valueOf(index)))
  				{
  			    	((Node)(allNodes.elementAt(i))).addIncidentEdge(newEdge);
  		        	allEdges.addElement(newEdge);
             	}
  			}
  		}
   }

   public void deleteIncidentEdge(int nodeIndex,int edgeIndex)
   {   	  
   }

   public float getPercent()
   {
   		return percent;
   }

   public Vector getAllSelectedObjects()
   {
   		return allSelectedObjects;
   }

   public void addToAllSelectedObjects(Object object)
   {
   		allSelectedObjects.addElement(object);
   }

   public void removeFromAllSelectedObjects(Object object)
   {
   		allSelectedObjects.removeElement(object);
   }

   public int changeNodeIndex()
   {
   		int max;
   		max=allNodes.size();
   		for(i=0;i<allNodes.size();i++)
   		{
	   		if(((Node)(allNodes.elementAt(i))).getIndex()>max)
   			{
   				max=((Node)(allNodes.elementAt(i))).getIndex();
   			}
   		}
   		return max;
   }

   public int changeEdgeIndex()
   {
   		int max;
   		max=allEdges.size();
   		for(i=0;i<allEdges.size();i++)
   		{
   			if(((Edge)(allEdges.elementAt(i))).getIndex()>max)
   			{
   				max=((Edge)(allEdges.elementAt(i))).getIndex();
   			}
   		}
   		return max;
   }

   public UserLog getUserLog()
   {
   		return userLog;
   }

   public void printStatus()
   {
   		String data="";
   	    data+="Name of the Graph:"+label+"\n";
   	    data+="Number of Rows:"+numRows+"\n";
   	    data+="Number of Columns:"+numCols+"\n";
   	    data+="Height of Rows:"+rowHeight+"\n";
   	    data+="Width of Columns:"+colWidth+"\n";
   	    data+="Number of Nodes:"+numNodes+"\n";
   	    data+="Number of Edges:"+numEdges+"\n";
   	    System.out.println("\n"+data);
   	    for(i=0;i<numNodes;i++)
   	    {
   	    	data+="Index:"+((Node)(allNodes.elementAt(i))).getIndex()+"\n";
   	    	data+="Label:"+((Node)(allNodes.elementAt(i))).getLabel()+"\n";
   	    	data+="X-location:"+((Node)(allNodes.elementAt(i))).getX()+"\n";
   	    	data+="Y-location:"+((Node)(allNodes.elementAt(i))).getY()+"\n";
   	    	data+="Color(RGB):"+((Node)(allNodes.elementAt(i))).getColor().getRGB()+"\n";

   	    	Vector incidentEdges=((Node)(allNodes.elementAt(i))).getIncidentEdges();
   	    	data+="Incident Edges Index's:";
   	    	for(j=0;j<incidentEdges.size();j++)
   	    	{
   	    	   data+=((Edge)(incidentEdges.elementAt(j))).getIndex()+",";
   	        }
   	        data+="\n";
   	    }

   	    for(i=0;i<numEdges &numEdges!=0;i++)
   	    {
   	    	data+="Label:"+((Edge)(allEdges.elementAt(i))).getLabel()+"\n";
   	    	data+="Index:"+((Edge)(allEdges.elementAt(i))).getIndex()+"\n";
   	    	data+="Start Node Index:"+((Edge)(allEdges.elementAt(i))).getStartNodeIndex()+"\n";
   	        data+="End Node Index:"+((Edge)(allEdges.elementAt(i))).getEndNodeIndex()+"\n";   	   
   	    	data+="Color(RGB):"+((Edge)(allEdges.elementAt(i))).getColor().getRGB()+"\n";
   	        data+="Is Straight?:"+((Edge)(allEdges.elementAt(i))).getIsStraight()+"\n";
   	    }
   		System.out.println("\n"+data);
   }

   public void showAllLabels(boolean bool)
   {
   		for(i=0;i<allNodes.size();i++)
  		{
  			((Node)(allNodes.elementAt(i))).setShowLabel(bool);
  		}
   }

   public void showAllCoordinates(boolean bool)
   {
   		for(i=0;i<allNodes.size();i++)
  		{
  			((Node)(allNodes.elementAt(i))).setShowCoordinate(bool);
  		}
   }

   public Node getOppositeNode(Node node, Edge edge)
   {
   		if(node.getIndex()==(edge.getStartNode()).getIndex())
   			return edge.getEndNode();
   		else
   			return edge.getStartNode();
   }

    public void addNode(Node sentNode)
    {
    	numNodes++;
    	nodeIndex++;
    	Node node=new Node(DEFAULTSHAPE, defalutNodeColor);
    	node.setIndex(sentNode.getIndex());
    	node.setLabel(sentNode.getLabel());
    	node.setShowCoordinate(sentNode.getShowCoordinate());
    	node.setShowLabel(sentNode.getShowLabel());
    	node.setX(sentNode.getX());
    	node.setY(sentNode.getY());
    	allNodes.add(node);

    	if(node.getX()>maxX)
    	{
    		setMaxX((int)node.getx());
    	}
    	if(node.getY()>maxY)
    	{
    		setMaxY((int)node.gety());
    	}
    }

    public void setMaxX(int x)
    {
    	maxX=x;    		
    }

    public void setMaxY(int y)
    {
    	maxY=y;
    }

    public int getNodesSize()
    {
   		return allNodes.size();
    }

   public int getEdgesSize()
   {
   		return allEdges.size();
   }

   public Edge getEdge(int index)
   {
   		for(i=0;i<allEdges.size();i++)
   		{
   			if(((Edge)allEdges.elementAt(i)).getIndex()==index)
   			{
   				edge=(Edge)allEdges.elementAt(i);
   				return edge;
   			}
   		}
   		return null;
   }

   public Node getNode(int index)
   {
   		Node node;

   		for(i=0;i<allNodes.size();i++)
   		{
   			if(((Node)allNodes.elementAt(i)).getIndex()==index)
   			{
   				node=(Node)allNodes.elementAt(i);
   				return node;
   			}
   		}
   		return null;
   }

    public void addEdge(Edge sentEdge, Node startNode, Node endNode)
    {
        Edge edge=new Edge(defaultEdgeColor,sentEdge.getWeight());
        edge.setCenterLocation(startNode,endNode);
        edge.setIndex(sentEdge.getIndex());
        edge.setLabel(sentEdge.getLabel());
    	numEdges++;
    	edgeIndex++;
    	allEdges.addElement(edge);

    	startNode.addIncidentEdge(edge);
    	endNode.addIncidentEdge(edge);
    }

 	public void setNodeDefaultColor(Color defaultColor)
    {
    	defalutNodeColor=defaultColor;
    }

    public Color getNodeDefaultColor()
    {
    	return defalutNodeColor;
    }

    public void setDefaultShape(int defaultShape)
	{
		DEFAULTSHAPE=defaultShape;
	}

	public int getDefaultShape()
    {
    	return DEFAULTSHAPE;
    }

	public void setEdgeDefaultColor(Color defaultColor)
    {
    	defaultEdgeColor=defaultColor;
    }

    public Color getEdgeDefaultColor()
    {
    	return defaultEdgeColor;
    }

    public void createEdge(Node newStartNode,Node newEndNode, int sentWeight)
    {
        Node startNode;
        Node endNode;
    	startNode=newStartNode;
   	    endNode=newEndNode;
    	Edge edge=new Edge(defaultEdgeColor,sentWeight);
    	numEdges+=1;
    	edgeIndex+=1;
    	edge.setCenterLocation(startNode,endNode);
    	edge.setIndex(edgeIndex);
    	edge.changeProperties(percent);
    	edge.setSelected(false);
    	edge.setLabel("Edge"+edge.getIndex());
    	allEdges.addElement(edge);
    	userLog.addToUserLog("Created a edge with index:"+edgeIndex);
    	for(i=0;i<allNodes.size();i++)
    	{
    		if(edge.startNode==allNodes.elementAt(i) || edge.endNode==allNodes.elementAt(i))
    		{
    			((Node)(allNodes.elementAt(i))).addIncidentEdge(edge);
    		}
    	}
    	userToolBar.addElementToGraph(this,edge);//panel.getGraphObject());
    }

     public Object clone() throws CloneNotSupportedException 
     {
     	Graph graph=(Graph)super.clone();
		
		for(int i = 0;i<graph.allNodes.size();i++) 
		{
			Node node=(Node)graph.allNodes.get(i);
			node=(Node)node.clone();
			Vector allNodes=new Vector();
			allNodes.add(node);
		}

		for(int i = 0;i<graph.allEdges.size();i++) 
		{
			Edge edge=(Edge)graph.allEdges.get(i);
			edge=(Edge)edge.clone();
			Vector allEdges=new Vector();
			allEdges.add(edge);
		}

		graph.setAllNodes(allNodes);
		graph.setAllEdges(allEdges);
		return graph;
    }

	public void setAllNodesEdgesColor(Color edgeColor,Color nodeColor) 
	{
		for(int i = 0;i<allEdges.size();i++)
			((Edge)allEdges.get(i)).setColor(edgeColor);

		for(int i = 0;i<allNodes.size();i++)
			((Node)allNodes.get(i)).setColor(nodeColor);
	}
}


