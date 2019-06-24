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
    static final String KEY_PREFIX = "lesson";
    static final String KEY_NAME = "name";
    static final String KEY_TOPICS = "topics";
    static final String KEY_DESCRIPTION = "description";

    static final String LESSONS_REF = "res.lessons";
    static final String LESSON_FORMAT = "Lesson%d";

    static final String LESSONS_PATH = "/res/lessons/";
    static Pattern LESSON_FILTER = Pattern.compile("^Lesson(\\d+).properties");

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
        final ResourceBundle r = lessonBundle(lessonId);
        final Lesson l = new Lesson(
            lessonId,
            r.getString(String.join(".", KEY_PREFIX, KEY_NAME)),
            r.getString(String.join(".", KEY_PREFIX, KEY_DESCRIPTION)),
            r.getString(String.join(".", KEY_PREFIX, KEY_TOPICS)).split(",")
        );

        for (Question q: QuestionLoader.questions(lessonId))
            l.addQuestion(q);

        return l;
    }

    /**
     * @param lessonId Lesson id to fetch resources for
     * @return a {@code ResourceBundle} instance, containing localized assets
     * for the given lesson.
     */
    public static ResourceBundle lessonBundle(int lessonId) {
        return ResourceBundle.getBundle(
            String.join(
                ".", LESSONS_REF, String.format(LESSON_FORMAT, lessonId)
            )
        );
    }
}
