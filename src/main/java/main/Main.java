package main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;

import gui.GUI;
import gui.GUIMain;
import guildObjects.GuildList;
import handlers.ConnectionHandler;
import handlers.MemberEventHandler;
import interfaces.InfoRetrieveable;
import interfaces.Loggable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
//IMPORT JAVAFX Libraries
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Main extends Application implements Loggable, InfoRetrieveable {

	private static JDA masterJDA;
	private static GuildList gl;
	private static final String WINDOW_TITLE = "Control Panel";
	//private Stage menuStage = new Stage();
	private static GUIMain main;
	@FXML
	private AnchorPane ap = new AnchorPane();
	@FXML
	private Scene home;
	@FXML
	// DO NOT MAKE THE LABEL STATIC OR AN IllegalStateException WILL BE THROWN
	private Label botStatus = new Label("Signing in...");

	public static void main(String[] args) throws IOException, InterruptedException {
		launch(args);
	}

	
	
	
	
	
	
	
	@Override
	@FXML
	public void start(Stage menuStage) throws Exception {// GUI driver
		DateTimeFormatter USClock = DateTimeFormatter.ofPattern("mm/dd/yyyy");
		LocalDateTime currTime = LocalDateTime.now();
		try {
			//CONVERT ALL GUI OBJECTS TO STATIC REFERENCES
			menuStage.setResizable(true);
			GUI gui = new GUI(masterJDA, menuStage);
			menuStage.setTitle(WINDOW_TITLE + " - " + gui.getGlobalTitle());
			masterJDA = JDABuilder.createDefault("NzA5ODY1Nzk1MzY2Mjg5NTYx.XuLCLQ.wwjRHQ5I1nOXfau5OQ1SbEYp100").build();
			ConnectionHandler ch = new ConnectionHandler(botStatus, masterJDA);
			masterJDA.addEventListener(ch);
			commandShell shell = new commandShell(menuStage);
			masterJDA.addEventListener(shell);
			MemberEventHandler memberEvent = new MemberEventHandler(masterJDA);
			masterJDA.addEventListener(memberEvent);
			masterJDA.getPresence().setPresence(OnlineStatus.ONLINE,
					Activity.playing(VERSION_PREFIX + " version " + VERSION));
			//menuStage.setTitle();
			main = new GUIMain(masterJDA, menuStage);
			AnchorPane.setLeftAnchor(botStatus, main.getScaledSize(10.0));
			AnchorPane.setBottomAnchor(botStatus, main.getScaledSize(5.00));
			botStatus.setFont(Font.font(main.getBodyFontSize()));
			botStatus.setMinWidth(main.getScaledSize(400));
			botStatus.setMinHeight(main.getScaledSize(100));
			Button shutdownBot = new Button("Shutdown bot");
			AnchorPane.setBottomAnchor(shutdownBot, main.getScaledSize(5.00));
			AnchorPane.setRightAnchor(shutdownBot, main.getScaledSize(10.00));
			shutdownBot.setFont(Font.font(main.getBodyFontSize()));
			Button listButton = new Button("Guild lists");
			listButton.setFont(Font.font(main.getBodyFontSize()));
			AnchorPane.setRightAnchor(listButton, main.getScaledSize(350.00));
			AnchorPane.setBottomAnchor(listButton, main.getScaledSize(5.00));
			ap.getChildren().addAll(botStatus, shutdownBot, listButton);
			GUIMain guiHandler = new GUIMain(masterJDA, menuStage);
			shutdownBot.addEventFilter(MouseEvent.MOUSE_CLICKED, guiHandler.shutdownButton);
			
			listButton.addEventFilter(MouseEvent.MOUSE_CLICKED, guiHandler.listButton);
			home = main.setSceneResolution(this.home, ap);
			menuStage.setScene(home);
			masterJDA.awaitReady();
			gl = new GuildList(masterJDA.getGuilds());
			gl.updateGuildList();
			System.out.println("\nMain method is updating label...\n");
			botStatus.setStyle("-fx-text-fill: green;");
			botStatus.setText("Connected to " + gl.toString());
			botStatus.setMaxWidth(main.getScaledSize(2100));
			botStatus.setWrapText(true);
			menuStage.show();
			currTime = LocalDateTime.now();
			System.out.println("BitOwl has signed in to Discord at " + currTime.getHour() + ":" + currTime.getMinute() + ".");
			//Developers Note: Create custom time class that formats 24 hours to 12 hours.

		} catch (LoginException e) {
			File logStream = new File(LINUX_LOG_PATH + "mainClassError.log");
			Scanner logData = new Scanner(logStream);
			currTime = LocalDateTime.now();
			String errorDate = USClock.format(currTime);
			StringWriter excSW = new StringWriter();
			e.printStackTrace(new PrintWriter(excSW));
			String stackTrace = excSW.toString();
			String errorMsg = errorDate + "\n\n"
					+ "There was a problem with bot signing into the Discord server.\nPrinting stack trace.\n"
					+ stackTrace;
			FileOutputStream eOutput = new FileOutputStream(LINUX_LOG_PATH + "mainClass.log");
			PrintWriter outputWr = new PrintWriter(eOutput);
			customIO stringConverter = new customIO(logData);
			String dataString = stringConverter.toString();
			outputWr.print(dataString + "\n" + errorMsg);
			outputWr.close();
			eOutput.close();
			logData.close();
			System.out.println(errorMsg);
			e.printStackTrace();
		}

	}

	public Label getConnStatLabel() {
		return botStatus;
	}

	public static GuildList getGuildList() {
		return gl;
	}

	public static JDA getBot() {
		return masterJDA;
	}
	
	

	public void updateStatLabel(boolean botIsOnline) {
		if (botIsOnline) {
			System.out.println("\n\"updateStatLabel\" is updating stat label as online.\n");
			botStatus.setStyle("-fx-test-fill: green;");
			botStatus.setText("Online.");
		} else {
			System.out.println("\n\"updateStatLabel\" is updating stat label as online.\n");
			botStatus.setStyle("-fx-test-fill: red;");
			botStatus.setText("Offline.");
		}
	}
}
