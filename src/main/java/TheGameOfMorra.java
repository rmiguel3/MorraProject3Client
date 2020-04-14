import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;

public class TheGameOfMorra extends Application {

	TextField s1,s2,s3,s4, c1;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	MorraInfo morraInfo = new MorraInfo();
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	MorraClient clientConnection;

	ListView<String> listItems2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	// push test
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("(Client) Let's Play Morra!!!");

		clientConnection = new MorraClient(data->{
			Platform.runLater(()->{listItems2.getItems().add(data.toString());
			});
		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		listItems2 = new ListView<String>();
		c1 = new TextField();
		b1 = new Button("Send");
		b1.setOnAction(e->{
			clientConnection.send(c1.getText());
			c1.clear();
		});

		clientConnection.start();
		clientBox = new VBox(10, c1,b1,listItems2);
		clientBox.setStyle("-fx-background-color: blue");
		Scene scene = new Scene(clientBox,500,400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
