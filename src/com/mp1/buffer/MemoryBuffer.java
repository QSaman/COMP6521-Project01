package com.mp1.buffer;

import com.mp1.schema.Student;
import com.mp1.sort.Tpmms;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author saman
 * This class represent a block in main memory. It can have at most 40 tuples
 */
public class MemoryBuffer {

    private int totalStudents = 0;

    private ArrayList<Student> students;
    private int len;
    private BufferedReader bufferedReader;
    private String line;

    public MemoryBuffer(String inputFileName) {	
        try {
        	this.students = new ArrayList<>(Tpmms.student_buf_size);
        	bufferedReader = new BufferedReader(new InputStreamReader(
        			new FileInputStream(inputFileName), StandardCharsets.US_ASCII),
        			Tpmms.tuples * Tpmms.tupleSize);
            clear();            
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the \"" + inputFileName + "\" file!");
        }
    }
    
    public void changeInputFile(String inputFileName)
    {
    	try {
    		bufferedReader.close();
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(inputFileName), StandardCharsets.US_ASCII),
					Tpmms.tuples * Tpmms.tupleSize);
			len = 0;
			totalStudents = 0;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public ArrayList<Student> getBuffer()
    {
    	return students;
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
    	return ret;
    }

    // Returns true if the input file is not finished
    public int readBlocksUntilMemory() {
    	int ret = 0;
        while (true) {
            for (int i = 0; i < Tpmms.tuples; i++) {
                try {
                	if (len >= students.size() && (!hasEnoughHeap() || len >= Tpmms.student_buf_size))
                		return ret;
                    if ((line = bufferedReader.readLine()) == null) {
                        bufferedReader.close();
                        return ret;
                    }
                    if (len < students.size())
                    	students.get(len++).parseLine(line);
                    else
                    {
                    	students.add(new Student(line));
                    	++len;
                    }
                    totalStudents++;
                    ++ret;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Memory Buffer: Cannot read the input file!");
                    return ret;
                }
            }
        }
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
