package jhuffman;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	private static RandomAccessFile textFile,hufFile;
	private static String textString;

	public static void main(String[] args) throws IOException
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

	public static void comprimir(String filename) throws IOException
	{
		// PROGRAMAR AQUI...
		System.out.println("comprimiendo "+filename);
		System.out.println("-------------------------");
		textFile=new RandomAccessFile(filename,"r");
		textString="";
		//leo el archivo de texto
		leerArchivoTxt();
		System.out.println("texto: "+textString);
		System.out.println("------------------------------------");
		SortedList<Node> listaOrdenada=SortList.buildList(tablaApariciones);
		System.out.println("Lista de nodos ordenada");
		int cantHojas=listaOrdenada.size();
		System.out.println("cant de hojas: "+cantHojas);
		for(int i=0; i<listaOrdenada.size(); i++)
			System.out.println(listaOrdenada.get(i).getC()+" - "+(char)listaOrdenada.get(i).getC()+" - "+listaOrdenada.get(i).getN());
		System.out.println("-------------------------");
		
		Node root=buildTree(listaOrdenada);
		
		// armo la lista de códigos
		final Map<Character,String> listaCodigos=new HashMap<>();
		buildCode(listaCodigos,root,"");
		
		System.out.println("Lista codificada");
		listaCodigos.forEach((k, v) -> System.out.println("Key: "+k+": Value: "+v));
		System.out.println("------------------------------------");

		BitWriter writerFile=new BitWriter("cocorito.huf");

		//escribo la cantidad de caracteres diferentes
		writerFile.writeBits(cantHojas);
		
		// escribe el árbol (caracter - long del código del caracter - código
		// del caracter)
		TreeUtil.writeTree(listaCodigos,writerFile, tablaApariciones);
		

		// escribo la longitud del archivo original
		System.out.println("-------------------------");
		System.out.println("long del archivo original "+String.valueOf(textString.length()));
		writerFile.writeBits((int)root.getN());

		// escribo el texto del archivo codificado
		writeCodedText(listaCodigos,writerFile);
		

		writerFile.close();
		textFile.close();
	}

	public static void descomprimir(String filename) throws IOException
	{
		// PROGRAMAR AQUI...
		System.out.println("Descomprimir");
		System.out.println("-----------------------");
		hufFile=new RandomAccessFile(filename,"r");
		BitReader readerFile = new BitReader(filename);
//		//leer 1 byte: cantidad de caracteres diferentes
		int cantCaracteres = readerFile.readByte();
		System.out.println("Cant de caracteres distintos a leer: "+cantCaracteres);
		
		int c = 0;
		int longitudCod = 0;
		int concurrencia = 0;
		String codHuffman = "";
		
		//Leo la estructura del nodo para cada caracter a leer
		for(int i=0;i<cantCaracteres;i++)
		{
			c = readerFile.readByte();
			longitudCod = readerFile.readByte();
			concurrencia = readerFile.readByte();
			
			tablaApariciones[c]= concurrencia;	
			
			double cantBytes = Math.ceil(longitudCod)/8.0;
			for(int x=0; x<cantBytes; x++)
			{
				codHuffman += readerFile.readByte();
			}
			
			/* TODO:
			 * Acá habria que capturar el codigo huffman para guardarlo en una estructura auxiliar,
			 * pero con la tabla de apariciones ya se puede rearmar el arbol, me parece mas simple asi XD 
			 */
					
			//Limpia el cod
			codHuffman = "";			
	
		}
		
		//Lee la long de caracteres del archivo
		int caracteresTotales = readerFile.readByte();
		
		//TODO: Arma el arbol de huffman como la compresion (Habria que encapsularlo en otro metodo)		
		SortedList<Node> listaOrdenada=SortList.buildList(tablaApariciones);
		System.out.println("Lista de nodos ordenada");
		int cantHojas=listaOrdenada.size();
		System.out.println("cant de hojas: "+cantHojas);
		for(int i=0; i<listaOrdenada.size(); i++)
			System.out.println(listaOrdenada.get(i).getC()+" - "+(char)listaOrdenada.get(i).getC()+" - "+listaOrdenada.get(i).getN());
		System.out.println("-------------------------");
		
		Node root=buildTree(listaOrdenada);
		
		// armo la lista de códigos
		final Map<Character,String> listaCodigos=new HashMap<>();
		buildCode(listaCodigos,root,"");
		
		System.out.println("Lista codificada");
		listaCodigos.forEach((k, v) -> System.out.println("Key: "+k+": Value: "+v));
		System.out.println("------------------------------------");
		
		//TODO: Leer los bytes restantes para traducir los bits a los caracteres desde el arbol
		
	}
	
	public static void writeCodedText(Map<Character,String> listaCodigos,BitWriter writerFile){
		String[] arrayCaracteres=new String[textString.length()];
		arrayCaracteres=textString.split("(?!^)");	//array con cada caracter del texto original
		int cantBitsTextoCodificado=0;
		System.out.println("***************************");
		for(String caracter:arrayCaracteres)
		{
			String codigoAEnviar=listaCodigos.get(caracter.toCharArray()[0]);	//obtengo el código mapeado por caracter
			cantBitsTextoCodificado+=codigoAEnviar.length();	//cantidad de bits que voy mandando
			
			//System.out.println("Codigo a enviar: "+codigoAEnviar.toString());
			
			for(int i=0; i<codigoAEnviar.length(); i++)
			{
				//System.out.print(codigoAEnviar.substring(i,i+1));
				//writerFile.writeBit(Integer.parseInt(codigoAEnviar.substring(i,i+1)));
				
				//System.out.println("Caracter a enviar: "+codigoAEnviar.charAt(i));
				System.out.print(codigoAEnviar.charAt(i));
				
				String c = ""+codigoAEnviar.charAt(i);
				writerFile.writeBit(Integer.parseInt(c));
				
			}
		}
		
		//verifico si los bits mandados son múltiplos de 8 y completo con 0
		int cantBytesTextoCodificado=BitWriter.roundUp(cantBitsTextoCodificado,8);
		int bitsFaltantes=(cantBytesTextoCodificado*8)-cantBitsTextoCodificado;
		for(int i=0; i<bitsFaltantes; i++)
		{
			System.out.print("0");
			writerFile.writeBit(0);
		}
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
	private static void leerArchivoTxt()
	{
		try
		{

			int c=textFile.read();
			while(c>=0)
			{
				textString+=(char)c;
				tablaApariciones[c]++;
				c=textFile.read();
			}

			// textFile.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void leerArchivoHuf(){
		
	}
}
