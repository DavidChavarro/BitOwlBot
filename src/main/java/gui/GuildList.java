package gui;

import java.io.IOException;

import interfaces.Loadable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import net.dv8tion.jda.api.JDA;


public class GuildList extends GUI implements Loadable
{

	public Stage guildList = new Stage();//Stage showing lists of guilds.
    private String apFXML = System.getenv(RESOURCES_ENV_VAR);
	public AnchorPane ap = new AnchorPane();
	public Scene glMain;
	public String windowTitle = "Guild List";//Name shown pertaining to window.
	
	@FXML
	public Button closeBttn = new Button("Close Window");//Closes window.
	@FXML
	public ScrollPane guilds = new ScrollPane();//New pane where guilds are listed.
	private final double STAGE_WIDTH = 800;
	private final double STAGE_HEIGHT = 600;
	private final boolean DEVELOPMENT_FINISHED = false; //Variable determines if window development is not finished.
	//More details shown when selected.
	GuildList(JDA bot, Stage main) {
		super(bot, main);//Default constructor for superclass
		try {
			ap = (AnchorPane) FXMLLoader.load(getClass().getResource("GuildList.fxml"));
			glMain.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glMain = new Scene(ap, STAGE_WIDTH, STAGE_HEIGHT);//Stage's scene.
	}
@FXML
	public void start() {//Opens guild list stage.
		try {
			guildList.setScene(glMain);
			guildList.setTitle(windowTitle + " - " + GLOBAL_TITLE);
			guildList.setResizable(false);
			ap.getChildren().add(closeBttn); //Add close button to scene.
			closeBttn.addEventFilter(MouseEvent.MOUSE_CLICKED, closeButton);//Add event filter to close button.
			if (DEVELOPMENT_FINISHED) {//Shows guild list if the developers have fnished programming window.
			ap = (AnchorPane) FXMLLoader.load(getClass().getResource("GuildList.fxml"));
			Label test = new Label();
			 VBox guildBox =  new VBox();
			 ScrollPane sp = new ScrollPane();
			 guildBox.getChildren().addAll(sp);
			} else {
				windowTitle = "Guild list (Coming Soon)";
				Label heading = new Label("This window is under construction.");
				String bodyText = "The guild list feature is coming soon. Please standby for updates. For any more info, please follow @The Tech Owl on Twitter, or contact him at david.e.chavarro@outlook.com.";
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
				ap.getChildren().addAll(heading,body);
			}
			guildList.show();//Maximize window
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	
	public EventHandler<MouseEvent> closeButton = new EventHandler<MouseEvent>() {//Closes window when close button is clicked.
		@Override
		public void handle(MouseEvent event) {
			guildList.close();
		}
	};


}
