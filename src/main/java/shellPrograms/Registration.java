package shellPrograms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import guildObjects.Guild;
import guildObjects.GuildList;
import main.Main;
import main.commandShell;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


//In a guild (Discord server) that requires users to be verified,
//Class allows bot to enter required info to verify if user is on list that
//server owner chooses who would be allowed to be on server.
public class Registration extends ListenerAdapter implements Runnable {
	GuildMessageReceivedEvent e;
	protected String CMDArgs;
	long guildID;
	protected long registrationChID;
	protected String userMention;
	protected int registrationStat;//Shows status of registration thread.
	protected boolean nameEntered;
	protected boolean emailEntered;
	protected String name;
	protected String email;
	private final String REG_BREAK_CMD = "!cancel";//Represents command string that cancel registration process.
	
	public Registration(GuildMessageReceivedEvent e, String CMDArgs) {
		this.e = e;
		this.CMDArgs = CMDArgs;
		registrationStat = 0;
		nameEntered = false;
		emailEntered = false;
	}
	
	@Override
	public void run() {
		GuildList gl = Main.getGuildList();
		guildID = e.getGuild().getIdLong();
		Guild main = gl.getGuildByID(guildID);
		registrationChID = main.getRegistrChID();
		if (e.getChannel().getIdLong() == registrationChID) {
			commandShell cs = new commandShell();
			userMention = cs.getMentionString(e);
			registrationStat = 0;
			String namePrompt = "Please enter your full name, %s";
			namePrompt = String.format(namePrompt, userMention);
			RegistrationScanner regScan = new RegistrationScanner();
			while (registrationStat == 0) {
				while (nameEntered == false) {
					System.out.println("\n\"Registration\" class: Obtaining member's real name...\n");
				e.getChannel().sendTyping();
				e.getChannel().sendMessage(namePrompt);
				//RUN A NEW THREAD THAT RECIEVES USERS' NAME AND EMAIL.
				e.getJDA().addEventListener(regScan);
				while (regScan.nameEntered() == false) {
					//Sleeps thread until name is entered.
				}
				nameEntered = regScan.nameEntered();
				name = regScan.getName();
				}
				 if (name.equals(REG_BREAK_CMD)) {//Checks if user decides to cancel. 
					 //If so, registrationStat is updated and outer loop breaks.
					 registrationStat = -1;
					 break;
				 }
				while (emailEntered == false) {
					String emailPrompt = "Now enter your e-mail, %s.\nNote that it is your university email ending in \"@students.kennesaw.edu\".";
					emailPrompt = String.format(emailPrompt, userMention);
					e.getChannel().sendTyping();
					e.getChannel().sendMessage(emailPrompt);
					while (regScan.emailEntered() == false) {
						//Sleep thread until email is entered via while loop.
					}
					emailEntered = regScan.emailEntered();
					email = regScan.getEmail();
				}
				if (email.equals(REG_BREAK_CMD)) {//Checks if user decides to cancel. 
					 //If so, registrationStat is updated and outer loop breaks.
					 registrationStat = -1;
					 break;
				 }
				//verifyUser();
				//CREATE METHOD ABOVE TO VERIFY USER, AND CREATE ANTOHER TO ADD VERIFIED ROLE.
			}
			
		}
	}
	
}