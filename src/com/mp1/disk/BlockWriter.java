/**
 * 
 */
package com.mp1.disk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.mp1.MemoryBuffer;

/**
 * @author saman
 *
 */
public class BlockWriter {
	FileOutputStream fos;

	/**
	 * 
	 */
	public BlockWriter() {
		fos = null;
	}
	
	public void open(String file_name) throws FileNotFoundException
	{
		fos = new FileOutputStream(new File(file_name));
	}
	
	public void close() throws IOException
	{
		if (fos != null)
			fos.close();
	}
	
	public void write(MemoryBuffer buffer) throws IOException
	{
		boolean first = true;
		for (int i = 0; i < buffer.size(); ++i)
		{
			if (first)
				first = false;
			else
				fos.write(System.getProperty("line.separator").getBytes());
			fos.write(buffer.get(i).getBytes());			
		}
	}

}
