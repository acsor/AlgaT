package unibo.algat.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <p>Utility class capable of constructing a "view object" out of a
 * {@link Lesson} instance.</p>
 */
public class LessonViewFactory {
	static final String KEY_VIEW = "lesson.view";
	static final String VIEW_PATH = "/view/";

	/**
	 * @param lesson Lesson to produce a view for
	 * @return The graphical object the lesson has been associated to
	 * @throws IOException Upon failure of loading the lesson view FXML file.
	 */
	public static Pane lessonView (Lesson lesson) throws IOException {
		final LessonLoader l = new LessonLoader(
			AlgaTController.LESSONS_DIR, Locale.getDefault()
		);
        final ResourceBundle r = l.lessonBundle(lesson.getId());

        return FXMLLoader.load(
            l.getClass().getResource(VIEW_PATH + r.getString(KEY_VIEW)),
			ResourceBundle.getBundle("Interface")
		);
	}
}
