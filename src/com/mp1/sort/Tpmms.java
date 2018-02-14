package com.mp1.sort;

import com.mp1.buffer.InputBuffer;
import com.mp1.buffer.MemoryBuffer;
import com.mp1.buffer.OutputBuffer;
import com.mp1.disk.BlockWriter;
import com.mp1.schema.Student;

import java.io.*;

/**
 * @author saman
 */
public class Tpmms {

    private short sublistCount = 0;
    private short buffersCount;
    private MemoryBuffer[] memoryBuffers;
    private BufferedReader bufferedReader;

    public Tpmms(String input_file_name, String output_file_name, short buffersCount) throws FileNotFoundException {
        this.buffersCount = buffersCount;
        memoryBuffers = new MemoryBuffer[buffersCount];
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
                String line = null;
                for (short j = 0; j < MemoryBuffer.size && (line = bufferedReader.readLine()) != null; j++) {
                    memoryBuffers[i].add(new Student(line), j);
                }
                memoryBuffers[i].sort();
                if (line == null) {
                    return sublistCount > 0;
                }
            }
            sublistCount++;
            flush();
        }
    }

    private boolean mergeRequired() {
        return buffers.size() != 1;
    }

    public void flush() throws IOException {
        for (short i = 0; i < buffersCount; i++) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.format("sublist%05d.out", i++)));
            bufferedWriter.write(memoryBuffers[i].toString());
        }
        int i = 1;
        for (MemoryBuffer buffer : buffers)
            if (!buffer.isDone()) {
                BlockWriter bw = new BlockWriter();
                try {
                    bw.open(String.format("sublist%05d.out", i++), true);
                    bw.write(buffer);
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        buffers.clear();
    }

    public void merge() {
        InputBuffer[] inputBuffers = new InputBuffer[totalBuffers];
        // Fill all input buffers
        for (short i = 0; i < totalBuffers; i++) {
            inputBuffers[i].reload(String.format("sublist%05d.out", i));
        }
        // Find the minimum student at each iteration and fill the output buffer
        OutputBuffer outputBuffer = new OutputBuffer();
        // This iterator cannot be short
        for (int i = 0; i < inputBuffers.length * 2; i++) {
            Student minStudent = getMinimum(inputBuffers);
            outputBuffer.add(minStudent);
            if (outputBuffer.isFull()) {
                outputBuffer.flush("sorted.txt");
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

    public void findDuplicates() throws IOException {
        if (phase1()) {
            if (mergeRequired()) // We can find duplicates in exactly 2 phases
            {
                save();
                merge();
            } else // We can find duplicates only in 1 phase
            {
                merge();
            }
        } else {
            // TODO we can find duplicates in more than 2 phases
        }
    }
}
