package com.mp2;

import java.io.File;

public class MakeDirectory {
	String folderName;
	
		public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

		public MakeDirectory(String folderName){
			this.folderName=folderName;
		}
		
	    public void checkDirectory(){
	    	File file = new File(folderName);
	    	if (!(file.mkdir())) {
	    	       deleteDir(file); 
	    	       file.mkdir();       
	    	}
	    }
		
	    public void deleteDir(File dir) {
		    File[] files = dir.listFiles();
		    for (File myFile: files) {
		        if (myFile.isDirectory()) {  
		            deleteDir(myFile);
		        } 
		        myFile.delete();
		    }
		}	
}
