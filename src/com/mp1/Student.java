package com.mp1;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author mirmohammad
 *
 */
public class Student {

	public static final int student_id_len = 8;
	public static final int first_name_len = 10;
	public static final int last_name_len = 10;
	public static final int department_len = 3;
	public static final int program_len = 3;
	public static final int sin_number_len = 9;
	public static final int address_len = 57;

	private int studentId;
	private byte[] firstName = new byte[10];
	private byte[] lastName = new byte[10];
	private short department;
	private short program;
	private int sinNumber;
	private byte[] address = new byte[57];

	public Student() {
	}
	
	public void parseLine(String line)
	{
		studentId = Integer.parseInt(line.substring(0, 8));
		firstName = line.substring(8, 18).getBytes(StandardCharsets.US_ASCII);
		lastName = line.substring(18, 28).getBytes(StandardCharsets.US_ASCII);
		department = Short.parseShort(line.substring(28, 31));
		program = Short.parseShort(line.substring(31, 34));
		sinNumber = Integer.parseInt(line.substring(34, 43));
		address = line.substring(43, 100).getBytes(StandardCharsets.US_ASCII);
	}


	public byte[] getFirstName() {
		return firstName;
	}

	public void setFirstName(byte[] firstName) {
		this.firstName = firstName;
	}

	public byte[] getLastName() {
		return lastName;
	}

	public void setLastName(byte[] lastName) {
		this.lastName = lastName;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}		

	/**
	 * @return the studentId
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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

	@Override
	public String toString() {
		String[] fields = new String[] { Integer.toString(studentId), new String(firstName), new String(lastName),
				Short.toString(department), Short.toString(program), Integer.toString(sinNumber), new String(address) };
		return "Student ID: " + fields[0] + "\r\nFirst Name: " + fields[1] + "\r\nLast Name: " + fields[2]
				+ "\r\nDepartment: " + fields[3] + "\r\nProgram: " + fields[4] + "\r\nSIN Number: " + fields[5]
				+ "\r\nAddress: " + fields[6];
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (studentId != other.studentId)
			return false;
		return true;
	}	
}
