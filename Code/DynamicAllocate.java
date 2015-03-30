/**
 * @(#)DynamicAllocate.java
 *
 *
 * @author 
 * @version 1.00 2009/7/24
 */

import java.util.*;
import java.lang.*;
import java.awt.*;

public class DynamicAllocate {

Graph graph;
Vector subGraph[]=new Vector[100];
Vector allNodes;
int numSubGraph;
Vector poly[]=new Vector[100];
int numPolygons;
Vector keys=new Vector<Integer>(100);
Vector stringKeys=new Vector<String>(100);
int adj[][]=new int[100][100];
Vector allotedNodes=new Vector();
Vector que=new Vector();
int alloted1,alloted2,alloted3;
double xplace,yplace;
//Queue que=new Queue();

    public DynamicAllocate(Graph newGraph) 
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
    	removeDuplicates();	
    	for(i=0;i<numSubGraph;i++)
    	{
    		System.out.println("SubGraph"+"("+subGraph[i].size()+")"+i);
    		 
    		 	Enumeration vEnum=subGraph[i].elements();
    		 	 while(vEnum.hasMoreElements())
    		 	 {
    		 	 	System.out.println(" "+((Node)(vEnum.nextElement())).getIndex());
    		 	 }
    		 
    	}
    	for(i=0;i<allNodes.size();i++)
    	{
    		((Node)(allNodes.elementAt(i))).setDynamicAlloted(false);
    	}	
    
      for(i=0;i<numSubGraph;i++)
      {
      	adjencyMatrix(i);
      	findPolygons(); 	
      //	allotedNodes=new Vector();
      	System.out.println("\nNum of poly"+numPolygons);		
      	
      	if(numPolygons!=0)
      	{
      		arrangePolygons();
      		arrangeOthers();
      	}	
        
      	else
      	{
      	
      		arrangeCenterNode();
      		arrangeOthers();
      	}	
      		reDraw();
      }		
      	
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
    		   	addToSubGraph(i,numSubGraph);
    		    addIncidentNodes(((Node)(allNodes.elementAt(i))),numSubGraph);
    		   	numSubGraph+=1;
    		   }
    		   
    		   else
    		   {
    		   	subGraph[vectorId].addElement(allNodes.elementAt(i));
    		   	addIncidentNodes(((Node)(allNodes.elementAt(i))),vectorId);
    		   }
    	}
    	
    }
    
    public void addIncidentNodes(Node newNode,int Id)
    {
    	int i;
    	Vector incidentEdges=((Node)(newNode)).getIncidentEdges();
    	for(i=0;i<incidentEdges.size();i++)
    	{
    		if (((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()))==newNode)
    		{
    			if(containsNode(subGraph[Id],((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())))==false)
    			subGraph[Id].addElement(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())));
    		}
    		else
    		{
    			if(containsNode(subGraph[Id],((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())))==false)
    			subGraph[Id].addElement((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()));
    		}	
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
    	((Node)(allNodes.elementAt(a))).setDynamicAlloted(true);
    	                              
    	Vector incidentEdges=((Node)(allNodes.elementAt(a))). getIncidentEdges();
    	for(i=0;i<((Node)(allNodes.elementAt(a))).getNumIncidentEdges();i++)
    	{
    		if(((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())).getDynamicAlloted()==false)
    		{
    			if(containsNode(subGraph[b],((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())))==false)
    			subGraph[b].addElement(((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode())));
    		}
    		
    		else if(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())).getDynamicAlloted()==false)
    		{
    				if(containsNode(subGraph[b],((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())))==false)
    				subGraph[b].addElement(((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())));
    		}
    	}
    }
    
    public void arrangeInOrderOfMaxNodes()
    {
    	int i,j,k;
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
    	
    	
    	for(k=0;k<numSubGraph;k++)
    	{
    
            for(i=0;i<subGraph[k].size();i++)
             	{
    		        for(j=i;j<subGraph[k].size();j++)
    		            {
    			           if((((Node)(subGraph[k].elementAt(i))).getNumIncidentEdges())<(((Node)(subGraph[k].elementAt(j))).getNumIncidentEdges()))
    			                {
    				             tNode=((Node)(subGraph[k].elementAt(i)));
    				             subGraph[k].setElementAt(((Node)(subGraph[k].elementAt(j))),i);
    				             subGraph[k].setElementAt(tNode,i);
    				           
    			                }
    		             }
    	         }
         }
    }
        
        
     public boolean containsNode(Vector vector,Node node)
     {
     	int i;
     	
     	for(i=0;i<vector.size();i++)
     	{System.out.println("comparing:"+((Node)(vector.elementAt(i))).getIndex()+" and "+((Node)(node)).getIndex());
     		if(((Node)(vector.elementAt(i))).getIndex()==((Node)(node)).getIndex())
     			return true;
     			
     		System.out.println("comparing the same:"+((Node)(vector.elementAt(i))).getIndex()+" and "+((Node)(node)).getIndex());	
     	}
     	
     	return false;
     }  
   
   public void removeDuplicates() 
  {
  	int i,j,k;
  	 for(i=0;i<numSubGraph;i++)
  	 {
  	 	for(j=0;j<subGraph[i].size();j++)
  	 	{
  	 		for(k=j;k<subGraph[i].size();k++)
  	 		{
  	 			if(k!=j && ((Node)(subGraph[i].elementAt(j))).getIndex()==((Node)(subGraph[i].elementAt(k))).getIndex())
  	 				subGraph[i].removeElementAt(k);
  	 		}
  	 	}
  	 }
  } 
  	
 
  public void adjencyMatrix(int Id)
     {
      int start,end,cur;
     // int adj[][]=new int[subGraph[Id].size()+1][subGraph[Id].size()+1];
      int i,j;
      int location;
      
      for(i=1;i<=subGraph[Id].size();i++)
      {
      	adj[0][i]=((Node)(subGraph[Id].elementAt(i-1))).getIndex();
      	adj[i][0]=adj[0][i];
      }
      
      
            System.out.println("\nAdjency Matrix\n");
      for(i=0;i<=subGraph[Id].size();i++)
        {
        	for(j=0;j<=subGraph[Id].size();j++)
        	{
        		
        		System.out.print(" "+adj[i][j]);
        	}
        System.out.println(" ");
        }
        
        
      for(i=1;i<=subGraph[Id].size();i++)
      {
      	Vector incidentEdges=((Node)(subGraph[Id].elementAt(i-1))).getIncidentEdges();
      	cur=((Node)(subGraph[Id].elementAt(i-1))).getIndex();	
      	for(j=0;j<incidentEdges.size();j++)
      	{
      	  start=((Edge)(incidentEdges.elementAt(j))).getStartNodeIndex();
      	  end=((Edge)(incidentEdges.elementAt(j))).getEndNodeIndex();
      	  System.out.println("Finding Loc for:"+j);
      	  
      	  //if(j!=0)
      	  //adj[i][j]=0;

      	  if(start!=cur)
      	  {
      	  	adj[i][findLoc(start)]=1;
      	  }
      	  
      	  else
      	  {
      	  	adj[i][findLoc(end)]=1;
      	  }
      	  
      System.out.println("Finding Loc for Again:"+j+"\n");
      	}
      }
      
      System.out.println("\nAdjency Matrix\n");
      for(i=0;i<=subGraph[Id].size();i++)
        {
        	for(j=0;j<=subGraph[Id].size();j++)
        	{
        		
        		System.out.print(" "+adj[i][j]);
        	}
        System.out.println(" ");
        }
     }
 
     public int findLoc( int index)
     {
     	int i;
     	System.out.println("Finding:"+index);
     	for(i=1;i<adj[0].length;i++)
     	{
     		if(adj[0][i]==index)
     		{
              	System.out.println("Returning:"+i+",Found" +adj[0][i]);	       	
     			return i;
         	}
     	}	
     	return 0;
     }
     
     
    public void findPolygons()
    {
    	int i,j,k;
    	Vector polygon;
    	for(i=0;i<100;i++)
    			poly[i]=new Vector<Integer>(3);
    			
    	numPolygons=0; 
    	
    	for(i=1;i<adj[0].length;i++)
    	{
    	  for(j=1;j<adj[0].length;j++)
    	  {
    	  	if(adj[i][j]==1 && i!=j)
    	  	{
    	  	  for(k=0;k<adj[0].length;k++)
    	  	    {
    	  		if(adj[j][k]==1 && adj[k][i]==1 && j!=k &&i!=k)
    	  		{
    	  		  System.out.println(k+" "+j+"  "+i);
    	  		  boolean bool=checkForRepitition(i,j,k);	
    	  		  	if(bool==false)
    	  		  	{
    	  		  poly[numPolygons].addElement(i);
    	  		  poly[numPolygons].addElement(j);
    	  		  poly[numPolygons].addElement(k);
    	  		  numPolygons+=1;
    	  		  	}
    	  		 }
    	  	   }
    	    }	
    	  }
       }  
       	
       	
       	for(i=0;i<numPolygons;i++)
       	{
       		System.out.print("\nVector "+(i+1)+" :");
       		for(j=0;j<poly[i].size();j++)
       		{
       			System.out.print(poly[i].elementAt(j)+" ");
       		}
       	}
       	
       
      }
      
      
      public boolean checkForRepitition(int a,int b,int c)
    {
      int array[]=new int[3];
     array[0]=a;
     array[1]=b;
     array[2]=c;
     
     Arrays.sort(array);
     int sum=array[2]*100+array[1]*10+array[0]*1;
     
     if(!keys.contains(sum))
     {
     	keys.addElement(sum);
     	stringKeys.addElement(String.valueOf(array[0])+String.valueOf(array[1])+String.valueOf(array[2])+String.valueOf(array[0]));
     	return false;
      }
      else
      	return true;
    }
    
    public void arrangePolygons()
    {
    	int i;
    	int index=findVirtualIndex((Integer)poly[0].elementAt(0));
    	((Node)(allNodes.elementAt(index))).setX(500);
    	((Node)(allNodes.elementAt(index))).setY(500);
    	((Node)(allNodes.elementAt(index))).setDynamicAlloted(true);
    	//allotedNodes.addElement(((Node)(allNodes.elementAt(index))));
    	
    	
         index=findVirtualIndex((Integer)poly[0].elementAt(1));
    	((Node)(allNodes.elementAt(index))).setX(500);
    	((Node)(allNodes.elementAt(index))).setY(600);
    	((Node)(allNodes.elementAt(index))).setDynamicAlloted(true);
    	//allotedNodes.addElement(((Node)(allNodes.elementAt(index))));
    	
    	index=findVirtualIndex((Integer)poly[0].elementAt(2));
    	((Node)(allNodes.elementAt(index))).setX(600);
    	((Node)(allNodes.elementAt(index))).setY(500);
    	((Node)(allNodes.elementAt(index))).setDynamicAlloted(true);
    	//allotedNodes.addElement(((Node)(allNodes.elementAt(index))));	
    	
    	int num=1;
    	int numAlloted;
    	System.out.println("\n Number of polygons:"+numPolygons);
    	while(num<numPolygons)
    	{
    		numAlloted=findNumAlloted(num);
    	
    		System.out.println("\n Number of polygons alloted:"+numAlloted+"for "+num);
    		if(numAlloted==0)
    		{
    			arrangePolygonType0(num);
    		}
    		
    		if(numAlloted==1)
    		{
    			arrangePolygonType1(num);
    		}
    		
    		if(numAlloted==2)
    		{
    			System.out.println("Alloted indexs are:"+alloted1+" "+alloted2);
    			arrangePolygonType2(num);
    		}
    		
    		num+=1;
    	}
    	
    }
    
    public int findVirtualIndex(int index)
    {
    	int i;
    	for(i=0;i<allNodes.size();i++)
    	{
    		if(((Node)(allNodes.elementAt(i))).getIndex()==index)
    			return i;
    	}
    	
    	return -1;
    }
    
    public int findNumAlloted(int Id)
    {
    	int i;
    	int index;
    	int numAlloted=0;
    	for(i=0;i<3;i++)
    	{
    		index=findVirtualIndex((Integer)poly[Id].elementAt(i));
    		if(((Node)(allNodes.elementAt(index))).getDynamicAlloted()==true)
    		{
    			if(numAlloted==0)
    				alloted1=index;
    				
    			else if(numAlloted==1)
    				alloted2=index;
    				
    			else
    				 alloted3=index;
    				 
    		    numAlloted+=1;		 	
    		}
    	}
    	return numAlloted;
    }
    
    public void arrangePolygonType0(int Id)
    {
               	
    }
   
    
    public void arrangePolygonType1(int Id)
    {
       	findRegionOfMinDensity(1,Id);  
    }
    
     
    public void arrangePolygonType2(int Id)
    {
         int index1=alloted1;
         int index2=alloted2;
         
         Node node1=((Node)(allNodes.elementAt(index1)));
         Node node2=((Node)(allNodes.elementAt(index2)));	
    
         int i;
         
         
         
         findRegionOfMinDensity(2,Id);
         
         int index3=0;
         int temp;
         for(i=0;i<3;i++)
         {
         	temp=findVirtualIndex((Integer)(poly[Id].elementAt(i)));
         	if(temp!=alloted1 && temp!=alloted2)
         	{
         		index3=temp;
         		break;
         	}
         }
         
         
         System.out.println("Indexs:"+index1+" "+index2+" "+index3);
         Node node3=((Node)(allNodes.elementAt(index3)));
         System.out.println("Places:"+xplace+" "+yplace);
         
           ((Node)node3).setX(xplace);
           ((Node)node3).setY(yplace);
           
           ((Node)node1).setDynamicAlloted(true);
           ((Node)node2).setDynamicAlloted(true);
           ((Node)node3).setDynamicAlloted(true);
           
          // allotedNodes.addElement(node1);
           //allotedNodes.addElement(node2);
           //allotedNodes.addElement(node3);
         
    }
     
     
    public void findRegionOfMinDensity(int type,int Id)
    {
    	int i;
    	
    	if(type==2)
    	{
    		
    	 int index1=alloted1;
         int index2=alloted2;
         	
    	 Node node1=((Node)(allNodes.elementAt(index1)));
         Node node2=((Node)(allNodes.elementAt(index2)));
         
         double x1=((Node)node1).getX();
         double x2=((Node)node2).getX();
         
         double y1=((Node)node1).getY();
         double y2=((Node)node2).getY(); 
         
         double midX=((Node)node1).getX()+((Node)node2).getX();
         midX/=2;
         
         double midY=((Node)node1).getY()+((Node)node2).getY();
         midY/=2;
         
         int northEast=0,northWest=0,southEast=0,southWest=0;
         
         for(i=0;i<allNodes.size();i++)
         {
         	if(((Node)(allNodes.elementAt(i))).getDynamicAlloted()==true)
         	{Node node=((Node)(allNodes.elementAt(i)));
         	
         
           if(contains(midX,midY,node))
         	{
         	if(((Node)(node)).getX()<x2 && ((Node)(node)).getX()<x1)
         	{
         		if(((Node)(node)).getY()<y2 && ((Node)(node)).getY()<y1)
         		{
         			if( ((Node)(node)).getX()>midX )
         			{
         				if(((Node)(node)).getY()>midY)
         				{
         					northEast+=1;
         				}
         				
         				else
         				{
         					southEast+=1;
         				}
         			}
         			
         			else
         			{
         				if(((Node)(node)).getY()>midY)
         				{
         					northWest+=1;
         				}
         				
         				else
         				{
         					southWest+=1;
         				}
         			}
         		}
         	}
          }
         }	
         }
        	
       if(northEast<=northWest &&northEast<=southWest &&northEast<=southEast)
       {
       	  xplace=(midX+x2)/2;
       	  yplace=y2+40;
       }
       
       else if(northWest<=southWest &&northWest<=southEast)
       {
       	 xplace=(midX+x1)/2;
       	 yplace=y1+40;
       }
       
       else if(southWest<=southEast)
       {
       	xplace=(midX+x1)/2;
       	yplace=y1-40;
       }	
       	
       else 
       {
       	xplace=(midX+x2)/2;
       	yplace=y1+40;
       } 
       	
       	xplace+=40;
       	System.out.println("North...."+northEast+" "+northWest+"  "+southWest+"  "+southEast);
        System.out.println("Xplace and yplace:"+xplace+" "+yplace);
    	}
    	
    	
    	
    	if(type==1)
    	{
    		int index1=alloted1;
    		int index2=-1,index3=-1;
    		int quadrant,temp;
    		double x=((Node)(allNodes.elementAt(index1))).getX();
    		double y=((Node)(allNodes.elementAt(index1))).getY();
    		
    		double x1,x2,y1,y2;
    		
    		 int northEast=0,northWest=0,southEast=0,southWest=0;
         
              for(i=0;i<allotedNodes.size();i++)
                   {
                   	quadrant=containsReturningQuadrant(x,y,((Node)allNodes.elementAt(i)));
                   	  if(quadrant==1)
                   	  	 northEast+=1;
                   	  	 
                   	  if(quadrant==2)
                   	  	 northWest+=1;
                   	  	 
                   	  if(quadrant==3)
                   	  	 southWest+=1;
                   	  	 
                   	  if(quadrant==4)
                   	  	 southEast+=1;	 	 	 
                   }
                   
             for(i=0;i<3;i++) 
             {
                 temp=(Integer)poly[Id].elementAt(i);
                 temp=findVirtualIndex(temp);
                 
                 if(temp!=index1 &&index2!=-1)
                 {
                    index3=temp;	
                 }
                 else if(temp!=index1 )
                 	index2=temp;
             }     
             
             	
       if(northEast<=northWest &&northEast<=southWest &&northEast<=southEast)
       {
       	  x1=x;
       	  y1=y+20;
       	  
       	  x2=x+20;
       	  y2=y; 
       }
       
       else if(northWest<=southWest &&northWest<=southEast)
       {
       	 x1=x;
       	 y1=y+20;
       	 
       	 x2=x-20;
       	 y2=y;
       }
       
       else if(southWest<=southEast)
       {
       	  
       	 x1=x-20;
       	 y1=y;
       	
       	 x2=x;
       	 y2=y-20;
       }	
       	
       else 
       {
       	 x1=x;
       	 y1=y-20;
       	 
       	 x2=x+20;
       	 y2=y; 
       } 
       	
           ((Node)(allNodes.elementAt(index2))).setX(x1);
       	   ((Node)(allNodes.elementAt(index2))).setY(y1);
           ((Node)(allNodes.elementAt(index2))).setDynamicAlloted(true);
          // allotedNodes.addElement(((Node)(allNodes.elementAt(index2))));
           
           
           ((Node)(allNodes.elementAt(index3))).setX(x2);
       	   ((Node)(allNodes.elementAt(index3))).setY(y2);
           ((Node)(allNodes.elementAt(index3))).setDynamicAlloted(true);
           //allotedNodes.addElement(((Node)(allNodes.elementAt(index3))));       	  
    	}
    }
    
    
     public boolean contains(double x,double y,Node node)
    {
    	double distance=(((Node)(node)).getX()-x)*(((Node)(node)).getX()-x)+(((Node)(node)).getY()-y)*(((Node)(node)).getY()-y);
       if(distance<=15*15)//Radius of search
       	 return true;
       	 
       	 return false;
    }
    
    
    public int containsReturningQuadrant(double x,double y,Node node)
    {
       double distance=(((Node)(node)).getX()-x)*(((Node)(node)).getX()-x)+(((Node)(node)).getY()-y)*(((Node)(node)).getY()-y);
       if(distance<=15*15)//Radius of search
       	 return -1;
       	
       double nodeX=((Node)node).getX();
       double nodeY=((Node)node).getY();
       	 	 
       	 
       if(nodeX<x)
       {
       	   if(nodeY<y)
       	   	 return 4;
       	   	 
       	   else
       	   	  return 1; 
       }
       
       else
       {
       	if(nodeY<y)
       		return 3;
       	
       	else
       		 return 2;
       }
       	
      		 
       	 
    }
    
    
    public void arrangeOthers()
    {
      	int weight;
    	double angle=0;
    	double distance;
    	int numIncidentNodes;
    	int quadrant;
    	int xsign,ysign;
    	double x,y;
    	Node start,end,node;
    	int i;
    	int index;
    	double cordX,cordY;
    	Random r=new Random();
    	//System.out.println("Diffrent sizes:"+allotedNodes.size()+" "+allNodes.size());
    	setAllotedNodes();
    	 while(que.size()!=0)//allotedNodes.size()!=allNodes.size())
    	 {
    	 	//System.out.println("Entering the loop of death:");
    	 	node=((Node)(que.elementAt(0)));
    	 	System.out.println("\n Node of Index is popped-------------------------------!!!!!!"+((Node)(node)).getIndex());
            x=((Node)(node)).getX();
            y=((Node)(node)).getY();
    	 		
    	 	numIncidentNodes=findNumIncidentNodesUnAllocated(node);
    		System.out.println("Num Incident  nodes  and index & index"+	numIncidentNodes+"  "+((Node)(node)).getIndex());
    		
    		if(numIncidentNodes!=0)
    		{angle=120/numIncidentNodes;//angle set to 75`
    		
    		double curAngle=-15;
    	//	System.out.println("Goings to find quad:");
    		quadrant=containsReturningQuadrant(((Node)node).getX(),((Node)(node)).getY());
    	System.out.println("The quad  is!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!:$$$$$$$$"+quadrant);
    	    //System.out.println("Coming bck from containsreturningQuad:");
    		index=((Node)(node)).getIndex();
    		
    			if(quadrant==1||quadrant==4  )
    			xsign=1;
    		else
    			xsign=-1;
    		
    		if(quadrant==2||quadrant==1)
    			ysign=-1;
    			
    		else
    			ysign=1;
    			
    			
    		Vector incidentEdges=((Node)(node)).getIncidentEdges();
    	    addIncidentNodesToQue(node);
    	    for(i=0;i<incidentEdges.size();i++)
    	    {
    	    start=((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()));
        	end=((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode()));
        //	System.out.println("Dyanmic allocation of inciden t edges"+i);
        	if(((Node)(start)).getDynamicAlloted()==false)
        		if(((Node)(start)).getIndex()!=index)
        		{
        			weight=findNumIncidentNodesUnAllocated(start);
                    if(weight==0)
                    	weight=1;
        			distance=150*weight;
        			
        			
        			cordX=(distance*xsign*Math.cos(curAngle));
        				cordY=(distance*ysign*Math.sin(curAngle));
        			if(cordX>50)
        				cordX=100;
        			if(cordY>50)
        				cordY=50;
        			
        			cordX+=x;
        			cordY+=y;		
        						
        				System.out.println("Corrdinates are@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+cordX+"   "+cordY);
        			((Node)(start)).setX(cordX);
        			((Node)(start)).setY(cordY);
        			
        			curAngle+=angle;
        			((Node)(start)).setDynamicAlloted(true);
        			allotedNodes.addElement(start);
        		}
           
           if(((Node)(end)).getDynamicAlloted()==false)
        		if(((Node)(end)).getIndex()!=index)
        		{
        			weight=findNumIncidentNodesUnAllocated(end);
        			distance=150*weight;
        			
        				
        		cordX=(distance*xsign*Math.cos(curAngle));
        				cordY=(distance*ysign*Math.sin(curAngle));
        			if(cordX>50)
        				cordX=50;
        			if(cordY>50)
        				cordY=50;
        			
        			cordX+=x;
        			cordY+=y;
        				
        				System.out.println("Corrdinates are@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+cordX+"   "+cordY);
        			curAngle+=angle;
        			
        			((Node)(end)).setDynamicAlloted(true);
        			allotedNodes.addElement(end);
        		}
        		
        		System.out.println("\n Current Angle is+++++++++++++++++++++++++++++++++++++++++++++++++++"+i+"     "+curAngle);
    	    } 
    		
    		
    	 	System.out.println("Node of index is alloted + weight"+((Node)(node)).getIndex()+"   "+findNumIncidentNodesUnAllocated(node));
    	 //	((Node)(node)).setDynamicAlloted(true);
    	 	allotedNodes.addElement(node);
    	 
    	 	
    	 	
    		}
    		((Node)(node)).setDynamicAlloted(true);
    		que.removeElementAt(0);
    	 }
    }
     
     
     public Node findUnAllotedNode()
     {
     	int i,j;
     	Vector incidentEdges;
     	int index;
     	Node start,end;
     	//System.out.println("Alloted Node .size"+allotedNodes.size());
     	for(i=0;i<allotedNodes.size();i++)
     	{
     		Node node=((Node)(allotedNodes.elementAt(i)));
     		incidentEdges=((Node)(node)).getIncidentEdges();
     		
     		index=((Node)node).getIndex();
     		j=0;
     		while(j<incidentEdges.size())
             {
        	start=((Node)(((Edge)(incidentEdges.elementAt(j))).getStartNode()));
        	end=((Node)(((Edge)(incidentEdges.elementAt(j))).getEndNode()));
        	
        	
        	
        	if(((Node)(start)).getDynamicAlloted()==false)
        		if(((Node)(start)).getIndex()!=index)
                   return start;
           
           if(((Node)(end)).getDynamicAlloted()==false)
        		if(((Node)(end)).getIndex()!=index)
                   return end;
            j++;
             }		
        		
        }
     		
            return null;
     	}
     
     
  
    	
    	
    public int findNumIncidentNodesUnAllocated(Node node)
    {
    	int i=0;
    	Node start,end;
    	int num=0;
    	Vector incidentEdges=((Node)(node)).getIncidentEdges();
    	int index=((Node)node).getIndex();
        while(i<incidentEdges.size())
        {
        	start=((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()));
        	end=((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode()));
        	
        	if(((Node)(start)).getDynamicAlloted()==false)
        		if(((Node)(start)).getIndex()!=index)
                   num+=1;
           
           if(((Node)(end)).getDynamicAlloted()==false)
        		if(((Node)(end)).getIndex()!=index)
                   num+=1; 
            i++;		
        		
        }
        return num;
    }	
   
   
   public void setAllotedNodes()
   {
   	int i;
   	//System.out.println("Alloted nodes size is:::::"+allotedNodes.size());
   	for(i=0;i<allNodes.size();i++)
   	{
   		if(((Node)(allNodes.elementAt(i))).getDynamicAlloted()==true)
   		{
   		 allotedNodes.addElement(((Node)(allNodes.elementAt(i))));
         que.addElement(((Node)(allNodes.elementAt(i))));
   		}   	
   	}
   	System.out.println("No of Nodes alloted is:"+que.size());
   } 	
   	
   	
   	   public int containsReturningQuadrant(double x,double y)
    {
    	double distance;
    	Node node;
    	int q1=0,q2=0,q3=0,q4=0;
    	int i;
    	for(i=0;i<allotedNodes.size();i++)
    	{
    		node=((Node)(allotedNodes.elementAt(i)));
    		distance=(((Node)(node)).getX()-x)*(((Node)(node)).getX()-x)+(((Node)(node)).getY()-y)*(((Node)(node)).getY()-y);
    	System.out.println("the distance was:"+distance);	     
    		 if(distance<=100*100)
    	      {
    	      	double nodeX=((Node)node).getX();
                double nodeY=((Node)node).getY(); 
                
               // System.out.println("distance,nodeX,nodeY,x,y,q1,q2,q3,q4"+distance+" "+nodeX+" "+nodeY+" "+x+" "+y+" "+q1+" "+q2+" "+q3+" "+q4+" ");	 
       if(nodeX<x)
       {
       	   if(nodeY<y)
       	   	  q2++;
       	   	 
       	   else
       	   	   q3++; 
       }
       
       else
       {
       	if(nodeY<y)
       		 q1++;
       	
       	else
       		  q4++;
       }
       		
       		
    	      }
    	      
    	     
    	}
    	System.out.println("Later q1,q2,q3,q4"+q1+" "+q2+" "+q3+" "+q4+" ");
    	
    	Vector<Integer> temp=new Vector<Integer>();
    	
    	
    	if(q1==0)
    	   temp.addElement(1);
    	   	
        if(q2==0)
    		temp.addElement(2);
    		
        if(q3==0)
    		temp.addElement(3);
    		
        if(q4==0)
    		temp.addElement(4);
    		
    	Random r=new Random();	
    		
    
    		
    
    		
    	if(temp.size()!=0)
    	{
    		
    		
    		int ret=r.nextInt(100);//temp.size());
    		ret=ret%temp.size();
    		System.out.println("Returning::::::::::::::::::::::::::::::::::::::::::::::::"+temp.elementAt(ret)+"   "+temp.size());
    		return temp.elementAt(ret);
    	}
    	
    		
    	if(q1<=q2 &&q1<=q3 &&q1<=q4)
       {
       	  return 1;
       }
       
       else if(q2<=q3 &&q2<=q4)
       {
       	  return 2;
       }
       
       else if(q3<=q4)
       {
       	  return 3;

       }	
       	
       else 
       {
       	   return 4;
       } 
       	
    	
    }
    
    
    public void addIncidentNodesToQue(Node node)
    {
    	int i=0;
    	
    	int index=((Node)(node)).getIndex();
    	Node start,end;
    	
        Vector incidentEdges=((Node)(node)).getIncidentEdges();
    	   //System.out.println("\n number of elemnts in que"+)
        while(i<incidentEdges.size())
        {
        		System.out.println("Running addIncidentNodesToQue------------------------------------------"+i+"    "+index);
            start=((Node)(((Edge)(incidentEdges.elementAt(i))).getStartNode()));
        	end=((Node)(((Edge)(incidentEdges.elementAt(i))).getEndNode())); 
        	System.out.println("Incident is--------------- start  -------------"+((Node)(start)).getDynamicAlloted());
        	System.out.println("Incident is--------------- end  -------------"+((Node)(end)).getDynamicAlloted());
        	if(((Node)(start)).getDynamicAlloted()==false && ((Node)(start)).getIndex()!=index)
        	{
        	System.out.println("Node of index is put to que:----------------------------------------"+((Node)(start)).getIndex());
                   que.addElement(start);
        	}       
                    
           else if(((Node)(end)).getDynamicAlloted()==false &&((Node)(end)).getIndex()!=index)
           {
           	System.out.println("Node of index is put to que:-----------------------------------------"+((Node)(end)).getIndex());
                  que.addElement(end);
           }       
        		i++;
        }
    }
    
    public void arrangeCenterNode()
    {
    	
    	int index=findVirtualIndex(findIndexOfMaxIncidentNodes());
    	((Node)(allNodes.elementAt(index))).setX(500);
    	((Node)(allNodes.elementAt(index))).setY(500);
    	((Node)(allNodes.elementAt(index))).setDynamicAlloted(true);
    }
    
    
    public int findIndexOfMaxIncidentNodes()
    {
    	int i,j;
    	int max=0,rowNumber=-1;
    	int a[]=new int[adj[0].length];
    	
    	for(i=1;i<adj[0].length;i++)
    	{
    		a[i]=0;
    		for(j=1;j<adj[0].length;j++)
    		{
    			if(adj[i][j]==1)
    				a[i]+=1;
    		}
    		
    		if(a[i]>max)
    		{
    			max=a[i];
    			rowNumber=i;
    		}
    	}
    	
    	
    	if(rowNumber!=-1)
    	{
    		return adj[rowNumber][0];
    	}
    	
    	return 0;
    }
    
    
    public void reDraw()
    {
      	
      sameCoordinate();	
      //insidePolygon();	
      putInPicture();
    }
    
    
    public void sameCoordinate()
    {
    	int i,j;
    	 for(i=0;i<allNodes.size();i++)
    	 {
    	 	 for(j=0;j<allNodes.size();j++)
    	 	 {
    	 	 	 if(i!=j)
    	 	 	 {
    	 	 	 	 if((((Node)(allNodes.elementAt(i))).getX()==((Node)(allNodes.elementAt(j))).getX())&&(((Node)(allNodes.elementAt(i))).getY()==((Node)(allNodes.elementAt(j))).getY()))
    	 	 	 	 {
    	 	 	 	 	int region=containsReturningQuadrant(((Node)(allNodes.elementAt(i))).getX(),((Node)(allNodes.elementAt(i))).getY());
    	 	 	 	 	 if(region==1)
    	 	 	 	 	 {
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setX(((Node)(allNodes.elementAt(i))).getX()+10);
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setY(((Node)(allNodes.elementAt(i))).getY()-10);
    	 	 	 	 	 }
    	 	 	 	 	 else if(region==2)
    	 	 	 	 	 {
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setX(((Node)(allNodes.elementAt(i))).getX()-10);
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setY(((Node)(allNodes.elementAt(i))).getY()-10);
    	 	 	 	 	 }
    	 	 	 	 	 else if(region==3)
    	 	 	 	 	 {
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setX(((Node)(allNodes.elementAt(i))).getX()-10);
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setY(((Node)(allNodes.elementAt(i))).getY()+10);
    	 	 	 	 	 }
    	 	 	 	 	 else if(region==4)
    	 	 	 	 	 {
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setX(((Node)(allNodes.elementAt(i))).getX()+10);
    	 	 	 	 	 ((Node)(allNodes.elementAt(i))).setY(((Node)(allNodes.elementAt(i))).getY()+10);
    	 	 	 	 	 }
    	 	 	 	 	 
    	 	 	 	 }
    	 	 	 }
    	 	 }
    	 }
    }
    
    public void insidePolygon()
    {
    	int i,j;
    	int index;
    	int x[][]=new int[100][3];
    	int y[][]=new int[100][3];
    	for(i=0;i<numPolygons;i++)
    	{
    		 index=findVirtualIndex((Integer)poly[i].elementAt(0));
    		 x[i][0]=(((Node)(allNodes.elementAt(index))).getx());
    		 y[i][0]=((Node)(allNodes.elementAt(index))).gety();
    		 
    		 index=findVirtualIndex((Integer)poly[i].elementAt(1));
    		 x[i][1]=((Node)(allNodes.elementAt(index))).getx();
    		 y[i][1]=((Node)(allNodes.elementAt(index))).gety();
    		 
    		 index=findVirtualIndex((Integer)poly[i].elementAt(2));
    		 x[i][2]=((Node)(allNodes.elementAt(index))).getx();
    		 y[i][2]=((Node)(allNodes.elementAt(index))).gety();
    	}
    	
    	
    	for(i=0;i<allNodes.size();i++)
    	{
    		for(j=0;j<numPolygons;j++)
    		{
    		
    	    Polygon polygon=new Polygon(x[j],y[j],3);
    	   	if(polygon.contains(((Node)(allNodes.elementAt(i))).getX(),((Node)(allNodes.elementAt(i))).getY())==false)
    	   	{
    	   		rectifyInside(x[j],y[j],i);
    	   	}
    		}
    	}
    }
  
  public void rectifyInside(int x[],int y[],int index)
  {
       Arrays.sort(x);
       Arrays.sort(y);
       
       ((Node)(allNodes.elementAt(index))).setX(x[2]+10);  
       ((Node)(allNodes.elementAt(index))).setY(y[2]+10);  	 	 
     
        
  }
  
  public void putInPicture()
  {
  	double maxx=0;
  	double maxy=0;
  	int i,j;
  	
  	for(i=0;i<allNodes.size();i++)
  	{
  		if(((Node)(allNodes.elementAt(i))).getX()<maxx)
  		{
  			maxx=((Node)(allNodes.elementAt(i))).getX();
  		}
  		
  		if(((Node)(allNodes.elementAt(i))).getY()<maxy)
  		{
  		maxy=((Node)(allNodes.elementAt(i))).getY();
  		}	
  		System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+maxx+"   "+maxy);	
  	}

maxx*=-1;
maxy*=-1;
   for(i=0;i<allNodes.size();i++)
   {
   	  ((Node)(allNodes.elementAt(i))).setX(((Node)(allNodes.elementAt(i))).getX()+maxx);  
      ((Node)(allNodes.elementAt(i))).setY(((Node)(allNodes.elementAt(i))).getY()+maxy);  	 
   }  	
  }
  
  
  }
  
    
 