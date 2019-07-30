package unibo.algat.view;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ResourceBundle;

public class UnavailableLessonView extends LessonView implements ToolBarUser {
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

	@Override
	public void onAcquireToolBar(AlgaToolBar toolBar) {
	}

	@Override
	public void onReleaseToolBar(AlgaToolBar toolBar) {
	}
}

