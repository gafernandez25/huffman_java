package jhuffman;

import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.Stack;

import jhuffman.util.SortedList;
import jhuffman.SortList.CmpInteger;
import jhuffman.ds.*;
import jhuffman.util.*;

public class Huffman
{
	private static final int longAlfabeto=256;
	private static int[] tablaCantidades=new int[longAlfabeto];
	private static TreeUtil tree;

	public static void main(String[] args)
	{
		String filename=args[0];
		if(filename.endsWith(".huf"))
		{
			descomprimir(filename);
		}
		else
		{
			comprimir(filename);
		}
	}

	public static void comprimir(String filename)
	{
		// PROGRAMAR AQUI...
		System.out.println("comprimimiendo "+filename);
		System.out.println("-------------------------");
		getCantidades(filename);

		SortedList<Node> listaOrdenada=SortList.buildList(tablaCantidades);
		System.out.println("Lista de nodos ordenada");
		for(int i=0; i<listaOrdenada.size(); i++)
		{
			System.out.println(listaOrdenada.get(i).getC()+" - "+(char)listaOrdenada.get(i).getC()+" - "+listaOrdenada.get(i).getN());
		}
		System.out.println("-------------------------");

		final Node root=buildTree();

		// tree=new TreeUtil(root)
		// Node root = buildTree();
	}

	

	

	public static void descomprimir(String filename)
	{
		// PROGRAMAR AQUI...
		System.out.println("descomprimir");
	}

	private static Node buildTree()
	{
		// inicializa cola de nodos
		Stack<Node> colaNodos=new Stack<Node>();
		for(char i=0; i<longAlfabeto; i++)
			if(tablaCantidades[i]>0) colaNodos.push(new Node(i,tablaCantidades[i],null,null));

		// caso especial si hay un solo caracter repetido N veces
		if(colaNodos.size()==1)
		{
			if(tablaCantidades['\0']==0) colaNodos.push(new Node('\0',0,null,null));
			else colaNodos.push(new Node('\1',0,null,null));
		}

		// junta dos nodos y mete al padre de ellos en la pila
		while(colaNodos.size()>1)
		{
			Node right=colaNodos.pop(); // 1er elemento
			Node left=colaNodos.pop(); // 2do elemento
			Node parent=new Node('\0',left.getN()+right.getN(),left,right);
			colaNodos.push(parent);
		}

		return colaNodos.pop();
	}

	/**
	 * lee 1 a 1 los caracteres del archivo e incrementa en 1 la posición de
	 * cada caracter en tablaCantidades tablaCantidades está indexada por el
	 * valor ascii de cada caracter
	 * 
	 * @param filename
	 */
	private static void getCantidades(String filename)
	{
		try
		{
			RandomAccessFile raf=new RandomAccessFile(filename,"r");

			int c=raf.read();
			while(c>=0)
			{
				System.out.print((char)c);
				tablaCantidades[c]++;
				c=raf.read();
			}

			raf.close();
			System.out.println();
			System.out.println("-------------------------");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
