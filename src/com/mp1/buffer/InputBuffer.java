package com.mp1.buffer;

import com.mp1.schema.Student;
import com.mp1.sort.Tpmms;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class InputBuffer {

    private boolean lastBatch = false;

    private List<Student> students;
    private int len, cur;
    private BufferedReader bufferedReader;
    private String line;
    private int blocks;
    private boolean readFromDisk;

    public InputBuffer(String sublistFileName, int blocks, List<Student> st) {
        try {
            students = st;
            readFromDisk = true;
            bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(sublistFileName), StandardCharsets.US_ASCII),
                    Tpmms.tuples * Tpmms.tupleSize);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the \"" + sublistFileName + "\" file!");
        }
        this.blocks = blocks;
        reload(blocks);
    }

    public InputBuffer(List<Student> students, int blocks) {
        this.blocks = blocks;
        bufferedReader = null;
        this.students = students;
        cur = 0;
        len = students.size();
        readFromDisk = false;
    }

    public void clear() {
        students.clear();
        len = 0;
        cur = 0;
    }

    public boolean isEmpty() {
        return cur >= len;
    }

    public boolean isLastBatch() {
        if (readFromDisk)
            return lastBatch;
        else
            return cur >= len;
    }

    public Student peekNextStudent() {
        return students.get(cur);
    }

    public Student getNextStudent() {
        return students.get(cur++);
    }

    public void reload(int blocks) {
        cur = 0;
        len = 0;
        if (bufferedReader == null)
            return;
        for (int i = 0; i < blocks; i++) {
            try {
                if ((line = bufferedReader.readLine()) == null) {
                    bufferedReader.close();
                    lastBatch = true;
                    return;
                }
                if (len < students.size())
                    students.get(len++).parseLine(line);
                else
                    return;
            } catch (IOException e) {
                System.out.println("Input Buffer: Cannot read the input file!");
            }
        }
    }

    /**
     * @return the blocks
     */
    public int getBlocks() {
        return blocks;
    }

    /**
     * @param blocks the blocks to set
     */
    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }
}
