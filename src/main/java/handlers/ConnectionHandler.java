package handlers;

//Class handles all connection events of bot.
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import guildObjects.GuildList;
import javafx.scene.control.Label;
import main.Main;

public class ConnectionHandler extends ListenerAdapter {
	Label botStatus; //Holds label that displays bot status on GUI Window.
	JDA bot;
	boolean botIsOnline;
	GuildList gl;
	
	public ConnectionHandler(Label botStatus, JDA bot) {
		this.botStatus = botStatus;
		this.bot = bot;
		this.gl = Main.getGuildList();
		botIsOnline = false;
	} 
	
	
	public void onDisconnect(DisconnectEvent e) {
		System.out.println("\nBot is offline. Check host connection.\n");
		botIsOnline = false;
		Main main = new Main();
		main.updateStatLabel(botIsOnline);
	}
	
	public void onReconnect(ReconnectedEvent e) {
		System.out.println("\nBot has successfully reconnected to the Discord server.\n");
		botIsOnline = true;
		Main main = new Main();
		main.updateStatLabel(botIsOnline);
	}
	
	public void onReady(ReadyEvent e) {
		System.out.println("BitOwl has detected that it is online");
		botIsOnline = true;
		Main main = new Main();
		main.updateStatLabel(botIsOnline);
	}
	
	
	
	public void updateAttributes(Label botStatus, JDA bot) {
		this.botStatus = botStatus;
		this.bot = bot;
		this.gl = Main.getGuildList();
	}
	
	
	
}