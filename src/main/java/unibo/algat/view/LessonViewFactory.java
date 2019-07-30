package unibo.algat.view;

import unibo.algat.AlgaTApplication;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.lang.reflect.Constructor;
import java.util.Locale;

/**
 * <p>Utility class capable of constructing a "view object" out of a
 * {@link Lesson} instance.</p>
 */
public class LessonViewFactory {
	static final String KEY_VIEW = "lesson.view";
	static final String VIEW_PATH = "/view/";

	/**
	 * @param lesson Lesson to produce a view for
	 * @return The graphical object the lesson is associated to
	 */
	public static LessonView lessonView (Lesson lesson) throws Exception {
		final LessonLoader loader = new LessonLoader(
			AlgaTApplication.LESSONS_DIR, Locale.getDefault()
		);
        Class<LessonView> toLoad;
        Constructor<LessonView> defaultConstructor;
        LessonView view;

		try {
			// TODO Remove cast and use instanceof operator
			toLoad = (Class<LessonView>) Class.forName(
				loader.lessonBundle(lesson).getString(KEY_VIEW)
			);
			defaultConstructor = toLoad.getConstructor();
			view = defaultConstructor.newInstance();
		} catch (Exception e) {
			// TODO Choose a more appropriate exception type
            throw new Exception(e);
		}

		view.setLesson(lesson);

		return view;
	}

	/**
	 * @param lesson Lesson whose view existence is to be tested
	 * @return {@code true} if the given {@link Lesson} has a view associated
	 * to it, {@code false} otherwise.
	 */
	public static boolean isAvailable (Lesson lesson) {
		final LessonLoader loader = new LessonLoader(
			AlgaTApplication.LESSONS_DIR, Locale.getDefault()
		);

		return !loader.lessonBundle(lesson).getString(KEY_VIEW).isBlank();
	}
}