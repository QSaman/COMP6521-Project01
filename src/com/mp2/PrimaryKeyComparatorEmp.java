package com.mp2;

import java.util.Comparator;

public class PrimaryKeyComparatorEmp implements Comparator<byte[]>{
		

	@Override
public int compare(byte[] s1, byte[] s2) {
		
		// TODO Auto-generated method stub
		String tmps1=new String(s1).substring(0, 8).trim();
		String tmps2=new String(s2).substring(0, 8).trim();
		int intStr1;
		int intStr2;
		int lenTmps1;
		int lenTmps2;
		
		
		lenTmps1=tmps1.length();
		lenTmps2=tmps2.length();
		
		if(lenTmps1>lenTmps2){
			return 1;
		}
		
		if(lenTmps1<lenTmps2){
			return -1;
		}
		
		intStr1=Integer.parseInt(tmps1.trim());
		intStr2=Integer.parseInt(tmps2.trim());
		
		if(lenTmps1==lenTmps2){
			if(intStr1>intStr2) {
				return 1;
			}
			
			if(intStr1<intStr2) {
				return -1;
			}
			else {
				return 0;
			}
		}
		return -2;
	}
	
}
