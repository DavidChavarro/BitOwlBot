package testerPkg;

import java.io.IOException;

import handlers.MemberEventHandler;
import main.BootLoader;
import main.Main;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

public class WelcomeMsgTester extends Tester implements Runnable{
	private static String className = "WelcomeMsgTester";
	public static void main(String[] args) throws InterruptedException {
		printTestStatus("Launching program...");
		WelcomeMsgTester tester = new WelcomeMsgTester();
		Thread testerThread = new Thread(tester);
		testerThread.start();
		Thread.sleep(5000);
		MemberEventHandler handler = new MemberEventHandler(Main.getBot());
		GuildMemberJoinEvent e = null;
		handler.onGuildMemberJoin(e);
	}
	@Override
	public void run() {
		String[] args = null;
		try {
			BootLoader.main(args);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void printTestStatus(String message) {
		System.out.println("\n\"" + className + "\" class: " + message + "\n");
	}
}
