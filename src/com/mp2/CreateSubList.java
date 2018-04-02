package com.mp2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.mp2.JoinFiles.JoinMode;

public class CreateSubList {

	FileInputStream fileIn;
	
	String inpfileNameWithPath;
	public String getInpfileNameWithPath() {
		return inpfileNameWithPath;
	}


	public void setInpfileNameWithPath(String inpfileNameWithPath) {
		this.inpfileNameWithPath = inpfileNameWithPath;
	}



	String outfilePath;
	String inpfileName;
	int tupleSize;
	int readIO=0;
	int maxFileSize=2000000;
	public int getMaxFileSize() {
		return maxFileSize;
	}


	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}


	public int getReadIO() {
		return readIO;
	}


	public void setReadIO(int readIO) {
		this.readIO = readIO;
	}



	int recordNumber=0;
	
	public int getRecordNumber() {
		return recordNumber;
	}


	public CreateSubList(String inpfileNameWithPath,
			                  String outFilePath){
		try {
			
			this.inpfileNameWithPath=inpfileNameWithPath;
			this.outfilePath=outFilePath;
			fileIn=new FileInputStream(this.inpfileNameWithPath);
			inpfileName=inpfileNameWithPath.substring(inpfileNameWithPath.lastIndexOf("/")+1);
			
			RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
			List<String> jvmArgs =  runtimeMXBean.getInputArguments();
			if(jvmArgs.contains("-Xmx5m"))
			{
				setMaxFileSize(1800000);
			}else if(jvmArgs.contains("-Xmx10m")){
				setMaxFileSize(2500000);
			}else{
				setMaxFileSize(2000000);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void readFile(int tupleSize,Comparator<byte[]> cmptr, JoinMode mode){
		
		ArrayList<byte[]> arrLst=new ArrayList<byte[]>();
		
		int fileSize=0;	
		int fileNumber=0;
		
				
		int blockSize=4000;				
		
		Scanner fileScanner= new Scanner(fileIn);
		try{
			
			while(fileScanner.hasNextLine()){
				
				getBlock(fileScanner,tupleSize, arrLst);
				readIO+=1;
				
				fileSize+=blockSize;
				
//				maxFileSize
				
				if(fileSize>=maxFileSize || !(fileScanner.hasNextLine())){
					if(mode == JoinMode.SORT_BASED) {
						Collections.sort(arrLst, cmptr);
					}
					fileNumber+=1;
					writeToFile(inpfileName,fileNumber,arrLst);
					recordNumber=arrLst.size()+recordNumber;
					arrLst.clear();
					fileSize=0;	
				}	
			}
			
		}catch(Exception e){			
			e.printStackTrace();			
		}
		fileScanner.close();
		System.out.println("Number Of I/O to create sublists of " + getInpfileNameWithPath() + ":" + (2* getReadIO()));
		System.out.println("--------------------------------------------------------");
	}

	private void getBlock(Scanner fileScanner,int maxLineSize, List<byte[]> arr){
		
//		int sizeInByte=4000;
		int tupleNum= 0;
		
		switch (maxLineSize){
			case 100:
				tupleNum=40;
				break;
			case 27:
				tupleNum=130;
				break;
			default:
				tupleNum=40;
				break;					
		}
				
		for(int i=1;i<=tupleNum && fileScanner.hasNextLine();i++){
			arr.add(fileScanner.nextLine().getBytes());
		}
	}
	
	private void writeToFile(String filename,int prefix,ArrayList<byte[]> arrLst){
		
		String tmpfileNameWithPath=this.outfilePath + "//" + prefix + "-" + filename;
		try {
			PrintWriter pw= new PrintWriter(tmpfileNameWithPath);
			for(byte[] str:arrLst){
				pw.println(new String(str));
			}
			
			pw.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		
		int empRecordNumber=0;
		int prjRecordNumber=0;
		String currentPath = System.getProperty("user.dir");
		
		String inputFileDirectory= currentPath+"/ADBInputFiles/";
		String outputFileDirectory= currentPath+"/ADBOutputFiles/";
		
		String empInFile="JoinT1.txt";
		String empOutFile="JoinT1-out.txt";
		
		String prjInFile="JoinT2.txt";
		String prjOutFile="JoinT2-out.txt";
		
		String joinInFile="JoinFile-in.txt";
		String gpaOutFile = "GPA.txt";
		
		String sublistFileDirectoryEmp= currentPath+ "/sublistT1";		
		String sublistFileDirectoryPrj= currentPath+"/sublistT2";

		long BeginTimeEmp=0;
		long EndTimeEmp=0;
		long BeginTime=0;
				
		long BeginTimePrj=0;
		long EndTimePrj=0;
		
		long BeginTimeEmpMerge=0;
		long EndTimeEmpMerge=0;
		
		long BeginTimePrjMerge=0;
		long EndTimePrjMerge=0;
		
		long BeginTimeJoin=0;
		long EndTimeJoin=0;
		
		long BeginTotalSortTime=0;
		long EndTotalSortTime=0;
		
		long BeginTotalSortJoinTime=0;
		long EndTotalSortJoinTime=0;
		
		// set up mode either nested_join_loop or sort_based algo
		JoinMode mode = JoinMode.SORT_BASED;
		JoinFiles jf=null;
		
		try {	
		// TODO Auto-generated method stub
			     
		MakeDirectory mDir=new MakeDirectory(sublistFileDirectoryEmp);
		mDir.checkDirectory();
		
		mDir.setFolderName(sublistFileDirectoryPrj);
		mDir.checkDirectory();
		
		mDir.setFolderName(outputFileDirectory);
		mDir.checkDirectory();
		
		System.out.println("Press Enter To Start");
		@SuppressWarnings("resource")
		Scanner scnr= new Scanner(System.in);
		scnr.nextLine();
		BeginTime = System.currentTimeMillis();
		
		BeginTotalSortJoinTime= System.currentTimeMillis();
		
		BeginTotalSortTime= System.currentTimeMillis();
		
		BeginTimeEmp = System.currentTimeMillis();
		
		CreateSubList csbEmp=new CreateSubList(inputFileDirectory+empInFile, sublistFileDirectoryEmp);		
		csbEmp.readFile(100,new PrimaryKeyComparatorEmp(),mode);
		empRecordNumber= csbEmp.getRecordNumber()-1;
		
		EndTimeEmp = System.currentTimeMillis();

		
		BeginTimePrj = System.currentTimeMillis();
		
		CreateSubList csbPrj=new CreateSubList(inputFileDirectory+prjInFile, sublistFileDirectoryPrj);
		csbPrj.readFile(27,new PrimaryKeyComparatorPrj(),mode);		
		prjRecordNumber= csbPrj.getRecordNumber()-1;
		
		EndTimePrj = System.currentTimeMillis();
		
		
		MergeSubList mg=new MergeSubList();
		BeginTimeEmpMerge = System.currentTimeMillis();
		mg.merge(empRecordNumber, outputFileDirectory ,empOutFile, sublistFileDirectoryEmp,1);
		EndTimeEmpMerge = System.currentTimeMillis();
		
		BeginTimePrjMerge = System.currentTimeMillis();
		mg.merge(prjRecordNumber, outputFileDirectory ,prjOutFile, sublistFileDirectoryPrj,2);
		EndTimePrjMerge = System.currentTimeMillis();
		
		EndTotalSortTime= System.currentTimeMillis();
		
		BeginTimeJoin = System.currentTimeMillis();
		jf= new JoinFiles(outputFileDirectory+empOutFile, 
									outputFileDirectory+prjOutFile, 
									outputFileDirectory+joinInFile,
									outputFileDirectory+gpaOutFile,
									mode);
		jf.join();
		EndTimeJoin = System.currentTimeMillis();						
		
		EndTotalSortJoinTime= System.currentTimeMillis();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Create subLists T1 Time(ms):"+ (EndTimeEmp - BeginTimeEmp));
		System.out.println("--------------------------------------------------------");
		System.out.println("Create subLists T2 Time(ms):"+ (EndTimePrj - BeginTimePrj));
		System.out.println("--------------------------------------------------------");
		System.out.println("Merge T1 Time(ms):"+ (EndTimeEmpMerge - BeginTimeEmpMerge));
		System.out.println("--------------------------------------------------------");
		System.out.println("Merge T2 Time(ms):"+ (EndTimePrjMerge - BeginTimePrjMerge));
		System.out.println("--------------------------------------------------------");
		System.out.println("Join Relations Time(ms):"+ (EndTimeJoin - BeginTimeJoin));
		System.out.println("");	
		System.out.println("Number Tuples In Join File : " + jf.getNumJoinLine());
		System.out.println("--------------------------------------------------------");
		
		System.out.println("--------------------------------------------------------");
		System.out.print("Total sort Time(minute):");
		System.out.printf("%.2f", ((float)(EndTotalSortTime-BeginTotalSortTime)/(float)(1000*60)));
		System.out.println();
		System.out.println("--------------------------------------------------------");
		System.out.print("Total sort Time(ms):");
		System.out.println((EndTotalSortTime-BeginTotalSortTime));
		
		System.out.println("--------------------------------------------------------");
		System.out.print("Total sort plus join Time(second):");
		System.out.printf("%.4f" ,((float)(EndTotalSortJoinTime-BeginTotalSortJoinTime)/(float)(1000)));
		System.out.println();
		System.out.println("--------------------------------------------------------");
		long endTime = System.currentTimeMillis();		
		System.out.println(("Total Execution Time(ms):" + (endTime-BeginTime)));
	}
	

}
