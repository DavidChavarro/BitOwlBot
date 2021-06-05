package main;


import exceptions.NullEnvVarException;
import net.dv8tion.jda.api.*;
//import net.dv8tion.jda.api.entities.Activity.ActivityType;
//import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

//import config.token.TokenAccessor;
import gui.GUI;
import gui.GUIMain;
import guildObjects.GuildList;
import handlers.ConnectionHandler;
import handlers.MemberEventHandler;
import interfaces.InfoRetrieveable;
import interfaces.Loggable;

import java.io.File;
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
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static interfaces.Loadable.RESOURCES_ENV_VAR;

public class Main extends Application implements Loggable, InfoRetrieveable {

	private static JDA masterJDA;
	private static GuildList gl;
	private static final String WINDOW_TITLE = "Control Panel";
	//private Stage menuStage = new Stage();
    private static final String BOT_TOKEN = System.getenv("BITOWL_TOKEN");
	private static GUIMain main;
	@FXML
	private AnchorPane ap = new AnchorPane();
	@FXML
	private Scene home;
	@FXML
	// DO NOT MAKE THE LABEL STATIC OR AN IllegalStateException WILL BE THROWN
	private Label botStatus = new Label("Signing in...");
	@FXML
	private Parent root;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		launch(args);
	}

	
	
	
	
	
	
	
	@Override
	@FXML
	public void start(Stage menuStage) throws Exception {// GUI driver
		DateTimeFormatter USClock = DateTimeFormatter.ofPattern("MM/DD/YYYY");
		LocalDateTime currTime = LocalDateTime.now();
		try {
			//CONVERT ALL GUI OBJECTS TO STATIC REFERENCES
			menuStage.setResizable(true);
			GUI gui = new GUI(masterJDA, menuStage);
			menuStage.setTitle(WINDOW_TITLE + " - " + GUI.getGlobalTitle());
			//URGENT: CREATE A TOKEN READER CLASS THAT READS "token.bof" FILE FROM THE "data/tokens" FOLDER TO MAKE GITHUB REPOSITORY PUBLIC. 
			//THE TOKEN FILE MUST NOT BE COMMITTED TO GITHUB.
			masterJDA = JDABuilder.createDefault(BOT_TOKEN).build();
			
			//COMMIT FINAL VERSION ONCE getToken method is implemented
			//masterJDA = JDABuilder.createDefault(TokenAccessor.getToken()).build();
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
			AnchorPane.setRightAnchor(listButton, main.getScaledSize(215.00));
			AnchorPane.setBottomAnchor(listButton, main.getScaledSize(5.00));
			//System.out.println(System.getenv("BITOWL_RESOURCES"));
			//String mainFXML = getEnvPath(RESOURCES_ENV_VAR);
			//mainFXML += "Main.fxml";
			//root = FXMLLoader.load(Main.class.getClass().getResource(mainFXML));
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
			botStatus.setStyle("-fx-test-fill: green;");
			botStatus.setText("Online.");
		} else {
			botStatus.setStyle("-fx-test-fill: red;");
			botStatus.setText("Offline.");
		}
	}

	//Returns path to home directory.
	private String getEnvPath(String environmentName) throws NullEnvVarException {
        try {
            String dataPath = System.getenv(environmentName);
            assert(dataPath.equals("null") == false);
            if (OPERATING_SYSTEM.equals("Windows")) {
                dataPath+="\\";
            } else {
                dataPath+="/";
            }
            return dataPath;
        } catch (NullPointerException npe) {
            NullEnvVarException ne = new NullEnvVarException(environmentName);
            throw ne;
        }
    }
}
