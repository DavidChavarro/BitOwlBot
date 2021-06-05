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
	
	public ConnectionHandler(Label botStatus, JDA bot) throws InterruptedException
    {
		this.botStatus = botStatus;
		this.bot = bot;
		botIsOnline = false;
	}


	public void onDisconnect(DisconnectEvent e) {
		System.out.println("\nBot is offline. Check host connection.\n");
		botIsOnline = false;
		updateStatLabel(botIsOnline);
    }

	public void onReconnect(ReconnectedEvent e) {
		System.out.println("\nBot has successfully reconnected to the Discord server.\n");
		botIsOnline = true;
		updateStatLabel(botIsOnline);
    }
	
	public void onReady(ReadyEvent e) {
		System.out.println("BitOwl has detected that it is online");
		botIsOnline = true;
		updateStatLabel(botIsOnline);
    }
	
	
	
	public void updateAttributes(Label botStatus, JDA bot) {
		this.botStatus = botStatus;
		this.bot = bot;
		this.gl = Main.getGuildList();
	}

    private void updateStatLabel(boolean botIsOnline)
    {
        if (botIsOnline) {
            botStatus.setStyle("-fx-test-fill: green;");
            botStatus.setText("Connected to: " + Main.getGuildList().toString());
        } else {
            botStatus.setStyle("-fx-test-fill: red;");
            botStatus.setText("Offline.");
        }
    }
	
	
	
}
