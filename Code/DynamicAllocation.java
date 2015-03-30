/**
 * @(#)DynamicAllocation.java
 *
 *
 * @author 
 * @version 1.00 2009/7/22
 */

import java.util.*;
import java.lang.*;
import java.lang.String.*;

public class DynamicAllocation {

Graph graph;
int adj[][]=new int[101][101];//[((Graph)(graph)).getNumNodes()][((Graph)(graph)).getNumNodes()];
Vector allNodes;
Vector allEdges;
Vector keys=new Vector<Integer>(100);
Vector stringKeys=new Vector<String>(100);
Vector unAllotedNodes;
Vector allotedNodes=new Vector();
Vector poly[]=new Vector[100];
int numPolygons=0;
double xplace,yplace;
    public DynamicAllocation(Graph newGraph)
    	 
    	{
    		int i;
    		graph=newGraph;
    		allNodes=((Graph)(graph)).getAllNodes();
    		allEdges=((Graph)(graph)).getAllEdges();
    		//int adj[][]=new int[((Graph)(graph)).getNumNodes()][((Graph)(graph)).getNumNodes()];
    		for(i=0;i<10;i++)
    		poly[i]=new Vector<Integer>(3);
        }
      
     public void adjencyMatrix()
     {
      int start,end;
      int i,j;
     	  for(i=0;i<allEdges.size();i++)
     	  {
     	  	System.out.println("edge"+i);
     	     start=((Edge)(allEdges.elementAt(i))).getStartNodeIndex();
     	     end=((Edge)(allEdges.elementAt(i))).getEndNodeIndex();
     	    System.out.println("edge"+i+" "+start+" "+end); 
     	     adj[start-1][end-1]=1;
     	     adj[end-1][start-1]=1; 
     	  }
     	  
     	arrangeInOrderOfIncidentNodes();
        
        
        for(i=0;i<allNodes.size();i++)
        {
        	for(j=0;j<allNodes.size();j++)
        	{
        		
        		System.out.print(" "+adj[i][j]);
        	}
        System.out.println(" ");
        }
        
        
       findPolygons(); 
       unAllotedNodes=(Vector)allNodes.clone();
       
       if(numPolygons==0)
       	  arrangeOthers();
       	  
       else
       	  arrangePolygons();	  	
     }
    
    public void arrangeInOrderOfIncidentEdges()
    {
       int temp[]=new int[allNodes.size()];
       
       int i,j,k;
       
       for(i=0;i<allNodes.size();i++)
       {
       	temp[i]=0;
       	for(j=0;j<allNodes.size();j++)
       	{
       	
       	     if(adj[i][j]==1)
       	     	temp[i]+=1;
        }
       } 
       
       
       int temporary;
       for(i=0;i<allNodes.size();i++)
       {
       	for(j=i;j<allNodes.size();j++)
       	{
       	
       	   if(temp[i]<temp[j])
       	   {
       	   	temporary=temp[i];
       	   	temp[i]=temp[j];
       	   	temp[j]=temporary;
       	   	  for(k=0;k<allNodes.size();k++)
       	   	  {
       	   	  	temp=adj[i][k];
       	   	  	adj[i][k]=adj[j][k];
       	   	  	adj[j][k]=temp;
       	   	  }
       	   	
       	    }
        }	
    }
    
    
    public void arrangePolygons()
    {
    	int i;
    	int x=(Integer)(poly[0].elementAt(0));
    	((Node)(allNodes.elementAt((Integer)poly[0].elementAt(0)))).setX(500);
    	((Node)(allNodes.elementAt((Integer)poly[0].elementAt(0)))).setY(500);
    	allotedNodes.addElement(((Node)(allNodes.elementAt((Integer)poly[0].elementAt(0)))));
    	
    	((Node)(allNodes.elementAt((Integer)poly[0].elementAt(1)))).setX(500);
    	((Node)(allNodes.elementAt((Integer)poly[0].elementAt(1)))).setY(600);
    	allotedNodes.addElement(((Node)(allNodes.elementAt((Integer)poly[0].elementAt(1)))));
    	
    	((Node)(allNodes.elementAt((Integer)poly[0].elementAt(2)))).setX(600);
    	((Node)(allNodes.elementAt((Integer)poly[0].elementAt(2)))).setY(500);
    	allotedNodes.addElement(((Node)(allNodes.elementAt((Integer)poly[0].elementAt(2)))));
    	
    	for(i=0;i<numPolygons;i++)
    		System.out.println("Keys:"+stringKeys.elementAt(i));
    		
    	for(i=1;i<numPolygons;i++)
    	{
    	  int numAllocated=findNumAllocated(i);	
    	  	if(i==1)
    	  	{
    	  		placeTwo(i,)
    	  	}
    	  String nodes=findKey(i);
             if(nodes!=null)
             {
             	placeAlong(i,nodes);
             	System.out.println("  nodes"+nodes);
             }
             else
             {
             	//placeApart(i);
             }
           // reDraw(); 
    	}
    	
    	/*while(unAllotedNodes.size()!=0)
    	{
    		
    	}*/
    	
    //	reDraw();
    }   
    
    public int findNumAllocated(int Id)
    {
      int i=0;;
      int numAllocated;
      String indexes=String.valueOf(stringKeys.elementAt(Id));
      while(i<3)
      {
      
      String temp=String.valueOf(indexes.charAt(i));
        if(((Node)(allNodes.elementAt(temp)))isAllocted(Integer.valueOf(temp))==true)
         {
      	numAllocated+=1;
         }
      }
      	
    }
    
    
    public void placeAlong(int Id,String codedString)
    {
        	int startIndex=Integer.valueOf(String.valueOf(codedString.charAt(0)));
            int endIndex=Integer.valueOf(String.valueOf(codedString.charAt(1)));
            
            double x1=((Node)(allNodes.elementAt(startIndex))).getX();
            double y1=((Node)(allNodes.elementAt(startIndex))).getY();
            double x2=((Node)(allNodes.elementAt(endIndex))).getX();
            double y2=((Node)(allNodes.elementAt(endIndex))).getY();
            
           
            
           System.out.print("Coordinates"+x1+" "+y1+" "+x2+" "+y2); 
           	
           findRegionAndPlace(x1,y1,x2,y2);
           	
           int index=findNodeIndex(Id,codedString);	
           ((Node)(allNodes.elementAt(index))).setX(xplace);
           ((Node)(allNodes.elementAt(index))).setY(yplace);		
    }
    
    public int findNodeIndex(int Id,String codedString)
    {
      String search=(String)stringKeys.elementAt(Id);
      Vector allIndex=new Vector<String>(3);
      int i;
      for(i=0;i<3;i++)
      {
      	allIndex.addElement((String.valueOf(search.charAt(0))));
      }
      
      for(i=0;i<codedString.length();i++)
      {
      	allIndex.removeElement((String.valueOf(search.charAt(i))));
      }
      int ret=Integer.valueOf(String.valueOf(allIndex.firstElement()));
      return ret;
    }
    
    public void findRegionAndPlace(double x11,double y11,double x22,double y22)
    {
    	
    	 double x1=Math.min(x11,x22);
    	 double x2=Math.max(x11,x22);
    	 
    	 double y1=Math.min(y11,y22);
    	 double y2=Math.max(y11,y22);
    	  
     	 double x_mid=(x1+x2)/2;
         double y_mid=(y1+y2)/2;
         int i;
         
         int northEast=0,northWest=0,southEast=0,southWest=0;
         
         for(i=0;i<allotedNodes.size();i++)
         {
         	Node node=((Node)(allotedNodes.elementAt(i)));
         	
         
           if(contains(x_mid,y_mid,node))
         	{
         	if(((Node)(node)).getX()<x2 && ((Node)(node)).getX()<x1)
         	{
         		if(((Node)(node)).getY()<y2 && ((Node)(node)).getY()<y1)
         		{
         			if( ((Node)(node)).getX()>x_mid )
         			{
         				if(((Node)(node)).getY()>y_mid)
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
         				if(((Node)(node)).getY()>y_mid)
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
    	
    	
       if(northEast<=northWest &&northEast<=southWest &&northEast<=southEast)
       {
       	  xplace=(x_mid+x2)/2;
       	  yplace=y2+10;
       }
       
       else if(northWest<=southWest &&northWest<=southEast)
       {
       	 xplace=(x_mid+x1)/2;
       	 yplace=y1+10;
       }
       
       else if(southWest<=southEast)
       {
       	xplace=(x_mid+x1)/2;
       	yplace=y1-10;
       }	
       	
       else 
       {
       	xplace=(x_mid+x2)/2;
       	yplace=y1+10;
       }	
       		
    }
    
    public boolean contains(double x,double y,Node node)
    {
    	double distance=(((Node)(node)).getX()-x)*(((Node)(node)).getX()-x)+(((Node)(node)).getY()-y)*(((Node)(node)).getY()-y);
       if(distance<=15*15)//Radius of search
       	 return true;
       	 
       	 return false;
    }
    
    public String findKey(int Id)
    {
    	int i,j;
    	String search=(String)stringKeys.elementAt(Id);
    	String temp="";
    	boolean allocated=false;
         for(i=0;i<3;i++)
         {
         	temp=search.substring(i,i+2);
         	System.out.println("searchin for:"+temp+" "+search);
         	for(j=0;j<Id;j++)
         	{
         		
         		if((String.valueOf(stringKeys.elementAt(j))).contains(temp))
         		{
         			allocated=true;
         			break;
         		}
         	}
         	if(allocated==true)
         		break;
         		
         	else
         	{
         		String temp2;
         		temp2=String.valueOf(temp.charAt(1))+String.valueOf(temp.charAt(0));
         	}
         	 
         	for(j=0;j<i;j++)
         	{
         		if((String.valueOf(stringKeys.elementAt(j))).contains(temp))
         		{
         			allocated=true;
         			break;
         		}
         	}
         	
         	
         		  		
          }
          
          System.out.println("Allocated"+allocated+" "+temp);
          if(allocated==true)
          	return temp;
           
          else
          	 return null; 
     }
    public void arrangeOthers()
    {
    	
    }	
    	 
    public void findPolygons()
    {
    	int i,j,k,l;
    	k=3;
    	int numtimes;
    	Vector polygon; 
    	
    	
    	
    	
    	
    	for(i=0;i<allNodes.size();i++)
    	{
    	  for(j=0;j<allNodes.size();j++)
    	  {
    	  	if(adj[i][j]==1 && i!=j)
    	  	{
    	  	  for(k=0;k<allNodes.size();k++)
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
}
