package unibo.algat.tests;

import org.junit.jupiter.api.Test;
import unibo.algat.lesson.Lesson;
import unibo.algat.lesson.LessonLoader;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * TODO Decide whether to improve and extend these tests.
 */
public class LessonLoaderTests {
	static final String
		ASSETS_REF = "unibo.algat.tests.assets.LessonLoaderTests";

	@Test
	/**
	 * <p>Tests {@code LessonLoader.lessons()}, or the whole {@code LessonLoader
	 * } class by that matter.</p>
	 */
	public void testLessons () {
		final LessonLoader l = new LessonLoader(ASSETS_REF, Locale.ROOT);
        final Map<Integer, Lesson> expected = new HashMap<>(3);
        Lesson e;

		expected.put(0, new Lesson(0, "Shortest Path Problem", "AAA", "Graph"));
		expected.put(1, new Lesson(1, "Minimum Spanning Tree", "BBB", "Graph"));
		expected.put(2, new Lesson(
			2, "Sample lesson name", "CCC", "A", "B", "C", "D", "E", "F", "...",
			"Z"
		));

		for (Lesson actual: l.lessons()) {
            e = expected.get(actual.getId());

            assertEquals(e.getId(), actual.getId());
			assertEquals(e.getName(), actual.getName());
			assertEquals(e.getDescription(), actual.getDescription());
			assertIterableEquals(e.getTopics(), actual.getTopics());
		}
	}
}
