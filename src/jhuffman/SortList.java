package jhuffman;

import java.util.Comparator;

import jhuffman.ds.Node;
import jhuffman.util.SortedList;

public class SortList
{
	public static SortedList<Node> buildList(int[] tablaApariciones)
	{
		SortedList<Node> lst=new SortedList<>();
		Comparator<Node> cmp=new CmpNode();
		for(int i=0; i<tablaApariciones.length; i++)
		{
			if(tablaApariciones[i]>0)
			{
				lst.add(new Node(i,tablaApariciones[i],null,null),cmp);
			}
		}
		

		return lst;
	}
	
	static class CmpNode implements Comparator<Node>
	{
		@Override
		public int compare(Node a, Node b)
		{
			return (int)(a.getN()-b.getN());
		}

	}
}
