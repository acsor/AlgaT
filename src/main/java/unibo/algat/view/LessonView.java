package unibo.algat.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import unibo.algat.control.LessonViewFactory;
import unibo.algat.lesson.Lesson;

import java.io.IOException;
import java.util.ResourceBundle;

public class LessonView extends BorderPane {
	private Lesson mLesson;

	@FXML private Label mTitle;
	@FXML private ExecutionControls mControls;

	public LessonView (Lesson lesson) throws IOException {
		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/LessonView.fxml"),
			ResourceBundle.getBundle("Interface")
		);
		mLesson = lesson;

		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	private void initialize () throws IOException {
		final Node lessonView = LessonViewFactory.lessonView(mLesson);

        mTitle.setText(mLesson.getName());
        // TODO Handle exception by displaying the error to the user
        setCenter(lessonView);
        setAlignment(lessonView, Pos.CENTER);
	}
}
