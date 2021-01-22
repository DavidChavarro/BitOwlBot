package gui;

import interfaces.InfoRetrieveable;
//import javafx.application.Application;
import javafx.stage.Stage;
import net.dv8tion.jda.api.JDA;

public class GUI implements InfoRetrieveable {
	protected static JDA bot;
	protected static Stage stage;
	protected final static String GLOBAL_TITLE = "BitOwl Discord Bot panel (" + OPERATING_SYSTEM + " " + VERSION_PREFIX + " version "
			+ VERSION + ")";
	public GUI(JDA bot, Stage main) {
		GUI.bot = bot;
		GUI.stage = main;
	}
	
	public GUI() {
		
	}
	
	public static String getGlobalTitle() {
		return GLOBAL_TITLE;
	}
	
	public static void closeStage() {
		stage.close();
	}
	
	public static Stage getMainStage() {
		return stage;
	}
}