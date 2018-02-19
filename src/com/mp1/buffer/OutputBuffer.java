package com.mp1.buffer;

import com.mp1.schema.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OutputBuffer {

    private ArrayList<Student> students = new ArrayList<>();
    private BufferedWriter bufferedWriter;

    public OutputBuffer(String outputFileName) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
        } catch (IOException e) {
            System.out.println("Cannot create the \"" + outputFileName + "\" file!");
        }
    }

    public void add(Student student) {
        students.add(student);
    }

    public void flush() {
        students.forEach(student -> {
            try {
                bufferedWriter.write(student.toString() + "\r\n");
            } catch (IOException e) {
                System.out.println("Cannot write to the output file!");
            }
        });
        students.clear();
    }

    public void closeOutputFile() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot close the output file!");
        }
    }
}
