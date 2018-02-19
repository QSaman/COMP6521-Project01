package com.mp1.schema;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author mirmohammad
 */
public class Student implements Comparable<Student> {

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

    //The following method is from:
    //https://stackoverflow.com/questions/5108091/java-comparator-for-byte-array-lexicographic/5108711
    private static int compareByteArrays(byte[] left, byte[] right) {
        for (int i = 0, j = 0; i < left.length && j < right.length; i++, j++) {
            int a = (left[i] & 0xff);
            int b = (right[j] & 0xff);
            if (a != b) {
                return a - b;
            }
        }
        return left.length - right.length;
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

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return String.format("%-8d", studentId) + String.format("%-10s", new String(firstName)) + String.format("%-10s", new String(lastName))
                + String.format("%03d", department) + String.format("%03d", program)
                + String.format("%09d", sinNumber) + String.format("%-57s", new String(address));
    }

    public boolean isLessThan(Student o) {
        return compareTo(o) < 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(address);
        result = prime * result + department;
        result = prime * result + Arrays.hashCode(firstName);
        result = prime * result + Arrays.hashCode(lastName);
        result = prime * result + program;
        result = prime * result + sinNumber;
        result = prime * result + studentId;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Student))
            return false;
        Student other = (Student) obj;
        if (!Arrays.equals(address, other.address))
            return false;
        if (department != other.department)
            return false;
        if (!Arrays.equals(firstName, other.firstName))
            return false;
        if (!Arrays.equals(lastName, other.lastName))
            return false;
        if (program != other.program)
            return false;
        if (sinNumber != other.sinNumber)
            return false;
        if (studentId != other.studentId)
            return false;
        return true;
    }

    public byte[] getBytes() {
        byte[] ret = new byte[100];

        System.arraycopy(String.format("%08d", studentId).getBytes(StandardCharsets.US_ASCII),
                0, ret, 0, 8);
        System.arraycopy(firstName, 0, ret, 8, 10);
        System.arraycopy(lastName, 0, ret, 18, 10);
        System.arraycopy(String.format("%03d", department).getBytes(StandardCharsets.US_ASCII),
                0, ret, 28, 3);
        System.arraycopy(String.format("%03d", program).getBytes(StandardCharsets.US_ASCII),
                0, ret, 31, 3);
        System.arraycopy(String.format("%09d", sinNumber).getBytes(StandardCharsets.US_ASCII),
                0, ret, 34, 9);
        System.arraycopy(address, 0, ret, 43, 57);
        return ret;
    }

    @Override
    public int compareTo(Student o) {
        int diff = Integer.compare(studentId, o.getStudentId());
        if (diff != 0)
            return diff;
        diff = Short.compare(department, o.getDepartment());
        if (diff != 0)
            return diff;
        diff = Short.compare(program, o.getProgram());
        if (diff != 0)
            return diff;
        diff = Integer.compare(sinNumber, o.getSinNumber());
        if (diff != 0)
            return diff;

        diff = compareByteArrays(firstName, o.getFirstName());
        if (diff != 0)
            return diff;
        diff = compareByteArrays(lastName, o.getLastName());
        if (diff != 0)
            return diff;
        diff = compareByteArrays(address, o.getAddress());
        return diff;
    }

    /**
     * @return the firstName
     */
    public byte[] getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(byte[] firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public byte[] getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(byte[] lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the department
     */
    public short getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(short department) {
        this.department = department;
    }

    /**
     * @return the program
     */
    public short getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(short program) {
        this.program = program;
    }

    /**
     * @return the sinNumber
     */
    public int getSinNumber() {
        return sinNumber;
    }

    /**
     * @param sinNumber the sinNumber to set
     */
    public void setSinNumber(int sinNumber) {
        this.sinNumber = sinNumber;
    }

    /**
     * @return the address
     */
    public byte[] getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(byte[] address) {
        this.address = address;
    }

}
