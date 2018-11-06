package jhuffman.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

	public void writeBits(int byteToWrite)
	{
		try
		{
//			Byte value_Byte=Byte.valueOf(byteToWrite);
			//this.output.write(byteToWrite);
			this.output.write(byteToWrite);
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
			this.writeBuffer=this.writeBuffer.concat(String.valueOf(bit));
		}
		
		if(this.writeCounter==0)		
		{
			//System.out.println("Escribe el byte: "+writeBuffer.toString());
			this.writeBits(Integer.parseInt(this.writeBuffer,2));
			this.writeBuffer="";
			this.writeCounter=8;
		}
		
		//System.out.println("BitBuffer: "+writeBuffer.toString());
		

	}

	public void flush()
	{
		// programar aqui
	}

	public void close()
	{
		// programar aqui
		try
		{
			this.output.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * redondea una división de enteros para arriba
	 * @param num
	 * @param divisor
	 * @return
	 */
	public static int roundUp(int num, double divisor) {
	    //return (num + divisor - 1) / divisor;
		return (int)Math.ceil(num / divisor); 
	}
}
