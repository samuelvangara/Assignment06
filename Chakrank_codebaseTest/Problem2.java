package com.java.Assignment6;

import java.util.Scanner;

public class Problem2 {

	public static void main(String[] args) {
		int n;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a number");
		n = sc.nextInt();
		sc.close();
		for(int i=1; i<=10;){
			for(int j=1; j<=10;j++){
				System.out.println(n+"*"+i+"*"+j+"="+(n*i*j));	
			}break;
		}
		for(int i=2;i<=10; i++){
			int j=10;
			System.out.println(n+"*"+i+"*"+j+"="+(n*i*j));
		}
	}

}
