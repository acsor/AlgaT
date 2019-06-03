package unibo.algat.lesson;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class designed to provide read and write operations about a given
 * lesson data (e.g. showing how many of them there are, loading their
 * associated questions etc.) <br><br>
 *
 * For this purpose, {@code LessonLoader} relies on {@code .properties} files
 * and the associated {@link java.util.Properties} and {@link
 * java.util.ResourceBundle} classes.
 */
public class LessonLoader {
    private static final String KEY_PREFIX = "Lesson";
    private static final String KEY_NAME = "Name";
    private static final String KEY_TOPICS = "Topics";

    static Pattern LESSON_ID_PATTERN = Pattern.compile("^Lesson([1-9]\\d*).Id");

    /**
     * @return The "list" of available lessons for the program.
     */
    public static Set<Lesson> lessons () throws IOException {
        // TODO Base the lesson database on a more locale-independent data
        // source
        Properties p = new Properties();
        Set<Lesson> lessons = new HashSet<>();
        Matcher m;

        p.load(LessonLoader.class.getResourceAsStream(
                "/res/locale/Lessons.properties"
        ));

        for (String key: p.stringPropertyNames()) {
            m = LESSON_ID_PATTERN.matcher(key);

            if (m.matches()) {
                lessons.add(loadFromLocale(Integer.valueOf(m.group(1))));
            }
        }

        return lessons;
    }

    public static Lesson loadFromLocale (int key) {
        final ResourceBundle r = ResourceBundle.getBundle("res.locale.Lessons");

        return new Lesson(
                key,
                r.getString(String.format("Lesson%d.%s", key, KEY_NAME)),
                r.getString(String.format("Lesson%d.%s", key, KEY_TOPICS))
                        .split(",")
        );
    }
}
