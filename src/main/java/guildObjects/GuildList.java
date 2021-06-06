package guildObjects;

import java.util.List;



public class GuildList {
	List<net.dv8tion.jda.api.entities.Guild> guildList;
	private Guild top;
	private Guild end;

    public GuildList(List<net.dv8tion.jda.api.entities.Guild> guildList) {
		this.guildList = guildList;
    }

    //Returns all the guilds the bot is connected to as a string.
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

	//Updates the guilds that the bot is connected to.
	public void updateGuildList() {
		int size = guildList.size();
		System.out.println("List size: " + size);
		System.out.println("Updating guild list with size of " + size);
		net.dv8tion.jda.api.entities.Guild object;
		for (int i = 0; i < size; i++) {
			System.out.println("Re-iterating guild loop...");
			System.out.print("Creating guild " + guildList.get(i) + " from index " + i + ".");
			object = guildList.get(i);
			addGuild(object);
			//System.out.println("done\n");
		}
	}

	//Adds a guild to the list.
	protected void addGuild(net.dv8tion.jda.api.entities.Guild object) {
		//System.out.println("Adding new guild...");
		Guild newGuild = new Guild(object);
		
		System.out.println("Guild " + newGuild.guildName + " created.");
		if (top == null) { 
			top = newGuild;
			System.out.println("Created as top guild");
		} else  {
			System.out.println("Created as end guild");
			end.next = newGuild;
			newGuild.previous = end;
		}
		end = newGuild;
		//newGuild.updateUserList();
	}

	//Returns the guild object by index.
	public Guild getGuildByIndex(int i) {
		if (guildList.size() == 0)
			System.out.println("List size is 0.");
		try {
			//System.out.println("Getting top guild...");
			Guild tempGuild = top;
			//System.out.println("Caught top guild...");
			int index = 0;
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
			System.out.println("Null pointer in guildList class.");
		}
		return null;
	}

	//Returns the guild object by its id.
	public Guild getGuildByID(long id) {
		//Returns guild object by ID.
		if (guildList.size() == 0) {
			System.out.println("List size is 0.");
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

	

}
