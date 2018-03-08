package com.mp1.buffer;

import com.mp1.schema.Student;
import com.mp1.sort.Tpmms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputBuffer {

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Integer> frequency = new ArrayList<>();
    private BufferedWriter bufferedWriter;
    private int len = 0;

    public OutputBuffer(String outputFileName) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            System.out.println("Cannot create the \"" + outputFileName + "\" file!");
        }
    }

    public void add(Student student) {
    	System.out.println("Adding student " + new String(student.getFirstName()));
    	if (len < students.size())
    	{
    		students.set(len, student);
    		frequency.set(len, 1);
    		++len;
    		
    	}
    	else
    	{
    		students.add(student);
    		frequency.add(1);
    		++len;
    	}
    	for (int i = 0; i < len; ++i )
    		System.out.println("Student name " + i + ": " + new String(students.get(i).getFirstName()));
    }
    
    public void increase()
    {
    	int index = frequency.size() - 1;
    	frequency.set(index, frequency.get(index) + 1);
    }
    
    public boolean shouldFlush()
    {
    	//return Runtime.getRuntime().freeMemory() <= Tpmms.misc && len >= students.size();
    	return students.size() > 1000;
    }

    public void flush() {
    	for (int i = 0; i < len; ++i)
    	{
            try {
            	System.out.println("writing student " + new String(students.get(i).getFirstName()) + "with freq " + frequency.get(i));
                bufferedWriter.write(students.get(i).toString() + " " + frequency.get(i) + "\r\n");
            } catch (IOException e) {
                System.out.println("Cannot write to the output file!");
            }
    	}
    	len = 0;
    }

    public void closeOutputFile() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot close the output file!");
        }
    }
}
