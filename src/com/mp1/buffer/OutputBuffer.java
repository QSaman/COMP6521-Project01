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
            if (isFull()) {
                for (Student student : students) {
                    if (student.getStudentId() != 0) {
                        bufferedWriter.write(student.toString() + "\r\n");
                    }
                }
            } else {
                int total = 0;
                for (int i = 0; total < itr; i++) {
                    if (students[i].getStudentId() != 0) {
                        bufferedWriter.write(students[i].toString() + "\r\n");
                        total++;
                    }
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot create\\open \"" + fileName + "\" file!");
        }
    }
}
