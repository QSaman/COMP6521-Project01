/**
 * 
 */
package com.mp1.buffer;

import com.mp1.schema.Student;

import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author saman
 * This class represent a block in main memory. It can have at most 40 tuples
 */
public class MemoryBuffer {
	
	private ArrayList<Student> students;
	private boolean done;

	/**
	 * 
	 */
	public MemoryBuffer() {
		students = new ArrayList<>();
		clear();
	}
	
	public void add(Student student) throws SizeLimitExceededException
	{
		if (students.size() < 40)
			students.add(student);
		else
			throw new SizeLimitExceededException("Memory buffer cannot have more than 40 tuples");
	}
	
	public boolean isFull()
	{
		return students.size() == 40;
	}
	
	public void sort()
	{
		Collections.sort(students);
	}
	
	public int size()
	{
		return students.size();
	}
	
	public void clear()
	{
		students.clear();
		done = false;
	}
	
	public Student get(int i)
	{
		return students.get(i);
	}
	
	@Override
	public String toString()
	{
		String ret = new String();
		for (Student s : students)
			ret += s + "\n";
		return ret;
	}

	/**
	 * @return the done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * @param done the done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}		
}
