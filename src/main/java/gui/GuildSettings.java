package gui;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
//import main.Main;
import net.dv8tion.jda.api.JDA;


public class GuildSettings extends GUI {

	public static Stage guildSettings = new Stage();//Stage showing lists of guilds.
	public static AnchorPane ap;
	public static Scene gsMain;//Stage's scene.
	public String windowTitle = "Guild List";//Name shown pertaining to window.
	
	@FXML
	public Button closeBttn = new Button();//Closes window.
	@FXML
	public ScrollPane guilds = new ScrollPane();//New pane where guilds are listed.
	private final static double STAGE_WIDTH = 800;
	private final static double STAGE_HEIGHT = 600;
	private final boolean DEVELOPMENT_FINISHED = false; //Variable determines if window development is not finished.
	//More details shown when selected.
	
	
	
	
	public GuildSettings(JDA bot, Stage main) {
		super(bot, main);//Default constructor for superclass
		//glMain = new Scene(ap, STAGE_WIDTH, STAGE_HEIGHT);//Stage's scene.
		ap = new AnchorPane();
		gsMain = new Scene(ap, STAGE_WIDTH, STAGE_HEIGHT);
		//glMain.getStylesheets().add(GuildList.class.getResource("application.css").toExternalForm());
	}









public GuildSettings() {
	ap = new AnchorPane();
	gsMain = new Scene(ap, STAGE_WIDTH, STAGE_HEIGHT);
	}









@FXML
	public void start() {//Opens guild list stage.
		try {
			if (DEVELOPMENT_FINISHED) {//Shows guild list if the developers have fnished programming window.
			ap = new AnchorPane();
			ap = (AnchorPane) FXMLLoader.load(getClass().getResource("GuildSettings.fxml"));
			gsMain = new Scene(ap, STAGE_WIDTH, STAGE_HEIGHT);
			windowTitle = "Guild Settings";
			closeBttn = new Button();
			closeBttn.setFont(Font.font(GUIMain.getBodyFontSizeStatic()));
			closeBttn.addEventFilter(MouseEvent.MOUSE_CLICKED, closeButton);
			VBox guildBox =  new VBox();
			ScrollPane sp = new ScrollPane();
			DialogPane[] guildDP= new DialogPane[7];
			guildDP[0] = new DialogPane();
			guildDP[0].setHeaderText("Hi");
			guildBox.getChildren().addAll(closeBttn);
			//sp.add(guildDP[0]);
			} else {//Shows temporary menu if development of page is not complete.
				ap = new AnchorPane();
				gsMain = new Scene(ap, 800, 600);
				closeBttn.setText("close window");
				closeBttn.addEventFilter(MouseEvent.MOUSE_CLICKED, closeButton);//Add event filter to close button.
				windowTitle = "Guild Settings (coming soon)";
				Label heading = new Label("This window is under construction.");
				String bodyText = "The guild settings menu is coming soon. Please standby for updates. For any more info, please follow @The Tech Owl on Twitter, or contact him at david.e.chavarro@outlook.com.";
				Label body = new Label(bodyText);
				AnchorPane.setTopAnchor(heading, (STAGE_HEIGHT/2) - 100);
				AnchorPane.setLeftAnchor(heading, 50.00);
				AnchorPane.setBottomAnchor(closeBttn, 10.00);
				AnchorPane.setRightAnchor(closeBttn, 25.00);
				closeBttn.setFont(Font.font(36.00));
				heading.setFont(Font.font("System", FontWeight.BOLD, 36));
				body.setFont(Font.font(36.00));
				body.setMaxWidth(STAGE_WIDTH - 100);
				body.setWrapText(true);
				AnchorPane.setTopAnchor(body, (STAGE_HEIGHT / 2));
				AnchorPane.setLeftAnchor(body, 50.00);
				ap.getChildren().add(closeBttn); //Add close button to scene.
				ap.getChildren().addAll(heading,body);
			}
			guildSettings.setScene(gsMain);
			guildSettings.setTitle(windowTitle + " - " + GLOBAL_TITLE);
			guildSettings.setResizable(false);
			guildSettings.show();//Maximize window
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	public EventHandler<MouseEvent> closeButton = new EventHandler<MouseEvent>() {//Closes window when close button is clicked.
		@Override
		public void handle(MouseEvent event) {
			guildSettings.close();
		}
	};
	
}
