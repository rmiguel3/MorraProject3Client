import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;

public class TheGameOfMorra extends Application {

	TextField answerBox;
	Button submitButton;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
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
		listItems2.setStyle("-fx-font-family: Verdana; -fx-font-weight: bold");
		answerBox = new TextField("Enter your guess");
		submitButton = new Button("Submit");
		submitButton.setOnAction(e->{clientConnection.send(answerBox.getText()); answerBox.clear();});

		clientConnection.start();

		Pane mainScenePane = new Pane();
		mainScenePane.setBackground(new Background(new BackgroundImage(new Image("Borobudur_Temple.jpg", 800, 450, false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,  BackgroundSize.DEFAULT)));

		mainScenePane.getChildren().addAll(answerBox, submitButton, listItems2);
		answerBox.relocate(100,200);
		listItems2.relocate(520, 25);


		Scene scene = new Scene(mainScenePane,800,450);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
