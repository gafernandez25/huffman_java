package jhuffman.util;

import java.util.Map;
import java.util.Stack;

import jhuffman.ds.Node;

public class TreeUtil
{
	private Stack<Node> pila=null;
	private Stack<String> pilaCod=null;

	public TreeUtil(Node root)
	{
		pila=new Stack<Node>();
		pilaCod=new Stack<String>();
		
		pila.push(root);
		pilaCod.push("");
	}
	
	public static void writeTree(Map<Character,String> listaCodigos,BitWriter writerFile){
		listaCodigos.forEach((caracter, codigo) -> {
			int cantBitsCodigo=codigo.length();
			System.out.println(caracter.toString()+" - caracter");
			System.out.println(String.valueOf(cantBitsCodigo)+" - long codigo");
			System.out.println(codigo+" - código");
			writerFile.writeBits(caracter);
			writerFile.writeBits(cantBitsCodigo);
			int cantBytesCodigo=BitWriter.roundUp(cantBitsCodigo,8);

			int bitsFaltantes=(cantBytesCodigo*8)-cantBitsCodigo;
			String codCompleto=codigo;
			// completo el código del string con 0 a la izquierda hasta un
			// múltiplo de 8
			for(int i=0; i<bitsFaltantes; i++)
			{
				codCompleto="0".concat(codCompleto);
			}
			System.out.println(codCompleto+" - código completo");

			for(int i=0; i<cantBytesCodigo; i++)
			{
				writerFile.writeBits(Integer.parseInt(codCompleto.substring(i,i+8)));
			}
			System.out.println("--");
		});
	}
	
	public Node next(StringBuffer cod)
	{
		boolean hoja=false;
		Node p=null;
		Node aux=null;
		String zz=null;
		
		while( pila.size()>0 && !hoja )
		{
			p=pila.pop();
			zz=pilaCod.pop();
		
			if( p.getDer()!=null )
			{
				pila.push(p.getDer());
				aux=new Node();
				aux.setN(1);
				pilaCod.push(zz+"1");
			}

			if( p.getIzq()!=null )
			{
				pila.push(p.getIzq());
				aux=new Node();
				aux.setN(0);
				pilaCod.push(zz+"0");
			}
						
			if( p.getIzq()==null && p.getDer()==null )
			{
				hoja=true;
				cod.delete(0, cod.length());
				cod.append(zz);
			}
			else
			{
				p=null;
			}
		}
		
		return p;
	}
}
