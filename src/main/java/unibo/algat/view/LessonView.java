package unibo.algat.view;

import javafx.beans.binding.BooleanBinding;
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
		// Common condition disabling a number of tool bar buttons
		final BooleanBinding baseStop = mAlgo.readyProperty().not().or(
			mAlgo.stoppedProperty()
		);

		toolBar.playingProperty().bind(mExecutor.autoRunningProperty());

		// Button disable property section
		toolBar.getStopButton().disableProperty().bind(baseStop);
		toolBar.getPlayButton().disableProperty().bind(baseStop);
		toolBar.getNextButton().disableProperty().bind(
			baseStop.or(mExecutor.autoRunningProperty())
		);

		// Button actions section
		toolBar.getStopButton().setOnAction(e -> mAlgo.cancel());
		toolBar.getPlayButton().setOnAction(
			e -> mExecutor.setAutoRunning(!mExecutor.isAutoRunning())
		);
		toolBar.getNextButton().setOnAction(event -> mExecutor.next());

		// TODO This binding should not happen during the toolbar acquisition.
		//  Another callback method should be defined for intents such as this
		mApp.statusMessageProperty().bind(mAlgo.messageProperty());
	}

	@Override
	public void onReleaseToolBar (AlgaToolBar toolBar) {
		mApp.statusMessageProperty().unbind();
	}
}
