package com.uncc.edu.algos.lcs;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 * 
 */
public class LongestCommonSequenceLinear {
	private static char[] seqOne;
	private static char[] seqTwo;
	private static int[][] editDistTableTop;
	private static int[][] editDistTableBottom;

	/**
	 * @param seqOne
	 * @param seqTwo
	 * @return the LCS of two sequences
	 */
	public static String compute(char[] seqOne, char[] seqTwo) {
		System.out.println("\n\nStarted Computing LCS using linear memory");
		LongestCommonSequenceLinear.seqOne = seqOne;
		LongestCommonSequenceLinear.seqTwo = seqTwo;
		return linearMemoryLCS(1, seqOne.length, 1, seqTwo.length).replace("",
				" ").trim();
	}

	/**
	 * @param top
	 *            - the starting index for seqOne to be considered for this
	 *            recursive call
	 * @param bottom
	 *            - the ending index for seqOne to be considered for this
	 *            recursive call
	 * @param left
	 *            - the starting index for seqTwo to be considered for this
	 *            recursive call
	 * @param right
	 *            - the ending index for seqTwo to be considered for this
	 *            recursive call
	 * @return the character to be appended in LCS or empty string
	 */
	private static String linearMemoryLCS(int top, int bottom, int left,
			int right) {
		// Terminating conditions for recursion, return empty string
		if (top > bottom)
			return "";
		if (left > right)
			return "";

		// Compute the lengths of seqOne and seqTwo
		int seqOneLength = bottom - top + 1;
		int seqTwoLength = right - left + 1;

		// Base Cases

		if (seqOneLength == 1) {
			// If only one element left in seqOne
			boolean flag = false;
			// Check if the one symbol matches with any of the symbols in seqTwo
			for (int i = left; i <= right; i++) {
				if (seqOne[bottom - 1] == seqTwo[i - 1]) {
					flag = true;
					break;
				}
			}
			// If a match is found return the symbol, else return empty string
			if (flag) {
				return seqOne[bottom - 1] + "";
			} else {
				return "";
			}
		} else if (seqTwoLength == 1) {
			// If only one element left in seqTwo
			boolean flag = false;
			// Check if the one symbol matches with any of the symbols in seqOne
			for (int i = top; i <= bottom; i++) {
				if (seqTwo[left - 1] == seqOne[i - 1]) {
					flag = true;
					break;
				}
			}
			// If a match is found return the symbol, else return empty string
			if (flag) {
				return seqTwo[left - 1] + "";
			} else {
				return "";
			}
		}

		// Find the index where to split the matrix horizontally
		int mid = top + ((seqOneLength) / 2) - 1;

		// Instantiate editDistTableTop matrix with rows = 2
		// & columns = number of characters in seq2 + 1
		editDistTableTop = new int[2][seqTwoLength + 1];

		// Initialize the 1st row of matrix from 0 to seqTwo.length
		for (int i = 0; i <= seqTwoLength; i++) {
			editDistTableTop[0][i] = i;
		}

		// Initialize current index to zero. This keeps track of the row for
		// which we are presently computing the edit distance. The possible
		// values it can take are 0 and 1 as the matrix has 2 rows only.
		int currentIndexTop = 0;

		System.out
				.println("\n\nStarting to compute upper half of the matrix\n\nInitialization (currentIndexTop = "
						+ currentIndexTop + ") : ");
		Util.printMatrix(editDistTableTop);

		// For 1st half elements in seqOne i.e. the sequence aligned down the
		// left of the matrix
		for (int i = top; i <= mid; i++) {

			// Toggle the current index between 0 and 1
			if (currentIndexTop == 0) {
				currentIndexTop = 1;
			} else {
				currentIndexTop = 0;
			}

			// Find the value of the 1st cell of the current row which is 1st
			// cell of previous row + 1.
			editDistTableTop[currentIndexTop][0] = editDistTableTop[Math
					.abs(currentIndexTop - 1)][0] + 1;

			// For every element of seqTwo i.e. the sequence aligned across top
			// of the matrix
			for (int j = left; j <= right; j++) {

				// Check if the two symbols in the sequences match
				if (seqOne[i - 1] == seqTwo[j - 1]) {

					// If yes, copy the diagonal top-left (if current index is
					// 1) or diagonal bottom-left (if current index is 0) to the
					// new cell
					editDistTableTop[currentIndexTop][j - left + 1] = editDistTableTop[Math
							.abs(currentIndexTop - 1)][j - left];
				} else {

					// If not, compare the value in the cell to the left of
					// current cell with the value in the cell at the top (if
					// current index is 1) or bottom (if current index is 0) of
					// current cell
					if (editDistTableTop[currentIndexTop][j - left] <= editDistTableTop[Math
							.abs(currentIndexTop - 1)][j - left + 1]) {

						// If the value in the left cell is less then copy that
						// value + 1 in the current cell
						editDistTableTop[currentIndexTop][j - left + 1] = editDistTableTop[currentIndexTop][j
								- left] + 1;
					} else {

						// Else copy the other value + 1 in the current cell
						editDistTableTop[currentIndexTop][j - left + 1] = editDistTableTop[Math
								.abs(currentIndexTop - 1)][j - left + 1] + 1;
					}
				}
			}
			System.out.println("\n\nIntermediate (currentIndexTop = "
					+ currentIndexTop + ") : ");
			Util.printMatrix(editDistTableTop);
		}

		System.out.println("\n\nTermination (currentIndexTop = "
				+ currentIndexTop + ") : ");
		Util.printMatrix(editDistTableTop);

		// Instantiate editDistTableBottom matrix with rows = 2
		// & columns = number of characters in seq2 + 1
		editDistTableBottom = new int[2][seqTwoLength + 1];

		// Initialize the 1st row of matrix from seqTwo.length to 0
		for (int i = 0; i <= seqTwoLength; i++) {
			editDistTableBottom[0][i] = seqTwoLength - i;
		}

		// Initialize current index to zero. This keeps track of the row for
		// which we are presently computing the edit distance. The possible
		// values it can take are 0 and 1 as the matrix has 2 rows only.
		int currentIndexBottom = 0;

		System.out
				.println("\n\nStarting to compute bottom half of the matrix\n\nInitialization (currentIndexBottom = "
						+ currentIndexBottom + ") : ");
		Util.printMatrix(editDistTableBottom);

		// For the 2nd half elements of seqOne i.e. the sequence aligned down
		// the left of the matrix, going in the reverse direction
		for (int i = bottom; i > mid; i--) {

			// Toggle the current index between 0 and 1
			if (currentIndexBottom == 0) {
				currentIndexBottom = 1;
			} else {
				currentIndexBottom = 0;
			}

			// Find the value of the last cell of the current row which is last
			// cell of previous row + 1.
			editDistTableBottom[currentIndexBottom][seqTwoLength] = editDistTableBottom[Math
					.abs(currentIndexBottom - 1)][seqTwoLength] + 1;

			// For every element of seqTwo i.e. the sequence aligned across top
			// of the matrix, going in the reverse direction
			for (int j = right; j >= left; j--) {

				// Check if the two symbols in the sequences match
				if (seqOne[i - 1] == seqTwo[j - 1]) {

					// If yes, copy the diagonal top-right (if current index is
					// 1) or diagonal bottom-right (if current index is 0) to
					// the new cell
					editDistTableBottom[currentIndexBottom][j - left] = editDistTableBottom[Math
							.abs(currentIndexBottom - 1)][j - left + 1];
				} else {

					// If not, compare the value in the cell to the right of
					// current cell with the value in the cell at the top (if
					// current index is 1) or bottom (if current index is 0) of
					// current cell
					if (editDistTableBottom[currentIndexBottom][j - left + 1] <= editDistTableBottom[Math
							.abs(currentIndexBottom - 1)][j - left]) {

						// If the value in the right cell is less then copy that
						// value + 1 in the current cell
						editDistTableBottom[currentIndexBottom][j - left] = editDistTableBottom[currentIndexBottom][j
								- left + 1] + 1;
					} else {

						// Else copy the other value + 1 in the current cell
						editDistTableBottom[currentIndexBottom][j - left] = editDistTableBottom[Math
								.abs(currentIndexBottom - 1)][j - left] + 1;
					}
				}
			}
			System.out.println("\n\nIntermediate (currentIndexBottom = "
					+ currentIndexBottom + ") : ");
			Util.printMatrix(editDistTableBottom);
		}

		System.out.println("\n\nTermination (currentIndexBottom = "
				+ currentIndexBottom + ") : ");
		Util.printMatrix(editDistTableBottom);

		// Initialize lowestSum to addition of 1st element from both forward
		// middle row and reverse middle row
		int lowestSum = editDistTableTop[currentIndexTop][0]
				+ editDistTableBottom[currentIndexBottom][0];
		int lowestSumIndex = 0;
		int currentSum = 0;
		// For the remaining elements in forward and reverse middle rows
		for (int i = 1; i <= seqTwoLength; i++) {
			// Compute the current sum
			currentSum = editDistTableTop[currentIndexTop][i]
					+ editDistTableBottom[currentIndexBottom][i];
			// If new sum is less or equal to old sum, update the new sum and
			// index
			if (currentSum <= lowestSum) {
				lowestSumIndex = i;
				lowestSum = currentSum;
			}
		}

		String lcs = "";

		// Use mid to divide the table horizontally and lowestSumIndex to divide
		// it vertically.

		// Call linearMemoryLCS recursively and send the top left part of the
		// table to it
		lcs = lcs + linearMemoryLCS(top, mid, left, left + lowestSumIndex - 1);

		// Call linearMemoryLCS recursively and send the bottom right part of
		// the table to it
		lcs = lcs
				+ linearMemoryLCS(mid + 1, bottom, left + lowestSumIndex, right);

		return lcs;
	}
}
