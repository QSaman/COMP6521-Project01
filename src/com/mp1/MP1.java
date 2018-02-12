package com.mp1;

import java.io.IOException;
import java.util.List;

public class MP1 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BlockReader br = new BlockReader();		
		br.open("input.txt");
		List<Student> list = br.nextBlock();
		for (Student s : list)
			System.out.println(s);
		// TEST
		if (list.get(0).equals(list.get(1))) {
			System.out.println(".equals works properly!");
		}
	}
}
