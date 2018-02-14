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

    private int totalStudents = 0;
    private int sublistCount = 0;
    private int bufferCount;
    private MemoryBuffer[] memoryBuffers;
    private BufferedReader bufferedReader;

    public Tpmms(String input_file_name, int bufferCount) throws FileNotFoundException {
        this.bufferCount = bufferCount;
        memoryBuffers = new MemoryBuffer[bufferCount];
        for (int i = 0; i < memoryBuffers.length; i++) {
            memoryBuffers[i] = new MemoryBuffer();
        }
        bufferedReader = new BufferedReader(new FileReader(input_file_name));
    }

    public boolean phase1() throws IOException {
        while (true) {
            for (int i = 0; i < bufferCount; i++) {
                String line = null;
                memoryBuffers[i].resetItr();
                while (!memoryBuffers[i].isFull() && (line = bufferedReader.readLine()) != null) {
                    memoryBuffers[i].add(new Student(line));
                    totalStudents++;
                }
                memoryBuffers[i].sort();
                if (line == null) {
                    flushAllBlocks(i + 1);
                    if (sublistCount > 0) {
                        sublistCount++;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            flushAllBlocks(bufferCount);
            sublistCount++;
        }
    }

    public void phase2(String fileName) {
        merge(fileName);
    }

    private void flushAllBlocks(int thisPatch) {
        for (int i = 0; i < thisPatch; i++) {
            memoryBuffers[i].flush(String.format("tmp/sublist%05d.txt", i), false);
        }
    }

    private void merge(String fileName) {
        InputBuffer[] inputBuffers = new InputBuffer[totalStudents / MemoryBuffer.size + 1];
        BufferedReader[] bufferedReaders = new BufferedReader[totalStudents / MemoryBuffer.size + 1];
        for (int i = 0; i < totalStudents / MemoryBuffer.size + 1; i++) {
            inputBuffers[i] = new InputBuffer();
            try {
                bufferedReaders[i] = new BufferedReader(new FileReader(String.format("tmp/sublist%05d.txt", i)));
                inputBuffers[i].reload(bufferedReaders[i]);
            } catch (IOException e) {
                System.out.println("Cannot open \"" + String.format("tmp/sublist%05d.txt", i) + "\" file!");
            }
        }
        OutputBuffer outputBuffer = new OutputBuffer();
        for (int i = 0; i < totalStudents; i++) {
            Student minStudent = getMinimum(inputBuffers, bufferedReaders);
            outputBuffer.add(minStudent);
            if (outputBuffer.isFull()) {
                outputBuffer.flush(fileName, true);
                outputBuffer.resetItr();
            }
        }
        outputBuffer.flush(fileName, true);
        outputBuffer.resetItr();
    }

    private Student getMinimum(InputBuffer[] inputBuffers, BufferedReader[] bufferedReaders) {
        int minIndex = -1;
        Student minStudent = new Student();
        for (int i = 0; i < inputBuffers.length; i++) {
            if (minIndex < 0 && !inputBuffers[i].isEmpty()) {
                minIndex = i;
                minStudent = inputBuffers[minIndex].getCurrentStudent();
            }
            if (!inputBuffers[i].isEmpty() && inputBuffers[i].getCurrentStudent().isLessThan(minStudent)
                    && inputBuffers[i].getCurrentStudent().getStudentId() != 0) {
                minIndex = i;
                minStudent = inputBuffers[minIndex].getCurrentStudent();
            }
        }
        inputBuffers[minIndex].incItr();
        if (inputBuffers[minIndex].isEmpty()) {
            inputBuffers[minIndex].reload(bufferedReaders[minIndex]);
        }
        return minStudent;
    }
}
