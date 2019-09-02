package unibo.algat.view;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import unibo.algat.AlgaTApplication;
import unibo.algat.algorithm.SerialAlgorithm;
import unibo.algat.algorithm.SerialExecutor;
import unibo.algat.lesson.Lesson;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>Top-most abstract base class for algorithm views. {@code LessonView}
 * holds a reference to a {@link SerialAlgorithm}, whose instantiation is
 * defined by concrete classes in {@link #algorithmFactory()}.</p>
 * <p><b>Note:</b> a user giving an implementation of a
 * {@link SerialAlgorithm} must take care of setting the
 * {@link SerialAlgorithm#readyProperty() readyProperty} appropriately, allowing
 * {@code LessonView} and its subclasses behave accordingly.</p>
 */
public abstract class LessonView extends SplitPane implements ToolBarUser {
	protected ObjectProperty<Lesson> mLesson;
	protected SerialAlgorithm<?> mAlgo;
    protected SerialExecutor mExecutor;

    protected AlgaTApplication mApp;
	protected ResourceBundle mInterface;

	protected LessonInfoView mInfoView;
	protected Label mTitle;
	private VBox mCenter;
	protected QuizView mQuizView;

	private static final double CHILD_PADDING = 14;

	public LessonView () throws IOException {
		// Note that LessonView doesn't load any FXML file -- apparently,
		// Java FX wasn't making this possible for some reason
		mLesson = new SimpleObjectProperty<>(this, "lesson");
		mAlgo = algorithmFactory();
		mExecutor = new SerialExecutor(mAlgo, 1000);

		mInterface = ResourceBundle.getBundle("Interface");
		mApp = AlgaTApplication.getInstance();

		mInfoView = new LessonInfoView();
		mTitle = new Label();
		mCenter = new VBox(mTitle);
		mQuizView = new QuizView();

		getStyleClass().add("lesson-view");
		mTitle.getStyleClass().add("lesson-view-title");

		getItems().addAll(mInfoView, mCenter, mQuizView);
		setDividerPositions(.25, .75);

		// TODO I'd rather prefer setting this padding with CSS, although I
		//  wasn't able to exploit smart selectors (because they may be
		//  missing?)
		mInfoView.setPadding(new Insets(CHILD_PADDING));
		mCenter.setPadding(new Insets(CHILD_PADDING));
		mCenter.setSpacing(10);
		mQuizView.setPadding(new Insets(CHILD_PADDING));
	}

	/**
	 * @return A {@link SerialAlgorithm} instance defining the behavior of
	 * the simulation upon running.
	 */
	protected abstract SerialAlgorithm<?> algorithmFactory();

	public void setLesson(Lesson lesson) {
		mLesson.set(lesson);

		mInfoView.setLesson(lesson);
		mTitle.setText(lesson != null ? lesson.getName(): "");
		mQuizView.setLesson(lesson);
	}

	public Lesson getLesson() {
		return mLesson.get();
	}

	public void setCenter (Node node) {
		final int size = mCenter.getChildren().size();

		if (size == 1) {
			mCenter.getChildren().add(node);
		} else if (size == 2) {
			mCenter.getChildren().set(1, node);
		} else {
			throw new IllegalStateException(
				"The center pane only allows two elements at most"
			);
		}
	}

	public Node getCenter () {
		return mCenter.getChildren().get(1);
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
