package unibo.algat.lesson;

import java.util.*;

/**
 * <p>Basic class encapsulating data about a lesson.</p>
 */
public class Lesson {
    private int mId;
    private String mName;
    private String mDescription;
    /**
     * <p>The nested topic categories this lesson belongs to.</p>
     *
     * <p>As an example, if the current lesson is "Topological sorting", it may
     * be classified as {@code Sorting > Graph}. In that case, {@code
     * mTopics} will contain the values {@code "Graphs" > "Sorting"} in
     * a nested fashion.</p>
     */
    private final Queue<String> mTopics;
    private Set<Question> mQuestions;

    /**
     * @param id Id of the lesson
     * @param name Name of the lesson
     * @param description Lesson description, cannot be {@code null}
     * @param topics <b>Ordered</b> array of topics this lesson belongs to.
     *               Any {@code topics} string will be stripped of her
     *               leading and trailing whitespaces.
     * @throws NullPointerException if any of the object arguments is {@code
     * null}
     */
    Lesson(int id, String name, String description, String ... topics) {
        if (id >= 0)
            mId = id;
        else
            throw new IllegalArgumentException("Negative ids not allowed");

        if (name != null)
            mName = name;
        else
            throw new NullPointerException("name argument was null");

        if (description != null)
            mDescription = description;
        else
            throw new NullPointerException("description argument was null");

        mTopics = new ArrayDeque<>(topics.length);

        for (String topic: topics)
            mTopics.add(topic.strip());

        mQuestions = new HashSet<>();
    }

    /**
     * @return This lesson id.
     */
    public int getId () {
        return mId;
    }

    /**
     * @return This lesson name.
     */
    public String getName() {
        return mName;
    }

    /**
     * @return This lesson description.
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * @return A copy of the queued topics this lesson belongs to, ordered
     * from most general to the most specific, as in {@code Ordering >
     * In-place algorithms > Quicksort}.
     */
    public Queue<String> getTopics () {
        return new ArrayDeque<>(mTopics);
    }

    /**
     * @param q Question instance to associate to this lesson
	 * @throws NullPointerException if q is {@code null}
     */
    public void addQuestion(Question q) {
    	if (q != null)
            mQuestions.add(q);
        else
    	    throw new NullPointerException("Question argument was null");
	}

    /**
     * @return All the available questions associated to this lesson.
     */
    public Set<Question> questions () {
        return mQuestions;
    }

    @Override
    public boolean equals (Object other) {
        Lesson o;

        if (other instanceof Lesson) {
            o = (Lesson) other;

            return mId == o.mId;
        }

        return false;
    }

    @Override
    public String toString () {
        return String.format("[%d] %s", mId, mName);
    }
}
