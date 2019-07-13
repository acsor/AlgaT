package unibo.algat.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AlgaToolBar extends ToolBar {
	private BooleanProperty mPlaying;

	@FXML ImageView mPlayImage, mPauseImage;
	@FXML Button mStop, mPrev, mTogglePlay, mNext;

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

		mPlaying = new SimpleBooleanProperty(this, "playing", false);
		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	private void initialize () {
		mTogglePlay.addEventHandler(ActionEvent.ACTION, mToggleHandler);
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

	public void setPlaying (boolean playing) {
        mPlaying.set(playing);
	}

	public boolean isPlaying () {
		return mPlaying.get();
	}

	public BooleanProperty playingProperty () {
		return mPlaying;
	}
}
