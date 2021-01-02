package guildObjects;

//Handles node objects.
public class Guild {
	protected Guild next;
	protected Guild previous;
	protected String guildName;
	protected long guildID;
	protected long welcomeChannel;
	protected long rulesChannel;
	protected long registrationChannel;
	private net.dv8tion.jda.api.entities.Guild guildData;

	Guild(net.dv8tion.jda.api.entities.Guild data) {
		guildData = data;
		guildName = guildData.getName();
		guildID = guildData.getIdLong();
		// Assigns attributes where Channel IDs are stored.
		welcomeChannel = guildData.getTextChannelsByName("welcome", true).get(0).getIdLong();
		rulesChannel = guildData.getTextChannelsByName("welcome", true).get(0).getIdLong();
		
	}

	

	

	protected void getUserList() {
		System.out.println(guildData.getMembers());
	}

	public String getName() {
		return guildName;
	}

	public long getID() {
		return guildID;
	}

	public long getWelcomeChID() {
		return welcomeChannel;
	}

	public long getRulesID() {
		return rulesChannel;
	}

	public long getRegistrChID() {
		return registrationChannel;
	}
	
	public void setRegistrChId() {
		try {
			registrationChannel = guildData.getTextChannelsByName("registration", true).get(0).getIdLong();
		} catch (IndexOutOfBoundsException iobe) {
			registrationChannel = -1;			
		}
	}

}