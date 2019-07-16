package unibo.algat.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AlgaToolBar extends ToolBar {
	private ObjectProperty<ToolBarUser> mUser;
	private BooleanProperty mPlaying;

	@FXML ImageView mPlayImage, mPauseImage;
	@FXML Button mStop, mPrev, mTogglePlay, mNext;
	@FXML Button mAdd, mRemove, mRandom, mClear;

	/**
	 * <p>Switches the graphic of {@code mTogglePlay} button from play to
	 * pause statuses, updating the {@code mPlaying} variable as well.</p>
	 */
	private final EventHandler<ActionEvent> mToggleHandler = event -> {
		mPlaying.set(!mPlaying.get());

		mTogglePlay.setGraphic(mPlaying.get() ? mPauseImage: mPlayImage);
	};

	public AlgaToolBar() throws IOException {
		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/AlgaToolBar.fxml")
		);

		mUser = new SimpleObjectProperty<>(this, "user", null);
		mPlaying = new SimpleBooleanProperty(this, "playing", false);
		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	private void initialize () {
		mTogglePlay.addEventHandler(ActionEvent.ACTION, mToggleHandler);
		reset();
	}

	/**
	 * @param user The new user for this tool bar. A {@code null} value is
	 *                 accepted, indicating that no object holds ownership of
	 *                 the toolbar.
	 */
	public void setUser (ToolBarUser user) {
		reset();
		mUser.set(user);

		if (user != null)
			user.onAcquireToolBar(this);
	}

	public ToolBarUser getUser () {
		return mUser.get();
	}

	/**
	 * @return The user of this tool bar, i.e. the object which currently
	 * determines the behavior of the toolbar (which buttons active, what
	 * actions to perform etc.).
	 */
	public ObjectProperty<ToolBarUser> userProperty() {
		return mUser;
	}

	public Button getStopButton () {
		return mStop;
	}

	public Button getPrevButton () {
		return mPrev;
	}

	public Button getTogglePlayButton () {
		return mTogglePlay;
	}

	public Button getNextButton () {
		return mNext;
	}

	public Button getAddButton () {
		return mAdd;
	}

	public Button getRemoveButton () {
		return mRemove;
	}

	public Button getRandomButton () {
		return mRandom;
	}

	public Button getClearButton () {
		return mClear;
	}

	public void setPlaying (boolean playing) {
        mPlaying.set(playing);
	}

	public boolean isPlaying () {
		return mPlaying.get();
	}

	public BooleanProperty playingProperty () {
		return mPlaying;
	}

	/**
	 * Resets the tool bar into a "tabula rasa" state, in which no additional
	 * event handlers are associated to the buttons and each of them is
	 * deactivated.
	 */
	private void reset () {
        getItems().stream().filter(n -> n instanceof Button).forEach(
			n -> {
				((Button) n).setOnAction(null);
				n.setDisable(true);
			}
		);
	}
}
