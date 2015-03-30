/**
 * @(#)Dynamic.java
 *
 *
 * @author 
 * @version 1.00 2009/7/17
 */

import java.util.*;
import java.lang.*;
import javax.swing.*;

public class Dynamic {

Graph graph;
Vector subGraph[]=new Vector[100];
Vector allNodes;
int numSubGraph;

    public Dynamic(Graph newGraph) 
    	{
    	int i;
    	 graph=newGraph;
    	 allNodes=graph.getAllNodes();
    	 for(i=0;i<100;i++)
    	 {
    	   subGraph[i]=new Vector();
         }
         numSubGraph=0;
        }
        
    public void dynamicAllocation()
    {
    	int i;
    	differentiateGraphs();
    	arrangeInOrderOfMaxNodes();	
    		
    	for(i=0;i<allNodes.size();i++)
    	{
    		((Node)(allNodes.elementat(i))).setDynamicAllocated(false);
    	}	
    		
    	for(i=0;i<numSubGraph;i++)
    		arrangeAmongst(i);
    		
        arrangeAll();			
    } 
    
    public void differentiateGraphs()
    {
        int vectorId;
        int i;
        addToSubGraph(0,0);
        numSubGraph+=1;
    	for(i=1;i<allNodes.size();i++)//check for i=1...
    	{
    		vectorId=doesItBelongTo((Node)(allNodes.elementAt(i)));
    		   if(vectorId==-1)
    		   {
    		   	subGraph[numSubGraph].addElement(allNodes.elementAt(i));
    		   //	addIncidentNodes(((Node)(allNodes.elementAt(i))),numSubGraph);
    		   	addToSubGraph(i,Id);
    		   	numSubGraph+=1;
    		   }
    		   
    		   else
    		   {
    		   	subGraph[vectorId].addElement(allNodes.elementAt(i));
    		   }
    	}
    	
    }
    
    public void addIncidentNodes(Node newNode,int Id)
    {
    	Vector incidentEdges=((Node)(newNode)).getIncidentEdges();
    	for(i=0;i<incidentEdges.size();i++)
    	{
    		if (((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()))==newNode)
    		{
    			subGraph[Id].addElement(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())));
    		}
    		else
    			((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()));
    	}
    	
    	
    }
    
    public int doesItBelongTo(Node newNode)
    {
    	int i;
    	int j;
    
    	Vector incidentEdges=((Node)(newNode)). getIncidentEdges();
    	for(i=0;i<((Node)(newNode)).getNumIncidentEdges();i++)
    	{
    	     for(j=0;j<numSubGraph;j++)
    	     {
     	     	if(subGraph[j].contains(((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()))))
                    return j;
                 
                 else if(subGraph[j].contains(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode()))))
                 	return j;     
    	     }
    	}
    	
    	return -1;
    }	
    
    public void addToSubGraph(int a,int b)
    {
    	int i;
    	subGraph[b].addElement(allNodes.elementAt(a));
    	((Node)(allNodes.elementAt(a))).setDynamicAllocated(true);
    	                              
    	Vector incidentEdges=((Node)(allNodes.elementAt(a))). getIncidentEdges();
    	for(i=0;i<((Node)(allNodes.elementAt(a))).getNumIncidentEdges();i++)
    	{
    		if(((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())).getDynamicAllocated()==false)
    		{
    			subGraph[b].addElement(((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())));
    		}
    		
    		else if(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())).getDynamicAllocated()==false)
    		{
    				subGraph[b].addElement(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())));
    		}
    	}
    }
    
    public void arrangeInOrderOfMaxNodes()
    {
    	int i,j;
    	Vector temp=new Vector();
    	for(i=0;i<numSubGraph;i++)
    	{
    		for(j=i;j<numSubGraph;j++)
    		{
    			if(subGraph[i].size()<subGraph[j].size())
    			{
    				temp=subGraph[i];
    				subGraph[i]=subGraph[j];
    				subGraph[j]=temp;
    			}
    		}
    	}
    	Node tNode;
    	
    	
    	for(k=0;k<numSubGraphs;k++)
    	{
    
            for(i=0;i<subGraph[k];i++)
             	{
    		        for(j=i;j<subGraph[k];j++)
    		            {
    			           if(((Node)(subGraph[k].elementAt(i))).getNumIncidentEdges()<((Node)(subGraph[k].elementAt(j))).getNumIncidentEdges())
    			                {
    				             tNode=((Node)(subGraph[k].elementAt(i)));
    				             ((Node)(subGraph[k].elementAt(i)))=((Node)(subGraph[k].elementAt(j)));
    				             ((Node)(subGraph[k].elementAt(j)))=tNode;
    			                }
    		             }
    	         }
         }
    }
    
     
    public void arrangeAmongst(int Id)
    {
    	if(subGraph[Id].size()>5)
    		arrangeBigGraph(Id);
    	else
    		arrangeSmallGraph(Id);	
    }
    
    public void arrangeBigGraph(int Id)
    {
        	double angle;
        	int numNodes=subGraph[Id].size(); 
        	int distance=50;	
          	angle=360/(subGraph[Id].elementAt(0));
            
            ((Node)(subGraph[Id].elementAt(0))).setX(numNodes*50);
            ((Node)(subGraph[Id].elementAt(0))).setY(numNodes*50);
            ((Node)(subGraph[Id].elementAt(0))).setDyanamicAllocated(true);
            ((Node)(subGraph[Id].elementAt(0))).setMasterNode(null);
            
            Vector balancedOrder=getBalancedOrder(Id);   
            Vector unAttended=new Vector(); 
            
            double X,Y;
            for(i=0;i<balancedOrder.size();i++)
            {
             X+=distance*Math.sin(angle);
             Y+=distance*Math.cos(angle);
             
             X*=((((Node)(balancedOrder.elementAt(i))).getWeight())*2)/((Node)(subGraph[Id].elementAt(0))).getNumIncidentEdges();
             Y*=((((Node)(balancedOrder.elementAt(i))).getWeight())*2)/((Node)(subGraph[Id].elementAt(0))).getNumIncidentEdges();
             	
            ((Node)(subGraph[Id].elementAt(0))).setX(X);
            ((Node)(subGraph[Id].elementAt(0))).setY(Y);
            ((Node)(subGraph[Id].elementAt(0))).setDyanamicAllocated(true);
            ((Node)(subGraph[Id].elementAt(0))).setMasterNode(((Node)(subGraph[Id].elementAt(0)))); 
            
             unAttended.addElement(((Node)(subGraph[Id].elementAt(0))));
            }
            
            arrangeOthers(unAttended);	             	
    }
    
    public void arrangeOthers(Vector unAttended)
    {
    	Node node;
        int numIncidentNodes;
        Node masterNode;
        Node closestNode1;
        Node closestNode2;
        double slope,curSlope;
    	while(unAttended.size()!=0)
    	{
    	    node=((Node)(unAttented.elementAt(0)));
    	    unAttented.removeElementAt(0);
    	    numIncidentNodes=getNumIncidentNodes(node);
    	    masterNode=((Node)(node)).getMasterNode();
    	    slope=calculateSlope(node,masterNode);
    	    curSlope=(-1)/slope;
    	    for(i=0;i<allNodes.size();i++)//Change here for betterment
    	    {
    	    	if(node.containsRound(allNodes.elementAt(i),60)==true)
    	    	{
    	    		if()
    	    	}
    	    }	
    	}
    }
   
    public Vector getBalancedOrder(int Id )
    {
    	Node node=((Node)(subGraph[i].elementAt(0)));
    	int i;
    	Vector ordered=new Vector();
    	Vector incidentEdges=((Node)(node)).getIncidentEdges();
    	for(i=0;i<incidentEdges.size();i++)
    	{
    	   	if(((Node)(((Edge)(incidentEdges)).getStartNode()))==node)
    	   	{
    	   		ordered.addElement(((Node)(((Edge)(incidentEdges)).getEndNode())));
    	   	}
    	   	
    	   	else
    	   		ordered.addElement(((Node)(((Edge)(incidentEdges)).getStartNode())));
    	   		
    	}
    	
    	Node tNode;
    	
    	for(i=0;i<ordered.size();i++)
    	{
    		for(j=i;j<ordered.size();j++)
    		{
    			
    		
    		if(((Node)(ordered.elementAt(i))).getWeight()<((Node)(ordered.elementAt(i))).getWeight())
    		{
    			tNode=((Node)(ordered.elementAt(i)));
    			((Node)(ordered.elementAt(i)))=((Node)(ordered.elementAt(j)));
    			((Node)(ordered.elementAt(j)))=tNode;
    		}
    	   }
    	}
    	
    	for(i=0;i<ordered.size()/2;i++)
    	{
    		j=ordered.size()-i-1;
    			tNode=((Node)(ordered.elementAt(i)));
    			((Node)(ordered.elementAt(i)))=((Node)(ordered.elementAt(j)));
    			((Node)(ordered.elementAt(j)))=tNode;
    	}
    	
    	
    	return ordered;
    } 
    public void arrangeSmallGraph(int Id)
    {
    	
    }
    
    public void arrangeAll()
    {
    	
    }
}