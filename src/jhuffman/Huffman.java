package jhuffman;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import jhuffman.SortList.*;
import jhuffman.ds.*;
import jhuffman.util.*;

public class Huffman
{
	private static final int longAlfabeto=256;
	private static int[] tablaApariciones=new int[longAlfabeto];
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
		System.out.println("comprimiendo "+filename);
		System.out.println("-------------------------");
		getCantidades(filename);

		SortedList<Node> listaOrdenada=SortList.buildList(tablaApariciones);
		System.out.println("Lista de nodos ordenada");
		for(int i=0; i<listaOrdenada.size(); i++)
		{
			System.out.println(listaOrdenada.get(i).getC()+" - "+(char)listaOrdenada.get(i).getC()+" - "+listaOrdenada.get(i).getN());
		}
		System.out.println("-------------------------");

		Node root=buildTree(listaOrdenada);

		// armo la lista de códigos 
		final Map<Character,String> listaCodigos=new HashMap<>();
		buildCode(listaCodigos,root,"");
		
		System.out.println("Lista codificada");
		listaCodigos.forEach((k,v)->System.out.println("Key: " + k + ": Value: " + v));
		System.out.println("------------------------------------");
		
		
		// guarda el árbol
		
		
		// BitWriter bitWriter=new BitWriter(filename);
		// bitWriter.writeBit(filename,root);

		// veo los códigos en consola
		// for(int i=0;i<listaCodigos.length;i++){
		// if(tablaApariciones[i]>0)
		// System.out.println(i+ " - "+(char)i+" - "+listaCodigos[i]);
		// }

		// veo elementos del árbol en consola
		// System.out.println(root.getC()+" -
		// "+(char)listaOrdenada.getFirst().getC()+" -
		// "+listaOrdenada.getFirst().getN());
		// System.out.println(root.getDer().getC()+" -
		// "+(char)listaOrdenada.getFirst().getDer().getC()+" -
		// "+listaOrdenada.getFirst().getDer().getN());
		// System.out.println(root.getIzq().getIzq().getIzq().getC()+" -
		// "+(char)root.getIzq().getIzq().getIzq().getC()+" -
		// "+root.getIzq().getIzq().getIzq().getN());
		// System.out.println(root.getIzq().getDer().getC()+" -
		// "+(char)root.getIzq().getDer().getC()+" -
		// "+root.getIzq().getDer().getN());
		//

	}

	public static void descomprimir(String filename)
	{
		// PROGRAMAR AQUI...
		System.out.println("descomprimir");
	}

	/**
	 * genera una cadena de bits con el árbol
	 * 
	 * @param nodo
	 */
	private static void buildTreeBits(String treeBits,Node nodo)
	{
		if(nodo.esHoja())
		{
//			BitWriter.writeBit(nodo.getC());
//			treeBits+=
			return;
		}
		buildTreeBits(treeBits,nodo.getIzq());
		buildTreeBits(treeBits,nodo.getDer());
	}

	/**
	 * arma la tabla con los códigos huffman la tabla queda indexada por el
	 * ascii del código
	 * 
	 * @param sLista
	 *            String[]
	 * @param n
	 *            Node
	 * @param s
	 *            String
	 */
	private static void buildCode(Map<Character,String> listaCodigos, Node nodo, String codigo)
	{
		if(!nodo.esHoja())
		{
			buildCode(listaCodigos,nodo.getIzq(),codigo+'0');
			buildCode(listaCodigos,nodo.getDer(),codigo+'1');
		}
		else
		{
			listaCodigos.put((char)nodo.getC(),codigo);
		}
	}

	/**
	 * arma el árbol de huffman
	 * 
	 * @param listaOrdenada
	 *            SortedList<Node>
	 * @return root Node
	 */
	private static Node buildTree(SortedList<Node> listaOrdenada)
	{
		Comparator<Node> cmp=new CmpNode();
		while(listaOrdenada.size()>1)
		{
			Node der=listaOrdenada.get(0);
			listaOrdenada.remove(0);
			Node izq=listaOrdenada.get(0);
			listaOrdenada.remove(0);
			Node parent=new Node('\0',izq.getN()+der.getN(),izq,der);
			listaOrdenada.add(parent,cmp);
		}
		return listaOrdenada.getFirst();// nodo root
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
				tablaApariciones[c]++;
				c=raf.read();
			}

			raf.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("-------------------------");

	}
	// Stack<Integer> pila=new Stack<Integer>();
	// pila.push(1);
	// pila.push(3);
	// pila.push(6);
	// pila.push(33);
	// pila.push(12);
	// pila.forEach(item -> System.out.println(item.toString()));
	// Integer numero;
	// while(!pila.isEmpty())
	// {
	// numero=pila.pop();
	// System.out.println(numero.toString());
	// }
}
