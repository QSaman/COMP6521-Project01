package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputBuffer extends Buffer {

    public boolean isEmpty() {
        return itr >= size;
    }

    public Student getCurrentStudent() {
        return buffer[itr];
    }

    public void reload(String fileName) {
        String line;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            for (short i = 0; i < Buffer.size && (line = bufferedReader.readLine()) != null; i++) {
                buffer[i] = new Student(line);
            }
        } catch (IOException e) {
            System.out.println("Cannot open \"" + fileName + "\" file!");
        }
    }
}
