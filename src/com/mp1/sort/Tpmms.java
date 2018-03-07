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
    
    public void changeInputFile(String inputFileName)
    {
    	memoryBuffer.changeInputFile(inputFileName);
    	totalStudents = totalSublists = 0;
    }

    public void sort(String outputFileName) {
    	System.out.println("misc: " + misc);
    	System.out.println("student_buf: " + student_buf);
    	System.out.println("student_buf_size: " + student_buf_size);
    	System.out.println("");
    	System.out.println("Starting phase 1");
        phase1();
    	System.out.println("students size: " + students.size());
    	System.out.println("Total Students: " + memoryBuffer.getTotalStudents());
    	System.out.println("Starting phase 2");
        phase2(outputFileName);
    }

    private boolean phase1() {
        boolean needPhase2 = false;
        while (true) {
        	int cnt = memoryBuffer.readBlocksUntilMemory();
        	if (cnt > 0)
        	{
        		memoryBuffer.sort();
            	++totalSublists;
        	}
            if (cnt < Tpmms.tuples)
            	break;
            String name = String.format("tmp/sublist%05d.txt", totalSublists);
            System.out.println("Saving memory buffer into " + name);
            memoryBuffer.flush(name);            
        }
//        memoryBuffer.clear();
//        System.gc();
        students = memoryBuffer.getBuffer();
        needPhase2 = totalSublists > 1;
        return needPhase2;
    }

    private void phase2(String outputFileName) {
        merge(outputFileName);
    }

    private void merge(String outputFileName) {
        ArrayList<InputBuffer> inputBuffers = new ArrayList<>();        
        // Initialize buffers
        int blocks = students.size() / (totalSublists + 1);
        System.out.println("block: " + blocks);        
        if (totalSublists == 1)
        {
        	blocks = memoryBuffer.getBuffer().size();
        	inputBuffers.add(new InputBuffer(memoryBuffer.getBuffer(), blocks));
        }
        else
        {
        	int index = 0;
	        for (int i = 0; i < totalSublists; i++) {
	        	//int blocks = (int)((Runtime.getRuntime().freeMemory() * buffer_ratio)) / (totalSublists + 1);
	            inputBuffers.add(new InputBuffer(String.format("tmp/sublist%05d.txt", i + 1), blocks, students.subList(index, index + blocks)));
	            index += blocks;
	        }
        }
        System.out.println("The number of input buffers: " + inputBuffers.size());
        OutputBuffer outputBuffer = new OutputBuffer(outputFileName);
        int progress = -1;
        int progress_cur;
        Student cur_student = null;
        for (int i = 0; i < memoryBuffer.getTotalStudents(); i++) {
        	progress_cur = (int)(i * 100 / memoryBuffer.getTotalStudents());
        	if (progress_cur > progress)
        	{
        		progress = progress_cur;
        		System.out.println(progress + "%");
        	}
            Student minStudent = getMinimum(inputBuffers).orElse(new Student());
            if (cur_student == null || !cur_student.equals(minStudent)) {
                if (outputBuffer.shouldFlush()) {
                    outputBuffer.flush();
                }
            	outputBuffer.add(minStudent);
            	cur_student = minStudent;
            }
            else
            	outputBuffer.increase();
        }
        System.out.println(100 + "%");
        System.out.println("Saving result into " + outputFileName);
        outputBuffer.flush();
        outputBuffer.closeOutputFile();
    }

    private Optional<Student> getMinimum(ArrayList<InputBuffer> inputBuffers) {
    	
        if (!inputBuffers.isEmpty()) {
            //System.out.println(inputBuffers.size());
            int minIndex = -1;
            Student minStudent = null;//inputBuffers.get(0).peekNextStudent();
            for (int i = 0; i < inputBuffers.size(); i++) {
            	if (inputBuffers.get(i).isEmpty())
            		continue;
                if (minStudent == null || inputBuffers.get(i).peekNextStudent().isLessThan(minStudent)) {
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
                } /*else {
                    inputBuffers.remove(minIndex);
                    inputBuffers.trimToSize();
                    System.gc();
                }*/
            }
            return Optional.of(minStudent);
        }
        return Optional.empty();
    }
}
