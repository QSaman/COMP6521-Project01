package com.mp1.schema;

/**
 * @author mirmohammad
 */
public class Student {

    private int studentId = 0;
    private byte[] firstName = new byte[10];
    private byte[] lastName = new byte[10];
    private short department = 0;
    private short program = 0;
    private int sinNumber = 0;
    private byte[] address = new byte[57];

    public Student() {
    }

    public Student(String entry) {
        parseLine(entry);
    }

    private void parseLine(String line) {
        studentId = Integer.parseInt(line.substring(0, 8));
        firstName = line.substring(8, 18).getBytes();
        lastName = line.substring(18, 28).getBytes();
        department = Short.parseShort(line.substring(28, 31));
        program = Short.parseShort(line.substring(31, 34));
        sinNumber = Integer.parseInt(line.substring(34, 43));
        address = line.substring(43, 100).getBytes();
    }

    public int getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return String.format("%-8d", studentId) + String.format("%-10s", new String(firstName)) + String.format("%-10s", new String(lastName))
                + String.format("%03d", department) + String.format("%03d", program)
                + String.format("%09d", sinNumber) + String.format("%-57s", new String(address));
    }

    public boolean isLessThan(Student o) {
        return studentId - o.getStudentId() < 0;
    }
}
