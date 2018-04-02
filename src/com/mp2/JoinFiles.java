package com.mp2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class JoinFiles {
	
	HashMap<String, Float> Gpa;
	float currGpa = 0;
	int countGpa = 0;
	PrintWriter gpaFileWrtr;
	
	public enum JoinMode {NESTED_JOIN_LOOP, SORT_BASED};
	private JoinMode mode;
	
	String empfileNameWithPath;
	String prjfileNameWithPath;	
	String joinfileNameWithPath;
	String gpafileNameWithPath;
	
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
					 String joinfileNameWithPath,
					 String gpafileNameWithPath,
					 JoinMode mode){
		
		
		this.empfileNameWithPath=empfileNameWithPath;
		this.prjfileNameWithPath=prjfileNameWithPath;
		this.joinfileNameWithPath=joinfileNameWithPath;
		this.gpafileNameWithPath = gpafileNameWithPath;
		
		this.mode = mode;
		
		Gpa = new HashMap<String, Float>();
		initializeGPA();
		
		try{
			empScanner=new Scanner(new FileInputStream(empfileNameWithPath));
			prjScanner=new Scanner(new FileInputStream(prjfileNameWithPath));
			joinFilePrntWrtr=new PrintWriter(joinfileNameWithPath);
			gpaFileWrtr = new PrintWriter(gpafileNameWithPath);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void initializeGPA() {
		Gpa.put("A+", 4.3f);
		Gpa.put("A", 4f);
		Gpa.put("A-", 3.7f);
		Gpa.put("B+", 3.3f);
		Gpa.put("B", 3f);
		Gpa.put("B-", 2.7f);
		Gpa.put("C+", 2.3f);
		Gpa.put("C", 2f);
		Gpa.put("C-", 1.7f);
		Gpa.put("D+", 1.3f);
		Gpa.put("D", 1f);
		Gpa.put("D-", 0.7f);
		Gpa.put("Fail", 0f);
	}
	
	public void join() throws FileNotFoundException{
		
		String crrEmp="";
		String crrPrj="";
		int crrEmpIDinPrj=-1;
		int crrEmpIDinEmp=-1;
				
		double numEmpLine=0;
		double numPrjLine=0;
		
		
		boolean empNextLine=true;
		boolean prjNextLine=true;
		StringBuilder joinTuple=new StringBuilder();
		do {
			
			if(empNextLine){
				crrEmp = empScanner.nextLine();
				numEmpLine++;
				crrEmpIDinEmp= Integer.parseInt(crrEmp.substring(0, 8).trim()) ;
				if(mode == JoinMode.NESTED_JOIN_LOOP) {
					prjScanner.close();
					prjScanner = new Scanner(new FileInputStream(prjfileNameWithPath));
				}
				
			}
			while(prjScanner.hasNextLine()){
				if(crrEmpIDinPrj==crrEmpIDinEmp){
					if(mode == JoinMode.SORT_BASED) {
						int creditValue = Integer.parseInt(crrPrj.substring(21,23).trim());
						currGpa += (float)(Gpa.get(crrPrj.substring(23).trim()) * creditValue);
						countGpa += creditValue;
					}
					joinTuple.append(crrPrj).append("******").append(crrEmp);			
					joinFilePrntWrtr.println(joinTuple);
					joinTuple.setLength(0);
					numJoinLine++;	
				}
				
				if(prjNextLine){
					crrPrj=prjScanner.nextLine();					
					crrEmpIDinPrj=Integer.parseInt(crrPrj.substring(0, 8).trim());
					numPrjLine++;
				}
				
				if(mode == JoinMode.SORT_BASED) {
					if(crrEmpIDinPrj<crrEmpIDinEmp ){					
						empNextLine=false;
						prjNextLine=true;
						continue;
					}
					
					if(crrEmpIDinPrj>crrEmpIDinEmp ){					
						empNextLine=true;
						prjNextLine=true;
						gpaFileWrtr.println(crrEmpIDinEmp+"    "+(currGpa/(float)countGpa));
						countGpa = 0;
						currGpa = 0;
						break;
					}
				}
			}
			
		}while(empScanner.hasNextLine());
		
		System.out.print("Number Of I/O to create Join File : ");
		System.out.println((long)(Math.ceil(numEmpLine/40) + Math.ceil(numPrjLine/130)+Math.ceil(numJoinLine/20)));
		System.out.println("--------------------------------------------------------");
		System.out.println("Number Of Blocks Output File : " + (long)Math.ceil(numJoinLine/20));
		System.out.println("--------------------------------------------------------");
		joinFilePrntWrtr.close();
		gpaFileWrtr.close();
		empScanner.close();
		prjScanner.close();
	}
	
	
	
}
