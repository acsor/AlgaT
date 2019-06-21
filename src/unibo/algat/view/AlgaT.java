package unibo.algat.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class AlgaT extends Application {
	@Override
	public void start (Stage stage) throws IOException {
		Parent root = FXMLLoader.load(
			getClass().getResource("/res/view/AlgaT.fxml"),
			ResourceBundle.getBundle("res.Interface")
		);
		Scene s = new Scene(root, 500, 500);

		stage.setScene(s);
		stage.setTitle("AlgaT");
		stage.getIcons().add(new Image(
			getClass().getResourceAsStream("/res/static/logo-small.png")
		));
		stage.show();
	}

	public static void main (String[] args) {
		launch(args);
	}
}
