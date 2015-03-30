import java.awt.*;
import java.util.*;
import javax.swing.*;

public class SpanningTree
{	
	private Graph graph,newGraph;	
	private EdgeArray edgeArray[];
	Vector edges,nodes;
	private int i,j,temp,count,index;
	private Edge tempEdge;
	private Node startNode,endNode;
	private boolean cycle;
	private BreadthSearch breadthSearch;
	
	SpanningTree(Panel panel)
	{
		graph=panel.getGraph();
		edges=graph.getAllEdges();
		nodes=graph.getAllNodes();
		edgeArray=new EdgeArray[edges.size()];
		
		for(i=0;i<edges.size();i++)		
			edgeArray[i]=new EdgeArray((Edge)edges.elementAt(i));			
		
		for(i=0;i<edges.size();i++)
			for(j=0;j<edges.size()-1;j++)
			{
				if(edgeArray[j].getWeight()>edgeArray[j+1].getWeight())
				{
					temp=edgeArray[j].getIndex();					
					edgeArray[j].setIndex(edgeArray[j+1].getIndex());					
					edgeArray[j+1].setIndex(temp);
					
					temp=edgeArray[j].getWeight();					
					edgeArray[j].setWeight(edgeArray[j+1].getWeight());					
					edgeArray[j+1].setWeight(temp);										
				}
			}
		for(i=0;i<edges.size();i++)
			System.out.println("   "+edgeArray[i].getIndex()+"  "+edgeArray[i].getWeight());
		
		newGraph=new Graph();
		for(i=0;i<nodes.size();i++)
		{
			newGraph.addNode(((Node)nodes.elementAt(i)));
		}
		
		nodes=newGraph.getAllNodes();
		for(i=0;i<nodes.size();i++)
			System.out.println(((Node)nodes.elementAt(i)).getNumIncidentEdges());
		
		i=0;
		count=0;		
		
		while(count<(newGraph.getNodesSize()-1))						
		{	
			index=((EdgeArray)edgeArray[i]).getIndex();
			tempEdge=(Edge)graph.getEdge(index);
						
			//System.out.println(tempEdge.getIndex()+"   "+newGraph.getEdgesSize());
			index=tempEdge.getStartNode().getIndex();
			startNode=newGraph.getNode(index);
			
			index=tempEdge.getEndNode().getIndex();
			endNode=newGraph.getNode(index);
			
			//startNode=tempEdge.getStartNode();
			//endNode=tempEdge.getEndNode();
			
			breadthSearch=new BreadthSearch(newGraph);						
			cycle=breadthSearch.bfs(startNode,endNode,false);
			
			System.out.println(startNode.getIndex()+"  "+endNode.getIndex()+"   "+cycle+"     "+newGraph.getEdgesSize());
						
			if(cycle==false)
			{			
				newGraph.addEdge(tempEdge,startNode,endNode);
				count++;
				//System.out.println("---------"+tempEdge.getIndex());
			}
			i++;
		}		
		
		nodes=newGraph.getAllNodes();
		edges=newGraph.getAllEdges();		
			
		/*for(i=0;i<newGraph.getEdgesSize();i++)
		{					
			System.out.println("   "+((Edge)edges.elementAt(i)).getIndex());
		}*/
	}	


	private class EdgeArray
	{
		private int index,weight;
		private Edge edge;
		
		EdgeArray(Edge edge1)
		{
			edge=edge1;
			setIndex(edge.getIndex());
			setWeight(edge.getWeight());
		}		
	
		public void setWeight(int val)
		{
			weight=val;
		}
	
		public int getWeight()
		{
			return weight;
		}
	
		public void setIndex(int val)
		{
			index=val;
		}
	
		public int getIndex()
		{
			return index;
		}				
	}
}