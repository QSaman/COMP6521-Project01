package com.mp1.buffer;

import com.mp1.schema.Student;

/**
 * @author saman
 * This class represent a block in main memory. It can have at most 40 tuples
 */
public class MemoryBuffer extends OutputBuffer {

    // Size of each memory block
    public static final short size = 40;

    private Student[] students = new Student[size];

    public MemoryBuffer() {
    }

    public void add(Student student, short index) {
        students[index] = student;
    }

    public void sort() {
        for (int i = 0; i < students.length; i++) {

        }
    }
}
