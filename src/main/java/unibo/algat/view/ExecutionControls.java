package unibo.algat.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ExecutionControls extends HBox {
	private boolean mPlaying;

	@FXML ImageView mPlayImage;
	@FXML ImageView mPauseImage;

	@FXML Button mStop;
	@FXML Button mPrev;
	@FXML Button mTogglePlay;
	@FXML Button mNext;

	/**
	 * <p>Switches the graphic of {@code mTogglePlay} button from play to
	 * pause statuses, updating the {@code mPlaying} variable as well.</p>
	 */
	private final EventHandler<ActionEvent>
		mToggleHandler = new EventHandler<>() {
		@Override
		public void handle(ActionEvent event) {
			mPlaying = !mPlaying;

			mTogglePlay.setGraphic(mPlaying ? mPauseImage: mPlayImage);
		}
	};

	public ExecutionControls () throws IOException {
		FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/ExecutionControls.fxml")
		);

		l.setRoot(this);
		l.setController(this);

		l.load();
	}

	@FXML
	private void initialize () {
		mPlaying = false;

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

	public boolean isPlaying () {
		return mPlaying;
	}
}
