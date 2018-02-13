package com.mp1;

import java.util.ArrayList;

public class Buffer {
	
	public static final short size = 2;
	
	private ArrayList<Student> buffer;
	private short itr;
	
	public Buffer() {
		buffer = new ArrayList<>(size);
		itr = 0;
	}
	
	public boolean isFull() {
		return itr < size ? false : true;
	}
	
	public void add(Student student) {
		if(!isFull()) {
			buffer.add(student);
		}
	}
	public Student getCurrentStudent() {
		return buffer.get(itr);
	}
	public int incItr() {
		return ++itr;
	}
	
	public ArrayList<Student> getBuffer() {
		return buffer;
	}
	public void setBuffer(ArrayList<Student> buffer) {
		this.buffer = buffer;
	}
	public short getItr() {
		return itr;
	}
	public void setItr(short itr) {
		this.itr = itr;
	}
}
