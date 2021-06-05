package guildObjects;

import java.util.List;

import net.dv8tion.jda.api.JDA;

public class GuildList {
	List<net.dv8tion.jda.api.entities.Guild> guildList;
	private Guild top;
	private Guild end;
	private JDA bot;
	public GuildList(List<net.dv8tion.jda.api.entities.Guild> guildList) {
		this.guildList = guildList;
		bot = null;
	}
	
	public GuildList(JDA bot) {
		this.bot = bot;
		this.guildList = bot.getGuilds();
	}
	
	
	public String toString() {
		String output = "";
		for (int i = 0 ; i < guildList.size(); i++) {
		    if (i == guildList.size() - 1) {
		        output += "and ";
            }
			output += guildList.get(i).toString().substring(2);
			if (i == guildList.size() - 1) {
				output+= ".";
			} else {
				output += ", ";
			}
		}
		return output;
	}
	
	public void updateGuildList() {
		int size = guildList.size();
		net.dv8tion.jda.api.entities.Guild object;
		for (int i = 0; i < size; i++) {
			object = guildList.get(i);
			addGuild(object);
		}
	}
	
	protected void addGuild(net.dv8tion.jda.api.entities.Guild object) {
		//System.out.println("Adding new guild...");
		Guild newGuild = new Guild(object);
		if (top == null) { 
			top = newGuild;
		} else  {
			end.next = newGuild;
			newGuild.previous = end;
		}
		end = newGuild;
		//newGuild.updateUserList();
	}
	
	public Guild getGuildByIndex(int i) {
		if (guildList.size() == 0)
			//System.out.println("getGuildByIndex caught list size as 0.");
			//throw new GuildListEmptyException();
		try {
			//System.out.println("Getting top guild...");
			Guild tempGuild = top;
			//System.out.println("Caught top guild...");
			int index = 0;
			boolean isFound = false;
			while (index < guildList.size()) {
				if (index == i) {
					return tempGuild;
				}
				if (index != guildList.size() - 1) {
				tempGuild = tempGuild.next;
				index++;}
			}
			//throw new GuildNotFoundException();
		} catch (NullPointerException e) {
			//System.out.println("Null pointer in guildList class.");
		}
		return null;
	}
	
	public Guild getGuildByID(long id) {
		//Returns guild object by ID.
		if (guildList.size() == 0) {
			return null;
		} else {
				Guild tempGuild = top;
				while (tempGuild.next != null) {
					if (tempGuild.getID() == id) {
						return tempGuild;
					} else {
						tempGuild = tempGuild.next;
					}
				}
				return null;
		}
	}
	
	public Guild getGuildByName(String name) {
		//System.out.println("Search method executing with index '" + name + "'.");
		if (guildList.size() == 0) {
			//System.out.println("getGuildByName caught list size as 0.");
			//throw new GuildListEmptyException();
		}
		try {
			//System.out.println("Getting top guild...");
			//System.out.println("Starting search");
			Guild tempGuild = top;
			//System.out.println("Caught top guild...");
			int index = 0;
			boolean isFound = false;
			String guildName;		
			while (index < guildList.size()) {
				guildName = tempGuild.getName();
				//System.out.println("Guildname = " + guildName);
				if (guildName.equals(name)) {
					//System.out.println("FOUND");
					return tempGuild;
				}
				tempGuild = tempGuild.next;
				index++;
			}
			//System.out.println("Not found");
			//throw new GuildNotFoundException();
		} catch (NullPointerException e) {
			System.out.println("Null pointer in guildList class.");
		}
		return null;
	}
	
	public List<net.dv8tion.jda.api.entities.Guild> getGuildData() {
		return guildList;
	}
	
	public void setGuildList(List<net.dv8tion.jda.api.entities.Guild> guildList) {
		this.guildList = guildList;
	}
	
	
	
}
