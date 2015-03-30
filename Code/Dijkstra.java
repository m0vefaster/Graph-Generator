/**
 * @(#)Dijkstra.java
 *
 *
 * @author
 * @version 1.00 2010/10/25
 */
 import java.util.*;
 import java.awt.Color;


public class Dijkstra {
Vector AllNodes;
HashMap cost;
Node startNode,endNode;
Vector selectedEdges;

    public Dijkstra(Vector AllNodes,Node startNode,Node endNode) {
    	this.AllNodes = AllNodes;
		this.startNode = startNode;
		this.endNode = endNode;
		selectedEdges = new Vector();
		shortestPath(startNode);

		Node prev = endNode;
		Edge edge;
		if(selectedEdges.size()!=0)
		for(int i = selectedEdges.size() - 1; i >= 0 ; i--) {

			edge=(Edge)selectedEdges.get(i);
			if(prev==startNode)
				selectedEdges.remove(edge);
			if(prev==edge.getStartNode())
				prev=edge.getEndNode();
			else if(prev==edge.getEndNode())
				prev=edge.getStartNode();
			else{
				selectedEdges.remove(edge);
			}

		}
		for(int i=0;i<selectedEdges.size();i++)
			((Edge)selectedEdges.get(i)).setColor(Color.GREEN);


    }

	public void shortestPath(Node v) {

		Node u,node;
		Edge edge;
		HashMap<Node,Integer> distance = new HashMap<Node,Integer>();
		Vector found = new Vector();
		HashMap<Node,Integer> temp = new HashMap<Node,Integer>();
		found.add(v);
	 	distance = getAdjacentNodes(v);
	 	distance.put(v,0); //gives the distance between node v and its adjacent nodes
		do{
			u = findMin(v,distance,found);
			found.add(u);
			selectedEdges.add(findMinEdge(u,distance,found));

			temp = getAdjacentNodes(u);
			for(int y = 0; y < AllNodes.size(); y++) {
				Node w = (Node)AllNodes.get(y);
				if(!found.contains(w) && temp.containsKey(w)) {
					if(!distance.containsKey(w))
						distance.put(w,(Integer)distance.get(u) + (Integer)temp.get(w));
					else if((Integer)distance.get(w)>(Integer)distance.get(u) + (Integer)temp.get(w)) {

						distance.remove(w);
						distance.put(w,(Integer)distance.get(u) + (Integer)temp.get(w));
					}

				}

			}
		}while(u!=endNode);
	}



	public Node findMin(Node v,HashMap distance,Vector found) {

		int min = Integer.MAX_VALUE;
		int position = 0;
		for(int i = 0; i<AllNodes.size(); i++) {
			if(!(found.contains((Node)AllNodes.get(i))) && distance.containsKey(AllNodes.get(i))){
				if((Integer)distance.get(AllNodes.get(i))<min) {

					position = i;
					min = (Integer) distance.get(AllNodes.get(i));
				  }
			}
		}

		return (Node)AllNodes.get(position);
	}


	public HashMap getAdjacentNodes(Node u) {

		HashMap<Node,Integer> adjacentNodeDist = new HashMap<Node,Integer>();
        Edge edge;
        Node node;

	    for(int i = 0;i < u.getIncidentEdges().size(); i++) {

		  edge = (Edge)u.getIncidentEdges().get(i);
		  node = edge.getStartNode() == u ? edge.getEndNode() : edge.getStartNode();

		  adjacentNodeDist.put(node , edge.getWeight());
		}
		return adjacentNodeDist;
	}

	public Edge findMinEdge(Node u,HashMap distance,Vector found) {

		Node v;
		Edge edge = null,temp;
		int min = Integer.MAX_VALUE,dist;
		for(int i = 0 ; i<u.getIncidentEdges().size() ; i++) {

			temp=(Edge)u.getIncidentEdges().get(i);
			v = temp.getStartNode()==u ? temp.getEndNode() : temp.getStartNode();
			if(found.contains(v)) {
				dist=temp.getWeight() + (Integer)distance.get(v);
				if( dist < min ) {
					edge = temp;
					min = dist;
				}
			}
		}

		return edge;
	}


}