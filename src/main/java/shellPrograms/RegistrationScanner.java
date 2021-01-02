package shellPrograms;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RegistrationScanner extends ListenerAdapter {
	protected boolean nameEntered;
	protected boolean emailEntered;
	protected String name;
	protected String email;
	
	
	
	RegistrationScanner() {
		nameEntered = false;
		emailEntered = false;
		name = "";
		email = "";
	}
	
	public boolean nameEntered() {
		return nameEntered;
	}
	public boolean emailEntered() {
		return emailEntered;
	}
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	

}