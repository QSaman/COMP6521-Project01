package com.mp1;

import java.io.FileNotFoundException;

public class MP1 {

    public static void main(String[] args) {
        try {
            Tpmms tpmms = new Tpmms("bag1.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open \"bag1.txt\" file!");
        }
    }
}
