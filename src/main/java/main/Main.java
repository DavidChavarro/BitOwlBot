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

    private commandShell shell;
	private static JDA masterJDA;
	private static GuildList gl;
	private static final String WINDOW_TITLE = "Control Panel";
	//private Stage menuStage = new Stage();
    private static final String BOT_TOKEN = System.getenv("BITOWL_TOKEN");
	private static GUIMain main;
    private DateTimeFormatter USClock = DateTimeFormatter.ofPattern("MM/DD/YYYY");
    private static LocalDateTime currTime;
	@FXML
	private AnchorPane ap = new AnchorPane();
	@FXML
	private Scene home;
	@FXML
	// DO NOT MAKE THE LABEL STATIC OR AN IllegalStateException WILL BE THROWN
	private Label botStatus = new Label("Signing in...");
	@FXML
	private Parent root;
	
	public static synchronized void main(String[] args) throws IOException, InterruptedException {
        try
        {
            masterJDA = JDABuilder.createDefault(BOT_TOKEN).build();
            if (args[0].equals("--cli") || args[0].equals("-c")) {
                commandShell shell = new commandShell();
                masterJDA.addEventListener(shell);
                MemberEventHandler memberEvent = new MemberEventHandler(masterJDA);
                masterJDA.addEventListener(memberEvent);
                masterJDA.getPresence().setPresence(OnlineStatus.ONLINE,
                        Activity.playing(VERSION_PREFIX + " version " + VERSION));
                gl = new GuildList(masterJDA.getGuilds());
                gl.updateGuildList();
                masterJDA.awaitReady();
                currTime = LocalDateTime.now();
                System.out.println("BitOwl has signed in to Discord at " + currTime.getHour() + ":" + currTime.getMinute() + ".");
            } else {
                launch(args);
            }

        } catch (LoginException le) {
            System.out.print("The token could not be verified. Please check that your system variables are set up. ");
            System.out.println("If you need the token, please contact david.e.chavarro@outlook.com.");
            System.out.println("Printing stack trace...");
            Thread.sleep(500);
            le.printStackTrace();
            System.out.println("\nTerminating program...\n");
        }


	}

	
	
	
	
	
	
	
	@Override
	@FXML
	public void start(Stage menuStage) throws Exception {// GUI driver
        gl = new GuildList(masterJDA.getGuilds());
        gl.updateGuildList();
	    main = new GUIMain(masterJDA, menuStage);
        masterJDA.awaitReady();
        currTime = LocalDateTime.now();
        System.out.println("BitOwl has signed in to Discord at " + currTime.getHour() + ":" + currTime.getMinute() + ".");
	}



	public static GuildList getGuildList() {
	    return gl;
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

    public static JDA getBot() {
	    return masterJDA;
    }
}
