package com.mp1.disk;

import com.mp1.OutputBuffer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BufferWriter {

    private BufferedWriter bufferedWriter;

    public BufferWriter() {
    }

    public BufferWriter(String fileName) throws IOException {
        open(fileName);
    }

    public void open(String fileName) throws IOException {
        bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
    }

    public void flushOutputBuffer(OutputBuffer buffer) throws IOException {
        bufferedWriter.write(buffer.toString());
    }
}
