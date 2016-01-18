package com.uncc.edu.algos.lcs;

import java.text.DecimalFormat;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 * 
 */
public class NormalizedEditDistance {
	/**
	 * @param seqOne
	 * @param seqTwo
	 * @return the Normalized Edit Distance between two sequences
	 */
	public static String compute(char[] seqOne, char[] seqTwo) {
		System.out.println("\n\nStarted Computing Normalized Edit Distance");

		// Instantiate editDistTable matrix with rows = 2
		// & columns = number of characters in seq2 + 1
		int[][] editDistTable = new int[2][seqTwo.length + 1];

		// Initialize the 1st row of matrix from 0 to seqTwo.length
		for (int i = 0; i <= seqTwo.length; i++) {
			editDistTable[0][i] = i;
		}

		// Initialize current index to zero. This keeps track of the row for
		// which we are presently computing the edit distance. The possible
		// values it can take are 0 and 1 as the matrix has 2 rows only
		int currentIndex = 0;

		System.out.println("\n\nInitialization (currentIndex = " + currentIndex
				+ ") : ");
		Util.printMatrix(editDistTable);

		// For every element of seqOne i.e. the sequence aligned down the left
		// of the matrix
		for (int i = 1; i <= seqOne.length; i++) {

			// Toggle the current index between 0 and 1
			if (currentIndex == 0) {
				currentIndex = 1;
			} else {
				currentIndex = 0;
			}

			// Find the value of the 1st cell of the current row which is 1st
			// cell of previous row + 1
			editDistTable[currentIndex][0] = editDistTable[Math
					.abs(currentIndex - 1)][0] + 1;

			// For every element of seqTwo i.e. the sequence aligned across top
			// of the matrix
			for (int j = 1; j <= seqTwo.length; j++) {

				// Check if the two symbols in the sequences match
				if (seqOne[i - 1] == seqTwo[j - 1]) {

					// If yes, copy the diagonal top-left (if current index is
					// 1) or diagonal bottom-left (if current index is 0) to the
					// new cell
					editDistTable[currentIndex][j] = editDistTable[Math
							.abs(currentIndex - 1)][j - 1];
				} else {

					// If not, compare the value in the cell to the left of
					// current cell with the value in the cell at the top (if
					// current index is 1) or bottom (if current index is 0) of
					// current cell
					if (editDistTable[currentIndex][j - 1] <= editDistTable[Math
							.abs(currentIndex - 1)][j]) {

						// If the value in the left cell is less then copy that
						// value + 1 in the current cell
						editDistTable[currentIndex][j] = editDistTable[currentIndex][j - 1] + 1;
					} else {

						// Else copy the other value + 1 in the current cell
						editDistTable[currentIndex][j] = editDistTable[Math
								.abs(currentIndex - 1)][j] + 1;
					}
				}
			}
			System.out.println("\n\nIntermediate (currentIndex = "
					+ currentIndex + ") : ");
			Util.printMatrix(editDistTable);
		}
		System.out.println("\n\nTermination (currentIndex = " + currentIndex
				+ ") : ");
		Util.printMatrix(editDistTable);

		// Calculate edit distance = m + n - d / m + n
		int totalLength = seqOne.length + seqTwo.length;
		float editDistance = (float) (totalLength - editDistTable[currentIndex][seqTwo.length])
				/ totalLength;
		DecimalFormat df = new DecimalFormat("#.######");
		return df.format(editDistance);
	}
}
