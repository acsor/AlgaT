package unibo.algat.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainMenuController {
	@FXML MenuItem mCloseTab;

	private final ResourceBundle mInterface;

	public MainMenuController() {
		mInterface = ResourceBundle.getBundle("res.Interface");
	}

	@FXML
	private void onExit () {
		System.exit(0);
	}

	@FXML
	private void onShowCredits () throws IOException {
		// TO-DO Ensure there are no better alternatives (e.g. dialog) to
		// display the credits screen
		final Stage dialog = new Stage(StageStyle.DECORATED);
		Parent layout = FXMLLoader.load(
			getClass().getResource("/res/view/CreditsDialog.fxml")
		);
		Scene s = new Scene(layout);

		dialog.setScene(s);
		dialog.setTitle(mInterface.getString("gui.dialog.credits.title"));
        dialog.getIcons().add(new Image(
			getClass().getResourceAsStream("/res/static/logo-small.png")
		));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
	}
}
