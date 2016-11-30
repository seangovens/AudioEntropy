package project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class fileManager {
	
	public static void writeFile(ByteArrayOutputStream in, String filename){
		try(OutputStream outputStream = new FileOutputStream(filename)) {
		    in.writeTo(outputStream);
		    in.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] readFile(String filename){
		FileInputStream fileInputStream = null;
		
		File file = new File(filename);
		
		byte[] file_bytes = new byte[(int) file.length()];
		
		try {
            //convert file into array of bytes
	    fileInputStream = new FileInputStream(file);
	    fileInputStream.read(file_bytes);
	    fileInputStream.close();

	    /*for (int i = 0; i < file_bytes.length; i++) {
	       	System.out.print((char)file_bytes[i]);
        }*/

	    System.out.println("Done");
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		return file_bytes;
	}
}
