package jhuffman;

import java.util.Comparator;

import jhuffman.ds.Node;
import jhuffman.util.SortedList;

public class SortList
{
	public static SortedList<Node> buildList(int[] tablaCantidades)
	{
		SortedList<Node> lst=new SortedList<>();
		Comparator<Node> cmp=new CmpInteger();
		for(int i=0; i<tablaCantidades.length; i++)
		{
			if(tablaCantidades[i]>0)
			{
				lst.add(new Node(i,tablaCantidades[i],null,null),cmp);
			}
		}
		

		return lst;
	}
	
	static class CmpInteger implements Comparator<Node>
	{
		@Override
		public int compare(Node a, Node b)
		{
			return (int)(a.getN()-b.getN());
		}

	}
}
