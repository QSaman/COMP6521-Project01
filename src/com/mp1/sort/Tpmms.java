package com.mp1.sort;

import com.mp1.buffer.InputBuffer;
import com.mp1.buffer.MemoryBuffer;
import com.mp1.buffer.OutputBuffer;
import com.mp1.schema.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author saman
 */
public class Tpmms {

    private short sublistCount = 0;

    private short buffersCount;
    private MemoryBuffer[] memoryBuffers;
    private BufferedReader bufferedReader;

    public Tpmms(String input_file_name, short buffersCount) throws FileNotFoundException {
        this.buffersCount = buffersCount;
        memoryBuffers = new MemoryBuffer[buffersCount];
        for (int i = 0; i < memoryBuffers.length; i++) {
            memoryBuffers[i] = new MemoryBuffer();
        }
        bufferedReader = new BufferedReader(new FileReader(input_file_name));
    }

    /**
     * @return true, if we can sort the relation in at most two phases;
     * otherwise return false
     * @throws IOException
     */
    public boolean phase1() throws IOException {
        while (true) {
            for (short i = 0; i < buffersCount; i++) {
                String line;
                memoryBuffers[i].resetItr();
                while ((line = bufferedReader.readLine()) != null && !memoryBuffers[i].isFull()) {
                    memoryBuffers[i].add(new Student(line));
                }
                memoryBuffers[i].sort();
                if (line == null) {
                    flushAllBlocks();
                    sublistCount++;
                    return true;
//                    if (sublistCount > 0) {
//                        flushAllBlocks();
//                        sublistCount++;
//                        return true;
//                    } else {
//                        return false;
//                    }
                }
            }
            sublistCount++;
            flushAllBlocks();
        }
    }

    public void phase2() {
        merge();
    }

    private void flushAllBlocks() throws IOException {
        for (short i = 0; i < buffersCount; i++) {
            memoryBuffers[i].flush(String.format("sublist%05d.out", i++), false);
        }
    }

    private void merge() {
        InputBuffer[] inputBuffers = new InputBuffer[sublistCount * buffersCount];
        for (short i = 0; i < sublistCount * buffersCount; i++) {
            inputBuffers[i] = new InputBuffer();
        }
        // Fill all input buffers
        for (short i = 0; i < sublistCount * buffersCount; i++) {
            inputBuffers[i].reload(String.format("sublist%05d.out", i));
        }
        // Find the minimum student at each iteration and fill the output buffer
        OutputBuffer outputBuffer = new OutputBuffer();
        // This iterator cannot be short
        for (int i = 0; i < inputBuffers.length * 2; i++) {
            Student minStudent = getMinimum(inputBuffers);
            outputBuffer.add(minStudent);
            if (outputBuffer.isFull()) {
                outputBuffer.flush("sorted.txt", true);
            }
        }
    }

    private Student getMinimum(InputBuffer[] inputBuffers) {
        short minIndex = 0;
        Student minStudent = inputBuffers[minIndex].getCurrentStudent();
        for (short i = 1; i < inputBuffers.length; i++) {
            if (inputBuffers[i].getCurrentStudent().isLessThan(minStudent)) {
                minIndex = i;
                minStudent = inputBuffers[minIndex].getCurrentStudent();
            }
        }
        inputBuffers[minIndex].incItr();
        if (inputBuffers[minIndex].isEmpty()) {
            inputBuffers[minIndex].reload(String.format("sublist%05d.out", minIndex));
        }
        return minStudent;
    }
}
