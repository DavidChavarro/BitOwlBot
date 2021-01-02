package shellPrograms;

import main.commandShell;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Help extends ShellPrograms {
	// Suggestion: Create a help directory where each program help has its own help
	// file.
	//Developer note: Implement a method where the "!register" command shows when applicable to the guild.
	private final String MAIN_MESSAGE = "Here are the commands you can use, %s:\n"
			+ "`!hello`: Tells user about the \"OwlBit\" bot.\n" + "`!quote <OPTIONAL CATEGORY>`: posts a movie quote.";
	private String userMention;
	public Help(GuildMessageReceivedEvent event, String cmdArgs) {
		super(event, cmdArgs);
	}

	public void start() {
		try {
			commandShell cs = new commandShell();
			userMention = cs.getMentionString(event);
			if (cmdArgs.length() > 1) {
				//printProgHelp(cmdArgs)
			} else {
				event.getChannel().sendTyping().queue();
				String msgOutput = String.format(MAIN_MESSAGE, userMention);
				Thread.sleep(250);
				event.getChannel().sendMessage(msgOutput).queue();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}