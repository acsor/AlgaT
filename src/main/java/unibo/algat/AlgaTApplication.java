package unibo.algat;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import unibo.algat.control.MainMenuController;
import unibo.algat.view.AlgaToolBar;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>The application class, behaving as a singleton to provide facilitated
 * access to menu bar(s), tool bar(s) and the {@code Stage} object.</p>
 */
public class AlgaTApplication extends Application {
	@FXML private MenuBar mMenuBar;
	@FXML private MainMenuController mMenuBarController;
	@FXML private AlgaToolBar mToolBar;
	private Stage mStage;

	private static AlgaTApplication sSingleton;

	public AlgaTApplication() {
		super();

		if (sSingleton == null) {
			sSingleton = this;
		} else {
			throw new IllegalStateException(
				"AlgaTApplication can only be instantiated once"
			);
		}
	}

	@Override
	public void start (Stage stage) throws IOException {
		final ResourceBundle r = ResourceBundle.getBundle("Interface");
		final FXMLLoader l = new FXMLLoader(
			getClass().getResource("/view/AlgaTApplication.fxml"), r
		);

		l.setController(this);
		mStage = stage;

		final Parent root = l.load();
		final Scene s = new Scene(root, 600, 500);

		s.getStylesheets().add("/static/AlgaT.css");
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

	public static AlgaTApplication getInstance () {
		return sSingleton;
	}

	public MenuBar getMenuBar () {
		return mMenuBar;
	}

	public MainMenuController getMenuBarController () {
		return mMenuBarController;
	}

	public AlgaToolBar getToolBar () {
		return mToolBar;
	}

	public void setWindowTitle (String title) {
		mStage.setTitle(title);
	}
}
