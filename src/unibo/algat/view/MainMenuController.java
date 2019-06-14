package unibo.algat.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainMenuController {
	public void onExit () {
		System.exit(0);
	}

	public void onShowCredits () throws IOException {
		// TO-DO Ensure there are no better alternatives (e.g. dialog) to
		// display the credits screen
		Stage dialog = new Stage(StageStyle.DECORATED);
		Parent layout = FXMLLoader.load(
				getClass().getResource("/res/CreditsDialog.fxml")
		);
		Scene s = new Scene(layout);

		dialog.setScene(s);
		dialog.setTitle("Credits");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
	}
}
