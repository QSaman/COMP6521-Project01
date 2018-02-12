/*
 * File: Student.java
 *
 * Author: Mirmohammad Saadati
 *
 *
 */

package com.mp1.domain;

public class Student {

	private byte[] studentID = new byte[8];
	private byte[] firstName = new byte[10];
	private byte[] lastName = new byte[10];
	private byte[] department = new byte[3];
	private byte[] program = new byte[3];
	private byte[] sinNumber = new byte[9];
	private byte[] address = new byte[57];

	public Student() {
	}

	public Student(byte[] studentID, byte[] firstName, byte[] lastName, byte[] department, byte[] program, byte[] sinNumber, byte[] address) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.program = program;
		this.sinNumber = sinNumber;
		this.address = address;
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
}
