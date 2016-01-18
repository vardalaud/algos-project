package com.uncc.edu.algos.lcs;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 *
 */
public class Util {

	public static void printMatrix(int arr[][]) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println();
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + "\t");
			}
		}
	}
}
