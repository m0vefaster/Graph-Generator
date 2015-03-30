import java.util.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;


public class UserToolBar implements java.io.Serializable {

	public JPanel panel1=new JPanel();
	public JTree tree;
	public String msg;
    public int i,j;
    //MainLayout mainLayout=new MainLayout();
    Vector allGraphs=new Vector();
    Vector allIds=new Vector();
    Vector vectorEdges[]=new Vector[1000];
    Vector vectorNodes[]=new Vector[1000];

    DefaultMutableTreeNode elements;
    DefaultMutableTreeNode graph[]=new DefaultMutableTreeNode[10];
    DefaultMutableTreeNode justNodes[]=new DefaultMutableTreeNode[10];
    DefaultMutableTreeNode justEdges[]=new DefaultMutableTreeNode[10];
    DefaultMutableTreeNode nodes[][]=new DefaultMutableTreeNode[10][1000];
    DefaultMutableTreeNode edges[][]=new DefaultMutableTreeNode[10][1000];

    public int numGraphs;
    public int numNodes[]=new int[1000];
    public int numEdges[]=new int[1000];
    public static DefaultTreeModel model;


    public UserToolBar()
    {

    	elements = new DefaultMutableTreeNode("Elements");
    	for(i=0;i<1000;i++)
    	{
    		numNodes[i]=0;
    		numEdges[i]=0;
    		vectorEdges[i]=new Vector();
    		vectorNodes[i]=new Vector();
    	}

    	 tree=new JTree(elements);
         panel1.setLayout(new BorderLayout());
		 panel1.add(tree,BorderLayout.WEST);

    	numGraphs=0;
    }

   public void addGraph(Graph newGraph,String newId)
   {
   	  graph[numGraphs]=new DefaultMutableTreeNode(newId);
   	  allGraphs.add(newGraph);
   	  elements.add(graph[numGraphs]);
   	  justNodes[numGraphs]= new DefaultMutableTreeNode("Nodes");
      justEdges[numGraphs] = new DefaultMutableTreeNode("Edges");
      graph[numGraphs].add(justNodes[numGraphs]);
      graph[numGraphs].add( justEdges[numGraphs]);
   	  numGraphs+=1;
  // 	 MainLayout.setChangeUserToolBar();
   	  panel1.remove(tree);
	     tree=new JTree(elements);
		// panel1.setLayout(new BorderLayout());
		 panel1.add(tree,BorderLayout.WEST);
 		  panel1.revalidate();
   }

   public void addElementToGraph(Graph newGraph,Object object)
   {

   	  for(i=0;i<allGraphs.size();i++)
   	  {
   	  	if(((Graph)(allGraphs.elementAt(i)))== newGraph)
   	  		break;
   	  }

   	  if(object instanceof Node)
   	  {

   	  	nodes[i][numNodes[i]]=new DefaultMutableTreeNode(((Node)object).getIndex());
   	  	justNodes[i].add(nodes[i][numNodes[i]]);
   	  	vectorNodes[i].addElement((Node)object);
   	  	System.out.println("Addddddded-------"+justNodes[i].getChildCount());
   	  	numNodes[i]+=1;
   	  }

   	   if(object instanceof Edge)
   	  {
   	  	Edge edge=(Edge)object;
   	  	edges[i][numEdges[i]]=new DefaultMutableTreeNode(((Edge)object).getIndex());
   	  	justEdges[i].add(edges[i][numEdges[i]]);
   	  	vectorEdges[i].addElement(edge);
   	  	numEdges[i]+=1;
   	  	System.out.println("Addddddded-------"+justNodes[i].getChildCount());
   	  }
   	 // 	MainLayout.setChangeUserToolBar( );
 	     panel1.remove(tree);
	     tree=new JTree(elements);
	//	 panel1.setLayout(new BorderLayout());
		 panel1.add(tree,BorderLayout.WEST);
   	panel1.revalidate();
	}

   public  DefaultMutableTreeNode getUserToolBar()
   {
   	  return elements;
   }

   public JTree getTree()
	{
		return tree;
		}

	public JPanel getPanel()
	{
		return panel1;
	}

	public void removeEdge(Graph newGraph,Edge newEdge)
	{
		System.out.println("removeedege1");
		 for(i=0;i<allGraphs.size();i++)
   	      {
   	  	if(((Graph)(allGraphs.elementAt(i)))== newGraph)
   	  		break;
   	      }
   	  	System.out.println("removeedege2"+" "+i);
   	     for(j=0;j<numEdges[i];j++)
   	     {
   	     	System.out.println("num edges and j are----"+numEdges[i]+"      "   +j);
   	     	String stringIndex=(String.valueOf(((Edge)(vectorEdges[i].elementAt(j))).getIndex()));
   	     	String edgeIndex=(String.valueOf(((Edge)newEdge).getIndex()));
   	     	System.out.println("num edges and j are----"+numEdges[i]+"      "   +j+ "   "+stringIndex+"        "+edgeIndex);
   	     	if(stringIndex.equals(edgeIndex))
   	     	{

	        //  justEdges[i].remove(edges[i][j]);
	         // arrangeEdges.(i,j);
	          ((DefaultMutableTreeNode)(justEdges[i])).remove(j);
	          vectorEdges[i].removeElementAt(j);
	          //System.out.println("removeedege3"+" "+(String.valueOf(((Edge)(vectorEdges[i].elementAt(j))).getIndex()))+"   "+((Edge)newEdge).getIndex());
	          break;
   	     	}
   	    }
   	     numEdges[i]-=1;
		 panel1.remove(tree);
	     tree=new JTree(elements);
		 panel1.setLayout(new BorderLayout());
		 panel1.add(tree,BorderLayout.WEST);
	}

		public void removeNode(Graph newGraph,Node newNode)
	{
		System.out.println("removeedege1");
		 for(i=0;i<allGraphs.size();i++)
   	      {
   	  	if(((Graph)(allGraphs.elementAt(i)))== newGraph)
   	  		break;
   	      }
   	  	System.out.println("removeedege2"+" "+i+"       --"+numNodes[i]+"    "+justNodes[i].getChildCount());
   	     for(j=0;j<numNodes[i];j++)
   	     {
   	     	String stringIndex=((DefaultMutableTreeNode)(justNodes[i].getChildAt(j))).toString();
   	     	int index=stringIndex.indexOf(":");
   	     	stringIndex=String.valueOf(stringIndex.charAt(index+1));
   	     	//=justNodes[i].getChildAt(String.valueOf(((Node)(vectorNodes[i].elementAt(j))).getIndex()));
   	     	String nodeIndex=(String.valueOf(((Node)newNode).getIndex()));
   	     	if(stringIndex.equals(nodeIndex))
   	     	{    System.out.println("Size------"+i+"   "+j+"    ");//+nodes[i].getChildCount());
   	     //	System.out.println("removeedege2"+" "+i+"       --"+numNodes[i]+"    "+justNodes[i].getChildCount());

	       //  justNodes[i].remove(nodes[i][j]);
	       ((DefaultMutableTreeNode)(justNodes[i])).remove(j);
	          //System.out.println("removeedege3"+" "+(String.valueOf(((Edge)(vectorEdges[i].elementAt(j))).getIndex()))+"   "+((Edge)newEdge).getIndex());
	         break;
   	     	}
   	    }
   	    numNodes[i]-=1;
		 panel1.remove(tree);
	     tree=new JTree(elements);
		 panel1.setLayout(new BorderLayout());
		 panel1.add(tree,BorderLayout.WEST);
	}

		public void removeGraph(Graph newGraph)
	{

		 for(i=0;i<allGraphs.size();i++)
   	      {
   	  	if(((Graph)(allGraphs.elementAt(i)))== newGraph)
   	  	{  elements.remove(graph[i]);
   	  	   allGraphs.removeElementAt(i);
   	  			break;
   	      }
   	      }


   }
}