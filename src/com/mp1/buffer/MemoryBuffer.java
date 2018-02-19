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

    private ArrayList<Student> students = new ArrayList<>();
    private BufferedReader bufferedReader;

    public MemoryBuffer(String inputFileName) {
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the \"" + inputFileName + "\" file!");
        }
    }

    // Returns true if the input file is not finished
    public boolean readBlocksUntilMemory() {
        String line;
        while (Runtime.getRuntime().freeMemory() > (Tpmms.tuples * Tpmms.tupleSize + Tpmms.misc)) {
            for (int i = 0; i < Tpmms.tuples; i++) {
                try {
                    if ((line = bufferedReader.readLine()) == null) {
                        bufferedReader.close();
                        return false;
                    }
                    students.add(new Student(line));
                } catch (IOException e) {
                    System.out.println("Cannot read the input file!");
                    return false;
                }
            }
        }
        return true;
    }

    public void sort() {
        students.sort(Student::compareTo);
    }

    public void flush(String sublistFileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(sublistFileName));
            for (Student student : students) {
                bufferedWriter.write(student.toString() + "\r\n");
            }
            students = new ArrayList<>();
            System.gc();
//            students.clear();
//            students.trimToSize();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot write to the \"" + sublistFileName + "\" file!");
        }
    }
}
