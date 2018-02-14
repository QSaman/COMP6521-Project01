package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputBuffer extends Buffer {

    public boolean isEmpty() {
        return itr >= students.length;
    }

    public Student getCurrentStudent() {
        return students[itr];
    }

    public void reload(String fileName) {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            for (short i = 0; i < students.length && (line = bufferedReader.readLine()) != null; i++) {
                students[i] = new Student(line);
            }
        } catch (IOException e) {
            System.out.println("Cannot open \"" + fileName + "\" file!");
        }
    }
}
