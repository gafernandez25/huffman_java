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
		// programar aqui
		return 0;
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
