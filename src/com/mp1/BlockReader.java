package com.mp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author saman
 *
 */

public class BlockReader {

	private BufferedReader br;

	/**
	 * @throws FileNotFoundException
	 * 
	 */
	public BlockReader() {
		br = null;
	}

	public void open(String file_name) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(file_name));
	}

	public void close() throws IOException {
		br.close();
	}

	List<Student> nextBlock() throws IOException {
		ArrayList<Student> ret = new ArrayList<>();
		String line;
		int tuples = 0;
		while (tuples < 40 && (line = br.readLine()) != null) {
			++tuples;
			Student student = new Student();
			student.parseLine(line);
			ret.add(student);
		}
		return ret;
	}
}
