package unibo.algat.view;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ResourceBundle;

public class UnavailableLessonView extends LessonView {
	public UnavailableLessonView() throws IOException {
		super();

		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/UnavailableLessonView.fxml"),
			ResourceBundle.getBundle("Interface")
		);

		l.setRoot(this);
		l.setController(this);

		l.load();
	}
}

