package unibo.algat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>The application class, behaving as a singleton to provide utility
 * access to the {@code Stage} object.</p>
 */
public class AlgaT extends Application {
	private static AlgaT sSingleton;
	private Stage mStage;

	public AlgaT () {
		super();

		if (sSingleton == null) {
			sSingleton = this;
		} else {
			throw new IllegalStateException(
				"AlgaT application can only be built once"
			);
		}
	}

	public static AlgaT getInstance () {
		return sSingleton;
	}

	@Override
	public void start (Stage stage) throws IOException {
		final ResourceBundle r = ResourceBundle.getBundle("Interface");
		mStage = stage;
		final Parent root = FXMLLoader.load(
			getClass().getResource("/view/AlgaT.fxml"), r
		);
		final Scene s = new Scene(root, 500, 500);

		mStage.setScene(s);
		mStage.setTitle(r.getString("gui.app.title"));
		mStage.getIcons().add(new Image(
			getClass().getResourceAsStream("/static/icon.png")
		));
		mStage.show();
	}

	public static void main (String[] args) {
		launch(args);
	}

	public void setWindowTitle (String title) {
		mStage.setTitle(title);
	}
}
