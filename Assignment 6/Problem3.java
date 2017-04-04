package com.java.Assignment6;

import java.util.Arrays;
import java.util.Scanner;

public class Problem3 {

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
		System.out.println("Sorted Numbers");
		for(int i=0; i<=a.length-1; i++){
			System.out.println(a[i]);
		}
	}

}
