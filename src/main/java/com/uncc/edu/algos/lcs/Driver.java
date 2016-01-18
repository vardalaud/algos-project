package com.uncc.edu.algos.lcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Varda Laud 800894577 vlaud@uncc.edu
 *
 */
public class Driver {
	// The two sequences from the files are read in seqOne & seqTwo
	private static char[] seqOne;
	private static char[] seqTwo;
	// Used to read the characters from file
	private static int c;
	// Used to keep track of index in file
	private static int fileIndex;

	public static void main(String[] args) {
		try {
			if (args.length == 3) {
				// Read 1st file name
				String srcFileName = args[0];
				InputStream seqOneStream = new FileInputStream(new File(
						srcFileName));
				fileIndex = 0;
				// Instantiate seqOne array with length = number of characters
				// in the file
				seqOne = new char[seqOneStream.available()];
				// Copy the characters in File to the seqOne array one by one
				while ((c = seqOneStream.read()) != -1) {
					seqOne[fileIndex++] = (char) c;
				}
				seqOneStream.close();
				System.out.print("Sequence One : ");
				System.out.print(seqOne);

				// Read 2nd file name
				srcFileName = args[1];
				InputStream seqTwoStream = new FileInputStream(new File(
						srcFileName));
				fileIndex = 0;
				// Instantiate seqTwo array with length = number of characters
				// in the file
				seqTwo = new char[seqTwoStream.available()];
				// Copy the characters in File to the seqTwo array one by one
				while ((c = seqTwoStream.read()) != -1) {
					seqTwo[fileIndex++] = (char) c;
				}
				seqTwoStream.close();
				System.out.print("\n\nSequence Two : ");
				System.out.print(seqTwo);

				// Call the algorithm for normalized edit distance
				String ned = NormalizedEditDistance.compute(seqOne, seqTwo);
				System.out.println("\n\nNormalized Edit Distance : " + ned);
				// Call the LCS algorithm using full table
				String lcsFull = LongestCommonSequence.compute(seqOne, seqTwo);
				System.out.println("\n\nLCS (using full table) : " + lcsFull);
				// Call the LCS algorithm using linear memory
				String lcsLinear = LongestCommonSequenceLinear.compute(seqOne,
						seqTwo);
				System.out.println("\n\nLCS (using linear memory) : "
						+ lcsLinear);

				System.out.println("\n\nFinal Output :\n" + ned + "\n"
						+ lcsFull + "\n" + lcsLinear);

				// Read target file name
				String targetFileName = args[2];
				OutputStreamWriter targetWriter = new OutputStreamWriter(
						new FileOutputStream(new File(targetFileName)));
				// Write the three results
				targetWriter.write(ned + "\n");
				targetWriter.write(lcsFull + "\n");
				targetWriter.write(lcsLinear);
				targetWriter.close();
			} else {
				throw new RuntimeException("Invalid number of arguments");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("\n\n" + e.getMessage() + "\n\n");
		} catch (Exception e) {
			System.err.println("\n\n" + e.getMessage() + "\n\n");
			e.printStackTrace();
		}
	}
}
