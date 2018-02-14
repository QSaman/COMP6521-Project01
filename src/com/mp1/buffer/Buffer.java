package com.mp1.buffer;

import com.mp1.schema.Student;

public class Buffer {

    public static int size = 2;

    int itr = 0;
    Student[] students = new Student[size];

    public void incItr() {
        itr++;
    }

    public void resetItr() {
        itr = 0;
    }
}
