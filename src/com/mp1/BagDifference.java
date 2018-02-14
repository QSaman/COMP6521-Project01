package com.mp1;

import com.mp1.sort.Tpmms;

import java.io.IOException;

public class BagDifference {

    // Available memory in KBytes
    private static final short availableMemory = 5000;
    // Tuple size in KBytes
    private static final short tupleSize = 4;
    // Internal memory usage in KBytes
    private static final short internalMemoryUsage = 500;

    private static void bd(String T1, String T2) throws IOException {
        Tpmms tpmms = new Tpmms(T1, (short) ((availableMemory - internalMemoryUsage) / tupleSize));
        tpmms.phase1();
        tpmms.phase2();
    }

    public static void main(String[] args) {
        try {
            bd("bag1.txt", "bag2.txt");
        } catch (IOException e) {
            System.out.println("Cannot open input file!");
        }
    }
}
