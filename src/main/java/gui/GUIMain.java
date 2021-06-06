package gui;

import handlers.ConnectionHandler;
import interfaces.InfoRetrieveable;
import javafx.event.EventHandler;
//import javafx.scene.Parent;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import net.dv8tion.jda.api.JDA;

public class GUIMain extends GUI implements InfoRetrieveable {

    private final String WINDOW_TITLE = "Control Panel";

    @FXML
    protected Label botStatus = new Label("Signing in...");
    @FXML
    private AnchorPane ap = new AnchorPane();

    @FXML
	protected ConnectionHandler ch;

    @FXML
    private Scene home;
	
	public GUIMain(JDA bot, Stage main) throws InterruptedException
    {
		super(bot, main);
		ch = new ConnectionHandler(botStatus, bot);
		run();
	}

	//Initializes all the GUI objects and button handlers, then displays the main window.
	private void run()
    {
        stage.setResizable(true);
        stage.setTitle(WINDOW_TITLE + " - " + getGlobalTitle());
        bot.addEventListener(ch);
        AnchorPane.setLeftAnchor(botStatus, getScaledSize(10.0));
        AnchorPane.setBottomAnchor(botStatus, getScaledSize(5.00));
        botStatus.setFont(Font.font(getBodyFontSize()));
        botStatus.setMinWidth(getScaledSize(400));
        botStatus.setMinHeight(getScaledSize(100));
        Button shutdownBot = new Button("Shutdown bot");
        AnchorPane.setBottomAnchor(shutdownBot, getScaledSize(5.00));
        AnchorPane.setRightAnchor(shutdownBot, getScaledSize(10.00));
        shutdownBot.setFont(Font.font(getBodyFontSize()));
        Button listButton = new Button("Guild lists");
        listButton.setFont(Font.font(getBodyFontSize()));
        AnchorPane.setRightAnchor(listButton, getScaledSize(215.00));
        AnchorPane.setBottomAnchor(listButton, getScaledSize(5.00));
        //System.out.println(System.getenv("BITOWL_RESOURCES"));
        //String mainFXML = getEnvPath(RESOURCES_ENV_VAR);
        //mainFXML += "Main.fxml";
        //root = FXMLLoader.load(Main.class.getClass().getResource(mainFXML));
        ap.getChildren().addAll(botStatus, shutdownBot, listButton);
        shutdownBot.addEventFilter(MouseEvent.MOUSE_CLICKED, shutdownButton);
        listButton.addEventFilter(MouseEvent.MOUSE_CLICKED, listButtonHandler);
        home = setSceneResolution(this.home, ap);
        stage.setScene(home);
        System.out.println("\nMain method is updating label...\n");
        botStatus.setStyle("-fx-text-fill: green;");
        botStatus.setText("Connected to " + Main.getGuildList().toString());
        botStatus.setMaxWidth(getScaledSize(2100));
        botStatus.setWrapText(true);
        stage.show();

    }

	@SuppressWarnings("exports")
	public EventHandler<MouseEvent> shutdownButton = new EventHandler<MouseEvent>() {//Runs shutdownBot method when
		//"shutdown bot" button is clicked.
		@Override
		public void handle(MouseEvent event) {
			shutdownBot();
		}
	};

	//Opens the guild list window when corresponding button is clicked.
	public EventHandler<MouseEvent> listButtonHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			GuildSettings guildList = new GuildSettings();
			guildList.start();
		}
	};

	//Shuts downs the program.
	private void shutdownBot() {
		bot.shutdown();
		stage.close();
	}
	
	
	//Sets an appropriate window resolution based on the Operating System it runs.
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

	//Scales the window size based on the operating system it runs.
	public double getScaledSize(double maxSize) {
		if (OPERATING_SYSTEM.equals("Linux")) {
			return maxSize;
		} else {
			return maxSize / 2;
		}
	}


	//Returns the body font size based on the operating system it runs.
	public int getBodyFontSize() {
		if (OPERATING_SYSTEM.equals("Linux")) {
			return 24;
		} else {
			return 12;}
		}
    //Returns the body font size based on the operating system it runs.
    //Method is static.
	public static int getBodyFontSizeStatic() {
		if (OPERATING_SYSTEM.equals("Linux")) {
			return 24;
		} else {
			return 12;}
		}



}
