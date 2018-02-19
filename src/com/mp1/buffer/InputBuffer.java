package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class InputBuffer {

    private boolean lastBatch = false;

    private ArrayList<Student> students = new ArrayList<>();
    private ListIterator<Student> studentIterator;
    private BufferedReader bufferedReader;

    public InputBuffer(String sublistFileName, int blocks) {
        try {
            bufferedReader = new BufferedReader(new FileReader(sublistFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the \"" + sublistFileName + "\" file!");
        }
        reload(blocks);
    }

    public boolean isEmpty() {
        return !studentIterator.hasNext();
    }

    public boolean isLastBatch() {
        return lastBatch;
    }

    public Student peekNextStudent() {
        return students.get(studentIterator.nextIndex());
    }

    public Student getNextStudent() {
        return studentIterator.next();
    }

    public void reload(int blocks) {
        students = new ArrayList<>();
        studentIterator = students.listIterator();
        System.gc();
        String line;
        for (int i = 0; i < blocks; i++) {
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    studentIterator = students.listIterator();
                    bufferedReader.close();
                    lastBatch = true;
                    return;
                }
                students.add(new Student(line));
            } catch (IOException e) {
                System.out.println("Cannot read the input file!");
            }
        }
        studentIterator = students.listIterator();
    }
}
