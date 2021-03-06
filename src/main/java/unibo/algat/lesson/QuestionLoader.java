package unibo.algat.lesson;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code QuestionLoader} takes care of loading question-related data from
 * {@code .properties} files. Such files are stored in the {@code res/questions}
 * path and their file name follows the
 * {@code Question<LessonId>:<QuestionId>_<locale spec>.properties} format.
 */
public class QuestionLoader extends PropertiesLoader {
	private final String mBaseRef;
	private final String mBasePath;

	static final String KEY_TEXT = "Text";
	static final String KEY_CHOICE = "Choice.%s";
	static final String KEY_CORRECT_ID = "CorrectChoiceId";
	static final Pattern CHOICE_PATTERN = Pattern.compile("^Choice.(\\d+)");

	static final String FILE_FORMAT = "Question%d:%d";
	static final Pattern FILE_PATTERN = Pattern.compile(
		"^Question(\\d+):(\\d+).properties"
	);

	public QuestionLoader (String classPath) {
		mBaseRef = classPath;
		mBasePath = "/" + String.join("/", classPath.split("\\."));
	}

	/**
	 * @param lesson Lesson to fetch questions for
	 * @return A list of available questions related to the lesson identified
	 * by {@code lessonId}.
	 */
	public Set<Question> questions (Lesson lesson) {
		// TODO The Lesson and Question classes are too loosely coupled, do
		//  something to strengthen their relationship
		Set<Question> questions = new HashSet<>();
		final Iterator<Path> paths = listProjectDir(mBasePath);

		while (paths.hasNext()) {
			Matcher m = FILE_PATTERN.matcher(
				paths.next().getFileName().toString()
			);

			if (m.matches() && Integer.valueOf(m.group(1)) == lesson.getId()) {
				questions.add(load(
					Integer.valueOf(m.group(1)),
					Integer.valueOf(m.group(2))
				));
			}
		}

		return questions;
	}

	/**
	 * @param lessonId Id of the lesson the question is associated to
	 * @param questionId Id of the question
	 * @return A {@link Question} instance identified by the two id integers,
	 * with localized values from {@code .properties} files (if available).
	 */
	public Question load (int lessonId, int questionId) {
		final ResourceBundle r = ResourceBundle.getBundle(
			String.join(
				".", mBaseRef, String.format(FILE_FORMAT, lessonId, questionId)
			)
		);
		final String correctChoiceId = r.getString(KEY_CORRECT_ID);
		final Question out = new Question(
			lessonId, questionId, r.getString(KEY_TEXT)
		);
		Matcher m;

		// Load choices "list"
		for (String key: r.keySet()) {
			m = CHOICE_PATTERN.matcher(key);

			if (m.matches()) {
				out.addChoice(
					new Question.Choice(
						Integer.parseInt(m.group(1)), r.getString(key)
					)
				);
			}
		}

		out.setCorrectChoice(
			new Question.Choice(
				Integer.parseInt(correctChoiceId),
				r.getString(String.format(KEY_CHOICE, correctChoiceId))
			)
		);

		return out;
	}
}
