package unibo.algat.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import unibo.algat.lesson.LessonLoader;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>Utility class designed to construct a "view" component capable of
 * displaying a given lesson data.</p>
 */
public class LessonViewFactory {
	static final String KEY_VIEW = "lesson.view";
	static final String VIEW_REF = "/res/";

	public static Pane lessonView (int lessonId) throws IOException {
		// TODO Test, even by direct use
        final ResourceBundle r = ResourceBundle.getBundle(
			LessonLoader.LESSONS_REF + lessonId
		);

        return FXMLLoader.load(
            LessonViewFactory.class.getResource(
            	VIEW_REF + r.getString(KEY_VIEW)
			),
			ResourceBundle.getBundle("ref.Interface")
		);
	}
}
