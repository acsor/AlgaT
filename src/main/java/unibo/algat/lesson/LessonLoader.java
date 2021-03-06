package unibo.algat.lesson;

import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>{@code LessonLoader} takes as input directory class paths, looks for
 * relevant {@code .properties} files of the form {@code Lesson<id>
 * .properties} and creates {@link Lesson} instances off that data. If
 * available, localized values will be returned.</p>
 * @see Lesson
 * @see QuestionLoader
 */
public class LessonLoader extends PropertiesLoader {
    /**
     * The base class path pointing to a directory containing {@code
     * .properties} lessons files.
     */
	private final String mBaseRef;
    private final String mBasePath;
	private Locale mLocale;

    static final String KEY_PREFIX = "lesson";
    static final String KEY_NAME = "name";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_TOPICS = "topics";
    static final String KEY_PSEUDO_CODE = "pseudoCode";

    static final String FILE_FORMAT = "Lesson%d";
    static final Pattern FILE_PATTERN = Pattern.compile(
        "^Lesson(\\d+).properties"
    );

    public LessonLoader (String classPath) {
        // TODO Is Locale.getDefault() what we really want?
        this(classPath, Locale.getDefault());
    }

    /**
     * @param classPath Class path of the directory containing lesson files
     * @param locale Locale fo fetch lesson data with
     */
    public LessonLoader (String classPath, Locale locale) {
        mBaseRef = classPath;
        mBasePath = "/" + String.join("/", classPath.split("\\."));
        mLocale = locale;
    }

    /**
     * @return The set of available lessons stored in the specified location.
     */
    public Set<Lesson> lessons () {
        final Set<Lesson> lessons = new HashSet<>();
        final Iterator<Path> paths = listProjectDir(mBasePath);

        while (paths.hasNext()) {
            Matcher m = FILE_PATTERN.matcher(
                paths.next().getFileName().toString()
            );

            if (m.matches()) {
                lessons.add(load(Integer.parseInt(m.group(1))));
            }
        }

        return lessons;
    }

    /**
     * @param lessonId Id of the lesson to fetch.
     * @return A {@code Lesson} instance, whose values have been set to the
     * specified locale, if available.
     */
    public Lesson load (int lessonId) {
        final ResourceBundle r = lessonBundle(lessonId);

        return new Lesson(
            lessonId,
            r.getString(String.join(".", KEY_PREFIX, KEY_NAME)),
            r.getString(String.join(".", KEY_PREFIX, KEY_DESCRIPTION)),
            r.getString(String.join(".", KEY_PREFIX, KEY_PSEUDO_CODE)),
            r.getString(String.join(".", KEY_PREFIX, KEY_TOPICS)).split(",")
        );
    }

    /**
     * @param lesson Lesson to fetch the associated {@link ResourceBundle} for
     * @return a {@code ResourceBundle} instance, containing localized assets
     * for the given lesson in the specified class path.
     */
    public ResourceBundle lessonBundle (Lesson lesson) {
        return lessonBundle(lesson.getId());
    }

    /**
     * @param lessonId Lesson id to fetch the associated {@link ResourceBundle}
     *                for
     * @return a {@code ResourceBundle} instance, containing localized assets
     * for the given lesson in the specified class path.
     */
    public ResourceBundle lessonBundle (int lessonId) {
        return ResourceBundle.getBundle(
            String.join(".", mBaseRef, String.format(FILE_FORMAT, lessonId)),
            mLocale
        );
    }
}
