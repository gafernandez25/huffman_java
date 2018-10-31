package jhuffman.util;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class BitWriter
{
	private RandomAccessFile raf=null;

	private OutputStream out;
    private int[] buffer = new int[8];
    private int count = 0;
	
	public BitWriter(String filename)
	{
		// programar aqui
		try
		{
			this.raf=new RandomAccessFile("cocorito.huf","w");
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeBit(int bit)
	{
		// programar aqui
		this.count++;
        this.buffer[8-this.count] = bit;
        if (this.count == 8){
            int num = 0;
            for (int index = 0; index < 8; index++){
                num = 2*num + this.buffer[index];
            }

            this.out.write(num - 128);

            this.count = 0;
        }
		
		
		
		
//		if(nodo.esHoja())
//		{
//			BinaryStdOut.write(true);
//			BinaryStdOut.write(nodo.getC(),8);
//			return;
//		}
//		BinaryStdOut.write(false);
//		writeTrie(nodo.getIzq());
//		writeTrie(nodo.getDer());
	

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
