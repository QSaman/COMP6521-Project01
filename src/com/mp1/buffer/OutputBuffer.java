package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputBuffer extends Buffer {

    public boolean isFull() {
        return itr >= students.length;
    }

    public void add(Student student) {
        students[itr++] = student;
    }

    public void flush(String fileName, boolean append) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, append));
            for (int i = 0; i < students.length; i++) {
                bufferedWriter.write(students[i].toString());
            }
        } catch (IOException e) {
            System.out.println("Cannot create\\open \"" + fileName + "\" file!");
        }
    }
}
