package com.mp1;

public class OutputBuffer extends Buffer {

    public boolean isFull() {
        return itr >= size;
    }

    public void add(Student student) {
        buffer[itr++] = student;
    }
}
