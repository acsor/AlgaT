package unibo.algat.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import unibo.algat.AlgaTApplication;
import unibo.algat.lesson.Lesson;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class LessonView extends BorderPane {
	protected Lesson mLesson;

	@FXML protected Label mTitle;
	@FXML protected QuizView mQuizView;
	protected AlgaToolBar mToolBar;

	public LessonView () throws IOException {
		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/LessonView.fxml"),
			ResourceBundle.getBundle("Interface")
		);

		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	protected void initialize () {
		mToolBar = AlgaTApplication.getInstance().getToolBar();

		mToolBar.getTogglePlayButton().setOnAction(
            e -> System.out.println("Toggle play button pressed!")
		);
	}

	public void setLesson(Lesson lesson) {
		mLesson = lesson;

		if (mLesson != null) {
			mTitle.setText(mLesson.getName());
			mQuizView.setLesson(mLesson);
		}
	}

	public Lesson getLesson() {
		return mLesson;
	}
}
