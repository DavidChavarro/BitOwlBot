package gui;

import interfaces.InfoRetrieveable;
import javafx.event.EventHandler;
//import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.dv8tion.jda.api.JDA;

public class GUIMain extends GUI implements InfoRetrieveable {
	
	
	
	public GUIMain(JDA bot, Stage main) {//Constructs object by having JDA bot and main stage object passed.
		super(bot, main);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("exports")
	public EventHandler<MouseEvent> shutdownButton = new EventHandler<MouseEvent>() {//Runs shutdownBot method when
		//"shutdown bot" button is clicked.
		@Override
		public void handle(MouseEvent event) {
			shutdownBot();
		}
	};
	
	public EventHandler<MouseEvent> listButton = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			//System.out.println("\nMouse clicked.\n");
			GuildSettings guildList = new GuildSettings();
			guildList.start();
		}
	};
	
	private void shutdownBot() {
		bot.shutdown();
		stage.close();
	}
	
	@SuppressWarnings("exports")
	public JDA getJDA() {
		return bot;
	}
	
	
	//NOTE TO DEVELOPER: Consider putting the following code under unique class GUIScaler.
	public Scene setSceneResolution(Scene home, AnchorPane ap) {
		int x;
		int y;
		if (OPERATING_SYSTEM.equals("Linux")) {
			x = 3000;
			y = 100;
		} else {
			x = 750;
			y = 25;
		}
		home = new Scene(ap, x, y);
		return home;
	}
	
	public double getScaledSize(double maxSize) {
		if (OPERATING_SYSTEM.equals("Linux")) {
			return maxSize;
		} else {
			return maxSize / 2;
		}
	}
	
	public int getBodyFontSize() {
		if (OPERATING_SYSTEM.equals("Linux")) {
			return 24;
		} else {
			return 12;}
		}
	
	public static int getBodyFontSizeStatic() {
		if (OPERATING_SYSTEM.equals("Linux")) {
			return 24;
		} else {
			return 12;}
		}
	
	
}
