package shellPrograms;
import main.commandShell;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Hello extends ShellPrograms{
	private String message;
	private String userMention;
	public Hello(GuildMessageReceivedEvent e, String cmdArgs) {
		super(e, cmdArgs);
		message = "Hello, %s! I am BitOwl! A Discord bot created by my father, @The Tech Owl. I was first made in May 11th, 2020. It's a pleasure to meet you!"; 
	}

	public void start() {
		try {
		commandShell cs = new commandShell();
		userMention = cs.getMentionString(event);
		message = String.format(message, userMention);
		event.getChannel().sendTyping().queue();
		Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
		event.getChannel().sendMessage(message).queue();
		}
	}
}