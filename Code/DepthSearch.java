import javax.swing.*;
import java.util.*;
import java.awt.*;

public class DepthSearch
{
	private Vector nodes,edges;
	private Graph graph;
	private Node node,node1,node2;
	private Edge edge;
	private int n,i,vertex[],j;		
	private String string="";
	private Panel panel;
	private Stack stack,stack2;
	public Vector df;
	
	DepthSearch(Graph sentGraph)
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
	
	DepthSearch(Graph sentGraph, int index)
	{
		graph=sentGraph;	
		nodes=graph.getAllNodes();				
		n=nodes.size();		
		vertex=new int[n];			
		
		for(i=0;i<n;i++)
		{												
			((Node)nodes.elementAt(i)).setVisited(false);
			((Node)nodes.elementAt(i)).processed=false;
		}
		
		df=new Vector();		
		node=(Node)nodes.elementAt(index-1);
		node.setVisited(true);
		
		Stack st=new Stack();
		st.add(node);
		
		while(!st.empty())
		{
			Node n=(Node)st.pop();					
			df.add(n.getIndex());
			n.processed=true;
			
			for(i=0;i<n.getNumIncidentEdges();i++)
			{
				edge=(Edge)n.incidentEdges.elementAt(i);			
				Node node=graph.getOppositeNode(n,edge);
				
				if(!node.getVisited() && !node.processed)
				{		
					st.push(node);
					node.setVisited(true);
				}
				else if(node.getVisited() && !node.processed)
				{
					st.remove(node);
					st.push(node);
				}
				
			}						
		}
	}
	
	public boolean dfs(boolean display, int val)
	{	
		boolean found=false;
		node=(Node)nodes.elementAt(0);	
		if(node.getIndex()==val)
			found=true;
		node.setVisited(true);
		stack=new Stack();
		stack2=new Stack();
		
		stack.push(node);
		while(!stack.empty())
		{			
			node1=(Node)stack.pop();
			stack2.push(node1);			
			edges=node1.getIncidentEdges();
			for(i=0;i<node1.getNumIncidentEdges();i++)
			{
				edge=(Edge)edges.elementAt(i);
				node2=graph.getOppositeNode(node1,edge);
				if(node.getIndex()==val)
					found=true;
				if(!node2.getVisited())
				{					
					node2.setVisited(true);
					stack.push(node2);					
				}				
			}						
		}
		
		n=stack2.size();
		if(display)
			for(i=0;i<n;i++)
				System.out.println(((Node)stack2.pop()).getIndex());
		
		return found;
	}	
}