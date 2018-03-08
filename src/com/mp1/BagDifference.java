package com.mp1;

import com.mp1.schema.Student;
import com.mp1.sort.Tpmms;

import java.io.*;

public class BagDifference {

    private static void bd(String T1, String T2) {
        Tpmms tpmms;
        {
            tpmms = new Tpmms(T1);
            //tpmms = new Tpmms("bag_test.txt");

            System.out.println("Sorting T1");
            System.out.println("#################################");
            tpmms.sort("tpmms1.txt");
            tpmms.changeInputFile(T2);
            System.out.println("#################################");
            System.out.println("Sorting T2");
            tpmms.sort("tpmms2.txt");
        }
        try {
            BufferedReader br1 = new BufferedReader(new FileReader("tpmms1.txt"));
            BufferedReader br2 = new BufferedReader(new FileReader("tpmms2.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("BG.txt"));
            String line;
            while ((line = br1.readLine()) != null) {
                int diff = -1, freq1 = 0, freq2 = 0;
                Student student1 = new Student();
                freq1 = student1.parseLineWithFreq(line);
                while ((line = br2.readLine()) != null) {
                    Student student2 = new Student();
                    freq2 = student2.parseLineWithFreq(line);
                    diff = student1.compareTo(student2);
                    System.out.println(diff);
                    if (diff >= 0) {
                        if (diff == 0 && freq1 > freq2) {
                            System.out.println(student1.toString() + " " + (freq1 - freq2));
                            bw.write(student1.toString() + " " + (freq1 - freq2) + "\r\n");
                        }
                        break;
                    }
                }
            }
            bw.flush();
            bw.close();
            br1.close();
            br2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        bd("demoBag1.txt", "demoBag1.txt");
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        double seconds = time / 1000000000.0;
        System.out.println("Execution time: " + seconds + "s");
    }
}
