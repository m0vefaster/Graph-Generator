/**
 * @(#) java
 *
 *
 * @author
 * @version 1.00 2009/6/10
 */
import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;

public class Edge implements Cloneable,Serializable{

public Node startNode;
public Node endNode;
public int weight;
public Color color;
public String label;
public int index;
public boolean isSelected;
public int x,ctrX;
public int y,ctrY;
public Double X;
public Double Y;
public int i,j;
boolean isStraight;
public int startNodeIndex;
public int endNodeIndex;
public boolean isAdded;
public boolean isGenerated;
public static int xOffset=0,yOffset=0;
public int radius=8;
public float percent;
public float STROKEVAL=3,SPECIALSTROKE=5,LINESTROKE=1;
public float strokeVal,specialStroke,lineStroke;
public double zoomX,zoomY;

    public Edge( )
    	{
    	 	weight=0;
    	 	color=Color.YELLOW;
    	 	label="";
    	 	index=0;
    	 	isSelected=false;
    	 	isStraight=true;
    	 	isAdded=true;
    	    //System.out.println("edge created");
        }
     public Edge(Color defaultColor )
    	{
    	 	weight=0;
    	 	color=defaultColor;
    	 	label="";
    	 	index=0;
    	 	isSelected=false;
    	 	isStraight=true;
    	 	isAdded=true;
    	    //System.out.println("edge created");
        }
     public Edge(Color defaultColor, int sentWeight )
    	{
    	 	weight=sentWeight;
    	 	color=defaultColor;
    	 	label="";
    	 	index=0;
    	 	isSelected=false;
    	 	isStraight=true;
    	 	isAdded=true;
    	    //System.out.println("edge created");
        }

    public void setColor(Color newColor)
    {
    	 color=newColor;
    }


   public void setCenterLocation(Node newStartNode,Node newEndNode)
   {
   		if(isStraight)
   		{
   			startNode=newStartNode;
   			endNode=newEndNode;
   			setStartNode(startNode);
   			setEndNode(endNode);
   			setStartNodeIndex(((Node)startNode).getIndex());
   			setEndNodeIndex(((Node)endNode).getIndex());
   			x=startNode.getx()+endNode.getx();
   			x/=2;
   			y=startNode.gety()+endNode.gety();
   			y/=2;
   			X=startNode.getX()+endNode.getX();
   			X/=2;
   			Y=startNode.getY()+endNode.getY();
   			Y/=2;

   			setZoomX(X);
   			setZoomY(Y);
   		}
   }

   public void setZoomX(double newZoomX)
   {
   	zoomX=percent*newZoomX;
   }

   public void setZoomY(double newZoomY)
   {
   	zoomY=percent*newZoomY;
   }

    public void setStartNodeIndex(int newIndex)
    {
    	startNodeIndex=newIndex;
    }

    public void setEndNodeIndex(int newIndex)
    {
    	endNodeIndex=newIndex;
    }
    public void setLabel(String newLabel)
    {
    	 label=newLabel;
    }

    public void setIndex(int newIndex)
    {
    	 index=newIndex;
    }

    public void setSelected(boolean newSelected)
    {
    	 isSelected=newSelected;
    }

    public void setWeight(int newWeight)
    {
    	 weight=newWeight;
    }

    public void setIsStraight(boolean newIsStraight)
    {
    	isStraight=newIsStraight;
    }


    public void setGenerated(boolean newIsGenerated)
    {
    	isGenerated=newIsGenerated;
    }

    public void setStartNode(Node newStartNode)
    {
    	startNode=newStartNode;
    }

    public void setEndNode(Node newEndNode)
    {
    	endNode=newEndNode;
    }

    public void setCtrX(int x)
    {
    	ctrX=x;
    	setX(x);
    	setZoomX(x);
    }

    public void setX(int x)
    {
    	X=(double)x;
    }

    public void setY(int y)
    {
    	Y=(double)y;
    }

    public void setCtrY(int y)
    {
    	ctrY=y;
    	setY(y);
    	setZoomY(y);
    }

    public boolean isAdded()
    {
    	return true;
    }
     public Color getColor()
    {
    	return  color;
    }

    public int getCtrX()
    {
    	return ctrX;
    }

    public int getCtrY()
    {
    	return ctrY;
    }

    public String getLabel()
    {
    	return  label;
    }

    public int getIndex()
    {
    	return  index;
    }

    public boolean getSelected()
    {
    	return  isSelected;
    }

    public int getStartNodeIndex()
    {
    	return startNodeIndex;
    }

    public int getEndNodeIndex()
    {
    	return endNodeIndex;
    }

    public boolean getIsStraight()
    {
    	return isStraight;
    }

    public double getX()
    {
    	return X;
    }

    public double getY()
    {
    	return Y;
    }

    public boolean getIsAdded()
    {
    	return isAdded;
    }

    public boolean getGenerated()
    {
    	return isGenerated;
    }

    public Node getStartNode()
    {
    	return startNode;
    }

    public Node getEndNode()
    {
    	return endNode;
    }

	 public int getWeight()
	 {
	  return weight;
	  }

   	public void drawEdge(Graphics g)
   	{
   	  	Graphics2D g2=(Graphics2D)g;

    	g2.setPaint(color);
    	if(isStraight)
    	{
    		if(!isSelected)

        	{
        		g2.setStroke(new BasicStroke(strokeVal));
        		g2.draw(new Line2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
    			g2.setStroke(new BasicStroke(specialStroke));
    	    	g2.setPaint(color.BLACK);
            	g2.fill(new Ellipse2D.Double( zoomX+xOffset-4, zoomY+yOffset-4,8,8));
        	}
        	else
        	{
        		g2.setStroke(new BasicStroke(strokeVal));
            	g2.draw(new Line2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
     	    	g2.setStroke(new BasicStroke(specialStroke));
    	    	g2.setPaint(color.BLACK);
            	g2.fill(new Ellipse2D.Double( zoomX+xOffset-4, zoomY+yOffset-4,8,8));
            	g2.setStroke(new BasicStroke(lineStroke));
            	g2.draw(new Line2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));

	   	    }
    	}

    	else
    	{
    	 	if(!isSelected)
	        {
    	     	g2.setStroke(new BasicStroke(strokeVal));
        		//g2.draw(new Line2D.Double(((Node)startNode).getX()+4,((Node)startNode).getY()+58,((Node)endNode).getX()+4,((Node)endNode).getY()+58));
        		//g2.draw(new QuadCurve2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),zoomX/2,zoomY/2,((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
        		g2.draw(new QuadCurve2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),
        										zoomX*2-(((Node)startNode).getZoomX())/2-(((Node)endNode).getZoomX())/2,
        										zoomY*2-(((Node)startNode).getZoomY())/2-(((Node)endNode).getZoomY())/2,
        										((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
	    		g2.setStroke(new BasicStroke(specialStroke));
    	     	g2.setPaint(color.BLACK);
        	    //g2.fill(new Ellipse2D.Double( X+xOffset-4, Y+yOffset-4,8,8));
        	    g2.fill(new Ellipse2D.Double( zoomX, zoomY,8,8));
        	}
        	else
        	{
	        	g2.setStroke(new BasicStroke(strokeVal));
    	        //g2.draw(new Line2D.Double(((Node)startNode).getX()+4,((Node)startNode).getY()+58,((Node)endNode).getX()+4,((Node)endNode).getY()+58));
    	        //g2.draw(new QuadCurve2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),zoomX,zoomY,((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
    	        g2.draw(new QuadCurve2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),
        										zoomX*2-(((Node)startNode).getZoomX())/2-(((Node)endNode).getZoomX())/2,
        										zoomY*2-(((Node)startNode).getZoomY())/2-(((Node)endNode).getZoomY())/2,
        										((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
     		    g2.setStroke(new BasicStroke(specialStroke));
    	 	    g2.setPaint(color.BLACK);
            	g2.fill(new Ellipse2D.Double( zoomX, zoomY,8,8));
            	g2.setStroke(new BasicStroke(lineStroke));
            	//g2.draw(new QuadCurve2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),zoomX,zoomY,((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
            	g2.draw(new QuadCurve2D.Double(((Node)startNode).getZoomX(),((Node)startNode).getZoomY(),
        										zoomX*2-(((Node)startNode).getZoomX())/2-(((Node)endNode).getZoomX())/2,
        										zoomY*2-(((Node)startNode).getZoomY())/2-(((Node)endNode).getZoomY())/2,
        										((Node)endNode).getZoomX(),((Node)endNode).getZoomY()));
        	}
    	}
    }

    public boolean contains(double newX,double newY)
  	{
    double distance = (newX -  X) * (newX - X) +
                   (newY -  Y) * (newY -  Y);
    return (distance <= ((radius)*(radius)));
  	}


  public int getLowerIndex()
  {
    return Math.min(((Node)getStartNode()).getIndex(), ((Node)getEndNode()).getIndex());
  }

  public int getHigherIndex()
  {
    return Math.max(((Node)getStartNode()).getIndex(), ((Node)getEndNode()).getIndex());
  }



    public void changeProperties(float zoomPercent)
  {
  	percent=zoomPercent;

  	strokeVal=percent*STROKEVAL;
  	lineStroke=percent*LINESTROKE;
  	specialStroke=percent*SPECIALSTROKE;

    zoomX=X*percent;
    zoomY=Y*percent;
  }

  public void translate(Node node)
  {
  	if(node.getIndex()==startNodeIndex)
  	{
  		startNode=node;
  		setCenterLocation(startNode,endNode);
  	}

  	else if(node.getIndex()==endNodeIndex)
  	{
  		endNode=node;
  		setCenterLocation(startNode,endNode);
  	}
  }

  public void refreshMidPoints()
  {
  	setCenterLocation(startNode,endNode);
  }

  public Object clone() throws CloneNotSupportedException {
                Edge edge=(Edge)super.clone();
				return edge;
  }

}