package com.mp1.disk;

import com.mp1.Buffer;
import com.mp1.InputBuffer;
import com.mp1.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferReader {

    private BufferedReader bufferedReader;

    public BufferReader() {
    }

    public BufferReader(String fileName) throws FileNotFoundException {
        open(fileName);
    }

    public void open(String fileName) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(fileName));
    }

    public void nextInputBuffer(InputBuffer inputBuffer) throws IOException {
        String line;
        for (short i = 0; i < Buffer.size && (line = bufferedReader.readLine()) != null; i++) {
            inputBuffer.add(new Student(line), i);
        }
    }
}
