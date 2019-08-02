package unibo.algat.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import unibo.algat.AlgaTApplication;
import unibo.algat.algorithm.SerialAlgorithm;
import unibo.algat.algorithm.SerialExecutor;
import unibo.algat.lesson.Lesson;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class LessonView extends BorderPane implements ToolBarUser {
	protected ObjectProperty<Lesson> mLesson;
	protected SerialAlgorithm<?> mAlgo;
    protected SerialExecutor mExecutor;

    protected AlgaTApplication mApp;
	protected ResourceBundle mInterface;

	protected Label mTitle;
	protected QuizView mQuizView;

	public LessonView () throws IOException {
		mLesson = new SimpleObjectProperty<>(this, "lesson");
		mAlgo = algorithmFactory();
		mExecutor = new SerialExecutor(mAlgo, 1000);

		mInterface = ResourceBundle.getBundle("Interface");
		mApp = AlgaTApplication.getInstance();

		// Node that LessonView doesn't load any FXML file -- apparently,
		// Java FX wasn't making this possible for some reason
		mTitle = new Label();
		mQuizView = new QuizView();

		getStyleClass().add("lesson-view");
		mTitle.getStyleClass().add("lesson-view-title");

		setTop(mTitle);
		setRight(mQuizView);
	}

	/**
	 * @return A {@link SerialAlgorithm} instance defining the behavior of
	 * the lesson upon running.
	 */
	protected abstract SerialAlgorithm<?> algorithmFactory();

	public void setLesson(Lesson lesson) {
		mLesson.set(lesson);

		if (lesson != null) {
			mTitle.setText(lesson.getName());
			mQuizView.setLesson(lesson);
		}
	}

	public Lesson getLesson() {
		return mLesson.get();
	}

	public ObjectProperty<Lesson> lessonProperty () {
		return mLesson;
	}

	@Override
	public void onAcquireToolBar(AlgaToolBar toolBar) {
		toolBar.playingProperty().bind(mExecutor.autoRunningProperty());

		toolBar.getStopButton().disableProperty().bind(mAlgo.stoppedProperty());
		toolBar.getStopButton().setOnAction(e -> mAlgo.cancel());

		toolBar.getPlayButton().disableProperty().bind(mAlgo.stoppedProperty());
		toolBar.getPlayButton().setOnAction(
			e -> mExecutor.setAutoRunning(!mExecutor.isAutoRunning())
		);

		// Disable the next button if it simply cannot execute due to the
		// background thread or because the auto run feature is on
		toolBar.getNextButton().disableProperty().bind(
			mAlgo.stoppedProperty().or(mExecutor.autoRunningProperty())
		);
		toolBar.getNextButton().setOnAction(event -> mExecutor.next());

		mApp.statusMessageProperty().bind(mAlgo.messageProperty());
	}

	@Override
	public void onReleaseToolBar (AlgaToolBar toolBar) {
		mApp.statusMessageProperty().unbind();
	}
}
