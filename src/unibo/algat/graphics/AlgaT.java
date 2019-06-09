package unibo.algat.graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class AlgaT extends Application {
	@Override
	public void start (Stage stage) throws IOException {
		Parent root = FXMLLoader.load(
			getClass().getResource("/res/AlgaT.fxml"),
			ResourceBundle.getBundle("res.Interface")
		);
		Scene s = new Scene(root, 500, 500);

		stage.setScene(s);
		stage.setTitle("AlgaT");
		stage.show();
	}
}
