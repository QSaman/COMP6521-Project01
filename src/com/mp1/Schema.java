package com.mp1;

/**
 * 
 */

/**
 * @author saman
 *
 */
public class Schema {
	
	public static final int student_id_len = 8;
	public static final int first_name_len = 10;
	public static final int last_name_len = 10;
	public static final int department_len = 3;
	public static final int program_len = 3;
	public static final int sin_number_len = 9;
	public static final int address_len = 57;
	
	public int student_id;
	public String first_name;
	public String last_name;
	public int department;
	public int program;
	public int sin_number;
	public String address;		

	/**
	 * 
	 */
	public Schema() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("studen id: ").append(student_id).append(", first name: ").append(first_name);
		sb.append(", last name: ").append(last_name).append(", department: ").append(department);
		sb.append(", program: ").append(program).append(", sin number: ").append(sin_number);
		sb.append(", address: ").append(address);
		return sb.toString();
	}

}
