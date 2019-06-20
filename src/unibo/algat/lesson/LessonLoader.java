package unibo.algat.lesson;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Utility class designed to provide read and write operations about a given
 * lesson data (e.g. showing how many of them there are, loading their
 * associated questions etc).</p>
 *
 * <p>For this purpose, {@code LessonLoader} relies on {@code .properties} files
 * and the associated {@link java.util.ResourceBundle} class.</p>
 */
public class LessonLoader {
    public static final String KEY_PREFIX = "lesson";
    public static final String KEY_NAME = "name";
    public static final String KEY_TOPICS = "topics";

    public static final String LESSONS_REF = "res.lessons";
    public static final String LESSON_FORMAT = "Lesson%d";

    public static final String LESSONS_PATH = "/res/lessons/";
    public static Pattern LESSON_FILTER = Pattern.compile(
        "^Lesson(\\d+).properties"
    );

    /**
     * @return The "list" of available lessons for the program.
     */
    public static Set<Lesson> lessons () {
        final Scanner in = new Scanner(
            LessonLoader.class.getResourceAsStream(LESSONS_PATH)
        );
        final Set<Lesson> lessons = new HashSet<>();
        Matcher m;

        while (in.hasNextLine()) {
            m = LESSON_FILTER.matcher(in.nextLine());

            if (m.matches()) {
                lessons.add(
                    loadFromLocale(Integer.valueOf(m.group(1)))
                );
            }
        }

        return lessons;
    }

    /**
     * @param lessonId Id of the lesson to fetch.
     * @return A {@code Lesson} instance, whose values have been set to the
     * current locale, if available.
     */
    public static Lesson loadFromLocale (int lessonId) {
        final ResourceBundle r = ResourceBundle.getBundle(
            String.join(
                ".", LESSONS_REF, String.format(LESSON_FORMAT, lessonId)
            )
        );

        return new Lesson(
            lessonId,
            r.getString(String.join(".", KEY_PREFIX, KEY_NAME)),
            r.getString(String.join(".", KEY_PREFIX, KEY_TOPICS)).split(",")
        );
    }
}
