/**
 * 
 */
package com.mp1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.mp1.disk.BlockReader;
import com.mp1.disk.BlockWriter;

/**
 * @author saman
 *
 */
public class Tpmms {
	
	private ArrayList<MemoryBuffer> buffers;
	private BlockReader inputReader;

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
	 * @return true, if we can sort the relation in at most two phases; otherwise return false
	 * @throws IOException
	 */
	public boolean phase1() throws IOException
	{
		boolean two_phase = true;
		while (true)
		{
			if (Runtime.getRuntime().freeMemory() <= 4096)
			{
				two_phase = false;
				break;
			}			
			MemoryBuffer buffer = new MemoryBuffer();
			inputReader.nextBlock(buffer);
			if (buffer.size() == 0)
				break;
			buffer.sort();
			buffers.add(buffer);
		}
		return two_phase;
	}
	
	private boolean mergeRequired()
	{
		return buffers.size() != 1;
	}
	
	public void save()
	{
		int i = 1;
		for (MemoryBuffer buffer : buffers)
			if (!buffer.isDone())
			{
				BlockWriter bw = new BlockWriter();
				try {
					bw.open(String.format("sublist%04d.out", i++), true);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public void merge()
	{
		
	}
	
	public void findDuplicates() throws IOException
	{
		if (phase1())
		{
			if (mergeRequired())	//We can find duplicates in exactly 2 phases
			{				
				save();
				merge();
			}
			else	//We can find duplicates only in 1 phase
			{
				merge();
			}
		}
		else
		{
			//TODO we can find duplicates in more than 2 phases
		}
	}

}
