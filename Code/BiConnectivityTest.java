/**
 * @(#)BiConnectivityTest.java
 *
 *
 * @author
 * @version 1.00 2010/10/23
 */
import java.util.Vector;
import java.util.HashMap;
import java.awt.Color;


public class BiConnectivityTest {
Vector articulatePoints;
Vector allNodes;
Vector visited;
HashMap low,num,parent;
int counter;

    public BiConnectivityTest(Vector allNodes) {
    articulatePoints = new Vector();
    this.allNodes = allNodes;
    visited = new Vector();
    low = new HashMap();
    num = new HashMap();
    parent = new HashMap();
	counter = 1;
	findArticulate((Node)allNodes.get(counter-1));
    }


   public void findArticulate(Node v) {

	Node w;
   	visited.add(v);
   	low.put(v,counter);
   	num.put(v,counter);
   	counter++;
   	for(int i=0;i<v.getIncidentEdges().size();i++) {

        Edge edge=(Edge)v.getIncidentEdges().get(i);
        w=(edge.getStartNode()).equals(v) ? (edge.getEndNode()) : v;

        if(!visited.contains(w)) {
        	parent.put(w,v);
        	System.out.println("parent of " + w.getx()+" is "+ ((Node)parent.get(w)).getx());
        	findArticulate(w);
			System.out.println("w " + w.getx()+" :: "+ low.get(w)+"  v "+ v.getx()+" :: "+ num.get(v));
			if((Integer)low.get(w)>=(Integer)num.get(v)) {

				System.out.println("\n"+v.getx()+" is an articulation point");
				if (!articulatePoints.contains(v)) {

						articulatePoints.add(v);
						v.setColor(Color.RED);
				}
			}
			System.out.println("w " + w.getx()+" :: "+ low.get(w)+"  v "+ v.getx()+" :: "+ num.get(v));
		    low.put(v,min((Integer)low.get(v),(Integer)low.get(w)));

    	    }
           else
           	if(parent.get(v)==null || parent.get(v)!=w)//back-off edge
        	    	low.put(v,min((Integer)low.get(v),(Integer)num.get(w)));

   		}



   }


   public int min(int a,int b) {

      return(a < b ? a : b);

	}





}