import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;

import static javafx.application.Application.launch;

public class TheGameOfMorra extends Application {
	// declare start screen variables
	TextField portBox;
	Text portText;
	TextField ipBox;
	Text ipText;
	Button openingScreenButton;
	Scene startScene;

	// main screen variables
	TextField answerBox;
	Button submitButton;
	Pane mainScenePane;
	Scene mainScene;
	Text player1Score;
	Text player2Score;
	Text opponentPlay;

	MorraClient clientConnection;
	HashMap<String, Scene> sceneMap = new HashMap<>();
	ListView<String> listItems2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("(Client) Let's Play Morra!!!");

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

		// set up start screen
		portBox = new TextField();

		// prevent user from being able to enter more than 4 characters for port
		portBox.setTextFormatter(new TextFormatter<String>(change ->
				change.getControlNewText().length() <= 4 ? change : null));

		portText = new Text("Enter a port:");
		portText.setFont(Font.font ("Verdana", 20));
		portText.setStyle("-fx-font-weight: bold");
		portText.setFill(Color.INDIGO);

		ipBox = new TextField();
		ipText = new Text("Enter an IP:");
		ipText.setFont(Font.font ("Verdana", 20));
		ipText.setStyle("-fx-font-weight: bold");
		ipText.setFill(Color.INDIGO);

		openingScreenButton = new Button("Let's play!");
		Pane startPane = new Pane();

		startPane.setBackground(new Background(new BackgroundImage(new Image("startScreen.png", 532, 720, false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,  BackgroundSize.DEFAULT)));

		startPane.getChildren().addAll(ipText, ipBox, portText, portBox, openingScreenButton);
		portText.relocate(185, 200);
		portBox.relocate(165,230);
		ipText.relocate(190,300);
		ipBox.relocate(165, 330);
		openingScreenButton.relocate(210, 400);

		// set up main screen
		listItems2 = new ListView<String>();
		listItems2.setStyle("-fx-font-family: Verdana; -fx-font-weight: bold");
		listItems2.setPrefSize(390,475);

		answerBox = new TextField();

		submitButton = new Button("Submit guess");
		submitButton.setOnAction(e->{clientConnection.send(answerBox.getText()); answerBox.clear();});

		mainScenePane = new Pane();
		mainScenePane.setBackground(new Background(new BackgroundImage(new Image("Borobudur_Temple.jpg", 1100, 495, false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,  BackgroundSize.DEFAULT)));

		// buttons for guessing
		ImageView stone0 = new ImageView(new Image("stone0.png", 100, 100, false, true));
		ImageView stone1 = new ImageView(new Image("stone1.png", 100, 100, false, true));
		ImageView stone2 = new ImageView(new Image("stone2.png", 100, 100, false, true));
		ImageView stone3 = new ImageView(new Image("stone3.png",100, 100, false, true));
		ImageView stone4 = new ImageView(new Image("stone4.png", 100, 100, false, true));
		ImageView stone5 = new ImageView(new Image("stone5.png", 100, 100, false, true));

		HBox guessImages = new HBox(18, stone0, stone1, stone2, stone3, stone4, stone5);

		player1Score = new Text("Player 1 score:");
		player1Score.setFont(Font.font ("Verdana", 20));
		player1Score.setStyle("-fx-font-weight: bold");
		player1Score.setFill(Color.INDIGO);

		player2Score = new Text("Player 2 score:");
		player2Score.setFont(Font.font ("Verdana", 20));
		player2Score.setStyle("-fx-font-weight: bold");
		player2Score.setFill(Color.INDIGO);

		opponentPlay = new Text("Opponent played:");
		opponentPlay.setFont(Font.font ("Verdana", 20));
		opponentPlay.setStyle("-fx-font-weight: bold");
		opponentPlay.setFill(Color.INDIGO);

		mainScenePane.getChildren().addAll(answerBox, submitButton, listItems2, guessImages, player1Score, player2Score, opponentPlay);
		answerBox.relocate(10,200);
		submitButton.relocate(10, 240);
		listItems2.relocate(700, 10);
		guessImages.relocate(0, 380);
		opponentPlay.relocate(5, 10);
		player1Score.relocate(275,10);
		player2Score.relocate(275, 50);


		// implement clicking functionality for buttons

		// "Let's play!" button
		openingScreenButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// display main game once port and ip are entered
				clientConnection = new MorraClient(data->{
						Platform.runLater(()->{listItems2.getItems().add(data.toString());
							int lastMessage = listItems2.getItems().size();
							listItems2.scrollTo(lastMessage);
						});
					}, Integer.parseInt(portBox.getText()), ipBox.getText());

				clientConnection.start();

				sceneMap.put("main screen", new Scene(mainScenePane,1100,495));
				primaryStage.setScene(sceneMap.get("main screen"));

			}
		});

		// "finger" choices
		stone0.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone1.setDisable(true);
				stone1.setVisible(false);

				stone2.setDisable(true);
				stone2.setVisible(false);

				stone3.setDisable(true);
				stone3.setVisible(false);

				stone4.setDisable(true);
				stone4.setVisible(false);

				stone5.setDisable(true);
				stone5.setVisible(false);

				clientConnection.send("0");
			}
		});

		stone1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stone0.setDisable(true);
				stone0.setVisible(false);

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
				stone0.setDisable(true);
				stone0.setVisible(false);

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
				stone0.setDisable(true);
				stone0.setVisible(false);

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
				stone0.setDisable(true);
				stone0.setVisible(false);

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
				stone0.setDisable(true);
				stone0.setVisible(false);

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


		// show the start screen
		sceneMap.put("start screen", new Scene(startPane, 532, 720));
		primaryStage.setScene(sceneMap.get("start screen"));
		primaryStage.show();
	}

}
