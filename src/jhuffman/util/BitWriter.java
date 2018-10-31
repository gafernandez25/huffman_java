package jhuffman.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class BitWriter
{
	private RandomAccessFile raf=null;

	private FileOutputStream output;
	private String writeBuffer="";
	private Integer writeCounter=8;

	public BitWriter(String filename)
	{
		// programar aqui
		try
		{
			output=new FileOutputStream(filename);
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeBits(String byteToWrite)
	{
		try
		{
			Byte value_Byte=Byte.valueOf(byteToWrite);
			output.write(byteToWrite.getBytes());
//			output.close();
		}
		catch(Exception e)
		{
			System.err.println("Error: "+e.getMessage());
		}
	}

	public void writeBit(int bit)
	{
		// programar aqui
		if(this.writeCounter>0)
		{
			this.writeCounter--;
		}
		else
		{
			this.writeBits(this.writeBuffer);
			this.writeBuffer = "";
		}
		this.writeBuffer=this.writeBuffer.concat(String.valueOf(bit));

		// if(nodo.esHoja())
		// {
		// BinaryStdOut.write(true);
		// BinaryStdOut.write(nodo.getC(),8);
		// return;
		// }
		// BinaryStdOut.write(false);
		// writeTrie(nodo.getIzq());
		// writeTrie(nodo.getDer());

		//
		// int c=raf.read();
		// while(c>=0)
		// {
		// System.out.print((char)c);
		//// tablaApariciones[c]++;
		// c=raf.read();
		// }
		//
		// raf.close();
		// System.out.println();
		// System.out.println("-------------------------");
	}

	public void flush()
	{
		// programar aqui
	}

	public void close()
	{
		// programar aqui
	}
}
