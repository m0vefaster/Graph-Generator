import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.lang.*;

public class Node implements Cloneable,Serializable{
public float STROKEVAL=1,RADIUS=5,FONTSIZE=12;
public float strokeVal;
public float radius;
public int x;
public int y;
public int index;
public double X;
public double Y;
public String label;
public Color color;
public boolean isSelected;
public boolean dynamicAlloted;
public Vector incidentEdges;
public boolean showCoordinates;
public boolean showLabels;
public boolean firstTime;
public boolean isGenerated;
public int numIncidentEdges;
public int shape;//1-Circle 2-Square  3-Rectangle 4-Oval
public int i,j,relOrder,order;
public float percent;
public double zoomX,zoomY;
public double fontSize;
public Node masterNode;
public Vector outgoingEdges=new Vector();
public int numOutgoingEdges, visit;
public boolean visited, showOrder=false, processed;

    public Node( )
    	 {
    	   label="";
    	   color=Color.blue;
    	   isSelected=false;
    	   incidentEdges=new Vector();
    	   shape=1;
    	   numIncidentEdges=0;

         }
     public Node(int DEFAULTSHAPE, Color DEFAULTCOLOR)
    	 {
    	   label="";
    	   color=DEFAULTCOLOR;
    	   isSelected=false;
    	   incidentEdges=new Vector();
    	   shape=DEFAULTSHAPE;
    	   numIncidentEdges=0;

         }


    public void setColor(Color newColor)
    {
    	color=newColor;

    }

    public void setx(double newx)
    {
    	x=(int)Math.round(newx);
    }

    public void setX(double newX)
    {
         X=newX;
         setx(X);
         setZoomX(X);
    }

     public void sety(double newy)
    {
    	y=(int)Math.round(newy);

    }

    public void setY(double newY)
    {
    	 Y=newY;
    	 sety(Y);
    	 setZoomY(Y);
    }

    public void setZoomX(double newZoomX)
    {
    	zoomX=newZoomX*percent;
    }

    public void setZoomY(double newZoomY)
    {
    	zoomY=newZoomY*percent;
    }

    public void setLabel(String newLabel)
    {
    	 label=newLabel;
    }

    public void setIndex(int newIndex)
    {
    	////System.out.println("Node Created");
    	 index=newIndex;
    }

    public void setSelected(boolean newSelected)
    {
    	////System.out.println("Selected:"+newSelected+"gdkghghjhjjytx");
    	 isSelected=newSelected;
    }

    public void setShape(int newShape)
    {
    	 shape=newShape;
    }

     public void setShowCoordinate(boolean newShowCoordinate)
    {
    	 showCoordinates=newShowCoordinate;
    }

    public void setShowLabel(boolean newShowLabel)
    {
    	 showLabels=newShowLabel;
    }

    public void setGenerated(boolean newIsGenerated)
    {
    	isGenerated=newIsGenerated;
    }

     public void setDynamicAlloted(boolean bool)
    {
    	dynamicAlloted=bool;
    }

    public boolean getDynamicAlloted()
    {
    	return dynamicAlloted;
    }

    public Color getColor()
    {
    	return  color;
    }

    public int getx( )
    {
    	return  x;
    }

    public Double getX()
    {
    	return  X;
    }

     public int gety()
    {
    	return  y;
    }

    public Double getY()
    {
    	return  Y;
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

    public int getShape()
    {
    	return  shape;
    }

    public boolean getShowCoordinate()
    {
    	return  showCoordinates;
    }

    public boolean  getShowLabel()
    {
    	return  showLabels;
    }

    public int getNumIncidentEdges()
    {
    	return numIncidentEdges;
    }


    public boolean getGenerated()
    {
    	return isGenerated;
    }

    public double getZoomX()
    {
    	return zoomX;
    }

    public double getZoomY()
    {
    	return zoomY;
    }
    public void drawNode(Graphics g)
    {
    	Graphics2D g2=(Graphics2D)g;

    	g2.setPaint(color);

    	if(isSelected)
    	{
    		//g2.setPaint(new GradientPaint(4+ x-radius/2,58+y-radiusOfSelected/2,Color.BLUE,2*radius,2*radiusOfSelected,Color.YELLOW,true));
    		if(shape==1)
    		{

    		g2.fill(new Ellipse2D.Double(zoomX-radius/2, zoomY-radius/2,2*radius,2*radius));
        	g2.setPaint(Color.black);
    		g2.setStroke(new BasicStroke(strokeVal));
    		g2.draw(new Ellipse2D.Double( zoomX-radius/2, zoomY-radius/2,2*radius,2*radius));
    		g2.setStroke(new BasicStroke(strokeVal));
    		g2.fill(new Ellipse2D.Double( zoomX-radius/4,zoomY-radius/4,radius,radius));
    		}
    		if(shape==4)
    		{
    		g2.fill(new Ellipse2D.Double( zoomX-radius/2, zoomY-radius*.625,2*radius,1.25*radius));
    		g2.setPaint(Color.black);
    		g2.draw(new Ellipse2D.Double( zoomX-radius/2, zoomY-radius*.625,2*radius,1.25*radius));
    		g2.setStroke(new BasicStroke(strokeVal));
    		g2.fill(new Ellipse2D.Double( zoomX-radius/4,zoomY-radius/4,radius,radius));
    		}
    		if(shape==2)
    		{
    		 g2.fill(new Rectangle2D.Double( X-radius/2, Y-radius/2,2*radius,2*radius));
    		 g2.setPaint(Color.black);
    		 g2.draw(new Rectangle2D.Double( X-radius/2, Y-radius/2,2*radius,2*radius));
    		 g2.setStroke(new BasicStroke(strokeVal));
    		g2.fill(new Ellipse2D.Double( X-radius/4,Y-radius/4,radius,radius));
    		}
    		if(shape==3)
    		{
    		g2.fill(new Rectangle2D.Double( X-radius/2, Y-radius*.625,2*radius,1.25*radius));
    	    g2.setPaint(Color.black);
    	    g2.draw(new Rectangle2D.Double( X-radius/2, Y-radius*.625,2*radius,1.25*radius));
    	    g2.setStroke(new BasicStroke(strokeVal));
    		g2.fill(new Ellipse2D.Double( X-radius/4,Y-radius/4,radius,radius));
    		}
       }
    	else if(!(isSelected))
    	{

    		if(shape==4)
    		{
    		g2.fill(new Ellipse2D.Double( X-radius/2, Y-radius*.625,2*radius,1.25*radius));
    		g2.setPaint(Color.black);
    		g2.draw(new Ellipse2D.Double( X-radius/2, Y-radius*.625,2*radius,1.25*radius));
    		}
    		if(shape==2)
    		{
    		 g2.fill(new Rectangle2D.Double( X-radius/2, Y-radius/2,2*radius,2*radius));
    		 g2.setPaint(Color.black);
    		 g2.draw(new Rectangle2D.Double( X-radius/2, Y-radius/2,2*radius,2*radius));

    		}
    		if(shape==3)
    		{
    		g2.fill(new Rectangle2D.Double( X-radius/2, Y-radius*.625,2*radius,1.25*radius));
    	    g2.setPaint(Color.black);
    	    g2.draw(new Rectangle2D.Double( X-radius/2, Y-radius*.625,2*radius,1.25*radius));

    		}
    		else if(shape==1)
    		{
    			////System.out.print("here i am!!!");
    		g2.fill(new Ellipse2D.Double(zoomX-radius/2, zoomY-radius/2,2*radius,2*radius));
        	g2.setPaint(Color.black);
    		g2.setStroke(new BasicStroke(strokeVal));
    		g2.draw(new Ellipse2D.Double(zoomX-radius/2,zoomY-radius/2,2*radius,2*radius));


    		}
    	}

    	/*if(showLabels)
    	{
    		Point2D.Float p1 = new Point2D.Float((float)( X+3), (float)( Y+3));
    		Point2D.Float p2 = new Point2D.Float((float)( X+5), ((float) Y+5));
    		Line2D.Float line = new Line2D.Float(p1,p2);
    		g2.draw(line);
    	}

    	if(showCoordinates)
    	{
    		Point2D.Float p1 = new Point2D.Float((float)( X-3), (float)( Y-3));
    		Point2D.Float p2 = new Point2D.Float((float)( X-5), (float)( Y-5));
    		Line2D.Float line = new Line2D.Float(p1,p2);
    		g2.draw(line);

    	}*/

    	//Font f = new Font(“Arial”, Font.PLAIN, fontSize);
    	//g.setFont(f);

    	//	int p=(int)(percent);
    	int p=1;
        if(showLabels)
        {

        	g.drawString(label,(int)Math.round(zoomX) *p, (int)Math.round(zoomY) *p+6*p);
        }

        if(showCoordinates)
    	{
    		g.drawString(x+","+y,(int)Math.round(zoomX) *p, (int)Math.round(zoomY) *p-6*p);
    	}
    	
    	if(showOrder)
    	{
    		g.drawString(order+"",(int)Math.round(zoomX) *p, (int)Math.round(zoomY) *p-6*p);
    	}
    }

    public Vector getIncidentEdges()
    {
     return incidentEdges;
    }

    public void addIncidentEdge(Edge newEdge)
    {
    	for(i=0;i<incidentEdges.size();i++)
    	{
    		if(((Edge)(incidentEdges.elementAt(i))).getIndex()==((Edge)newEdge).getIndex())
    	      return;
    	}
    	numIncidentEdges+=1;
    	incidentEdges.add(newEdge);
    }

    public void removeIncidentEdge(Edge newEdge)
    {
    	for(i=0;i<incidentEdges.size();i++)
    	{
    		if((Edge)incidentEdges.elementAt(i)==newEdge)
    		{
    		incidentEdges.removeElementAt(i);
    		}
    	}
    }

  	public boolean contains(double newX,double newY)
  	{
    double distance = (newX -  X) * (newX -  X) +
                   (newY -  Y) * (newY -  Y);
    return (distance <= ((radius)*(radius)+5));
  	}  	   

  public void translate(int transX, int transY)
  {

    this.setX(transX);
    this.setY(transY);
    this.setx(transX);
    this.sety(transY);
    refreshEdgesForTranslate();
  }

  public void refreshEdgesForTranslate()
  {
  	for(i=0;i<incidentEdges.size();i++)
  	{
  		((Edge)(incidentEdges.elementAt(i))).translate(this);

  	}
  }

  public void rotate(double angle)
  {
  	//System.out.println("Roatating");
    double cos = Math.cos(Math.toRadians(angle));
    double sin = Math.sin(Math.toRadians(angle));
    this.setX(cos * X - sin * Y);
    this.setY(sin * X + cos * Y);
  }

  public void rotateThrough(int referencePointX,int referencePointY,double angle)
  {
  	double cos = Math.cos(Math.toRadians(angle));
    double sin = Math.sin(Math.toRadians(angle));
    double tempX = X - referencePointX;
    double tempY = Y - referencePointY;
    this.setX( (cos * tempX - sin * tempY) + referencePointX);
    this.setY( (sin * tempX + cos * tempY) + referencePointY);
  }

  public void changeProperties(float zoomPercent)
  {

  	percent=zoomPercent;
  	strokeVal=percent*STROKEVAL;
    radius=percent*RADIUS;
    fontSize=FONTSIZE;

    zoomX=X*percent;
    zoomY=Y*percent;

  }

  public void setMasterNode(Node newNode)
  {
  	masterNode=newNode;
  }

  public Node getMasterNode()
  {
  	return masterNode;
  }

  public boolean containsRound(Node node,double radius)
  {
  	double distance=(((Node)(node)).getX()-X)*(((Node)(node)).getX()-X)-(((Node)(node)).getY()-Y)*(((Node)(node)).getY()-Y);
  	if(distance<radius*radius)
  		return true;

  	else
  		return false;
  }

  public void setOrdering(int num)
  {
  	relOrder=num;
  }


  public int getOrdering( )
  {
  	return relOrder;
  }


  public void setOutgoingEdges()
  {
  	int i;
  	for(i=0;i<incidentEdges.size();i++)
  	{
  		if((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())==this)
  		{
  			outgoingEdges.addElement((Edge)(incidentEdges.elementAt(i)));
  		}
  	}

  	setNumOutgoingEdges();
  }

  public Vector getOutgoingEdges()
  {
  	return outgoingEdges;
  }

  public void setNumOutgoingEdges()
  {
  	numOutgoingEdges=outgoingEdges.size();
  }

  public int getNumOutgoingEdges()
  {
  	return numOutgoingEdges;
  }

      public void setVisited(boolean val)
    {
    	visited=val;
    }

    public boolean getVisited()
    {
    	return visited;
    }

     public Object clone() throws CloneNotSupportedException {
                Node node=(Node)super.clone();
				node.incidentEdges=(Vector)node.incidentEdges.clone();
				return node;

     }
}