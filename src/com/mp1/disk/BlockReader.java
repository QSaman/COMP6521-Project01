//package com.mp1.disk;
//
//import com.mp1.buffer.MemoryBuffer;
//import com.mp1.schema.Student;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
///**
// * @author saman
// */
//public class BlockReader {
//
//    private BufferedReader br;
//
//    public BlockReader() {
//        br = null;
//    }
//
//    public void open(String file_name) throws FileNotFoundException {
//        br = new BufferedReader(new FileReader(file_name));
//    }
//
//    public void close() throws IOException {
//        if (br != null)
//            br.close();
//    }
//
//    public void nextBlock(MemoryBuffer memoryBuffer) throws IOException {
//        String line;
//        for (short i = 0; i < MemoryBuffer.size && (line = br.readLine()) != null; i++) {
//            memoryBuffer.add(new Student(line), i);
//        }
//    }
//}
