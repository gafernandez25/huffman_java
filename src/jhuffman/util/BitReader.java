package jhuffman.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BitReader
{
	private RandomAccessFile raf = null;
	
	private String readBuffer="";
	private Integer readCounter=8;
	
	public BitReader(String filename)
	{
		// programar aqui	
		try
		{
			this.raf = new RandomAccessFile(filename,"r");	
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public int readBit()
	{
		byte buffer=0;
		
		if(readCounter==8)
		{
			buffer = this.readByte();
			readBuffer = String.format("%8s", Integer.toBinaryString(buffer & 0xFF)).replace(' ', '0');
			//System.out.println(readBuffer);
			readCounter = 0;
		}
		
		char c = readBuffer.charAt(readCounter);
		readCounter++;
		return Integer.parseInt(""+c);
	}
	
	public byte readByte()
	{
		byte b=0;
		
		try
		{
			b = this.raf.readByte();	
		}
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public long readLong()
	{
		long l=0;
		
		try
		{
			l = this.raf.readLong();	
		}
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}
	
	public boolean eof()
	{
		// programar aqui
		return false;
	}
		
	public void close()
	{
		// programar aqui	
		try
		{
			this.raf.close();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
