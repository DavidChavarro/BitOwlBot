package testerPkg;

import java.io.IOException;
//import java.util.List;

import guildObjects.GuildList;
import main.BootLoader;
import main.Main;

//Tests the output of all guilds with their own user info.
public class GuildListTester extends Tester implements Runnable{
	private static String className = "GuildListTester";
	public static void main(String[] args) throws IOException, InterruptedException {
		printTestStatus("waiting for main program to launch...");
		GuildListTester mainThread = new GuildListTester();
		Thread runnable = new Thread(mainThread);
		runnable.start();
		Thread.sleep(5000);
		printTestStatus("Displaying guilds with own info...");
		GuildList testList = Main.getGuildList();
		System.out.println(testList.getGuildByIndex(0).getName());
	}
	
	public static void printTestStatus(String message) {
		System.out.println("\n\"" + className + "\" class: " + message + "\n");
	}

	@Override
	public void run() {
		try {
			String[] args = null;
			BootLoader.main(args);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public GuildList getGuildList() {
		return Main.getGuildList();
	}
	
	
	
}
