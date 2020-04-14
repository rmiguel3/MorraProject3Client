import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

		answerBox = new TextField("");
		// prevent user from being able to enter more than 4 characters for port
		answerBox.setTextFormatter(new TextFormatter<String>(change ->
				change.getControlNewText().length() <= 2 ? change : null));

		submitButton = new Button("Submit");
		submitButton.setOnAction(e->{clientConnection.send(answerBox.getText()); answerBox.clear();});

		clientConnection.start();

		Pane mainScenePane = new Pane();
		mainScenePane.setBackground(new Background(new BackgroundImage(new Image("Borobudur_Temple.jpg", 800, 450, false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,  BackgroundSize.DEFAULT)));

		// buttons for guessing
		ImageView stone1 = new ImageView(new Image("stone1.png", 100, 100, false, true));
		ImageView stone2 = new ImageView(new Image("stone2.png", 100, 100, false, true));
		ImageView stone3 = new ImageView(new Image("stone3.png",100, 100, false, true));
		ImageView stone4 = new ImageView(new Image("stone4.png", 100, 100, false, true));
		ImageView stone5 = new ImageView(new Image("stone5.png", 100, 100, false, true));

		HBox guessImages = new HBox();
		guessImages.getChildren().addAll(stone1, stone2, stone3, stone4, stone5);

		mainScenePane.getChildren().addAll(answerBox, submitButton, listItems2, guessImages);
		answerBox.relocate(100,200);
		submitButton.relocate(100, 240);
		listItems2.relocate(520, 25);
		guessImages.relocate(0, 335);


		// implement clicking functionality for buttons
		stone1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone2.setDisable(true);
				stone2.setVisible(false);

				stone3.setDisable(true);
				stone3.setVisible(false);

				stone4.setDisable(true);
				stone4.setVisible(false);

				stone5.setDisable(true);
				stone5.setVisible(false);

				clientConnection.send("1");
			}
		});

		stone2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone1.setDisable(true);
				stone1.setVisible(false);

				stone3.setDisable(true);
				stone3.setVisible(false);

				stone4.setDisable(true);
				stone4.setVisible(false);

				stone5.setDisable(true);
				stone5.setVisible(false);

				clientConnection.send("2");
			}
		});

		stone3.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone2.setDisable(true);
				stone2.setVisible(false);

				stone1.setDisable(true);
				stone1.setVisible(false);

				stone4.setDisable(true);
				stone4.setVisible(false);

				stone5.setDisable(true);
				stone5.setVisible(false);

				clientConnection.send("3");
			}
		});

		stone4.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone2.setDisable(true);
				stone2.setVisible(false);

				stone3.setDisable(true);
				stone3.setVisible(false);

				stone1.setDisable(true);
				stone1.setVisible(false);

				stone5.setDisable(true);
				stone5.setVisible(false);

				clientConnection.send("4");
			}
		});

		stone5.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone2.setDisable(true);
				stone2.setVisible(false);

				stone3.setDisable(true);
				stone3.setVisible(false);

				stone4.setDisable(true);
				stone4.setVisible(false);

				stone1.setDisable(true);
				stone1.setVisible(false);

				clientConnection.send("5");
			}
		});

		clientConnection.send(answerBox.getText());

		Scene scene = new Scene(mainScenePane,800,450);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
