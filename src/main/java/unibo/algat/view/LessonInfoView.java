package unibo.algat.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import unibo.algat.lesson.Lesson;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>A GUI component responsible of displaying user info (run instructions,
 * descriptions etc.) in a given {@link LessonView}.
 * This component might be intended for use as a lateral panel, for e.g. in
 * an instance of {@link javafx.scene.layout.BorderPane}, although its uses
 * can be wider than that.</p>
 */
class LessonInfoView extends HBox {
	private Lesson mLesson;
	private final ResourceBundle mInterface;
	private boolean mHidden = false;

	@FXML private Button mHideButton;
	@FXML private ImageView mHideGraphic, mShowGraphic;
	@FXML private ScrollPane mBody;
	@FXML private Label mHowToTitle, mDescriptionTitle, mPseudoCodeTitle;
	@FXML private Label mHowTo, mDescription, mPseudoCode;

	/**
	 * Action invoked when the hide button is pressed.
	 */
	private final EventHandler<ActionEvent> mHideAction = e -> {
		mHidden = !mHidden;

		if (mHidden) {
			setMaxWidth(mHideButton.getWidth());
			mBody.setVisible(false);
			mHideButton.setGraphic(mShowGraphic);
		} else {
			setMaxWidth(Region.USE_COMPUTED_SIZE);
			mBody.setVisible(true);
			mHideButton.setGraphic(mHideGraphic);
		}
	};

	public LessonInfoView () throws IOException {
		this(null);
	}

	public LessonInfoView (Lesson lesson) throws IOException {
		FXMLLoader loader;

		mLesson = lesson;
		mInterface = ResourceBundle.getBundle("Interface");

		loader = new FXMLLoader(
			getClass().getResource("/view/LessonInfoView.fxml"), mInterface
		);
		loader.setRoot(this);
		loader.setController(this);

		loader.load();
	}

	void setLesson (Lesson lesson) {
		mLesson = lesson;

		updateLessonInfo();
	}

	Lesson getLesson () {
		return mLesson;
	}

	/**
	 * @param howToInfo Run instructions the user is shown. These are
	 * intended to explain what actions perform in order to execute the
	 * algorithm.
	 */
	void setHowToInfo (String howToInfo) {
		mHowTo.setText(howToInfo);
	}

	/**
	 * Getter method of {@link #setHowToInfo}.
	 */
	String getHowToInfo () {
		return mHowTo.getText();
	}

	@FXML
	private void initialize () {
		mHideButton.setOnAction(mHideAction);

		mHowToTitle.setText(mInterface.getString("gui.infoview.title.howto"));
		mDescriptionTitle.setText(
			mInterface.getString("gui.infoview.title.desc")
		);
		mPseudoCodeTitle.setText(
			mInterface.getString("gui.infoview.title.pseudocode")
		);

		updateLessonInfo();
	}

	/**
	 * Updates info shown for the current lesson, re-caching values.
	 */
	private void updateLessonInfo() {
		if (mLesson != null) {
			mDescription.setText(mLesson.getDescription());
			mPseudoCode.setText(mLesson.getPseudoCode());
		} else {
			mDescription.setText("");
			mPseudoCode.setText("");
		}
	}
}
