package com.mp1.buffer;

import com.mp1.schema.Student;

/**
 * @author saman
 * This class represent a block in main memory. It can have at most 40 tuples
 */
public class MemoryBuffer extends OutputBuffer {

    public static short size = 40;

    public MemoryBuffer() {
        students = new Student[size];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student();
        }
    }

    public void sort() {
        for (short i = 1; i < students.length; i++) {
            Student key = students[i];
            short j = (short) (i - 1);
            while (j >= 0 && students[j].getStudentId() > key.getStudentId()) {
                students[j + 1] = students[j];
                j = (short) (j - 1);
            }
            students[j + 1] = key;
        }
    }
}
