package com.mp2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MergeSubList {
	String inputDirectory;
	String inputFileName;
	String outputFileName;
	String outputDirectory;

	public int merge(int numOfTuples, String outputDirectory, String outputFileName, String inputDirectory,int key)
			throws IOException {

		String absInpPathSubLists, minElementFile, value, tupleContent;
		int empID;
		String prjID="";
		int maxValue = Integer.MAX_VALUE;
		String strMaxValue=Integer.toString(Integer.MAX_VALUE);
		String rrr;

		File inpDir = new File(inputDirectory);
		absInpPathSubLists = inpDir.getAbsolutePath();

		File folder = new File(absInpPathSubLists);
		File[] arrTempFiles = folder.listFiles();

		int numOfSublist = arrTempFiles.length;

		int minimum, subList = 0;
		String minimumPrj ="";

		int[] minEmpID = new int[numOfSublist];
		String[] minPrjID = new String[numOfSublist];
		
		String[] minNumberTuple = new String[numOfSublist];

		Scanner[] scnr = new Scanner[numOfSublist];

		PrintWriter tuple = new PrintWriter(outputDirectory + outputFileName);

		for (int i = 0; i < numOfSublist; i++) {
			scnr[i] = new Scanner(arrTempFiles[i]);

			value = scnr[i].nextLine();

			empID = Integer.parseInt(value.substring(0, 8).trim());
			
			if(key==2)
				prjID=value.substring(8,16).trim();
				
			if (value != null) {
				minEmpID[i] = empID;
				minNumberTuple[i] = value;
				
				if(key==2){
					minPrjID[i] = prjID;					
				}
					
			}
		}

		for (int i = 0; i <= numOfTuples; i++) {
			minimum = minEmpID[0];
			tupleContent = minNumberTuple[0];
			subList = 0;
			
			if(key==2)
				minimumPrj=minPrjID[0];
			
			for (int j = 0; j < numOfSublist; j++) {
				if(key==1){
					if (minimum > minEmpID[j]) {
						minimum = minEmpID[j];
						tupleContent = minNumberTuple[j];
						subList = j;
					}
				}
				if(key==2){
					if (minimum > minEmpID[j]) {
						minimum = minEmpID[j];
						tupleContent = minNumberTuple[j];
						subList = j;
						minimumPrj=minPrjID[j];
					}
					if (minimum == minEmpID[j]) {
						if(minimumPrj.compareTo(minPrjID[j]) > 0){
							
							minimum = minEmpID[j];
							minimumPrj=minPrjID[j];
							tupleContent = minNumberTuple[j];
							subList = j;
							
						}
						
					}
					
				}
			}
			
			if(!tupleContent.equalsIgnoreCase(strMaxValue))
				tuple.println(tupleContent);

			if (scnr[subList].hasNextLine()) {
				
				rrr = scnr[subList].nextLine();
				if (rrr != null) {

					minElementFile = rrr;

					minEmpID[subList] = Integer.parseInt(minElementFile.substring(0, 8).trim());
					minNumberTuple[subList] = minElementFile;
					
					if(key==2){
						minPrjID[subList] = minElementFile.substring(8,16).trim();
					}
					
				} else {
					minEmpID[subList] = maxValue;
					minNumberTuple[subList] = strMaxValue; //String.valueOf(maxValue);
					minPrjID[subList] = strMaxValue;
				}
			} else {
				minEmpID[subList] = maxValue;
				minNumberTuple[subList] = strMaxValue;//String.valueOf(maxValue);
				minPrjID[subList] = strMaxValue;
			}
		}

		for (int i = 0; i < numOfSublist; i++) {
			scnr[i].close();
			tuple.close();
		}
		return 0;
	}

}
