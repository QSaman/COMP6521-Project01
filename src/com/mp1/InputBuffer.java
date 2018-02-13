package com.mp1;

public class InputBuffer extends Buffer {

    public boolean isEmpty() {
        return itr >= size;
    }

    public void add(Student student, short index) {
        buffer[index] = student;
    }

    public Student getCurrentStudent() {
        return buffer[itr];
    }
}
