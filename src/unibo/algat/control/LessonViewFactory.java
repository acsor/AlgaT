package unibo.algat.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>Utility class designed to construct a "view" component capable of
 * displaying a given lesson.</p>
 */
public class LessonViewFactory {
	static final String KEY_VIEW = "lesson.view";
	static final String VIEW_REF = "/res/view/";

	/**
	 * @param lesson Lesson to produce a view for
	 * @return The graphical object the lesson was associated to
	 * @throws IOException Upon failure of loading the lesson view FXML file.
	 */
	public static Pane lessonView (Lesson lesson) throws IOException {
		// TODO Test, even by direct use
        final ResourceBundle r = LessonLoader.lessonBundle(lesson.getId());

        return FXMLLoader.load(
            LessonViewFactory.class.getResource(
            	VIEW_REF + r.getString(KEY_VIEW)
			),
			ResourceBundle.getBundle("res.Interface")
		);
	}
}
