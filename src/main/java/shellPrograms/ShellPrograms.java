package shellPrograms;
//Holds any bot's programs/features that are executed by user commands
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
public abstract class ShellPrograms {
	protected GuildMessageReceivedEvent event;
	protected String cmdArgs;
	
	ShellPrograms(GuildMessageReceivedEvent e, String cmdArgs) {
		event = e;
		this.cmdArgs = cmdArgs;
	}

	public abstract void start();
}