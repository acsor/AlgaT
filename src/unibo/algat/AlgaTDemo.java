package unibo.algat;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlgaTDemo extends Application {
	public static void main (String[] args) {
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) {
		BorderPane root = new BorderPane();
		Scene s = new Scene(root, 450, 300);
		Text title = new Text(
                "This sample demo is to ensure that your Java setup was OK. \n"
				+ "Click the button below, and check the console for new "
				+ "output."
		);
		Button b = new Button();

		b.setText("Greet");
		b.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent actionEvent) {
				System.out.println("Hi dude!");
			}
		});

		root.setTop(title);
		root.setCenter(b);

		primaryStage.setTitle("Hello World");
		primaryStage.setScene(s);
		primaryStage.show();
	}
}
