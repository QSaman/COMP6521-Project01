/**
 * 
 */
package com.mp1;

import java.util.ArrayList;

import javax.naming.SizeLimitExceededException;

/**
 * @author saman
 * This class represent a block in main memory. It can have at most 40 tuples
 */
public class MemoryBuffer {
	
	private ArrayList<Student> students;

	/**
	 * 
	 */
	public MemoryBuffer() {
		students = new ArrayList<>();
	}
	
	public void add(Student student) throws SizeLimitExceededException
	{
		if (students.size() < 40)
			students.add(student);
		else
			throw new SizeLimitExceededException("Memory buffer cannot have more than 40 tuples");
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

}
