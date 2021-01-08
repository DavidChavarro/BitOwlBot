package main;

import gui.GUI;
import gui.GUIMain;
import guildObjects.Member;
import interfaces.InfoRetrieveable;
import javafx.stage.Stage;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import shellPrograms.Hello;
import shellPrograms.Help;
import shellPrograms.Quotes;
import shellPrograms.Registration;

public class commandShell extends ListenerAdapter implements InfoRetrieveable {
	public final char CMD_PREFIX = '!';
	// Activates bot.
	public final String SPACE_DELIM = "\\s+";
	// Link to verification form for STS 1101 Discord server.
	public final String STS1101_FORM = "https://forms.gle/z39y8QTuzrtLuHKB9";

	public Stage mainStage;

	public commandShell(Stage mainStage) {
		mainStage = this.mainStage;
	}

	public commandShell() {

	}


	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

		User name = e.getAuthor();
		String nameString = name.toString();
		// int nameLength = nameString.length();
		// String mention = "<@" + nameString.substring(nameLength - 19, nameLength - 1)
		// + ">";
		printConsoleMessage(e);
		String rawMessage = e.getMessage().getContentRaw();
		try {
			if (nameString.equals("U:BitOwl(709865795366289561)") == false && rawMessage != null) {
				if (rawMessage.charAt(0) == CMD_PREFIX) {
					System.out.println("Running method helper");
					runMethodHelper(e, e.getMessage().getContentRaw());
				}
			}
		} catch (StringIndexOutOfBoundsException strIndex) {
			System.out.println("\"commandShell\" class: Caught following exception. Method Helper has been aborted.");
			strIndex.printStackTrace();
		}

	}

	@SuppressWarnings("exports")
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		// DETERMINE WHY THIS EVENT LISTENER DOES NOT ACTIVATE.
		System.out.println("onGuildMemberJoin is working.");
		// System.out.println("discordServerBot.MemberEvent class:\n" + "BitOwl has
		// detected " +e.getMember().getNickname() + "join the " +
		// e.getGuild().getName() + "server.");
		// String memberStr = e.getMember().toString();
		// int memberStrLen = memberStr.length();
		// int idStart = memberStrLen - 20;
		// String memberIdStr = memberStr.substring(idStart, memberStrLen - 1);
		// long memberId = Long.parseLong(memberIdStr);
		// System.out.println(memberId);
		// typeWelcomeMessage(e, memberId);
	}

	private void printConsoleMessage(GuildMessageReceivedEvent e) {
		System.out.println(e.getGuild() + ", " + e.getChannel());
		System.out.println("New message from " + e.getAuthor().toString().substring(2) + ": ");
		System.out.println(e.getMessage().getContentRaw() + "\n");
	}

	private void runMethodHelper(GuildMessageReceivedEvent e, String rawMessage) {
		String cmdArgs = "";
		System.out.println("Raw message: " + rawMessage);
		System.out.println("splitting raw message");
		String[] messageArr = rawMessage.split(" ");
		if (messageArr.length > 1) {
			System.out.println("Message array length is " + messageArr.length);
			cmdArgs = getCommandArgs(messageArr);
		}
		switch (messageArr[0].substring(1)) {
		case "help":
			Help help = new Help(e, cmdArgs);
			help.start();
			break;
		case "hello":
			Hello hello = new Hello(e, cmdArgs);
			hello.start();
			break;
		// case "register":
		// System.out.println("\nRunning register program.\n");
		// Registration registerWizard = new Registration(e, cmdArgs);
		// Thread mainThr = new Thread(registerWizard);
		// mainThr.run();
		// Error message for users trying to invoke method.
		// Message below is printed to console.
		// System.out.println("Cannot be executed because the method is not
		// implemented.\nPlease DM `@The Tech Owl`.");
		// break;
		case "quote":
			Quotes quote = new Quotes(e, cmdArgs);
			quote.start();
			break;
		case "shutdown":
			try {
			e.getChannel().sendTyping().queue();
			e.getChannel().sendMessage("Shutting down bot... After shutting down, please kill `java` thread via your pc's SSH connection.").queue();
			Main.getBot().shutdown();
			mainStage.close();}
			catch (NullPointerException npe) {
				mainStage = GUI.getMainStage();
				//e.getChannel().sendTyping().queue();
				//e.getChannel().sendMessage("Shutting down bot... After shutting down, please kill `java` thread via your pc's SSH connection.").queue();
				Main.getBot().shutdown();
				mainStage.close();
			}
			break;
		default:
			e.getChannel().sendTyping().queue();
			e.getChannel().sendMessage("I cannot recognize your command, " + getMentionString(e) + ".\n"
					+ "For lists of commands that I can understand, type \"!help\".").queue();
			}
		
	}

	private String getCommandArgs(String[] inputArr) {
		String cmdArgs = "";
		System.out.println("command args length: " + inputArr.length);
		for (int i = 1; i < inputArr.length; i++) {
			System.out.println("i index is " + i);
			cmdArgs += inputArr[i] + " ";
			System.out.println("cmdArgs string obtained: " + cmdArgs);
		}
		cmdArgs = cmdArgs.trim();
		System.out.println("Returning cmdArgs string \"" + cmdArgs + "\"...");
		return cmdArgs;
	}

	public String getMentionString(@SuppressWarnings("exports") GuildMessageReceivedEvent e) {
		Member idConverter = new Member();
		return "<@" + idConverter.getID(e.getAuthor().toString()) + ">";
	}
}
