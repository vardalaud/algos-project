package com.uncc.edu.algos.lcs;

import java.util.Stack;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 * 
 */
public class LongestCommonSequence {
	/**
	 * @param seqOne
	 * @param seqTwo
	 * @return the LCS of two sequences
	 */
	public static String compute(char[] seqOne, char[] seqTwo) {
		System.out.println("\n\nStarted Computing LCS using full table");

		// Instantiate editDistTable matrix with rows = number of characters
		// in seq1 + 1 & columns = number of characters in seq2 + 1
		int[][] editDistTable = new int[seqOne.length + 1][seqTwo.length + 1];

		// Initialize the 1st row of matrix from 0 to seqTwo.length
		for (int i = 0; i <= seqTwo.length; i++) {
			editDistTable[0][i] = i;
		}
		System.out.println("\n\nInitialization : ");
		Util.printMatrix(editDistTable);

		// For every element of seqOne i.e. the sequence aligned down the left
		// of the matrix
		for (int i = 1; i <= seqOne.length; i++) {

			// Find the value of the 1st cell of the current row which is 1st
			// cell of previous row + 1
			editDistTable[i][0] = editDistTable[i - 1][0] + 1;

			// For every element of seqTwo i.e. the sequence aligned across top
			// of the matrix
			for (int j = 1; j <= seqTwo.length; j++) {

				// Check if the two symbols in the sequences match
				if (seqOne[i - 1] == seqTwo[j - 1]) {

					// If yes, copy the diagonal top-left to the new cell
					editDistTable[i][j] = editDistTable[i - 1][j - 1];
				} else {

					// If not, compare the value in the cell to the left of
					// current cell with the value in the cell at the top of
					// current cell
					if (editDistTable[i][j - 1] <= editDistTable[i - 1][j]) {

						// If the value in the left cell is less then copy that
						// value + 1 in the current cell
						editDistTable[i][j] = editDistTable[i][j - 1] + 1;
					} else {

						// Else copy the other value + 1 in the current cell
						editDistTable[i][j] = editDistTable[i - 1][j] + 1;
					}
				}
			}
		}
		System.out.println("\n\nTermination : ");
		Util.printMatrix(editDistTable);

		Stack<Character> lcsStack = new Stack<Character>();

		// Start from the last symbol of both sequences i.e. the element in the
		// bottom right corner in matrix
		int i = seqOne.length;
		int j = seqTwo.length;

		while (i > 0 && j > 0) {

			// Check if the two symbols in the sequences match
			if (seqOne[i - 1] == seqTwo[j - 1]) {

				// Put the symbol in Stack
				lcsStack.add(seqOne[i - 1]);

				// Move to the top left element
				i--;
				j--;
			} else {

				// If not, compare the value in the cell to the left of
				// current cell with the value in the cell at the top of
				// current cell
				if (editDistTable[i][j - 1] <= editDistTable[i - 1][j]) {

					// If the value in the left cell is less or equal then
					// move to left
					j--;
				} else {

					// Else move to top
					i--;
				}
			}
		}

		String lcs = "";
		// While Stack not empty pop the symbols and append in the LCS
		while (!lcsStack.empty()) {
			lcs = lcs + lcsStack.pop();
		}
		return lcs.replace("", " ").trim();
	}

}
