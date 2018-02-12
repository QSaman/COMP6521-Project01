package com.mp1;

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

	private byte[] studentID = new byte[8];
	private byte[] firstName = new byte[10];
	private byte[] lastName = new byte[10];
	private byte[] department = new byte[3];
	private byte[] program = new byte[3];
	private byte[] sinNumber = new byte[9];
	private byte[] address = new byte[57];

	public Student() {
	}

	public Student(byte[] fields) {
		setFields(fields);
	}

	public Student(byte[] studentID, byte[] firstName, byte[] lastName, byte[] department, byte[] program,
			byte[] sinNumber, byte[] address) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.program = program;
		this.sinNumber = sinNumber;
		this.address = address;
	}

	public byte[] getFields() {
		byte[] fields = new byte[100];
		int i = 0;
		for (byte b : studentID)
			fields[i++] = b;
		for (byte b : firstName)
			fields[i++] = b;
		for (byte b : lastName)
			fields[i++] = b;
		for (byte b : department)
			fields[i++] = b;
		for (byte b : program)
			fields[i++] = b;
		for (byte b : sinNumber)
			fields[i++] = b;
		for (byte b : address)
			fields[i++] = b;
		return fields;
	}

	public void setFields(byte[] fields) {
		studentID = Arrays.copyOfRange(fields, 0, 8);
		firstName = Arrays.copyOfRange(fields, 8, 18);
		lastName = Arrays.copyOfRange(fields, 18, 28);
		department = Arrays.copyOfRange(fields, 28, 31);
		program = Arrays.copyOfRange(fields, 31, 34);
		sinNumber = Arrays.copyOfRange(fields, 34, 43);
		address = Arrays.copyOfRange(fields, 43, 100);
	}

	public byte[] getStudentID() {
		return studentID;
	}

	public void setStudentID(byte[] studentID) {
		this.studentID = studentID;
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

	public byte[] getDepartment() {
		return department;
	}

	public void setDepartment(byte[] department) {
		this.department = department;
	}

	public byte[] getProgram() {
		return program;
	}

	public void setProgram(byte[] program) {
		this.program = program;
	}

	public byte[] getSinNumber() {
		return sinNumber;
	}

	public void setSinNumber(byte[] sinNumber) {
		this.sinNumber = sinNumber;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

	@Override
	public String toString() {
		String[] fields = new String[] { new String(studentID), new String(firstName), new String(lastName),
				new String(department), new String(program), new String(sinNumber), new String(address) };
		return "Student ID: " + fields[0] + "\r\nFirst Name: " + fields[1] + "\r\nLast Name: " + fields[2]
				+ "\r\nDepartment: " + fields[3] + "\r\nProgram: " + fields[4] + "\r\nSIN Number: " + fields[5]
				+ "\r\nAddress: " + fields[6];
	}

	@Override
	public boolean equals(Object other) {
		return Arrays.equals(getFields(), ((Student) other).getFields());
	}
}
