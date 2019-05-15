package com.example.demo;

import com.hellolife.sys.pub.Calculator;
import com.hellolife.sys.pub.pubfunction;

import java.util.ArrayList;
import java.util.List;

public class DemoApplicationTests {
	public static void main(String[] args) {
		char[] a = new char[]{'p','j','a','v','a','p','y','c','p'};
		lowercaseToUppercase(a,25);
		System.out.println(new String(a));
	}

	public static void lowercaseToUppercase(char[] str, int offset) {
		// write your code here
		if(offset==0) return;
		int len=str.length;
		if(len==0)return;
		int i = offset%len;
		if(i==0) return;
		for(int m = 0;m<i;m++){
			char temp = str[len-1];
			int j=len-2;
			while(j>=0){
				str[j+1]=str[j];
				j--;
			}
			str[0]=temp;
		}
	}
}
