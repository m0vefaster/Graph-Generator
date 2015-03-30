/**
 * @(#)UserLog.java
 *
 *
 * @author
 * @version 1.00 2009/6/19
 */


import java.util.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;


public class UserLog implements java.io.Serializable
  {

  	public JPanel panel1=new JPanel();
	public JTree tree;

    DefaultMutableTreeNode log[]=new DefaultMutableTreeNode[1000];
    DefaultMutableTreeNode subEntry[][]=new DefaultMutableTreeNode[1000][10];
    DefaultMutableTreeNode userLog ;
    DefaultMutableTreeNode elements;
    Vector allObjects=new Vector();
    Graph graph;

    public int numSubEntries[]=new int[1000];
    public int numLogs;
    public int i,k;
    Stack<DefaultMutableTreeNode> stack=new  Stack<DefaultMutableTreeNode>();
    Vector objects=new Vector();
    String deletedEntry;
    Vector deletedSubEntry=new Vector();
    boolean redoPossible;
    boolean addToUserEntry;
    public int index;

    public UserLog()
    {
        userLog = new DefaultMutableTreeNode("User Log");

    	for(i=0;i<1000;i++)
    	{

    		numSubEntries[i]=0;
    	}

    	tree=new JTree(userLog);
        panel1.setLayout(new BorderLayout());
	    panel1.add(tree,BorderLayout.EAST);
    	redoPossible=false;
    	addToUserEntry=true;
    }

	 public void addToUserLog(String newLogEntry)
   {
   	if(addToUserEntry)
   {

   	  log[numLogs]=new DefaultMutableTreeNode(newLogEntry);
   	  numLogs+=1;
   	  userLog.add(log[numLogs-1]);
   	 // userLog.add(new DefaultMutableTreeNode("helllo" ));
   	  //System.out.println("Child count:"+userLog.getChildCount());
   	   for(i=0;i<userLog.getChildCount();i++)
	  {
	  //	System.out.println("\nHerrerererer");
	  	 //System.out.println("Here---"+((DefaultMutableTreeNode)(userLog.getChildAt(i))).toString());
	  }
   	  panel1.remove(tree);
	  tree=new JTree(userLog);
	  panel1.setLayout(new BorderLayout());
	  panel1.add(tree,BorderLayout.EAST);
      panel1.revalidate();
   }
   }

   public void addToUserLog(String newLogEntry,Object object)
   {

   System.out.println("addToUserEntry is-----"+addToUserEntry);
   	if(addToUserEntry)
   {
   	System.out.println("size----------"+allObjects.size());

   	  log[numLogs]=new DefaultMutableTreeNode(newLogEntry);
   	  numLogs+=1;
   	  userLog.add(log[numLogs-1]);
   	  System.out.println("Child count:"+userLog.getChildCount());
   }
    allObjects.addElement(object);
   	  System.out.println("\nsize of all objects-------------------------"+allObjects.size());
   	  System.out.println("printing size____________-------------"+allObjects.size());
   	  for(i=0;i<userLog.getChildCount();i++)
	  {
	  	System.out.println("\nHerrerererer");
	    System.out.println("Here---"+((DefaultMutableTreeNode)(userLog.getChildAt(i))).toString());
	  }
   	  panel1.remove(tree);
	  tree=new JTree(userLog);
	  panel1.setLayout(new BorderLayout());
	  panel1.add(tree,BorderLayout.EAST);
	  panel1.revalidate();
	  System.out.println("its true___----------------------"+allObjects.size());
	  //System.out.println("   "+userLog.toString());


   }

   public void addToUserLogSubEntry(String newLogEntry)
   {
   	 if(addToUserEntry)
   {
   	  subEntry[numLogs-1][numSubEntries[numLogs-1]]=new DefaultMutableTreeNode(newLogEntry);
   	  log[numLogs-1].add(subEntry[numLogs-1][numSubEntries[numLogs-1]]);
   	  numSubEntries[numLogs-1]+=1;
   	  panel1.remove(tree);
	  tree=new JTree(userLog);
	  panel1.setLayout(new BorderLayout());
	  panel1.add(tree,BorderLayout.EAST);
	  panel1.revalidate();
   }
   }
    public void addToUserLogSubEntry(String newLogEntry,Object object)
   {
   	if(addToUserEntry)
   {
   		System.out.println("size----------"+allObjects.size());

   	  subEntry[numLogs-1][numSubEntries[numLogs-1]]=new DefaultMutableTreeNode(newLogEntry);
   	  log[numLogs-1].add(subEntry[numLogs-1][numSubEntries[numLogs-1]]);
   	  numSubEntries[numLogs-1]+=1;
   }
    allObjects.addElement(object);
   	  System.out.println("\nsize of all objects-------------------------"+allObjects.size());
   	  panel1.remove(tree);
	  tree=new JTree(userLog);
	  panel1.setLayout(new BorderLayout());
	  panel1.add(tree,BorderLayout.EAST);
   	  panel1.revalidate();
   }
   public JPanel getPanel()
	{
		return panel1;
	}

	public void setGraph(Graph newGraph)
	{
		graph=newGraph;
	}
   public void setUndo()
   {
      if(numLogs>0)
      {
       stack.push(log[numLogs-1]);
       userLog.remove(log[numLogs-1]);
   	   redoPossible=true;
   	   numLogs-=1;
   	   backwardInGraph();
      }
      else
      {
      	redoPossible=false;
      }
      System.out.println("size undo,,,,,----------"+allObjects.size());
   }

   public void setRedo()
   {
   	System.out.println("size----------"+allObjects.size()+  "             "+addToUserEntry);
   	  if(redoPossible && !stack.empty())
   	  {
          log[numLogs]=((DefaultMutableTreeNode )(stack.pop()));
          userLog.add(log[numLogs]);
          deletedEntry=((DefaultMutableTreeNode)log[numLogs]).toString();
          numLogs+=1;
          for(i=0;i<log[numLogs-1].getChildCount();i++)
          {
          	deletedSubEntry.addElement(((DefaultMutableTreeNode)subEntry[numLogs-1][i]).toString());
          }
          	System.out.println("size----------"+allObjects.size());
          redeemInGraph();

   	  }

   }

   public void backwardInGraph()
   {
   	String string;
   	DefaultMutableTreeNode tempTreeNode;
   	DefaultMutableTreeNode tempSubTreeNode;
   	tempTreeNode=((DefaultMutableTreeNode )(stack.pop()));

   	string=tempTreeNode.toString();
   	System.out.println("String is:"+string);

   	changeBack(string);

   	for(i=0;i<tempTreeNode.getChildCount();i++)
   	{
   		tempSubTreeNode=((DefaultMutableTreeNode)(tempTreeNode.getChildAt(i)));
   	//	System.out.println("delteing edge with index:"+(Integer.valueOf(string.substring(index+1) )).intValue());

   		string=tempSubTreeNode.toString();
   		System.out.println("String is:"+string);

   		changeBack(string);
   	}

   	stack.push(tempTreeNode);
   }



   public void redeemInGraph()
   {
   	System.out.println("size----------"+allObjects.size());
   	changeForward((String)deletedEntry);
   	graph.printStatus();
   	System.out.println("deleted entry is:"+deletedEntry);
   	System.out.println("Entry is:"+deletedEntry);
   	for(i=0;i<deletedSubEntry.size();i++)
   	{
   		System.out.println("Sub entry is:"+(String)deletedSubEntry.elementAt(i));
   		changeForward((String)deletedSubEntry.elementAt(i));
   	}
   	 deletedSubEntry.removeAllElements();
   }



   public void changeBack(String string)
   {
   	addToUserEntry=false;
   	if(string.startsWith("Created a node with index"))
   	{
   	     index=string.indexOf(":");

   	     graph.deleteNode((Integer.valueOf(string.substring(index+1) )));//.intValue());//String.valueOf(string.substring(index)));
   	}

   	if(string.startsWith("Created a edge with index"))
   	{
   		index=string.indexOf(":");
   	    System.out.println("delteing edge with index:"+(Integer.valueOf(string.substring(index+1) )).intValue());
   	    graph.deleteEdge((Integer.valueOf(string.substring(index+1) )));//.intValue());//String.valueOf(string.substring(index)));
   	}

   	if(string.startsWith("Deleted a node with index"))
   	{
   		i=allObjects.size();
   		i=i-1;
   		 while((allObjects.elementAt(i)) instanceof  Edge)
   		  {
   		  	i=i-1;
   		  }

   		graph.createNode((Node)(allObjects.elementAt(i)));
   		//}
   	/*	else if((allObjects.lastElement()) instanceof Edge)
   		{
   		 graph.createEdge((Edge)(allObjects.lastElement()));
   		}*/
   		allObjects.removeElementAt(i);//allObjects.elementAt(i));
   	}

   	if(string.startsWith("Deleted a edge with index"))
   	{
   		graph.createEdge((Edge)(allObjects.lastElement()));
   		allObjects.removeElementAt(allObjects.size()-1);
   	}

   	if(string.startsWith("Deleted a incident edge with index"))
   	{
   	/*	index=string.indexOf(":");
   		int indexNode=string.lastIndexOf(":");
   		System.out.println("Indexs are"+(Integer.valueOf(string.charAt(indexNode+1)))+"   "+(Integer.valueOf(string.charAt(index+1))).intValue());

        graph.createIncidentEdge(((Edge)(allObjects.lastElement())),string.charAt(indexNode+1));//(Integer.valueOf(string.charAt(indexNode+1))));//.intValue(),(Integer.valueOf(string.charAt(index+1))).intValue());//String.valueOf(string.charAt(index+1)));
   	*/
   graph.createEdge((Edge)(allObjects.lastElement()));
   allObjects.removeElementAt(allObjects.size()-1);
   	}

   		addToUserEntry=true;
   }




    public void changeForward(String string)
   {
   	addToUserEntry=true;
   	//System.out.println("size----------"+allObjects.size());
   	System.out.println("The string is.............................."+string);
   	if(string.startsWith("Created a node with index"))
   	{
   			k=allObjects.size();
   		    k=k-1;
   		 while((allObjects.elementAt(k)) instanceof  Edge)
   		  {
   		  	k=k-1;
   		  }
   		graph.createNode((Node)(allObjects.elementAt(k)));
   		allObjects.removeElementAt(k);
   	}

   	if(string.startsWith("Created a edge with index"))
   	{
   	   System.out.println("size----------"+allObjects.size());
   	  // graph.createEdge((Edge)(allObjects.elementAt(allObjects.size()-1)));
   	   //allObjects.removeElementAt(allObjects.size()-1);
   	   	graph.createEdge((Edge)(allObjects.lastElement()));
   		allObjects.removeElementAt(allObjects.size()-1);
   	}

   	if(string.startsWith("Deleted a node with index"))
   	{
   		index=string.indexOf(":");

   	    graph.deleteNode((Integer.valueOf(string.substring(index+1) )));//.intValue());//Integer.valueOf());
   	}

   	if(string.startsWith("Deleted a edge with index"))
   	{
   		index=string.indexOf(":");

   	    graph.deleteEdge((Integer.valueOf(string.substring(index+1) )));//.intValue());//String.valueOf(string.substring(index)));
   	}

   /*	if(string.startsWith("Deleted a incident edge with index"))
   	{
   		/*
   		int indexNode=string.lastIndexOf(":");
   		//System.out.println("Delted incident edge index of....."+index);
   		index=Integer.valueOf(String.valueOf(string.charAt(index+1))).intValue();//have prob
   		indexNode=Integer.valueOf(String.valueOf(string.charAt(indexNode+1))).intValue();//have prob
   		System.out.println("Delted incident edge index of....."+index+"    "+indexNode);
       // graph.deleteIncidentEdge(index,indexNode);

        //graph.deleteEdge(in)
        index=string.indexOf(":");
        graph.deleteEdge(index);
   	} */

   }




}