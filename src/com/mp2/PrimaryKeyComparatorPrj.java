package com.mp2;

import java.util.Comparator;

public class PrimaryKeyComparatorPrj implements Comparator<byte[]>{
	
	public int compare(byte[] s1, byte[] s2) {
		
		
		String sTmp1 = new String(s1);
		String sTmp2 = new String(s2);
		
		String tmps1=sTmp1.substring(0, 8).trim();
		String tmps2=sTmp2.substring(0, 8).trim();
		
		String tmpP1=sTmp1.substring(8, 16).trim();
		String tmpP2=sTmp2.substring(8, 16).trim();
		
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
		
		if(lenTmps1==lenTmps2){
			intStr1=Integer.parseInt(tmps1.trim());
			intStr2=Integer.parseInt(tmps2.trim());
			if(intStr1>intStr2) {				
				return 1;
			}			
			else if(intStr1<intStr2) {
				return -1;
			}
			else {//if(intStr1==intStr2)
				 if (comparePrjKey(tmpP1, tmpP2)==1){
					return 1;
				 }else if (comparePrjKey(tmpP1, tmpP2)==-1){
					return -1;
				 }else{
					 return 0;
				 }
			}
		}
		return -2;
	}
	
	private int comparePrjKey(String p1, String p2){
		
		int lenP1;
		int lenP2;
		
		lenP1=p1.length();
		lenP2=p2.length();
		
		if(lenP1>lenP2){
			return 1;
		}
		
		if(lenP1<lenP2){
			return -1;
		}
		
		int check = p1.trim().compareTo(p2.trim());
		
		if(lenP1==lenP2){
			if(check > 0) {
				return 1;
			}
			
			if(check < 0) {
				return -1;
			}
			else {
				return 0;
			}
		}
		
		
		return 0;
	}
}
