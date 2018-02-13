package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputBuffer extends Buffer {

    public boolean isFull() {
        return itr >= size;
    }

    public void add(Student student) {
        buffer[itr++] = student;
    }

    public void flush(String fileName) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            for (int i = 0; i < Buffer.size; i++) {
                bufferedWriter.write(buffer[i].toString());
            }
        } catch (IOException e) {
            System.out.println("Cannot create\\open \"sorted.txt\" file!");
        }
    }
}
