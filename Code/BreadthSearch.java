import java.util.*;
import javax.swing.*;
import java.awt.*;

public class BreadthSearch 
{
	private Vector nodes,edges;
	private Graph graph;
	private Node node,node1,node2;
	private Edge edge;
	private int n,i,vertex[],j;		
	private String string="";
	private Panel panel;
	private Stack stack,stack2;
	public Vector bf;
	
	BreadthSearch(Graph sentGraph)
	{				
		graph=sentGraph;	
		nodes=graph.getAllNodes();		
		n=nodes.size();		
		vertex=new int[n];			
		for(i=0;i<n;i++)
		{
			vertex[i]=((Node)nodes.elementAt(i)).getIndex();					
			((Node)nodes.elementAt(i)).setVisited(false);
		}					
	}
	
	BreadthSearch(Graph sentGraph, int index)
	{
		graph=sentGraph;	
		nodes=graph.getAllNodes();		
		n=nodes.size();		
		vertex=new int[n];			
		bf=new Vector();
		
		for(i=0;i<n;i++)				
			((Node)nodes.elementAt(i)).setVisited(false);
		
		Vector q=new Vector();
		Node n=(Node)nodes.elementAt(index-1);
		n.setVisited(true);
		bf.add(n.getIndex());
		q.add(n);
		
		while(!q.isEmpty())
		{
			Node nod=(Node)q.remove(0);
			
			for(i=0;i<nod.getNumIncidentEdges();i++)
			{
				edge=(Edge)nod.incidentEdges.elementAt(i);			
				Node node=graph.getOppositeNode(nod,edge);
				
				if(!node.getVisited())
				{
					q.add(node);
					node.setVisited(true);
					bf.add(node.getIndex());
				}
			}
		}
		
	}

	
	public boolean bfs(Boolean display,int val)
	{
		boolean found=false;		
		node=(Node)nodes.elementAt(0);		
		node.setVisited(true);
		stack=new Stack();
		stack2=new Stack();
		
		stack.push(node);
		if(node.getIndex()==val)
			found=true;
		if(display)
			System.out.println(""+node.getIndex());
		while(!stack.empty())
		{			
			node1=(Node)stack.pop();
			stack2.push(node1);						
			edges=node1.getIncidentEdges();
			for(i=0;i<node1.getNumIncidentEdges();i++)
			{
				edge=(Edge)edges.elementAt(i);
				node2=graph.getOppositeNode(node1,edge);				
				if(!node2.getVisited())
				{					
					node2.setVisited(true);
					stack.push(node2);	
					if(node2.getIndex()==val)
						found=true;	
					if(display)			
						System.out.println(node2.getIndex());
				}				
			}						
		}				
		return found;						
	}
	
	public boolean bfs(Node firstNode,Node secondNode,Boolean display)
	{							
		boolean found=false;		
		int val=secondNode.getIndex();
		node=firstNode;		
		node.setVisited(true);
		
		System.out.println(node.getIndex()+"--------"+secondNode.getIndex()+"******"+node.getNumIncidentEdges());
		
		stack=new Stack();
		stack2=new Stack();				
		
		stack.push(node);
		if(node.getIndex()==val)
		{
			found=true;						
		}
		if(display)
			System.out.println(""+node.getIndex());
		while(!stack.empty())
		{			
			node1=(Node)stack.pop();
			stack2.push(node1);						
			edges=node1.getIncidentEdges();
						
			for(i=0;i<node1.getNumIncidentEdges();i++)
			{
				edge=(Edge)edges.elementAt(i);
				node2=graph.getOppositeNode(node1,edge);				
				if(!node2.getVisited())
				{					
					node2.setVisited(true);
					stack.push(node2);	
					if(node2.getIndex()==val)
					{
						found=true;	
						System.out.println(node2.getIndex()+"----"+val);					
					}
					if(display)			
						System.out.println(node2.getIndex());
				}				
			}						
		}	
			return found;						
	}				
}