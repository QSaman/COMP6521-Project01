package com.mp1.buffer;

import com.mp1.schema.Student;
import com.mp1.sort.Tpmms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ListIterator;

public class InputBuffer {

    private boolean lastBatch = false;

    private ArrayList<Student> students;
    private int len, cur;
    private BufferedReader bufferedReader;
    private String line;
    private int blocks;

    public InputBuffer(String sublistFileName, int blocks) {
        try {
        	students = new ArrayList<>();
        	bufferedReader = new BufferedReader(new InputStreamReader(
        			new FileInputStream(sublistFileName), StandardCharsets.US_ASCII),
        			Tpmms.tuples * Tpmms.tupleSize);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the \"" + sublistFileName + "\" file!");
        }
        this.blocks = blocks;
        reload(blocks);
    }
    
    public void clear()
    {
    	students.clear();
    	len = 0;
    	cur = 0;
    }

    public boolean isEmpty() {
        return cur >= len;
    }

    public boolean isLastBatch() {
        return lastBatch;
    }

    public Student peekNextStudent() {
        return students.get(cur + 1);
    }

    public Student getNextStudent() {
        return students.get(++cur);
    }

    public void reload(int blocks) {
    	cur = 0;
        for (int i = 0; i < blocks; i++) {
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    bufferedReader.close();
                    lastBatch = true;
                    return;
                }
                if (len < students.size())
                	students.get(len++).parseLine(line);
                else
                	students.add(new Student(line));
            } catch (IOException e) {
                System.out.println("Cannot read the input file!");
            }
        }
    }

	/**
	 * @return the blocks
	 */
	public int getBlocks() {
		return blocks;
	}

	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}
    
}
