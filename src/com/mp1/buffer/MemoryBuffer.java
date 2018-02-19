package com.mp1.buffer;

import com.mp1.schema.Student;
import com.mp1.sort.Tpmms;

import java.io.*;
import java.util.ArrayList;

/**
 * @author saman
 * This class represent a block in main memory. It can have at most 40 tuples
 */
public class MemoryBuffer {

    private int totalStudents = 0;

    private ArrayList<Student> students = new ArrayList<>();
    private int len;
    private BufferedReader bufferedReader;

    public MemoryBuffer(String inputFileName) {
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFileName));
            clear();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the \"" + inputFileName + "\" file!");
        }
    }
    
    public void clear()
    {
    	students.clear();
    	len = 0;
    }

    public int getTotalStudents() {
        return totalStudents;
    }
    
    private boolean hasEnoughHeap()
    {
    	boolean ret = Runtime.getRuntime().freeMemory() > (Tpmms.tuples * Tpmms.tupleSize + Tpmms.misc);
    	if (!ret)
    	{
    		System.out.println("Empty memoery: " + Runtime.getRuntime().freeMemory());
    	}
    	return ret;
    }

    // Returns true if the input file is not finished
    public boolean readBlocksUntilMemory() {
        String line;
        while (len < students.size() || hasEnoughHeap()) {
            for (int i = 0; i < Tpmms.tuples; i++) {
                try {
                    if ((line = bufferedReader.readLine()) == null) {
                        bufferedReader.close();
                        System.out.println("We reached end of file");
                        return false;
                    }
                    if (len < students.size())
                    	students.get(len++).parseLine(line);
                    else 
                    	students.add(new Student(line));
                    totalStudents++;
                } catch (IOException e) {
                    System.out.println("Cannot read the input file!");
                    return false;
                }
            }
        }
        System.out.println("We utilized memory");
        return true;
    }

    public void sort() {
    	students.subList(0, len).sort(Student::compareTo);
    }

    public void flush(String sublistFileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sublistFileName));
            for (int i = 0; i < len; ++i) {
            	Student student = students.get(i);
                bufferedWriter.write(student.toString() + "\r\n");
            }
            bufferedWriter.close();
            len = 0;
        } catch (IOException e) {
            System.out.println("Cannot write to the \"" + sublistFileName + "\" file!");
        }
    }
}
