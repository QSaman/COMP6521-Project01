import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author saman
 *
 */
public class BlockReader {
	private BufferedReader br;

	/**
	 * @throws FileNotFoundException 
	 * 
	 */
	public BlockReader() 
	{
		br = null;
	}
	
	public void open(String file_name) throws FileNotFoundException
	{
		br = new BufferedReader(new FileReader(file_name));
	}
	
	List<Schema> nextBlock() throws IOException
	{
		ArrayList<Schema> ret = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null)
		{
			int i = 0;
			Schema schema = new Schema();
						
			schema.student_id = Integer.parseInt(line.substring(0, Schema.student_id_len));
			i += Schema.student_id_len;
			schema.first_name = line.substring(i, i + Schema.first_name_len).trim();
			i += Schema.first_name_len;
			schema.last_name = line.substring(i, i + Schema.last_name_len).trim();
			i += Schema.last_name_len;
			schema.department = Integer.parseInt(line.substring(i, i + Schema.department_len));
			i += Schema.department_len;
			schema.program = Integer.parseInt(line.substring(i,  i + Schema.program_len));
			i += Schema.program_len;
			schema.sin_number = Integer.parseInt(line.substring(i,  i + Schema.sin_number_len));
			i += Schema.sin_number_len;
			schema.address = line.substring(i, Schema.address_len).trim();
			i += Schema.address_len;
			
			ret.add(schema);
		}
		return ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
