package com.mp1;

import java.io.IOException;
import java.util.List;

import com.mp1.disk.BlockReader;

public class MP1 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BlockReader br = new BlockReader();		
		br.open("input.txt");
		MemoryBuffer buffer = new MemoryBuffer();
		br.nextBlock(buffer);
		System.out.println(buffer);
		// TEST
		if (buffer.get(0).equals(buffer.get(1))) {
			System.out.println(".equals works properly!");
		}
	}
}
