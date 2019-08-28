package unibo.algat.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import unibo.algat.AlgaTApplication;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.Question;
import unibo.algat.lesson.QuestionLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <p>Graphical component displaying quizzes for a given {@link Lesson}.</p>
 */
public class QuizView extends VBox {
	private Lesson mLesson;
	private List<Question> mQuestions;
	/**
	 * <p>An Integer property storing the Question currently displayed.</p>
	 */
	private int mCurrQuestion = 0;
	private QuestionLoader mQLoader;

	private ResourceBundle mInterface;

	@FXML private Image mCorrectImage, mWrongImage;
	@FXML private Label mText;
	@FXML private Label mEmpty;
	@FXML private VBox mChoiceView;
	private ToggleGroup mChoiceGroup;
	@FXML private ImageView mAnswerIndicator;
	@FXML private Button mAccept;
	@FXML private Button mPrev, mNext;
	@FXML private Label mPos;

	/**
	 * Handles reactions to the presses {@link #mPrev} and {@link #mNext}
	 * receive.
	 */
	private final EventHandler<ActionEvent>
		mPrevNextHandler = new EventHandler<>() {
		@Override
		public void handle(ActionEvent event) {
            if (event.getSource() == mPrev) {
            	setShownQuestion(mCurrQuestion - 1);
			} else if (event.getSource() == mNext) {
				setShownQuestion(mCurrQuestion + 1);
			}
		}
	};
	/**
	 * Monitors the selection to the group of possible answers the user is
	 * displayed.
	 */
	private final ChangeListener<Toggle>
		mToggleListener = new ChangeListener<>() {
		@Override
		public void changed(
			ObservableValue<? extends Toggle> observable, Toggle oldValue,
			Toggle newValue) {
			// If an option is selected, the user must be given the possibility
			// to submit it -- activate the mAccept button
			mAccept.setDisable(newValue == null);
		}
	};
	/**
	 * Reacts to presses received by {@link #mAccept}.
	 */
	private final EventHandler<ActionEvent>
		mAcceptHandler = new EventHandler<>() {
		@Override
		public void handle(ActionEvent event) {
			if (mChoiceGroup.getSelectedToggle() != null) {
				Question.Choice answer = (Question.Choice) mChoiceGroup.
					getSelectedToggle().getUserData();

				if (answer != null) {
					mQuestions.get(mCurrQuestion).setSubmittedAnswer(answer);
					updateView();
				}
			}
		}
	};

	public QuizView () throws IOException {
		// TODO This class might be implemented by following the Java Properties
		//  paradigm instead of relying on the huge updateView() method
		FXMLLoader loader;

		mQuestions = new ArrayList<>(0);
		mQLoader = new QuestionLoader(AlgaTApplication.QUESTIONS_DIR);
		mInterface = ResourceBundle.getBundle("Interface");
		mChoiceGroup = new ToggleGroup();

		mChoiceGroup.selectedToggleProperty().addListener(mToggleListener);

		loader = new FXMLLoader(
            getClass().getResource("/view/QuizView.fxml"), mInterface
		);
        loader.setRoot(this);
        loader.setController(this);

        loader.load();
	}

	@FXML
	private void initialize () {
        mAccept.setOnAction(mAcceptHandler);
		mPrev.setOnAction(mPrevNextHandler);
		mNext.setOnAction(mPrevNextHandler);

		setShownQuestion(0);
	}

	/**
	 * @param lesson The {@link Lesson} this {@code QuizView} should display
	 * quizzes for.
	 */
	public void setLesson (Lesson lesson) {
		mLesson = lesson;

		if (mLesson != null) {
			mQuestions = new ArrayList<>(mQLoader.questions(mLesson));
			setShownQuestion(0);
		}
	}

	/**
	 * @return The {@link Lesson} this {@code QuizView} is displaying quizzes
	 * for, {@code null} if there are none.
	 */
	public Lesson getLesson () {
		return mLesson;
	}

	/**
	 * <p>Sets the question to display.</p>
	 * <p>This method acts in such a way to select a question
	 * only if it is allowed to do so, that is all the ones before it have been
	 * previously answered.</p>
	 *
	 * @param index Index of the question to be shown. If {@code < 0}, if
	 * defaults to {@code 0}; if {@code >= questions size}, it is set to the
	 *                 index of the last question.
	 */
	public void setShownQuestion (int index) {
        if (index < 0) {
			mCurrQuestion = 0;
		} else if (mQuestions.size() <= index) {
			mCurrQuestion = Math.max(0, mQuestions.size() - 1);
		} else {
        	if (index <= mCurrQuestion) {
        		mCurrQuestion = index;
			} else if (mQuestions.get(index - 1).getSubittedAnswer() != null) {
                mCurrQuestion = index;
			}
		}

		updateView();
	}

	/**
	 * @return The index associated to the {@link Question} currently shown,
	 * {@code -1} if none is selected at the time of this calling.
	 */
	public int getShownQuestion () {
		return mCurrQuestion;
	}

	/**
	 * <p>Updates the display of this {@code QuizView}, such that the
	 * currently selected question content is displayed.</p>
	 * <p>If there are questions to display, one of them <b>must</b> be
	 * chosen; otherwise, i.e. the list of questions is empty, a placeholder
	 * will be used instead.</p>
	 */
	private void updateView () {
        // Clear up previous content
		mChoiceView.getChildren().clear();
		mChoiceGroup.getToggles().clear();

		mPrev.setDisable(mCurrQuestion <= 0);
		mNext.setDisable(!canAdvanceTo(mCurrQuestion + 1));

		// If there is a question to display:
		if (0 <= mCurrQuestion && mCurrQuestion < mQuestions.size()) {
			Question selected = mQuestions.get(mCurrQuestion);
			Question.Choice answer = selected.getSubittedAnswer();

			mText.setText(selected.getText());
			mPos.setText(
				String.format("%d/%d", mCurrQuestion + 1, mQuestions.size())
			);

            for (Question.Choice c: selected.choices()) {
            	RadioButton option = new RadioButton(c.getText());

            	option.getStyleClass().add("quiz-view-choice");
            	option.setToggleGroup(mChoiceGroup);
            	// Associates the current Question.Choice instance to the
				// button, to be later retrieved when the user submits their
				// answer
            	option.setUserData(c);

            	// If the answer already picked by the user for this question
				// equals the current Choice:
            	if (c.equals(answer))
                    mChoiceGroup.selectToggle(option);

            	mChoiceView.getChildren().add(option);
			}

            // If an answer has already been submitted
            if (answer != null) {
                mChoiceView.setDisable(true);
                mAnswerIndicator.setImage(
                	selected.isCorrect(answer) ? mCorrectImage : mWrongImage
				);
                mAccept.setDisable(true);
			} else {
            	mChoiceView.setDisable(false);
				mAnswerIndicator.setImage(null);
			}
		} else {
			mChoiceView.getChildren().add(mEmpty);
			mPos.setText("*/*");
		}
	}

	/**
	 *
	 * @param index Index of the {@link Question} to query display status for.
	 * @return {@code true} if it is possible for the {@code QuizView} to
	 * display the question at number {@code index}, {@code false} otherwise.
	 */
	private boolean canAdvanceTo (int index) {
		if (index < 0 || index >= mQuestions.size()) {
			return false;
		} else if (index == 0) {
			return true;
		} else {
			return mQuestions.get(index - 1).getSubittedAnswer() != null;
		}
	}
}
