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

    // Size of each tuple in bytes
    public static final int tupleSize = 89;
    // Number of tuples per block
    public static final int tuples = 40;
    
    // Minimum free memory allowed in bytes
    public static final double system_ratio = 1.0 / 3.0;
    public static final double buffer_ratio = 2.0 / 3.0;
    
    public static final int misc = (int)((Runtime.getRuntime().freeMemory()) * system_ratio);
    public static final int student_buf = (int)((Runtime.getRuntime().freeMemory() * buffer_ratio));
    public static final int student_buf_size =  student_buf / tupleSize;

    private int totalStudents = 0;
    private int totalSublists = 0;

    private MemoryBuffer memoryBuffer;
    private ArrayList<Student> students;

    public Tpmms(String inputFileName) {
        memoryBuffer = new MemoryBuffer(inputFileName);        
    }

    public void sort(String outputFileName) {
    	System.out.println("misc: " + misc);
    	System.out.println("student_buf: " + student_buf);
    	System.out.println("student_buf_size: " + student_buf_size);
    	System.out.println("");
    	System.out.println("Starting phase 1");    	
        if (phase1()) {
        	System.out.println("students size: " + students.size());
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
//        memoryBuffer.clear();
//        System.gc();
        students = memoryBuffer.getBuffer();
        return needPhase2;
    }

    private void phase2(String outputFileName) {
        merge(outputFileName);
    }

    private void merge(String outputFileName) {
        ArrayList<InputBuffer> inputBuffers = new ArrayList<>();        
        // Initialize buffers
        int blocks = students.size() / (totalSublists + 1);
        int index = 0;
        for (int i = 0; i < totalSublists; i++) {
        	//int blocks = (int)((Runtime.getRuntime().freeMemory() * buffer_ratio)) / (totalSublists + 1);
            inputBuffers.add(new InputBuffer(String.format("tmp/sublist%05d.txt", i), blocks, students.subList(index, index + blocks)));
        }
        OutputBuffer outputBuffer = new OutputBuffer(outputFileName);
        for (int i = 0; i < memoryBuffer.getTotalStudents(); i++) {
            Student minStudent = getMinimum(inputBuffers).orElse(new Student());
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

    private Optional<Student> getMinimum(ArrayList<InputBuffer> inputBuffers) {
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
                    inputBuffers.get(minIndex).reload(inputBuffers.get(minIndex).getBlocks());
                } else {
                    inputBuffers.remove(minIndex);
                    inputBuffers.trimToSize();
                    System.gc();
                }
            }
            return Optional.of(minStudent);
        }
        return Optional.empty();
    }
}
