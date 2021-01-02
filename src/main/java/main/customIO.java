package main;

import java.util.Scanner;

public class customIO {
	Scanner scannerFile;
	String scannerToString;
	
	customIO(Scanner scannerFile) {
		this.scannerFile = scannerFile;
		scannerToString = "";
	}
	
	@Override
	public String toString() {
		String tempString  = "";
		while (scannerFile.hasNext()) {
			tempString += scannerFile.nextLine() + "\n";
		}
		scannerToString  = tempString;
		return scannerToString;
	}
	
	
	
	
}