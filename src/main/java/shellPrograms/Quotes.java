package shellPrograms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.Scanner;

import exceptions.IterationLimitReachedException;
import interfaces.InfoRetrieveable;
import interfaces.Loadable;
import main.commandShell;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Quotes extends ShellPrograms implements Loadable, InfoRetrieveable {
	private File sourceFile;
	private String SOURCE_DIRECTORY = GET_DATA_PATH() + "quotes.bof";
	private Scanner fileScanner;
	private String[][] data;
	private int categoryCount; // Count of different categories that can be invoked.
	private int maxQuote; // The highest amount of quote a category has/
	//private boolean handlingNullIndex = false; //indicates whether program is handling null index.
	private String errorStack;

	public Quotes(GuildMessageReceivedEvent e, String cmdArgs) {
		super(e, cmdArgs);
		sourceFile = new File(SOURCE_DIRECTORY);
		categoryCount = 0;
	}

	@Override
	public void start() throws NullPointerException {
		updateData(); // Extracts data from data file to program.
		System.out.println("\nupdateData method finished executing.");
		String quote = "";
		event.getChannel().sendTyping().queue();
		try {
			quote = getQuote(); // Gets quote from data.
			if (quote.contentEquals("")) {
				System.out.println("System detected quote category does not exist. Throwing exception\n");
				throw new IllegalArgumentException();
			} else {
				commandShell cs = new commandShell();
				String userMention = cs.getMentionString(event);
				quote = String.format(quote, userMention);
				Thread.sleep(250);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException npe) {
			StringWriter npeString = new StringWriter();
			PrintWriter npePrinter = new PrintWriter(npeString);
			quote = "I'm sorry, but there was a problem getting a quote. Please report this error to <@"
					+ BOT_CREATOR_ID + "> on Discord or Twitter, or e-mail him at david.e.chavarro@outlook.com:\n";
			npe.printStackTrace(npePrinter);
			quote += npePrinter.toString();
		} catch (IllegalArgumentException iae) {
			System.out.println("Catching exception.");
			quote = "The quote category \"" + cmdArgs + "\" does not exist.\nType `!quote list` if you want to see all the quote categories I could say.\nOtherwise, only type `!quote` if you want me to say any quote.\nError code: IllegalArgumentException";
		} catch (IterationLimitReachedException ilre) {

		} finally {
			// System.out.println("Sending error message to Discord chat...");
			event.getChannel().sendMessage(quote).queue();
			// System.out.println("Done!");
		}

	}

	public void updateData() {// Updates data array from reading data file.
		try {
			fileScanner = new Scanner(sourceFile);// Instantiates scanner.
			int tempCatCount = 0;// default category count.
			int tempQuoteMax = 0;// default maximum quotes in a category count.
			int tempQuoteCount = 0;// default value for temporary count of quotes per line.
			String rawData = "##";// Sets to blank value
			while (fileScanner.hasNext()) {// Loop determines the dimensions of data array.
				System.out.println("Traversing to next line");
				rawData = fileScanner.nextLine();
				System.out.println("Raw data in line: " + rawData);
				if (rawData.substring(0, 2).equals(FILE_COMMENT) == false) {// Following code executes if
					// LINE 57 THROWS StringIndexOutOfBoundException. // line is not comment.
					if (rawData.charAt(0) == '[') {// Examines line if first char denotes a quote
													// category
						tempQuoteCount = countQuotes(rawData);
						System.out.println("Quotes counted in line: " + tempQuoteCount);
						if (tempQuoteCount > tempQuoteMax) {
							System.out.println("Quote Max Updated");
							tempQuoteMax = tempQuoteCount;
						}
						tempCatCount++;
						System.out.println("\nupdateData(): temp category count increased to " + tempCatCount + ".\n");
					}
				}
			}
			categoryCount = tempCatCount;
			maxQuote = tempQuoteMax;
			fileScanner.close();
			toDataArray();
			System.out.println("\ntoDataArray stack finished executing.\n");
		} catch (FileNotFoundException e) {
			System.out.println("Could not update data because the \"" + SOURCE_DIRECTORY
					+ "\" file could not be found.\nShowing stack trace...\n");
			e.printStackTrace();
			errorStack = e.getStackTrace().toString();
			// throw new MissingDataFileException();
		}

	}

	private String getQuote() throws NullPointerException, IllegalArgumentException, IterationLimitReachedException { // Retrieves																											// arguments.
		int category = 0;
		int quoteIndex = 0;
		int requestedCat = -1;// Holds random value to retrieve quote category
		int requestedQuoteInd = -1;// Holds random value to retrieve quote index.
		int transverseCount = 0;
		String output = null;
		try {
			Random categoryRand;
			Random quoteRand;
			if (cmdArgs.contentEquals("list")) {
				//CREATE PERMANENT SOLUTION WHERE PROGRAM READS ALL CATEGORIES
				//Temporary fix.
				output = "Here are the categories: `fine`, `joshua`, `jurassic Park`, `programming`, and `terminator`.";
			}
			while (output == null) {
				System.out.println("Is output null? " + (output == null));
				if (transverseCount > categoryCount) {
					throw new IterationLimitReachedException(categoryCount, "Quotes");
				}
				categoryRand = new Random();
				quoteRand = new Random();
				if (cmdArgs.contentEquals("")) {
					categoryRand = new Random();
					requestedCat = -1;
					requestedCat = categoryRand.nextInt(categoryCount);
					System.out.println("Quotes.getQuote(): Category randomized to index " + requestedCat + ".");
				} else {
					requestedCat = getCatIndex(cmdArgs.toLowerCase());
					System.out.println("Quotes.getQuote() method has detected that the user searched for category \""
							+ cmdArgs + "\", which is in category index " + requestedCat + ".");
				}
				if (requestedCat == -1) {
					throw new IllegalArgumentException();
				} else {
					output = "##";
					while (requestedQuoteInd < 1 || requestedQuoteInd >= maxQuote) {
						quoteRand = new Random();
						requestedQuoteInd = quoteRand.nextInt(maxQuote);
					}
					System.out.println(
							"Obtaining quote from category " + requestedCat + " and quote index " + requestedQuoteInd);
					output = data[requestedCat][requestedQuoteInd];
					transverseCount++;

					if (output.equals("")) {
						System.out.println(
								"Method detected that the quote index is null.\nSearching for different quote index.\n");
						output = null;
					}
				}
			}
		} catch (NullPointerException npe) {
			output = getQuote();
			// StringWriter npeString = new StringWriter();
			// PrintWriter npePrinter = new PrintWriter(npeString);
			// output = "I'm sorry, but there was a problem getting a quote. Please report
			// this error to <@"
			// + BOT_CREATOR_ID + "> on Discord or Twitter, or e-mail him at
			// david.e.chavarro@outlook.com:\n";

			// output += "NullPointerException";
		}
		return output;
	}

	private void toDataArray() {// Isolates raw data from file into elements.
		try {
			System.out.println("toDataArray method: \"data\" array created with " + categoryCount
					+ " categories and a max quote of " + (maxQuote - 1) + ".");
			data = new String[categoryCount][maxQuote];
			Scanner methodScanner = new Scanner(sourceFile);
			String tempData = "";
			String rawData = "";
			String[] lineArray;// Array holding elements split between a "]" character.
			String[] isolatedQuotes;// Array holding individual quote element.
			int i = 0;
			while (i < data.length) {
				System.out.println("\ntoDataArray: Started new iteration");
				tempData = methodScanner.nextLine();
				if (tempData.charAt(0) == '[') {
					System.out.println("Detected quote category.\nSaving to data array...");
					rawData = tempData.substring(1);
					System.out.println("Splitting line by character ']'...");
					lineArray = rawData.split("]");
					// System.out.println("Saving data to array...");
					// data[i][0] = lineArray[0];
					// System.out.println("Data of dataArray: " + data[i][0]);
					System.out.println("Splitting quotes and adding to array...");
					isolatedQuotes = new String[lineArray[0].split(";").length];
					isolatedQuotes = lineArray[0].split(";");
					for (int j = 0; j < isolatedQuotes.length; j++) {
						try {
							data[i][j] = isolatedQuotes[j];
							System.out.println("Quote added to category " + i + ", element " + j + ".");
						} catch (NullPointerException npe) {
							data[i][j] = "##";
						}
					}
					i++;
				} else {
					System.out.println("Bypassed line as the first char is '" + tempData.charAt(0) + "'.");
				}
				
			}
			methodScanner.close();
		} catch (FileNotFoundException fe) {
			System.out.println(
					"toDataArray method failed to convert assign data array because it failed to find data file.\nPrinting stack trace...\n");
		}
	}

	private int countQuotes(String rawData) {// Count quotes contained in each quote category
		String[] rawArray = rawData.split(";");
		return rawArray.length;
	}

	public String getStack() {// Returns error stack.
		try {
			return errorStack;
		} catch (NullPointerException npe) {
			errorStack = "";
			return errorStack;
		}
	}

	private int getCatIndex(String cmdArgs) {// Returns category index by name as specified in cmdArgs variable.
		for (int i = 0; i < data.length; i++) {
			if (data[i][0].equals(cmdArgs)) {
				return i;
			}
		}
		return -1;
	}
	
	
	//WORK ON EXPORTING ERROR TO STRING
	/*private void errorToString() {

	}
	*/
	
	public String GET_DATA_PATH() { //Returns appropriate data path depending on the platform
		//the program is compiled for.
		if (OPERATING_SYSTEM.equals("Linux")) {
			return LINUX_DATA_PATH;
		} else if (OPERATING_SYSTEM.equals("Windows")) {
			return WINDOWS_DATA_PATH;
		} else {
			return null;
		}
	}
}