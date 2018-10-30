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

		final Node root=buildTree(listaOrdenada);

		// tree=new TreeUtil(root)
		// Node root = buildTree();
	}

	public static void descomprimir(String filename)
	{
		// PROGRAMAR AQUI...
		System.out.println("descomprimir");
	}

	private static Node buildTree(SortedList<Node> listaOrdenada)
	{
		Comparator<Node> cmp=new CmpInteger();
		// for(int i=0; i<listaOrdenada.size(); i++){
		// int i=0;
		while(listaOrdenada.size()>1)
		{
			Node der=listaOrdenada.get(0);
			listaOrdenada.remove(0);
			Node izq=listaOrdenada.get(0);
			listaOrdenada.remove(0);
			Node parent=new Node('\0',izq.getN()+der.getN(),izq,der);
			listaOrdenada.add(parent,cmp);
		}
		System.out.println(listaOrdenada.getFirst().getC()+" - "+(char)listaOrdenada.getFirst().getC()+" - "+listaOrdenada.getFirst().getN());
		System.out.println(listaOrdenada.getFirst().getDer().getC()+" - "+(char)listaOrdenada.getFirst().getDer().getC()+" - "+listaOrdenada.getFirst().getDer().getN());
		System.out.println(listaOrdenada.getFirst().getIzq().getC()+" - "+(char)listaOrdenada.getFirst().getIzq().getC()+" - "+listaOrdenada.getFirst().getIzq().getN());
		// }

		return null;
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
