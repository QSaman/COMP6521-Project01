package com.mp1;

import com.mp1.sort.Tpmms;

public class BagDifference {

    private static void bd(String T1, String T2) {
        Tpmms tpmms;
        {
            tpmms = new Tpmms(T2);
            tpmms.sort("tpmms2.txt");
        }
//        {
//            tpmms = new Tpmms(T2);
//            tpmms.sort("tpmms2.txt");
//        }
        // TODO: Compare tpmms1.txt and tpmms2.txt and find duplicates I guess!!!
    }

    public static void main(String[] args) {
    	long startTime = System.nanoTime();
        bd("bag1.txt", "bag2.txt");
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        double seconds = time / 1000000000.0;
        System.out.println("Execution time: " + seconds + "s");
    }
}
