import java.util.*;

public class Connectivity
{
	Vector nodes;
	int i;
	
	Connectivity(Panel panel)
	{
		nodes=(panel.getGraph()).getAllNodes();
	}
	
	public boolean getIsConnected()
	{
		for(i=0;i<nodes.size();i++)
		{
			if(((Node)nodes.elementAt(i)).getNumIncidentEdges()==0)
				return false;
		}
		return true;
	}
}