package unibo.algat.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import unibo.algat.AlgaTApplication;
import unibo.algat.lesson.Lesson;

import java.io.IOException;

public abstract class LessonView extends BorderPane {
	protected Lesson mLesson;

	protected Label mTitle;
	protected QuizView mQuizView;
	protected AlgaToolBar mToolBar;

	public LessonView () throws IOException {
		// TODO It looks like defining double-nested custom controls through
		//  FXML yields some problems, particularly with fetching elements
		//  through fx:id. For this reason, LessonView FXML file was deleted,
		//  to let its subclasses have one. One should try to have LessonView
		//  posses one too.
		mTitle = new Label();
		mQuizView = new QuizView();
		mToolBar = AlgaTApplication.getInstance().getToolBar();

		mTitle.setFont(new Font("Arial", 23));
		mToolBar.getTogglePlayButton().setOnAction(
			e -> System.out.println("Toggle play button pressed!")
		);

		setTop(mTitle);
		setRight(mQuizView);
		setPadding(new Insets(16));
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
