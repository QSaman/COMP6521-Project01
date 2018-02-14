package com.mp1.buffer;

import com.mp1.schema.Student;

public class Buffer {

    public static short size = 2;

    short itr = 0;
    Student[] students = new Student[size];

    public void incItr() {
        itr++;
    }

    public void resetItr() {
        itr = 0;
    }
}
