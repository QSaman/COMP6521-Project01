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

	private final int numberOfBuffers = 1000;
	private int totalBuffers = 0;

	/**
	 * @throws FileNotFoundException
	 * 
	 */
	public Tpmms(String input_file_name) throws FileNotFoundException {
		buffers = new ArrayList<>();
		inputReader = new BlockReader();
		inputReader.open(input_file_name);
	}

	/**
	 * 
	 * @return true, if we can sort the relation in at most two phases;
	 *         otherwise return false
	 * @throws IOException
	 */
	public boolean phase1() throws IOException {
		boolean two_phase = true;
		boolean done = false;
		while (!done) {
			while (buffers.size() <= numberOfBuffers) {
				if (Runtime.getRuntime().freeMemory() <= 4096) {
					two_phase = false;
					break;
				}
				MemoryBuffer buffer = new MemoryBuffer();
				inputReader.nextBlock(buffer);
				if (buffer.size() == 0) {
					done = true;
					break;
				}
				buffer.sort();
				buffers.add(buffer);
				totalBuffers++;
			}
			save();
		}
		return two_phase;
	}

	private boolean mergeRequired() {
		return buffers.size() != 1;
	}

	public void save() {
		int i = 1;
		for (MemoryBuffer buffer : buffers)
			if (!buffer.isDone()) {
				BlockWriter bw = new BlockWriter();
				try {
					bw.open(String.format("sublist%05d.out", i++), true);
					bw.write(buffer);
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		buffers.clear();
	}

	public void merge() {
		BlockReader br = new BlockReader();
		BlockWriter bw = new BlockWriter();
		ArrayList<Buffer> inputBuffers = new ArrayList<>(totalBuffers);
		short minIndex = -1;
		Student minStudent = new Student();
		for (short i = 0; i < totalBuffers; i++) {
			try {
				br.open(String.format("sublist%05d.out", i));
				try {
					br.nextInputBuffer(inputBuffers.get(i));
					if(minIndex == -1) {
						minStudent = inputBuffers.get(i).getCurrentStudent();
						minIndex = i;
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		Buffer outputBuffer = new Buffer();
		bw.open("output");
		do {
			outputBuffer.add(minStudent);
			minStudent = inputBuffers.get(0).getCurrentStudent();
			minIndex = 0;
			for(short i = 1; i < totalBuffers; i++) {
				if(inputBuffers.get(i).getCurrentStudent().isLessThan(minStudent)) {
					minStudent = inputBuffers.get(i).getCurrentStudent();
					minIndex = i;
				}
			}
			if(outputBuffer.isFull()) {
				
			}
		} while(true);
	}

	public void findDuplicates() throws IOException {
		if (phase1()) {
			if (mergeRequired()) // We can find duplicates in exactly 2 phases
			{
				save();
				merge();
			} else // We can find duplicates only in 1 phase
			{
				merge();
			}
		} else {
			// TODO we can find duplicates in more than 2 phases
		}
	}

}
