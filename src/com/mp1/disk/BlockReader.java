package com.mp1.disk;

import com.mp1.buffer.MemoryBuffer;
import com.mp1.schema.Student;

import javax.naming.SizeLimitExceededException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author saman
 */
public class BlockReader {

    private BufferedReader br;

    public BlockReader() {
        br = null;
    }

    public void open(String file_name) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(file_name));
    }

    public void close() throws IOException {
        if (br != null)
            br.close();
    }

    public void nextBlock(MemoryBuffer buffer) throws IOException {
        String line;
        int tuples = 0;
        while (tuples < 40 && (line = br.readLine()) != null) {
            ++tuples;
            Student student = new Student();
            student.parseLine(line);
            try {
                buffer.add(student);
            } catch (SizeLimitExceededException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
