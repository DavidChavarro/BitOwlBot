package shellPrograms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

import java.util.Scanner;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RosterList {
	private File file;
	private Scanner fileReader;
	private FileOutputStream output;
	private PrintWriter outputPrinter;
	private String rawData;
	private String[] dataArray;
	private final String ABSOLUTE_PATH = "data/rosterList/";
	private String guildIDString;
	private String name;
	private String email;
	private GuildMessageReceivedEvent e;
	RosterList(GuildMessageReceivedEvent e, long guildID, String name, String email) {
		guildIDString = Long.toString(guildID);
		dataArray = new String[3];
		this.e = e;
		this.name = name;
		this.email = email;
	}
	
	//Verifies user. The return value represents the following:
	//-2: FileNotFoundException has occurred. 
	//May have searched for non-existent guild.
	//-1: Person is already verified.
	//0: Person is not found on list.
	//1: User verificiation successful.
	public int verifyUser() {
		try {
			file = new File(ABSOLUTE_PATH + guildIDString);
			fileReader = new Scanner(file);
			rawData = fileReader.nextLine();
			int line = 1;
			while (fileReader.hasNext()) {
				if (!rawData.substring(0, 2).equals("##")) {
					rawData = fileReader.nextLine();
					dataArray = rawData.split(", ");
					if (personExists()) {
						if (dataArray[2].equals("false")) {
						String newData = updateUserStatus(true, line);
						e.getGuild().addRoleToMember(e.getMember(), e.getGuild().getRolesByName("Verified Classmates", true).get(0));
						output = new FileOutputStream(ABSOLUTE_PATH + guildIDString);
						outputPrinter = new PrintWriter(output);
						outputPrinter.print(newData);
						outputPrinter.close();
						return 1;
						} else {
							return -1;
						}
					}
				}
				line++;
			} return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -2;
		}
		
	}
	
	public boolean personExists() {
		if (dataArray[0].contentEquals(name) && dataArray[1].equals(email.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	//Updates role status to the data file contain member list.
	//Boolean represents if member is verified.
	private String updateUserStatus(boolean state, int line) {
		String rawData = null;
		try {
			dataArray[2] = "true";
			String updateLine = toString(dataArray);
			Scanner tempScanner = new Scanner(file);
			String preUpdateLn = "";
			for (int i = 1; i < line; i++) {
				preUpdateLn += tempScanner.nextLine() + "\n";
			}
			rawData = preUpdateLn + updateLine;
			tempScanner.nextLine();
			String postUpdateLines = "";
			while (tempScanner.hasNextLine()) {
				postUpdateLines += tempScanner.nextLine() + "\n";
			}
			rawData += "\n" + postUpdateLines;
			tempScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return rawData;
		
	}
	
	
	//Converts array to string that would be stored in a file.
	public String toString(String[] array) {
		String output = "";
		for (int i = 0; i < array.length; i++) {
			output += array[i];
			if (i != array.length - 1) {
				output += ", ";
			}
		}
		return output;
	}
}