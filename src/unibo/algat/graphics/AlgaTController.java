package unibo.algat.graphics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class AlgaTController {
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab lessonTab;

	public void initialize() throws IOException {
		final ResourceBundle r = ResourceBundle.getBundle("res.Interface");
		// TODO Is this the JavaFX-approved way of programmatically loading
		//  content from FXML files? Check it.
		final Parent lessonsView = FXMLLoader.load(
			getClass().getResource("/res/LessonsView.fxml"),
			ResourceBundle.getBundle("res.Interface")
		);

		lessonTab.setContent(lessonsView);
	}
}
