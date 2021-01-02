package BitOwlBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Tests the Scann.er.hasNext() feature from a file.
public class scannerTester {
	private static File testFile = new File("/home/thetechowl/Documents/BitOwlBot/src/test/java/BitOwlBot/scannerTestFile.txt");
	private static Scanner fileScanner;
	
	public static void main(String[] args) throws FileNotFoundException {
		fileScanner = new Scanner(testFile);
		while (fileScanner.hasNext()) {
			System.out.println(fileScanner.nextLine());
		}
	}
}
