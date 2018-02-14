package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedReader;
import java.io.IOException;

public class InputBuffer extends Buffer {

    public boolean isEmpty() {
        return itr >= students.length;
    }

    public Student getCurrentStudent() {
        return students[itr];
    }

    public void reload(BufferedReader bufferedReader) {
        String line;
        try {
            for (int i = 0; i < students.length; i++) {
                if ((line = bufferedReader.readLine()) != null) {
                    students[i] = new Student(line);
                    resetItr();
                } else {
                    students[i] = new Student();
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot use provided buffered reader!");
        }
    }
}
