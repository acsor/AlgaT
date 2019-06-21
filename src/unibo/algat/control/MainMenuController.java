package unibo.algat.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainMenuController {
	@FXML MenuItem mCloseTab;

	@FXML
	private void onExit () {
		System.exit(0);
	}

	@FXML
	private void onShowCredits () throws IOException {
		// TO-DO Ensure there are no better alternatives (e.g. dialog) to
		// display the credits screen
		Stage dialog = new Stage(StageStyle.DECORATED);
		Parent layout = FXMLLoader.load(
				getClass().getResource("/res/view/CreditsDialog.fxml")
		);
		Scene s = new Scene(layout);

		dialog.setScene(s);
		dialog.setTitle("Credits");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
	}
}
