package com.mp1;

import com.mp1.disk.BlockReader;
import com.mp1.disk.BlockWriter;
import com.mp1.disk.BufferReader;
import com.mp1.disk.BufferWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author saman
 */
public class Tpmms {

    private final short numberOfBuffers = 1000;
    private ArrayList<MemoryBuffer> buffers;
    private BlockReader inputReader;
    private short totalBuffers = 0;

    public Tpmms(String input_file_name) throws FileNotFoundException {
        buffers = new ArrayList<>();
        inputReader = new BlockReader();
        inputReader.open(input_file_name);
    }

    /**
     * @return true, if we can sort the relation in at most two phases;
     * otherwise return false
     * @throws IOException
     */
    public boolean phase1() throws IOException {
        boolean two_phase = true;
        boolean done = false;
        while (!done) {
            while (buffers.size() <= numberOfBuffers) {
                if (Runtime.getRuntime().freeMemory() <= 4096) {
                    two_phase = false;
                    break;
                }
                MemoryBuffer buffer = new MemoryBuffer();
                inputReader.nextBlock(buffer);
                if (buffer.size() == 0) {
                    done = true;
                    break;
                }
                buffer.sort();
                buffers.add(buffer);
                totalBuffers++;
            }
            save();
        }
        return two_phase;
    }

    private boolean mergeRequired() {
        return buffers.size() != 1;
    }

    public void save() {
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
        BufferReader bufferReader = new BufferReader();
        InputBuffer[] inputBuffers = new InputBuffer[totalBuffers];
        // Fill all input buffers
        for (short i = 0; i < totalBuffers; i++) {
            try {
                bufferReader.open(String.format("sublist%05d.out", i));
                try {
                    bufferReader.nextInputBuffer(inputBuffers[i]);
                } catch (IOException e) {
                    System.out.println("There is a problem with sublist files!");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find sublist files!");
            }
        }
        // Find the minimum student at each iteration and fill the output buffer
        OutputBuffer outputBuffer = new OutputBuffer();
        for (int i = 0; i < inputBuffers.length * 2; i++) {
            Student minStudent = getMinimum(inputBuffers);
            outputBuffer.add(minStudent);
            if (outputBuffer.isFull()) {
                try {
                    BufferWriter bufferWriter = new BufferWriter("sorted.txt");
                    bufferWriter.flushOutputBuffer(outputBuffer);
                    outputBuffer.resetItr();
                } catch (IOException e) {
                    System.out.println("Cannot create\\open \"sorted.txt\" file!");
                }
            }
        }
    }

    public Student getMinimum(InputBuffer[] inputBuffers) {
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
            try {
                BufferReader bufferReader = new BufferReader(String.format("sublist%05d.out", minIndex));
                try {
                    bufferReader.nextInputBuffer(inputBuffers[minIndex]);
                    inputBuffers[minIndex].resetItr();
                } catch (IOException e) {
                    System.out.println("There is a problem with sublist files!");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Cannot find sublist files!");
            }
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
