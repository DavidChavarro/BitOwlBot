package handlers;

import guildObjects.Guild;
import guildObjects.GuildList;
import main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberEventHandler extends ListenerAdapter{
	private final String WELCOME_MSG = "Welcome to the %s, %s!\n" + 
			"\n" + 
			"Before you could continue, please read the rules set in the <#%l> channel. Then, please mention me or type `!register` at the <#%l> channel to start the registration process.\n" + 
			"\n" + 
			"Have a great day!";
	protected JDA bot;
	
	public MemberEventHandler(@SuppressWarnings("exports") JDA bot) {
		this.bot = bot;
	}
	
	@SuppressWarnings("exports")
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		//DETERMINE WHY THIS EVENT LISTENER DOES NOT ACTIVATE.
		System.out.println("onGuildMemberJoin is working.");
	//System.out.println("discordServerBot.MemberEvent class:\n" + "BitOwl has detected " +e.getMember().getNickname() + "join the " + e.getGuild().getName() + "server.");
	//String memberStr = e.getMember().toString();
	//int memberStrLen = memberStr.length();
	//int idStart = memberStrLen - 20;
	//String memberIdStr = memberStr.substring(idStart, memberStrLen - 1);
	//long memberId = Long.parseLong(memberIdStr);
	//System.out.println(memberId);
	//typeWelcomeMessage(e, memberId);
	}
	
	protected void typeWelcomeMessage(GuildMemberJoinEvent e, long memberId) {
		System.out.println("Sending welcome message...");
		String guildName = guildToString(e.getGuild().toString());
		long guildId = getGuildId(e.getGuild().toString());
		long[] outputParam = getOutputParam(guildId);
		String message = String.format(WELCOME_MSG, guildName, memberId, outputParam[2], outputParam[3]);
		System.out.println(message);
		TextChannel welcome = bot.getGuildById(guildId).getTextChannelById(outputParam[1]);
		welcome.sendTyping().queue();
		welcome.sendMessage(message).queue();
	}
	
	
	
	protected long[] getOutputParam(long guildID) {
		long[] parameter = new long[4];
		GuildList gl = Main.getGuildList();
		Guild guild = gl.getGuildByID(guildID);
		parameter[0] = guild.getID();
		parameter[1] = guild.getWelcomeChID();
		parameter[2] = guild.getRulesID();
		parameter[3] = guild.getRegistrChID();
		return parameter;
	}
	
	protected String guildToString(String rawString) { 
		int length = rawString.length();
		String guildName = rawString.substring(2, length - 20);
		System.out.println("Event guildName: " + guildName);
		return guildName;
	}
	
	protected int getGuildId(String rawString) {
		int length = rawString.length();
		int substrStart = length - 19;
		int substrEnd = length - 1;
		String idString = rawString.substring(substrStart,substrEnd);
		int id = Integer.parseInt(idString);
		return id;
	}
}