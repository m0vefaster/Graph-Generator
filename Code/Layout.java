/**
 * @(#)Layout.java
 *
 *
 * @author 
 * @version 1.00 2009/12/26
 */

import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.lang.*;
import javax.swing.*;
public class Layout {

Graph graph;
Vector allNodes,allEdges;
int i,j;
int notAllotedNodes;
int numNodes;
//int alloted[][]=new int[100][100];
int xSpacing=100;
int ySpacing=100;
int xOffset=10;
int yOffset=10;
Vector layers[]=new Vector[100];
int numLayers;

    public Layout(Graph newGraph) 
    	{
    	   graph=newGraph;
    	   allNodes=graph.getAllNodes();
    	   allEdges=graph.getAllEdges();
    	   numNodes=graph.getNumNodes();
           
          /* for(i=0;i<100;i++)
           {
           	 for(j=0;j<100;j++)
           	 {
           	 	// alloted[i][j]=0;
           	 }
           }
           */
           for(i=0;i<100;i++)
    	     {
    	   layers[i]=new Vector();
             }
        }
        
    public void setLayout()
    {
    
    	
    	for(i=0;i<allNodes.size();i++)
    	{
    		((Node)(allNodes.elementAt(i))).setOutgoingEdges();
    		((Node)(allNodes.elementAt(i))).setOrdering(0);
    	}	
    		
    	setOrdering();
    	setLayering();
    	setMatrixing();
    	findNonAlloted();
    	setNonAlloted();
    	System.out.println("The size of edes is"+allEdges.size());
    //	makeCurvedEdges();
    	setNoOrdering();
    	
    	removeEdgePoints();
    	
    //	((Edge)(allEdges.elementAt(0))).setCtrX((int)(((Edge)(allEdges.elementAt(0))).getX())+50);
      //  	((Edge)(allEdges.elementAt(0))).setIsStraight(false);	
        //	((Edge)(allEdges.elementAt(0))).setSelected(true);	
    }    
    
    public void setOrdering()
    {
         Node needed;
         Vector outgoingEdges;
         double X,Y;
         int max=((Node)(allNodes.elementAt(0))).getNumOutgoingEdges();
         needed=((Node)(allNodes.elementAt(0)));
         for(i=1;i<allNodes.size();i++)
         {
         	if(((Node)(allNodes.elementAt(i))).getNumOutgoingEdges()>max)
         	{
         		needed=((Node)(allNodes.elementAt(i)));
         		max=((Node)(allNodes.elementAt(i))).getNumOutgoingEdges();
         	}
         }
         System.out.println("Hellop"+max+" "+allNodes.size());
         outgoingEdges=((Node)(needed)).getOutgoingEdges();
         
         layers[0].addElement((Node)(needed));
        // alloted[0][50]=1;
         
         X=50*xOffset+xSpacing;
         Y=0*ySpacing+yOffset;
         needed.setX(X);
         needed.setY(Y);
         needed.setOrdering(1);
         System.out.println(X+"  "+Y);
         for(i=0;i<max;i++)
         {
             
             layers[1].addElement((Node)(((Edge)(outgoingEdges.elementAt(i))).getEndNode()));
             
             if(i<max/2)
             {
            // alloted[1][(int)(X-(i-max)/2*xSpacing+xOffset)]+=1;
             ((Node)(((Edge)(outgoingEdges.elementAt(i))).getEndNode())).setX(X+(i-max/2)*xSpacing);
             System.out.println(X+(i-max/2)*xSpacing+"  "+i);
              System.out.println("    "+(Y+ySpacing));
             }
             else
             {
             //alloted[1][(int)(i+max/2)]+=1;
             ((Node)(((Edge)(outgoingEdges.elementAt(i))).getEndNode())).setX(X+(i-max/2)*xSpacing);
            System.out.println(X+(i-max/2)*xSpacing+"  "+i);
             System.out.println("    "+(Y+ySpacing));
             } 
             ((Node)(((Edge)(outgoingEdges.elementAt(i))).getEndNode())).setY(Y+ySpacing);
           
             System.out.println("    "+(Y+ySpacing));
             ((Node)(((Edge)(outgoingEdges.elementAt(i))).getEndNode())).setOrdering(1);
          }
         
          
    }
    
    public void setLayering()
    {
     System.out.println("Get it done!");
    	Vector outgoingEdges;
    	int i,j;
    	int layer=1;
    	int k=1;
    	double X,Y;
    	
    	while(k>0)
    	{
    		layer+=1;
    		System.out.println("Get it done2!");
    		 k=0;
    		 int layerNodes=layers[layer-1].size();
    		 i=0;
    		 while(i<layerNodes)
    		 {
    		 	
    		 	System.out.println("Get it done3!");
    		 	
    		 	 outgoingEdges=((Node)(layers[layer-1].elementAt(i))).getOutgoingEdges();
                 X=((Node)(layers[layer-1].elementAt(i))).getX();
                 Y=((Node)(layers[layer-1].elementAt(i))).getY();
                 int max=((Node)(layers[layer-1].elementAt(i))).getNumOutgoingEdges();
        
         
       
         for(j=0;j<max;j++)
         {
             if(((Node)(((Edge)(outgoingEdges.elementAt(j))).getEndNode())).getOrdering()!=1)
             {
             k++;
             layers[layer].addElement((Node)(((Edge)(outgoingEdges.elementAt(j))).getEndNode()));
             
             if(j<max/2)
             {
             //alloted[layer][(int)(j-max/2)]+=1;
             ((Node)(((Edge)(outgoingEdges.elementAt(j))).getEndNode())).setX(X+(j-max/2)*xSpacing);
              System.out.println(X+(j-max/2)*xSpacing+"  "+i);
              System.out.println("    "+(Y+ySpacing));
             }
             else
             {
            // alloted[layer][(int)(i+max/2)]+=1;
             ((Node)(((Edge)(outgoingEdges.elementAt(j))).getEndNode())).setX(X+(j-max/2)*xSpacing);
             System.out.println(X+(j-max/2)*xSpacing+"  "+i);
             System.out.println("    "+(Y+ySpacing));	
             } 
             ((Node)(((Edge)(outgoingEdges.elementAt(j))).getEndNode())).setY(layer*ySpacing);
         
             ((Node)(((Edge)(outgoingEdges.elementAt(j))).getEndNode())).setOrdering(1);
          }
          
         }
          i++;
    		 }
    	}
    	numLayers=layer;
    }
    
    
    public void setMatrixing()
    {
    	int i,j;
    	double x1,x2;
    	for(i=numLayers;i>=0;i--)
    	{
    		for(j=0;j<layers[i].size()-1;j++)
    		{
    			x1=((Node)(layers[i].elementAt(j))).getX();
    			x2=((Node)(layers[i].elementAt(j+1))).getX();
    			
    			if(x2-x1<xSpacing)
    			{
    				while((x2-x1)<25)
    				{
    					x2=x2+25;
    				}
    			}
    			
    			((Node)(layers[i].elementAt(j+1))).setX(x2);
    		}
    	}
    }
    
    public void findNonAlloted()
    {
    	notAllotedNodes=0;
    		for(i=0;i<allNodes.size();i++)
    	{
    		if(((Node)(allNodes.elementAt(i))).getOrdering()==0)
    		{
    			notAllotedNodes++;
    		}
    	}
    }
    
    public void setNonAlloted()
    {
    	int i;
    	Vector notAlloted=new Vector();
    	for(i=0;i<allNodes.size();i++)
    	{
    		if(((Node)(allNodes.elementAt(i))).getOrdering()==0)
    		{
    		
    		notAlloted.addElement(((Node)(allNodes.elementAt(i))));	
    		}
    		
    	}
    	
    	notAllotedNodes=(int)Math.sqrt(notAllotedNodes);
    	int max=notAlloted.size();
    	
    	double X=1000,x=200;
    	if(max!=0)
    		X/=10;
    	
    	
    	for(i=0;i<max;i++)
    	{
    		if(i%notAllotedNodes==0)
    		{
    			numLayers++;
    			x=200;
    		}
    		layers[numLayers].addElement((Node)(allNodes.elementAt(i)));
    		((Node)(notAlloted.elementAt(i))).setX(x);
    		((Node)(notAlloted.elementAt(i))).setY(ySpacing*(numLayers-1));
    	    x+=X;
    	}
    	numLayers++;
    }
    
    
    public void setNoOrdering()
    {
    	int i;
    	for(i=0;i<allNodes.size();i++)
    	{
    		
    	
    		((Node)(allNodes.elementAt(i))).setOrdering(0);
    		System.out.println(((Node)(allNodes.elementAt(i))).getOrdering());
        }
    } 
    
    
    public void removeEdgePoints()
    {
    	int i;
    	for(i=0;i<allEdges.size();i++)
    	{
    		((Edge)(allEdges.elementAt(i))). refreshMidPoints();
    	}
    }
    
    
    
    public void makeCurvedEdges()
    {
    	Vector zeroSlope=new Vector();
    	Vector yPos=new Vector();
    	double tempY;
        Vector involved=new Vector();
        
    	Edge edge;
    	Node  s,e;
    	int i;
    	for(i=0;i<allEdges.size();i++)
    	{
    		edge=(Edge)allEdges.elementAt(i);
    		s=(Node)edge.getStartNode();
            e=(Node)edge.getEndNode();
            
            if(s.getY()-e.getY()==0)
            {
            	zeroSlope.add((Edge)edge);
            	
            	System.out.println("Y is..."+s.getY());
            }
    	}
    	
    	System.out.println("Number of zero slopes are:"+zeroSlope.size());
    	for(i=0;i<zeroSlope.size();i++)
    	{
    		
    		
    		involved.add((Edge)(zeroSlope.elementAt(i)));
    	//	zeroSlope.removeElementAt(i);
    		
    	//	tempY=(((Node)(((Edge)(zeroSlope.elementAt(i))).getStartNode())).getY());
    	    tempY=((Edge)(zeroSlope.elementAt(i))).getY();
    	//	i--;
    	
    	   if(yPos.indexOf(tempY)==-1)
    	   {
    	   	yPos.addElement(tempY);
    		for(j=i+1;j<zeroSlope.size();j++)
    		{
    		//	if((((Node)(((Edge)(zeroSlope.elementAt(j))).getStartNode())).getY())==tempY)
    		    if(tempY==((Edge)(zeroSlope.elementAt(i))).getY())
    			{
    	           	involved.add((Edge)(zeroSlope.elementAt(j)));
    	          // 	zeroSlope.removeElementAt(j);
    	           //	j--;			
    			}
    		}
    		
    	  }
    		if(involved.size()>1)
    		{
    			System.out.println("Involved size is:"+involved.size());
    			int y=5;
    			
    			
    			for(i=0;i<involved.size();i++)
    			{
    		    	((Edge)(involved.elementAt(i))).setCtrY((int)(((Edge)(involved.elementAt(i))).getY())+y);
                 	((Edge)(involved.elementAt(i))).setIsStraight(false);	
                    
                    
                     y=y+5;	  
                      
                    
    			}
    			
    		}
    		involved.removeAllElements();
    	}
    	
    }
    
    
}