package com.mp1;

import com.mp1.sort.Tpmms;

import java.io.IOException;

public class BagDifference {

    // Available memory in KBytes
    private static final int availableMemory = 5000;
    // Tuple size in KBytes
    private static final int tupleSize = 4;
    // Internal memory usage in KBytes
    private static final int internalMemoryUsage = 500;

    private static void bd(String T1, String T2) throws IOException {
        Tpmms tpmms;
        {
            tpmms = new Tpmms(T1, (availableMemory - internalMemoryUsage) / tupleSize);
            if (tpmms.phase1()) {
                tpmms.phase2("tpmms1.txt");
            } else {
                tpmms.phase2("tpmms1.txt");
            }
        }
        {
            tpmms = new Tpmms(T2, (availableMemory - internalMemoryUsage) / tupleSize);
            if (tpmms.phase1()) {
                tpmms.phase2("tpmms2.txt");
            } else {
                tpmms.phase2("tpmms2.txt");
            }
        }
        // TODO: Compare tpmms1.txt and tpmms2.txt and find duplicates I guess!!!
    }

    public static void main(String[] args) {
        try {
            bd("bag1.txt", "bag2.txt");
        } catch (IOException e) {
            System.out.println("Cannot open input file!");
        }
    }
}
