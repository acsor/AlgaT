package unibo.algat.view;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class CreditsDialogController {
	@FXML
	private WebView creditsView;

	@FXML
    private void initialize () {
		// TODO Have links in the WebView open in an external browser
		creditsView.getEngine().load(
			getClass().getResource("/res/static/CreditsDialog.html")
				.toExternalForm()
		);
	}

}
