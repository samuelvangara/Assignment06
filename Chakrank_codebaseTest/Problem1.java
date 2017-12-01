package com.java.Assignment6;

import java.util.Scanner;

public class Problem1 {

	public static void main(String[] args) {
		int n;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a number");
		n = sc.nextInt();
		sc.close();
		for(int i=1; i<=10; i++){
			System.out.println(n+"*"+i+"="+(n*i));
		}
	}

}
