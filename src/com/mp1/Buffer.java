package com.mp1;

public class Buffer {

    public static final short size = 2;

    Student[] buffer = new Student[size];
    short itr = 0;

    public void incItr() {
        itr++;
    }

    public void resetItr() {
        itr = 0;
    }
}
