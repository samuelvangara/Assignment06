package com.java.Assignment6;

import java.util.Arrays;
import java.util.Scanner;

public class Problem4 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter length of array");
		int size = sc.nextInt();
		int a[] = new int[size];
		System.out.println("Enter elements into array");
		for(int i=0; i<=a.length-1; i++){
			a[i]=sc.nextInt();
		}
		sc.close();
		Arrays.sort(a);
		System.out.println("Second Heighest Number of an array: "+a[a.length-2]);
	}

}
