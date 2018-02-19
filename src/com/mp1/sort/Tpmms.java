package com.mp1.sort;

import com.mp1.buffer.InputBuffer;
import com.mp1.buffer.MemoryBuffer;
import com.mp1.buffer.OutputBuffer;
import com.mp1.schema.Student;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author saman
 */
public class Tpmms {

    // Minimum free memory allowed in bytes
    public static final int misc = 1000000;
    // Size of each tuple in bytes
    public static final int tupleSize = 89;
    // Number of tuples per block
    public static final int tuples = 40;

    private int totalStudents = 0;
    private int totalSublists = 0;

    private MemoryBuffer memoryBuffer;

    public Tpmms(String inputFileName) {
        memoryBuffer = new MemoryBuffer(inputFileName);
    }

    public void sort(String outputFileName) {
    	System.out.println("Starting phase 1");
        if (phase1()) {
        	System.out.println("Starting phase 2");
            phase2(outputFileName);
        } else {
            memoryBuffer.flush(outputFileName);
        }
    }

    private boolean phase1() {
        boolean needPhase2 = false;
        while (memoryBuffer.readBlocksUntilMemory()) {
            memoryBuffer.sort();
            memoryBuffer.flush(String.format("tmp/sublist%05d.txt", totalSublists++));
            needPhase2 = true;
        }
        memoryBuffer.clear();
        System.gc();
        return needPhase2;
    }

    private void phase2(String outputFileName) {
        merge(outputFileName);
    }

    private void merge(String outputFileName) {
        ArrayList<InputBuffer> inputBuffers = new ArrayList<>();
        int blocks = (int) (Runtime.getRuntime().freeMemory() - misc) / (totalSublists + 1);
        // Initialize buffers
        for (int i = 0; i < totalSublists; i++) {
            inputBuffers.add(new InputBuffer(String.format("tmp/sublist%05d.txt", i), blocks));
        }
        OutputBuffer outputBuffer = new OutputBuffer(outputFileName);
        for (int i = 0; i < memoryBuffer.getTotalStudents(); i++) {
            Student minStudent = getMinimum(inputBuffers, blocks).orElse(new Student());
            outputBuffer.add(minStudent);
            if (Runtime.getRuntime().freeMemory() < misc) {
                outputBuffer.flush();
                /// TEST
                // System.out.println("Output buffer flushed at " + i + " th tuple!");
            }
        }
        outputBuffer.flush();
        outputBuffer.closeOutputFile();
    }

    private Optional<Student> getMinimum(ArrayList<InputBuffer> inputBuffers, int blocks) {
        if (!inputBuffers.isEmpty()) {
            //System.out.println(inputBuffers.size());
            int minIndex = 0;
            Student minStudent = inputBuffers.get(0).peekNextStudent();
            for (int i = 1; i < inputBuffers.size(); i++) {
                if (inputBuffers.get(i).peekNextStudent().isLessThan(minStudent)) {
                    minIndex = i;
                    minStudent = inputBuffers.get(i).peekNextStudent();
                }
            }
            minStudent = inputBuffers.get(minIndex).getNextStudent();
            if (inputBuffers.get(minIndex).isEmpty()) {
                // System.out.println("Inside is empty " + minIndex);
                // Reload and check if sublist is done
                if (!inputBuffers.get(minIndex).isLastBatch()) {
                    inputBuffers.get(minIndex).reload(blocks);
                } else {
                    inputBuffers.remove(minIndex);
                    inputBuffers.trimToSize();
                    System.gc();
                    System.out.println(inputBuffers.size());
                }
            }
            return Optional.of(minStudent);
        }
        return Optional.empty();
    }
}
