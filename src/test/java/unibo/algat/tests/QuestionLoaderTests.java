package unibo.algat.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import unibo.algat.lesson.Question;
import unibo.algat.lesson.QuestionLoader;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionLoaderTests {
	static final String
		ASSETS_REF ="unibo.algat.tests.assets.QuestionLoaderTests";

	@ParameterizedTest
	@MethodSource("questionFactory")
	void testLoad (Question expected) {
		Question actual = new QuestionLoader(ASSETS_REF).load(
			expected.getLessonId(), expected.getId()
		);

		// Test values identity
		assertEquals(expected.getLessonId(), actual.getLessonId());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getText(), actual.getText());
		assertEquals(expected.choices(), actual.choices());
		assertEquals(expected.getLessonId(), actual.getLessonId());

		// Test Question.isCorrect()
		for (Question.Choice c: expected.choices()) {
            assertEquals(expected.isCorrect(c), actual.isCorrect(c));
		}
	}

	private static Iterable<Question> questionFactory () {
		// TODO Add more data to test
		Question expected = new Question(0, 1, "Question 2");
		Question.Choice correctChoice = new Question.Choice(4, "Choice 4");

		expected.addChoice(new Question.Choice(0, "Choice 0"));
		expected.addChoice(new Question.Choice(1, "Choice 1"));
		expected.addChoice(new Question.Choice(2, "Choice 2"));
		expected.addChoice(new Question.Choice(3, "Choice 3"));
		expected.addChoice(correctChoice);
		expected.setCorrectChoice(correctChoice);

		return Arrays.asList(expected);
	}
}
