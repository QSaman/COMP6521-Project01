package com.mp2;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class JoinFiles {

	String empfileNameWithPath;
	String prjfileNameWithPath;	
	String joinfileNameWithPath;
	
	Scanner empScanner;
	Scanner prjScanner;
	PrintWriter joinFilePrntWrtr;
	
	long numJoinLine=0;
	
	public long getNumJoinLine() {
		return numJoinLine;
	}

	public void setNumJoinLine(long numJoinLine) {
		this.numJoinLine = numJoinLine;
	}
	
	public JoinFiles(String empfileNameWithPath,
					 String prjfileNameWithPath,
					 String joinfileNameWithPath){
		
		
		this.empfileNameWithPath=empfileNameWithPath;
		this.prjfileNameWithPath=prjfileNameWithPath;
		this.joinfileNameWithPath=joinfileNameWithPath;
		
		try{
			empScanner=new Scanner(new FileInputStream(empfileNameWithPath));
			prjScanner=new Scanner(new FileInputStream(prjfileNameWithPath));
			joinFilePrntWrtr=new PrintWriter(joinfileNameWithPath);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void join(){
		
		String crrEmp="";
		String crrPrj="";
		int crrEmpIDinPrj=0;
		int crrEmpIDinEmp=0;
		
		String crrPrjID="";
		
		double numEmpLine=0;
		double numPrjLine=0;
		
		
		boolean empNextLine=true;
		boolean prjNextLine=true;
		StringBuilder joinTuple=new StringBuilder();
		do {
			
			if(prjNextLine){
				crrPrj = prjScanner.nextLine();
				numPrjLine++;
				crrEmpIDinPrj= Integer.parseInt(crrPrj.substring(0, 8).trim()) ;
				crrPrjID= crrPrj.substring(8, 16).trim() ;
			}
			while(empScanner.hasNextLine()){
				if(empNextLine){
					crrEmp=empScanner.nextLine();					
					crrEmpIDinEmp=Integer.parseInt(crrEmp.substring(0, 8).trim());
					numEmpLine++;
				}
				
				if(crrEmpIDinPrj>crrEmpIDinEmp ){					
					empNextLine=true;
					prjNextLine=false;
					continue;
				}
				
				if(crrEmpIDinPrj==crrEmpIDinEmp){
					
					joinTuple.append(crrEmpIDinPrj).append("------").
							  append(crrPrjID).append("******").
							  append(crrPrj).append("******").append(crrEmp);			
					joinFilePrntWrtr.println(joinTuple);
					joinTuple.setLength(0);
					numJoinLine++;	
					empNextLine=false;
					prjNextLine=true;
					break;
				}
			}
			
		}while(prjScanner.hasNextLine());
		
		System.out.print("Number Of I/O to create Join File : ");
		System.out.println((long)(Math.ceil(numEmpLine/40) + Math.ceil(numPrjLine/130)+Math.ceil(numJoinLine/20)));
		System.out.println("--------------------------------------------------------");
		System.out.println("Number Of Blocks Output File : " + (long)Math.ceil(numJoinLine/20));
		System.out.println("--------------------------------------------------------");
		joinFilePrntWrtr.close();
	}
	
	
	
}
