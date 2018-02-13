/**
 * 
 */
package com.mp1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.mp1.disk.BlockReader;

/**
 * @author saman
 *
 */
public class Tpmms {
	
	ArrayList<MemoryBuffer> buffers;
	BlockReader inputReader;

	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public Tpmms(String input_file_name) throws FileNotFoundException 
	{
		buffers = new ArrayList<>();
		inputReader = new BlockReader();
		inputReader.open(input_file_name);
	}
	
	/**
	 * 
	 * @return true, if we can sort the relation in two phases; otherwise return false
	 * @throws IOException
	 */
	public boolean phase1() throws IOException
	{
		boolean two_phase = true;
		int buffer_cnt = 0;
		while (true)
		{
			if (Runtime.getRuntime().freeMemory() <= 400)
			{
				two_phase = false;
				break;
			}			
			MemoryBuffer buffer = new MemoryBuffer();
			inputReader.nextBlock(buffer);
			if (buffer.size() == 0)
				break;
			++buffer_cnt;
			buffers.add(buffer);
		}
		return two_phase;
	}

}
